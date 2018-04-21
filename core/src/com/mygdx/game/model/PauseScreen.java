package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.format;
import static com.mygdx.game.Squarz.valueVibration;
import static com.mygdx.game.Squarz.valueVolume;

//CLEAN//
public class PauseScreen {
    private Icon texture, sound, vibration, resume, back, quickSetting, addS, deleteS, addV, deleteV, backToPause;
    private GlyphLayout pauseGlyph, sGlyph, vGlyph;


    public PauseScreen() {

        this.texture = new Icon(new Texture(Gdx.files.internal("rectangle.png")),0,0);
        this.resume = new Icon(new Texture(Gdx.files.internal(format+"/pause/resume.png")),0, 0);
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/pause/back.png")),0, 0);
        this.quickSetting = new Icon(new Texture(Gdx.files.internal(format+"/pause/settings.png")),0, 0);
        this.sound = new Icon(new Texture(Gdx.files.internal(format+"/pause/speaker.png")),0,0);
        this.vibration = new Icon(new Texture(Gdx.files.internal(format+"/pause/vibration.png")),0,0);
        this.addS = new Icon(new Texture(Gdx.files.internal(format+"/pause/add.png")), 0, 0);
        this.deleteS = new Icon(new Texture(Gdx.files.internal(format+"/pause/minus.png")), 0, 0);
        this.addV = new Icon(new Texture(Gdx.files.internal(format+"/pause/add.png")), 0, 0);
        this.deleteV = new Icon(new Texture(Gdx.files.internal(format+"/pause/minus.png")), 0, 0);
        this.backToPause = new Icon(new Texture(Gdx.files.internal(format+"/pause/back.png")),0, 0);

        this.texture.setPosX(WIDTH/2 - this.texture.getTexture().getWidth()/2);
        this.texture.setPosY(HEIGHT/2 - this.texture.getTexture().getHeight()/2);
        this.resume.setPosX(WIDTH/2 - this.resume.getTexture().getWidth()/2 );
        this.resume.setPosY(HEIGHT/2 - this.texture.getTexture().getHeight()/6 - this.resume.getTexture().getHeight()/2);
        this.back.setPosX(WIDTH/2 - this.texture.getTexture().getWidth()*1/3 - this.back.getTexture().getWidth()/2);
        this.back.setPosY(HEIGHT/2 - this.texture.getTexture().getHeight()/6 - this.back.getTexture().getHeight()/2);
        this.quickSetting.setPosX(WIDTH/2 + this.texture.getTexture().getWidth()*1/3 - this.quickSetting.getTexture().getWidth()/2 );
        this.quickSetting.setPosY(HEIGHT/2 - this.texture.getTexture().getHeight()/6 - this.quickSetting.getTexture().getHeight()/2);
        this.sound.setPosX(this.texture.getPosX() + this.texture.getTexture().getWidth()/5 - this.sound.getTexture().getWidth()/2);
        this.sound.setPosY(this.texture.getPosY() + this.texture.getTexture().getHeight()*2/4  - this.sound.getTexture().getHeight()/2);
        this.vibration.setPosX(this.texture.getPosX() + this.texture.getTexture().getWidth()/5 - this.vibration.getTexture().getWidth()/2);
        this.vibration.setPosY(this.texture.getPosY() + this.texture.getTexture().getHeight()*1/4  - this.vibration.getTexture().getHeight()/2);
        this.addS.setPosX(this.texture.getPosX() + this.texture.getTexture().getWidth()*4/5 - this.addS.getTexture().getWidth()/2);
        this.addS.setPosY(this.texture.getPosY() + this.texture.getTexture().getHeight()*2/4  - this.addS.getTexture().getHeight()/2);
        this.deleteS.setPosX(this.texture.getPosX() + this.texture.getTexture().getWidth()*2/5 - this.deleteS.getTexture().getWidth()/2);
        this.deleteS.setPosY(this.texture.getPosY() + this.texture.getTexture().getHeight()*2/4  - this.deleteS.getTexture().getHeight()/2);
        this.addV.setPosX(this.texture.getPosX() + this.texture.getTexture().getWidth()*4/5 - this.addV.getTexture().getWidth()/2);
        this.addV.setPosY(this.texture.getPosY() + this.texture.getTexture().getHeight()*1/4  - this.addV.getTexture().getHeight()/2);
        this.deleteV.setPosX(this.texture.getPosX() + this.texture.getTexture().getWidth()*2/5 - this.deleteV.getTexture().getWidth()/2);
        this.deleteV.setPosY(this.texture.getPosY() + this.texture.getTexture().getHeight()*1/4  - this.deleteV.getTexture().getHeight()/2);
        this.backToPause.setPosX(this.texture.getPosX() + this.texture.getTexture().getWidth()*9/10 - this.backToPause.getTexture().getWidth()/2);
        this.backToPause.setPosY(this.texture.getPosY() + this.texture.getTexture().getHeight()*3/4  - this.backToPause.getTexture().getHeight()/2);

        this.pauseGlyph = new GlyphLayout(Squarz.font, "PAUSE");
        this.sGlyph = new GlyphLayout(Squarz.font2, String.valueOf(valueVolume));
        this.vGlyph = new GlyphLayout(Squarz.font2, String.valueOf(valueVibration));
    }

