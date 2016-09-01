package fr.redrelay.dwrc.model.provider;

import java.util.List;

import fr.redrelay.dwrc.gui.RecipeGuiWorkbench;
import fr.redrelay.dwrc.model.RecipeModelWorkbench;
import fr.redrelay.dwrc.registry.IRecipeGuiModelProvider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;

public class ProviderWorkbench implements IRecipeGuiModelProvider {

	@Override
	public boolean accept(GuiContainer gui) {
		return gui instanceof GuiCrafting;
	}
	
	public boolean accept(Container container) {
		return container instanceof ContainerWorkbench;
	}

	@Override
	public RecipeGuiWorkbench getRecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		return new RecipeGuiWorkbench(gui, listButton, this.getRecipeModel(gui.inventorySlots));
	}
	
	public RecipeModelWorkbench getRecipeModel(Container container) {
		return new RecipeModelWorkbench((ContainerWorkbench) container);
	}

}
