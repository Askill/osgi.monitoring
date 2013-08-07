package osgi.framework.monitoring.event;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import osgi.framework.monitoring.event.filter.TypeFilterSet;


/**
 * Will raise an event if there is any change in the state of any bundle
 * @author Anibal
 */
public class StateMonitor extends EventMonitor implements BundleListener 
{	

	/**
	 * Constructor. Initialize the monitoring process
	 * @param bundleContext OSGi BundleContext
	 */
	public StateMonitor(BundleContext bundleContext) 
	{
		super(bundleContext);
		
		bundleContext.addBundleListener(this);
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
		
		tf.addEntry(BundleEvent.INSTALLED, false);
		tf.addEntry(BundleEvent.LAZY_ACTIVATION, false);
		tf.addEntry(BundleEvent.RESOLVED, false);
		tf.addEntry(BundleEvent.STARTED, false);
		tf.addEntry(BundleEvent.STARTING, false);
		tf.addEntry(BundleEvent.STOPPED, false);
		tf.addEntry(BundleEvent.STOPPING, false);
		tf.addEntry(BundleEvent.UNINSTALLED, false);
		tf.addEntry(BundleEvent.UNRESOLVED, false);
		tf.addEntry(BundleEvent.UPDATED, false);
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleListener#bundleChanged
	 * (org.osgi.framework.BundleEvent)
	 */
	public void bundleChanged(BundleEvent be) 
	{
		super.checkUpdate(be, be.getType(), be.getBundle());
	}
}