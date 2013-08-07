package osgi.bundle.monitoring.dem1.view;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import osgi.bundle.monitoring.dem1.controller.ButtonListener;
import osgi.bundle.monitoring.dem1.controller.CheckBoxMenuListener;
import osgi.bundle.monitoring.dem1.controller.MenuItemListener;
import osgi.bundle.monitoring.dem1.model.FieldEvent;

public class View implements Observer
{
	
	private JFrame mainFrame;
	
	private LeftPane leftPane;
	
	private RightPane rightPane;
	
	private Menu menu;
	
	private CheckBoxMenuListener checkBoxMenuListener;
	
	public View()
	{
		rightPane = new RightPane();
		
		leftPane = new LeftPane();
		
		menu = new Menu();
	}
	
	private static void addFillerToXPane(Container pane) 
	{
		pane.add(Box.createRigidArea(new Dimension(25, 0)));
	}
	
	private void addComponentsToPane(Container pane) 
	{
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		
		addFillerToXPane(pane);
		pane.add(leftPane);
		addFillerToXPane(pane);
		pane.add(rightPane);
		addFillerToXPane(pane);
	}

	public void initUi()
	{
		mainFrame = new JFrame("Monitoring System: demonstrator 1");
		
		mainFrame.setJMenuBar(menu);
		addComponentsToPane(mainFrame.getContentPane());
		
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}

	public String installDialog() 
	{
		JFileChooser fc = new JFileChooser();
		
		int returnVal = fc.showOpenDialog(mainFrame);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			return fc.getSelectedFile().getName();
		}
		
		return null;
	}
	
	public String addFieldDialog() 
	{	
		return JOptionPane.showInputDialog(mainFrame, "Please, introduce the fully qualified name of class and field (e.g. for String class and foo field \"java.lang.String/foo\"):", "New Field", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void completeUi(ButtonListener buttonListener, CheckBoxMenuListener checkBoxMenuListener, MenuItemListener menuItemListener) 
	{
		this.checkBoxMenuListener = checkBoxMenuListener;
		
		rightPane.completeRightPane(buttonListener);
		
		menu.completeMenu(checkBoxMenuListener, menuItemListener);
	}

	public long getSelectedBundleId() 
	{
		return leftPane.getSelectedBundleId();
	}

	public void checkMonitoring(long id) 
	{
		leftPane.checkMonitor(id);
	}

	public void uncheckMonitoring(long id) 
	{
		leftPane.uncheckMonitor(id);
	}
	
	public void close()
	{
		mainFrame.setVisible(false);
		mainFrame.dispose();
	}
	
	@Override
	public void update(Observable obs, Object obj) 
	{
		String className = obj.getClass().getName();
		
		if(className.compareTo(FieldEvent.class.getName()) == 0)
		{
			FieldEvent fe = (FieldEvent)obj;
			menu.addField(fe.getClassName(), fe.getFieldName(), checkBoxMenuListener);				
		}
		else
		{
			leftPane.checkEvent(obj);
		}
	}

}
