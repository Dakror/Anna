package de.dakror.anna;

import javax.swing.UIManager;

import de.dakror.anna.game.Game;
import de.dakror.anna.game.UpdateThread;
import de.dakror.anna.gui.Frame;


/**
 * @author Dakror
 */
public class Anna {
	static Frame frame;
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		// frame = new Frame();
		
		new Game();
		Game.currentFrame.init("Anna");
		try {
			Game.currentFrame.setFullscreen();
		} catch (IllegalStateException e) {
			System.exit(0);
		}
		
		Game.currentFrame.updater = new UpdateThread();
		
		while (true)
			Game.currentFrame.main();
	}
}
