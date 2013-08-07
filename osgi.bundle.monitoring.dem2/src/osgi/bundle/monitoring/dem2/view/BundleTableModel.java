package osgi.bundle.monitoring.dem2.view;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class BundleTableModel extends AbstractTableModel
{

	private static final long serialVersionUID = 1L;

	private String[] columnNames = 
		{"Monitoring",
			"Id",
			"State",
			"Symbolic Name"
		};
	
	private ArrayList<ArrayList<Object>> data;
	
	public BundleTableModel()
	{
		data = new ArrayList<ArrayList<Object>>();
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
	
	public void insertRow(Boolean monitoring, long id, String state, String name)
	{
		int row = data.size();
		
		setValueAt(monitoring, row, 0);
		setValueAt(id, row, 1);
		setValueAt(state, row, 2);
		setValueAt(name, row, 3);
	}
	
	private int rowOfId(long id) 
	{
		int first = 0;
		int last = data.size() - 1;
		
		int middle;
		
		while((first <= last))
		{
			middle = (int)(first + ((last - first)/2));
			
			if(id < (long)data.get(middle).get(1))
			{
				last = middle - 1;
			}
			else if(id > (long)data.get(middle).get(1))
			{
				first = middle + 1;
			}
			else
			{
				return middle;
			}
		}
		
		return -1;
	}
	
	public void modifyState(long id, String state)
	{
		int row = rowOfId(id);
		
		if(row > -1)
		{
			setValueAt(state, row, 2);
		}
	}
	
	public void modifyMonitoring(long id, Boolean monitoring)
	{
		int row = rowOfId(id);
		
		if(row > -1)
		{
			setValueAt(monitoring, row, 0);
		}
	}

	public void deleteRow(long id) 
	{
		int row = rowOfId(id);
		
		data.remove(row);
		fireTableDataChanged();
	}
	
	public long idOfRow(int row)
	{
		return (long)data.get(row).get(1);
	}
	
}
