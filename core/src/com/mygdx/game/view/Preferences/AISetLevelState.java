package com.mygdx.game.view.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.aI.Settings;
import com.mygdx.game.view.AIPreferencesState;
import com.mygdx.game.view.GameStateManager;
import com.mygdx.game.model.State;

/**
 * Created by mathi on 06/03/2018.
 */

public class AISetLevelState extends State{
    private Texture background, add, delete, beginer, medium, advanced, expert, back;
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
        sb.begin();
        sb.draw(background, 0, 0, Squarz.WIDTH, Squarz.HEIGHT);
        for(int i=0; i<4; i++){

        }
        sb.end();
    }

    @Override
    public void dispose() {

    }
}