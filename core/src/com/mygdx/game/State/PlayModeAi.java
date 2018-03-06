package com.mygdx.game.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Square;

/**
 * Created by Antoine Dc on 06/03/2018.
 */

public class PlayModeAi extends State {
    private Texture background;
    private Square sq1;
    private boolean firstTouch = false;

    public PlayModeAi(GameStateManager gsm) {
        super(gsm);
        this.background=new Texture(Gdx.files.internal("background.png"));

    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()){
            if(Gdx.input.getX()>Gdx.graphics.getWidth()/4 && Gdx.input.getX()>Gdx.graphics.getWidth()/2 ){
                firstTouch = true;
                sq1 = new Square();
                sq1.setPosition(new Vector2(Gdx.graphics.getWidth()/4, 0));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (firstTouch) {
            sq1.move();
        }
        sb.begin();
        sb.draw(background, 0, 0);
        if (firstTouch) {
            sb.draw(sq1.getTexture(), sq1.getPosition().x, sq1.getPosition().y);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        if (firstTouch){
            sq1.getTexture().dispose();

        }
    }
}
