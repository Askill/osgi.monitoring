package osgi.framework.monitoring.event;

import java.util.EventObject;

import org.osgi.framework.Bundle;

/**
 * Contains the relevant data of a ManifestMonitor event
 * @author Anibal
 */
public class ManifestEvent extends EventObject
{
	// CONSTANTS
	public static final int CREATED = 1;
	
	public static final int MODIFIED = 2;
	
	public static final int DELETED = 3;

	private static final long serialVersionUID = 1L;
	
	// Bundle that raised the event
	private Bundle bundle;
	
	// Header that was created, modified or deleted
	private String header;
	
	// Type of event 1: CREATED, 2: MODIFIED or 3: DELETED
	private int type;
	
	/**
	 * Constructor
	 * @param source observable that raised the event
	 * @param bundle bundle that raised the event
	 * @param header header that was created, modified or deleted
	 * @param type 1: CREATED, 2: MODIFIED or 3: DELETED
	 */
	public ManifestEvent(Object source, 
			Bundle bundle,
			String header, 
			int type) 
	{
		super(source);
		
		this.bundle = bundle;
		this.header = header;
		this.type = type;
	}
	
	/**
	 * @return bundle that raised the event
	 */
	public Bundle getBundle()
	{
		return bundle;
	}
	
	/**
	 * @return header that was created, modified or deleted
	 */
	public String getHeader()
	{
		return header;
	}
	
	/**
	 * @return 1: CREATED, 2: MODIFIED or 3: DELETED
	 */
	public int getType()
	{
		return type;
	}
	
}
