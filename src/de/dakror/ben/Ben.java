package de.dakror.ben;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat.Type;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.synthesiser.Synthesiser;

/**
 * @author Dakror
 */
public class Ben
{
	public static void main(String[] args)
	{
		Microphone mic = new Microphone(Type.WAVE);
		
		try
		{
			// File recordWav = new File("/recording.wav");
			// File recordFlac = new File("/recording.flac");
			//
			// mic.captureAudioToFile(recordWav);
			// mic.open();
			//
			// Thread.sleep(3000);
			// mic.close();
			//
			// FlacEncoder flacEncoder = new FlacEncoder();
			// flacEncoder.convertWaveToFlac(recordWav, recordFlac);
			//
			// Recognizer recognizer = new Recognizer(Languages.GERMAN);
			// GoogleResponse googleResponse = recognizer.getRecognizedDataForFlac(recordFlac);
			// System.out.println(googleResponse.getResponse());
			//
			// recordWav.delete();
			// recordFlac.delete();
			
			Synthesiser synthesiser = new Synthesiser(Synthesiser.LANG_DE_GERMAN);
			InputStream is = synthesiser.getMP3Data("Hello Ben");
			copyInputStream(is, new FileOutputStream(new File("/synth.mp3")));
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
