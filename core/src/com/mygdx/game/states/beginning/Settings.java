package com.mygdx.game.states.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.gameStateManager.GameStateManager;
import com.mygdx.game.model.other.Icon;
import com.mygdx.game.gameStateManager.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.format;
import static com.mygdx.game.Squarz.valueVibration;
import static com.mygdx.game.Squarz.valueVolume;

// CLEAN //
public class Settings extends State {
    private final Icon sound;
    private final Icon vibration;
    private final Icon back;
    private final Icon maxS;
    private final Icon minS;
    private final Icon maxV;
    private final Icon minV;
    private final GlyphLayout sLayout;
    private final GlyphLayout vLayout;

    public Settings(GameStateManager gsm) {
        super(gsm);

        this.sound = new Icon(new Texture(Gdx.files.internal(format+"/settings/sound.png")),0,0);
        this.maxS = new Icon(new Texture(Gdx.files.internal(format+"/add.png")),0,0); // touch it to increase the sound of 1 (until 10)
        this.minS = new Icon(new Texture(Gdx.files.internal(format+"/delete.png")),0,0); // touch it to decrease the sound of 1 (until 0)
        this.vibration = new Icon(new Texture(Gdx.files.internal(format+"/settings/vibration.png")),0,0);
        this.maxV = new Icon(new Texture(Gdx.files.internal(format+"/add.png")),0,0); // touch it to increase the vibration of 1 (until 10)
        this.minV = new Icon(new Texture(Gdx.files.internal(format+"/delete.png")),0,0); // touch it to decrease the vibration of 1 (until 0)
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);

        this.sound.setPosX(WIDTH/6-this.sound.getTexture().getWidth()/2);
        this.sound.setPosY(7*HEIGHT/10-this.sound.getTexture().getHeight()/2);
        this.maxS.setPosX(5*WIDTH/6-this.maxS.getTexture().getWidth()/2);
        this.maxS.setPosY(7*HEIGHT/10-this.maxS.getTexture().getHeight()/2);
        this.minS.setPosX(3*WIDTH/6-this.minS.getTexture().getWidth()/2);
        this.minS.setPosY(7*HEIGHT/10-this.minS.getTexture().getHeight()/2);
        this.vibration.setPosX(WIDTH/6-this.vibration.getTexture().getWidth()/2);
        this.vibration.setPosY(3*HEIGHT/10-this.vibration.getTexture().getHeight()/2);
        this.maxV.setPosX(5*WIDTH/6-this.maxV.getTexture().getWidth()/2);
        this.maxV.setPosY(3*HEIGHT/10-this.maxV.getTexture().getHeight()/2);
        this.minV.setPosX(3*WIDTH/6-this.minV.getTexture().getWidth()/2);
        this.minV.setPosY(3*HEIGHT/10-this.minV.getTexture().getHeight()/2);
        this.back.setPosX(this.back.getTexture().getWidth()/2);
        this.back.setPosY(this.back.getTexture().getHeight()/2);

        this.sLayout = new GlyphLayout(Squarz.font, String.valueOf(valueVolume));
        this.vLayout = new GlyphLayout(Squarz.font, String.valueOf(valueVibration));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (this.maxS.contains(x,y)) {
                inc(0);
            }
            if (this.minS.contains(x,y)) {
                dec(0);
            }
            if (this.maxV.contains(x,y)) {
                inc(1);
            }
            if (this.minV.contains(x,y)) {
                dec(1);
            }
            if (this.back.contains(x,y)) {
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
        sb.draw(this.sound.getTexture(),this.sound.getPosX() ,this.sound.getPosY());
        sb.draw(this.maxS.getTexture(),this.maxS.getPosX(),this.maxS.getPosY());
        this.sLayout.setText(Squarz.font,String.valueOf(valueVolume));
        Squarz.font.draw(sb, this.sLayout, WIDTH*4/6-this.sLayout.width/2,7*HEIGHT/10+this.sLayout.height/2);
        sb.draw(this.minS.getTexture(),this.minS.getPosX(),this.minS.getPosY());
        sb.draw(this.vibration.getTexture(),this.vibration.getPosX(),this.vibration.getPosY());
        sb.draw(this.maxV.getTexture(),this.maxV.getPosX(),this.maxV.getPosY());
        this.vLayout.setText(Squarz.font,String.valueOf(valueVibration));
        Squarz.font.draw(sb, this.vLayout, WIDTH*4/6-this.vLayout.width/2,3*HEIGHT/10+this.vLayout.height/2);
        sb.draw(this.minV.getTexture(),this.minV.getPosX(),this.minV.getPosY());
        sb.draw(this.back.getTexture(),this.back.getPosY(),this.back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {
    }

    private void inc(int i) { // this function will increase the volume (i=0) or the vibration (i=1)
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

    private void dec(int i) { // this function will decrease the volume (i=0) or the vibration (i=1)
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
