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

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.microphone.Microphone.CaptureState;

/**
 * @author Dakror
 */
public class Frame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public static Frame frame;
	
	public JToggleButton rec;
	
	public Microphone mic;
	
	public Frame()
	{
		super();
		
		frame = this;
		
		mic = new Microphone(AudioFileFormat.Type.WAVE);
		
		setSize(300, 256);
		setUndecorated(true);
		setLocationRelativeTo(null);
		
		rec = new JToggleButton(new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (mic.getState() == CaptureState.STARTING_CAPTURE) // already recording
				{
					mic.close();
					return;
				}
				else
				{
					try
					{
						mic.open();
						mic.captureAudioToFile(new File("recording.wav"));
					}
					catch (Exception e1)
					{
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
}
