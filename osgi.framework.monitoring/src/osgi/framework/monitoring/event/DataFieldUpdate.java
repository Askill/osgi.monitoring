package osgi.framework.monitoring.event;

/**
 * This class is registered as a service by DataFieldMonitor, inserted in a
 * weave class by ClassWeaver and invoked by that class when a field value
 * is modified. It contains the different update functions for the different
 * primitive data types
 * @author Anibal
 */
public class DataFieldUpdate 
{
	
	private DataFieldMonitor dfm;
	
	public DataFieldUpdate(DataFieldMonitor dfm)
	{
		this.dfm = dfm;
	}
	
	public void update(Object field, String className, String fieldName)
	{
		dfm.update(field, className, fieldName);
	}
	
	public void update(byte field, String className, String fieldName)
	{
		dfm.update((Byte)field, className, fieldName);
	}
	
	public void update(short field, String className, String fieldName)
	{
		dfm.update((Short)field, className, fieldName);
	}
	
	public void update(int field, String className, String fieldName)
	{
		dfm.update((Integer)field, className, fieldName);
	}
	
	public void update(long field, String className, String fieldName)
	{
		dfm.update((Long)field, className, fieldName);
	}
	
	public void update(float field, String className, String fieldName)
	{
		dfm.update((Float)field, className, fieldName);
	}
	
	public void update(double field, String className, String fieldName)
	{
		dfm.update((Double)field, className, fieldName);
	}
	
	public void update(boolean field, String className, String fieldName)
	{
		dfm.update((Boolean)field, className, fieldName);
	}
	
	public void update(char field, String className, String fieldName)
	{
		dfm.update((Character)field, className, fieldName);
	}

}
