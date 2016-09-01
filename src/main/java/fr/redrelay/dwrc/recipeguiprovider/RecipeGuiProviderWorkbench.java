package fr.redrelay.dwrc.recipeguiprovider;

import java.util.List;

import fr.redrelay.dwrc.registry.recipegui.IRecipeGuiProvider;
import fr.redrelay.dwrc.registry.recipegui.builder.RecipeGuiBuilder;
import fr.redrelay.dwrc.registry.recipegui.gui.IRecipeGui;
import fr.redrelay.dwrc.registry.recipegui.gui.RecipeGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeGuiProviderWorkbench implements IRecipeGuiProvider {

	private final RecipeGuiBuilder builder = new RecipeGuiBuilder("workbench", 96, 59, 148, 59, 124, 64);
	
	@Override
	public boolean accept(GuiContainer gui) {
		return gui instanceof GuiCrafting;
	}

	@Override
	public IRecipeGui getRecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		return new RecipeGui(gui, listButton, builder);
	}

	@Override
	public void onConfigChanged(Configuration config) {
		builder.onConfigChanged(config);
		
	}

}
