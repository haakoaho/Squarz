package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import com.mygdx.game.view.AIPreferences;

import java.util.WeakHashMap;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 12/03/2018.
 */

public class SetAITimer extends State {
    private PreferencesSettings set;
    private Icon add, delete, timer;


    public SetAITimer(GameStateManager gsm, PreferencesSettings setting){
        super(gsm);
        add = new Icon(new Texture(Gdx.files.internal("add.png")),0,0);
        delete = new Icon(new Texture(Gdx.files.internal("delete.png")),0,0);
        timer = new Icon(new Texture(Gdx.files.internal("ai_settings/setTimer.png")),0,0);
        set = setting;

        add.setPosx(WIDTH/2-add.getTexture().getWidth()/2);
        add.setPosy(HEIGHT*2/3-add.getTexture().getHeight()/2);
        delete.setPosx(WIDTH/2-add.getTexture().getWidth()/2);
        delete.setPosy(HEIGHT/3-add.getTexture().getHeight()/2);
        timer.setPosx(WIDTH/2-timer.getTexture().getWidth()/2);
        timer.setPosy(HEIGHT/2 - timer.getTexture().getHeight()/2);
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(add.isIn(x,y)) { //add
                if (this.set.getTimer().getDuration() != 60) {
                    this.set.getTimer().increment();
                }
            }
            if(delete.isIn(x,y)) { //delete
                if (this.set.getTimer().getDuration() != 30) {
                    this.set.getTimer().decrement();
                }
            }
            if(timer.isIn(x,y)){
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

        sb.draw(add.getTexture(), add.getPosx(), add.getPosy());
        sb.draw(delete.getTexture(),delete.getPosx(), delete.getPosy());

        if(this.set.getTimer().getDuration() == 30){
            sb.draw(timer.getTexture(),timer.getPosx() ,timer.getPosy());
        }
        if(this.set.getTimer().getDuration() == 45){
            sb.draw(timer.getTexture(),timer.getPosx()-timer.getTexture().getWidth()/2 -5 ,timer.getPosy());
            sb.draw(timer.getTexture(),timer.getPosx()+timer.getTexture().getWidth()/2 +5,timer.getPosy());
        }
        if(this.set.getTimer().getDuration() == 60){
            sb.draw(timer.getTexture(),timer.getPosx()-timer.getTexture().getWidth()-10 ,timer.getPosy());
            sb.draw(timer.getTexture(),timer.getPosx() ,timer.getPosy());
            sb.draw(timer.getTexture(),timer.getPosx()+timer.getTexture().getWidth()+10 ,timer.getPosy());
        }


        sb.end();
    }

    @Override
    public void dispose() {

    }
}
