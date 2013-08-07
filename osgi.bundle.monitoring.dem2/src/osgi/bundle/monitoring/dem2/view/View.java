package osgi.bundle.monitoring.dem2.view;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import osgi.bundle.monitoring.dem2.controller.ButtonListener;
import osgi.bundle.monitoring.dem2.controller.CheckBoxMenuListener;


public class View implements Observer
{
	
	private JFrame mainFrame;
	
	private LeftPane leftPane;
	
	private RightPane rightPane;
	
	private Menu menu;
	
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
		mainFrame = new JFrame("Monitoring System: demonstrator 2");
		
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

	public void completeUi(ButtonListener buttonListener, CheckBoxMenuListener checkBoxMenuListener) 
	{
		rightPane.completeRightPane(buttonListener);
		
		menu.completeMenu(checkBoxMenuListener);
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
		leftPane.checkEvent(obj);
	}
}
