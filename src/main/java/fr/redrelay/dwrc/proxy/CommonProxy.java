package fr.redrelay.dwrc.proxy;

import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.model.provider.ProviderWorkbench;
import fr.redrelay.dwrc.packet.CraftingResultPacket;
import fr.redrelay.dwrc.registry.RecipeGuiModelRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

	protected RecipeGuiModelRegistry registry = new RecipeGuiModelRegistry();
	
	public void registerRecipeGuiModelProviders() {
		registry.register(new ProviderWorkbench());
	}
	
	public void registerHandlers() {
	}

	public void registerChannels() {
		SimpleNetworkWrapper channel = DWRC.getChannel();
		channel.registerMessage(new CraftingResultPacket.CraftingResultPacketHandler(), CraftingResultPacket.class, 0, Side.SERVER);
	}
	
}
