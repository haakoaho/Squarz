package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.State;
import com.mygdx.game.view.Preferences.SetAILevel;
import com.mygdx.game.view.Preferences.SetAITimer;
import com.mygdx.game.view.beginning.Menu;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 06/03/2018.
 */

public class AIPreferences extends State {
    private Texture setAILevel, setTimer, setBonuses, play, add, delete;
    private PreferencesSettings setting;

    public AIPreferences(GameStateManager gsm){
        super(gsm);

        this.setAILevel = new Texture(Gdx.files.internal("ai_settings/setAILevel.png"));
        this.setTimer = new Texture(Gdx.files.internal("ai_settings/setTimer.png"));
        this.setBonuses = new Texture(Gdx.files.internal("ai_settings/setBonus.png"));
        this.play = new Texture(Gdx.files.internal("ai_settings/play.png"));

        this.setting = new PreferencesSettings();
    }

    //used once a setting is changed
    public AIPreferences(GameStateManager gsm, PreferencesSettings setting){
        super(gsm);

        this.setAILevel = new Texture(Gdx.files.internal("ai_settings/setAILevel.png"));
        this.setTimer = new Texture(Gdx.files.internal("ai_settings/setTimer.png"));
        this.setBonuses = new Texture(Gdx.files.internal("ai_settings/setBonus.png"));
        this.play = new Texture(Gdx.files.internal("ai_settings/play.png"));

        this.setting = setting;
        this.setAILevel = new Texture(Gdx.files.internal("ai_settings/setAILevel.png"));
        this.setTimer = new Texture(Gdx.files.internal("ai_settings/setTimer.png"));
        this.setBonuses = new Texture(Gdx.files.internal("ai_settings/setBonus.png"));
        this.play = new Texture(Gdx.files.internal("menu/play.png"));
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getY() < Gdx.graphics.getHeight() / 4 + setAILevel.getHeight() / 2) {
                gsm.set(new SetAILevel(gsm, setting));
            }
            if (Gdx.input.getY() < Gdx.graphics.getHeight() / 2 + setTimer.getHeight() / 2 && Gdx.input.getY() > Gdx.graphics.getHeight() / 4 + setTimer.getHeight() / 2) {
                gsm.set(new SetAITimer(gsm, setting));
            }
            if (Gdx.input.getY() > Gdx.graphics.getHeight() *3 / 4 + play.getHeight() / 2) {
                gsm.set(new PlayModeAi(gsm, setting));
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
        sb.draw(setAILevel, WIDTH/2-setAILevel.getWidth()/2, HEIGHT*4/5-setAILevel.getHeight()/2);
        sb.draw(setTimer, WIDTH/2-setTimer.getWidth()/2, HEIGHT*3/5-setTimer.getHeight()/2);
        sb.draw(setBonuses, WIDTH/2-setBonuses.getWidth()/2, HEIGHT*2/5-setBonuses.getHeight()/2);
        sb.draw(play,WIDTH/2-play.getWidth()/2, HEIGHT/5-play.getHeight()/2);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
