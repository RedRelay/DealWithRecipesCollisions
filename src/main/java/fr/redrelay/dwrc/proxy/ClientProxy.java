package fr.redrelay.dwrc.proxy;

import fr.redrelay.dwrc.Config;
import fr.redrelay.dwrc.recipeguiprovider.RecipeGuiProviderPlayer;
import fr.redrelay.dwrc.recipeguiprovider.RecipeGuiProviderWorkbench;
import fr.redrelay.dwrc.registry.recipegui.RecipeGuiRegistry;
import fr.redrelay.dwrc.registry.recipegui.gui.IRecipeGui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	private Config config;
	
	private final RecipeGuiRegistry recipeGuiRegistry = new RecipeGuiRegistry();

	private IRecipeGui handler;

	@Override
	public void preInit(FMLPreInitializationEvent evt) {
		super.preInit(evt);
		config = new Config(evt.getSuggestedConfigurationFile());
	}

	@Override
	public void init(FMLInitializationEvent evt) {
		super.init(evt);
		this.registerRecipeGuis();
	}

	@Override
	public void postInit(FMLPostInitializationEvent evt) {
		super.postInit(evt);
		config.onConfigChanged();
		config.saveConfig();
	}

	private void registerRecipeGuis() {
		recipeGuiRegistry.register(new RecipeGuiProviderWorkbench());
		recipeGuiRegistry.register(new RecipeGuiProviderPlayer());
	}

	@SubscribeEvent
	public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post evt) {
		if(evt.getGui() instanceof GuiContainer) {
			handler = recipeGuiRegistry.findRecipeGui((GuiContainer) evt.getGui(), evt.getButtonList());
		}else {
			handler = null;
		}
	}

	@SubscribeEvent
	public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post evt) {
		if(handler == null) return;
		handler.onActionPerformed(evt.getButton());
	}

	@SubscribeEvent
	public void onDrawScreenPost(GuiScreenEvent.DrawScreenEvent.Post evt) {
		if(handler == null) return;
		handler.drawOverlay(evt.getGui());
	}

	public RecipeGuiRegistry getRecipeGuiRegistry() {
		return recipeGuiRegistry;
	}
	
	public Config getConfig() {
		return config;
	}
	
	public IRecipeGui getHandler() {
		return handler;
	}
}
