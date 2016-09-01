package fr.redrelay.dwrc;

import java.util.List;

import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CraftingUtils {

	private CraftingUtils() {
	}
	
	public static void getMatchedRecipes(List<IRecipe> matchedRecipes, ContainerWorkbench container) {
		matchedRecipes.clear();
		
		World world = ReflectionHelper.getPrivateValue(ContainerWorkbench.class, container, "field_75161_g", "worldObj");
		
		for(IRecipe r : CraftingManager.getInstance().getRecipeList()) {
    		if(r.matches(container.craftMatrix, world)) { //TODO Maybe set a world ?
    			matchedRecipes.add(r);
    		}
    	}
	}
	
}
