package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.State;


import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 13/03/2018.
 */

public class EndModeAI extends State {
    private PreferencesSettings setting;
    private Score score;
    private Texture gameOver, scoreTex;
    private Icon replay, back;

    public EndModeAI(GameStateManager gsm, PreferencesSettings setting, Score s){
        super(gsm);
        this.setting = setting;
        this.score  = s;
        //vraies textures:
        this.gameOver = new Texture(Gdx.files.internal("temporary/gameOver.png"));
        this.scoreTex = new Texture(Gdx.files.internal("temporary/scoreTex.png"));

        //a mettre en icon:
        this.replay = new Icon(new Texture(Gdx.files.internal("endMode/replay.png")), 0, 0);
        this.replay.setPosX(WIDTH/2-replay.getTexture().getWidth()/2);
        this.replay.setPosY(HEIGHT*2/5-replay.getTexture().getHeight()/2);

        this.back = new Icon(new Texture(Gdx.files.internal("endMode/backToMenu.png")), 0, 0);
        this.back.setPosX(WIDTH/2 - back.getTexture().getWidth()/2);
        this.back.setPosY(HEIGHT/5 - back.getTexture().getHeight()/2);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(replay.contains(x, y)){
                gsm.set(new AIPreferences(gsm, setting));
            }
            if(back.contains(x, y)){
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
        sb.draw(gameOver, WIDTH/2-gameOver.getWidth()/2, HEIGHT*4/5-gameOver.getHeight()/2);
        sb.draw(scoreTex, WIDTH/2-scoreTex.getWidth()/2, HEIGHT*3/5-scoreTex.getHeight()/2);
        sb.draw(replay.getTexture(), replay.getPosX(), replay.getPosY());
        sb.draw(back.getTexture(), back.getPosX(), back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
