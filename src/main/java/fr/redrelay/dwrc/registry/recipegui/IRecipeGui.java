package fr.redrelay.dwrc.registry.recipegui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IRecipeGui {
	public IRecipeModel getModel();
	public void onActionPerformed(GuiButton button);
	public void drawOverlay(GuiScreen gui);
}
