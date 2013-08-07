package osgi.framework.monitoring.event;

import java.util.ArrayList;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.hooks.weaving.WeavingHook;

import osgi.framework.monitoring.event.impl.ClassWeaver;
import osgi.framework.monitoring.event.impl.MonitoredField;


/**
 * Will monitor different class attributes within a bundle and will raise an
 * event if any previously specified primitive data field is modified
 * @author Anibal
 */
public class DataFieldMonitor extends EventMonitor
{
	
	// Contains OSGi ServiceRegistration for DataFieldMonitor necessary 
	// services
	private ArrayList<ServiceRegistration<?>> serviceRegistration;
	
	// Contains information about monitored field and its classes
	private ArrayList<MonitoredField> fields;

	/**
	 * Constructor
	 * @param bundleContext - OSGi BundleContext object
	 */
	public DataFieldMonitor(BundleContext bundleContext) 
	{	
		super(bundleContext);

		serviceRegistration = new ArrayList<ServiceRegistration<?>>();
		
		fields = new ArrayList<MonitoredField>();
	}

	/**
	 * It will specify a new class and field to be monitored
	 * @param className
	 * @param fieldName
	 * @return true if the specified data field exists
	 */
	public boolean addDataField(String className, String fieldName)
	{
		if(getIndex(className, fieldName) == -1)
		{
			fields.add(new MonitoredField(className, fieldName));
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param className
	 * @param fieldName
	 * @return position of field in the array
	 */
	private int getIndex(String className,  String fieldName)
	{
		for(int i = 0; i < fields.size(); i++)
		{
			if(className.compareTo(fields.get(i).getClassName()) == 0)
			{
				if(fieldName.compareTo(fields.get(i).getFieldName()) == 0)
				{
					return i;
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * Registers the necessary services for data field monitor to work.
	 * Initialize monitoring process
	 */
	public void registerWeavingService()
	{
		// DataFieldUpdate will provide an interface that can be used to raise
		// a DataFieldMonitor event
		serviceRegistration.add(super.getBundleContext().registerService(
				DataFieldUpdate.class.getName(), 
				new DataFieldUpdate(this), null));
		// WeavingHook will allow class modifications to insert invocations to
		// DataFieldUpdate methods
		serviceRegistration.add(super.getBundleContext().registerService(
				WeavingHook.class.getName(), 
				new ClassWeaver(fields),
				null));
	}
	
	/**
	 * Unregister the necessary services for data field monitor to work
	 */
	public void unregisterWeavingService()
	{
		for(ServiceRegistration<?> sr: serviceRegistration)
		{
			if(sr != null)
			{
				sr.unregister();
			}
		}
		
		serviceRegistration.clear();
	}
	
	/**
	 * Used to create and raise an event
	 * @param field
	 * @param className
	 * @param fieldName
	 */
	public void update(Object field, String className, String fieldName)
	{
		super.notify(new DataFieldEvent(this, field, className, fieldName));
	}

}
