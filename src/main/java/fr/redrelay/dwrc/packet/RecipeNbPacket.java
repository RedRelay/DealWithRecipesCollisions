package fr.redrelay.dwrc.packet;

import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.registry.recipegui.gui.IRecipeGui;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Server -> Client
 * @author RedRelay
 *
 */
public class RecipeNbPacket implements IMessage {

	private int recipeNb;
	
	public RecipeNbPacket() {
	}
	
	public RecipeNbPacket(int recipeNb){
		this.recipeNb = recipeNb;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.recipeNb = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(recipeNb);
	}
	
	public static class RecipeNbPacketClientHandler implements IMessageHandler<RecipeNbPacket, IMessage> {

		@Override
		public IMessage onMessage(final RecipeNbPacket message, final MessageContext ctx) {
			
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                	
                	final IRecipeGui gui = DWRC.getClientProxy().getHandler();
                	if(gui != null) {
                		gui.setRecipeNb(message.recipeNb);
                	}
                	
                }
			});
			
			return null;
		}
		
	}

}
