package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Icon;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;
import com.mygdx.game.view.beginning.Menu;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font2;
import static com.mygdx.game.Squarz.format;

/**
 * Created by mathi on 06/03/2018.
 */

public class SetAILevel extends State{
    private Icon add, delete, levelToDraw, back;
    private PreferencesSettings set;
    private CountDown countDown;
    private GlyphLayout choose, levelTitle;


    public SetAILevel(GameStateManager gsm, PreferencesSettings setting, CountDown countDown){
        super(gsm);
        this.add = new Icon(new Texture(Gdx.files.internal(format+"/add.png")),0,0);
        this.delete = new Icon(new Texture(Gdx.files.internal(format+"/delete.png")),0,0);
        this.levelToDraw = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/ai_levels/beginer.png")),0,0);
        this.set = setting;
        this.countDown = countDown;
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);

        this.levelTitle = new GlyphLayout(Squarz.font, "CHOOSE AI LEVEL");
        this.choose = new GlyphLayout(font2,this.set.getStringLevel());

        add.setPosX(WIDTH/2-add.getTexture().getWidth()/2);
        add.setPosY(HEIGHT*2/3-add.getTexture().getHeight()/2);
        delete.setPosX(WIDTH/2-delete.getTexture().getWidth()/2);
        delete.setPosY(HEIGHT/3-delete.getTexture().getHeight()/2);
        levelToDraw.setPosX(WIDTH/2-levelToDraw.getTexture().getWidth()/2);
        levelToDraw.setPosY(HEIGHT/2-levelToDraw.getTexture().getHeight()/2);
        back.setPosX(back.getTexture().getWidth()/2);
        back.setPosY(back.getTexture().getHeight()/2);


        setTextureToDraw();
    }
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(add.contains(x,y)){ //add
                this.set.AILevelUp();
                this.choose = new GlyphLayout(font2,this.set.getStringLevel());
                setTextureToDraw();
            }
            if(delete.contains(x,y)){ //delete
                this.set.AILevelDown();
                this.choose = new GlyphLayout(font2,this.set.getStringLevel());
                setTextureToDraw();
            }
            if(levelToDraw.contains(x,y)){//go back
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, set, countDown));
            }
            if (back.contains(x,y)) {
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, set, countDown));
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
        Squarz.font.draw(sb, levelTitle, WIDTH/2 - levelTitle.width/2,HEIGHT-2*levelTitle.height);
        font2.draw(sb, this.choose, WIDTH/2 - this.choose.width/2, 8*HEIGHT/10 - this.choose.height/2);
        sb.draw(add.getTexture(), add.getPosX(),add.getPosY() );
        sb.draw(delete.getTexture(),delete.getPosX() ,delete.getPosY() );
        sb.draw(levelToDraw.getTexture(),levelToDraw.getPosX() , levelToDraw.getPosY());
        sb.draw(back.getTexture(),back.getPosX(),back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {
    }

    public void setTextureToDraw(){
        if(set.getLevelKey()==0){
            levelToDraw.setTexture(new Texture(Gdx.files.internal(format+"/ai_settings/ai_levels/beginer.png")));
        }
        if(set.getLevelKey()==1){
            levelToDraw.setTexture(new Texture(Gdx.files.internal(format+"/ai_settings/ai_levels/medium.png")));
        }
        if(set.getLevelKey()==2){
            levelToDraw.setTexture(new Texture(Gdx.files.internal(format+"/ai_settings/ai_levels/advanced.png")));
        }
        if(set.getLevelKey()==3){
            levelToDraw.setTexture(new Texture(Gdx.files.internal(format+"/ai_settings/ai_levels/expert.png")));
        }
    }
}