package com.mhc.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Abstract exception indicating the request failed because the operation is not authorized for
 * the requester.
 */
public abstract class UnauthorizedException extends ServerException {
	private static final long serialVersionUID = 6850906146621716586L;
	private static final int STATUS = HttpServletResponse.SC_UNAUTHORIZED;
	
	public UnauthorizedException(String message) {
		super(message);
	}

	@Override
	public int getStatus() {
		return STATUS;
	}
}
