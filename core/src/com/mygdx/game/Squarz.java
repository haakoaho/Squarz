package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.MultiplayerInterface;
import com.mygdx.game.view.beginning.Menu;

public class Squarz extends ApplicationAdapter  {
	public final String TITLE = "Squarz";
	public static int WIDTH;
	public static int HEIGHT;
	public static String format;

	public MultiplayerInterface multiplayerInterface;

	public static BitmapFont font;
	public static BitmapFont font2;

	public static int valueVolume, valueVibration;

	private GameStateManager gsm;
	private SpriteBatch batch;

	public Squarz(MultiplayerInterface multiplayerInterface){
		this.multiplayerInterface = multiplayerInterface;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.setMultiplayerInterface(multiplayerInterface);


		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		valueVolume=5;
		valueVibration=5;

		if (WIDTH < 600) {
			format="low";
		} else if (WIDTH < 1000) {
			format="medium";
		} else {
			format="high";
		}

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Alcubierre.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		parameter.borderWidth = 2;
		parameter.borderColor = Color.WHITE;
		font = generator.generateFont(parameter);
		generator.dispose();
        gsm.push(new Menu(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(.84f,.84f,.84f, 1);


		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
	}

}
