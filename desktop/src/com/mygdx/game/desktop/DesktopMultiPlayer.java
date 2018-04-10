package com.mygdx.game.desktop;

import com.mygdx.game.model.MultiplayerInterface;

import java.util.Queue;

<<<<<<< HEAD
/**
 * Created by mathi on 03/04/2018.
 */

public class DesktopMultiPlayer  implements MultiplayerInterface {
=======


public class DesktopMultiPlayer implements MultiplayerInterface {
>>>>>>> Send+Invite

    @Override
    public Queue<Byte> popMoves() {
        return null;
    }

    @Override
    public void startQuickGame() {

    }

    @Override
<<<<<<< HEAD
=======
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


    @Override
>>>>>>> Send+Invite
    public void sendIncrement(Byte msg) {

    }

    @Override
    public void startSignInIntent() {

    }
<<<<<<< HEAD
}
=======

    @Override
    public void signInSilently() {

    }
}
>>>>>>> Send+Invite
