package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import com.mygdx.game.view.Preferences.SetAILevel;
import com.mygdx.game.view.Preferences.SetAITimer;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 06/03/2018.
 */

public class AIPreferences extends State {
    private Icon setAILevel, setTimer, setBonuses, play;
    private PreferencesSettings setting;

    public AIPreferences(GameStateManager gsm){
        super(gsm);
        setAILevel = new Icon(new Texture(Gdx.files.internal("ai_settings/setAILevel.png")),0,0);
        setTimer = new Icon(new Texture(Gdx.files.internal("ai_settings/setTimer.png")),0,0);
        setBonuses = new Icon(new Texture(Gdx.files.internal("ai_settings/setBonus.png")),0,0);
        play = new Icon(new Texture(Gdx.files.internal("ai_settings/play.png")),0,0);
        setting = new PreferencesSettings();

        setAILevel.setPosx(WIDTH/2-setAILevel.getTexture().getWidth()/2);
        setAILevel.setPosy(HEIGHT*4/5-setAILevel.getTexture().getHeight()/2);
        setTimer.setPosx(WIDTH/2-setTimer.getTexture().getWidth()/2);
        setTimer.setPosy(HEIGHT*3/5-setTimer.getTexture().getHeight()/2);
        setBonuses.setPosx(WIDTH/2-setBonuses.getTexture().getWidth()/2);
        setBonuses.setPosy(HEIGHT*2/5-setBonuses.getTexture().getHeight()/2);
        play.setPosx(WIDTH/2-play.getTexture().getWidth()/2);
        play.setPosy(HEIGHT/5-play.getTexture().getHeight()/2);
    }

    //used once a setting is changed
    public AIPreferences(GameStateManager gsm, PreferencesSettings setting){
        super(gsm);
        setAILevel = new Icon(new Texture(Gdx.files.internal("ai_settings/setAILevel.png")),0,0);
        setTimer = new Icon(new Texture(Gdx.files.internal("ai_settings/setTimer.png")),0,0);
        setBonuses = new Icon(new Texture(Gdx.files.internal("ai_settings/setBonus.png")),0,0);
        play = new Icon(new Texture(Gdx.files.internal("ai_settings/play.png")),0,0);
        this.setting = setting;

        setAILevel.setPosx(WIDTH/2-setAILevel.getTexture().getWidth()/2);
        setAILevel.setPosy(HEIGHT*4/5-setAILevel.getTexture().getHeight()/2);
        setTimer.setPosx(WIDTH/2-setTimer.getTexture().getWidth()/2);
        setTimer.setPosy(HEIGHT*3/5-setTimer.getTexture().getHeight()/2);
        setBonuses.setPosx(WIDTH/2-setBonuses.getTexture().getWidth()/2);
        setBonuses.setPosy(HEIGHT*2/5-setBonuses.getTexture().getHeight()/2);
        play.setPosx(WIDTH/2-play.getTexture().getWidth()/2);
        play.setPosy(HEIGHT/5-play.getTexture().getHeight()/2);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
<<<<<<< HEAD
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (setAILevel.isIn(x,y)) {
                gsm.set(new SetAILevel(gsm, setting));
            }
            if (setTimer.isIn(x,y)) {
                gsm.set(new SetAITimer(gsm, setting));
            }
            if (play.isIn(x,y)) {
=======
            if (Gdx.input.getY() < Gdx.graphics.getHeight() / 4 + 30) {
                gsm.set(new SetAILevel(gsm, setting));
            }
            if (Gdx.input.getY() < Gdx.graphics.getHeight() / 2 && Gdx.input.getY() > Gdx.graphics.getHeight() / 4 + 30) {
                gsm.set(new SetAITimer(gsm, setting));
            }
            if (Gdx.input.getY() > Gdx.graphics.getHeight() * 3 / 4) {
>>>>>>> mathieu
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
        sb.draw(setAILevel.getTexture(),setAILevel.getPosx() ,setAILevel.getPosy() );
        sb.draw(setTimer.getTexture(),setTimer.getPosx() ,setTimer.getPosy() );
        sb.draw(setBonuses.getTexture(),setBonuses.getPosx() ,setBonuses.getPosy() );
        sb.draw(play.getTexture(),play.getPosx(),play.getPosy());
        sb.end();

    }

    @Override
    public void dispose() {
<<<<<<< HEAD
        setAILevel.getTexture().dispose();
        setTimer.getTexture().dispose();
        setBonuses.getTexture().dispose();
        play.getTexture().dispose();
=======
>>>>>>> mathieu
    }
}
