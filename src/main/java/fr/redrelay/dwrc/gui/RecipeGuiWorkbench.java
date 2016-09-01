package fr.redrelay.dwrc.gui;


import java.util.List;

import org.lwjgl.util.Dimension;

import fr.redrelay.dwrc.registry.recipefinder.IRecipeFinder;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeGuiWorkbench extends RecipeGui {

	
	public RecipeGuiWorkbench(GuiContainer gui, List<GuiButton> listButton, IRecipeFinder finder) {
		super(gui, listButton, finder);
	}

	@Override
	protected Dimension getPreviousButtonLocation() {
		return new Dimension(96, 59);
	}

	@Override
	protected Dimension getNextButtonLocation() {
		return new Dimension(148, 59);
	}

	@Override
	protected Dimension getLabelLocation() {
		return new Dimension(124, 64);
	}

}
