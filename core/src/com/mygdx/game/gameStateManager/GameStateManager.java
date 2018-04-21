package com.mygdx.game.gameStateManager;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.model.MultiplayerInterface;

import java.util.Stack;


public class GameStateManager {
    private Stack<State> states;

    private MultiplayerInterface multiplayerInterface;

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop();
    }

    public void set(State state){
        states.pop();
        states.push(state);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

    public void setMultiplayerInterface(MultiplayerInterface multiplayerInterface) {
        this.multiplayerInterface = multiplayerInterface;
    }

    public MultiplayerInterface getMultiplayerInterface() {
        return multiplayerInterface;
    }
}
