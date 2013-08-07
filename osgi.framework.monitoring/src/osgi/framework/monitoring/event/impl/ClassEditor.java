package osgi.framework.monitoring.event.impl;

import java.util.ArrayList;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

/**
 * This class edits other classes to call DataFieldUpdate methods where all
 * modifications of a data field is made
 * @author Anibal
 */
public class ClassEditor extends ExprEditor
{
	
	// Contains all data field names that are going to be edited
	private ArrayList<String> fieldsClass;
	
	// Name of the class that is going to be edited
	private String className;
	
	/**
	 * @param className name of the class to be edited
	 * @param fieldsClass all data field names to be edited
	 */
	public ClassEditor(String className, ArrayList<String> fieldsClass)
	{
		this.className = className;
		
		this.fieldsClass = fieldsClass;
	}

	public void edit(FieldAccess f) throws CannotCompileException
	{
		// If a modification of a field on the list is made
		if(f.isWriter() && (fieldsClass.contains(f.getFieldName())))
		{
			// Add after the modification line a call to DataFieldUpdate method
			// if that service is previously gotten. The parameters for such
			// method are the modified field, class name and modified field 
			// name
			f.replace("$_ = $proceed($$);if(dfu != null) dfu.update("+
			          f.getFieldName()+
			          ", \""+className+"\", \""+
			          f.getFieldName()+"\");");
		}
	}

}
