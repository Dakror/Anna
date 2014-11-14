package de.dakror.anna.game;

import java.awt.Graphics2D;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;

import javazoom.jl.player.Player;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.recognizer.Recognizer.Languages;
import com.darkprograms.speech.synthesiser.Synthesiser;

import de.dakror.anna.ai.Reaction;
import de.dakror.anna.layer.MainLayer;
import de.dakror.gamesetup.GameFrame;

/**
 * @author Dakror
 */
public class Game extends GameFrame {
	public static Game currentGame;
	
	public Microphone mic;
	public Recognizer recognizer;
	public Synthesiser synthesiser;
	public Player player;
	
	// public Complex[] amp; float leftAmp, rightAmp;
	
	public File file;
	
	public Game() {
		currentGame = this;
	}
	
	@Override
	public void initGame() {
		mic = new Microphone(AudioFileFormat.Type.WAVE);
		recognizer = new Recognizer(Languages.ENGLISH_US, false);
		synthesiser = new Synthesiser(Synthesiser.LANG_US_ENGLISH);
		
		file = new File("recording.wav");
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (layers.size() == 0) {
			addLayer(new MainLayer());
		}
		
		drawLayers(g);
	}
	
	public void sendRecognizeRequest() {
		new Thread() {
			@Override
			public void run() {
				try {
					Reaction.react(recognizer.getRecognizedDataForWave(file));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}.start();
	}
}
