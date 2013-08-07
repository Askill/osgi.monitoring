package osgi.bundle.monitoring.dem1.model;

public class FieldEvent 
{
	
	private String className;
	
	private String fieldName;

	public FieldEvent(String className, String fieldName) 
	{
		this.className = className;
		this.fieldName = fieldName;
	}
	
	public String getClassName()
	{
		return className;
	}
	
	public String getFieldName()
	{
		return fieldName;
	}

}
