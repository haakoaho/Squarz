package com.mygdx.game.view.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

import javax.swing.event.ChangeListener;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Lucas on 06/03/2018.
 */

public class Settings extends State {
<<<<<<< HEAD
    private Texture background, sound, vibration, back;
=======
    Texture sound, vibration, back;
>>>>>>> Maxime

    public Settings(GameStateManager gsm) {
        super(gsm);
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
        sb.draw(sound, WIDTH/6-sound.getWidth()/2, 7*HEIGHT/10-sound.getHeight()/2);
        sb.draw(vibration, WIDTH/6-vibration.getWidth()/2, 3*HEIGHT/10-vibration.getHeight()/2);
        sb.draw(back,back.getWidth()/2,back.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {
        sound.dispose();
        vibration.dispose();
        back.dispose();
    }
}
