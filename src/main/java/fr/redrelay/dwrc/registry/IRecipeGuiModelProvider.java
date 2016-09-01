package fr.redrelay.dwrc.registry;

import java.util.List;

import fr.redrelay.dwrc.gui.IRecipeGui;
import fr.redrelay.dwrc.model.IRecipeModel;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public interface IRecipeGuiModelProvider {
	public boolean accept(GuiContainer gui);
	public boolean accept(Container container);
	public IRecipeGui getRecipeGui(GuiContainer gui, List<GuiButton> listButton);
	public IRecipeModel getRecipeModel(Container container);
}
