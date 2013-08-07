package osgi.bundle.monitoring.dem2.model;

import org.osgi.framework.BundleContext;
import osgi.bundle.monitoring.dem2.view.View;

/**
 * Provides an API with the actions the user can perform
 * @author Anibal
 */
public class Model
{
	
	private Monitor monitor;
	
	private ContextManager contextManager;
	
	public Model(BundleContext bundleContext)
	{	
		monitor = new Monitor(bundleContext);
		
		contextManager = new ContextManager(bundleContext);
	}

	public void installBundle(String fileName) 
	{
		contextManager.installBundle(fileName);
	}

	public void uninstallBundle(long id)
	{
		contextManager.uninstallBundle(id);
	}

	public void updateBundle(long id) 
	{
		contextManager.updateBundle(id);
	}

	public void startBundle(long id) 
	{
		contextManager.startBundle(id);
	}

	public void stopBundle(long id) 
	{
		contextManager.stopBundle(id);
	}

	public void setIdOpened(long id) 
	{
		monitor.setIdOpened(id);
	}

	public void setIdClosed(long id) 
	{
		monitor.setIdClosed(id);
	}

	public void setMonitorOn(int index) 
	{
		monitor.setMonitorOn(index);
	}

	public void setMonitorOff(int index) 
	{
		monitor.setMonitorOff(index);
	}

	public void setTypeOpened(int index, int type) 
	{
		monitor.setTypeOpened(index, type);
	}

	public void setTypeClosed(int index, int type) 
	{
		monitor.setTypeClosed(index, type);
	}

	public void addObserver(View view) 
	{
		monitor.addObserver(view);
	}
	
}
