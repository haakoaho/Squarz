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
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import com.mygdx.game.view.AIPreferences;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font2;
import static com.mygdx.game.Squarz.format;

// CLEAN //
public class BonusSelection extends State {
    private final GlyphLayout bonusTitle;
    private final GlyphLayout clean;
    private final GlyphLayout selected;
    private final GlyphLayout bonusDescription1;
    private final GlyphLayout bonusDescription2;
    private final Icon back;
    private final Icon nurse;
    private final Icon punisher;
    private final Icon none;
    private final Icon mrPropre;
    private final Icon field1;
    private final Icon field2;
    private final Icon cleanButton;
    private final Icon selectButton;
    private final ICountdownDuration countDown;
    private final PreferencesSettings settings;
    private Boolean isFilled1, isFilled2;
    private final ShapeRenderer shapeRenderer;

    public BonusSelection(GameStateManager gsm, PreferencesSettings settings, ICountdownDuration countDown) {
        super(gsm);

        this.countDown = countDown; // set the coutDown
        this.settings = settings; // set the AI settings

        this.isFilled1 = false; // true if the first slot of bonus is filled, else false
        this.isFilled2 = false; // true if the second slot of binus is filled, else false

        this.back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);
        this.none = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/none.png")),0,0);
        this.cleanButton = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/button.png")), 0, 0); // touch this button to remove your selection
        this.selectButton = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/button.png")), 0, 0); // touch this button to select definitely your bonuses
        this.field1 = new Icon(this.settings.getBonus1().getBonustexture(this.settings.getBonus1().getBonusKey()),0,0);
        this.field2 = new Icon(this.settings.getBonus2().getBonustexture(this.settings.getBonus2().getBonusKey()),0,0);
        this.punisher = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/punisher.png")),0,0);
        this.nurse = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/nurse.png")),0,0);
        this.mrPropre = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/mrPropre.png")),0,0);

        this.back.setPosX(back.getTexture().getWidth()/2);
        this.back.setPosY(back.getTexture().getHeight()/2);
        this.none.setPosX(WIDTH /6 - this.none.getTexture().getWidth()/2);
        this.none.setPosY(HEIGHT/2 - this.none.getTexture().getHeight()/2);
        this.cleanButton.setPosX(WIDTH /3 - this.cleanButton.getTexture().getWidth()/2 - this.none.getTexture().getWidth()/2);
        this.cleanButton.setPosY(HEIGHT * 5/8 - this.cleanButton.getTexture().getHeight()/2);
        this.selectButton.setPosX(WIDTH*2/3 - this.cleanButton.getTexture().getWidth()/2 + this.none.getTexture().getWidth()/2);
        this.selectButton.setPosY(HEIGHT * 5/8 - this.cleanButton.getTexture().getHeight()/2);
        this.field1.setPosX(WIDTH/3 - this.field1.getTexture().getWidth()/2);
        this.field1.setPosY(HEIGHT*3/4 - this.field1.getTexture().getHeight()/2);
        this.field2.setPosX(WIDTH*2/3 - this.field2.getTexture().getWidth()/2);
        this.field2.setPosY(HEIGHT*3/4 - this.field2.getTexture().getHeight()/2);
        this.punisher.setPosX(WIDTH /6 + WIDTH*2/9- this.punisher.getTexture().getWidth()/2);
        this.punisher.setPosY(HEIGHT/2 - this.punisher.getTexture().getHeight()/2);
        this.nurse.setPosX(WIDTH /6 + WIDTH*4/9 - this.nurse.getTexture().getWidth()/2);
        this.nurse.setPosY(HEIGHT/2 - this.nurse.getTexture().getWidth()/2);
        this.mrPropre.setPosX(WIDTH*5/6 - this.nurse.getTexture().getWidth()/2);
        this.mrPropre.setPosY(HEIGHT/2 - this.nurse.getTexture().getWidth()/2);

        this.bonusTitle = new GlyphLayout(Squarz.font, "CHOOSE YOUR BONUSES");
        this.clean = new GlyphLayout(Squarz.font2, "CLEAN ALL");
        this.selected = new GlyphLayout(Squarz.font2, "SELECT");
        this.bonusDescription1 = new GlyphLayout(font2, this.settings.getDescriptionBonus1());
        this.bonusDescription2 = new GlyphLayout(font2, this.settings.getDescriptionBonus2());

        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (this.back.contains(x, y)){
                gsm.push(new AIPreferences(gsm, settings, countDown)); // go back to the Settings selection menu
            }
            if(this.selectButton.contains(x, y)){
                gsm.push(new AIPreferences(gsm, settings, countDown)); // go back to the Settings selection menu
            }

            chosenBonus(x,y); // select a bonus if you click on its icon

