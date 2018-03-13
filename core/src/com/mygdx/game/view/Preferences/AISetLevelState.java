package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.view.AIPreferences;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 06/03/2018.
 */

public class AISetLevelState extends State{
    private Texture background, add, delete, beginer, medium, advanced, expert, back;
    private PreferencesSettings set;


    public AISetLevelState(GameStateManager gsm){
        super(gsm);
        this.set = new PreferencesSettings();
        this.background = new Texture(Gdx.files.internal("background.png"));
        this.add = new Texture(Gdx.files.internal("add.png"));
        this.delete = new Texture(Gdx.files.internal("delete.png"));
        this.back = new Texture(Gdx.files.internal("back.png"));
    }
    @Override
    public void handleInput() {

        if(Gdx.input.isTouched()){
            if(Gdx.input.getY()>Gdx.graphics.getHeight()*5/6-back.getHeight()/2){
                gsm.set(new AIPreferences(gsm));
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
        sb.draw(background, 0, 0, WIDTH, HEIGHT);
        for(int i=0; i<4; i++){
            sb.draw(add, WIDTH/3-add.getWidth()/2, HEIGHT*(5-i)/6-add.getHeight()/2);
            sb.draw(delete, WIDTH*2/3-delete.getWidth()/2, HEIGHT*(5-i)/6-delete.getHeight()/2);
        }
        sb.draw(back, WIDTH/2 - back.getWidth()/2, HEIGHT/6-back.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
