package fr.redrelay.dwrc.model;

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
