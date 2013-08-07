package osgi.framework.monitoring.event;

import java.util.EventObject;

/**
 * Contains the relevant data of a DataFieldMonitor event
 * @author Anibal
 */
public class DataFieldEvent extends EventObject 
{
	
	private static final long serialVersionUID = 1L;
	
	// New field value
	private Object field;
	
	// Monitored field's class name
	private String className;
	
	// Monitored field's name
	private String fieldName;

	/**
	 * Constructor
	 * @param obs observable that raised the event
	 * @param field monitored field object
	 * @param className monitored field's class name
	 * @param fieldName monitored field name
	 */
	public DataFieldEvent(Object obs, 
			Object field, 
			String className, 
			String fieldName) 
	{
		super(obs);
		
		this.field = field;
		
		this.className = className;
		
		this.fieldName = fieldName;
	}

	/**
	 * @return new value of field
	 */
	public Object getField()
	{
		return field;
	}

	/**
	 * @return monitored field's class name
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * @return monitored field name
	 */
	public String getFieldName()
	{
		return fieldName;
	}

}