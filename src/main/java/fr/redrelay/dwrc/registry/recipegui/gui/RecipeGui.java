package fr.redrelay.dwrc.registry.recipegui.gui;

import java.util.List;

import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.packet.RecipeCursorPacket;
import fr.redrelay.dwrc.registry.recipegui.builder.IRecipeGuiBuilder;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeGui implements IRecipeGui {

	private String label;
	private GuiButton prev, next;
	
	private final int xOffset;
	private final int yOffset;
	
	private final int xLabel, yLabel;
	
	private int recipeNb;
	private int cur;
	
	public RecipeGui(GuiContainer gui, List<GuiButton> listButton, IRecipeGuiBuilder builder) {
		this(gui, listButton, builder, listButton.size(), listButton.size()+1);
	}
	
	public RecipeGui(GuiContainer gui, List<GuiButton> listButton, IRecipeGuiBuilder builder, int idPrev, int idNext) {
		
		xOffset = (gui.width-((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, "field_146999_f", "xSize")))/2;
		yOffset = (gui.height-((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, "field_147000_g", "ySize")))/2;
		
		this.prev = new GuiButton(idPrev, xOffset+builder.getXPrev(), yOffset+builder.getYPrev(), 20, 20, "<");
		this.next = new GuiButton(idNext, xOffset+builder.getXNext(), yOffset+builder.getYNext(), 20, 20, ">");
		
		listButton.add(prev);
		listButton.add(next);
		
		this.xLabel = builder.getXLabel();
		this.yLabel = builder.getYLabel();
		
		this.setRecipeNb(0);
	}
	
	@Override
	public void setCursor(int cur) {
			this.cur = cur;
			prev.enabled = cur != 0;
			next.enabled = cur != recipeNb-1;
			label = (cur+1) + "/" + recipeNb;
	}

	@Override
	public void setRecipeNb(int recipeNb) {
		this.recipeNb = recipeNb;
		this.setCursor(0);
		
		boolean enableOverlay = isOverlayEnabled();
		prev.visible = enableOverlay;
		next.visible = enableOverlay;
	}
	
	@Override
	public boolean isOverlayEnabled() {
		return recipeNb > 1;
	}
	
	private void askForCursor(int cur) {
		DWRC.getChannel().sendToServer(new RecipeCursorPacket(cur));
	}
	
	@Override
	public void onActionPerformed(GuiButton button) {
		if(button == prev) {
			askForCursor(cur-1);
		}else if(button == next) {
			askForCursor(cur+1);
		}
	}

	@Override
	public void drawOverlay(GuiScreen gui) {
		if(isOverlayEnabled()) {
			gui.mc.fontRendererObj.drawString(label, xOffset+xLabel, yOffset+yLabel, 4210752);
		}
	}
	
}
