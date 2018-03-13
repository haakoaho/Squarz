package com.mygdx.game.view.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Lucas on 06/03/2018.
 */

public class Settings extends State {
    Texture background, sound, vibration, back;

    public Settings(GameStateManager gsm) {
        super(gsm);
        background = new Texture(Gdx.files.internal("background.png"));
        sound = new Texture(Gdx.files.internal("settings/sound.png"));
        vibration = new Texture(Gdx.files.internal("settings/vibration.png"));
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
        sb.draw(background, 0, 0, WIDTH, HEIGHT);
        sb.draw(sound, WIDTH/6-sound.getWidth()/2, 7*HEIGHT/10-sound.getHeight()/2);
        sb.draw(vibration, WIDTH/6-vibration.getWidth()/2, 3*HEIGHT/10-vibration.getHeight()/2);
        sb.draw(back,back.getWidth()/2,back.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        sound.dispose();
        vibration.dispose();
        back.dispose();
    }
}
