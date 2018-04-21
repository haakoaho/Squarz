package com.mygdx.game.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.control.GameStateManager;

public abstract class State {
    protected final GameStateManager gsm;


    protected State(GameStateManager gsm){
        this.gsm = gsm;
        OrthographicCamera cam = new OrthographicCamera();
        Vector3 mouse = new Vector3();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}

