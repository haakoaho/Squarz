package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.State;


import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.format;

/**
 * Created by mathi on 13/03/2018.
 */

public class EndModeAI extends State {
    private PreferencesSettings setting;
    private Icon replay, back;
    private CountDown countDown;
    private GlyphLayout scoreUser, scoreAi, message1, message2;

    public EndModeAI(GameStateManager gsm, PreferencesSettings setting, Score s, CountDown countDown){
        super(gsm);

        this.setting = setting; // we keep the settings, like this, if the player wants to play again, he does not have to choose again (even if he can modify them if he wants to)
        this.countDown = new CountDown(countDown.getTimerKey()); // we keep the countDown for the same reasons

        this.replay = new Icon(new Texture(Gdx.files.internal(format+"/endMode/replay.png")), 0, 0);
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/endMode/backToMenu.png")), 0, 0);

        this.replay.setPosX(WIDTH/2-this.replay.getTexture().getWidth()/2);
        this.replay.setPosY(HEIGHT*2/5-this.replay.getTexture().getHeight()/2);
        this.back.setPosX(WIDTH/2 - this.back.getTexture().getWidth()/2);
        this.back.setPosY(HEIGHT/5 - this.back.getTexture().getHeight()/2);

        this.scoreAi = new GlyphLayout(Squarz.font, s.getOpponentScore().toString());
        this.scoreUser = new GlyphLayout(Squarz.font, s.getUserScore().toString());

        if(s.getOpponentScore()>s.getUserScore()) {
            this.message1 = new GlyphLayout(Squarz.font, "Haha!");
            this.message2 = new GlyphLayout(Squarz.font, "Computer better!");
        } else if (s.getOpponentScore()<s.getUserScore()) {
            this.message1 = new GlyphLayout(Squarz.font, "Human won...");
            this.message2 = new GlyphLayout(Squarz.font, "Well played!");
        } else {
            this.message1 = new GlyphLayout(Squarz.font, "Tie!");
            this.message2 = new GlyphLayout(Squarz.font, "Revenge?");
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(this.replay.contains(x, y)){
                gsm.set(new AIPreferences(gsm, setting, countDown));
            }
            if(this.back.contains(x, y)){
                gsm.set(new com.mygdx.game.view.beginning.Menu(gsm));
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
        sb.draw(this.back.getTexture(), this.back.getPosX(), this.back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
