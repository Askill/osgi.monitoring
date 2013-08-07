package osgi.bundle.monitoring.dem2.view;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceEvent;

import osgi.framework.monitoring.event.ServiceEventMod;

public class LogList extends JScrollPane
{
	
	private static final long serialVersionUID = 1L;

	private JList<String> listLog;
	
	private DefaultListModel<String> listModelLog;
	
	public LogList(int x, int y)
	{
		listLog = new JList<String>();
		
		listModelLog = new DefaultListModel<String>();
		
		listLog.setModel(listModelLog);

		setViewportView(listLog);
		setPreferredSize(new Dimension(x, y));
		
		listLog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void insertList(String message)
	{
		listModelLog.addElement(message);
	}
	
	public void insertListLog(String message)
	{
		try 
		{
			Thread.sleep(10);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
				
		insertList(message);
	}
	
	private String getBundleEvent(int type) 
	{
		switch(type)
		{
		case BundleEvent.INSTALLED:
			return "Installed";
		case BundleEvent.LAZY_ACTIVATION:
			return "Lazily activated";
		case BundleEvent.RESOLVED:
			return "Resolved";
		case BundleEvent.STARTED:
			return "Started";
		case BundleEvent.STARTING:
			return "Activated";
		case BundleEvent.STOPPED:
			return "Stopped";
		case BundleEvent.STOPPING:
			return "Deactivated";
		case BundleEvent.UNINSTALLED:
			return "Uninstalled";
		case BundleEvent.UNRESOLVED:
			return "Unresolved";
		case BundleEvent.UPDATED:
			return "Updated";
		}
		
		return "Unknown";
	}
	
	private String getServiceEvent(int type) 
	{
		switch(type)
		{
			case ServiceEvent.MODIFIED:
				return "modified";
			case ServiceEvent.MODIFIED_ENDMATCH:
				return "modified endmatch";
			case ServiceEvent.REGISTERED:
				return "registered";
			case ServiceEvent.UNREGISTERING:
				return "unregistered";
		}
		
		return "Unknown";
	}

	public void logBundleEvent(BundleEvent be) 
	{
		String message = "Bundle state: bundle "+
				be.getBundle().getBundleId()+
				" "+
				getBundleEvent(be.getType());
		insertListLog(message);
	}
	
	public void logServiceEvent(ServiceEventMod se) 
	{
		String message = "Service registry: bundle "+
				se.getServiceReference().getBundle().getBundleId()+
				" has "+
				getServiceEvent(se.getType())+
				" a service "+
				se.getService().getClass().getName();
		
		//Only working with this
		/*try 
		{
			Thread.sleep(10);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}*/
		
		insertListLog(message);
	}
	
}
