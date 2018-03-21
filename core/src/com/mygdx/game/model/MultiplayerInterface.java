package com.mygdx.game.model;


//platform specific code for implementing multiplayer
public interface MultiplayerInterface {
    public void startQuickGame();
    public void sendIncrement(Byte msg);
}
