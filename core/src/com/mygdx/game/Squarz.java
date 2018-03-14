package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.view.beginning.Menu;
import com.mygdx.game.view.beginning.Settings;

public class Squarz extends ApplicationAdapter {
	public final String TITLE = "Squarz";
	public static int WIDTH;
	public static int HEIGHT;

	public static BitmapFont font;

	public static int valueVolume, valueVibration;

	private GameStateManager gsm;
	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		valueVolume=5;
		valueVibration=5;
		font = new BitmapFont(Gdx.files.internal("font/timeless.fnt"), false);
		font.setColor(Color.GREEN);

		gsm.push(new Settings(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(.84f,.84f,.84f, 1);

		gsm.updtate(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
	}
}
