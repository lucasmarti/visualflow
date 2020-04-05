package selector;
import java.io.File;
import java.io.FilenameFilter;
public class HeadFileFilter implements FilenameFilter 
{
	public boolean accept(File arg0, String arg1) 
	{
	return arg1.endsWith("head");
	}

}
