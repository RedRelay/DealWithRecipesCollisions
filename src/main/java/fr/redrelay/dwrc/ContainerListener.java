package fr.redrelay.dwrc;

import java.util.ArrayList;
import java.util.List;

import fr.redrelay.dwrc.packet.RecipeNbPacket;
import fr.redrelay.dwrc.registry.recipecontainer.IRecipeContainer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class ContainerListener implements IContainerListener {

	private final IRecipeContainer recipeContainer;
	private final EntityPlayerMP player;
	private final List<IRecipe> matchedRecipes = new ArrayList<IRecipe>();
	private boolean multipleRecipeEnabled;
	
	public ContainerListener(IRecipeContainer recipeContainer, EntityPlayerMP player) {
		this.recipeContainer = recipeContainer;
		this.player = player;
	}
	
	@Override
	public void updateCraftingInventory(Container containerToSend, List<ItemStack> itemsList) {
		
		matchedRecipes.clear();
		//TODO perf : don't do if container.craftingResult = null (because no recipe anyway)
		
		CraftingUtils.getMatchedRecipes(matchedRecipes, containerToSend, recipeContainer);
		if(matchedRecipes.size() > 1) {
			multipleRecipeEnabled = true;
			DWRC.getChannel().sendTo(new RecipeNbPacket(matchedRecipes.size()), player);
		}

	}

	@Override
	public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
	}

	@Override
	public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {
	}

	@Override
	public void sendAllWindowProperties(Container containerIn, IInventory inventory) {

	}

}
