package fr.redrelay.dwrc.registry.recipegui.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.redrelay.dwrc.CraftingUtils;
import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.exceptions.NoRecipeFinderFoundException;
import fr.redrelay.dwrc.packet.CraftingResultPacket;
import fr.redrelay.dwrc.registry.recipecontainer.IRecipeContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeModel implements IRecipeModel{

	private final List<IRecipeModelListener> listeners = new ArrayList<IRecipeModelListener>();
	
	private final List<ItemStack> cache = new LinkedList<ItemStack>();
	protected final List<IRecipe> matchedRecipes = new ArrayList<IRecipe>();
	private int cur;
	
	private final IRecipeContainer recipeContainer;
	private final Container container;
	
	public RecipeModel(Container container) {
		this.container = container;
		this.recipeContainer = DWRC.getRecipeContainerRegistry().findRecipeFinder(container);
		
		if(this.recipeContainer == null) {
			throw new NoRecipeFinderFoundException(container);
		}
	}
	
	@Override
	public void update() {
		cache.clear();
		for(int i = 0; i<recipeContainer.getInventoryCrafting(container).getSizeInventory(); i++) {
			cache.add(recipeContainer.getInventoryCrafting(container).getStackInSlot(i));
		}
		
		
		matchedRecipes.clear();
		CraftingUtils.getMatchedRecipes(matchedRecipes, recipeContainer.getInventoryCrafting(container), recipeContainer.getWorld(container));
		
		setCursor(0);
		
		for(int i=0; i<listeners.size(); i++) {
			listeners.get(i).onUpdate();
		}
		
	}
	
	private void onCursorChange() {
		if(matchedRecipes.size() < 2) {
			cur = -1;
			return;
		}
		
		if(cur < 0) {
			cur = 0;
		}else if(cur >= matchedRecipes.size()){
			cur = matchedRecipes.size()-1;
		}
		
		DWRC.getChannel().sendToServer(new CraftingResultPacket(matchedRecipes.get(cur).getCraftingResult(recipeContainer.getInventoryCrafting(container))));
		recipeContainer.getResultInventory(container).setInventorySlotContents(0, matchedRecipes.get(cur).getCraftingResult(recipeContainer.getInventoryCrafting(container)));
		
		for(int i=0; i<listeners.size(); i++) {
			listeners.get(i).onCursorChange();
		}
	}
	
	@Override
	public boolean isDirty() {
		if(cache.size() != recipeContainer.getInventoryCrafting(container).getSizeInventory()) return true;
		
		int i = 0;
		for(ItemStack cachedStack : cache) {
			if(cachedStack != recipeContainer.getInventoryCrafting(container).getStackInSlot(i)) {
				return true;
			}
			i++;
		}
		
		return false;
	}

	@Override
	public boolean isOverlayEnabled() {
		return matchedRecipes.size() > 1;
	}
	
	@Override
	public int getCursor() {
		return cur;
	}
	
	@Override
	public int getNbRecipes() {
		return matchedRecipes.size();
	}
	
	@Override
	public void setCursor(int cur) {
		this.cur = cur;
		onCursorChange();
	}
	
	@Override
	public void addListener(IRecipeModelListener listener) {
		this.listeners.add(listener);
	}
	
	@Override
	public void removeListener(IRecipeModelListener listener) {
		this.listeners.remove(listener);
	}
}