    public void drawPause(SpriteBatch sb){
        sb.draw(this.texture.getTexture(), this.texture.getPosX(), this.texture.getPosY());
        sb.draw(this.resume.getTexture(), this.resume.getPosX(), this.resume.getPosY());
        sb.draw(this.back.getTexture(), this.back.getPosX(), this.back.getPosY());
        sb.draw(this.quickSetting.getTexture(), this.quickSetting.getPosX(), quickSetting.getPosY());
        Squarz.font.draw(sb, this.pauseGlyph,
                (WIDTH/2 - this.pauseGlyph.width/2), this.texture.getPosY() + this.texture.getTexture().getHeight()*3/4 + this.pauseGlyph.height/2);
    }
    public void drawPauseSetting(SpriteBatch sb){
        sb.draw(this.texture.getTexture(), this.texture.getPosX(), this.texture.getPosY());
        sb.draw(this.sound.getTexture(), this.sound.getPosX(), this.sound.getPosY());
        sb.draw(this.vibration.getTexture(), this.vibration.getPosX(), this.vibration.getPosY());
        sb.draw(this.addS.getTexture(), this.addS.getPosX(), this.addS.getPosY());
        sb.draw(this.deleteS.getTexture(), this.deleteS.getPosX(), this.deleteS.getPosY());
        sb.draw(this.addV.getTexture(), this.addV.getPosX(), this.addV.getPosY());
        sb.draw(this.deleteV.getTexture(), this.deleteV.getPosX(), this.deleteV.getPosY());

        this.sGlyph.setText(Squarz.font2, String.valueOf(valueVolume));
        Squarz.font2.draw(sb, this.sGlyph, this.texture.getPosX() + this.texture.getTexture().getWidth()*3/5 - this.sGlyph.width/2,this.texture.getPosY() + this.texture.getTexture().getHeight()*2/4  +this.sGlyph.height/2);
        this.vGlyph.setText(Squarz.font2, String.valueOf(valueVibration));
        Squarz.font2.draw(sb, this.vGlyph, this.texture.getPosX() + this.texture.getTexture().getWidth()*3/5 - this.vGlyph.width/2,this.texture.getPosY() + this.texture.getTexture().getHeight()*1/4  +this.vGlyph.height/2);

        Squarz.font.draw(sb, this.pauseGlyph,
                (WIDTH/2 - this.pauseGlyph.width/2), this.texture.getPosY() + this.texture.getTexture().getHeight()*3/4 + this.pauseGlyph.height/2);
        sb.draw(this.backToPause.getTexture(), this.backToPause.getPosX(),this.backToPause.getPosY());
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
