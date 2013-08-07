package osgi.framework.monitoring.event.impl;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Observable;

/**
 * This class performs observation of all changes within a directory
 * @author Anibal
 */
public class DirectoryMonitor extends Observable implements Runnable
{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		try 
		{
			monitorDirectory();
		} catch (IOException e) 
		{
			e.printStackTrace();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method continuously performs observation of directory changes
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void monitorDirectory() throws IOException, InterruptedException
	{
		// Get running directory
		Path tmpPath = Paths.get(System.getProperty("user.dir"));
		
		WatchService watchService = FileSystems.getDefault().newWatchService();
	 
		// Register service for create, modify and delete operations
	    tmpPath.register(
	    	watchService,
	    	StandardWatchEventKinds.ENTRY_CREATE,
	    	StandardWatchEventKinds.ENTRY_MODIFY,
	    	StandardWatchEventKinds.ENTRY_DELETE);
	        
		/*
		 * Keep polling for events on the watched directory,
		 */
		while(true)
		{
		    WatchKey key = watchService.take();
		 
		    // Poll all the events queued for the key
		    for ( WatchEvent<?> event: key.pollEvents())
		    {   
		        setChanged();
				notifyObservers(event);
		    }
		    
		    // Reset is invoked to put the key back to ready state
		    boolean valid = key.reset();
		 
		    // If the key is invalid, just exit.
		    if ( !valid)
		    {
		        break;
		    }
		}	
	}

}
