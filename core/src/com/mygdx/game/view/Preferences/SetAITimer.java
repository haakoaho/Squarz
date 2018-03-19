package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import com.mygdx.game.view.beginning.Menu;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 12/03/2018.
 */

public class SetAITimer extends State {
    private PreferencesSettings set;
    private Icon add, delete, timer, back;


    public SetAITimer(GameStateManager gsm, PreferencesSettings setting){
        super(gsm);

        add = new Icon(new Texture(Gdx.files.internal("add.png")),0,0);
        delete = new Icon(new Texture(Gdx.files.internal("delete.png")),0,0);
        timer = new Icon(new Texture(Gdx.files.internal("ai_settings/setTimer.png")),0,0);
        back = new Icon(new Texture(Gdx.files.internal("back.png")),0,0);
        set = setting;

        back.setPosX(back.getTexture().getWidth()/2);
        back.setPosY(back.getTexture().getHeight()/2);
        add.setPosX(WIDTH/2-add.getTexture().getWidth()/2);
        add.setPosY(HEIGHT*2/3-add.getTexture().getHeight()/2);
        delete.setPosX(WIDTH/2-add.getTexture().getWidth()/2);
        delete.setPosY(HEIGHT/3-add.getTexture().getHeight()/2);
        timer.setPosX(WIDTH/2-timer.getTexture().getWidth()/2);
        timer.setPosY(HEIGHT/2 - timer.getTexture().getHeight()/2);
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(add.contains(x,y)) { //add
                if (this.set.getTimer().getDuration() != 60) {
                    this.set.getTimer().increment();
                }
            }
            if(delete.contains(x,y)) { //delete
                if (this.set.getTimer().getDuration() != 30) {
                    this.set.getTimer().decrement();
                }
            }
            if(timer.contains(x,y)){
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, set));
            }
            if (back.contains(x,y)) {
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, set));
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

        sb.draw(add.getTexture(), add.getPosX(), add.getPosY());
        sb.draw(delete.getTexture(),delete.getPosX(), delete.getPosY());

        if(this.set.getTimer().getDuration() == 30){
            sb.draw(timer.getTexture(),timer.getPosX() ,timer.getPosY());
        }
        if(this.set.getTimer().getDuration() == 45){
            sb.draw(timer.getTexture(),timer.getPosX()-timer.getTexture().getWidth()/2 -5 ,timer.getPosY());
            sb.draw(timer.getTexture(),timer.getPosX()+timer.getTexture().getWidth()/2 +5,timer.getPosY());
        }
        if(this.set.getTimer().getDuration() == 60){
            sb.draw(timer.getTexture(),timer.getPosX()-timer.getTexture().getWidth()-10 ,timer.getPosY());
            sb.draw(timer.getTexture(),timer.getPosX() ,timer.getPosY());
            sb.draw(timer.getTexture(),timer.getPosX()+timer.getTexture().getWidth()+10 ,timer.getPosY());
        }
        sb.draw(back.getTexture(),back.getPosX(),back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
