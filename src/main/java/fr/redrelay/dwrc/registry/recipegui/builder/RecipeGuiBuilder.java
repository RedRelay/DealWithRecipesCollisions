package fr.redrelay.dwrc.registry.recipegui.builder;

import java.util.LinkedList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

public class RecipeGuiBuilder implements IRecipeGuiBuilder {

	protected static final String Y_LABEL = "yLabel";
	protected static final String X_LABEL = "xLabel";
	protected static final String Y_NEXT = "yNext";
	protected static final String X_NEXT = "xNext";
	protected static final String Y_PREV = "yPrev";
	protected static final String X_PREV = "xPrev";
	
	private static final List<String> propsOrder = new LinkedList<String>();
	
	static {
		propsOrder.add(X_PREV);
		propsOrder.add(Y_PREV);
		propsOrder.add(X_NEXT);
		propsOrder.add(Y_NEXT);
		propsOrder.add(X_LABEL);
		propsOrder.add(Y_LABEL);
	}
	
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
	
	public void sortProps(Configuration config) {
		config.setCategoryPropertyOrder(name, propsOrder);
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
