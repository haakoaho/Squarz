package com.mygdx.game.view.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.valueVibration;
import static com.mygdx.game.Squarz.valueVolume;

/**
 * Created by Lucas on 06/03/2018.
 */

public class Settings extends State {
    private Icon sound, vibration, back;
    private Icon maxS,minS,maxV,minV;

    public Settings(GameStateManager gsm) {
        super(gsm);

        sound = new Icon(new Texture(Gdx.files.internal("settings/sound.png")),0,0);
        maxS = new Icon(new Texture(Gdx.files.internal("add.png")),0,0);
        minS = new Icon(new Texture(Gdx.files.internal("delete.png")),0,0);
        vibration = new Icon(new Texture(Gdx.files.internal("settings/vibration.png")),0,0);
        maxV = new Icon(new Texture(Gdx.files.internal("add.png")),0,0);
        minV = new Icon(new Texture(Gdx.files.internal("delete.png")),0,0);
        back = new Icon(new Texture(Gdx.files.internal("back.png")),0,0);

<<<<<<< HEAD
        sound.setPosx(WIDTH/6-sound.getTexture().getWidth()/2);
        sound.setPosy(7*HEIGHT/10-sound.getTexture().getHeight()/2);
        maxS.setPosx(3*WIDTH/6-sound.getTexture().getWidth()/2);
        maxS.setPosy(7*HEIGHT/10-maxS.getTexture().getHeight()/2);
        minS.setPosx(5*WIDTH/6-sound.getTexture().getWidth()/2);
        minS.setPosy(7*HEIGHT/10-minS.getTexture().getHeight()/2);
        vibration.setPosx(WIDTH/6-vibration.getTexture().getWidth()/2);
        vibration.setPosy(3*HEIGHT/10-vibration.getTexture().getHeight()/2);
        maxV.setPosx(3*WIDTH/6-sound.getTexture().getWidth()/2);
        maxV.setPosy(3*HEIGHT/10-maxV.getTexture().getHeight()/2);
        minV.setPosx(5*WIDTH/6-sound.getTexture().getWidth()/2);
        minV.setPosy(3*HEIGHT/10-minV.getTexture().getHeight()/2);
        back.setPosx(back.getTexture().getWidth()/2);
        back.setPosy(back.getTexture().getHeight()/2);
=======
        sound.setPosX(WIDTH/6-sound.getTexture().getWidth()/2);
        sound.setPosY(7*HEIGHT/10-sound.getTexture().getHeight()/2);
        vibration.setPosX(WIDTH/6-vibration.getTexture().getWidth()/2);
        vibration.setPosY(3*HEIGHT/10-vibration.getTexture().getHeight()/2);
        back.setPosX(back.getTexture().getWidth()/2);
        back.setPosY(back.getTexture().getHeight()/2);
>>>>>>> mathieu

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (maxS.contains(x,y)) {
                inc(0);
            }
            if (minS.contains(x,y)) {
                dec(0);
            }
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
<<<<<<< HEAD
        sb.draw(sound.getTexture(),sound.getPosx() ,sound.getPosy());
        sb.draw(maxS.getTexture(),maxS.getPosx(),maxS.getPosy());
        Squarz.font.draw(sb, valueVolume+"", 62*WIDTH/100,72*HEIGHT/100);
        sb.draw(minS.getTexture(),minS.getPosx(),minS.getPosy());
        sb.draw(vibration.getTexture(),vibration.getPosx(),vibration.getPosy());
        sb.draw(maxV.getTexture(),maxV.getPosx(),maxV.getPosy());
        Squarz.font.draw(sb, valueVibration+"", 62*WIDTH/100,32*HEIGHT/100);
        sb.draw(minV.getTexture(),minV.getPosx(),minV.getPosy());
        sb.draw(back.getTexture(),back.getPosx(),back.getPosy());
=======
        sb.draw(sound.getTexture(),sound.getPosX() ,sound.getPosY());
        sb.draw(vibration.getTexture(),vibration.getPosX(),vibration.getPosY());
        sb.draw(back.getTexture(),back.getPosX(),back.getPosY());
>>>>>>> mathieu
        sb.end();
    }

    @Override
    public void dispose() {
    }

    public void inc(int i) {
        if(i==0) { //volume
            if (Squarz.valueVolume < 10) {
                Squarz.valueVolume++;
            }
        }
        if(i==1) { //vibration
            if (Squarz.valueVibration < 10) {
                Squarz.valueVibration++;
            }
        }

    }

    public void dec(int i) {
        if(i==0) { //volume
            if (Squarz.valueVolume > 0) {
                Squarz.valueVolume--;
            }
        }
        if(i==1) { //vibration
            if (Squarz.valueVibration > 0) {
                Squarz.valueVibration--;
            }
        }

    }
}
