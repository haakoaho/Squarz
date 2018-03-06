package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

/**
 * Created by Lucas on 05/03/2018.
 */

class Settings extends State {
    SpriteBatch batch;
    private Sprite history;

    public Settings(GameStateManager gsm) {
        super(gsm);
        batch = new SpriteBatch();
        Gdx.gl.glClearColor(0,0,0, 1);
        history = new Sprite(new Texture(Gdx.files.internal("history.png")));
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        batch.begin();
        batch.draw(history,0,0);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}

