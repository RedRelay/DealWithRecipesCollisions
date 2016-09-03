package fr.redrelay.dwrc.proxy;

import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.capabilities.MultipleRecipeCapability;
import fr.redrelay.dwrc.recipecontainer.RecipeContainerPlayer;
import fr.redrelay.dwrc.recipecontainer.RecipeContainerWorkbench;
import fr.redrelay.dwrc.registry.recipecontainer.RecipeContainerRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonProxy {

	protected final RecipeContainerRegistry recipeContainerRegistry = new RecipeContainerRegistry();
	
	public void preInit(FMLPreInitializationEvent evt) {
		registerCapabilities();
	}
	
	public void init(FMLInitializationEvent evt) {
		this.registerHandlers();
		this.registerRecipeContainers();
	}
	
	public void postInit(FMLPostInitializationEvent evt) {
	}
	
	private void registerRecipeContainers() {
		recipeContainerRegistry.register(new RecipeContainerWorkbench());
		recipeContainerRegistry.register(new RecipeContainerPlayer());
	}
	
	private void registerHandlers() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void registerCapabilities() {
		MultipleRecipeCapability.register();
	}
	
	public RecipeContainerRegistry getRecipeContainerRegistry() {
		return recipeContainerRegistry;
	}
	
	@SubscribeEvent
	public void onAttachEntityCapabilities(AttachCapabilitiesEvent.Entity evt) {
		if(!evt.getEntity().getEntityWorld().isRemote && evt.getEntity() instanceof EntityPlayer) {
			evt.addCapability(new ResourceLocation(DWRC.MODID, PlayerCapabilityProvider.KEY), new PlayerCapabilityProvider((EntityPlayerMP) evt.getEntity()));
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone evt) {
		if(!evt.getEntityPlayer().getEntityWorld().isRemote && evt.isWasDeath()) {
			evt.getEntityPlayer().getCapability(MultipleRecipeCapability.CAPABILITY, null).copyFrom(evt.getOriginal().getCapability(MultipleRecipeCapability.CAPABILITY, null));
		}
	}
	
	@SubscribeEvent
	public void onContainerOpenned(PlayerContainerEvent.Open evt) {
		if(!evt.getEntityPlayer().getEntityWorld().isRemote) {
			evt.getEntityPlayer().getCapability(MultipleRecipeCapability.CAPABILITY, null).onContainerOpenned();
		}
	}
	
	@SubscribeEvent
	public void onPlayerContainerClosed(PlayerContainerEvent.Close evt) {
		if(!evt.getEntityPlayer().getEntityWorld().isRemote) {
			evt.getEntityPlayer().getCapability(MultipleRecipeCapability.CAPABILITY, null).onContainerClosed();
		}
	}
	
	public static class PlayerCapabilityProvider implements ICapabilityProvider {

		public static final String KEY = "PlayerCapabilities";
		private final MultipleRecipeCapability data;
		
		public PlayerCapabilityProvider(EntityPlayerMP player) {
			data = new MultipleRecipeCapability(player);
		}
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == MultipleRecipeCapability.CAPABILITY;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			return (T) data;
		}
		
	}
	

}
