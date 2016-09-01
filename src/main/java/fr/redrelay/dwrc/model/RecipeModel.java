package fr.redrelay.dwrc.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.redrelay.dwrc.CraftingUtils;
import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.packet.CraftingResultPacket;
import fr.redrelay.dwrc.registry.recipefinder.IRecipeFinder;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public abstract class RecipeModel implements IRecipeModel{

	private final List<IRecipeModelListener> listeners = new ArrayList<IRecipeModelListener>();
	
	private final List<ItemStack> cache = new LinkedList<ItemStack>();
	protected final List<IRecipe> matchedRecipes = new ArrayList<IRecipe>();
	private int cur;
	
	private final IRecipeFinder finder;
	
	public RecipeModel(IRecipeFinder finder) {
		this.finder = finder;
	}
	
	@Override
	public void update() {
		cache.clear();
		for(int i = 0; i<getInputInventory().getSizeInventory(); i++) {
			cache.add(getInputInventory().getStackInSlot(i));
		}
		
		
		matchedRecipes.clear();
		CraftingUtils.getMatchedRecipes(matchedRecipes, finder.getInventoryCrafting(getContainer()), finder.getWorld(getContainer()));
		
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
		
		DWRC.getChannel().sendToServer(new CraftingResultPacket(matchedRecipes.get(cur).getCraftingResult(getInputInventory())));
		getOutputInventory().setInventorySlotContents(0, matchedRecipes.get(cur).getCraftingResult(getInputInventory()));
		
		for(int i=0; i<listeners.size(); i++) {
			listeners.get(i).onCursorChange();
		}
	}
	
	@Override
	public boolean isDirty() {
		if(cache.size() != getInputInventory().getSizeInventory()) return true;
		
		int i = 0;
		for(ItemStack cachedStack : cache) {
			if(cachedStack != getInputInventory().getStackInSlot(i)) {
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

	protected abstract InventoryCrafting getInputInventory();
	protected abstract IInventory getOutputInventory();
	protected abstract Container getContainer(); 
}
