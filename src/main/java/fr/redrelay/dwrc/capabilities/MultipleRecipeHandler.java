package fr.redrelay.dwrc.capabilities;

import java.util.ArrayList;
import java.util.List;

import fr.redrelay.dwrc.CraftingUtils;
import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.packet.RecipeCursorPacket;
import fr.redrelay.dwrc.packet.RecipeNbPacket;
import fr.redrelay.dwrc.registry.recipecontainer.IRecipeContainer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class MultipleRecipeHandler implements IContainerListener{

	private int listenerIndex;

	private final EntityPlayerMP player;
	private final Container container;
	private final IRecipeContainer recipeContainer;
	private final ArrayList<ItemStack> craftingCache = new ArrayList<ItemStack>();
	private final ArrayList<IRecipe> matchedRecipes = new ArrayList<IRecipe>();

	private boolean craftResultUpdated;
	private boolean multipleRecipeEnabled;
	private int cur;

	public MultipleRecipeHandler(EntityPlayerMP player, Container container, IRecipeContainer recipeContainer) {
		this.player = player;
		this.container = container;
		this.recipeContainer = recipeContainer;


		this.matchedRecipes.clear();
		if(container.getSlot(recipeContainer.getSlotCraftingIndex()).getStack() != null) {
			CraftingUtils.getMatchedRecipes(matchedRecipes, container, recipeContainer);
		}

		this.cur = 0;
		this.craftResultUpdated = false;

		this.updateCache();

		this.startListen();

	}

	@Override
	public void updateCraftingInventory(Container containerToSend, List<ItemStack> itemsList) {
	}

	/**
	 * Don't send slot contents, just fix SlotCrafting when CraftingInventory updated or CraftingResult updated by a craft
	 */
	@Override
	public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
		if(slotInd == recipeContainer.getSlotCraftingIndex()) {

			boolean cacheUpdated = updateCache();
			if(stack == null || cacheUpdated) {
				matchedRecipes.clear();
				if(stack != null) {
					CraftingUtils.getMatchedRecipes(matchedRecipes, container, recipeContainer);
				}
				sendRecipeNb();
			}else if(craftResultUpdated){
				craftResultUpdated = false;
				player.connection.sendPacket(new SPacketSetSlot(containerToSend.windowId, slotInd, stack));
			}else {
				onCursorUpdate();
			}
		}

	}

	@Override
	public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {
	}

	@Override
	public void sendAllWindowProperties(Container containerIn, IInventory inventory) {

	}

	private void sendRecipeNb() {
		boolean shouldEnableMultipleRecipe = matchedRecipes.size() > 1;
		if(shouldEnableMultipleRecipe || multipleRecipeEnabled) {
			multipleRecipeEnabled = shouldEnableMultipleRecipe;
			this.cur = 0;
			DWRC.getChannel().sendTo(new RecipeNbPacket(matchedRecipes.size()), player);
		}

	}

	private boolean updateCache() {
		final InventoryCrafting inv = recipeContainer.getInventoryCrafting(container);

		if(inv.getSizeInventory() != craftingCache.size()) {
			craftingCache.clear();
			for(int i=0; i<inv.getSizeInventory(); i++) {
				craftingCache.add(inv.getStackInSlot(i));
			}
			return true;
		}

		boolean updated = false;
		for(int i = 0; i <craftingCache.size(); i++) {
			if(craftingCache.get(i) != inv.getStackInSlot(i)) {
				craftingCache.set(i, inv.getStackInSlot(i));
				updated = true;
			}
		}

		return updated;
	}

	private List<IContainerListener> getListener() {
		return ReflectionHelper.getPrivateValue(Container.class, container, "field_75149_d", "listeners");
	}

	public void startListen() {
		final List<IContainerListener> listeners = getListener();
		listeners.add(this);
		this.listenerIndex = listeners.size()-1;
	}

	public void stopListen() {
		final List<IContainerListener> listeners = getListener();
		this.getListener().remove(listenerIndex);
	}

	public void setCursor(int cur) {
		if(cur < 0 || cur >= matchedRecipes.size()) return;
		this.cur = cur;
		onCursorUpdate();
	}

	private void onCursorUpdate() {
		container.getSlot(recipeContainer.getSlotCraftingIndex()).putStack(matchedRecipes.get(cur).getCraftingResult(recipeContainer.getInventoryCrafting(container)));
		craftResultUpdated = true;
		container.detectAndSendChanges();
		DWRC.getChannel().sendTo(new RecipeCursorPacket(cur), player);
	}

}
