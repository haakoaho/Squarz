package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.gameStateManager.GameStateManager;
import com.mygdx.game.gameStateManager.State;
import com.mygdx.game.model.other.CountDown;
import com.mygdx.game.states.beginning.Menu;

/**
 * Created by Antoine Dc on 21/04/2018.
 */

public class Intro extends State {

    private TextureAtlas introduction;
    private Animation animation;
    private float timePassed1 = 0;
    private float startAnimate = 50;
    private CountDown countDown;

    public Intro (GameStateManager gsm){
        super(gsm);

        introduction = new TextureAtlas(Gdx.files.internal("introduction.atlas"));
        animation = new Animation (1/2f, introduction.getRegions());
        this.countDown = new CountDown(5);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new Menu(gsm));
        }


    }

    @Override
    public void update(float dt) {
        if (countDown.isTimeUp()){
            gsm.set(new Menu(gsm));
        }

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        timePassed1 += Gdx.graphics.getDeltaTime();
        if (timePassed1 >= startAnimate) {
            sb.draw((TextureRegion) animation.getKeyFrame(timePassed1, false),
                    Gdx.graphics.getWidth() / 2 - introduction.getRegions().get(0).getRegionWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - introduction.getRegions().get(0).getRegionHeight() / 2);
        }
        sb.end();

    }

    @Override
    public void dispose() {
        introduction.dispose();
    }
}
