package graph;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DataLoader 
{
	public static Data fromFile(String filename) throws IOException
	{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("my_results/"+filename+".data")));
			
			//--- first line
			StringTokenizer stk = new StringTokenizer(br.readLine(),";");
			int c = new Integer(stk.nextToken()).intValue();
			int r = new Integer(stk.nextToken()).intValue();
			int s = new Integer(stk.nextToken()).intValue();
			Data data = new Data(c,r,s);
			
			//--- second line
			stk = new StringTokenizer(br.readLine(),";");
			for(int i=0; i<c; i++)
				data.names[i] = stk.nextToken();
			
			//--- the data
			stk = new StringTokenizer(br.readLine(),";");
			for(int _c=0; _c<c; _c++)
				for(int _r=0; _r<r; _r++)
					for(int _s=0; _s<s; _s++)
						data.data[_c][_r][_s]=new Double(stk.nextToken()).doubleValue();
			return data;
	}
}
