package fr.redrelay.dwrc.capabilities;

import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.registry.recipecontainer.IRecipeContainer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * IContainerListener : SHOULD BE THE LAST LISTENER !
 * @author RedRelay
 *
 */
public class MultipleRecipeCapability {

	@CapabilityInject(MultipleRecipeCapability.class)
	public static final Capability<MultipleRecipeCapability> CAPABILITY = null;

	private final EntityPlayerMP player;
	
	private MultipleRecipeHandler openContainerHandler;
	
	
	public MultipleRecipeCapability() {
		this(null);
	}

	public MultipleRecipeCapability(EntityPlayerMP player) {
		this.player = player;
	}
	
	public void onContainerOpenned() {
		IRecipeContainer recipeContainer = DWRC.getRecipeContainerRegistry().findRecipeFinder(player.openContainer);
		if(recipeContainer != null) {
			if(this.openContainerHandler != null) {
				this.openContainerHandler.stopListen();
			}
			this.openContainerHandler = new MultipleRecipeHandler(player, player.openContainer, recipeContainer);
		}
	}
	
	public void onContainerClosed() {
		this.openContainerHandler.stopListen();
		this.openContainerHandler = null;
		
		player.openContainer = player.inventoryContainer;
		onContainerOpenned();
	}

	public MultipleRecipeHandler getOpenContainerHandler() {
		return openContainerHandler;
	}

	public static void register() {
		CapabilityManager.INSTANCE.register(MultipleRecipeCapability.class, new Storage(), MultipleRecipeCapability.class);
	}

	public static class Storage implements IStorage<MultipleRecipeCapability> {
		@Override
		public NBTBase writeNBT(Capability<MultipleRecipeCapability> capability, MultipleRecipeCapability instance, EnumFacing side) {
			return null;
		}
		@Override
		public void readNBT(Capability<MultipleRecipeCapability> capability, MultipleRecipeCapability instance, EnumFacing side, NBTBase nbt) {
		}
	}

	public void copyFrom(MultipleRecipeCapability c) {
		this.openContainerHandler = c.openContainerHandler;
	}

}
