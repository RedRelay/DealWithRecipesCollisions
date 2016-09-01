package fr.redrelay.dwrc.registry.recipegui.builder;

import net.minecraftforge.common.config.Configuration;

public class RecipeGuiBuilder implements IRecipeGuiBuilder {

	private static final String Y_LABEL = "yLabel";
	private static final String X_LABEL = "xLabel";
	private static final String Y_NEXT = "yNext";
	private static final String X_NEXT = "xNext";
	private static final String Y_PREV = "yPrev";
	private static final String X_PREV = "xPrev";
	
	private final String name;
	private int xPrev, yPrev, xNext, yNext, xLabel, yLabel;
	
	public RecipeGuiBuilder(String name) {
		this(name,0,0,20,0,0,20);
	}
	
	public RecipeGuiBuilder(String name, int xPrev, int yPrev, int xNext, int yNext, int xLabel, int yLabel) {
		this.name = name;
		this.xPrev = xPrev;
		this.yPrev = yPrev;
		this.xNext = xNext;
		this.yNext = yNext;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}
	
	public void onConfigChanged(Configuration config) {
		this.xPrev = config.get(name, X_PREV, xPrev).getInt();
		this.yPrev = config.get(name, Y_PREV, yPrev).getInt();
		this.xNext = config.get(name, X_NEXT, xNext).getInt();
		this.yNext = config.get(name, Y_NEXT, yNext).getInt();
		this.xLabel = config.get(name, X_LABEL, xLabel).getInt();
		this.yLabel = config.get(name, Y_LABEL, yLabel).getInt();
	}

	@Override
	public int getXPrev() {
		return xPrev;
	}

	@Override
	public int getYPrev() {
		return yPrev;
	}

	@Override
	public int getXNext() {
		return xNext;
	}

	@Override
	public int getYNext() {
		return yNext;
	}

	@Override
	public int getXLabel() {
		return xLabel;
	}

	@Override
	public int getYLabel() {
		return yLabel;
	}

}
