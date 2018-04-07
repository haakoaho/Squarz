package com.mygdx.game.desktop;

import com.mygdx.game.model.MultiplayerInterface;

import java.util.Queue;

/**
 * Created by mathi on 03/04/2018.
 */

public class DesktopMultiPlayer implements MultiplayerInterface {

    @Override
    public Queue<Byte> popMoves() {
        return null;
    }

    @Override
    public void startQuickGame() {

    }

    @Override
    public void invite() {

    }

    @Override
    public boolean isMRoomId() {
        return false;
    }

    @Override
    public void Receive() {

    }

    @Override
    public void Accept() {

    }

    @Override
    public void decline() {

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
}