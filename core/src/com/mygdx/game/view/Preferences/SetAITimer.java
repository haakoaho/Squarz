package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 12/03/2018.
 */

public class SetAITimer extends State {
    private PreferencesSettings set;
    private Icon add, delete, countDown;


    public SetAITimer(GameStateManager gsm, PreferencesSettings setting){
        super(gsm);
        add = new Icon(new Texture(Gdx.files.internal("add.png")),0,0);
        delete = new Icon(new Texture(Gdx.files.internal("delete.png")),0,0);
        countDown = new Icon(new Texture(Gdx.files.internal("ai_settings/setTimer.png")),0,0);
        set = setting;

        add.setPosX(WIDTH/2-add.getTexture().getWidth()/2);
        add.setPosY(HEIGHT*2/3-add.getTexture().getHeight()/2);
        delete.setPosX(WIDTH/2-add.getTexture().getWidth()/2);
        delete.setPosY(HEIGHT/3-add.getTexture().getHeight()/2);
        countDown.setPosX(WIDTH/2- countDown.getTexture().getWidth()/2);
        countDown.setPosY(HEIGHT/2 - countDown.getTexture().getHeight()/2);
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(add.contains(x,y)) { //add
                if (this.set.getCountDown().getWorldTimer() != 60) {
                    this.set.getCountDown().increaseTime();
                }
            }
            if(delete.contains(x,y)) { //delete
                if (this.set.getCountDown().getWorldTimer() != 30) {
                    this.set.getCountDown().decreaseTime();
                }
            }
            if(countDown.contains(x,y)){
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, set));
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

        sb.draw(add.getTexture(), add.getPosX(), add.getPosY());
        sb.draw(delete.getTexture(),delete.getPosX(), delete.getPosY());

        if(this.set.getCountDown().getWorldTimer() == 30){
            sb.draw(countDown.getTexture(), countDown.getPosX() , countDown.getPosY());
        }
        if(this.set.getCountDown().getWorldTimer() == 45){
            sb.draw(countDown.getTexture(), countDown.getPosX()- countDown.getTexture().getWidth()/2 -5 , countDown.getPosY());
            sb.draw(countDown.getTexture(), countDown.getPosX()+ countDown.getTexture().getWidth()/2 +5, countDown.getPosY());
        }
        if(this.set.getCountDown().getWorldTimer() == 60){
            sb.draw(countDown.getTexture(), countDown.getPosX()- countDown.getTexture().getWidth()-10 , countDown.getPosY());
            sb.draw(countDown.getTexture(), countDown.getPosX() , countDown.getPosY());
            sb.draw(countDown.getTexture(), countDown.getPosX()+ countDown.getTexture().getWidth()+10 , countDown.getPosY());
        }


        sb.end();
    }

    @Override
    public void dispose() {

    }
}
