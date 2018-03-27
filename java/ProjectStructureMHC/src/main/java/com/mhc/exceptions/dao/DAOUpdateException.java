package com.mhc.exceptions.dao;

public class DAOUpdateException extends RuntimeException
{
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
    public DAOUpdateException(Exception e)
    {
        super(e);
    }

    public DAOUpdateException(String msg)
    {
        super(msg);
    }
}
