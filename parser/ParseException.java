package parser;


public class ParseException extends Exception implements ParseExceptionTypes
{
	String msg;
	String link;
	int type;
	
	public ParseException(String msg)
	{
		this.msg = msg;
	}
	
	public ParseException(int type, int line, String s)
	{
		link = "<a href=\"#"+type+"\">ERROR "+type+": </a>";
		msg = link+"Line "+line+", "+s; 		
	}
	public ParseException(int type, String s)
	{
		link = "<a href=\"#"+type+"\">ERROR "+type+": </a>";
		msg = link+s; 
	}
	public void printMsg()
	{
		System.out.println(msg);
	}
}
