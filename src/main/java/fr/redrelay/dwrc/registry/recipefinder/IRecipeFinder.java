package fr.redrelay.dwrc.registry.recipefinder;

import fr.redrelay.dwrc.model.IRecipeModel;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRecipeFinder {
	public boolean accept(Container container);
	public InventoryCrafting getInventoryCrafting(Container container);
	public World getWorld(Container container);
	@SideOnly(Side.CLIENT)
	public IRecipeModel getModel(Container container);
}
