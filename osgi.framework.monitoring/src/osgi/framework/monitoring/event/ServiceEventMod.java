package osgi.framework.monitoring.event;

import java.util.EventObject;

import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;

/**
 * Contains the relevant data of a ServiceRegistryMod event. OSGi
 * ServiceEvent modified to store the Service object
 * @author Anibal
 */
public class ServiceEventMod extends EventObject
{
	// CONSTANTS
	public static final int MODIFIED = ServiceEvent.MODIFIED;
	
	public static final int MODIFIED_ENDMATCH = ServiceEvent.MODIFIED_ENDMATCH;
	
	public static final int REGISTERED = ServiceEvent.REGISTERED;
	
	public static final int UNREGISTERED = ServiceEvent.UNREGISTERING;

	private static final long serialVersionUID = 1L;
	
	// Type of event 2: MODIFIED, 8:MODIFIED_ENDMATCH, 1: REGISTERED,
	// 4: UNREGISTERED
	private int type;
	
	// Service reference that raised the event
	private ServiceReference<?> reference;
	
	// Service object that raised the event
	private Object service;
	
	/**
	 * Constructor
	 * @param source observable that raised the event
	 * @param type 2: MODIFIED, 8:MODIFIED_ENDMATCH, 1: REGISTERED, 
	 * 4: UNREGISTERED
	 * @param reference service reference that raised the event
	 * @param service service object that raised the event
	 */
	public ServiceEventMod(Object source, 
			int type, 
			ServiceReference<?> reference, 
			Object service) 
	{
		super(source);

		this.type = type;
		this.reference = reference;
		this.service = service;
	}
	
	/**
	 * @return type 2: MODIFIED, 8:MODIFIED_ENDMATCH, 1: REGISTERED, 
	 * 4: UNREGISTERED
	 */
	public int getType()
	{
		return type;
	}
	
	/**
	 * @return service reference that raised the event
	 */
	public ServiceReference<?> getServiceReference()
	{
		return reference;
	}
	
	/**
	 * @return service object that raised the event
	 */
	public Object getService()
	{
		return service;
	}

}
