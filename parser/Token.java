package parser;
public class Token 
{
	public String payload;
	public int type;
	
	public Token(String p, int t)
	{
		payload = p;
		type = t;
	}
}
