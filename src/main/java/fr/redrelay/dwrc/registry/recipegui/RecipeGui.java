package fr.redrelay.dwrc.registry.recipegui;

import java.util.List;

import fr.redrelay.dwrc.model.IRecipeModel;
import fr.redrelay.dwrc.model.IRecipeModelListener;
import fr.redrelay.dwrc.model.RecipeModel;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeGui implements IRecipeGui, IRecipeModelListener{

	private final IRecipeModel model;
	
	private String label;
	private GuiButton prev, next;
	
	private final int xOffset;
	private final int yOffset;
	
	private final int xLabel, yLabel;
	
	public RecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		this(gui, listButton, 0, 0, 0, 20, 0, 20);
	}
	
	public RecipeGui(GuiContainer gui, List<GuiButton> listButton, int xPrev, int yPrev, int xNext, int yNext, int xLabel, int yLabel) {
		this(gui, listButton, xPrev, yPrev, xNext, yNext, xLabel, yLabel, listButton.size(), listButton.size()+1);
	}
	
	public RecipeGui(GuiContainer gui, List<GuiButton> listButton, int xPrev, int yPrev, int xNext, int yNext, int xLabel, int yLabel, int idPrev, int idNext) {
		this.model = new RecipeModel(gui.inventorySlots);
		this.model.addListener(this);
		
		xOffset = (gui.width-((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, "field_146999_f", "xSize")))/2;
		yOffset = (gui.height-((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, "field_147000_g", "ySize")))/2;
		
		this.prev = new GuiButton(idPrev, xOffset+xPrev, yOffset+yPrev, 20, 20, "<");
		this.next = new GuiButton(idNext, xOffset+xNext, yOffset+yNext, 20, 20, ">");
		
		listButton.add(prev);
		listButton.add(next);
		
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		
		this.onUpdate();
		this.onCursorChange();
	}
	
	@Override
	public IRecipeModel getModel() {
		return model;
	}
	
	@Override
	public void onUpdate() {
		boolean enableOverlay = model.isOverlayEnabled();
		prev.visible = enableOverlay;
		next.visible = enableOverlay;
	}
	
	@Override
	public void onCursorChange() {
		prev.enabled = model.getCursor() != 0;
		next.enabled = model.getCursor() != model.getNbRecipes()-1;
		label = (model.getCursor()+1) + "/" + model.getNbRecipes();
	}
	
	@Override
	public void onActionPerformed(GuiButton button) {
		if(button == prev) {
			model.setCursor(model.getCursor()-1);
		}else if(button == next) {
			model.setCursor(model.getCursor()+1);
		}
	}

	@Override
	public void drawOverlay(GuiScreen gui) {
		if(model.isOverlayEnabled()) {
			gui.mc.fontRendererObj.drawString(label, xOffset+xLabel, yOffset+yLabel, 4210752);
		}
	}
	
}
