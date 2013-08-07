package osgi.bundle.monitoring.dem2.view;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.osgi.framework.ServiceReference;

public class ServiceTableModel extends AbstractTableModel
{

	private static final long serialVersionUID = 1L;
	
	private String[] columnNames = 
		{"Service Name",
			"Bundle Id",
			"Bundle Symbolic Name",
			"Bundle Version"
		};
	
	private ArrayList<ArrayList<Object>> data;

	private ArrayList<ServiceReference<?>> indexServiceList;
	
	public ServiceTableModel()
	{
		data = new ArrayList<ArrayList<Object>>();
		
		indexServiceList = new ArrayList<ServiceReference<?>>();
	}

	@Override
	public int getColumnCount() 
	{
		return columnNames.length;
	}

	@Override
	public int getRowCount() 
	{
		return data.size();
	}
	
	public String getColumnName(int col)
	{
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) 
	{
		return data.get(row).get(col);
	}
	
	public Class<?> getColumnClass(int col)
	{
		return getValueAt(0, col).getClass();
	}
	
	public void setValueAt(Object value, int row, int col)
	{
		if(row == data.size())
		{
			data.add(new ArrayList<Object>());
			data.get(row).add(value);
			data.get(row).add(null);
			data.get(row).add(null);
			data.get(row).add(null);
			fireTableDataChanged();
		}
		else if(row < data.size())
		{
			data.get(row).set(col, value);
			fireTableCellUpdated(row, col);
		}
	}

	public void insertRow(ServiceReference<?> reference, String serviceName,
			long bundleId, String symbolicName, String string) 
	{
		int row = data.size();
		
		setValueAt(serviceName, row, 0);
		setValueAt(bundleId, row, 1);
		setValueAt(symbolicName, row, 2);
		setValueAt(string, row, 3);
		
		indexServiceList.add(reference);
	}

	public void modifyState(ServiceReference<?> reference, String serviceName,
			long bundleId, String symbolicName, String string) 
	{
		int row = indexOfServiceList(reference);
		
		if(row > -1)
		{
			setValueAt(serviceName, row, 0);
			setValueAt(bundleId, row, 1);
			setValueAt(symbolicName, row, 2);
			setValueAt(string, row, 3);
		}
	}

	public void deleteRow(ServiceReference<?> reference) 
	{
		int row = indexOfServiceList(reference);
		
		data.remove(row);
		fireTableDataChanged();
		
		indexServiceList.remove(row);
	}
	
	private int indexOfServiceList(ServiceReference<?> unregistered)
	{
		int index = -1;
		
		for(ServiceReference<?> item: indexServiceList)
		{
			if(unregistered.equals(item) == true)
			{
				index = indexServiceList.indexOf(item);
			}
		}
		
		return index;
	}

}
