package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.CountDown;
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
    private CountDown countDown;
    private Icon add, delete, countDownIcon,back;


    public SetAITimer(GameStateManager gsm, PreferencesSettings setting, CountDown countDown){
        super(gsm);

        add = new Icon(new Texture(Gdx.files.internal("add.png")),0,0);
        delete = new Icon(new Texture(Gdx.files.internal("delete.png")),0,0);
        back = new Icon(new Texture(Gdx.files.internal("back.png")),0,0);
        countDownIcon = new Icon(new Texture(Gdx.files.internal("ai_settings/setTimer.png")),0,0);
        set = setting;
        this.countDown = countDown;


        back.setPosX(back.getTexture().getWidth()/2);
        back.setPosY(back.getTexture().getHeight()/2);
        add.setPosX(WIDTH/2-add.getTexture().getWidth()/2);
        add.setPosY(HEIGHT*2/3-add.getTexture().getHeight()/2);
        delete.setPosX(WIDTH/2-add.getTexture().getWidth()/2);
        delete.setPosY(HEIGHT/3-add.getTexture().getHeight()/2);
        countDownIcon.setPosX(WIDTH/2- countDownIcon.getTexture().getWidth()/2);
        countDownIcon.setPosY(HEIGHT/2 - countDownIcon.getTexture().getHeight()/2);
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(add.contains(x,y)) { //add
                if (this.countDown.getWorldTimer() != 60) {
                    this.countDown.increaseTime();
                }
            }
            if(delete.contains(x,y)) { //delete
                if (this.countDown.getWorldTimer() != 30) {
                    this.countDown.decreaseTime();
                }
            }
            if(countDownIcon.contains(x,y)){ //go back
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, set, countDown));
                dispose();
            }
            if (back.contains(x,y)) {
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, set, countDown));
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

        drawAccurateTexture(sb);

        sb.end();
    }

    @Override
    public void dispose() {
    }

    public void drawAccurateTexture(SpriteBatch sb){
        if(countDown.getWorldTimer() == 30){
            sb.draw(countDownIcon.getTexture(), countDownIcon.getPosX() , countDownIcon.getPosY());
        }
        if(countDown.getWorldTimer() == 45){
            sb.draw(countDownIcon.getTexture(), countDownIcon.getPosX()- countDownIcon.getTexture().getWidth()/2 -5 , countDownIcon.getPosY());
            sb.draw(countDownIcon.getTexture(), countDownIcon.getPosX()+ countDownIcon.getTexture().getWidth()/2 +5, countDownIcon.getPosY());
        }
        if(countDown.getWorldTimer() == 60){
            sb.draw(countDownIcon.getTexture(), countDownIcon.getPosX()- countDownIcon.getTexture().getWidth()-10 , countDownIcon.getPosY());
            sb.draw(countDownIcon.getTexture(), countDownIcon.getPosX() , countDownIcon.getPosY());
            sb.draw(countDownIcon.getTexture(), countDownIcon.getPosX()+ countDownIcon.getTexture().getWidth()+10 , countDownIcon.getPosY());
        }
    }
}