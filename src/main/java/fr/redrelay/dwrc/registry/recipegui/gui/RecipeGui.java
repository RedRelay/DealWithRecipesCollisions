package fr.redrelay.dwrc.registry.recipegui.gui;

import java.util.List;

import fr.redrelay.dwrc.registry.recipegui.builder.IRecipeGuiBuilder;
import fr.redrelay.dwrc.registry.recipegui.model.IRecipeModel;
import fr.redrelay.dwrc.registry.recipegui.model.IRecipeModelListener;
import fr.redrelay.dwrc.registry.recipegui.model.RecipeModel;
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
	
	public RecipeGui(GuiContainer gui, List<GuiButton> listButton, IRecipeGuiBuilder builder) {
		this(gui, listButton, builder, listButton.size(), listButton.size()+1);
	}
	
	public RecipeGui(GuiContainer gui, List<GuiButton> listButton, IRecipeGuiBuilder builder, int idPrev, int idNext) {
		this.model = new RecipeModel(gui.inventorySlots);
		this.model.addListener(this);
		
		xOffset = (gui.width-((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, "field_146999_f", "xSize")))/2;
		yOffset = (gui.height-((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, "field_147000_g", "ySize")))/2;
		
		this.prev = new GuiButton(idPrev, xOffset+builder.getXPrev(), yOffset+builder.getYPrev(), 20, 20, "<");
		this.next = new GuiButton(idNext, xOffset+builder.getXNext(), yOffset+builder.getYNext(), 20, 20, ">");
		
		listButton.add(prev);
		listButton.add(next);
		
		this.xLabel = builder.getXLabel();
		this.yLabel = builder.getYLabel();
		
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
