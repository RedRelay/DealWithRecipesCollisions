package fr.redrelay.dwrc.exceptions;

import net.minecraft.inventory.Container;

public class NoRecipeFinderFoundException extends DWRCException {

	public final Container container;
	
	public NoRecipeFinderFoundException(Container container) {
		super("Unable to find recipe finder for container "+container);
		this.container = container;
	}

}
