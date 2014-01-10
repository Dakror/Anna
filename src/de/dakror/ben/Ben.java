package de.dakror.ben;

import java.io.File;

import javax.sound.sampled.AudioFileFormat.Type;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.FlacEncoder;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.recognizer.Recognizer.Languages;

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
			File recordWav = new File("/recording.wav");
			File recordFlac = new File("/recording.flac");
			
			mic.captureAudioToFile(recordWav);
			mic.open();
			
			Thread.sleep(3000);
			mic.close();
			
			FlacEncoder flacEncoder = new FlacEncoder();
			flacEncoder.convertWaveToFlac(recordWav, recordFlac);
			
			Recognizer recognizer = new Recognizer(Languages.GERMAN);
			GoogleResponse googleResponse = recognizer.getRecognizedDataForFlac(recordFlac);
			System.out.println(googleResponse.getResponse());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
