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
 

package de.dakror.anna.layer;

import java.awt.Graphics2D;

import com.darkprograms.speech.microphone.Microphone.CaptureState;

import de.dakror.anna.game.Game;
import de.dakror.anna.ui.MicrophoneButton;
import de.dakror.gamesetup.layer.Layer;
import de.dakror.gamesetup.ui.ClickEvent;

/**
 * @author Dakror
 */
public class MainLayer extends Layer {
	public MicrophoneButton mic;
	
	@Override
	public void draw(Graphics2D g) {
		drawComponents(g);
		
		// Color c = g.getColor();
		//
		// g.setColor(Color.blue);
		//
		// int width = Game.getWidth() / 3 * 2;
		// int height = Game.getHeight() / 8;
		// if (Game.currentGame.amp != null)
		// {
		// try
		// {
		// int wper = width / Game.currentGame.amp.length;
		// for (int i = 0; i < Game.currentGame.amp.length / 2; i++)
		// {
		// double mag = Game.currentGame.amp[i].getMagnitude();
		// CFG.p(mag / 44100d);
		// double h = height / 2 * mag / 44100d;
		// g.fillRect((Game.getWidth() - width) / 2 + wper * i, Game.getHeight() / 4 * 3 - (int) Math.round(h), wper, (int) Math.round(h));
		// }
		// }
		// catch (NullPointerException e)
		// {}
		// g.fillRect(Math.round(Game.getWidth() / 2 - Game.currentGame.leftAmp * width), Math.round(Game.getHeight() / 4 * 3), Math.round(Game.currentGame.leftAmp * width + Game.currentGame.rightAmp *
		// width), 20);
		// }
		// g.setColor(c);
	}
	
	@Override
	public void update(int tick) {
		updateComponents(tick);
	}
	
	@Override
	public void init() {
		mic = new MicrophoneButton((Game.getWidth() - 256) / 2, (Game.getHeight() - 256) / 2);
		mic.addClickEvent(new ClickEvent() {
			@Override
			public void trigger() {
				if (Game.currentGame.mic.getState() != CaptureState.CLOSED) // already recording
				{
					Game.currentGame.mic.close();
					Game.currentGame.sendRecognizeRequest();
					return;
				} else {
					try {
						Game.currentGame.mic.captureAudioToFile(Game.currentGame.file);
						Game.currentGame.mic.open();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		components.add(mic);
	}
}
