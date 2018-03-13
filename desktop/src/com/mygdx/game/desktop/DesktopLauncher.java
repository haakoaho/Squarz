package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Squarz;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
<<<<<<< HEAD
		config.height = 1420;
=======
		config.height = 1400;
>>>>>>> mathieu
		new LwjglApplication(new Squarz(), config);
	}
}
