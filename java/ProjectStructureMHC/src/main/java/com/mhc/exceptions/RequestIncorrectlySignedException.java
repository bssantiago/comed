package com.mhc.exceptions;

public class RequestIncorrectlySignedException extends UnauthorizedException {
	private static final long serialVersionUID = -6980854909935859386L;
	private static final String DESCRIPTION = "Request is signed incorrectly";
	private static final int ERRORID = 2005;
	
	public RequestIncorrectlySignedException(String expectedSignature) {
		super(DESCRIPTION);
		//DESCRIPTION = " , expected : " + expectedSignature;
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
