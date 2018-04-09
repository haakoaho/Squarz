package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.model.Icon;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.model.Score;

import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.format;
import static com.mygdx.game.Squarz.valueVibration;
import static com.mygdx.game.Squarz.valueVolume;

/**
 * Created by Antoine Dc on 21/03/2018.
 */

public class PauseScreen {
    private Texture texture, sound, vibration;
    private Icon resume, back, quickSetting, addS, deleteS, addV, deleteV, backToPause;
    private GlyphLayout pauseGlyph;


    public PauseScreen() {
        this.texture = new Texture(Gdx.files.internal("rectangle.png"));
        this.resume = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/play.png")),0, 0);
        resume.setPosX(WIDTH/2 - getResume().getTexture().getWidth()/2 );
        resume.setPosY(HEIGHT*2/5 - getResume().getTexture().getHeight()/2);
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/pause/back.png")),0, 0);
        back.setPosX(WIDTH*2/3 - getBack().getTexture().getWidth()/2);
        back.setPosY(HEIGHT*2/5 - getBack().getTexture().getHeight()/2);
        this.quickSetting = new Icon(new Texture(Gdx.files.internal(format+"/pause/settings.png")),0, 0);
        quickSetting.setPosX(WIDTH/3 - getQuickSetting().getTexture().getWidth()/2);
        quickSetting.setPosY(HEIGHT*2/5 - getQuickSetting().getTexture().getHeight()/2);
        this.addS = new Icon(new Texture(Gdx.files.internal(format+"/pause/add.png")), 0, 0);
        addS.setPosX(WIDTH*65/100);
        addS.setPosY(HEIGHT/2 - addS.getTexture().getHeight()/2);
        this.deleteS = new Icon(new Texture(Gdx.files.internal(format+"/pause/minus.png")), 0, 0);
        deleteS.setPosX(WIDTH*40/100);
        deleteS.setPosY(HEIGHT/2 - deleteS.getTexture().getHeight()/2);
        this.addV = new Icon(new Texture(Gdx.files.internal(format+"/pause/add.png")), 0, 0);
        addV.setPosX(WIDTH*65/100);
        addV.setPosY(HEIGHT/3 - addV.getTexture().getHeight()/2);
        this.deleteV = new Icon(new Texture(Gdx.files.internal(format+"/pause/minus.png")), 0, 0);
        deleteV.setPosX(WIDTH*40/100);
        deleteV.setPosY(HEIGHT/3 - deleteV.getTexture().getHeight()/2);
        this.backToPause = new Icon(new Texture(Gdx.files.internal(format+"/pause/back.png")),0, 0);
        backToPause.setPosX(WIDTH*63/100);
        backToPause.setPosY(HEIGHT*61/100);
        this.pauseGlyph = new GlyphLayout(Squarz.font, "PAUSE");


        this.sound = new Texture(Gdx.files.internal(format+"/pause/speaker.png"));
        this.vibration = new Texture(Gdx.files.internal(format+"/pause/vibration.png"));

    }

    public void drawPause(SpriteBatch sb){
        sb.draw(this.getTexture(), WIDTH/2 - this.getTexture().getWidth()/2
                , HEIGHT/2 - this.getTexture().getHeight()/2);
        sb.draw(getResume().getTexture(), getResume().getPosX(), getResume().getPosY());
        sb.draw(this.getBack().getTexture(), getBack().getPosX(), getBack().getPosY());
        sb.draw(this.getQuickSetting().getTexture(), getQuickSetting().getPosX(), getQuickSetting().getPosY());
        Squarz.font.draw(sb, pauseGlyph,
                (WIDTH/2 - pauseGlyph.width/2), HEIGHT/2 + this.getTexture().getHeight()*1/4);
    }
    public void drawPauseSetting(SpriteBatch sb){
        sb.draw(this.getTexture(), WIDTH/2 - this.getTexture().getWidth()/2
                , HEIGHT/2 - this.getTexture().getHeight()/2);
        sb.draw(this.sound, WIDTH/4-sound.getWidth()/2, HEIGHT/2-sound.getHeight()/2);
        sb.draw(this.vibration, WIDTH/4-sound.getWidth()/2, HEIGHT/3-sound.getHeight()/2);
        sb.draw(addS.getTexture(), addS.getPosX(), addS.getPosY());
        sb.draw(deleteS.getTexture(), deleteS.getPosX(), deleteS.getPosY());
        sb.draw(addV.getTexture(), addV.getPosX(), addV.getPosY());
        sb.draw(deleteV.getTexture(), deleteV.getPosX(), deleteV.getPosY());

        Squarz.font.draw(sb, valueVolume+"", WIDTH*55/100,HEIGHT/2 + sound.getHeight()/4);
        Squarz.font.draw(sb, valueVibration+"", WIDTH*55/100,HEIGHT/3 + sound.getHeight()/4);



        Squarz.font.draw(sb, pauseGlyph,
                (WIDTH*2/5 - pauseGlyph.width/2), HEIGHT/2 + this.getTexture().getHeight()*1/4);
        sb.draw(getBackToPause().getTexture(), getBackToPause().getPosX(),getBackToPause().getPosY());
    }



    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public Icon getResume() {
        return resume;
    }
    public Icon getBack(){ return back;}
    public Icon getQuickSetting(){ return this.quickSetting;}
    public Icon getAddS() {
        return addS;
    }
    public Icon getDeleteS() {
        return deleteS;
    }
    public Icon getAddV() {
        return addV;
    }
    public Icon getDeleteV() {
        return deleteV;
    }
    public Icon getBackToPause() {
        return backToPause;
    }

    public GlyphLayout getPauseGlyph() {
        return pauseGlyph;
    }
    public void setPauseGlyph(GlyphLayout pauseGlyph) {
        this.pauseGlyph = pauseGlyph;
    }

}
