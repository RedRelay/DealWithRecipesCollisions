package fr.redrelay.dwrc.registry.recipegui;

import java.util.LinkedList;
import java.util.List;

import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.gui.IRecipeGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

public class RecipeGuiRegistry {

	private final List<IRecipeGuiProvider> providers = new LinkedList<IRecipeGuiProvider>();
	
	public void register(IRecipeGuiProvider provider) {
		providers.add(provider);
	}
	
	public IRecipeGui findRecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		for(IRecipeGuiProvider provider : providers) {
			if(provider.accept(gui)) {
				return provider.getRecipeGui(gui, listButton, DWRC.getRecipeFinderRegistry().findRecipeFinder(gui.inventorySlots));
			}
		}
		return null;
	}
	
	public List<IRecipeGuiProvider> getProviders() {
		return providers;
	}
	
}
