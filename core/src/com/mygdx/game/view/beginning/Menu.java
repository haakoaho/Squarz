package com.mygdx.game.view.beginning;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

public class Menu extends State {
    private Icon play, settings, history;

    public Menu(GameStateManager gsm) {
        super(gsm);
        play = new Icon(new Texture(Gdx.files.internal("menu/play.png")),0,0);
        settings = new Icon(new Texture(Gdx.files.internal("menu/settings.png")),0,0);
        history = new Icon(new Texture(Gdx.files.internal("menu/history.png")),0,0);

        play.setPosx(WIDTH/2-play.getTexture().getWidth()/2);
        play.setPosy(HEIGHT*3/4-play.getTexture().getHeight()/2);
        settings.setPosx(WIDTH/2-settings.getTexture().getWidth()/2);
        settings.setPosy(HEIGHT*2/4-settings.getTexture().getHeight()/2);
        history.setPosx(WIDTH/2-history.getTexture().getWidth()/2);
        history.setPosy(HEIGHT*1/4-history.getTexture().getHeight()/2);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (play.contains(x,y)) {
                gsm.set(new Pref(gsm));
                dispose();
            }
            if(settings.contains(x,y)){
                gsm.set(new Settings(gsm));
                dispose();
            }
            if(history.contains(x,y)){
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
        sb.draw(play.getTexture(), play.getPosx(), play.getPosy());
        sb.draw(settings.getTexture(), settings.getPosx(), settings.getPosy());
        sb.draw(history.getTexture(), history.getPosx(), history.getPosy());
        sb.end();
    }

    @Override
    public void dispose() {
        play.getTexture().dispose();
        settings.getTexture().dispose();
        history.getTexture().dispose();
    }


}

