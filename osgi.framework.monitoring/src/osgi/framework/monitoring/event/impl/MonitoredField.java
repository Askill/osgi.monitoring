package osgi.framework.monitoring.event.impl;

/**
 * This class stores instrumented field data
 * @author Anibal
 */
public class MonitoredField
{
	
	// Name of the field's class
	private String className;
	
	// Instrumented field name
	private String fieldName;
	
	/**
	 * @param className name of the field's class
	 * @param fieldName name of the instrumented field
	 */
	public MonitoredField(String className, String fieldName)
	{
		this.className = className;
		
		this.fieldName = fieldName;
	}
	
	/**
	 * @return name of the field's class
	 */
	public String getClassName()
	{
		return className;
	}
	
	/**
	 * @return instrumented field name
	 */
	public String getFieldName()
	{
		return fieldName;
	}

}
