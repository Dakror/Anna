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
