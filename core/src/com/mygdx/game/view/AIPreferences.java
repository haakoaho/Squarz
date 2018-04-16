package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import com.mygdx.game.view.Preferences.SetAILevel;
import com.mygdx.game.view.Preferences.SetAITimer;
import com.mygdx.game.view.beginning.Pref;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font2;
import static com.mygdx.game.Squarz.font3;
import static com.mygdx.game.Squarz.format;

/**
 * Created by mathi on 06/03/2018.
 */

public class AIPreferences extends State {
    private Icon setAILevel, setTimer, setBonuses, play, back;
    private PreferencesSettings setting;
    private CountDown countDown;
    private GlyphLayout AILevel, Timer, Bonuses;

    public AIPreferences(GameStateManager gsm){
        super(gsm);
        setAILevel = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setAILevel.png")),0,0);
        setTimer = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setTimer.png")),0,0);
        setBonuses = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setBonus.png")),0,0);
        play = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/play.png")),0,0);
        back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);

        setting = new PreferencesSettings();
        countDown = new CountDown(60);

        AILevel = new GlyphLayout(font2, this.setting.getStringLevel());
        Timer = new GlyphLayout(font2, this.countDown.getWorldTimer()+" seconds");
        Bonuses = new GlyphLayout(font2, "None");

        setAILevel.setPosX(WIDTH/4-setAILevel.getTexture().getWidth()/2);
        setAILevel.setPosY(HEIGHT*4/5-setAILevel.getTexture().getHeight()/2);
        setAILevel.setLegend("Choose AI Level");
        setTimer.setPosX(WIDTH/4-setTimer.getTexture().getWidth()/2);
        setTimer.setPosY(HEIGHT*3/5-setTimer.getTexture().getHeight()/2);
        setTimer.setLegend("Choose the time");
        setBonuses.setPosX(WIDTH/4-setBonuses.getTexture().getWidth()/2);
        setBonuses.setPosY(HEIGHT*2/5-setBonuses.getTexture().getHeight()/2);
        setBonuses.setLegend("Choose your bonuses");
        play.setPosX(WIDTH/2-play.getTexture().getWidth()/2);
        play.setPosY(HEIGHT/8-play.getTexture().getHeight()/2);
        back.setPosX(back.getTexture().getWidth()/2);
        back.setPosY(back.getTexture().getHeight()/2);


    }

    //used once a setting is changed
    public AIPreferences(GameStateManager gsm, PreferencesSettings setting, CountDown countDown){

        super(gsm);
        setAILevel = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setAILevel.png")),0,0);
        setTimer = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setTimer.png")),0,0);
        setBonuses = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setBonus.png")),0,0);
        play = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/play.png")),0,0);
        back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);
        this.setting = setting;
        this.countDown = countDown;

        AILevel = new GlyphLayout(font2, this.setting.getStringLevel());
        Timer = new GlyphLayout(font2, this.countDown.getWorldTimer()+" seconds");
        Bonuses = new GlyphLayout(font2, "None");

        setAILevel.setPosX(WIDTH/4-setAILevel.getTexture().getWidth()/2);
        setAILevel.setPosY(HEIGHT*4/5-setAILevel.getTexture().getHeight()/2);
        setAILevel.setLegend("Choose AI Level");
        setTimer.setPosX(WIDTH/4-setTimer.getTexture().getWidth()/2);
        setTimer.setPosY(HEIGHT*3/5-setTimer.getTexture().getHeight()/2);
        setTimer.setLegend("Choose the time");
        setBonuses.setPosX(WIDTH/4-setBonuses.getTexture().getWidth()/2);
        setBonuses.setPosY(HEIGHT*2/5-setBonuses.getTexture().getHeight()/2);
        setBonuses.setLegend("Choose your bonuses");
        play.setPosX(WIDTH/2-play.getTexture().getWidth()/2);
        play.setPosY(HEIGHT/8-play.getTexture().getHeight()/2);
        back.setPosX(back.getTexture().getWidth()/2);
        back.setPosY(back.getTexture().getHeight()/2);
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
            if (back.contains(x, y)){
                gsm.set(new Pref(gsm));
            }
            if(setBonuses.contains(x, y)){
                gsm.set(new BonusSelection(gsm, setting, countDown));
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
        font3.draw(sb,this.setAILevel.getLegend(), setAILevel.getPosX() + setAILevel.getTexture().getWidth()/2 - this.setAILevel.getLegend().width/2, setAILevel.getPosY() - 2*this.setAILevel.getLegend().height);
        font2.draw(sb,this.AILevel, 3*WIDTH/4 - this.AILevel.width/2, setAILevel.getPosY() + setAILevel.getTexture().getHeight()/2 - this.AILevel.height/2);
        sb.draw(setTimer.getTexture(),setTimer.getPosX() ,setTimer.getPosY() );
        font3.draw(sb,this.setTimer.getLegend(), setTimer.getPosX() + setTimer.getTexture().getWidth()/2 - this.setTimer.getLegend().width/2, setTimer.getPosY() - 2*this.setTimer.getLegend().height);
        font2.draw(sb,this.Timer, 3*WIDTH/4 - this.Timer.width/2, setTimer.getPosY() + setTimer.getTexture().getHeight()/2 - this.Timer.height/2);
        sb.draw(setBonuses.getTexture(),setBonuses.getPosX() ,setBonuses.getPosY() );
        font3.draw(sb,this.setBonuses.getLegend(), setBonuses.getPosX() + setBonuses.getTexture().getWidth()/2 - this.setBonuses.getLegend().width/2, setBonuses.getPosY() - 2*this.setBonuses.getLegend().height);
        font2.draw(sb,this.Bonuses, 3*WIDTH/4 - this.Bonuses.width/2, setBonuses.getPosY() + setBonuses.getTexture().getHeight()/2 - this.Bonuses.height/2);
        sb.draw(play.getTexture(),play.getPosX(),play.getPosY());
        sb.draw(back.getTexture(), back.getPosX(), back.getPosX());
        sb.end();

    }

    @Override
    public void dispose() {
    }
}
