package osgi.framework.monitoring.event;


import java.util.EventObject;
import java.util.Observable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import osgi.framework.monitoring.event.filter.BundleFilterSet;
import osgi.framework.monitoring.event.filter.IdFilterSet;
import osgi.framework.monitoring.event.filter.NameFilterSet;
import osgi.framework.monitoring.event.filter.TypeFilterSet;



/**
 * Abstract class that can be extended to create event based monitor classes.
 * It supports type filter set and bundle filter set
 * @author Anibal
 */
public abstract class EventMonitor extends Observable
{
	//OSGi BundleContext
	BundleContext bundleContext = null;
		
	TypeFilterSet typeFilterSet = null;
	
	BundleFilterSet bundleFilterSet = null;
	
	/**
	 * Constructor
	 * @param bundleContext OSGi bundle context
	 */
	public EventMonitor(BundleContext bundleContext)
	{
		this.bundleContext = bundleContext;
	}

	/**
	 * Set a new TypeFilterSet that will control the event flow depending on
	 * the event type
	 * @param fs
	 */
	public void setTypeFilterSet(TypeFilterSet fs)
	{		
		typeFilterSet = fs;
	}

	/**
	 * Get TypeFilterSet
	 * @return null if not previously set
	 */
	public TypeFilterSet getFilterSet()
	{		
		return typeFilterSet;
	}
	
	/**
	 * Set a new BundleFilterSet that will control the event flow depending on
	 * the bundle that raises the event
	 * @param fs
	 */
	public void setBundleFilterSet(BundleFilterSet fs)
	{		
		bundleFilterSet = fs;
	}

	/**
	 * Get BundleFilterSet
	 * @return null if not previously set
	 */
	public BundleFilterSet getBundleFilterSet()
	{		
		return bundleFilterSet;
	}
	
	/**
	 * @return OSGi BundleContext
	 */
	public BundleContext getBundleContext()
	{
		return bundleContext;
	}
	
	/**
	 * Notify the observers, invoked by the child monitor class itself
	 * @param eo the event generated
	 */
	public void notify(EventObject eo)
	{
		setChanged();
		notifyObservers(eo);
	}
	
	/**
	 * Notify the observers after checking filters, invoked by the monitors
	 * that support them
	 * @param eo
	 * @param type type code of event
	 * @param bundle bundle that raised the event
	 */
	public void checkUpdate(EventObject eo, int type, Bundle bundle)
	{		
		Boolean notify = true;
		
		// Check if the event meets the TypeFilterSet specifications
		if(typeFilterSet != null)
		{
			notify = ((TypeFilterSet)typeFilterSet).isOpen(type);
		}
		
		// Check if the bundle meets the BundleFilterSet specifications
		if((notify != false) && (bundleFilterSet != null))
		{
			String filterClassName = bundleFilterSet.getClass().getName();
			
			// Check by name
			if(filterClassName.compareTo(NameFilterSet.class.getName()) == 0)
			{
				notify = ((NameFilterSet)bundleFilterSet).isOpen(bundle.getSymbolicName());
			}
			// Check by id
			else if(filterClassName.compareTo(IdFilterSet.class.getName()) == 0)
			{		
				notify = ((IdFilterSet)bundleFilterSet).isOpen(bundle.getBundleId());
			}
		}
	
		if(notify)
		{
			notify(eo);
		}
		
	}
}
