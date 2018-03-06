package com.mygdx.game.State.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.AI.Settings;
import com.mygdx.game.State.AIPreferencesState;
import com.mygdx.game.State.GameStateManager;
import com.mygdx.game.State.State;

/**
 * Created by mathi on 06/03/2018.
 */

public class AISetLevelState extends State{
    private Texture background, add, delete, easy, medium, hard, back;
    private Settings set;
    private int tap;


    public AISetLevelState(GameStateManager gsm){
        super(gsm);
        this.set = new Settings();
        this.tap = 0;
    }
    @Override
    public void handleInput() {

        if(Gdx.input.isTouched()){
            if(tap > 30){
                gsm.set(new AIPreferencesState(gsm));
            }
            else{
                tap ++;
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }
}
