package fr.redrelay.dwrc.gui;

import fr.redrelay.dwrc.model.IRecipeModel;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public interface IRecipeGui {
	public IRecipeModel getModel();
	public void onActionPerformed(GuiButton button);
	public void drawOverlay(GuiScreen gui);
}
