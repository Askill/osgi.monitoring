package osgi.bundle.monitoring.dem2.view;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;

import osgi.framework.monitoring.event.ServiceEventMod;

public class ServiceTable extends JScrollPane
{

	private static final long serialVersionUID = 1L;
	
	JTable serviceTable;
	
	private ServiceTableModel tableModel;

	public ServiceTable(int x, int y) 
	{
		tableModel = new ServiceTableModel();
		
		serviceTable = new JTable(tableModel);
		serviceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		serviceTable.getColumnModel().getColumn(0).setPreferredWidth(225);
		serviceTable.getColumnModel().getColumn(1).setPreferredWidth(75);
		serviceTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		serviceTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		
		setViewportView(serviceTable);
		setPreferredSize(new Dimension(x, y));
	}
	
	private void addService(ServiceReference<?> reference, String serviceName) 
	{
		tableModel.insertRow(reference,
				serviceName,
				reference.getBundle().getBundleId(), 
				reference.getBundle().getSymbolicName(), 
				reference.getBundle().getVersion().toString());	
	}
	
	private void modifyService(ServiceReference<?> reference, String serviceName)
	{
		tableModel.modifyState(reference,
				serviceName,
				reference.getBundle().getBundleId(), 
				reference.getBundle().getSymbolicName(), 
				reference.getBundle().getVersion().toString());
	}
	
	private void removeService(ServiceReference<?> reference) 
	{
		tableModel.deleteRow(reference);
	}

	public void changeService(ServiceEventMod se) 
	{
		switch(se.getType())
		{
			case ServiceEvent.MODIFIED:
				modifyService(se.getServiceReference(), se.getService().getClass().getName());
				break;
			case ServiceEvent.MODIFIED_ENDMATCH:
				modifyService(se.getServiceReference(), se.getService().getClass().getName());
				break;
			case ServiceEvent.REGISTERED:
				addService(se.getServiceReference(), se.getService().getClass().getName());
				break;
			case ServiceEvent.UNREGISTERING:
				removeService(se.getServiceReference());
				break;
		}
	}
}
