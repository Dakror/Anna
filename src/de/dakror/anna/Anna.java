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
