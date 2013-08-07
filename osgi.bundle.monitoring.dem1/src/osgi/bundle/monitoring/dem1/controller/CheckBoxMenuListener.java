package osgi.bundle.monitoring.dem1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;

import org.osgi.framework.BundleEvent;

import osgi.bundle.monitoring.dem1.model.Model;
import osgi.framework.monitoring.event.RepositoryEvent;
import osgi.framework.monitoring.event.ManifestEvent;

public class CheckBoxMenuListener implements ActionListener
{
	
	private Model model;
	
	public CheckBoxMenuListener(Model model)
	{
		this.model = model;
	}
	
	public void changeMonitorState(boolean state, int index)
	{
		if(state == true)
		{
			model.setMonitorOn(index);
		}
		else
		{
			model.setMonitorOff(index);
		}
	}
	
	private void changeTypeFilter(boolean state, int index, int type)
	{
		if(state == true)
		{
			model.setTypeOpened(index, type);
		}
		else
		{
			model.setTypeClosed(index, type);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		JCheckBoxMenuItem cb = (JCheckBoxMenuItem)ae.getSource();
		String checkBox = cb.getText();
		
		if(checkBox.compareTo("Bundle repository") == 0)
		{
			changeMonitorState(cb.getState(), 0);
		}
		else if(checkBox.compareTo("Bundle state") == 0)
		{
			changeMonitorState(cb.getState(), 1);
		}
		else if(checkBox.compareTo("Bundle manifest") == 0)
		{
			changeMonitorState(cb.getState(), 2);
		}
		else if(checkBox.compareTo("Data field") == 0)
		{
			changeMonitorState(cb.getState(), 3);
		}
		else if(checkBox.compareTo("Create") == 0)
		{
			changeTypeFilter(cb.getState(), 0, RepositoryEvent.ENTRY_CREATE);
		}
		else if(checkBox.compareTo("Modify") == 0)
		{
			changeTypeFilter(cb.getState(), 0, RepositoryEvent.ENTRY_MODIFY);
		}
		else if(checkBox.compareTo("Delete") == 0)
		{
			changeTypeFilter(cb.getState(), 0, RepositoryEvent.ENTRY_DELETE);
		}
		else if(checkBox.compareTo("Installed") == 0)
		{
			changeTypeFilter(cb.getState(), 1, BundleEvent.INSTALLED);
		}
		else if(checkBox.compareTo("Lazy Activation") == 0)
		{
			changeTypeFilter(cb.getState(), 1, BundleEvent.LAZY_ACTIVATION);
		}
		else if(checkBox.compareTo("Resolved") == 0)
		{
			changeTypeFilter(cb.getState(), 1, BundleEvent.RESOLVED);
		}
		else if(checkBox.compareTo("Started") == 0)
		{
			changeTypeFilter(cb.getState(), 1, BundleEvent.STARTED);
		}
		else if(checkBox.compareTo("Starting") == 0)
		{
			changeTypeFilter(cb.getState(), 1, BundleEvent.STARTING);
		}
		else if(checkBox.compareTo("Stopped") == 0)
		{
			changeTypeFilter(cb.getState(), 1, BundleEvent.STOPPED);
		}
		else if(checkBox.compareTo("Stopping") == 0)
		{
			changeTypeFilter(cb.getState(), 1, BundleEvent.STOPPING);
		}
		else if(checkBox.compareTo("Uninstalled") == 0)
		{
			changeTypeFilter(cb.getState(), 1, BundleEvent.UNINSTALLED);
		}
		else if(checkBox.compareTo("Unresolved") == 0)
		{
			changeTypeFilter(cb.getState(), 1, BundleEvent.UNRESOLVED);
		}
		else if(checkBox.compareTo("Updated") == 0)
		{
			changeTypeFilter(cb.getState(), 1, BundleEvent.UPDATED);
		}
		else if(checkBox.compareTo("Created") == 0)
		{
			changeTypeFilter(cb.getState(), 2, ManifestEvent.CREATED);
		}
		else if(checkBox.compareTo("Modified") == 0)
		{
			changeTypeFilter(cb.getState(), 2, ManifestEvent.MODIFIED);
		}
		else if(checkBox.compareTo("Deleted") == 0)
		{
			changeTypeFilter(cb.getState(), 2, ManifestEvent.DELETED);
		}
		else
		{
			//fields
		}
	}

}
