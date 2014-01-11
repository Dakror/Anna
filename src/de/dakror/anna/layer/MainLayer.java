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
public class MainLayer extends Layer
{
	public MicrophoneButton mic;
	
	@Override
	public void draw(Graphics2D g)
	{
		drawComponents(g);
		
		// Color c = g.getColor();
		//
		// g.setColor(Color.blue);
		//
		// int width = Game.getWidth() / 4;
		//
		// g.fillRect(Math.round(Game.getWidth() / 2 - Game.currentGame.leftAmp * width), Math.round(Game.getHeight() / 4 * 3), Math.round(Game.currentGame.leftAmp * width + Game.currentGame.rightAmp * width), 20);
		//
		// g.setColor(c);
	}
	
	@Override
	public void update(int tick)
	{
		updateComponents(tick);
	}
	
	@Override
	public void init()
	{
		mic = new MicrophoneButton((Game.getWidth() - 256) / 2, (Game.getHeight() - 256) / 2);
		mic.addClickEvent(new ClickEvent()
		{
			@Override
			public void trigger()
			{
				if (Game.currentGame.mic.getState() != CaptureState.CLOSED) // already recording
				{
					Game.currentGame.mic.close();
					Game.currentGame.sendRecognizeRequest();
					return;
				}
				else
				{
					try
					{
						Game.currentGame.mic.captureAudioToFile(Game.currentGame.file);
						Game.currentGame.mic.open();
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
		components.add(mic);
	}
}
