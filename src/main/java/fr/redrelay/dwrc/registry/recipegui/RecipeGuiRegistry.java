package fr.redrelay.dwrc.registry.recipegui;

import java.util.ArrayList;
import java.util.List;

import fr.redrelay.dwrc.registry.recipegui.gui.IRecipeGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeGuiRegistry {

	private final List<IRecipeGuiProvider> providers = new ArrayList<IRecipeGuiProvider>();
	
	public void register(IRecipeGuiProvider provider) {
		providers.add(provider);
	}
	
	public IRecipeGui findRecipeGui(GuiContainer gui, List<GuiButton> listButton) {
		for(int i = 0; i<providers.size(); i++) {
			if(providers.get(i).accept(gui)) {
				return providers.get(i).getRecipeGui(gui, listButton);
			}
		}
		return null;
	}
	
	public List<IRecipeGuiProvider> getProviders() {
		return providers;
	}
	
	public void onConfigChanged(Configuration config) {
		for(int i = 0; i<providers.size(); i++) {
			providers.get(i).onConfigChanged(config);
		}
	}
	
}
