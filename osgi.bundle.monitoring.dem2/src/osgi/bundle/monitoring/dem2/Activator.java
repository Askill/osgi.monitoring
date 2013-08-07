package osgi.bundle.monitoring.dem2;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import osgi.bundle.monitoring.dem2.controller.ButtonListener;
import osgi.bundle.monitoring.dem2.controller.CheckBoxMenuListener;
import osgi.bundle.monitoring.dem2.model.Model;
import osgi.bundle.monitoring.dem2.view.View;

public class Activator implements BundleActivator 
{

	private static BundleContext context;
	
	private View view;

	static BundleContext getContext() 
	{
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception 
	{
		Activator.context = bundleContext;
		
		view = new View();
		view.initUi();
		
		Model model = new Model(bundleContext);
		
		ButtonListener buttonListener = new ButtonListener(model, view);
		CheckBoxMenuListener checkBoxMenuListener = new CheckBoxMenuListener(model);
		
		model.addObserver(view);
		
		view.completeUi(buttonListener, checkBoxMenuListener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception 
	{
		Activator.context = null;
		
		view.close();
	}

}
