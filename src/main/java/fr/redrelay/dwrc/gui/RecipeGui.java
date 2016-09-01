package fr.redrelay.dwrc.gui;

import java.util.List;

import org.lwjgl.util.Dimension;

import fr.redrelay.dwrc.exceptions.NoRecipeFinderFoundException;
import fr.redrelay.dwrc.model.IRecipeModel;
import fr.redrelay.dwrc.model.IRecipeModelListener;
import fr.redrelay.dwrc.registry.recipefinder.IRecipeFinder;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class RecipeGui implements IRecipeGui, IRecipeModelListener{

	private final IRecipeModel model;
	
	private String label;
	private GuiButton prev, next;
	
	private final int xOffset;
	private final int yOffset;
	private final Dimension labelLoc;
	
	public RecipeGui(GuiContainer gui, List<GuiButton> listButton, IRecipeFinder finder) {
		
		if(finder == null) {
			throw new NoRecipeFinderFoundException(gui.inventorySlots);
		}
		
		this.model = finder.getModel(gui.inventorySlots);
		this.model.addListener(this);
		
		xOffset = (gui.width-((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, "field_146999_f", "xSize")))/2;
		yOffset = (gui.height-((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, "field_147000_g", "ySize")))/2;
		
		Dimension loc = getPreviousButtonLocation();
		this.prev = new GuiButton(listButton.size(), xOffset+loc.getWidth(), yOffset+loc.getHeight(), 20, 20, "<");
		listButton.add(prev);
		loc = getNextButtonLocation();
		this.next = new GuiButton(listButton.size(), xOffset+loc.getWidth(), yOffset+loc.getHeight(), 20, 20, ">");
		listButton.add(next);
		
		labelLoc = getLabelLocation();
		
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
			gui.mc.fontRendererObj.drawString(label, xOffset+labelLoc.getWidth(), yOffset+labelLoc.getHeight(), 4210752);
		}
	}
	
	protected abstract Dimension getPreviousButtonLocation();
	protected abstract Dimension getNextButtonLocation();
	protected abstract Dimension getLabelLocation();
	
}
