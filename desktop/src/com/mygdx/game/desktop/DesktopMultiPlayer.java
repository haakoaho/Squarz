package com.mygdx.game.desktop;

import com.mygdx.game.model.MultiplayerInterface;

import java.util.Queue;




public class DesktopMultiPlayer implements MultiplayerInterface {
/**
 * Created by mathi on 03/04/2018.
 */


    @Override
    public Queue<Byte> popMoves() {
        return null;
    }

    @Override
    public void startQuickGame() {

    }
    /*

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

    } */


    @Override
    public void sendIncrement(Byte msg) {

    }

    @Override
    public void startSignInIntent() {

    }

    // @Override
    public void signInSilently() {

    }
}
