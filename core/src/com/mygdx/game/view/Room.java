package com.mygdx.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;

import java.util.ArrayList;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

public class Room extends State {

    Icon quick = new Icon(new Texture(Gdx.files.internal("modes/quick.png")),0,0);

    public Room(GameStateManager gsm) {
        super(gsm);
        gsm.getMultiplayerInterface().startSignInIntent();

        quick.setPosX(3*WIDTH/4-quick.getTexture().getWidth()/2);
        quick.setPosY(7*HEIGHT/10-quick.getTexture().getHeight()/2);


    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            ArrayList<String> invitee = new ArrayList<String>();
            invitee.add("kevinmyr"); //TODO fetch this from the user input place this and the line below in handleInput()
            gsm.getMultiplayerInterface().signInSilently();
            gsm.getMultiplayerInterface().Invite(invitee);
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        //TODO text input field with a submit button
        sb.begin();
        sb.draw(quick.getTexture(),quick.getPosX() ,quick.getPosY() );
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
