package fr.redrelay.dwrc.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class DWRCGuiHandler {

	private final InventoryCrafting inv;
	private final List<ItemStack> cache = new LinkedList<ItemStack>();
	private final List<IRecipe> matchedRecipes = new ArrayList<IRecipe>();
	private int cur;
	private GuiButton prev, next;
	
	public DWRCGuiHandler(List<GuiButton> listButton, InventoryCrafting inv) {
		this.inv = inv;
		
		this.prev = new GuiButton(listButton.size(), 0, 0, 20, 20, "<");
		listButton.add(prev);
		this.next = new GuiButton(listButton.size(), 20, 0, 20, 20, ">");
		listButton.add(next);

		this.update();
	}
	
	public void update() {
		
		cache.clear();
		for(int i = 0; i<inv.getSizeInventory(); i++) {
			cache.add(inv.getStackInSlot(i));
		}
		
		updateMatchedRecipes();
		
		boolean enableOverlay = matchedRecipes.size() > 1;
		prev.visible = enableOverlay;
		next.visible = enableOverlay;
		cur = enableOverlay ? 0 : -1;
		onCursorChange();
	}
	
	private void updateMatchedRecipes() {
		this.matchedRecipes.clear();
    	for(IRecipe r : CraftingManager.getInstance().getRecipeList()) {
    		if(r.matches(inv, null)) { //TODO Maybe set a world ?
    			matchedRecipes.add(r);
    		}
    	}
	}
	
	private void onCursorChange() {
		if(cur < 0) {
			cur = -1;
		}else if(cur >= matchedRecipes.size()){
			cur = matchedRecipes.size()-1;
		}
		
		prev.enabled = cur != 0;
		next.enabled = cur != matchedRecipes.size()-1;
	}
	
	private void setCursor(int cur) {
		this.cur = cur;
		onCursorChange();
	}

	public void onActionPerformed(GuiButton button) {
		if(button == prev) {
			setCursor(cur-1);
		}else if(button == next) {
			setCursor(cur+1);
		}
	}

	public boolean isDirty() {
		if(cache.size() != inv.getSizeInventory()) return true;
		
		int i = 0;
		for(ItemStack cachedStack : cache) {
			if(cachedStack != inv.getStackInSlot(i)) {
				return true;
			}
			i++;
		}
		
		return false;
	}

	
}
