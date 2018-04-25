package com.mygdx.game.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.model.other.Icon;
import com.mygdx.game.model.other.Score;
import com.mygdx.game.Squarz;
import com.mygdx.game.gameStateManager.State;
import com.mygdx.game.gameStateManager.GameStateManager;
import com.mygdx.game.model.ai_settings.PreferencesSettings;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;
import com.mygdx.game.model.AbstractFactory.CountdownFactory.LongCountdownFactory;
import com.mygdx.game.model.AbstractFactory.CountdownFactory.ShortCountdownFactory;
import com.mygdx.game.model.AbstractFactory.CountdownFactory.VeryLongCountdownFactory;
import com.mygdx.game.states.Preferences.AIPreferences;


import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font3;
import static com.mygdx.game.Squarz.format;

/**
 * Created by mathi on 13/03/2018.
 */

public class EndMode extends State {
    private final PreferencesSettings setting;
    private final Icon replay;
    private final Icon back;
    private ICountdownDuration countDown1;
    private final GlyphLayout scoreUser;
    private final GlyphLayout scoreAi;
    private final GlyphLayout message1;
    private final GlyphLayout message2;

    public EndMode(GameStateManager gsm, PreferencesSettings setting, Score s, ICountdownDuration countDown){
        super(gsm);

        this.setting = setting; // we keep the settings, like this, if the player wants to play again, he does not have to choose again (even if he can modify them if he wants to)

        this.replay = new Icon(new Texture(Gdx.files.internal(format+"/endMode/replay.png")), 0, 0);
        this.replay.setLegend("Replay against AI");
        this.replay.setPosX(WIDTH/2-this.replay.getTexture().getWidth()/2);
        this.replay.setPosY(HEIGHT*2/5-this.replay.getTexture().getHeight()/2);

        this.back = new Icon(new Texture(Gdx.files.internal(format+"/endMode/backToMenu.png")), 0, 0);
        this.back.setLegend("Go to the Menu");
        this.back.setPosX(WIDTH/2 - this.back.getTexture().getWidth()/2);
        this.back.setPosY(HEIGHT/5 - this.back.getTexture().getHeight()/2);

        this.scoreAi = new GlyphLayout(Squarz.font, s.getOpponentScore().toString());
        this.scoreUser = new GlyphLayout(Squarz.font, s.getUserScore().toString());

        if(s.getOpponentScore()>s.getUserScore()) {
            this.message1 = new GlyphLayout(Squarz.font, "Hooo...");
            this.message2 = new GlyphLayout(Squarz.font, "You lost!");
        } else if (s.getOpponentScore()<s.getUserScore()) {
            this.message1 = new GlyphLayout(Squarz.font, "Haaa!");
            this.message2 = new GlyphLayout(Squarz.font, "You won!");
        } else {
            this.message1 = new GlyphLayout(Squarz.font, "Tie!");
            this.message2 = new GlyphLayout(Squarz.font, "Revenge?");
        }

        if(countDown.getTimerKey()==30){
            this.countDown1 = new ShortCountdownFactory().createCountDown();
        }
        if(countDown.getTimerKey()==45){
            this.countDown1 = new LongCountdownFactory().createCountDown();
        }
        if(countDown.getTimerKey()==60){
            this.countDown1 = new VeryLongCountdownFactory().createCountDown();
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(this.replay.contains(x, y)){
                Score.getInstance().reset();
                gsm.set(new AIPreferences(gsm, setting, countDown1));
            }
            if(this.back.contains(x, y)){
                Score.getInstance().reset();
                gsm.set(new com.mygdx.game.states.beginning.Menu(gsm));
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
        Squarz.font.draw(sb, this.message1, WIDTH/2 - this.message1.width/2,HEIGHT * 4/5);
        Squarz.font.draw(sb, this.message2, WIDTH/2 - this.message2.width/2,HEIGHT * 4/5- this.message1.height*3/2);
        Squarz.font.draw(sb, this.scoreUser, WIDTH * 2/5 - this.scoreUser.width/2, HEIGHT * 3/5);
        Squarz.font.draw(sb, this.scoreAi, WIDTH * 3/5 - this.scoreUser.width/2, HEIGHT * 3/5);
        sb.draw(this.replay.getTexture(), this.replay.getPosX(), this.replay.getPosY());
        font3.draw(sb, this.replay.getLegend(), this.replay.getPosX() + this.replay.getTexture().getWidth()/2 - this.replay.getLegend().width/2, this.replay.getPosY() - 2*this.replay.getLegend().height);
        sb.draw(this.back.getTexture(), this.back.getPosX(), this.back.getPosY());
        font3.draw(sb, this.back.getLegend(), this.back.getPosX() + this.back.getTexture().getWidth()/2 - this.back.getLegend().width/2, this.back.getPosY() - 2*this.back.getLegend().height);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
