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
import com.mygdx.game.view.Preferences.BonusSelection;
import com.mygdx.game.view.Preferences.SetAILevel;
import com.mygdx.game.view.Preferences.SetAITimer;
import com.mygdx.game.view.beginning.Pref;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font2;
import static com.mygdx.game.Squarz.font3;
import static com.mygdx.game.Squarz.format;

//CLEAN//
public class AIPreferences extends State {
    private Icon setAILevel, setTimer, setBonuses, play, back;
    private PreferencesSettings setting;
    private CountDown countDown;
    private GlyphLayout AILevel, Timer, Bonuses;

    public AIPreferences(GameStateManager gsm){ // used if the settings have not been changed yet
        super(gsm);

        this.setting = new PreferencesSettings();
        this.countDown = new CountDown(60); // initial countDown, but he can be changed if the player wants

        this.setAILevel = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setAILevel.png")),0,0);
        this.setTimer = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setTimer.png")),0,0);
        this.setBonuses = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setBonus.png")),0,0);
        this.play = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/play.png")),0,0);
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);

        this.AILevel = new GlyphLayout(font2, this.setting.getStringLevel());
        this.Timer = new GlyphLayout(font2, this.countDown.getWorldTimer()+" seconds");
        this.Bonuses = new GlyphLayout(font2, "None \n\nNone");

        this.setAILevel.setPosX(WIDTH/4-this.setAILevel.getTexture().getWidth()/2);
        this.setAILevel.setPosY(HEIGHT*4/5-this.setAILevel.getTexture().getHeight()/2);
        this.setAILevel.setLegend("Choose AI Level");
        this.setTimer.setPosX(WIDTH/4-this.setTimer.getTexture().getWidth()/2);
        this.setTimer.setPosY(HEIGHT*3/5-this.setTimer.getTexture().getHeight()/2);
        this.setTimer.setLegend("Choose the time");
        this.setBonuses.setPosX(WIDTH/4-this.setBonuses.getTexture().getWidth()/2);
        this.setBonuses.setPosY(HEIGHT*2/5-this.setBonuses.getTexture().getHeight()/2);
        this.setBonuses.setLegend("Choose your bonuses");
        this.play.setPosX(WIDTH/2-this.play.getTexture().getWidth()/2);
        this.play.setPosY(HEIGHT/8-this.play.getTexture().getHeight()/2);
        this.back.setPosX(this.back.getTexture().getWidth()/2);
        this.back.setPosY(this.back.getTexture().getHeight()/2);
    }

    public AIPreferences(GameStateManager gsm, PreferencesSettings setting, CountDown countDown){ // used once the settings have been changed

        super(gsm);
        this.setAILevel = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setAILevel.png")),0,0);
        this.setTimer = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setTimer.png")),0,0);
        this.setBonuses = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setBonus.png")),0,0);
        this.play = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/play.png")),0,0);
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);
        this.setting = setting;
        this.countDown = countDown;

        this.AILevel = new GlyphLayout(font2, this.setting.getStringLevel());
        this.Timer = new GlyphLayout(font2, this.countDown.getWorldTimer()+" seconds");
        this.Bonuses = new GlyphLayout(font2, this.setting.getStringBonus1() + "\n\n" + this.setting.getStringBonus2());

        this.setAILevel.setPosX(WIDTH/4-this.setAILevel.getTexture().getWidth()/2);
        this.setAILevel.setPosY(HEIGHT*4/5-this.setAILevel.getTexture().getHeight()/2);
        this.setAILevel.setLegend("Choose AI Level");
        this.setTimer.setPosX(WIDTH/4-this.setTimer.getTexture().getWidth()/2);
        this.setTimer.setPosY(HEIGHT*3/5-this.setTimer.getTexture().getHeight()/2);
        this.setTimer.setLegend("Choose the time");
        this.setBonuses.setPosX(WIDTH/4-this.setBonuses.getTexture().getWidth()/2);
        this.setBonuses.setPosY(HEIGHT*2/5-this.setBonuses.getTexture().getHeight()/2);
        this.setBonuses.setLegend("Choose your bonuses");
        this.play.setPosX(WIDTH/2-this.play.getTexture().getWidth()/2);
        this.play.setPosY(HEIGHT/8-this.play.getTexture().getHeight()/2);
        this.back.setPosX(this.back.getTexture().getWidth()/2);
        this.back.setPosY(this.back.getTexture().getHeight()/2);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (this.setAILevel.contains(x,y)) {
                gsm.set(new SetAILevel(gsm, setting, countDown));
            }
            if (this.setTimer.contains(x,y)) {
                gsm.set(new SetAITimer(gsm, setting, countDown));
            }
            if (this.play.contains(x,y)) {
                gsm.set(new PlayModeAi(gsm, setting, countDown));
            }
            if (this.back.contains(x, y)){
                gsm.set(new Pref(gsm));
            }
            if(this.setBonuses.contains(x, y)){
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
        sb.draw(this.setAILevel.getTexture(),this.setAILevel.getPosX() ,this.setAILevel.getPosY() );
        font3.draw(sb,this.setAILevel.getLegend(), this.setAILevel.getPosX() + this.setAILevel.getTexture().getWidth()/2 - this.setAILevel.getLegend().width/2, this.setAILevel.getPosY() - 2*this.setAILevel.getLegend().height);
        font2.draw(sb,this.AILevel, 3*WIDTH/4 - this.AILevel.width/2, this.setAILevel.getPosY() + this.setAILevel.getTexture().getHeight()/2 - this.AILevel.height/2);
        sb.draw(this.setTimer.getTexture(), this.setTimer.getPosX(), this.setTimer.getPosY());
        font3.draw(sb,this.setTimer.getLegend(), this.setTimer.getPosX() + this.setTimer.getTexture().getWidth()/2 - this.setTimer.getLegend().width/2, this.setTimer.getPosY() - 2*this.setTimer.getLegend().height);
        font2.draw(sb,this.Timer, 3*WIDTH/4 - this.Timer.width/2, this.setTimer.getPosY() + this.setTimer.getTexture().getHeight()/2 - this.Timer.height/2);
        sb.draw(this.setBonuses.getTexture(), this.setBonuses.getPosX(), this.setBonuses.getPosY() );
        font3.draw(sb,this.setBonuses.getLegend(), this.setBonuses.getPosX() + this.setBonuses.getTexture().getWidth()/2 - this.setBonuses.getLegend().width/2, this.setBonuses.getPosY() - 2*this.setBonuses.getLegend().height);
        font2.draw(sb,this.Bonuses, 3*WIDTH/4 - this.Bonuses.width/2, this.setBonuses.getPosY() + this.setBonuses.getTexture().getHeight()/2 + this.Bonuses.height/2 - this.setBonuses.getLegend().height/2);
        sb.draw(this.play.getTexture(),this.play.getPosX(),this.play.getPosY());
        sb.draw(this.back.getTexture(), this.back.getPosX(), this.back.getPosX());
        sb.end();

    }

    @Override
    public void dispose() {
    }
}
