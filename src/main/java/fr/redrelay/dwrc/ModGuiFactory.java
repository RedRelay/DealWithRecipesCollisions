package fr.redrelay.dwrc;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModGuiFactory implements IModGuiFactory{

	@Override
	public void initialize(Minecraft minecraftInstance) {
		
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return DWRCGuiConfig.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}
	
	public static class DWRCGuiConfig extends GuiConfig {
		
		public DWRCGuiConfig(GuiScreen parent) {
			super(parent, getConfigElements(), DWRC.MODID, false, false, DWRC.MODNAME);
		}

		private static List<IConfigElement> getConfigElements() {
			Configuration config = DWRC.getConfig();
			List<IConfigElement> elements = new LinkedList<IConfigElement>();
			for(String category : config.getCategoryNames()) {
				final ConfigElement e = new ConfigElement(config.getCategory(category));
				elements.add(e);
			}
			return elements;
		}
		
		@Override
		public void onGuiClosed() {
			Configuration config = DWRC.getConfig();
			if(config.hasChanged()) {
				DWRC.onConfigChanged();
				config.save();
			}
			super.onGuiClosed();
		}
	}

}
