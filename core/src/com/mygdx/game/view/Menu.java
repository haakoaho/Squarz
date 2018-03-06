package com.mygdx.game.view;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

public class Menu extends State {
    private Sprite play;
    private Sprite settings;
    private Sprite history;
    private Texture background;


    public Menu(GameStateManager gsm) {
        super(gsm);
        background = new Texture(Gdx.files.internal("background.png"));
        play = new Sprite(new Texture(Gdx.files.internal("play.png")));
        settings = new Sprite(new Texture(Gdx.files.internal("settings.png")));
        history = new Sprite(new Texture(Gdx.files.internal("history.png")));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            if(Gdx.input.getY()<Gdx.graphics.getHeight()/3){
                gsm.set(new Pref(gsm));
            }
            if(Gdx.input.getY()<2*Gdx.graphics.getHeight()/3 && Gdx.input.getY()>=Gdx.graphics.getHeight()/3){
                gsm.set(new Settings(gsm));
            }
            if(Gdx.input.getY()>=2*Gdx.graphics.getHeight()/3){
                gsm.set(new History(gsm));
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
        sb.draw(background, 0, 0, Squarz.WIDTH, Squarz.HEIGHT);
        sb.draw(play, Gdx.graphics.getWidth()/2-play.getWidth()/2, Gdx.graphics.getHeight()*3/4-play.getHeight()/2);
        sb.draw(settings, Gdx.graphics.getWidth()/2-settings.getWidth()/2, Gdx.graphics.getHeight()*2/4-settings.getHeight()/2);
        sb.draw(history, Gdx.graphics.getWidth()/2-history.getWidth()/2, Gdx.graphics.getHeight()*1/4-history.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {

    }


}

