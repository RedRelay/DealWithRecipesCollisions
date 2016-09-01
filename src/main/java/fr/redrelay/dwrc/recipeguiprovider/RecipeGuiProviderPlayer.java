package fr.redrelay.dwrc.recipeguiprovider;

import java.util.List;

import fr.redrelay.dwrc.registry.recipegui.RecipeGuiProvider;
import fr.redrelay.dwrc.registry.recipegui.builder.RecipeGuiBuilder;
import fr.redrelay.dwrc.registry.recipegui.gui.IRecipeGui;
import fr.redrelay.dwrc.registry.recipegui.gui.RecipeGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;

public class RecipeGuiProviderPlayer extends RecipeGuiProvider {

	public RecipeGuiProviderPlayer() {
		super(new RecipeGuiBuilder("inventory", 97, 59, 149, 59, 125, 64));
	}

	
	@Override
	public boolean accept(GuiContainer gui) {
		return gui instanceof GuiInventory;
	}

	@Override
	public IRecipeGui getRecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		return new RecipeGui(gui, listButton, builder, listButton.size()+2, listButton.size()+3);
	}

}
