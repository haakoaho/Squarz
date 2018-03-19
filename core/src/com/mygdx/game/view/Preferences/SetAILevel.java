package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.Icon;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;
import com.mygdx.game.view.beginning.Menu;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 06/03/2018.
 */

public class SetAILevel extends State{
    private Icon add, delete, levelToDraw, back;
    private PreferencesSettings set;


    public SetAILevel(GameStateManager gsm, PreferencesSettings setting){
        super(gsm);
        add = new Icon(new Texture(Gdx.files.internal("ai_settings/add.png")),0,0);
        delete = new Icon(new Texture(Gdx.files.internal("ai_settings/delete.png")),0,0);
        levelToDraw = new Icon(new Texture(Gdx.files.internal("ai_settings/ai_levels/beginer.png")),0,0);
        back = new Icon(new Texture(Gdx.files.internal("back.png")),0,0);
        set = setting;

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
                setTextureToDraw();
            }
            if(delete.contains(x,y)){ //delete
                this.set.AILevelDown();
                setTextureToDraw();
            }
            if(levelToDraw.contains(x,y)){
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, set));
            }
            if (back.contains(x,y)) {
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm, set));
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
            levelToDraw.setTexture(new Texture(Gdx.files.internal("ai_settings/ai_levels/beginer.png")));
        }
        if(set.getLevelKey()==1){
            levelToDraw.setTexture(new Texture(Gdx.files.internal("ai_settings/ai_levels/medium.png")));
        }
        if(set.getLevelKey()==2){
            levelToDraw.setTexture(new Texture(Gdx.files.internal("ai_settings/ai_levels/advanced.png")));
        }
        if(set.getLevelKey()==3){
            levelToDraw.setTexture(new Texture(Gdx.files.internal("ai_settings/ai_levels/expert.png")));
        }
    }



}
