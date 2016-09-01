package fr.redrelay.dwrc.registry.recipegui.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IRecipeModel {
	public void update();
	public boolean isOverlayEnabled();
	public int getCursor();
	public int getNbRecipes();
	public void setCursor(int cur);
	public boolean isDirty();
	public void addListener(IRecipeModelListener listener);
	public void removeListener(IRecipeModelListener listener);
}
