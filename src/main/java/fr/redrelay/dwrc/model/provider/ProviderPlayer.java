package fr.redrelay.dwrc.model.provider;

import java.util.List;

import fr.redrelay.dwrc.gui.IRecipeGui;
import fr.redrelay.dwrc.gui.RecipeGuiPlayer;
import fr.redrelay.dwrc.registry.recipefinder.IRecipeFinder;
import fr.redrelay.dwrc.registry.recipegui.IRecipeGuiProvider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;

public class ProviderPlayer implements IRecipeGuiProvider {

	@Override
	public boolean accept(GuiContainer gui) {
		return gui instanceof GuiInventory;
	}

	@Override
	public IRecipeGui getRecipeGui(GuiContainer gui, List<GuiButton> listButton, IRecipeFinder finder) {
		return new RecipeGuiPlayer(gui, listButton, finder);
	}

}
