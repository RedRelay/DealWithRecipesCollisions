package fr.redrelay.dwrc.packet;

import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.capabilities.MultipleRecipeCapability;
import fr.redrelay.dwrc.capabilities.MultipleRecipeHandler;
import fr.redrelay.dwrc.registry.recipegui.gui.IRecipeGui;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Client -> Server
 * @author RedRelay
 *
 */
public class RecipeCursorPacket implements IMessage {

	private int cursor;
	
	public RecipeCursorPacket() {
	}
	
	public RecipeCursorPacket(int cursor){
		this.cursor = cursor;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.cursor = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(cursor);
	}
	
	public static class RecipeCursorPacketServerHandler implements IMessageHandler<RecipeCursorPacket, IMessage> {

		@Override
		public IMessage onMessage(final RecipeCursorPacket message, final MessageContext ctx) {
			
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                	MultipleRecipeHandler handler = ctx.getServerHandler().playerEntity.getCapability(MultipleRecipeCapability.CAPABILITY, null).getOpenContainerHandler();
                	if(handler != null) {
                		handler.setCursor(message.cursor);
                	}
                }
			});
			
			return null;
		}
		
	}
	
	public static class RecipeCursorPacketClientHandler implements IMessageHandler<RecipeCursorPacket, IMessage> {

		@Override
		public IMessage onMessage(final RecipeCursorPacket message, final MessageContext ctx) {
			
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                	final IRecipeGui gui = DWRC.getClientProxy().getHandler();
                	if(gui != null) {
                		gui.setCursor(message.cursor);
                	}
                }
			});
			
			return null;
		}
		
	}

}
