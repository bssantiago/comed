package com.mhc.exceptions;

import org.springframework.context.MessageSource;

import com.mhc.services.ApplicationContextProvider;
import com.mhc.util.Constants;

/**
 * Exception indicating a request must be signed (i.e. contain an <tt>api_sig</tt> parameter) but one
 * was not provided.
 */
public class ParseCSVException extends Exception {
	private static final long serialVersionUID = -3168174728658294956L;
	private static MessageSource messageSource = (MessageSource) ApplicationContextProvider.getApplicationContext().getBean("messageSource");
	
	public ParseCSVException() {
		super(messageSource.getMessage(Constants.ERROR_CSV_INVALID_FORMAT, null, null));
	}
	
}
