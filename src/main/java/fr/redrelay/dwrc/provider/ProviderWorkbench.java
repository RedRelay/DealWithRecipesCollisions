package fr.redrelay.dwrc.provider;

import java.util.List;

import fr.redrelay.dwrc.registry.recipegui.IRecipeGui;
import fr.redrelay.dwrc.registry.recipegui.IRecipeGuiProvider;
import fr.redrelay.dwrc.registry.recipegui.RecipeGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ProviderWorkbench implements IRecipeGuiProvider {

	@Override
	public boolean accept(GuiContainer gui) {
		return gui instanceof GuiCrafting;
	}

	@Override
	public IRecipeGui getRecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		return new RecipeGui(gui, listButton, 96, 59, 148, 59, 124, 64);
	}

}
