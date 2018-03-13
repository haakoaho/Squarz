package com.mygdx.game.view.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Olivier on 06/03/2018.
 */

public class History extends State {
    Texture back;

    public History(GameStateManager gsm) {
        super(gsm);
        back = new Texture(Gdx.files.internal("back.png"));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new Menu(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(back,back.getWidth()/2,back.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {
        back.dispose();
    }
}
