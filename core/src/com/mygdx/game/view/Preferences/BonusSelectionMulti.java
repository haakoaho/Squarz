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

/**
 * Created by Max on 15/04/2018.
 */

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
        this.countDown = new CountDown(60);
        this.tenToChoose = new CountDown(10);

        this.tenToChooseGlyph = new GlyphLayout(Squarz.font, String.valueOf(this.tenToChoose.getWorldTimer()));
        this.clean = new GlyphLayout(Squarz.font2, "CLEAN ALL");

        this.isFilled1 = false;
        this.isFilled2 = false;

        bonusDescription1 = new GlyphLayout(font2, this.settings.getDescriptionBonus1());
        bonusDescription2 = new GlyphLayout(font2, this.settings.getDescriptionBonus2());

        this.shapeRenderer = new ShapeRenderer();

        none = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/none.png")),0,0);
        none.setPosX(WIDTH*1/6 - none.getTexture().getWidth()/2);
        none.setPosY(HEIGHT/2 - none.getTexture().getHeight()/2);

        cleanButton = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/button.png")), 0, 0);
        cleanButton.setPosX(WIDTH/2 - cleanButton.getTexture().getWidth()/2);
        cleanButton.setPosY(HEIGHT * 5/8 - cleanButton.getTexture().getHeight()/2);

        emptyField1 = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/none.png")),0,0);
        emptyField1.setTexture(this.settings.getBonus1().getBonustexture(this.settings.getBonus1().getBonusKey()));
        emptyField1.setPosX(WIDTH/3 - emptyField1.getTexture().getWidth()/2);
        emptyField1.setPosY(HEIGHT*3/4 - emptyField1.getTexture().getHeight()/2);

        emptyField2 = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/none.png")),0,0);
        emptyField2.setTexture(this.settings.getBonus2().getBonustexture(this.settings.getBonus2().getBonusKey()));
        emptyField2.setPosX(WIDTH*2/3 - emptyField2.getTexture().getWidth()/2);
        emptyField2.setPosY(HEIGHT*3/4 - emptyField2.getTexture().getHeight()/2);

        punisher = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/punisher.png")),0,0);
        punisher.setPosX(WIDTH*1/6 + WIDTH*2/9- punisher.getTexture().getWidth()/2);
        punisher.setPosY(HEIGHT/2 - punisher.getTexture().getHeight()/2);

        nurse = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/nurse.png")),0,0);
        nurse.setPosX(WIDTH*1/6 + WIDTH*4/9 - nurse.getTexture().getWidth()/2);
        nurse.setPosY(HEIGHT/2 - nurse.getTexture().getWidth()/2);

        mrPropre = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/mrPropre.png")),0,0);
        mrPropre.setPosX(WIDTH*5/6 - nurse.getTexture().getWidth()/2);
        mrPropre.setPosY(HEIGHT/2 - nurse.getTexture().getWidth()/2);



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
        Squarz.font.draw(sb, tenToChooseGlyph, WIDTH/2 - tenToChooseGlyph.width/2, HEIGHT - 2*tenToChooseGlyph.height);

        sb.draw(emptyField1.getTexture(), emptyField1.getPosX(), emptyField1.getPosY());
        sb.draw(emptyField2.getTexture(), emptyField2.getPosX(), emptyField2.getPosY());

        sb.draw(cleanButton.getTexture(), cleanButton.getPosX(), cleanButton.getPosY());
        Squarz.font2.draw(sb, clean, WIDTH/2 - clean.width/2, HEIGHT * 5/8 + clean.height/2);

        sb.draw(none.getTexture(), none.getPosX(), none.getPosY());
        sb.draw(punisher.getTexture(), punisher.getPosX(), punisher.getPosY());
        sb.draw(nurse.getTexture(), nurse.getPosX(), nurse.getPosY());
        sb.draw(mrPropre.getTexture(), mrPropre.getPosX(), mrPropre.getPosY());

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
        if(emptyField1.contains(x, y)){
            this.emptyField1.setTexture(this.settings.getBonus1().getBonustexture(0));
            this.settings.getBonus1().setBonusKey(0);
            this.settings.getBonus1().setColorKey(3);
            isFilled1 = false;
        }

        if(emptyField2.contains(x, y)){
            this.emptyField2.setTexture(this.settings.getBonus2().getBonustexture(0));
            this.settings.getBonus2().setBonusKey(0);
            this.settings.getBonus2().setColorKey(3);
            isFilled2 = false;
        }

        if (none.contains(x, y)){
            if(isFilled1 && isFilled2) {
                this.emptyField2.setTexture(this.settings.getBonus2().getBonustexture(0));
                this.settings.getBonus2().setBonusKey(0);
                this.settings.getBonus2().setColorKey(3);

                isFilled2 = false;
            }else if(isFilled1 && !isFilled2){
                isFilled1 = false;
                this.emptyField1.setTexture(this.settings.getBonus1().getBonustexture(0));
                this.settings.getBonus1().setBonusKey(0);
                this.settings.getBonus1().setColorKey(3);
            }else {
                isFilled2 = false;
                isFilled1 = false;
            }
        }
        if (punisher.contains(x, y)){
            isFilled(1, 4);
        }
        if (nurse.contains(x, y)){
            isFilled(2, 5);
        }
        if (mrPropre.contains(x, y)){
            isFilled(3, 6);
        }

        bonusDescription1.setText(font2, this.settings.getDescriptionBonus1());
        bonusDescription2.setText(font2, this.settings.getDescriptionBonus2());
    }
    public void isFilled (int BonusKey, int colorKey){
        if(!isFilled1 && this.settings.getBonus2().getBonusKey() != BonusKey) {
            isFilled1 = true;
            this.emptyField1.setTexture(this.settings.getBonus1().getBonustexture(BonusKey));
            this.settings.getBonus1().setBonusKey(BonusKey);
            this.settings.getBonus1().setColorKey(colorKey);
        }else if(this.settings.getBonus1().getBonusKey() != BonusKey){
            isFilled2 = true;
            this.emptyField2.setTexture(this.settings.getBonus2().getBonustexture(BonusKey));
            this.settings.getBonus2().setBonusKey(BonusKey);
            this.settings.getBonus2().setColorKey(colorKey);
        }
    }
}
