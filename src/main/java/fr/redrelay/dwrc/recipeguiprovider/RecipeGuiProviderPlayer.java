package fr.redrelay.dwrc.recipeguiprovider;

import java.util.List;

import fr.redrelay.dwrc.registry.recipegui.IRecipeGui;
import fr.redrelay.dwrc.registry.recipegui.IRecipeGuiProvider;
import fr.redrelay.dwrc.registry.recipegui.RecipeGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;

public class RecipeGuiProviderPlayer implements IRecipeGuiProvider {

	@Override
	public boolean accept(GuiContainer gui) {
		return gui instanceof GuiInventory;
	}

	@Override
	public IRecipeGui getRecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		return new RecipeGui(gui, listButton, 97, 59, 149, 59, 125, 64, listButton.size()+2, listButton.size()+3);
	}

}
