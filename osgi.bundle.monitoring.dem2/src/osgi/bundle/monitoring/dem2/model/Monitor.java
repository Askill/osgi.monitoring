package osgi.bundle.monitoring.dem2.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.osgi.framework.BundleContext;
import osgi.framework.monitoring.event.EventMonitor;
import osgi.framework.monitoring.event.ServiceRegistryMonitor;
import osgi.framework.monitoring.event.StateMonitor;
import osgi.framework.monitoring.event.filter.IdFilterSet;
import osgi.framework.monitoring.event.filter.TypeFilterSet;

/**
 * Manages the different types of monitors: state monitor and service monitor
 * @author Anibal
 */
public class Monitor extends Observable implements Observer
{
	// Contains all monitor objects
	private ArrayList<EventMonitor> bundleMonitors;
	
	// Contains all type filer for the various monitors
	private ArrayList<TypeFilterSet> typeFilters;
	
	private IdFilterSet idFilter;
	
	/**
	 * Constructor. Initialize the different monitors
	 * @param bundleContext
	 */
	public Monitor(BundleContext bundleContext)
	{	
		bundleMonitors = new ArrayList<EventMonitor>();
		
		typeFilters = new ArrayList<TypeFilterSet>();
		
		idFilter = new IdFilterSet();
	
		// Positions in array 0: StateMonitor, 1: ServiceRegistryMonitor
		bundleMonitors.add(new StateMonitor(bundleContext));
		bundleMonitors.add(new ServiceRegistryMonitor(bundleContext));
		
		// Set the type filter sets to each monitor object
		for(int i = 0; i < bundleMonitors.size() - 1; i++)
		{
			typeFilters.add(new TypeFilterSet());
			bundleMonitors.get(i).setTypeFilterSet(typeFilters.get(i));
			bundleMonitors.get(i).setBundleFilterSet(idFilter);
		}
	
		// StateMonitor activated by default
		setMonitorOn(0);
	}
	
	/**
	 * Activate monitor object
	 * @param index 0: RepositoryMonitor, 1: StateMonitor, 2: ManifestMonitor, 3: DataFieldMonitor
	 */
	public void setMonitorOn(int index)
	{
		bundleMonitors.get(index).addObserver(this);
	}
	
	/**
	 * Deactivate monitor object
	 * @param index 0: RepositoryMonitor, 1: StateMonitor, 2: ManifestMonitor, 3: DataFieldMonitor
	 */
	public void setMonitorOff(int index)
	{
		bundleMonitors.get(index).deleteObservers();
	}
	
	/**
	 * Open or close type in one TypeFilterSet
	 * @param typeFilterSet
	 * @param type type of event
	 * @param open true: opened, false: closed
	 */
	private void setType(TypeFilterSet typeFilterSet, int type, Boolean open) 
	{
		typeFilterSet.addEntry(type, open);
	}
	
	/**
	 * Open type in one TypeFilterSet
	 * @param index 0: RepositoryMonitor, 1: StateMonitor, 2: ManifestMonitor, 3: DataFieldMonitor
	 * @param type type of event
	 */
	public void setTypeOpened(int index, int type)
	{
		setType(typeFilters.get(index), type, true);
	}
	
	/**
	 * Close type in one TypeFilterSet
	 * @param index 0: RepositoryMonitor, 1: StateMonitor, 2: ManifestMonitor, 3: DataFieldMonitor
	 * @param type type of event
	 */
	public void setTypeClosed(int index, int type)
	{
		setType(typeFilters.get(index), type, false);
	}
	
	/**
	 * Open or close bundle in one IdFilterSet
	 * @param idBundle
	 * @param mode true: opened, false: closed
	 */
	private void setId(long idBundle, boolean mode) 
	{
		idFilter.addEntry(idBundle, mode);
	}
	
	/**
	 * Open bundle in one IdFilterSet
	 * @param idBundle
	 */
	public void setIdOpened(long idBundle)
	{
		setId(idBundle, true);
	}
	
	/**
	 * Close bundle in one IdFilterSet
	 * @param idBundle
	 */
	public void setIdClosed(long idBundle)
	{
		setId(idBundle, false);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object obj) 
	{
		setChanged();
	    notifyObservers(obj);
	}
	
}