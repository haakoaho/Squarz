package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

/**
 * Created by Lucas on 05/03/2018.
 */

class Settings extends State {

    public Settings(GameStateManager gsm) {
        super(gsm);
        Gdx.gl.glClearColor(0,0,0, 1);
    }

    @Override
    public void handleInput() {
        //ieuvghaiuegvitule
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
    }

    @Override
    public void dispose() {

    }
}

