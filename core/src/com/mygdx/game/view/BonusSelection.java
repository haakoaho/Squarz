package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.Bonus;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.format;

/**
 * Created by Max on 15/04/2018.
 */

public class BonusSelection extends State {

    private GlyphLayout bonusTitle;
    private Icon back, multi, killer, none, emptyField;
    private CountDown countDown;
    private PreferencesSettings settings;
    //private Texture bonusSelected;

    protected BonusSelection(GameStateManager gsm, PreferencesSettings settings, CountDown countDown) {
        super(gsm);

        this.bonusTitle = new GlyphLayout(Squarz.font, "CHOOSE YOUR BONUS");
        this.countDown = countDown;
        this.settings = settings;

        //this.bonusSelected = new Texture(Gdx.files.internal(format+"/bonuses/none.png"));
        //this.setBonusSelected(this.settings.getBonuses().getBonustexture(this.settings.getBonuses().getBonusKey()));

        back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);
        back.setPosX(back.getTexture().getWidth()/2);
        back.setPosY(back.getTexture().getHeight()/2);

        emptyField = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/none.png")),0,0);
        emptyField.setTexture(this.settings.getBonuses().getBonustexture(this.settings.getBonuses().getBonusKey()));
        emptyField.setPosX(WIDTH/2 - emptyField.getTexture().getWidth()/2);
        emptyField.setPosY(HEIGHT*3/4 - emptyField.getTexture().getHeight()/2);

        none = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/none.png")),0,0);
        none.setPosX(WIDTH*1/6 - none.getTexture().getWidth()/2);
        none.setPosY(HEIGHT/2 - none.getTexture().getHeight());

        killer = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/killer.png")),0,0);
        killer.setPosX(WIDTH*3/6 - killer.getTexture().getWidth()/2);
        killer.setPosY(HEIGHT/2 - killer.getTexture().getHeight());

        multi = new Icon(new Texture(Gdx.files.internal(format+"/bonuses/multi.png")),0,0);
        multi.setPosX(WIDTH*5/6 - multi.getTexture().getWidth()/2);
        multi.setPosY(HEIGHT/2 - multi.getTexture().getWidth());


    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (back.contains(x, y)){
                gsm.push(new AIPreferences(gsm, settings, countDown));
            }

            chosenBonus(x, y);


        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        Squarz.font.draw(sb, bonusTitle, WIDTH/2 - bonusTitle.width/2,HEIGHT-2*bonusTitle.height);
        sb.draw(back.getTexture(), back.getPosX(), back.getPosY());
        sb.draw(emptyField.getTexture(), emptyField.getPosX(), emptyField.getPosY());
        sb.draw(none.getTexture(), none.getPosX(), none.getPosY());
        sb.draw(killer.getTexture(), killer.getPosX(), killer.getPosY());
        sb.draw(multi.getTexture(), multi.getPosX(), multi.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {

    }

    public void chosenBonus(int x, int y){
        if(emptyField.contains(x, y)){
            gsm.push(new AIPreferences(gsm, settings, countDown));
        }
        if (none.contains(x, y)){
            this.emptyField.setTexture(this.settings.getBonuses().getBonustexture(0));
            this.settings.getBonuses().setBonusKey(0);
            this.settings.getBonuses().setColorKey(3);
        }
        if (killer.contains(x, y)){
            this.emptyField.setTexture(this.settings.getBonuses().getBonustexture(1));
            this.settings.getBonuses().setBonusKey(1);
            this.settings.getBonuses().setColorKey(4);
        }
        if (multi.contains(x, y)){
            this.emptyField.setTexture(this.settings.getBonuses().getBonustexture(2));
            this.settings.getBonuses().setBonusKey(2);
            this.settings.getBonuses().setColorKey(5);
        }
    }

    /*public Texture getBonusSelected() {
         return bonusSelected;
    }
    public void setBonusSelected(Texture bonusSelected) {
        this.bonusSelected = bonusSelected;
    }*/
    public Icon getEmptyField() {
        return emptyField;
    }
    public void setEmptyField(Icon emptyField) {
        this.emptyField = emptyField;
    }
}
