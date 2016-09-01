package fr.redrelay.dwrc.registry.recipefinder;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.world.World;

public interface IRecipeFinder {
	public boolean accept(Container container);
	public InventoryCrafting getInventoryCrafting(Container container);
	public IInventory getResultInventory(Container container);
	public World getWorld(Container container);
}
