package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

/**
 * Created by Lucas on 06/03/2018.
 */

public class Settings extends State {
    Texture texture;

    public Settings(GameStateManager gsm) {
        super(gsm);
        texture = new Texture(Gdx.files.internal("add.png"));
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(texture,100,100);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
