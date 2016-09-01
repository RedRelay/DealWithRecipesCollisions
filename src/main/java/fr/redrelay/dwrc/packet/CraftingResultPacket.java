package fr.redrelay.dwrc.packet;

import java.util.LinkedList;
import java.util.List;

import fr.redrelay.dwrc.CraftingUtils;
import fr.redrelay.dwrc.DWRC;
import fr.redrelay.dwrc.registry.recipefinder.IRecipeFinder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
                	EntityPlayer player = ctx.getServerHandler().playerEntity;
        			
        			IRecipeFinder finder = DWRC.getRecipeFinderRegistry().findRecipeFinder(player.openContainer);
        			
        			if(finder != null) {
        				
        				//Cheat verifications
        				final List<IRecipe> matchedRecipes = new LinkedList<IRecipe>();
        				CraftingUtils.getMatchedRecipes(matchedRecipes, finder.getInventoryCrafting(player.openContainer), finder.getWorld(player.openContainer));
        				if(matchedRecipes.size() < 2) {
        					return;
        				}
        				
        				for(IRecipe recipe : matchedRecipes) {
        					final ItemStack trustedCraftingResult = recipe.getCraftingResult(finder.getInventoryCrafting(player.openContainer));
        					if(ItemStack.areItemStacksEqual(trustedCraftingResult, message.craftingResult)) {
        						player.openContainer.putStackInSlot(0, message.craftingResult);
        						return;
        					}
        				}
        				
        			}
                }
			});
			
			return null;
		}
		
	}

}
