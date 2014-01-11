package de.dakror.anna.ai;

import java.awt.Desktop;
import java.net.URI;

import javazoom.jl.player.Player;

import com.darkprograms.speech.recognizer.GoogleResponse;

import de.dakror.anna.game.Game;
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
		else if (c(" wood chuck ") || c(" woodchuck ") || c(" chuck wood ")) respond("He would chuck, he would, as much as he could, and chuck as much wood as a woodchuck would, if a woodchuck could chuck wood.");
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
			Game.currentGame.player = new Player(Game.currentGame.synthesiser.getMP3Data(text)/* , new VisualEffectDevice(new JavaSoundAudioDeviceFactory().createAudioDevice()) */);
			Game.currentGame.player.play();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static boolean c(String s)
	{
		if (input.getResponse() == null) return false;
		return i().contains(s);
	}
	
	private static boolean cn()
	{
		if (input.getResponse() == null) return false;
		return i().contains("anna") || i().contains("ana");
	}
	
	private static String i()
	{
		return (" " + input.getResponse().toLowerCase() + " ");
	}
	
	// public static class VisualEffectDevice implements AudioDevice
	// {
	// private Decoder decoder;
	// private AudioDevice delegate;
	//
	// public VisualEffectDevice(AudioDevice delegate)
	// {
	// this.delegate = delegate;
	// }
	//
	// @Override
	// public void close()
	// {
	// Game.currentGame.amp = null;// leftAmp = Game.currentGame.rightAmp = 0;
	// delegate.close();
	// }
	//
	// @Override
	// public void flush()
	// {
	// delegate.flush();
	// }
	//
	// @Override
	// public int getPosition()
	// {
	// return delegate.getPosition();
	// }
	//
	// @Override
	// public boolean isOpen()
	// {
	// return delegate.isOpen();
	// }
	//
	// @Override
	// public void open(Decoder decoder) throws JavaLayerException
	// {
	// delegate.open(decoder);
	// this.decoder = decoder;
	// }
	//
	// @Override
	// public void write(short[] sample, int offs, int len) throws JavaLayerException
	// {
	// short[] s = Arrays.copyOfRange(sample, offs, offs + 512);
	// final Complex[] complex = new Complex[s.length];
	// for (int i = 0; i < complex.length; i++)
	// {
	// complex[i] = new Complex(s[i], 0);
	// }
	// new Thread()
	// {
	// @Override
	// public void run()
	// {
	// Game.currentGame.amp = FFT.fft(complex);
	// }
	// }.start();
	// // short[] sampleLeft = getSampleLeft(sample, offs, len);
	// // short[] sampleRight = getSampleRight(sample, offs, len);
	// // CFG.p(getUnsignedAverage(sampleLeft));
	// // Game.currentGame.leftAmp = getUnsignedAverage(sampleLeft) / 32768f;
	// // Game.currentGame.rightAmp = getUnsignedAverage(sampleRight) / 32768f;
	// delegate.write(sample, offs, len);
	// }
	//
	// private int channels()
	// {
	// return decoder.getOutputChannels();
	// }
	//
	// private short[] getSampleRight(short[] sample, int offs, int len)
	// {
	// short[] temp = Arrays.copyOfRange(sample, offs, offs + len - 1);
	// if (channels() == 1)
	// {
	// return temp;
	// }
	// short[] sampleRight = new short[temp.length / 2];
	// for (int i = sampleRight.length - 1; i > 0; i--)
	// {
	// sampleRight[i] = temp[i * 2 + 1];
	// }
	// return sampleRight;
	// }
	//
	// private short[] getSampleLeft(short[] sample, int offs, int len)
	// {
	// short[] temp = Arrays.copyOfRange(sample, offs, offs + len - 1);
	// if (channels() == 1)
	// {
	// return temp;
	// }
	// short[] sampleLeft = new short[temp.length / 2];
	// for (int i = sampleLeft.length - 1; i > 0; i--)
	// {
	// sampleLeft[i] = temp[i * 2];
	// }
	// return sampleLeft;
	// }
	//
	// private static int getUnsignedAverage(short[] sample)
	// {
	// CFG.p(Arrays.toString(sample));
	// int sum = 0;
	// for (short val : sample)
	// {
	// sum += val + Math.abs(Short.MIN_VALUE);
	// }
	// return sum / sample.length;
	// }
	// }
}
