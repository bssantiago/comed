package com.mhc.exceptions.dao;



/**
 * DAOSystemException is thrown by a DAO component when there is 
 * some irrecoverable error (like SQLException)
 */
public class DAOSystemException extends RuntimeException
{
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
    public DAOSystemException(Exception e)
    {
        super(e);
    }

    public DAOSystemException(String msg)
    {
        super(msg);
    }
    
    public DAOSystemException(String msg, Exception e)
    {
        super(msg, e);
    }
}
