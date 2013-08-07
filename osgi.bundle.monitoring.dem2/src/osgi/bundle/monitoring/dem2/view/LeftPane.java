package osgi.bundle.monitoring.dem2.view;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import org.osgi.framework.BundleEvent;

import osgi.framework.monitoring.event.ServiceEventMod;

public class LeftPane extends Container
{
	
	private static final long serialVersionUID = 1L;
	
	private LogList logList;
	
	private BundleTable bundleTable;
	
	private ServiceTable serviceTable;
	
	public LeftPane()
	{			
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		addFillerToYPane();
		addLabelToPane("Installed bundles");
		addBundleListToPane(600, 150);
		addFillerToYPane();
		addLabelToPane("Services advailable");
		addServiceListToPane(600, 150);
		addFillerToYPane();
		addLabelToPane("Monitoring log");
		addLogListToPane(600, 200);
		addFillerToYPane();
	}

	private void addFillerToYPane() 
	{
		add(Box.createRigidArea(new Dimension(0, 25)));
	}
	
	private void addLabelToPane(String text) 
	{
		JLabel label = new JLabel(text);
		add(label);
	}
	
	private void addLogListToPane(int x, int y) 
	{
		logList = new LogList(x, y);
		
		add(logList);
	}
	
	private void addServiceListToPane(int x, int y) 
	{
		serviceTable = new ServiceTable(x, y);
		
		add(serviceTable);
	}
	
	private void addBundleListToPane(int x, int y) 
	{
		bundleTable = new BundleTable(x, y);
		
		add(bundleTable);
	}
	
	public void checkEvent(Object event)
	{
		String className = event.getClass().getName();
		
		if(className.compareTo(BundleEvent.class.getName()) == 0)
		{
			bundleTable.changeBundle((BundleEvent)event);
			logList.logBundleEvent((BundleEvent)event);
		}
		else if(className.compareTo(ServiceEventMod.class.getName()) == 0)
		{
			serviceTable.changeService((ServiceEventMod)event);
			logList.logServiceEvent((ServiceEventMod)event);
		}
	}

	public long getSelectedBundleId() 
	{
		return bundleTable.getSelectedBundleId();
	}

	public void checkMonitor(long id) 
	{
		bundleTable.checkMonitor(id);
	}

	public void uncheckMonitor(long id) 
	{
		bundleTable.uncheckMonitor(id);
	}

}
