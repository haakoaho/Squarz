package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.LongCountdown;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ShortCountdown;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.VeryLongCountdown;
import com.mygdx.game.model.AbstractFactory.CountdownFactory.LongCountdownFactory;
import com.mygdx.game.model.AbstractFactory.CountdownFactory.ShortCountdownFactory;
import com.mygdx.game.model.AbstractFactory.CountdownFactory.VeryLongCountdownFactory;
import com.mygdx.game.model.Countdown;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font2;
import static com.mygdx.game.Squarz.format;

// CLEAN //
public class SetAITimer extends State {
<<<<<<< HEAD
    private PreferencesSettings settings;
    private CountDown countDown;
    private Icon add, delete, countDownIcon, back;
    private GlyphLayout choose, timeTitle;

    public SetAITimer(GameStateManager gsm, PreferencesSettings settings, CountDown countDown){
=======
    private PreferencesSettings set;
    private ICountdownDuration countDown;
    private Icon add, delete, countDownIcon, back;
    private GlyphLayout choose, timeTitle;

    public SetAITimer(GameStateManager gsm, PreferencesSettings setting, ICountdownDuration countDown){
>>>>>>> 2cd1869f471b46c99867f7d31763bde5d5bd28e6
        super(gsm);

        this.settings = settings;
        this.countDown = countDown;

        this.add = new Icon(new Texture(Gdx.files.internal(format+"/add.png")),0,0); // to increase the time of the game (until 60s)
        this.delete = new Icon(new Texture(Gdx.files.internal(format+"/delete.png")),0,0); // to decrease the time of the game (until 30s)
        this.countDownIcon = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/setTimer.png")),0,0);
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);

        add.setPosX(WIDTH/2-add.getTexture().getWidth()/2);
        add.setPosY(HEIGHT*2/3-add.getTexture().getHeight()/2);
        delete.setPosX(WIDTH/2-add.getTexture().getWidth()/2);
        delete.setPosY(HEIGHT/3-add.getTexture().getHeight()/2);
        countDownIcon.setPosX(WIDTH/2- countDownIcon.getTexture().getWidth()/2);
        countDownIcon.setPosY(HEIGHT/2 - countDownIcon.getTexture().getHeight()/2);
        back.setPosX(back.getTexture().getWidth()/2);
        back.setPosY(back.getTexture().getHeight()/2);

        this.timeTitle = new GlyphLayout(Squarz.font, "CHOOSE THE TIME");
        this.choose = new GlyphLayout(font2, this.countDown.getWorldTimer()+" seconds");
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            System.out.println(this.countDown.getWorldTimer()+" seconds");
            if(add.contains(x,y)) { //add
                if(this.countDown.getWorldTimer() == 45){
                    this.countDown = new VeryLongCountdownFactory().createCountDown();
                    this.choose = new GlyphLayout(font2, this.countDown.getWorldTimer()+" seconds");
                }
                if(this.countDown.getWorldTimer() == 30){
                    this.countDown = new LongCountdownFactory().createCountDown();
                    this.choose = new GlyphLayout(font2, this.countDown.getWorldTimer()+" seconds");
                }
            }
            if(delete.contains(x,y)) { //delete
                if(this.countDown.getWorldTimer() == 45){
                    this.countDown = new ShortCountdownFactory().createCountDown();
                    this.choose = new GlyphLayout(font2, this.countDown.getWorldTimer()+" seconds");
                }
                if(this.countDown.getWorldTimer() == 60){
                    this.countDown = new LongCountdownFactory().createCountDown();
                    this.choose = new GlyphLayout(font2, this.countDown.getWorldTimer()+" seconds");
                }
            }
            if(countDownIcon.contains(x,y)){ //go back
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, settings, countDown));
            }
            if (back.contains(x,y)) {
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, settings, countDown));
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
        Squarz.font.draw(sb, timeTitle, WIDTH/2 - timeTitle.width/2,HEIGHT-2*timeTitle.height);
        font2.draw(sb, this.choose, WIDTH/2 - this.choose.width/2, 8*HEIGHT/10 - this.choose.height/2);
        sb.draw(add.getTexture(), add.getPosX(), add.getPosY());
        sb.draw(delete.getTexture(),delete.getPosX(), delete.getPosY());
        drawAccurateTexture(sb);
        sb.draw(back.getTexture(),back.getPosX(),back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {
    }

    public void drawAccurateTexture(SpriteBatch sb){ // draw the good number of stopwatch, according to the time you have chosen
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