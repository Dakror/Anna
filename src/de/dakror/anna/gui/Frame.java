/*******************************************************************************
 * Copyright 2015 Maximilian Stark | Dakror <mail@dakror.de>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package de.dakror.anna.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import javazoom.jl.player.Player;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.microphone.Microphone.CaptureState;
import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.recognizer.Recognizer.Languages;
import com.darkprograms.speech.synthesiser.Synthesiser;

import de.dakror.anna.ai.Reaction;

/**
 * @author Dakror
 */
public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static Frame frame;
	
	public JToggleButton rec;
	
	public Microphone mic;
	public Recognizer recognizer;
	public Synthesiser synthesiser;
	public Player player;
	
	public File file;
	
	public Frame() {
		super();
		
		frame = this;
		mic = new Microphone(AudioFileFormat.Type.WAVE);
		recognizer = new Recognizer(Languages.ENGLISH_US, false);
		synthesiser = new Synthesiser(Synthesiser.LANG_US_ENGLISH);
		
		file = new File("recording.wav");
		
		setSize(300, 256);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		rec = new JToggleButton(new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mic.getState() != CaptureState.CLOSED) // already recording
				{
					mic.close();
					sendRecognizeRequest();
					return;
				} else {
					try {
						mic.captureAudioToFile(file);
						mic.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		rec.setIcon(new ImageIcon(getClass().getResource("/img/microphone.png")));
		rec.setSelectedIcon(new ImageIcon(getClass().getResource("/img/microphone_blue.png")));
		rec.setBorder(null);
		rec.setOpaque(false);
		rec.setFocusPainted(false);
		rec.setBorderPainted(false);
		
		JPanel p = new JPanel(new BorderLayout());
		p.add(rec, BorderLayout.CENTER);
		setContentPane(p);
		
		setVisible(true);
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
