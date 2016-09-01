package fr.redrelay.dwrc.registry.recipegui;

import java.util.List;

import fr.redrelay.dwrc.registry.recipegui.builder.RecipeGuiBuilder;
import fr.redrelay.dwrc.registry.recipegui.gui.IRecipeGui;
import fr.redrelay.dwrc.registry.recipegui.gui.RecipeGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.common.config.Configuration;

public abstract class RecipeGuiProvider implements IRecipeGuiProvider {

	protected final RecipeGuiBuilder builder;
	
	public RecipeGuiProvider(RecipeGuiBuilder builder) {
		this.builder = builder;
	}

	@Override
	public IRecipeGui getRecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		return new RecipeGui(gui, listButton, builder);
	}

	@Override
	public void onConfigChanged(Configuration config) {
		this.builder.onConfigChanged(config);
	}
	
	@Override
	public void sortProps(Configuration config) {
		builder.sortProps(config);
	}

}
