package fr.redrelay.dwrc.model;

import fr.redrelay.dwrc.registry.recipefinder.IRecipeFinder;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;

public class RecipeModelWorkbench extends RecipeModel{

	private final ContainerWorkbench container;
	
	public RecipeModelWorkbench(IRecipeFinder finder, ContainerWorkbench container) {
		super(finder);
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
	protected ContainerWorkbench getContainer() {
		return container;
	}

}
