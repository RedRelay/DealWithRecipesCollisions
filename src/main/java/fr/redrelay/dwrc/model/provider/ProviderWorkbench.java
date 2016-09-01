package fr.redrelay.dwrc.model.provider;

import java.util.List;

import fr.redrelay.dwrc.gui.RecipeGuiWorkbench;
import fr.redrelay.dwrc.registry.recipefinder.IRecipeFinder;
import fr.redrelay.dwrc.registry.recipegui.IRecipeGuiProvider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ProviderWorkbench implements IRecipeGuiProvider {

	@Override
	public boolean accept(GuiContainer gui) {
		return gui instanceof GuiCrafting;
	}
	
	public boolean accept(Container container) {
		return container instanceof ContainerWorkbench;
	}

	@Override
	public RecipeGuiWorkbench getRecipeGui(GuiContainer gui, List<GuiButton> listButton, IRecipeFinder finder) {
		return new RecipeGuiWorkbench(gui, listButton, finder);
	}

}
