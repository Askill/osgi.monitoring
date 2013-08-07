package osgi.bundle.monitoring.dem1.view;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import org.osgi.framework.BundleEvent;

import osgi.framework.monitoring.event.DataFieldEvent;
import osgi.framework.monitoring.event.RepositoryEvent;
import osgi.framework.monitoring.event.ManifestEvent;

public class LeftPane extends Container
{
	
	private static final long serialVersionUID = 1L;
	
	private LogList logList;
	
	private BundleTable bundleTable;
	
	public LeftPane()
	{			
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		addFillerToYPane();
		addLabelToPane("Installed bundles");
		addBundleListToPane(600, 150);
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
	
	private void addBundleListToPane(int x, int y) 
	{
		bundleTable = new BundleTable(x, y);
		
		add(bundleTable);
	}
	
	public void checkEvent(Object event)
	{
		String className = event.getClass().getName();
		
		if(className.compareTo(RepositoryEvent.class.getName()) == 0)
		{
			logList.logFileEvent((RepositoryEvent)event);					
		}
		else if(className.compareTo(BundleEvent.class.getName()) == 0)
		{
			bundleTable.changeBundle((BundleEvent)event);
			logList.logBundleEvent((BundleEvent)event);
		}
		else if(className.compareTo(ManifestEvent.class.getName()) == 0)
		{
			logList.logManifestEvent((ManifestEvent)event);
		}
		else if(className.compareTo(DataFieldEvent.class.getName()) == 0)
		{
			logList.logDataFieldEvent((DataFieldEvent)event);
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
