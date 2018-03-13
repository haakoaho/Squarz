package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.State;
import com.mygdx.game.view.AIPreferences;

import java.util.WeakHashMap;

/**
 * Created by mathi on 12/03/2018.
 */

public class SetAITimer extends State {
    private PreferencesSettings set;
    private Texture add, delete, timer, background;


    public SetAITimer(GameStateManager gsm, PreferencesSettings setting){
        super(gsm);
        this.set = setting;
        this.add = new Texture(Gdx.files.internal("add.png"));
        this.delete = new Texture(Gdx.files.internal("delete.png"));
        this.timer = new Texture(Gdx.files.internal("settingTimer.png"));
        this.background = new Texture(Gdx.files.internal("background.png"));
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            if(Gdx.input.getY()<Gdx.graphics.getHeight()/3+add.getHeight()/2) { //add
                if (this.set.getTimer().getDuration() != 60) {
                    this.set.getTimer().increment();
                }
            }
            if(Gdx.input.getY()>Gdx.graphics.getHeight()*2/3-delete.getHeight()/2) { //delete
                if (this.set.getTimer().getDuration() != 30) {
                    this.set.getTimer().decrement();
                }
            }
            if(Gdx.input.getY()>Gdx.graphics.getHeight()/3+add.getHeight()/2 && Gdx.input.getY()<Gdx.graphics.getHeight()*2/3-delete.getHeight()/2){
                gsm.set(new AIPreferences(gsm, set));
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
        sb.draw(add, Gdx.graphics.getWidth()/2-add.getWidth()/2, Gdx.graphics.getHeight()*2/3-add.getHeight()/2);
        sb.draw(delete, Gdx.graphics.getWidth()/2-add.getWidth()/2, Gdx.graphics.getHeight()/3-add.getHeight()/2);

        if(this.set.getTimer().getDuration() == 30){
            sb.draw(timer, Gdx.graphics.getWidth()/2-timer.getWidth()/2, Gdx.graphics.getHeight()/2 - timer.getHeight()/2);
        }
        if(this.set.getTimer().getDuration() == 45){
            sb.draw(timer, Gdx.graphics.getWidth()*2/5-timer.getWidth()/2-10, Gdx.graphics.getHeight()/2 - timer.getHeight()/2);
            sb.draw(timer, Gdx.graphics.getWidth()*3/5-timer.getWidth()/2+10, Gdx.graphics.getHeight()/2 - timer.getHeight()/2);

        }
        if(this.set.getTimer().getDuration() == 60){
            sb.draw(timer, Gdx.graphics.getWidth()/3-timer.getWidth()/2-10, Gdx.graphics.getHeight()/2 - timer.getHeight()/2);
            sb.draw(timer, Gdx.graphics.getWidth()/2-timer.getWidth()/2, Gdx.graphics.getHeight()/2 - timer.getHeight()/2);
            sb.draw(timer, Gdx.graphics.getWidth()*2/3-timer.getWidth()/2+10, Gdx.graphics.getHeight()/2 - timer.getHeight()/2);
        }


        sb.end();
    }

    @Override
    public void dispose() {

    }
}
