package osgi.framework.monitoring.event;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;

import osgi.framework.monitoring.event.filter.TypeFilterSet;

/**
 * Will monitor the installed bundles manifest and will raise an event if any 
 * entry of the manifest of any updated bundle is created, modified or deleted
 * @author Anibal
 */
public class ManifestMonitor extends EventMonitor implements Observer
{
	// Stores a map with bundle id and its correspondent manifest copies
	Map<Long, Dictionary<String, String>> manifestMap;

	/**
	 * Constructor. Initialize monitoring process
	 * @param bundleContext OSGi BundleContext
	 */
	public ManifestMonitor(BundleContext bundleContext) 
	{
		super(bundleContext);
		
		initManifestMap();
		initObserver();
	}

	/**
	 * This method adds the current bundles and manifest copies to the map
	 */
	private void initManifestMap() 
	{
		manifestMap = new HashMap<Long, Dictionary<String, String>>();
		
		Bundle[] bundles = super.getBundleContext().getBundles();
		
		for(int i = 0; i < bundles.length; i++)
		{
			addManifestToMap(bundles[i]);
		}
	}

	/**
	 * This method adds a new bundle and its manifest copy to the map
	 * @param bundle
	 */
	private void addManifestToMap(Bundle bundle) 
	{
		manifestMap.put(bundle.getBundleId(), 
				cloneDictionary(bundle.getHeaders()));
	}
	
	/**
	 * This method get a manifest and make a copy of it
	 * @param headers manifest
	 * @return copy of the manifest
	 */
	private Dictionary<String, String> cloneDictionary(
			Dictionary<String, String> headers) 
	{
		Enumeration<String> keys = headers.keys();
		Dictionary<String, String> copy = new Hashtable<String, String>();
		String header;
		
		while(keys.hasMoreElements())
		{
			header = keys.nextElement();
			copy.put(header, headers.get(header));
		}
		
		return copy;
	}

	/**
	 * This method removes a bundle and manifest copy from the map
	 * @param bundle
	 */
	private void removeManifestFromMap(Bundle bundle) 
	{
		manifestMap.remove(bundle.getBundleId());
	}

	/**
	 * This method initialize the bundle state monitor
	 */
	private void initObserver() 
	{
		StateMonitor bsm = new StateMonitor(super.getBundleContext());
		
		bsm.addObserver(this);
	}
	
	/**
	 * Initialize the TypeFilterSet values with ManifestMonitor ones
	 * @param tf
	 */
	public void typeFilterSetDefaultConfiguration(TypeFilterSet tf)
	{
		if(tf.size() > 0)
		{
			tf.clear();
		}
		
		tf.addEntry(ManifestEvent.CREATED, false);
		tf.addEntry(ManifestEvent.MODIFIED, false);
		tf.addEntry(ManifestEvent.DELETED, false);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object obj) 
	{
		int type = ((BundleEvent)obj).getType();
		Bundle bundle = ((BundleEvent)obj).getBundle();
		
		// ManifestMonitor uses StateMonitor, and will make different changes
		// in its map if there is a change in the state of any bundle. This is
		// made to keep updated information about installed bundles and its
		// manifest files
		switch(type)
		{
			case BundleEvent.INSTALLED:
				addManifestToMap(bundle);
				break;
			case BundleEvent.UPDATED:
				checkManifestChanges(bundle);
				break;
			case BundleEvent.UNINSTALLED:
				removeManifestFromMap(bundle);
				break;
		}
	}
	
	/**
	 * This method raises a ManifestMonitor event
	 * @param bundle bundle taht raised the event
	 * @param header header that was created, modified or deleted
	 * @param type 1: CREATED, 2: MODIFIED or 3: DELETED
	 */
	private void raiseEvent(Bundle bundle, String header, int type)
	{
		ManifestEvent me = new ManifestEvent(this, bundle, header, type);
		
		super.checkUpdate(me, type, bundle);
	}
	
	/**
	 * This method compares a current bundle manifest with its old version to
	 * seek for created and modified headers
	 * @param bundle
	 * @param oldManifest
	 * @return
	 */
	private int compareHeaders(Bundle bundle,
			Dictionary<String, String> oldManifest) 
	{
		int created = 0;
		Dictionary<String, String> newManifest = bundle.getHeaders();
		String header;
		Enumeration<String> keys = newManifest.keys();

		while(keys.hasMoreElements())
		{
			header = keys.nextElement();
			if(oldManifest.get(header) != null)
			{
				if(newManifest.get(header).compareTo(oldManifest.get(header)) != 0)
				{
					raiseEvent(bundle, header, ManifestEvent.MODIFIED);
				}
			}
			else
			{
				raiseEvent(bundle, header, ManifestEvent.CREATED);
				created++;
			}
		}
		
		return newManifest.size() - created;
	}
	
	/**
	 * This method compares a current bundle manifest with its old version to
	 * seek for deleted headers
	 * @param bundle
	 * @param oldManifest
	 */
	private void checkDeleted(Bundle bundle,
			Dictionary<String, String> oldManifest) 
	{
		Dictionary<String, String> newManifest = bundle.getHeaders();
		String header;
		Enumeration<String> keys = oldManifest.keys();
		
		while(keys.hasMoreElements())
		{
			header = keys.nextElement();
			if(newManifest.get(header) == null)
			{
				raiseEvent(bundle, header, ManifestEvent.DELETED);
			}
		}
	}

	/**
	 * This method checks updates and changes a manifest to its new version 
	 * if it is necessary
	 * @param bundle
	 */
	private void checkManifestChanges(Bundle bundle) 
	{
		long id = bundle.getBundleId();
		
		// Old manifest
		Dictionary<String, String> oldManifest = manifestMap.get(id);
		
		// Check created and modified headers
		int size = compareHeaders(bundle, oldManifest);
		if(size < oldManifest.size())
		{
			// Check deleted headers
			checkDeleted(bundle, oldManifest);
		}
		
		// Replace manifest
		manifestMap.put(id, bundle.getHeaders());
	}

}
