package com.mygdx.game.desktop;

import com.mygdx.game.model.MultiplayerInterface;

import java.util.Queue;

<<<<<<< HEAD



public class DesktopMultiPlayer implements MultiplayerInterface {
=======
/**
 * Created by mathi on 03/04/2018.
 */

public class DesktopMultiPlayer  implements MultiplayerInterface {
>>>>>>> parent of 0aba017... Merge branch 'Send+Invite' into Lucas-le-BG-du-27

    @Override
    public Queue<Byte> popMoves() {
        return null;
    }

    @Override
    public void startQuickGame() {

    }

    @Override
<<<<<<< HEAD
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
=======
>>>>>>> parent of 0aba017... Merge branch 'Send+Invite' into Lucas-le-BG-du-27
    public void sendIncrement(Byte msg) {

    }

    @Override
    public void startSignInIntent() {

    }
<<<<<<< HEAD

    @Override
    public void signInSilently() {

    }
=======
>>>>>>> parent of 0aba017... Merge branch 'Send+Invite' into Lucas-le-BG-du-27
}
