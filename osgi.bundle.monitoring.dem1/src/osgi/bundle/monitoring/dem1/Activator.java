package osgi.bundle.monitoring.dem1;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import osgi.bundle.monitoring.dem1.controller.ButtonListener;
import osgi.bundle.monitoring.dem1.controller.CheckBoxMenuListener;
import osgi.bundle.monitoring.dem1.controller.MenuItemListener;
import osgi.bundle.monitoring.dem1.model.Model;
import osgi.bundle.monitoring.dem1.view.View;

public class Activator implements BundleActivator 
{

	private static BundleContext context;
	
	private View view;
	
	private Model model;

	static BundleContext getContext() 
	{
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception 
	{
		Activator.context = bundleContext;
		
		view = new View();
		view.initUi();
		
		model = new Model(bundleContext);
		
		ButtonListener buttonListener = new ButtonListener(model, view);
		CheckBoxMenuListener checkBoxMenuListener = new CheckBoxMenuListener(model);
		MenuItemListener menuItemListener = new MenuItemListener(model, view);
		
		model.addObserver(view);
		
		view.completeUi(buttonListener, checkBoxMenuListener, menuItemListener);
	}

	public void stop(BundleContext bundleContext) throws Exception 
	{
		Activator.context = null;
		
		model.unregisterServices();
		
		view.close();
	}

}
