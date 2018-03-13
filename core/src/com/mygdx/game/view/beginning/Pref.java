package com.mygdx.game.view.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import com.mygdx.game.view.AIPreferences;

import java.awt.Rectangle;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Lucas on 06/03/2018.
 */

public class Pref extends State {
    private Icon ai, quick, invite, answer, back;

    public Pref(GameStateManager gsm) {
        super(gsm);
        ai = new Icon(new Texture(Gdx.files.internal("modes/ai.png")),0,0);
        quick = new Icon(new Texture(Gdx.files.internal("modes/quick.png")),0,0);
        invite = new Icon(new Texture(Gdx.files.internal("modes/invite.png")),0,0);
        answer = new Icon(new Texture(Gdx.files.internal("modes/answer.png")),0,0);
        back = new Icon(new Texture(Gdx.files.internal("back.png")),0,0);

        ai.setPosx(WIDTH/4-ai.getTexture().getWidth()/2);
        ai.setPosy(7*HEIGHT/10-ai.getTexture().getHeight()/2);
        quick.setPosx(3*WIDTH/4-quick.getTexture().getWidth()/2);
        quick.setPosy(7*HEIGHT/10-quick.getTexture().getHeight()/2);
        invite.setPosx(WIDTH/4-invite.getTexture().getWidth()/2);
        invite.setPosy(3*HEIGHT/10-invite.getTexture().getHeight()/2);
        answer.setPosx(3*WIDTH/4-answer.getTexture().getWidth()/2);
        answer.setPosy(3*HEIGHT/10-answer.getTexture().getHeight()/2);
        back.setPosx(back.getTexture().getWidth()/2);
        back.setPosy(back.getTexture().getHeight()/2);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(ai.isIn(x,y)){
                gsm.set(new AIPreferences(gsm));
                dispose();
            }
            if (back.isIn(x,y)) {
                gsm.set(new Menu(gsm));
                dispose();
            }
        }
    }
//
    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(ai.getTexture(), ai.getPosx(),ai.getPosy() );
        sb.draw(quick.getTexture(),quick.getPosx() ,quick.getPosy() );
        sb.draw(invite.getTexture(), invite.getPosx(), invite.getPosy());
        sb.draw(answer.getTexture(),answer.getPosx(),answer.getPosy());
        sb.draw(back.getTexture(),back.getPosx(),back.getPosy());
        sb.end();
    }

    @Override
    public void dispose() {
        ai.getTexture().dispose();
        quick.getTexture().dispose();
        invite.getTexture().dispose();
        answer.getTexture().dispose();
        back.getTexture().dispose();
    }
}
