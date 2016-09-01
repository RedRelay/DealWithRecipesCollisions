package fr.redrelay.dwrc.model;

import fr.redrelay.dwrc.registry.recipefinder.IRecipeFinder;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