            if (cleanButton.contains(x, y)){ // your selection is removed if you click on the clean button
                this.isFilled1 = false;
                this.isFilled2 = false;
                this.field1.setTexture(this.none.getTexture());
                this.field2.setTexture(this.none.getTexture());
                this.settings.getBonus1().setBonusKey(0);
                this.settings.getBonus2().setBonusKey(0);
                this.bonusDescription1.setText(font2, this.settings.getDescriptionBonus1());
                this.bonusDescription2.setText(font2, this.settings.getDescriptionBonus2());
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

        // to draw the rectangles
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect( this.none.getPosX(), this.none.getPosY() - this.none.getTexture().getHeight()/2 - HEIGHT/6, WIDTH*2/3+this.none.getTexture().getWidth(), HEIGHT/6);
        shapeRenderer.rect( this.none.getPosX(), this.none.getPosY() - this.none.getTexture().getHeight() - HEIGHT*2/6, WIDTH*2/3+this.none.getTexture().getWidth(), HEIGHT/6);
        shapeRenderer.end();

        sb.begin();
        Squarz.font.draw(sb, this.bonusTitle, WIDTH/2 - this.bonusTitle.width/2,HEIGHT-2*this.bonusTitle.height);

        sb.draw(this.field1.getTexture(), this.field1.getPosX(), this.field1.getPosY());
        sb.draw(this.field2.getTexture(), this.field2.getPosX(), this.field2.getPosY());

        sb.draw(this.cleanButton.getTexture(), this.cleanButton.getPosX(), this.cleanButton.getPosY());
        Squarz.font2.draw(sb, this.clean, WIDTH /3 - this.clean.width/2 - this.none.getTexture().getWidth()/2, HEIGHT * 5/8 + this.clean.height/2);
        sb.draw(this.selectButton.getTexture(), this.selectButton.getPosX(), this.selectButton.getPosY());
        Squarz.font2.draw(sb, this.selected, WIDTH * 2/3 - this.selected.width/2 + this.none.getTexture().getWidth()/2, HEIGHT * 5/8 + this.selected.height/2);

        sb.draw(this.none.getTexture(), this.none.getPosX(), this.none.getPosY());
        sb.draw(this.punisher.getTexture(), this.punisher.getPosX(), this.punisher.getPosY());
        sb.draw(this.nurse.getTexture(), this.nurse.getPosX(), this.nurse.getPosY());
        sb.draw(this.mrPropre.getTexture(), this.mrPropre.getPosX(), this.mrPropre.getPosY());

        font2.draw(sb, this.bonusDescription1, this.none.getPosX() + (WIDTH*2/3+this.none.getTexture().getWidth())/2 - this.bonusDescription1.width/2,
                this.none.getPosY() - this.none.getTexture().getHeight()/2 - HEIGHT/6 + HEIGHT/6/2 + this.bonusDescription1.height/2);
        font2.draw(sb, this.bonusDescription2, this.none.getPosX() + (WIDTH*2/3+this.none.getTexture().getWidth())/2 - this.bonusDescription2.width/2,
                this.none.getPosY() - this.none.getTexture().getHeight() - HEIGHT*2/6 + HEIGHT/6/2 + this.bonusDescription2.height/2);

        sb.draw(this.back.getTexture(), this.back.getPosX(), this.back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {
    }

    private void chosenBonus(int x, int y){ // called each time you touch the screen, this function will select the bonus you want to use
        if(this.field1.contains(x, y)){ // if field1 is touched, it removes the bonus 1 previously selected
            this.field1.setTexture(this.settings.getBonus1().getBonustexture(0));
            this.settings.getBonus1().setBonusKey(0);
            this.settings.getBonus1().setColorKey(3);
            this.isFilled1 = false;
        }
        if(this.field2.contains(x, y)){ // if field1 is touched, it removes the bonus 2 previously selected
            this.field2.setTexture(this.settings.getBonus2().getBonustexture(0));
            this.settings.getBonus2().setBonusKey(0);
            this.settings.getBonus2().setColorKey(3);
            this.isFilled2 = false;
        }
        if (this.none.contains(x, y)){ // if none is touched, one bonus is subsituted by nothing (no bonus)
            if(this.isFilled1 && this.isFilled2) {
                this.field2.setTexture(this.settings.getBonus2().getBonustexture(0));
                this.settings.getBonus2().setBonusKey(0);
                this.settings.getBonus2().setColorKey(3);
                this.isFilled2 = false;
            }else if(this.isFilled1 && !this.isFilled2){
                this.field1.setTexture(this.settings.getBonus1().getBonustexture(0));
                this.settings.getBonus1().setBonusKey(0);
                this.settings.getBonus1().setColorKey(3);
                this.isFilled1 = false;
            }else {
                this.isFilled2 = false;
                this.isFilled1 = false;
            }
        }
        if (this.punisher.contains(x, y)){ // if you want to use the bonus Punisher
            isFilled(1, 4);
        }
        if (this.nurse.contains(x, y)){ // if you want to use the bonus Nurse
            isFilled(2, 5);
        }
        if (this.mrPropre.contains(x, y)){ // if you want to use the bonus Mr. Propre
            isFilled(3, 6);
        }

        this.bonusDescription1.setText(font2, this.settings.getDescriptionBonus1());
        this.bonusDescription2.setText(font2, this.settings.getDescriptionBonus2());
    }

    private void isFilled(int BonusKey, int colorKey){ // fill the bonus field with your choice (the bonus you have selected)
        if(!this.isFilled1 && this.settings.getBonus2().getBonusKey() != BonusKey) {
            this.isFilled1 = true;
            this.field1.setTexture(this.settings.getBonus1().getBonustexture(BonusKey));
            this.settings.getBonus1().setBonusKey(BonusKey);
            this.settings.getBonus1().setColorKey(colorKey);
        }else if(this.settings.getBonus1().getBonusKey() != BonusKey){
            this.isFilled2 = true;
            this.field2.setTexture(this.settings.getBonus2().getBonustexture(BonusKey));
            this.settings.getBonus2().setBonusKey(BonusKey);
            this.settings.getBonus2().setColorKey(colorKey);
        }
    }
}
