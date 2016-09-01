package fr.redrelay.dwrc.proxy;

import fr.redrelay.dwrc.recipeguiprovider.RecipeGuiProviderPlayer;
import fr.redrelay.dwrc.recipeguiprovider.RecipeGuiProviderWorkbench;
import fr.redrelay.dwrc.registry.recipegui.RecipeGuiRegistry;
import fr.redrelay.dwrc.registry.recipegui.gui.IRecipeGui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	private final RecipeGuiRegistry recipeGuiRegistry = new RecipeGuiRegistry();
	private Configuration config;

	private IRecipeGui handler;

	@Override
	public void preInit(FMLPreInitializationEvent evt) {
		super.preInit(evt);
		config = new Configuration(evt.getSuggestedConfigurationFile());
		config.load();
	}

	@Override
	public void init(FMLInitializationEvent evt) {
		super.init(evt);
		this.registerRecipeGuis();
	}

	@Override
	public void postInit(FMLPostInitializationEvent evt) {
		super.postInit(evt);
		recipeGuiRegistry.onConfigChanged(config);
		saveConfig();
	}

	private void registerRecipeGuis() {
		recipeGuiRegistry.register(new RecipeGuiProviderWorkbench());
		recipeGuiRegistry.register(new RecipeGuiProviderPlayer());
	}

	@Override
	public void registerHandlers() {
		super.registerHandlers();
		MinecraftForge.EVENT_BUS.register(this);
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

	public Configuration getConfig() {
		return config;
	}

	public void onConfigChanged() {
		recipeGuiRegistry.onConfigChanged(config);
	}

	public void saveConfig() {
		if(config.hasChanged()) {
			recipeGuiRegistry.sortProps(config);
			config.save();
		}
	}
}
