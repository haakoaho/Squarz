package com.mygdx.game.model;


import java.util.Queue;

//platform specific code for implementing multiplayer
public interface MultiplayerInterface {
    public Queue<Byte> popMoves(); // Receives buffered data from the opponent
    public void startQuickGame();  //request a quick game lobby
    public void sendIncrement(Byte msg ); //sends a byte of data to the oppoent
    public void startSignInIntent();  // sign in to multiplayer server
}
