package fr.redrelay.dwrc;

import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class CraftingUtils {

	private CraftingUtils() {
	}
	
	public static void getMatchedRecipes(List<IRecipe> matchedRecipes, InventoryCrafting inv, World world) {
		
		for(IRecipe r : CraftingManager.getInstance().getRecipeList()) {
    		if(r.matches(inv, world)) {
    			matchedRecipes.add(r);
    		}
    	}
	}
	
}
