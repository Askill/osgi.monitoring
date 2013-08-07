package osgi.bundle.monitoring.dem1.view;

import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import osgi.bundle.monitoring.dem1.controller.CheckBoxMenuListener;
import osgi.bundle.monitoring.dem1.controller.MenuItemListener;

public class Menu extends JMenuBar
{
	
	private static final long serialVersionUID = 1L;

	private ArrayList<JCheckBoxMenuItem> checkBoxesMonitor;
	
	private ArrayList<JCheckBoxMenuItem> checkBoxesRepFilter;
	
	private ArrayList<JCheckBoxMenuItem> checkBoxesStatFilter;
	
	private ArrayList<JCheckBoxMenuItem> checkBoxesManFilter;
	
	private JMenuItem addInstItem;
	
	private JMenu instrumentationMenu;
	
	public Menu()
	{
		checkBoxesMonitor = new ArrayList<JCheckBoxMenuItem>();
		
		checkBoxesRepFilter = new ArrayList<JCheckBoxMenuItem>();
		
		checkBoxesStatFilter = new ArrayList<JCheckBoxMenuItem>();
		
		checkBoxesManFilter = new ArrayList<JCheckBoxMenuItem>();
		
		JMenu monitorMenu, filterMenu, repSubMenu, statSubMenu, manSubMenu;
		
		monitorMenu = new JMenu("Monitors");
		
		checkBoxesMonitor.add(new JCheckBoxMenuItem("Bundle repository"));
		checkBoxesMonitor.add(new JCheckBoxMenuItem("Bundle state"));
		checkBoxesMonitor.add(new JCheckBoxMenuItem("Bundle manifest"));
		checkBoxesMonitor.add(new JCheckBoxMenuItem("Data field"));
		
		for(JCheckBoxMenuItem checkBox: checkBoxesMonitor)
		{
			monitorMenu.add(checkBox);
		}
		
		checkBoxesMonitor.get(1).setState(true);
		
		filterMenu = new JMenu("Filters");
		
		repSubMenu = new JMenu("Bundle repository");
		statSubMenu = new JMenu("Bundle state");
		manSubMenu = new JMenu("Bundle manifest");
		
		checkBoxesRepFilter.add(new JCheckBoxMenuItem("Create"));
		checkBoxesRepFilter.add(new JCheckBoxMenuItem("Modify"));
		checkBoxesRepFilter.add(new JCheckBoxMenuItem("Delete"));
		
		for(JCheckBoxMenuItem checkBox: checkBoxesRepFilter)
		{
			repSubMenu.add(checkBox);
			checkBox.setState(true);
		}
		
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
		
		checkBoxesManFilter.add(new JCheckBoxMenuItem("Created"));
		checkBoxesManFilter.add(new JCheckBoxMenuItem("Modified"));
		checkBoxesManFilter.add(new JCheckBoxMenuItem("Deleted"));
		
		for(JCheckBoxMenuItem checkBox: checkBoxesManFilter)
		{
			manSubMenu.add(checkBox);
			checkBox.setState(true);
		}
		
		filterMenu.add(repSubMenu);
		filterMenu.add(statSubMenu);
		filterMenu.add(manSubMenu);
		
		instrumentationMenu = new JMenu("Instrumentation");
		
		addInstItem = new JMenuItem("Add field");

		instrumentationMenu.add(addInstItem);
		instrumentationMenu.addSeparator();
		
		add(monitorMenu);
		add(filterMenu);
		add(instrumentationMenu);
	}

	public void completeMenu(CheckBoxMenuListener checkBoxMenuListener, MenuItemListener menuItemListener) 
	{
		for(JCheckBoxMenuItem checkBox: checkBoxesMonitor)
		{
			checkBox.addActionListener(checkBoxMenuListener);
		}
		
		for(JCheckBoxMenuItem checkBox: checkBoxesRepFilter)
		{
			checkBox.addActionListener(checkBoxMenuListener);
		}
		
		for(JCheckBoxMenuItem checkBox: checkBoxesStatFilter)
		{
			checkBox.addActionListener(checkBoxMenuListener);
		}
		
		for(JCheckBoxMenuItem checkBox: checkBoxesManFilter)
		{
			checkBox.addActionListener(checkBoxMenuListener);
		}
		
		addInstItem.addActionListener(menuItemListener);
	}
	
	public void addField(String className, String fieldName, CheckBoxMenuListener checkBoxMenuListener)
	{
		JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem(className+" "+fieldName);
		instrumentationMenu.add(checkBoxMenuItem);
		checkBoxMenuItem.addActionListener(checkBoxMenuListener);
	}

}
