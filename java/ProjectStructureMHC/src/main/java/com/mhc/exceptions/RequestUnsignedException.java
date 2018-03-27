package com.mhc.exceptions;

/**
 * Exception indicating a request must be signed (i.e. contain an <tt>api_sig</tt> parameter) but one
 * was not provided.
 */
public class RequestUnsignedException extends UnauthorizedException {
	private static final long serialVersionUID = -3168174728658294956L;
	private static final String DESCRIPTION = "Request must be signed";
	private static final int ERRORID = 2003;
	
	public RequestUnsignedException() {
		super(DESCRIPTION);
	}
	
	@Override
	public String getErrorDescription() {
		return DESCRIPTION;
	}

	@Override
	public int getErrorId() {
		return ERRORID;
	}
}
