package com.mygdx.game.model;


import java.util.Queue;

//platform specific code for implementing multiplayer
public interface MultiplayerInterface {
    Queue<Byte> popMoves(); // Receives buffered data from the opponent
    void sendIncrement(Byte msg); //sends a byte of data to the oppoent
    void startSignInIntent();  // sign in to Google's API, use this when you are not signed in
    void signInSilently(); // sign in silently to Google's API use this when you are already signed in

    void startQuickGame();  //request a quick game lobby
    void invite(); //Create a room and invite players to join
    boolean isGameReady(); // returns if the game is ready to be played
    void checkForInvitation(); //receive an invitation
    void leaveRoom(); //game is over we destroy the room

    void writeToLog(String s); //writes data to the log for debugging purposes
}
