package fr.redrelay.dwrc;

import fr.redrelay.dwrc.packet.CraftingResultPacket;
import fr.redrelay.dwrc.proxy.CommonProxy;
import fr.redrelay.dwrc.registry.recipecontainer.RecipeContainerRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = DWRC.MODID, version = DWRC.VERSION)
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
    }
    
    @EventHandler
    public void onInit(FMLInitializationEvent evt) {
    	setupTest();
    	proxy.init(evt);
    }
    
    private void setupTest() {
    	final ItemStack iron = new ItemStack(Items.IRON_INGOT), stick = new ItemStack(Items.STICK);
    	CraftingManager.getInstance().addRecipe(new ShapedRecipes(3, 3, new ItemStack[]{iron, iron, iron, null, stick, null, null, stick, null}, iron));
    	CraftingManager.getInstance().addRecipe(iron, new Object[] {"X", "#", 'X', Items.COAL, '#', Items.STICK});
    }
    
    public static SimpleNetworkWrapper getChannel() {
		return channel;
	}
    
    public static RecipeContainerRegistry getRecipeContainerRegistry() {
    	return proxy.getRecipeContainerRegistry();
    }
    
    
    
}
