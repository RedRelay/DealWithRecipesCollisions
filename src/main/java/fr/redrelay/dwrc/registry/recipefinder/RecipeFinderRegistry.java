package fr.redrelay.dwrc.registry.recipefinder;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.Container;

public class RecipeFinderRegistry {

	private final List<IRecipeFinder> recipeFinders = new ArrayList<IRecipeFinder>();
	
	public IRecipeFinder findRecipeFinder(Container container) {
		for(int i=0; i<recipeFinders.size(); i++) {
			if(recipeFinders.get(i).accept(container)) {
				return recipeFinders.get(i);
			}
		}
		return null;
	}
	
	public void register(IRecipeFinder recipeFinder) {
		this.recipeFinders.add(recipeFinder);
	}
	
	public List<IRecipeFinder> getRegisteredRecipeFinders() {
		return recipeFinders;
	}
	
}
