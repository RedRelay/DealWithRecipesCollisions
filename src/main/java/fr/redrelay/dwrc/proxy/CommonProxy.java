package fr.redrelay.dwrc.proxy;

import fr.redrelay.dwrc.finders.RecipeFinderWorkbench;
import fr.redrelay.dwrc.registry.recipefinder.RecipeFinderRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class CommonProxy {

	protected final RecipeFinderRegistry recipeFinderRegistry = new RecipeFinderRegistry();
	
	public void init(FMLInitializationEvent evt) {
		this.registerHandlers();
		this.registerRecipeFinders();
	}
	
	private void registerRecipeFinders() {
		recipeFinderRegistry.register(new RecipeFinderWorkbench());
	}
	
	protected void registerHandlers() {
	}
	
	public RecipeFinderRegistry getRecipeFinderRegistry() {
		return recipeFinderRegistry;
	}
}
