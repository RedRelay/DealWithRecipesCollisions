package fr.redrelay.dwrc.registry.recipegui;

import java.util.List;

import fr.redrelay.dwrc.registry.recipegui.gui.IRecipeGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IRecipeGuiProvider {
	public boolean accept(GuiContainer gui);
	public IRecipeGui getRecipeGui(GuiContainer gui, List<GuiButton> listButton);
	public void onConfigChanged(Configuration config);
	public void sortProps(Configuration config);
}
