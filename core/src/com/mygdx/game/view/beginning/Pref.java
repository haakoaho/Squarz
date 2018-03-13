package com.mygdx.game.view.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

import javax.xml.soap.Text;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Lucas on 06/03/2018.
 */

public class Pref extends State {
    private Texture background;
    private Texture ai, quick, invite, answer, back;

    public Pref(GameStateManager gsm) {
        super(gsm);
        background = new Texture(Gdx.files.internal("background.png"));
        ai = new Texture(Gdx.files.internal("modes/ai.png"));
        quick = new Texture(Gdx.files.internal("modes/quick.png"));
        invite = new Texture(Gdx.files.internal("modes/invite.png"));
        answer = new Texture(Gdx.files.internal("modes/answer.png"));
        back = new Texture(Gdx.files.internal("back.png"));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new Menu(gsm));
            dispose();
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
        sb.draw(background, 0, 0, WIDTH, HEIGHT);
        sb.draw(ai, WIDTH/4-ai.getWidth()/2, 7*HEIGHT/10-ai.getHeight()/2);
        sb.draw(quick, 3*WIDTH/4-quick.getWidth()/2, 7*HEIGHT/10-quick.getHeight()/2);
        sb.draw(invite, WIDTH/4-invite.getWidth()/2, 3*HEIGHT/10-invite.getHeight()/2);
        sb.draw(answer,3*WIDTH/4-answer.getWidth()/2, 3*HEIGHT/10-answer.getHeight()/2);
        sb.draw(back,back.getWidth()/2,back.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        ai.dispose();
        quick.dispose();
        invite.dispose();
        answer.dispose();
        back.dispose();
    }
}
