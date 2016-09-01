package fr.redrelay.dwrc;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Config {

	private Configuration config;
	
	
	public Config(File f) {
		config = new Configuration(f);
		config.load();
	}
	
	public void saveConfig() {
		if(config.hasChanged()) {
			DWRC.getClientProxy().getRecipeGuiRegistry().sortProps(config);
			config.save();
		}
	}
	
	protected Configuration getConfiguration() {
		return config;
	}

	public void onConfigChanged() {
		DWRC.getClientProxy().getRecipeGuiRegistry().onConfigChanged(config);
	}
	
}
