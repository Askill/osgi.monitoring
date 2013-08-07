package osgi.framework.monitoring.event;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import osgi.framework.monitoring.event.filter.TypeFilterSet;


/**
 * Will will monitor the OSGi service registry and will raise an event if any
 * service is registered, modified or unregistered
 * @author Anibal
 */
public class ServiceRegistryMonitor 
extends EventMonitor 
implements ServiceListener
{	
	/**
	 * Constructor. Initialize the monitoring process
	 * @param bundleContext OSGi bundle context
	 */
	public ServiceRegistryMonitor(BundleContext bundleContext) 
	{
		super(bundleContext);
		
		bundleContext.addServiceListener(this);
	}
	
	/**
	 * Initialize the TypeFilterSet values with ServiceRegistryMonitor
	 * @param tf
	 */
	public void typeFilterSetDefaultConfiguration(TypeFilterSet tf)
	{
		if(tf.size() > 0)
		{
			tf.clear();
		}
		
		tf.addEntry(ServiceEvent.MODIFIED, false);
		tf.addEntry(ServiceEvent.MODIFIED_ENDMATCH, false);
		tf.addEntry(ServiceEvent.REGISTERED, false);
		tf.addEntry(ServiceEvent.UNREGISTERING, false);
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.ServiceListener#serviceChanged
	 * (org.osgi.framework.ServiceEvent)
	 */
	public void serviceChanged(ServiceEvent se) 
	{
		ServiceReference<?> reference = se.getServiceReference();
		
		ServiceEventMod sem = new ServiceEventMod(this,
				se.getType(),
				reference,
				getBundleContext().getService(reference));
		
		super.checkUpdate(sem, 
				se.getType(), 
				se.getServiceReference().getBundle());
	}
	
}
