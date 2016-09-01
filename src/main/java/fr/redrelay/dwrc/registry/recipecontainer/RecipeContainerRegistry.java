package fr.redrelay.dwrc.registry.recipecontainer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.Container;

public class RecipeContainerRegistry {

	private final List<IRecipeContainer> recipeFinders = new ArrayList<IRecipeContainer>();
	
	public IRecipeContainer findRecipeFinder(Container container) {
		for(int i=0; i<recipeFinders.size(); i++) {
			if(recipeFinders.get(i).accept(container)) {
				return recipeFinders.get(i);
			}
		}
		return null;
	}
	
	public void register(IRecipeContainer recipeFinder) {
		this.recipeFinders.add(recipeFinder);
	}
	
	public List<IRecipeContainer> getRegisteredRecipeFinders() {
		return recipeFinders;
	}
	
}
