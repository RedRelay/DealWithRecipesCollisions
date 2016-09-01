package fr.redrelay.dwrc.registry.recipegui.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IRecipeModelListener {
	public void onUpdate();
	public void onCursorChange();
}
