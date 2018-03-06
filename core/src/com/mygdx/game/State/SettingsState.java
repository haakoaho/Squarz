package com.mygdx.game.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Lucas on 05/03/2018.
 */

class SettingsState extends State {

    public SettingsState(GameStateManager gsm) {
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

