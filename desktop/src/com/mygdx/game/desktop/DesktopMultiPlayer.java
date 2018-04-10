package com.mygdx.game.desktop;

import com.mygdx.game.model.MultiplayerInterface;

import java.util.Queue;

<<<<<<< HEAD



public class DesktopMultiPlayer implements MultiplayerInterface {
/**
 * Created by mathi on 03/04/2018.
 */


=======


public class DesktopMultiPlayer implements MultiplayerInterface {
>>>>>>> Send+Invite
    @Override
    public Queue<Byte> popMoves() {
        return null;
    }

    @Override
    public void sendIncrement(Byte msg) {

    }
    /*

    @Override
<<<<<<< HEAD
    public void invite() {
=======
    public void startSignInIntent() {
>>>>>>> Send+Invite

    }

    @Override
    public void signInSilently() {

    }

    @Override
    public void startQuickGame() {

    }

    @Override
    public void invite() {

    } */

    @Override
<<<<<<< HEAD
    public void sendIncrement(Byte msg) {

=======
    public boolean isGameReady() {
        return false;
>>>>>>> Send+Invite
    }

    @Override
    public void checkForInvitation() {

    }

<<<<<<< HEAD
    // @Override
    public void signInSilently() {

    }
}
=======
    @Override
    public void leaveRoom() {

    }
}
>>>>>>> Send+Invite
