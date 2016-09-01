package fr.redrelay.dwrc.exceptions;

public class DWRCException extends RuntimeException {

	public DWRCException() {
		super();
	}

	public DWRCException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DWRCException(String arg0) {
		super(arg0);
	}

	public DWRCException(Throwable arg0) {
		super(arg0);
	}

}
