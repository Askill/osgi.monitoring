package osgi.bundle.monitoring.dem2.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;

import osgi.bundle.monitoring.dem2.controller.ButtonListener;


public class RightPane extends Container
{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<JButton> buttons;
	
	public RightPane()
	{
		setLayout(new GridLayout(15, 1));
		
		buttons = new ArrayList<JButton>();
		
		addFillerToYPane();
		addButtonToPane("Install");
		addFillerToYPane();
		addButtonToPane("Uninstall");
		addFillerToYPane();
		addButtonToPane("Update");
		addFillerToYPane();
		addButtonToPane("Start");
		addFillerToYPane();
		addButtonToPane("Stop");
		addFillerToYPane();
		addButtonToPane("Monitor");
		addFillerToYPane();
		addButtonToPane("Unmonitor");
		addFillerToYPane();
	}
	
	private void addFillerToYPane() 
	{
		add(Box.createRigidArea(new Dimension(0, 25)));
	}
	
	private void addButtonToPane(String text)
	{
		JButton button = new JButton(text);
		buttons.add(button);
		add(button);
	}

	public void completeRightPane(ButtonListener buttonListener) 
	{
		for(JButton button: buttons)
		{
			button.addMouseListener(buttonListener);
		}
	}	

}
