package com.mygdx.game.model;


import java.util.Queue;

//platform specific code for implementing multiplayer
public interface MultiplayerInterface {
    public Queue<Byte> popMoves(); // Receives buffered data from the opponent
    public void sendIncrement(Byte msg ); //sends a byte of data to the oppoent
    public void startSignInIntent();  // sign in to Google's API, use this when you are not signed in
    public void signInSilently(); // sign in silently to Google's API use this when you are already signed in

    public void startQuickGame();  //request a quick game lobby
    public void invite(); //Create a room and invite players to join
    public boolean isMRoomId(); //checks if a room is created
    public void Receive(); //receive an invitation
}
