package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.view.AIPreferences;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

/**
 * Created by mathi on 06/03/2018.
 */

public class SetAILevel extends State{
    private Texture add, delete, levelToDraw, beginer, medium, advanced, expert;
    private PreferencesSettings set;


    public SetAILevel(GameStateManager gsm, PreferencesSettings setting){
        super(gsm);
        this.set = setting;

        this.add = new Texture(Gdx.files.internal("ai_settings/add.png"));
        this.delete = new Texture(Gdx.files.internal("ai_settings/delete.png"));
        this.beginer = new Texture(Gdx.files.internal("ai_settings/ai_levels/beginer.png"));
        this.medium = new Texture(Gdx.files.internal("ai_settings/ai_levels/medium.png"));
        this.advanced = new Texture(Gdx.files.internal("ai_settings/ai_levels/advanced.png"));
        this.expert = new Texture(Gdx.files.internal("ai_settings/ai_levels/expert.png"));

        setTextureToDraw();
    }
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            if(Gdx.input.getY()<Gdx.graphics.getHeight()/3+add.getHeight()/2){ //add
                this.set.AILevelUp();
                setTextureToDraw();
            }
            if(Gdx.input.getY()>Gdx.graphics.getHeight()*2/3-delete.getHeight()/2){ //delete
                this.set.AILevelDown();
                setTextureToDraw();
            }
            if(Gdx.input.getY()>Gdx.graphics.getHeight()/3+add.getHeight()/2 && Gdx.input.getY()<Gdx.graphics.getHeight()*2/3-delete.getHeight()/2){
                gsm.set(new AIPreferences(gsm, set));
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
        sb.draw(add, Gdx.graphics.getWidth()/2-add.getWidth()/2, Gdx.graphics.getHeight()*2/3-add.getHeight()/2);
        sb.draw(delete, Gdx.graphics.getWidth()/2-add.getWidth()/2, Gdx.graphics.getHeight()/3-add.getHeight()/2);
        sb.draw(levelToDraw, Gdx.graphics.getWidth()/2-levelToDraw.getWidth()/2, Gdx.graphics.getHeight()/2-levelToDraw.getHeight()/2);

        sb.end();
    }

    @Override
    public void dispose() {
    }

    public void setTextureToDraw(){
        if(set.getLevelKey()==0){
            this.levelToDraw = this.beginer;
        }
        if(set.getLevelKey()==1){
            this.levelToDraw = this.medium;
        }
        if(set.getLevelKey()==2){
            this.levelToDraw = this.advanced;
        }
        if(set.getLevelKey()==3){
            this.levelToDraw = this.expert;
        }
    }



}
