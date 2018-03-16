package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.view.beginning.Menu;

import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.IGameServiceListener;
import de.golfgl.gdxgamesvcs.NoGameServiceClient;

public class Squarz extends ApplicationAdapter implements IGameServiceListener {
	public final String TITLE = "Squarz";
	public static int WIDTH;
	public static int HEIGHT;
	public IGameServiceClient gsClient;

	private GameStateManager gsm;
	private SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();


        if (gsClient == null)
            gsClient = new NoGameServiceClient();

        // for getting callbacks from the client
        gsClient.setListener(this);

        // establish a connection to the game service without error messages or login screens
        gsClient.resumeSession();

		gsm.push(new Menu(gsm));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// move this to appropriate place
		gsClient.logIn();

		gsm.updtate(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
	}

    @Override
    public void gsOnSessionActive() {

    }

    @Override
    public void gsOnSessionInactive() {

    }

    @Override
    public void gsShowErrorToUser(GsErrorType et, String msg, Throwable t) {

    }
}
