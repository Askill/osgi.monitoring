package osgi.framework.monitoring.event;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Observable;
import java.util.Observer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import osgi.framework.monitoring.event.filter.TypeFilterSet;
import osgi.framework.monitoring.event.impl.DirectoryMonitor;

/**
 * Will monitor the bundle repository, and will raise an event if any JAR file
 * of any installed bundle is added, modified (updated) or deleted
 * @author Anibal
 */
public class RepositoryMonitor extends EventMonitor implements Observer
{

	/**
	 * Constructor, initialize monitoring process
	 * @param bundleContext OSGi BundleContext
	 */
	public RepositoryMonitor(BundleContext bundleContext) 
	{
		super(bundleContext);
		
		initObserver();		
	}
	
	/**
	 * Initialize the observation of the current directory in a different 
	 * thread
	 */
	private void initObserver() 
	{
		//run directory monitor
		DirectoryMonitor dm = new DirectoryMonitor(); 
		Thread myThread = new Thread(dm);
		myThread.start();
				
		dm.addObserver(this);
	}

	/**
	 * Initialize the TypeFilterSet values with RepositoryMonitor ones
	 * @param tf
	 */
	public void typeFilterSetDefaultConfiguration(TypeFilterSet tf)
	{
		if(tf.size() > 0)
		{
			tf.clear();
		}
		
		tf.addEntry(RepositoryEvent.ENTRY_ERROR, false);
		tf.addEntry(RepositoryEvent.ENTRY_CREATE, false);
		tf.addEntry(RepositoryEvent.ENTRY_MODIFY, false);
		tf.addEntry(RepositoryEvent.ENTRY_DELETE, false);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object obj) 
	{
		Path fileName = ((Path)((WatchEvent<?>)obj).context());
		
		Bundle bundle = getBundleContext().getBundle("file:" + fileName);
		
		// If the changed file corresponds with a bundle JAR file it will raise
		// an event
		if(bundle != null)
		{
			RepositoryEvent fe = new RepositoryEvent(this, 
					bundle, 
					((WatchEvent<?>)obj).kind().name());
			
			super.checkUpdate(fe, fe.getType(), fe.getBundle());
		}
	}
	
}
