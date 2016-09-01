package fr.redrelay.dwrc;

import java.util.ArrayList;
import java.util.List;

import fr.redrelay.dwrc.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

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
    public void onInit(FMLInitializationEvent evt) {
    	setupTest();
    	channel = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    	proxy.registerChannels();
    	proxy.registerHandlers();
    	proxy.registerRecipeGuiModelProviders();
    }
    
    private void setupTest() {
    	final ItemStack iron = new ItemStack(Items.IRON_INGOT), stick = new ItemStack(Items.STICK);
    	CraftingManager.getInstance().addRecipe(new ShapedRecipes(3, 3, new ItemStack[]{iron, iron, iron, null, stick, null, null, stick, null}, iron));
    	
    	//Check if recipe collision
    	InventoryCrafting inv = new InventoryCrafting(new Container() {
			
			@Override
			public boolean canInteractWith(EntityPlayer playerIn) {
				return true;
			}
		}, 3, 3);
    	
    	inv.setInventorySlotContents(0, iron);
    	inv.setInventorySlotContents(1, iron);
    	inv.setInventorySlotContents(2, iron);
    	inv.setInventorySlotContents(4, stick);
    	inv.setInventorySlotContents(7, stick);
    	
    	List<IRecipe> matchedRecipes = new ArrayList<IRecipe>();
    	for(IRecipe r : CraftingManager.getInstance().getRecipeList()) {
    		if(r.matches(inv, null)) {
    			matchedRecipes.add(r);
    		}
    	}
    	
    	System.out.println("MATCHED RECIPES : "+matchedRecipes.size());
    	
    }
    
    public static SimpleNetworkWrapper getChannel() {
		return channel;
	}
    
    
    
}
