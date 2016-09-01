package fr.redrelay.dwrc;

import fr.redrelay.dwrc.packet.CraftingResultPacket;
import fr.redrelay.dwrc.proxy.ClientProxy;
import fr.redrelay.dwrc.proxy.CommonProxy;
import fr.redrelay.dwrc.registry.recipecontainer.RecipeContainerRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = DWRC.MODID, version = DWRC.VERSION, guiFactory="fr.redrelay.dwrc.ModGuiFactory", acceptableRemoteVersions = "*")
public class DWRC
{
    
    public static final String VERSION = "1.0";
    public static final String MODID = "dealwithrecipescollisions";
    public static final String MODNAME = "Deal with Recipes Collisions "+VERSION;
    
    @SidedProxy(clientSide = "fr.redrelay.dwrc.proxy.ClientProxy", serverSide = "fr.redrelay.dwrc.proxy.CommonProxy")
	private static CommonProxy proxy;
    
    @Instance(DWRC.MODID)
	public static DWRC instance;
    
    private static SimpleNetworkWrapper channel;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
    	channel = NetworkRegistry.INSTANCE.newSimpleChannel("DWRC");
    	channel.registerMessage(new CraftingResultPacket.CraftingResultPacketHandler(), CraftingResultPacket.class, 0, Side.SERVER);
    	proxy.preInit(evt);
    }
    
    @EventHandler
    public void onInit(FMLInitializationEvent evt) {
    	proxy.init(evt);
    }
    
    @EventHandler
    public void onPostInit(FMLPostInitializationEvent evt) {
    	proxy.postInit(evt);
    }
    
    public static SimpleNetworkWrapper getChannel() {
		return channel;
	}
    
    public static RecipeContainerRegistry getRecipeContainerRegistry() {
    	return proxy.getRecipeContainerRegistry();
    }
    
    @SideOnly(Side.CLIENT)
    public static ClientProxy getClientProxy() {
    	return (ClientProxy) proxy;
    }
    
    
    
}
