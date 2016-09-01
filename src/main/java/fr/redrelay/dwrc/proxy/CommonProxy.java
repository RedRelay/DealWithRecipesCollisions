package fr.redrelay.dwrc.proxy;

import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.finders.RecipeFinderWorkbench;
import fr.redrelay.dwrc.packet.CraftingResultPacket;
import fr.redrelay.dwrc.registry.recipefinder.RecipeFinderRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

	protected final RecipeFinderRegistry recipeFinderRegistry = new RecipeFinderRegistry();
	
	public void init(FMLInitializationEvent evt) {
		this.registerChannels();
		this.registerHandlers();
		this.registerRecipeFinders();
	}
	
	private void registerRecipeFinders() {
		recipeFinderRegistry.register(new RecipeFinderWorkbench());
	}
	
	protected void registerHandlers() {
	}

	private void registerChannels() {
		SimpleNetworkWrapper channel = DWRC.getChannel();
		channel.registerMessage(new CraftingResultPacket.CraftingResultPacketHandler(), CraftingResultPacket.class, 0, Side.SERVER);
	}
	
	public RecipeFinderRegistry getRecipeFinderRegistry() {
		return recipeFinderRegistry;
	}
}
