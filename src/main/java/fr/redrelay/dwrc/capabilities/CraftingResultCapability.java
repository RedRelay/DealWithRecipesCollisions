package fr.redrelay.dwrc.capabilities;

import java.util.LinkedList;
import java.util.List;

import fr.redrelay.dwrc.CraftingUtils;
import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.registry.recipecontainer.IRecipeContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CraftingResultCapability {

	@CapabilityInject(CraftingResultCapability.class)
	public static final Capability<CraftingResultCapability> CAPABILITY = null;

	private final EntityPlayer player;
	private ItemStack craftingResult;

	public CraftingResultCapability() {
		this(null);
	}

	public CraftingResultCapability(EntityPlayer player) {
		this.player = player;
	}
	
	public void updateCraftingSlot() {
		this.changeCraftingResult(craftingResult);
	}

	public void changeCraftingResult(ItemStack craftingResult) {

		if(player.openContainer == null) return;

		IRecipeContainer recipeContainer = DWRC.getRecipeContainerRegistry().findRecipeFinder(player.openContainer);

		if(recipeContainer != null) {

			//Cheat verifications
			final List<IRecipe> matchedRecipes = new LinkedList<IRecipe>();
			CraftingUtils.getMatchedRecipes(matchedRecipes, recipeContainer.getInventoryCrafting(player.openContainer), recipeContainer.getWorld(player.openContainer));
			if(matchedRecipes.size() < 2) {
				this.craftingResult = null;
				return;
			}

			for(IRecipe recipe : matchedRecipes) {
				final ItemStack trustedCraftingResult = recipe.getCraftingResult(recipeContainer.getInventoryCrafting(player.openContainer));
				if(ItemStack.areItemStacksEqual(trustedCraftingResult, craftingResult)) {
					this.craftingResult = craftingResult;
					player.openContainer.putStackInSlot(0, craftingResult);
					return;
				}
			}

		}
	}
	
	public void onContainerClosed() {
		this.craftingResult = null;
	}
	
	public void copyFrom(CraftingResultCapability c) {
		this.craftingResult = c.craftingResult;
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(CraftingResultCapability.class, new Storage(), CraftingResultCapability.class);
	}

	public static class Storage implements IStorage<CraftingResultCapability> {
		@Override
		public NBTBase writeNBT(Capability<CraftingResultCapability> capability, CraftingResultCapability instance, EnumFacing side) {
			return null;
		}
		@Override
		public void readNBT(Capability<CraftingResultCapability> capability, CraftingResultCapability instance, EnumFacing side, NBTBase nbt) {
		}
	}

}
