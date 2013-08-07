package osgi.framework.monitoring.event.filter;


import java.util.HashMap;
import java.util.Map;

/**
 * This class filter events depending on the name of the bundle that raised it
 * @author Anibal
 */
public class NameFilterSet extends BundleFilterSet
{
	// Stores name and correspondent value
	private Map<String, Boolean> filter;
	
	/**
	 * Constructor
	 */
	public NameFilterSet()
	{
		filter = new HashMap<String, Boolean>();
	}
	
	/**
	 * It will add or modify the mode (opened or closed) associated with name
	 * of a bundle. An opened value will allow all events related to that name,
	 * while a closed one will block them
	 * @param nameBundle
	 * @param mode True: opened, False: closed
	 */
	public void addEntry(String nameBundle, Boolean mode)
	{
		// Adds or modifies
		filter.put(nameBundle, mode);
	}

	/**
	 * Return the value associated with nameBundle
	 * @param nameBundle
	 * @return mode True: opened, False: closed, null if it doesn't exist
	 */
	public boolean isOpen(String nameBundle)
	{	
		Boolean open =  filter.get(nameBundle);

		if(open == null)
		{
			return true;
		}
		
		return open;
	}
	
}
