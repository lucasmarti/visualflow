package help;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
public class Help extends JFrame implements HyperlinkListener
{
	JEditorPane editor_pane;
	/*
	 * creates an invisible Help object with no page shown 
	 */
	public Help()
	{
		editor_pane = new JEditorPane();
		editor_pane.addHyperlinkListener(this);
		editor_pane.setEditable(false);
		this.getContentPane().add(new JScrollPane(editor_pane));
		this.setTitle("Visual Flow Help Page");
		this.setSize(400,400);
	}
	/*
	 * makes the Help object visible and displays the default page
	 */
	public void setDefaultPage() throws IOException
	{	
		this.pack();
		editor_pane.setPage(Help.class.getResource("help.html"));
		this.setSize(400,400);
		this.setVisible(true);
		
	}
	/*
	 * makes the Help object visible and displays the given URL
	 */
	public void setPage(URL url) throws IOException
	{
		editor_pane.setPage(url);
		this.setSize(400,400);
		this.setVisible(true);
		
	}
	/*
	 *  reacts on a HyperLinkEvent. Makes the Help object visible and displays the selected URL
	 */
	public void hyperlinkUpdate(HyperlinkEvent e) 
	{
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) 
		{
             if (e instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                HTMLDocument doc = (HTMLDocument)editor_pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
            } else {
                try {
                    setPage(e.getURL());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }		
	}

}
