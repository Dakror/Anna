package de.dakror.anna.ai;

import java.awt.Desktop;
import java.net.URI;

import javazoom.jl.player.Player;

import com.darkprograms.speech.recognizer.GoogleResponse;

import de.dakror.anna.gui.Frame;
import de.dakror.anna.settings.CFG;
import de.dakror.anna.util.Assistant;

/**
 * @author Dakror
 */
public class Reaction
{
	private static GoogleResponse input;
	
	public static void react(GoogleResponse input) throws Exception
	{
		Reaction.input = input;
		CFG.p(input.getResponse(), input.getConfidence(), input.getAllPossibleResponses());
		
		if ((c(" hello ") && cn()) || c(" hello ")) respond("hello");
		else if (c(" pi ")) respond("the first 15 digits of Pi are " + Math.PI);
		else if (c(" how old ") || c(" version ") || c(" age ")) respond("My current version is " + CFG.PHASE + "." + CFG.VERSION);
		else if (c(" wood chuck ") || c("woodchuck ") || c(" chuck wood ")) respond("He would chuck, he would, as much as he could, and chuck as much wood as a woodchuck would, if a woodchuck could chuck wood.");
		else if (c(" browse ") || c(" internet "))
		{
			String[] parts = input.getResponse().split(" ");
			String browser = Assistant.getDefaultBrowser();
			if (browser.startsWith("Unable"))
			{
				respond("I couldn't figure out the name of your default browser. I'm sorry");
				return;
			}
			boolean found = false;
			for (int i = 0; i < parts.length; i++)
			{
				if (parts[i].contains("."))
				{
					respond("Opening " + Assistant.getDefaultBrowser());
					Desktop.getDesktop().browse(new URI("http://" + parts[i]));
					found = true;
					break;
				}
			}
			
			if (!found) respond("Please tell me what to browse??");
		}
		else if (c(" search ") || c(" google "))
		{
			String[] parts = input.getResponse().split(" ");
			
			if (parts.length == 1)
			{
				respond("Please tell me what to search??");
				return;
			}
			else
			{
				String q = "";
				boolean images = false;
				for (String s : parts)
				{
					if (s.toLowerCase().contains("google") || s.toLowerCase().contains("search")) continue;
					if (s.toLowerCase().contains("images"))
					{
						images = true;
						continue;
					}
					q += s + ",";
				}
				
				String browser = Assistant.getDefaultBrowser();
				if (browser.startsWith("Unable"))
				{
					respond("I couldn't figure out the name of your default browser. I'm sorry");
					return;
				}
				
				respond("I will look that up for you. Opening " + browser);
				q = q.substring(0, q.length() - 1);
				
				Desktop.getDesktop().browse(new URI("http://google.com/search?q=" + q + (images ? "&tbm=isch" : "")));
			}
		}
		else respond("Could you please repeat that??");
	}
	
	public static void respond(String text)
	{
		try
		{
			Frame.frame.player = new Player(Frame.frame.synthesiser.getMP3Data(text));
			Frame.frame.player.play();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static boolean c(String s)
	{
		if (input.getResponse() == null) return false;
		return (" " + input.getResponse().toLowerCase()).contains(s);
	}
	
	private static boolean cn()
	{
		if (input.getResponse() == null) return false;
		return (" " + input.getResponse().toLowerCase()).contains("anna") || (" " + input.getResponse().toLowerCase()).contains("ana");
	}
}
