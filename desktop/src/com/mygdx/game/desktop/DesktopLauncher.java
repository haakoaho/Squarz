package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Squarz;
import com.mygdx.game.model.MultiplayerInterface;
import com.sun.org.apache.xpath.internal.operations.Mult;

<<<<<<< HEAD
public class DesktopLauncher {
	public static void main (String[] arg) {
=======
import java.util.Queue;

public class DesktopLauncher{
	public static void main (String[] arg){
>>>>>>> parent of 3438c66... Merge branch 'Send+Invite' into Maxime
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 1400;
		new LwjglApplication(new Squarz(), config);
	}
}
