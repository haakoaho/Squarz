package com.mygdx.game.views.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controls.GameStateManager;
import com.mygdx.game.models.Icon;
import com.mygdx.game.models.State;

import static com.mygdx.game.Squarz.HEIGHT;

/**
 * Created by Olivier on 06/03/2018.
 */

public class History extends State {
    Icon back;

    public History(GameStateManager gsm) {
        super(gsm);
        back = new Icon(new Texture(Gdx.files.internal("back.png")),0,0);

        back.setPosX(back.getTexture().getWidth()/2);
        back.setPosY(back.getTexture().getHeight()/2);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (back.contains(x,y)) {
                gsm.set(new Menu(gsm));
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
        sb.draw(back.getTexture(),back.getPosX(),back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {
        back.getTexture().dispose();
    }
}
