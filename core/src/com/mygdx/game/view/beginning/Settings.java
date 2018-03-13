package com.mygdx.game.view.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Lucas on 06/03/2018.
 */

public class Settings extends State {
    private Icon sound, vibration, back;

    public Settings(GameStateManager gsm) {
        super(gsm);
        sound = new Icon(new Texture(Gdx.files.internal("settings/sound.png")),0,0);
        vibration = new Icon(new Texture(Gdx.files.internal("settings/vibration.png")),0,0);
        back = new Icon(new Texture(Gdx.files.internal("back.png")),0,0);

        sound.setPosx(WIDTH/6-sound.getTexture().getWidth()/2);
        sound.setPosy(7*HEIGHT/10-sound.getTexture().getHeight()/2);
        vibration.setPosx(WIDTH/6-vibration.getTexture().getWidth()/2);
        vibration.setPosy(3*HEIGHT/10-vibration.getTexture().getHeight()/2);
        back.setPosx(back.getTexture().getWidth()/2);
        back.setPosy(back.getTexture().getHeight()/2);

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
        sb.draw(sound.getTexture(),sound.getPosx() ,sound.getPosy());
        sb.draw(vibration.getTexture(),vibration.getPosx(),vibration.getPosy());
        sb.draw(back.getTexture(),back.getPosx(),back.getPosy());
        sb.end();
    }

    @Override
    public void dispose() {
        sound.getTexture().dispose();
        vibration.getTexture().dispose();
        back.getTexture().dispose();
    }
}
