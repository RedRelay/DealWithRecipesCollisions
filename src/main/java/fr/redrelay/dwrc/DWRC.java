package fr.redrelay.dwrc;

import fr.redrelay.dwrc.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;

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
}
