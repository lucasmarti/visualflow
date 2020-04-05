package selector;
public class SelectorException extends Exception 
{
	String msg;
	public SelectorException(String msg)
	{
		this.msg = msg;
	}
	
	public void printMsg()
	{
		System.out.println(msg);
	}
}
