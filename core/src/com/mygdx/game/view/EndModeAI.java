package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.State;

import javax.swing.Icon;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 13/03/2018.
 */

public class EndModeAI extends State {
    private AIPreferences setting;
    private Score score;
    private Texture gameOver, scoreTex;
    //private Icon replay, back;
    private Texture replay, backToMenu;

    public EndModeAI(GameStateManager gsm, AIPreferences setting, Score s){
        super(gsm);
        this.setting = setting;
        this.score  = s;
        //vrai textures:
        this.gameOver = new Texture(Gdx.files.internal("gameOver.png"));
        this.scoreTex = new Texture(Gdx.files.internal("scoreTex.png"));

        //a mettre en icon:
        this.replay = new Texture(Gdx.files.internal("replay.png"));
        this.backToMenu = new Texture(Gdx.files.internal("backToMenu.png"));
    }

    @Override
    public void handleInput() {

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
        sb.draw(replay, WIDTH/2-replay.getWidth()/2, HEIGHT*2/5-replay.getHeight()/2);
        sb.draw(backToMenu, WIDTH/2-backToMenu.getWidth()/2, HEIGHT/5-backToMenu.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
