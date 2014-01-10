package de.dakror.ben;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat.Type;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.FlacEncoder;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.recognizer.Recognizer.Languages;

/**
 * @author Dakror
 */
public class Anna
{
	public static void main(String[] args)
	{
		Microphone mic = new Microphone(Type.WAVE);
		
		try
		{
			File recordWav = new File("/recording.wav");
			File recordFlac = new File("/recording.flac");
			
			mic.captureAudioToFile(recordWav);
			mic.open();
			
			Thread.sleep(3000);
			mic.close();
			
			FlacEncoder flacEncoder = new FlacEncoder();
			flacEncoder.convertWaveToFlac(recordWav, recordFlac);
			
			Recognizer recognizer = new Recognizer(Languages.ENGLISH_US, false);
			GoogleResponse googleResponse = recognizer.getRecognizedDataForFlac(recordFlac);
			System.out.println(googleResponse.getResponse());
			
			recordWav.delete();
			recordFlac.delete();
			
			// Synthesiser synthesiser = new Synthesiser(Synthesiser.LANG_US_ENGLISH);
			// InputStream is = synthesiser.getMP3Data("Anna could you please calculate Pi");
			// copyInputStream(is, new FileOutputStream(new File("/synth.mp3")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void copyInputStream(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len >= 0)
		{
			out.write(buffer, 0, len);
			len = in.read(buffer);
		}
		in.close();
		out.close();
	}
}
