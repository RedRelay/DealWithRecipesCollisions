package fr.redrelay.dwrc.packet;

import java.util.LinkedList;
import java.util.List;

import fr.redrelay.dwrc.CraftingUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
		public IMessage onMessage(CraftingResultPacket message, MessageContext ctx) {
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			if(player.openContainer instanceof ContainerWorkbench) {
				ContainerWorkbench container = (ContainerWorkbench) player.openContainer;
				
				//Cheat verifications
				final List<IRecipe> matchedRecipes = new LinkedList<IRecipe>();
				CraftingUtils.getMatchedRecipes(matchedRecipes, container);
				if(matchedRecipes.size() < 2) {
					return null;
				}
				
				for(IRecipe recipe : matchedRecipes) {
					final ItemStack trustedCraftingResult = recipe.getCraftingResult(container.craftMatrix);
					if(ItemStack.areItemStacksEqual(trustedCraftingResult, message.craftingResult)) {
						container.putStackInSlot(0, message.craftingResult);
						return null;
					}
				}
				
			}
			return null;
		}
		
	}

}
