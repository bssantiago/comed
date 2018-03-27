package com.mhc.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract exception indicating an internal server error during request processing.
 */
public abstract class ServerException extends RuntimeException {
	private static final long serialVersionUID = -5267528234590411706L;
	
	public ServerException(String message) {
		super(message);
	}
	
	public abstract int getStatus();
	public abstract int getErrorId();
	public abstract String getErrorDescription();

	public Map<String,Object> getKeyValues() {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("ID", getErrorId());
		result.put("Description", getErrorDescription());
		return result;
	}

}
