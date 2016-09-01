package fr.redrelay.dwrc.model;

import fr.redrelay.dwrc.registry.recipefinder.IRecipeFinder;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;

public class RecipeModelPlayer extends RecipeModel {

	private final ContainerPlayer container;
	
	public RecipeModelPlayer(IRecipeFinder finder, ContainerPlayer container) {
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
	protected Container getContainer() {
		return container;
	}

}
