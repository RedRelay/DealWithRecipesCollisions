package fr.redrelay.dwrc.model;

import fr.redrelay.dwrc.CraftingUtils;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;

public class RecipeModelWorkbench extends RecipeModel{

	private final ContainerWorkbench container;
	
	public RecipeModelWorkbench(ContainerWorkbench container) {
		this.container = container;
	}
	
	@Override
	protected InventoryCrafting getInputInventory() {
		return container.craftMatrix;
	}

	@Override
	protected IInventory getOutputInventory() {
		return container.craftResult;
	}

	@Override
	protected void updateMatchedRecipes() {
		CraftingUtils.getMatchedRecipes(matchedRecipes, container);
	}

}
