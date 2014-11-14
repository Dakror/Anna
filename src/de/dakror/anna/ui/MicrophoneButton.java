package de.dakror.anna.ui;

import java.awt.Graphics2D;

import de.dakror.anna.game.Game;
import de.dakror.gamesetup.ui.ClickEvent;
import de.dakror.gamesetup.ui.ClickableComponent;

/**
 * @author Dakror
 */
public class MicrophoneButton extends ClickableComponent {
	public boolean selected;
	
	public MicrophoneButton(int x, int y) {
		super(x, y, 256, 256);
		selected = false;
		addClickEvent(new ClickEvent() {
			@Override
			public void trigger() {
				selected = !selected;
			}
		});
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(Game.getImage(state == 1 || selected ? "microphone_blue.png" : "microphone.png"), x, y, width, height, Game.w);
	}
	
	@Override
	public void update(int tick) {}
}
