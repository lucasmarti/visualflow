package core;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

public class Console extends JEditorPane implements HyperlinkListener
{
	String header;
	String body;
	String footer;
	Kernel kernel;
	public Console(Kernel kernel)
	{
		this.kernel = kernel;
		header = "<html><head>" +
				"<style type=\"text/css\">"+
				"p {font-size:14px; font-family:'Courier New'}"+
				"a {font-size:14px; font-family:'Courier New'; text-decoration:none; font-weight:bold}"+
				"</style>" +
				"</head><body><font face=\"Courier New\" size=\"5\">";
		footer = "</font></body></html>";
		body = "<a href=\"#allg\">Welcome to Visual Flow</a>";
		this.addHyperlinkListener(this);
		this.setEditorKit(new HTMLEditorKit());
		this.append("");
	}
	
	public void append(String s)
	{
		String text ="";
		body = body +"<br>"+ s;
		text = header + body + footer;
		this.setText(text);
	}

	public void hyperlinkUpdate(HyperlinkEvent e) 
	{
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
		{
			kernel.help(e);
		}
	}
}
