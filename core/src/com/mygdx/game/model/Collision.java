package com.mygdx.game.model;

/**
 * Created by Antoine Dc on 14/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class Collision {

    public Collision() {
    }

    public void collision( Player player, AIPlayer computer, Boolean firstTouch) {
        if (firstTouch) {
            if (!player.getLeft().isEmpty() && !computer.getComputer().getLeft().isEmpty() &&
                    player.getLeft().get(0).getRectangle().overlaps(computer.getComputer().getLeft().get(0).getRectangle())) {
                switch (player.getLeftColor().get(0)) {
                    case 0:
                        switch (computer.getComputer().getLeftColor().get(0)) {
                            case 0:
                                player.getLeft().remove(player.getLeft().get(0));
                                computer.getComputer().getLeft().remove(computer.getComputer().getLeft().get(0));
                                break;

                            case 1:
                                player.getLeft().remove(player.getLeft().get(0));
                                break;

                            case 2:
                                player.getLeft().remove(player.getLeft().get(0));
                                break;
                        }
                        break;

                    case 1:
                        switch (computer.getComputer().getLeftColor().get(0)) {
                            case 0:
                                computer.getComputer().getLeft().remove(computer.getComputer().getLeft().get(0));
                                break;

                            case 1:
                                player.getLeft().remove(player.getLeft().get(0));
                                computer.getComputer().getLeft().remove(computer.getComputer().getLeft().get(0));
                                break;

                            case 2:
                                player.getLeft().remove(player.getLeft().get(0));
                                break;
                        }
                        break;

                    case 2:
                        switch (computer.getComputer().getLeftColor().get(0)) {
                            case 0:
                                player.getLeft().remove(player.getLeft().get(0));
                                break;

                            case 1:
                                computer.getComputer().getLeft().remove(computer.getComputer().getLeft().get(0));
                                break;

                            case 2:
                                player.getLeft().remove(player.getLeft().get(0));
                                computer.getComputer().getLeft().remove(computer.getComputer().getLeft().get(0));
                                break;
                        }
                        break;
                }
            }


            if (!player.getMiddle().isEmpty() && !computer.getComputer().getMiddle().isEmpty() &&
                    player.getMiddle().get(0).getRectangle().overlaps(computer.getComputer().getMiddle().get(0).getRectangle())) {
                switch (player.getMiddleColor().get(0)) {
                    case 0:
                        switch (computer.getComputer().getMiddleColor().get(0)) {
                            case 0:
                                player.getMiddle().remove(player.getMiddle().get(0));
                                computer.getComputer().getMiddle().remove(computer.getComputer().getMiddle().get(0));
                                break;

                            case 1:
                                player.getMiddle().remove(player.getMiddle().get(0));
                                break;

                            case 2:
                                player.getMiddle().remove(player.getMiddle().get(0));
                                break;
                        }
                        break;

                    case 1:
                        switch (computer.getComputer().getMiddleColor().get(0)) {
                            case 0:
                                computer.getComputer().getMiddle().remove(computer.getComputer().getMiddle().get(0));
                                break;

                            case 1:
                                player.getMiddle().remove(player.getMiddle().get(0));
                                computer.getComputer().getMiddle().remove(computer.getComputer().getMiddle().get(0));
                                break;

                            case 2:
                                player.getMiddle().remove(player.getMiddle().get(0));
                                break;
                        }
                        break;

                    case 2:
                        switch (computer.getComputer().getMiddleColor().get(0)) {
                            case 0:
                                player.getMiddle().remove(player.getMiddle().get(0));
                                break;

                            case 1:
                                computer.getComputer().getMiddle().remove(computer.getComputer().getMiddle().get(0));
                                break;

                            case 2:
                                player.getMiddle().remove(player.getMiddle().get(0));
                                computer.getComputer().getMiddle().remove(computer.getComputer().getMiddle().get(0));
                                break;
                        }
                        break;
                }
            }

            if (!player.getRight().isEmpty() && !computer.getComputer().getRight().isEmpty() &&
                    player.getRight().get(0).getRectangle().overlaps(computer.getComputer().getRight().get(0).getRectangle())) {
                switch (player.getRightColor().get(0)) {
                    case 0:
                        switch (computer.getComputer().getRightColor().get(0)) {
                            case 0:
                                player.getRight().remove(player.getRight().get(0));
                                computer.getComputer().getRight().remove(computer.getComputer().getRight().get(0));
                                break;

                            case 1:
                                player.getRight().remove(player.getRight().get(0));
                                break;

                            case 2:
                                player.getRight().remove(player.getRight().get(0));
                                break;
                        }
                        break;

                    case 1:
                        switch (computer.getComputer().getRightColor().get(0)) {
                            case 0:
                                computer.getComputer().getRight().remove(computer.getComputer().getRight().get(0));
                                break;

                            case 1:
                                player.getRight().remove(player.getRight().get(0));
                                computer.getComputer().getRight().remove(computer.getComputer().getRight().get(0));
                                break;

                            case 2:
                                player.getRight().remove(player.getRight().get(0));
                                break;
                        }
                        break;

                    case 2:
                        switch (computer.getComputer().getRightColor().get(0)) {
                            case 0:
                                player.getRight().remove(player.getRight().get(0));
                                break;

                            case 1:
                                computer.getComputer().getRight().remove(computer.getComputer().getRight().get(0));
                                break;

                            case 2:
                                player.getRight().remove(player.getRight().get(0));
                                computer.getComputer().getRight().remove(computer.getComputer().getRight().get(0));
                                break;
                        }
                        break;
                }
            }
        }
    }
}

