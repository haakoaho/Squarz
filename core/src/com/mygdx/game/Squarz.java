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

	public static BitmapFont font, font2, font3;

	public static int valueVolume, valueVibration;

	private GameStateManager gsm;
	private SpriteBatch batch;

	public Squarz(MultiplayerInterface multiplayerInterface){
		this.multiplayerInterface = multiplayerInterface;
	}

	public Squarz(){
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
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Alcubierre.otf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
			FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameter.size = 45;
			parameter2.size = 30;
			parameter3.size = 20;
			parameter.borderWidth = 2;
			parameter2.borderWidth = 3;
			parameter3.borderWidth = 4;
			parameter3.color = Color.BLACK;
			parameter.borderColor = Color.WHITE;
			parameter2.borderColor = Color.WHITE;
			parameter3.borderColor = Color.BLACK;
			font = generator.generateFont(parameter);
			font2 = generator.generateFont(parameter2);
			font3 = generator.generateFont(parameter3);
			generator.dispose();
		} else if (WIDTH < 1000) {
			format="medium";
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Alcubierre.otf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
			FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameter.size = 60;
			parameter2.size = 40;
			parameter3.size = 20;
			parameter.borderWidth = 2;
			parameter2.borderWidth = 3;
			parameter3.borderWidth = 2;
			parameter3.color = Color.BLACK;
			parameter.borderColor = Color.WHITE;
			parameter2.borderColor = Color.WHITE;
			parameter3.borderColor = Color.BLACK;
			font = generator.generateFont(parameter);
			font2 = generator.generateFont(parameter2);
			font3 = generator.generateFont(parameter3);
			generator.dispose();
		} else {
			format="high";
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Alcubierre.otf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
			FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameter.size = 90;
			parameter2.size = 60;
			parameter3.size = 45;
			parameter.borderWidth = 2;
			parameter2.borderWidth = 3;
			parameter3.borderWidth = 4;
			parameter3.color = Color.BLACK;
			parameter.borderColor = Color.WHITE;
			parameter2.borderColor = Color.WHITE;
			parameter3.borderColor = Color.BLACK;
			font = generator.generateFont(parameter);
			font2 = generator.generateFont(parameter2);
			font3 = generator.generateFont(parameter3);
			generator.dispose();
		}


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
