package fr.redrelay.dwrc.proxy;

import fr.redrelay.dwrc.gui.IRecipeGui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	private IRecipeGui handler;
	
	@Override
	public void registerHandlers() {
		super.registerHandlers();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post evt) {
		if(evt.getGui() instanceof GuiContainer) {
			handler = registry.findRecipeGui((GuiContainer) evt.getGui(), evt.getButtonList());
		}
	}
	
	@SubscribeEvent
	public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post evt) {
		if(handler == null) return;
		handler.onActionPerformed(evt.getButton());
	}
	
	@SubscribeEvent
	public void onDrawScreenPre(GuiScreenEvent.DrawScreenEvent.Pre evt) {
		if(handler == null) return;
		if(handler.getModel().isDirty()) {
			handler.getModel().update();;
		}
	}
	
	@SubscribeEvent
	public void onDrawScreenPost(GuiScreenEvent.DrawScreenEvent.Post evt) {
		if(handler == null) return;
		handler.drawOverlay(evt.getGui());
	}
}
