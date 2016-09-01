package fr.redrelay.dwrc.proxy;

import fr.redrelay.dwrc.recipecontainer.RecipeContainerPlayer;
import fr.redrelay.dwrc.recipecontainer.RecipeContainerWorkbench;
import fr.redrelay.dwrc.registry.recipecontainer.RecipeContainerRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	protected final RecipeContainerRegistry recipeContainerRegistry = new RecipeContainerRegistry();
	
	public void preInit(FMLPreInitializationEvent evt) {
		
	}
	
	public void init(FMLInitializationEvent evt) {
		this.registerHandlers();
		this.registerRecipeContainers();
	}
	
	private void registerRecipeContainers() {
		recipeContainerRegistry.register(new RecipeContainerWorkbench());
		recipeContainerRegistry.register(new RecipeContainerPlayer());
	}
	
	protected void registerHandlers() {
	}
	
	public RecipeContainerRegistry getRecipeContainerRegistry() {
		return recipeContainerRegistry;
	}
}
