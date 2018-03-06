package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
<<<<<<< HEAD
import com.mygdx.game.view.Menu;
import com.mygdx.game.view.AIPreferences;

public class Squarz extends ApplicationAdapter {
	public final String TITLE = "Squarz";
	public final static int WIDTH = 720;
	public final static int HEIGHT = 1240;
=======
import com.mygdx.game.view.PlayModeAi;

public class Squarz extends ApplicationAdapter {
	public final String TITLE = "Squarz";
	public final static int WIDTH = 480;
	public final static int HEIGHT = 800;
>>>>>>> Maxime

	private GameStateManager gsm;
	private SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor((float)0.84,(float)0.84,(float)0.84, 1);

<<<<<<< HEAD
		gsm.push(new Menu(gsm));
=======
		gsm.push(new PlayModeAi(gsm));
>>>>>>> Maxime
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
<<<<<<< HEAD
		Gdx.gl.glClearColor(100, 0, 50, 1);
=======
>>>>>>> Maxime
		gsm.updtate(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
