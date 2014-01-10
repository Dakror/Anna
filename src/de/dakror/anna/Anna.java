package de.dakror.anna;

import javax.swing.UIManager;

import de.dakror.anna.gui.Frame;


/**
 * @author Dakror
 */
public class Anna
{
	static Frame frame;
	
	public static void main(String[] args) throws Exception
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		frame = new Frame();
	}
}
