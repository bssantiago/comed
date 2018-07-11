package com.mhc.exceptions;

public class EncryptionException extends ServerException{
	private static final long serialVersionUID = -4283121244903200097L;
	private static final String DESCRIPTION = "An Exception occurred while encrypting";
	
	public EncryptionException() {
		super(DESCRIPTION);
	}
	
	public EncryptionException(String message){
		super(message);
	}
	
	public EncryptionException(Exception e){
		super(e.getMessage());
	}

	@Override
	public int getStatus() {
		return 0;
	}

	@Override
	public int getErrorId() {
		return 0;
	}

	@Override
	public String getErrorDescription() {
		return "An Exception occurred while encrypting";
	}
}
