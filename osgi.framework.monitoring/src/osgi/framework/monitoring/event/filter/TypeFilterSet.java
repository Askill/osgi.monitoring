package osgi.framework.monitoring.event.filter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * This class can block or allow events depending on their type
 * @author Anibal
 */
public class TypeFilterSet
{
	// Stores type and corespondent value
	Map<Integer, Boolean> filter;
	
	/**
	 * Constructor
	 */
	public TypeFilterSet()
	{
		filter = new HashMap<Integer, Boolean>();
	}
	
	/**
	 * This method will add or modify the mode (opened or closed) associated
	 * with a type of event. An opened value will allow all events of that 
	 * kind, while a closed one will block them
	 * @param type
	 * @param mode True: opened, False: closed
	 */
	public void addEntry(int type, boolean mode)
	{
		//adds or modifies
		filter.put(type, mode);
	}
	
	/**
	 * Reset all filter with a specified value
	 * @param mode value
	 */
	public void resetTypeFilter(boolean mode)
	{
		Iterator<Map.Entry<Integer, Boolean>> entries = 
				filter.entrySet().iterator();
		Map.Entry<Integer, Boolean> entry;
		
		while (entries.hasNext()) 
		{
		    entry = entries.next();
		    entry.setValue(mode);
		}
	}
	
	/**
	 * Reset filter with opened value
	 */
	public void openTypeFilter()
	{
		resetTypeFilter(true);
	}
	
	/**
	 * Reset filter with closed value
	 */
	public void closeTypeFilter()
	{
		resetTypeFilter(false);
	}
	
	/**
	 * Return the value associated with type
	 * @param type
	 * @return mode True: opened, False: closed
	 */
	public boolean isOpen(int type)
	{
		Boolean open = filter.get(type);
		
		if(open == null)
		{
			return true;
		}
		
		return open;
	}

	/**
	 * @return size of filter
	 */
	public int size() 
	{
		return filter.size();
	}

	/**
	 * Remove all entries from filter
	 */
	public void clear() 
	{
		filter.clear();
	}
}
