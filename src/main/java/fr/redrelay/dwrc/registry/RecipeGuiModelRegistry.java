package fr.redrelay.dwrc.registry;

import java.util.LinkedList;
import java.util.List;

import fr.redrelay.dwrc.gui.IRecipeGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

public class RecipeGuiModelRegistry {

	private final List<IRecipeGuiModelProvider> providers = new LinkedList<IRecipeGuiModelProvider>();
	
	public void register(IRecipeGuiModelProvider provider) {
		providers.add(provider);
	}
	
	public IRecipeGui findRecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		for(IRecipeGuiModelProvider provider : providers) {
			if(provider.accept(gui)) {
				return provider.getRecipeGui(gui, listButton);
			}
		}
		return null;
	}
	
	public List<IRecipeGuiModelProvider> getProviders() {
		return providers;
	}
	
}
