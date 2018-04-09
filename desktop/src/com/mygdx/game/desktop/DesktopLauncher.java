package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Squarz;

<<<<<<< HEAD

public class DesktopLauncher{
	public static void main (String[] arg){
=======
public class DesktopLauncher {
	public static void main (String[] arg) {
>>>>>>> parent of 6df28c3... Revert "Revert "Merge branch 'mathieu' into Maxime""
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 1400;
		new LwjglApplication(new Squarz(), config);
	}
}