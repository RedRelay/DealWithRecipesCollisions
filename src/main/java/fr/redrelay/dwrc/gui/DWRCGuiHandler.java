package fr.redrelay.dwrc.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.redrelay.dwrc.CraftingUtils;
import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.packet.CraftingResultPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class DWRCGuiHandler {

	private final ContainerWorkbench container;
	private final List<ItemStack> cache = new LinkedList<ItemStack>();
	private final List<IRecipe> matchedRecipes = new ArrayList<IRecipe>();
	private int cur;
	private GuiButton prev, next;
	
	public DWRCGuiHandler(List<GuiButton> listButton, ContainerWorkbench container) {
		this.container = container;
		
		this.prev = new GuiButton(listButton.size(), 0, 0, 20, 20, "<");
		listButton.add(prev);
		this.next = new GuiButton(listButton.size(), 20, 0, 20, 20, ">");
		listButton.add(next);

		this.update();
	}
	
	public void update() {
		
		cache.clear();
		for(int i = 0; i<container.craftMatrix.getSizeInventory(); i++) {
			cache.add(container.craftMatrix.getStackInSlot(i));
		}
		
		CraftingUtils.getMatchedRecipes(matchedRecipes, container);
		
		boolean enableOverlay = matchedRecipes.size() > 1;
		prev.visible = enableOverlay;
		next.visible = enableOverlay;
		setCursor(0);
	}
	
	private void onCursorChange() {
		
		if(matchedRecipes.isEmpty()) {
			cur = -1;
			return;
		}
		
		if(cur < 0) {
			cur = 0;
		}else if(cur >= matchedRecipes.size()){
			cur = matchedRecipes.size()-1;
		}
		
		prev.enabled = cur != 0;
		next.enabled = cur != matchedRecipes.size()-1;
		
		DWRC.getChannel().sendToServer(new CraftingResultPacket(matchedRecipes.get(cur).getCraftingResult(container.craftMatrix)));
		container.craftResult.setInventorySlotContents(0, matchedRecipes.get(cur).getCraftingResult(container.craftMatrix));
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
		if(cache.size() != container.craftMatrix.getSizeInventory()) return true;
		
		int i = 0;
		for(ItemStack cachedStack : cache) {
			if(cachedStack != container.craftMatrix.getStackInSlot(i)) {
				return true;
			}
			i++;
		}
		
		return false;
	}

	
}
