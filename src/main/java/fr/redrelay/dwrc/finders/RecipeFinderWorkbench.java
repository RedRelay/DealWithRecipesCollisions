package fr.redrelay.dwrc.finders;

import fr.redrelay.dwrc.model.IRecipeModel;
import fr.redrelay.dwrc.model.RecipeModelWorkbench;
import fr.redrelay.dwrc.registry.recipefinder.IRecipeFinder;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RecipeFinderWorkbench implements IRecipeFinder{

	@Override
	public boolean accept(Container container) {
		return container instanceof ContainerWorkbench;
	}

	@Override
	public InventoryCrafting getInventoryCrafting(Container container) {
		return ((ContainerWorkbench)container).craftMatrix;
	}

	@Override
	public World getWorld(Container container) {
		return ReflectionHelper.getPrivateValue(ContainerWorkbench.class, (ContainerWorkbench)container, "field_75161_g", "worldObj");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IRecipeModel getModel(Container container) {
		return new RecipeModelWorkbench(this, (ContainerWorkbench) container);
	}

}
