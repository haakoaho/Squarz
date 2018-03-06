package com.mygdx.game.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.State.Preferences.AISetLevelState;

/**
 * Created by mathi on 06/03/2018.
 */

public class AIPreferencesState extends State{
    private Texture background;
    private Texture setAILevel, setTimer, setBonuses, play;

    public AIPreferencesState(GameStateManager gsm){
        super(gsm);

        this.background = new Texture(Gdx.files.internal("background.png"));
        this.setAILevel = new Texture(Gdx.files.internal("setAILevel.png"));
        this.setTimer = new Texture(Gdx.files.internal("setTimer.png"));
        this.setBonuses = new Texture(Gdx.files.internal("setBonus.png"));
        this.play = new Texture(Gdx.files.internal("play.png"));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            if(Gdx.input.getY()<Gdx.graphics.getHeight()/4){
                gsm.set(new AISetLevelState(gsm));
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
        sb.draw(setAILevel, Gdx.graphics.getWidth()/2-setAILevel.getWidth()/2, Gdx.graphics.getHeight()*4/5-setAILevel.getHeight()/2);
        sb.draw(setTimer, Gdx.graphics.getWidth()/2-setTimer.getWidth()/2, Gdx.graphics.getHeight()*3/5-setTimer.getHeight()/2);
        sb.draw(setBonuses, Gdx.graphics.getWidth()/2-setBonuses.getWidth()/2, Gdx.graphics.getHeight()*2/5-setBonuses.getHeight()/2);
        sb.draw(play,Gdx.graphics.getWidth()/2-play.getWidth()/2, Gdx.graphics.getHeight()/5-play.getHeight()/2);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
