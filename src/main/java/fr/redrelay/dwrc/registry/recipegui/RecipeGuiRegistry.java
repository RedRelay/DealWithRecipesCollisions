package fr.redrelay.dwrc.registry.recipegui;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeGuiRegistry {

	private final List<IRecipeGuiProvider> providers = new LinkedList<IRecipeGuiProvider>();
	
	public void register(IRecipeGuiProvider provider) {
		providers.add(provider);
	}
	
	public IRecipeGui findRecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		for(IRecipeGuiProvider provider : providers) {
			if(provider.accept(gui)) {
				return provider.getRecipeGui(gui, listButton);
			}
		}
		return null;
	}
	
	public List<IRecipeGuiProvider> getProviders() {
		return providers;
	}
	
}
