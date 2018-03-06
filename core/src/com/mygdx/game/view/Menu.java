package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

public class Menu extends State {
    SpriteBatch batch;
    Texture text;

    public Menu(GameStateManager gsm) {
        super(gsm);
        batch = new SpriteBatch();
        text = new Texture(Gdx.files.internal("badlogic.jpg"));
    }

    @Override
    public void handleInput() {
        gsm.push(new Settings(gsm));
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        batch.begin();
        batch.draw(text,0,0);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}

