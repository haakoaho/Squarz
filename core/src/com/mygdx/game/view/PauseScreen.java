package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private Icon texture, sound, vibration, resume, back, quickSetting, addS, deleteS, addV, deleteV, backToPause;
    private GlyphLayout pauseGlyph;


    public PauseScreen() {

        this.texture = new Icon(new Texture(Gdx.files.internal("rectangle.png")),0,0);
        this.texture.setPosX(WIDTH/2 - this.texture.getTexture().getWidth()/2);
        this.texture.setPosY(HEIGHT/2 - this.texture.getTexture().getHeight()/2);

        this.resume = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/play.png")),0, 0);
        this.resume.setPosX(WIDTH/2 - resume.getTexture().getWidth()/2 );
        this.resume.setPosY(HEIGHT/2 - texture.getTexture().getHeight()/6 - resume.getTexture().getHeight()/2);

        this.back = new Icon(new Texture(Gdx.files.internal(format+"/pause/back.png")),0, 0);
        this.back.setPosX(WIDTH/2 - texture.getTexture().getWidth()*1/3 - back.getTexture().getWidth()/2);
        this.back.setPosY(HEIGHT/2 - texture.getTexture().getHeight()/6 - back.getTexture().getHeight()/2);

        this.quickSetting = new Icon(new Texture(Gdx.files.internal(format+"/pause/settings.png")),0, 0);
        this.quickSetting.setPosX(WIDTH/2 + texture.getTexture().getWidth()*1/3 - quickSetting.getTexture().getWidth()/2 );
        this.quickSetting.setPosY(HEIGHT/2 - texture.getTexture().getHeight()/6 - quickSetting.getTexture().getHeight()/2);

        this.sound = new Icon(new Texture(Gdx.files.internal(format+"/pause/speaker.png")),0,0);
        this.sound.setPosX(texture.getPosX() + texture.getTexture().getWidth()/5 - sound.getTexture().getWidth()/2);
        this.sound.setPosY(texture.getPosY() + texture.getTexture().getHeight()*2/4  - sound.getTexture().getHeight()/2);

        this.vibration = new Icon(new Texture(Gdx.files.internal(format+"/pause/vibration.png")),0,0);
        this.vibration.setPosX(texture.getPosX() + texture.getTexture().getWidth()/5 - vibration.getTexture().getWidth()/2);
        this.vibration.setPosY(texture.getPosY() + texture.getTexture().getHeight()*1/4  - vibration.getTexture().getHeight()/2);

        this.addS = new Icon(new Texture(Gdx.files.internal(format+"/pause/add.png")), 0, 0);
        this.addS.setPosX(texture.getPosX() + texture.getTexture().getWidth()*4/5 - addS.getTexture().getWidth()/2);
        this.addS.setPosY(texture.getPosY() + texture.getTexture().getHeight()*2/4  - addS.getTexture().getHeight()/2);

        this.deleteS = new Icon(new Texture(Gdx.files.internal(format+"/pause/minus.png")), 0, 0);
        this.deleteS.setPosX(texture.getPosX() + texture.getTexture().getWidth()*2/5 - deleteS.getTexture().getWidth()/2);
        this.deleteS.setPosY(texture.getPosY() + texture.getTexture().getHeight()*2/4  - deleteS.getTexture().getHeight()/2);

        this.addV = new Icon(new Texture(Gdx.files.internal(format+"/pause/add.png")), 0, 0);
        this.addV.setPosX(texture.getPosX() + texture.getTexture().getWidth()*4/5 - addV.getTexture().getWidth()/2);
        this.addV.setPosY(texture.getPosY() + texture.getTexture().getHeight()*1/4  - addV.getTexture().getHeight()/2);

        this.deleteV = new Icon(new Texture(Gdx.files.internal(format+"/pause/minus.png")), 0, 0);
        this.deleteV.setPosX(texture.getPosX() + texture.getTexture().getWidth()*2/5 - deleteV.getTexture().getWidth()/2);
        this.deleteV.setPosY(texture.getPosY() + texture.getTexture().getHeight()*1/4  - deleteV.getTexture().getHeight()/2);

        this.backToPause = new Icon(new Texture(Gdx.files.internal(format+"/pause/back.png")),0, 0);
        this.backToPause.setPosX(texture.getPosX() + texture.getTexture().getWidth()*9/10 - backToPause.getTexture().getWidth()/2);
        this.backToPause.setPosY(texture.getPosY() + texture.getTexture().getHeight()*3/4  - backToPause.getTexture().getHeight()/2);

        this.pauseGlyph = new GlyphLayout(Squarz.font, "PAUSE");
    }

    public void drawPause(SpriteBatch sb){
        sb.draw(texture.getTexture(), texture.getPosX(), texture.getPosY());
        sb.draw(resume.getTexture(), resume.getPosX(), resume.getPosY());
        sb.draw(back.getTexture(), back.getPosX(), back.getPosY());
        sb.draw(quickSetting.getTexture(), quickSetting.getPosX(), quickSetting.getPosY());
        Squarz.font.draw(sb, pauseGlyph,
                (WIDTH/2 - pauseGlyph.width/2), texture.getPosY()+4/5*texture.getTexture().getHeight() -pauseGlyph.height/2);
    }
    public void drawPauseSetting(SpriteBatch sb){
        sb.draw(texture.getTexture(), texture.getPosX(), texture.getPosY());
        sb.draw(sound.getTexture(), sound.getPosX(), sound.getPosY());
        sb.draw(vibration.getTexture(), vibration.getPosX(), vibration.getPosY());
        sb.draw(addS.getTexture(), addS.getPosX(), addS.getPosY());
        sb.draw(deleteS.getTexture(), deleteS.getPosX(), deleteS.getPosY());
        sb.draw(addV.getTexture(), addV.getPosX(), addV.getPosY());
        sb.draw(deleteV.getTexture(), deleteV.getPosX(), deleteV.getPosY());

        Squarz.font.draw(sb, valueVolume+"", texture.getPosX() + texture.getTexture().getWidth()*3/5,texture.getPosY() + texture.getTexture().getHeight()*11/20);
        Squarz.font.draw(sb, valueVibration+"", texture.getPosX() + texture.getTexture().getWidth()*3/5,texture.getPosY() + texture.getTexture().getHeight()*6/20);

        Squarz.font.draw(sb, pauseGlyph,
                (WIDTH/2 - pauseGlyph.width/2), texture.getPosY()+4/5*texture.getTexture().getHeight() -pauseGlyph.height/2);
        sb.draw(backToPause.getTexture(), backToPause.getPosX(),backToPause.getPosY());
    }

    public Texture getTexture() {
        return texture.getTexture();
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
