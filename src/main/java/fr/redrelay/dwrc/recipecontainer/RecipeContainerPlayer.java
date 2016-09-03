package fr.redrelay.dwrc.recipecontainer;

import fr.redrelay.dwrc.registry.recipecontainer.IRecipeContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class RecipeContainerPlayer implements IRecipeContainer {

	@Override
	public boolean accept(Container container) {
		return container instanceof ContainerPlayer;
	}

	@Override
	public InventoryCrafting getInventoryCrafting(Container container) {
		return ((ContainerPlayer)container).craftMatrix;
	}

	@Override
	public World getWorld(Container container) {
		return ((EntityPlayer)ReflectionHelper.getPrivateValue(ContainerPlayer.class, (ContainerPlayer)container, "field_82862_h", "thePlayer")).getEntityWorld();
	}

	@Override
	public IInventory getResultInventory(Container container) {
		return ((ContainerPlayer)container).craftResult;
	}

	@Override
	public int getSlotCraftingIndex() {
		return 0;
	}



}
