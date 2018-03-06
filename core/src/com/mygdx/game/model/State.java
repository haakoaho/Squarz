package com.mygdx.game.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.control.GameStateManager;

public abstract class State {
    protected OrthographicCamera cam;
    protected Viewport viewport;
    protected Vector3 mouse;
    protected GameStateManager gsm;


    protected State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        viewport = new FitViewport(800,480,cam);
        viewport.apply();
        mouse = new Vector3();
        cam.setToOrtho(false);
        cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
    }

    public void resize(int width, int height){
        viewport.update(width,height);
        cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}

