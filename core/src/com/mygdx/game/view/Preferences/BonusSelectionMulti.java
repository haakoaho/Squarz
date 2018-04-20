package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import com.mygdx.game.view.PlayModeMulti;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font2;
import static com.mygdx.game.Squarz.format;

// CLEAN // This class is very similar to BonusSelection, please read it before reading this one for more understanding
public class BonusSelectionMulti extends State {
    private GlyphLayout tenToChooseGlyph, clean, bonusDescription1, bonusDescription2;
    private Icon nurse, punisher, none, mrPropre, emptyField1, emptyField2, cleanButton;
    private CountDown countDown, tenToChoose;
    private PreferencesSettings settings;
    private Boolean isFilled1;
    private Boolean isFilled2;
    private ShapeRenderer shapeRenderer;

    public BonusSelectionMulti(GameStateManager gsm) {
        super(gsm);

        this.settings = new PreferencesSettings();
        this.countDown = new CountDown(60); // set the countDown to a fixed value: 1 minute
        this.tenToChoose = new CountDown(10); // set the time each player has to select his bonuses 10 seconds

        this.isFilled1 = false;
        this.isFilled2 = false;

        this.tenToChooseGlyph = new GlyphLayout(Squarz.font, String.valueOf(this.tenToChoose.getWorldTimer()));
        this.clean = new GlyphLayout(Squarz.font2, "CLEAN ALL");
        this.bonusDescription1 = new GlyphLayout(font2, this.settings.getDescriptionBonus1());
        this.bonusDescription2 = new GlyphLayout(font2, this.settings.getDescriptionBonus2());

        this.none = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/none.png")),0,0);
        this.cleanButton = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/button.png")), 0, 0);
        this.emptyField1 = new Icon(this.settings.getBonus1().getBonustexture(this.settings.getBonus1().getBonusKey()),0,0);
        this.emptyField2 = new Icon(this.settings.getBonus2().getBonustexture(this.settings.getBonus2().getBonusKey()),0,0);
        this.punisher = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/punisher.png")),0,0);
        this.nurse = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/nurse.png")),0,0);
        this.mrPropre = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/mrPropre.png")),0,0);

        this.none.setPosX(WIDTH*1/6 - this.none.getTexture().getWidth()/2);
        this.none.setPosY(HEIGHT/2 - this.none.getTexture().getHeight()/2);
        this.cleanButton.setPosX(WIDTH/2 - this.cleanButton.getTexture().getWidth()/2);
        this.cleanButton.setPosY(HEIGHT * 5/8 - this.cleanButton.getTexture().getHeight()/2);
        this.emptyField1.setPosX(WIDTH/3 - this.emptyField1.getTexture().getWidth()/2);
        this.emptyField1.setPosY(HEIGHT*3/4 - this.emptyField1.getTexture().getHeight()/2);
        this.emptyField2.setPosX(WIDTH*2/3 - this.emptyField2.getTexture().getWidth()/2);
        this.emptyField2.setPosY(HEIGHT*3/4 - this.emptyField2.getTexture().getHeight()/2);
        this.punisher.setPosX(WIDTH*1/6 + WIDTH*2/9- this.punisher.getTexture().getWidth()/2);
        this.punisher.setPosY(HEIGHT/2 - this.punisher.getTexture().getHeight()/2);
        this.nurse.setPosX(WIDTH*1/6 + WIDTH*4/9 - this.nurse.getTexture().getWidth()/2);
        this.nurse.setPosY(HEIGHT/2 - this.nurse.getTexture().getWidth()/2);
        this.mrPropre.setPosX(WIDTH*5/6 - this.nurse.getTexture().getWidth()/2);
        this.mrPropre.setPosY(HEIGHT/2 - this.nurse.getTexture().getWidth()/2);

        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();

            chosenBonus(x, y);

            if (cleanButton.contains(x, y)){
                isFilled1 = false;
                isFilled2 = false;
                this.emptyField1.setTexture(this.none.getTexture());
                this.emptyField2.setTexture(this.none.getTexture());
                this.settings.getBonus1().setBonusKey(0);
                this.settings.getBonus2().setBonusKey(0);
                bonusDescription1.setText(font2, this.settings.getDescriptionBonus1());
                bonusDescription2.setText(font2, this.settings.getDescriptionBonus2());
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        this.tenToChoose.update(dt);
        if (this.tenToChoose.isTimeUp()) {
            gsm.set(new PlayModeMulti(gsm, settings, countDown));
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect( this.none.getPosX(), this.none.getPosY() - this.none.getTexture().getHeight()/2 - HEIGHT/6, WIDTH*2/3+this.none.getTexture().getWidth(), HEIGHT/6);
        shapeRenderer.rect( this.none.getPosX(), this.none.getPosY() - this.none.getTexture().getHeight() - HEIGHT*2/6, WIDTH*2/3+this.none.getTexture().getWidth(), HEIGHT/6);
        shapeRenderer.end();

        sb.begin();
        tenToChooseGlyph.setText(Squarz.font, String.valueOf(this.tenToChoose.getWorldTimer()));
        Squarz.font.draw(sb, this.tenToChooseGlyph, WIDTH/2 - this.tenToChooseGlyph.width/2, HEIGHT - 2*this.tenToChooseGlyph.height);

        sb.draw(this.emptyField1.getTexture(), this.emptyField1.getPosX(), this.emptyField1.getPosY());
        sb.draw(this.emptyField2.getTexture(), this.emptyField2.getPosX(), this.emptyField2.getPosY());

        sb.draw(this.cleanButton.getTexture(), this.cleanButton.getPosX(), this.cleanButton.getPosY());
        Squarz.font2.draw(sb, clean, WIDTH/2 - clean.width/2, HEIGHT * 5/8 + clean.height/2);

        sb.draw(this.none.getTexture(), this.none.getPosX(), this.none.getPosY());
        sb.draw(this.punisher.getTexture(), this.punisher.getPosX(), this.punisher.getPosY());
        sb.draw(this.nurse.getTexture(), this.nurse.getPosX(), this.nurse.getPosY());
        sb.draw(this.mrPropre.getTexture(), this.mrPropre.getPosX(), this.mrPropre.getPosY());

        font2.draw(sb, this.bonusDescription1, this.none.getPosX() + (WIDTH*2/3+this.none.getTexture().getWidth())/2 - this.bonusDescription1.width/2,
                this.none.getPosY() - this.none.getTexture().getHeight()/2 - HEIGHT/6 + HEIGHT/6/2 + this.bonusDescription1.height/2);
        font2.draw(sb, this.bonusDescription2, this.none.getPosX() + (WIDTH*2/3+this.none.getTexture().getWidth())/2 - this.bonusDescription2.width/2,
                this.none.getPosY() - this.none.getTexture().getHeight() - HEIGHT*2/6 + HEIGHT/6/2 + this.bonusDescription2.height/2);
        sb.end();
    }

    @Override
    public void dispose() {

    }


    public void chosenBonus(int x, int y){
        if(this.emptyField1.contains(x, y)){
            this.emptyField1.setTexture(this.settings.getBonus1().getBonustexture(0));
            this.settings.getBonus1().setBonusKey(0);
            this.settings.getBonus1().setColorKey(3);
            this.isFilled1 = false;
        }

        if(this.emptyField2.contains(x, y)){
            this.emptyField2.setTexture(this.settings.getBonus2().getBonustexture(0));
            this.settings.getBonus2().setBonusKey(0);
            this.settings.getBonus2().setColorKey(3);
            this.isFilled2 = false;
        }

        if (this.none.contains(x, y)){
            if(this.isFilled1 && this.isFilled2) {
                this.emptyField2.setTexture(this.settings.getBonus2().getBonustexture(0));
                this.settings.getBonus2().setBonusKey(0);
                this.settings.getBonus2().setColorKey(3);
                this.isFilled2 = false;
            }else if(isFilled1 && !isFilled2){
                this.isFilled1 = false;
                this.emptyField1.setTexture(this.settings.getBonus1().getBonustexture(0));
                this.settings.getBonus1().setBonusKey(0);
                this.settings.getBonus1().setColorKey(3);
            }else {
                this.isFilled2 = false;
                this.isFilled1 = false;
            }
        }
        if (this.punisher.contains(x, y)){
            isFilled(1, 4);
        }
        if (this.nurse.contains(x, y)){
            isFilled(2, 5);
        }
        if (this.mrPropre.contains(x, y)){
            isFilled(3, 6);
        }

        this.bonusDescription1.setText(font2, this.settings.getDescriptionBonus1());
        this.bonusDescription2.setText(font2, this.settings.getDescriptionBonus2());
    }

    public void isFilled (int BonusKey, int colorKey){
        if(!this.isFilled1 && this.settings.getBonus2().getBonusKey() != BonusKey) {
            this.isFilled1 = true;
            this.emptyField1.setTexture(this.settings.getBonus1().getBonustexture(BonusKey));
            this.settings.getBonus1().setBonusKey(BonusKey);
            this.settings.getBonus1().setColorKey(colorKey);
        }else if(this.settings.getBonus1().getBonusKey() != BonusKey){
            this.isFilled2 = true;
            this.emptyField2.setTexture(this.settings.getBonus2().getBonustexture(BonusKey));
            this.settings.getBonus2().setBonusKey(BonusKey);
            this.settings.getBonus2().setColorKey(colorKey);
        }
    }
}
