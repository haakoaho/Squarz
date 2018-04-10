package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Squarz;
import com.mygdx.game.model.MultiplayerInterface;
import com.sun.org.apache.xpath.internal.operations.Mult;

<<<<<<< HEAD
=======
import java.util.Queue;

>>>>>>> parent of 0aba017... Merge branch 'Send+Invite' into Lucas-le-BG-du-27
public class DesktopLauncher{
	public static void main (String[] arg){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 1400;
		new LwjglApplication(new Squarz(new DesktopMultiPlayer()), config);
	}
}
