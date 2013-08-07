package osgi.framework.monitoring.event;

import java.util.EventObject;

import org.osgi.framework.Bundle;

/**
 * Contains the relevant data of a RepositoryMonitor event
 * @author Anibal
 */
public class RepositoryEvent extends EventObject
{
	//CONSTANTS
	public static final int ENTRY_ERROR = 0;
	
	public static final int ENTRY_CREATE = 1;
	
	public static final int ENTRY_MODIFY = 2;
	
	public static final int ENTRY_DELETE = 3;

	private static final long serialVersionUID = 1L;

	// Bundle that raised the event
	private Bundle bundle;
	
	// Type of event ENTRY_ERROR, ENTRY_CREATE, ENTRY_MODIFY or ENTRY_DELETE
	private String name;
	
	/**
	 * Constructor
	 * @param obj observable that raised the event
	 * @param bundle bundle that raised the event
	 * @param name 0: ENTRY_ERROR, 1: ENTRY_CREATE, 2: ENTRY_MODIFY or 
	 * 3: ENTRY_DELETE
	 */
	public RepositoryEvent(Object obj, Bundle bundle, String name) 
	{
		super(obj);

		this.bundle = bundle;
		this.name = name;
	}
	
	/**
	 * @return bundle that raised the event
	 */
	public Bundle getBundle()
	{
		return bundle;
	}
	
	/**
	 * @return ENTRY_ERROR, ENTRY_CREATE, ENTRY_MODIFY or ENTRY_DELETE
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return type 0: ENTRY_ERROR, 1: ENTRY_CREATE, 2: ENTRY_MODIFY or 
	 * 3: ENTRY_DELETE
	 */
	public int getType()
	{
		switch(name)
		{
			case "ENTRY_CREATE":
				return ENTRY_CREATE;
			case "ENTRY_MODIFY":
	        	return ENTRY_MODIFY;
			case "ENTRY_DELETE":
	        	return ENTRY_DELETE;
		}
		
		return ENTRY_ERROR;
	}

}
