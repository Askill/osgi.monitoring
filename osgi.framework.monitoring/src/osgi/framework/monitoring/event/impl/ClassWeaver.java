package osgi.framework.monitoring.event.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javassist.ByteArrayClassPath;
import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.osgi.framework.ServiceReference;
import org.osgi.framework.hooks.weaving.*;

import osgi.framework.monitoring.event.DataFieldUpdate;

/**
 * This class implements the OSGi WeavingHook. WeavingHook must be registered
 * as a service to be able to work. It is used to get all classes that are
 * loaded in the JVM. Then, it modifies previously specified classes to add
 * instrumentation in them. This instrumentation will consist in get the
 * DataFieldUpdate service and call the update method if there is any change in
 * previously specified fields
 * @author Anibal
 */
public class ClassWeaver implements WeavingHook
{
	
	// Contains all fields and correspondent classes that are going to be 
	// instrumented
	private ArrayList<MonitoredField> fields;

	/**
	 * Constructor
	 * @param fields all data field names and correspondent class names that
	 * are going to be instrumented
	 */
	public ClassWeaver(ArrayList<MonitoredField> fields) 
	{
		this.fields = fields;
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.hooks.weaving.WeavingHook#weave
	 * (org.osgi.framework.hooks.weaving.WovenClass)
	 */
	@Override
	public void weave(WovenClass wc) 
	{
		// Get all fields that must be instrumented from this class
		ArrayList<String> fieldsClass = getFieldName(wc.getClassName());
		
		// If not null, means that this is the class and field or fields we are
		// looking for
		if(fieldsClass != null)
		{
			// Add necessary imports to the bundle
			List<String> imports = wc.getDynamicImports();
			imports.add("osgi.framework.monitoring.event;version=\"1.0.0\"");
			imports.add("javassist.runtime");
			
			// Initialize Javassist container of classes
			ClassPool  cp = ClassPool.getDefault();
			cp.appendSystemPath();
			
			// Insert instrumented class
			cp.insertClassPath(new ByteArrayClassPath(wc.getClassName(), 
					wc.getBytes()));
			// Insert OSGi ServiceReference to be able to get services from
			// this class
			cp.insertClassPath(new ClassClassPath(ServiceReference.class));
			// Insert DataFieldUpdate, the service that we are going to use to
			// alert the monitoring framework
			cp.insertClassPath(new ClassClassPath(DataFieldUpdate.class));
			
			try 
			{
				// Get the CtClass objects from the pool
				CtClass cc = cp.get(wc.getClassName());
				CtClass srcc = cp.get(ServiceReference.class.getName());
				CtClass dfmcc = cp.get(DataFieldUpdate.class.getName());
				
				// New fields for ServiceReference and DataFieldUpdate
				CtField srf = new CtField(srcc, "sr", cc);
				CtField dfmf = new CtField(dfmcc, "dfu", cc);
				
				// Add these fields to the instrumented class
				cc.addField(srf);
				cc.addField(dfmf);
				
				// Get all methods from instrumented class
				CtMethod[] behaviors = cc.getDeclaredMethods();
				
				// This class will instrument every modification within the
				// list of fields
				ClassEditor classEditor = new ClassEditor(wc.getClassName(),
						fieldsClass);
				
				// for all methods
				for(int i = 0; i < behaviors.length; i++)
				{
					// Insert in the beginning line to get DataFieldUpdate 
					// service
					behaviors[i].insertBefore("sr = org.osgi.framework." +
							"FrameworkUtil.getBundle(this.getClass())." +
							"getBundleContext().getServiceReference(osgi." +
							"framework.monitoring.event.DataFieldUpdate." +
							"class.getName()); if(sr != null) {dfu = ("+
							DataFieldUpdate.class.getName()+
							")(org.osgi.framework.FrameworkUtil.getBundle" +
							"(this.getClass()).getBundleContext()." +
							"getService(sr));}");
					// instrument all fields to call update method within
					// DataFieldUpdate service
					behaviors[i].instrument(classEditor);
					// Insert in the end line to unget DataFieldUpdate service
					behaviors[i].insertAfter("if(sr != null) org.osgi." +
							"framework.FrameworkUtil.getBundle(this." +
							"getClass()).getBundleContext().ungetService(sr);"
							);
				}
				
				// Transform instrumented class into array of bytes
				byte[] b = cc.toBytecode();
				
				// Detach all used classes from pool
				cc.detach();
				srcc.detach();
				dfmcc.detach();
				
				// Modify instrumented class
				wc.setBytes(b);
			} 
			catch (NotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			catch (CannotCompileException e) 
			{
				e.printStackTrace();
			}		
		}
	}

	/**
	 * Get fields to be instrumented from a class
	 * @param className
	 * @return null if there is there is no correspondence between fields
	 * stored and class name
	 */
	private ArrayList<String> getFieldName(String className) 
	{
		ArrayList<String> fieldsClass = new ArrayList<String>();
		
		// For all fields to be instrumented
		for(int i = 0; i < fields.size(); i++)
		{
			if(className.compareTo(fields.get(i).getClassName()) == 0)
			{
				fieldsClass.add(fields.get(i).getFieldName());
			}
		}
		
		if(fieldsClass.size() > 0)
		{
			return fieldsClass;
		}
		else
		{
			return null;
		}
	}

}
