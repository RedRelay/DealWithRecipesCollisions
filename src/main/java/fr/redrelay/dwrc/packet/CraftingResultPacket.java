package fr.redrelay.dwrc.packet;

import fr.redrelay.dwrc.capabilities.CraftingResultCapability;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Client -> Server
 * @author RedRelay
 *
 */
public class CraftingResultPacket implements IMessage {

	private ItemStack craftingResult;
	
	public CraftingResultPacket() {
	}
	
	public CraftingResultPacket(ItemStack craftingResult){
		this.craftingResult = craftingResult;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.craftingResult = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, craftingResult);
	}
	
	public static class CraftingResultPacketHandler implements IMessageHandler<CraftingResultPacket, IMessage> {

		@Override
		public IMessage onMessage(final CraftingResultPacket message, final MessageContext ctx) {
			
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                	ctx.getServerHandler().playerEntity.getCapability(CraftingResultCapability.CAPABILITY, null).changeCraftingResult(message.craftingResult);;
                }
			});
			
			return null;
		}
		
	}

}
