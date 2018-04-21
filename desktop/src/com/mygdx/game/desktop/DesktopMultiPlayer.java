package com.mygdx.game.desktop;

import com.mygdx.game.model.multiplayer.MultiplayerInterface;

import java.util.Queue;


public class DesktopMultiPlayer implements MultiplayerInterface {
    @Override
    public Queue<Byte> popMoves() {
        return null;
    }

    @Override
    public void sendIncrement(Byte msg) {

    }

    @Override
    public void startSignInIntent() {

    }

    @Override
    public void signInSilently() {

    }

    @Override
    public void startQuickGame() {

    }

    @Override
    public void invite() {

    }

    @Override
    public boolean isGameReady() {
        return false;
    }

    @Override
    public void checkForInvitation() {

    }

    @Override
    public void leaveRoom() {

    }


}