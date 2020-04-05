package core;
import java.io.File;

import javax.swing.filechooser.FileFilter;


public class ModFileFilter extends FileFilter 
{
	public boolean accept(File arg0) 
	{
	return arg0.getName().endsWith("mod");
	}

	public String getDescription() 
	{
		return "Visual Flow Model Files (*.mod)";
	}

}