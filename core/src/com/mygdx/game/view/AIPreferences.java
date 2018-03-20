package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import com.mygdx.game.view.Preferences.SetAILevel;
import com.mygdx.game.view.Preferences.SetAITimer;
import com.mygdx.game.view.beginning.Menu;
import com.mygdx.game.view.beginning.Pref;
import com.mygdx.game.view.beginning.Settings;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 06/03/2018.
 */

public class AIPreferences extends State {
    private Icon setAILevel, setTimer, setBonuses, play, back;
    private PreferencesSettings setting;
    private CountDown countDown;

    public AIPreferences(GameStateManager gsm){
        super(gsm);
        this.setAILevel = new Icon(new Texture(Gdx.files.internal("ai_settings/setAILevel.png")),0,0);
        this.setTimer = new Icon(new Texture(Gdx.files.internal("ai_settings/setTimer.png")),0,0);
        this.setBonuses = new Icon(new Texture(Gdx.files.internal("ai_settings/setBonus.png")),0,0);
        this.play = new Icon(new Texture(Gdx.files.internal("ai_settings/play.png")),0,0);
        this.setting = new PreferencesSettings();
        this.countDown = new CountDown();
        this.back = new Icon(new Texture(Gdx.files.internal("back.png")),0,0);

        this.setAILevel.setPosX(WIDTH/2-setAILevel.getTexture().getWidth()/2);
        this.setAILevel.setPosY(HEIGHT*4/5-setAILevel.getTexture().getHeight()/2);
        this.setTimer.setPosX(WIDTH/2-setTimer.getTexture().getWidth()/2);
        this.setTimer.setPosY(HEIGHT*3/5-setTimer.getTexture().getHeight()/2);
        this.setBonuses.setPosX(WIDTH/2-setBonuses.getTexture().getWidth()/2);
        this.setBonuses.setPosY(HEIGHT*2/5-setBonuses.getTexture().getHeight()/2);
        this.play.setPosX(WIDTH/2-play.getTexture().getWidth()/2);
        this.play.setPosY(HEIGHT/5-play.getTexture().getHeight()/2);
        this.back.setPosX(back.getTexture().getWidth()/2);
        this.back.setPosY(back.getTexture().getHeight()/2);
    }

    //used once a setting is changed
    public AIPreferences(GameStateManager gsm, PreferencesSettings setting, CountDown countDown){
        super(gsm);
        this.setAILevel = new Icon(new Texture(Gdx.files.internal("ai_settings/setAILevel.png")),0,0);
        this.setTimer = new Icon(new Texture(Gdx.files.internal("ai_settings/setTimer.png")),0,0);
        this.setBonuses = new Icon(new Texture(Gdx.files.internal("ai_settings/setBonus.png")),0,0);
        this.play = new Icon(new Texture(Gdx.files.internal("ai_settings/play.png")),0,0);
        this.setting = setting;
        this.countDown = countDown;
        this.back = new Icon(new Texture(Gdx.files.internal("back.png")),0,0);

        this.setAILevel.setPosX(WIDTH/2-setAILevel.getTexture().getWidth()/2);
        this.setAILevel.setPosY(HEIGHT*4/5-setAILevel.getTexture().getHeight()/2);
        this.setTimer.setPosX(WIDTH/2-setTimer.getTexture().getWidth()/2);
        this.setTimer.setPosY(HEIGHT*3/5-setTimer.getTexture().getHeight()/2);
        this.setBonuses.setPosX(WIDTH/2-setBonuses.getTexture().getWidth()/2);
        this.setBonuses.setPosY(HEIGHT*2/5-setBonuses.getTexture().getHeight()/2);
        this.play.setPosX(WIDTH/2-play.getTexture().getWidth()/2);
        this.play.setPosY(HEIGHT/5-play.getTexture().getHeight()/2);
        this.back.setPosX(back.getTexture().getWidth()/2);
        this.back.setPosY(back.getTexture().getHeight()/2);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (setAILevel.contains(x,y)) {
                gsm.set(new SetAILevel(gsm, setting, countDown));
            }
            if (setTimer.contains(x,y)) {
                gsm.set(new SetAITimer(gsm, setting, countDown));
            }
            if (play.contains(x,y)) {
                gsm.set(new PlayModeAi(gsm, setting, countDown));
            }
            if (back.contains(x,y)) {
                gsm.set(new Pref(gsm));
                dispose();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb){
        sb.begin();
        sb.draw(setAILevel.getTexture(),setAILevel.getPosX() ,setAILevel.getPosY() );
        sb.draw(setTimer.getTexture(),setTimer.getPosX() ,setTimer.getPosY() );
        sb.draw(setBonuses.getTexture(),setBonuses.getPosX() ,setBonuses.getPosY() );
        sb.draw(play.getTexture(),play.getPosX(),play.getPosY());
        sb.draw(back.getTexture(),back.getPosX(),back.getPosY());
        sb.end();

    }

    @Override
    public void dispose() {
    }
}
