package osgi.bundle.monitoring.dem2.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import osgi.bundle.monitoring.dem2.model.Model;
import osgi.bundle.monitoring.dem2.view.View;

public class ButtonListener implements MouseListener
{
	
	private Model model;
	
	private View view;
	
	public ButtonListener(Model model, View view)
	{
		this.model = model;
		this.view = view;
	}

	@Override
	public void mouseClicked(MouseEvent me)
	{
		String button = ((JButton)me.getSource()).getText();
		
		if(button.compareTo("Install") == 0)
		{
			String fileName = view.installDialog();
			
			if(fileName != null)
			{
				model.installBundle(fileName);
			}
		}
		else if(button.compareTo("Uninstall") == 0)
		{
			long id = view.getSelectedBundleId();
			
			if(id >= 0)
			{
				model.uninstallBundle(id);
			}
		}
		else if(button.compareTo("Update") == 0)
		{
			long id = view.getSelectedBundleId();
			
			if(id >= 0)
			{
				model.updateBundle(id);
			}
		}
		else if(button.compareTo("Start") == 0)
		{
			long id = view.getSelectedBundleId();
			
			if(id >= 0)
			{
				model.startBundle(id);
			}
		}
		else if(button.compareTo("Stop") == 0)
		{
			long id = view.getSelectedBundleId();
			
			if(id >= 0)
			{
				model.stopBundle(id);
			}
		}
		else if(button.compareTo("Monitor") == 0)
		{
			long id = view.getSelectedBundleId();
			
			if(id >= 0)
			{
				model.setIdOpened(id);
				view.checkMonitoring(id);
			}
		}
		else if(button.compareTo("Unmonitor") == 0)
		{
			long id = view.getSelectedBundleId();
			
			if(id >= 0)
			{
				model.setIdClosed(id);
				view.uncheckMonitoring(id);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent me) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent me) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent me) 
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent me) 
	{
		
	}

}
