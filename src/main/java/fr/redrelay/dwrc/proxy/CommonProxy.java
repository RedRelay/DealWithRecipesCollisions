package fr.redrelay.dwrc.proxy;

import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.packet.CraftingResultPacket;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

	public void registerHandlers() {
	}

	public void registerChannels() {
		SimpleNetworkWrapper channel = DWRC.getChannel();
		channel.registerMessage(new CraftingResultPacket.CraftingResultPacketHandler(), CraftingResultPacket.class, 0, Side.SERVER);
	}
	
}
