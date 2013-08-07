package osgi.bundle.monitoring.dem1.view;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;

public class BundleTable extends JScrollPane
{
	
	private static final long serialVersionUID = 1L;

	JTable bundleTable;
	
	BundleTableModel tableModel;
	
	public BundleTable(int x, int y)
	{
		tableModel = new BundleTableModel();
				
		bundleTable = new JTable(tableModel);
		bundleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		bundleTable.getColumnModel().getColumn(0).setPreferredWidth(75);
		bundleTable.getColumnModel().getColumn(1).setPreferredWidth(75);
		bundleTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		bundleTable.getColumnModel().getColumn(3).setPreferredWidth(300);
		
		setViewportView(bundleTable);
		setPreferredSize(new Dimension(x, y));
	}

	private String getBundleState(int state) 
	{
		switch(state)
		{
			case Bundle.UNINSTALLED:
				return "Uninstalled";
			case Bundle.INSTALLED:
				return "Installed";
			case Bundle.RESOLVED:
				return "Resolved";
			case Bundle.STARTING:
				return "Starting";
			case Bundle.STOPPING:
				return "Stopping";
			case Bundle.ACTIVE:
				return "Active";
		}
		
		return "Unknown";
	}
	
	private void addBundle(Bundle bundle) 
	{
		tableModel.insertRow(true,
				bundle.getBundleId(), 
				getBundleState(bundle.getState()), 
				bundle.getSymbolicName());	
	}
	
	private void modifyBundle(Bundle bundle)
	{
		tableModel.modifyState(bundle.getBundleId(),
				getBundleState(bundle.getState()));
	}
	
	private void removeBundle(long id) 
	{
		tableModel.deleteRow(id);
	}	

	public void changeBundle(BundleEvent be) 
	{
		switch(be.getType())
		{
			case BundleEvent.INSTALLED:
				addBundle(be.getBundle());
				break;
			case BundleEvent.LAZY_ACTIVATION:
				modifyBundle(be.getBundle());
				break;
			case BundleEvent.RESOLVED:
				modifyBundle(be.getBundle());
				break;
			case BundleEvent.STARTED:
				modifyBundle(be.getBundle());
				break;
			case BundleEvent.STARTING:
				modifyBundle(be.getBundle());
				break;
			case BundleEvent.STOPPED:
				modifyBundle(be.getBundle());
				break;
			case BundleEvent.STOPPING:
				modifyBundle(be.getBundle());
				break;
			case BundleEvent.UNINSTALLED:
				removeBundle(be.getBundle().getBundleId());
				break;
			case BundleEvent.UNRESOLVED:
				modifyBundle(be.getBundle());
				break;
			case BundleEvent.UPDATED:
				modifyBundle(be.getBundle());
				break;
		}
	}
	
	public long getSelectedBundleId()
	{	
		int index = bundleTable.getSelectedRow();
		
		if(index >= 0)
		{
			return tableModel.idOfRow(index);
		}
		
		return -1;
	}

	public void checkMonitor(long id) 
	{
		tableModel.modifyMonitoring(id, true);
	}

	public void uncheckMonitor(long id) 
	{
		tableModel.modifyMonitoring(id, false);
	}
	
}