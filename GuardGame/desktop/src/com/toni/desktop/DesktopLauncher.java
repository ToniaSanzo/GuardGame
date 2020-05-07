package com.toni.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.toni.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title      = "Guard Game";  // Stage title
		config.width      = 700;           // Stage width
		config.height     = 550;           // Stage height
		config.useGL30    = false;         // Not using GL30
		config.resizable  = true;          // Stage not resizeable

		// Launch application
		new LwjglApplication(new Game(), config);
	}
}
