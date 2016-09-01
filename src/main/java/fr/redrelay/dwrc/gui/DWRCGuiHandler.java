package fr.redrelay.dwrc.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.redrelay.dwrc.CraftingUtils;
import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.packet.CraftingResultPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class DWRCGuiHandler {

	private final ContainerWorkbench container;
	private final List<ItemStack> cache = new LinkedList<ItemStack>();
	private final List<IRecipe> matchedRecipes = new ArrayList<IRecipe>();
	private int cur;
	
	private String label;
	private GuiButton prev, next;
	
	private final int xOffset;
	private final int yOffset;
	
	public DWRCGuiHandler(GuiContainer gui, List<GuiButton> listButton, ContainerWorkbench container) {
		this.container = container;
		
		xOffset = (gui.width-((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, "field_146999_f", "xSize")))/2;
		yOffset = (gui.height-((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, "field_147000_g", "ySize")))/2;
		
		this.prev = new GuiButton(listButton.size(), xOffset+96, yOffset+59, 20, 20, "<");
		listButton.add(prev);
		this.next = new GuiButton(listButton.size(), xOffset+148, yOffset+59, 20, 20, ">");
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
		
		if(matchedRecipes.size() < 2) {
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
		
		label = (cur+1) + "/" + matchedRecipes.size();
		
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

	public void drawOverlay(GuiScreen gui) {
		if(cur == -1) return;
		gui.mc.fontRendererObj.drawString(label, xOffset+124, yOffset+64, 4210752);
		
	}

	
}
