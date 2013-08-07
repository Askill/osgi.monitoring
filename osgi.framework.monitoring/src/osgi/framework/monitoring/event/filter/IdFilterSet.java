package osgi.framework.monitoring.event.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * This class filter events depending on the id bundle that raised it
 * @author Anibal
 */
public class IdFilterSet extends BundleFilterSet
{
	
	// Stores id and the correspondent value
	Map<Long, Boolean> filter;
	
	/**
	 * Constructor
	 */
	public IdFilterSet()
	{
		filter = new HashMap<Long, Boolean>();
	}
	
	/**
	 * It will add or modify the mode (opened or closed) associated with an id
	 * bundle. An opened value will allow all events related to that id, while
	 * a closed one will block them
	 * @param idBundle
	 * @param mode True: opened, False: closed
	 */
	public void addEntry(long idBundle, Boolean mode)
	{
		// Adds or modifies
		filter.put(idBundle, mode);
	}
	
	/**
	 * Return the value associated with idBundle
	 * @param idBundle
	 * @return mode True: opened, False: closed, null if doesn't exist
	 */
	public boolean isOpen(long idBundle)
	{
		Boolean open = filter.get(idBundle);

		if(open == null)
		{
			return true;
		}
		
		return open;
	}
	
}
