package com.mygdx.game.view.beginning;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

public class Menu extends State {
    private Icon play, settings, history;

    public Menu(GameStateManager gsm) {
        super(gsm);

        this.play = new Icon(new Texture(Gdx.files.internal("menu/play.png")),0,0);
        this.settings = new Icon(new Texture(Gdx.files.internal("menu/settings.png")),0,0);
        this.history = new Icon(new Texture(Gdx.files.internal("menu/history.png")),0,0);

        this.play.setPosX(WIDTH/2-play.getTexture().getWidth()/2);
        this.play.setPosY(HEIGHT*3/4-play.getTexture().getHeight()/2);
        this.settings.setPosX(WIDTH/2-settings.getTexture().getWidth()/2);
        this.settings.setPosY(HEIGHT*2/4-settings.getTexture().getHeight()/2);
        this.history.setPosX(WIDTH/2-history.getTexture().getWidth()/2);
        this.history.setPosY(HEIGHT*1/4-history.getTexture().getHeight()/2);
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
                gsm.set(new com.mygdx.game.view.beginning.Settings(gsm));
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
        sb.draw(play.getTexture(), play.getPosX(), play.getPosY());
        sb.draw(settings.getTexture(), settings.getPosX(), settings.getPosY());
        sb.draw(history.getTexture(), history.getPosX(), history.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {
        play.getTexture().dispose();
        settings.getTexture().dispose();
        history.getTexture().dispose();
    }
}