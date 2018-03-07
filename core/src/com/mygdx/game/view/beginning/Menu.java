package com.mygdx.game.view.beginning;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

public class Menu extends State {
    private Texture background, play, settings, history;

    public Menu(GameStateManager gsm) {
        super(gsm);
        background = new Texture(Gdx.files.internal("background.png"));
        play = new Texture(Gdx.files.internal("menu/play.png"));
        settings = new Texture(Gdx.files.internal("menu/settings.png"));
        history = new Texture(Gdx.files.internal("menu/history.png"));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            if(Gdx.input.getY()<HEIGHT/3){
                gsm.set(new Pref(gsm));
                dispose();
            }
            if(Gdx.input.getY()<2*HEIGHT/3 && Gdx.input.getY()>=HEIGHT/3){
                gsm.set(new Settings(gsm));
                dispose();
            }
            if(Gdx.input.getY()>=2*HEIGHT/3){
                gsm.set(new History(gsm));
                dispose();
            }
        }


    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, WIDTH, HEIGHT);
        sb.draw(play, WIDTH/2-play.getWidth()/2, HEIGHT*3/4-play.getHeight()/2);
        sb.draw(settings, WIDTH/2-settings.getWidth()/2, HEIGHT*2/4-settings.getHeight()/2);
        sb.draw(history, WIDTH/2-history.getWidth()/2, HEIGHT*1/4-history.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        play.dispose();
        settings.dispose();
        history.dispose();
    }


}

