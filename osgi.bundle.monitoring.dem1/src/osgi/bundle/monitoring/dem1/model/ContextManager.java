package osgi.bundle.monitoring.dem1.model;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 * Provides methods to manage bundles in the OSGi BundleContext
 * @author Anibal
 */
public class ContextManager
{
	
	private BundleContext bundleContext;
	
	public ContextManager(BundleContext bundleContext)
	{
		this.bundleContext = bundleContext;
	}
	
	public void installBundle(String file)
	{
		try 
		{
			bundleContext.installBundle("file:"+file);
		} catch (BundleException e) 
		{
			
		}
	}

	public void uninstallBundle(long id)
	{
		try 
		{
			bundleContext.getBundle(id).uninstall();
		} catch (BundleException e) 
		{
			
		}
	}
	
	public void updateBundle(long id)
	{
		try 
		{
			bundleContext.getBundle(id).update();
		} catch (BundleException e) 
		{
			
		}
	}
	
	public void startBundle(long id)
	{
		try 
		{
			bundleContext.getBundle(id).start();
		} catch (BundleException e) 
		{
			
		}
	}
	
	public void stopBundle(long id)
	{
		try 
		{
			bundleContext.getBundle(id).stop();
		} catch (BundleException e) 
		{
			
		}
	}

}
