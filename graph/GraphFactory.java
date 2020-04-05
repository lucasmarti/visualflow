package graph;
import java.io.IOException;
import java.util.LinkedList;

public class GraphFactory 
{
	public static Graph newGraph(LinkedList<Entry> selection, String title)
	{
		double[][]values;
		String[] names;
			try {
				names = new String[selection.size()];
				values = new double[selection.size()][];
				for(int i=0; i<selection.size(); i++)
				{
					Data d = DataLoader.fromFile(selection.get(i).filename);
					names[i]=selection.get(i).filename+" "+selection.get(i).compartment+" "+selection.get(i).run;
					values[i] = d.get(selection.get(i).compartment,selection.get(i).run);
				}					
				return new Graph(values,names,title);
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			return null;		
	}
	
}
