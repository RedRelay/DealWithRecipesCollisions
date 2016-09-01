package fr.redrelay.dwrc.recipeguiprovider;

import fr.redrelay.dwrc.registry.recipegui.RecipeGuiProvider;
import fr.redrelay.dwrc.registry.recipegui.builder.RecipeGuiBuilder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeGuiProviderWorkbench extends RecipeGuiProvider {

	public RecipeGuiProviderWorkbench() {
		super(new RecipeGuiBuilder("workbench", 96, 59, 148, 59, 124, 64));
	}
	
	@Override
	public boolean accept(GuiContainer gui) {
		return gui instanceof GuiCrafting;
	}

}
