package fr.redrelay.dwrc.registry.recipegui.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IRecipeGui {
	public void onActionPerformed(GuiButton button);
	public void drawOverlay(GuiScreen gui);
	public void setCursor(int cur);
	public void setRecipeNb(int recipeNb);
	public boolean isOverlayEnabled();
}
