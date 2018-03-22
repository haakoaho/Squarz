package com.mygdx.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.State;

import java.util.Queue;

public class PlayModeMulti extends State {

    private Music music;

    public PlayModeMulti(GameStateManager gsm){
        super(gsm);
        music=Gdx.audio.newMusic(Gdx.files.internal("sound/here.mp3"));
        music.setLooping(true);
        music.setVolume(Squarz.valueVolume*0.1f);
        music.play();
    }

    @Override
    public void handleInput() {

        if (Gdx.input.justTouched()) {
           send((byte) 1);
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


    void send(byte b){
        gsm.getMultiplayerInterface().sendIncrement(b);

    }

    Queue<Byte> recieve(){
        return gsm.getMultiplayerInterface().popMoves();
    }
}
