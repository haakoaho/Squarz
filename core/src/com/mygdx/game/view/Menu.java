package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

public class Menu extends State {
    SpriteBatch batch;
    Texture text;
    private Sprite play;
    private Sprite settings;
    private Sprite history;
    private Texture background;


    public Menu(GameStateManager gsm) {
        super(gsm);
        batch = new SpriteBatch();
        play = new Sprite(new Texture(Gdx.files.internal("play.png")));
        play.setCenter(cam.viewportWidth);
        settings = new Sprite(new Texture(Gdx.files.internal("settings.png")));
        settings.setCenter();
        history = new Sprite(new Texture(Gdx.files.internal("history.png")));
        history.setCenter();
    }

    @Override
    public void handleInput() {
            if (Gdx.input.justTouched()) {
                if (play.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    gsm.set(new Settings(gsm));
                }

                if (settings.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    gsm.set(new Settings(gsm));
                }

                if (history.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    gsm.set(new Settings(gsm));
                }

               // System.out.println(Gdx.input.getX() + "_____________" +Gdx.input.getY() );

            }


    }

    @Override
    public void update(float dt) {

        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(play,0,100);
        batch.draw(history, 100,100);
        batch.draw(settings, 200,200);
        batch.end();
    }

    @Override
    public void dispose() {
        text.dispose();
    }


}

