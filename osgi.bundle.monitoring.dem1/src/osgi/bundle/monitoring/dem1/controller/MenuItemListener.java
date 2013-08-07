package osgi.bundle.monitoring.dem1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import osgi.bundle.monitoring.dem1.model.Model;
import osgi.bundle.monitoring.dem1.view.View;

public class MenuItemListener implements ActionListener
{
	
	private Model model;
	
	private View view;
	
	public MenuItemListener(Model model, View view)
	{
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		JMenuItem mi = (JMenuItem)ae.getSource();
		String menuItem = mi.getText();
		
		if(menuItem.compareTo("Add field") == 0)
		{
			model.addField(view.addFieldDialog());
		}
	}

}
