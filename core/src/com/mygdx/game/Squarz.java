package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.AIPreferences;
import com.mygdx.game.control.GameStateManager;

public class Squarz extends ApplicationAdapter {
	public final String TITLE = "Squarz";
	public final static int WIDTH = 720;
	public final static int HEIGHT = 1240;

	private GameStateManager gsm;
	private SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new AIPreferences(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(100, 0, 50, 1);
		gsm.updtate(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
