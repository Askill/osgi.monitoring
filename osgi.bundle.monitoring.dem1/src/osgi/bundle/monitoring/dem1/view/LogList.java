package osgi.bundle.monitoring.dem1.view;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.osgi.framework.BundleEvent;

import osgi.framework.monitoring.event.DataFieldEvent;
import osgi.framework.monitoring.event.RepositoryEvent;
import osgi.framework.monitoring.event.ManifestEvent;

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
	
	public String getFileEvent(int type)
	{
		switch(type)
		{
			case RepositoryEvent.ENTRY_CREATE:
				return "Created";
			case RepositoryEvent.ENTRY_MODIFY:
				return "Modified";
			case RepositoryEvent.ENTRY_DELETE:
				return "Deleted";
		}
		
		return "Unknown";
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
	
	private String getManifestEvent(int type) 
	{
		switch(type)
		{
			case ManifestEvent.CREATED:
				return "Created";
			case ManifestEvent.MODIFIED:
				return "Modified";
			case ManifestEvent.DELETED:
				return "Deleted";
		}
		
		return "Unknown";
	}
	
	public void logFileEvent(RepositoryEvent fe) 
	{
		String message = "Bundle repository: bundle "+
				fe.getBundle().getBundleId()+
				" file "+
				getFileEvent(fe.getType());
		insertListLog(message);
	}

	public void logBundleEvent(BundleEvent be) 
	{
		String message = "Bundle state: bundle "+
				be.getBundle().getBundleId()+
				" "+
				getBundleEvent(be.getType());
		insertListLog(message);
	}

	public void logManifestEvent(ManifestEvent me) 
	{
		String message = "Bundle manifest: bundle "+
				me.getBundle().getBundleId()+
				" manifest modified: "+
				me.getHeader()+
				" "+
				getManifestEvent(me.getType());
		insertListLog(message);
	}

	public void logDataFieldEvent(DataFieldEvent dfe) 
	{
		String message = "Data field: "+
				dfe.getFieldName()+
				" in "+
				dfe.getClassName()+
				" has changed to "+
				dfe.getField();
		insertListLog(message);
	}
	
}