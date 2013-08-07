package osgi.bundle.monitoring.dem2.view;

import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import osgi.bundle.monitoring.dem2.controller.CheckBoxMenuListener;


public class Menu extends JMenuBar
{
	
	private static final long serialVersionUID = 1L;

	private ArrayList<JCheckBoxMenuItem> checkBoxesMonitor;
	
	private ArrayList<JCheckBoxMenuItem> checkBoxesStatFilter;
	
	private ArrayList<JCheckBoxMenuItem> checkBoxesServFilter;
	
	public Menu()
	{
		checkBoxesMonitor = new ArrayList<JCheckBoxMenuItem>();
		
		checkBoxesStatFilter = new ArrayList<JCheckBoxMenuItem>();
		
		checkBoxesServFilter = new ArrayList<JCheckBoxMenuItem>();
		
		JMenu monitorMenu, filterMenu, statSubMenu, servSubMenu;
		
		monitorMenu = new JMenu("Monitors");
		
		checkBoxesMonitor.add(new JCheckBoxMenuItem("Bundle state"));
		checkBoxesMonitor.add(new JCheckBoxMenuItem("Services"));
		
		for(JCheckBoxMenuItem checkBox: checkBoxesMonitor)
		{
			monitorMenu.add(checkBox);
		}
		
		checkBoxesMonitor.get(0).setState(true);
		
		filterMenu = new JMenu("Filters");
		
		statSubMenu = new JMenu("Bundle state");
		servSubMenu = new JMenu("Services");
		
		checkBoxesStatFilter.add(new JCheckBoxMenuItem("Installed"));
		checkBoxesStatFilter.add(new JCheckBoxMenuItem("Lazy Activation"));
		checkBoxesStatFilter.add(new JCheckBoxMenuItem("Resolved"));
		checkBoxesStatFilter.add(new JCheckBoxMenuItem("Started"));
		checkBoxesStatFilter.add(new JCheckBoxMenuItem("Starting"));
		checkBoxesStatFilter.add(new JCheckBoxMenuItem("Stopped"));
		checkBoxesStatFilter.add(new JCheckBoxMenuItem("Stopping"));
		checkBoxesStatFilter.add(new JCheckBoxMenuItem("Uninstalled"));
		checkBoxesStatFilter.add(new JCheckBoxMenuItem("Unresolved"));
		checkBoxesStatFilter.add(new JCheckBoxMenuItem("Updated"));
		
		for(JCheckBoxMenuItem checkBox: checkBoxesStatFilter)
		{
			statSubMenu.add(checkBox);
			checkBox.setState(true);
		}
		
		checkBoxesServFilter.add(new JCheckBoxMenuItem("Modified"));
		checkBoxesServFilter.add(new JCheckBoxMenuItem("Modified Endmatch"));
		checkBoxesServFilter.add(new JCheckBoxMenuItem("Registered"));
		checkBoxesServFilter.add(new JCheckBoxMenuItem("Unregistered"));
		
		for(JCheckBoxMenuItem checkBox: checkBoxesServFilter)
		{
			servSubMenu.add(checkBox);
			checkBox.setState(true);
		}
		
		filterMenu.add(statSubMenu);
		filterMenu.add(servSubMenu);
		
		add(monitorMenu);
		add(filterMenu);
	}

	public void completeMenu(CheckBoxMenuListener checkBoxMenuListener) 
	{
		for(JCheckBoxMenuItem checkBox: checkBoxesMonitor)
		{
			checkBox.addActionListener(checkBoxMenuListener);
		}
		
		for(JCheckBoxMenuItem checkBox: checkBoxesStatFilter)
		{
			checkBox.addActionListener(checkBoxMenuListener);
		}
		
		for(JCheckBoxMenuItem checkBox: checkBoxesServFilter)
		{
			checkBox.addActionListener(checkBoxMenuListener);
		}
	}

}
