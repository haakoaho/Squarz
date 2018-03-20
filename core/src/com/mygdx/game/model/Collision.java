package com.mygdx.game.model;


/**
 * Created by Max on 14/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class Collision {
    private Integer leftCollisioner, middleCollisioner, rightCollisioner, leftCntAi, middleCntAi, rightCntAi;

    public Collision() {
        this.leftCollisioner = 0;
        this.middleCollisioner = 0;
        this.rightCollisioner = 0;
        this.leftCntAi = 0;
        this.middleCntAi = 0;
        this.rightCntAi = 0;
    }

    public void collision(Player player, AIPlayer computer){

            //left row
            if (!player.getLeft().isEmpty() && !computer.getComputer().getLeft().isEmpty()) {

                if (player.getLeftCounter() > this.leftCollisioner
                        && computer.getComputer().getLeftCounter() > this.leftCntAi) {


                    while (player.getLeftCounter() > this.leftCollisioner && !player.getLeft().get(this.leftCollisioner).isInUser()) {
                        this.leftCollisioner = this.leftCollisioner + 1;
                    }

                    while (computer.getComputer().getLeftCounter() > this.leftCntAi && !computer.getComputer().getLeft().get(this.leftCntAi).isInAi()) {
                        this.leftCntAi = this.leftCntAi + 1;
                    }

                    if (player.getLeftCounter() > this.leftCollisioner
                            && computer.getComputer().getLeftCounter() > this.leftCntAi) {

                        if (player.getLeft().get(this.leftCollisioner).getRectangle().overlaps(
                                computer.getComputer().getLeft().get(this.leftCntAi).getRectangle())) {

                            if (player.getLeft().get(leftCollisioner).getColorKey() == 0) {
                                if (computer.getComputer().getLeft().get(leftCntAi).getColorKey() == 0) {

                                    player.decrement(player.getLeft(), this.leftCollisioner, 0);
                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);

                                    this.leftCollisioner = this.leftCollisioner + 1;
                                    this.leftCntAi = this.leftCntAi + 1;


                                } else if (computer.getComputer().getLeft().get(leftCntAi).getColorKey() == 1) {

                                    player.decrement(player.getLeft(), this.leftCollisioner, 0);

                                    this.leftCollisioner = this.leftCollisioner + 1;

                                } else if (computer.getComputer().getLeft().get(leftCntAi).getColorKey() == 2) {

                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);

                                    this.leftCntAi = this.leftCntAi + 1;
                                }
                            } else if (player.getLeft().get(this.leftCollisioner).getColorKey() == 1) {

                                if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 0) {
                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);
                                    this.leftCntAi += 1;
                                } else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 1) {
                                    player.decrement(player.getLeft(), this.leftCollisioner, 0);
                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);
                                    this.leftCollisioner += 1;
                                    this.leftCntAi += 1;
                                } else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 2) {
                                    player.decrement(player.getLeft(), this.leftCollisioner, 0);
                                    this.leftCollisioner += 1;
                                }
                            } else if (player.getLeft().get(this.leftCollisioner).getColorKey() == 2) {
                                if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 0) {
                                    player.decrement(player.getLeft(), this.leftCollisioner, 0);
                                    this.leftCollisioner += 1;
                                } else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 1) {
                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);
                                    this.leftCntAi += 1;
                                } else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 2) {
                                    player.decrement(player.getLeft(), this.leftCollisioner, 0);
                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);
                                    this.leftCollisioner += 1;
                                    this.leftCntAi += 1;
                                }
                            }
                        }
                    }
                }
            }






            //middle row
            if (!player.getMiddle().isEmpty() && !computer.getComputer().getMiddle().isEmpty()) {

                if (player.getMiddleCounter() > this.middleCollisioner
                        && computer.getComputer().getMiddleCounter() > this.middleCntAi) {


                    while (player.getMiddleCounter() > this.middleCollisioner && !player.getMiddle().get(this.middleCollisioner).isInUser()) {
                        this.middleCollisioner = this.middleCollisioner + 1;
                    }

                    while (computer.getComputer().getMiddleCounter() > this.middleCntAi && !computer.getComputer().getMiddle().get(this.middleCntAi).isInAi()) {
                        this.middleCntAi = this.middleCntAi + 1;
                    }

                    if (player.getMiddleCounter() > this.middleCollisioner
                            && computer.getComputer().getMiddleCounter() > this.middleCntAi) {

                        if (player.getMiddle().get(this.middleCollisioner).getRectangle().overlaps(
                                computer.getComputer().getMiddle().get(this.middleCntAi).getRectangle())) {

                            if (player.getMiddle().get(middleCollisioner).getColorKey() == 0) {
                                if (computer.getComputer().getMiddle().get(middleCntAi).getColorKey() == 0) {

                                    player.decrement(player.getMiddle(), this.middleCollisioner, 1);
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);

                                    this.middleCollisioner = this.middleCollisioner + 1;
                                    this.middleCntAi = this.middleCntAi + 1;


                                } else if (computer.getComputer().getMiddle().get(middleCntAi).getColorKey() == 1) {

                                    player.decrement(player.getMiddle(), this.middleCollisioner, 1);

                                    this.middleCollisioner = this.middleCollisioner + 1;

                                } else if (computer.getComputer().getMiddle().get(middleCntAi).getColorKey() == 2) {
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);


                                    this.middleCntAi = this.middleCntAi + 1;
                                }
                            } else if (player.getMiddle().get(this.middleCollisioner).getColorKey() == 1) {

                                if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 0) {
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);

                                    this.middleCntAi += 1;
                                } else if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 1) {
                                    player.decrement(player.getMiddle(), this.middleCollisioner, 1);
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);
                                    this.middleCollisioner += 1;
                                    this.middleCntAi += 1;
                                } else if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 2) {
                                    player.decrement(player.getMiddle(), this.middleCollisioner, 1);
                                    this.middleCollisioner += 1;
                                }
                            } else if (player.getMiddle().get(this.middleCollisioner).getColorKey() == 2) {
                                if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 0) {
                                    player.decrement(player.getMiddle(), this.middleCollisioner, 1);
                                    this.middleCollisioner += 1;
                                } else if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 1) {
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);
                                    this.middleCntAi += 1;
                                } else if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 2) {
                                    player.decrement(player.getMiddle(), this.middleCollisioner, 1);
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);
                                    this.middleCollisioner += 1;
                                    this.middleCntAi += 1;
                                }
                            }
                        }
                    }
                }
            }

            //right row

            if (!player.getRight().isEmpty() && !computer.getComputer().getRight().isEmpty()) {

                if (player.getRightCounter() > this.rightCollisioner
                        && computer.getComputer().getRightCounter() > this.rightCntAi) {


                    while (player.getRightCounter() > this.rightCollisioner && !player.getRight().get(this.rightCollisioner).isInUser()) {
                        this.rightCollisioner = this.rightCollisioner + 1;
                    }

                    while (computer.getComputer().getRightCounter() > this.rightCntAi && !computer.getComputer().getRight().get(this.rightCntAi).isInAi()) {
                        this.rightCntAi = this.rightCntAi + 1;
                    }

                    if (player.getRightCounter() > this.rightCollisioner
                            && computer.getComputer().getRightCounter() > this.rightCntAi) {

                        if (player.getRight().get(this.rightCollisioner).getRectangle().overlaps(
                                computer.getComputer().getRight().get(this.rightCntAi).getRectangle())) {

                            if (player.getRight().get(rightCollisioner).getColorKey() == 0) {
                                if (computer.getComputer().getRight().get(rightCntAi).getColorKey() == 0) {

                                    player.decrement(player.getRight(), this.rightCollisioner, 2);
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);

                                    this.rightCollisioner = this.rightCollisioner + 1;
                                    this.rightCntAi = this.rightCntAi + 1;


                                } else if (computer.getComputer().getRight().get(rightCntAi).getColorKey() == 1) {

                                    player.decrement(player.getRight(), this.rightCollisioner, 2);

                                    this.rightCollisioner = this.rightCollisioner + 1;

                                } else if (computer.getComputer().getRight().get(rightCntAi).getColorKey() == 2) {
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);


                                    this.rightCntAi = this.rightCntAi + 1;
                                }
                            } else if (player.getRight().get(this.rightCollisioner).getColorKey() == 1) {

                                if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 0) {
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);

                                    this.rightCntAi += 1;
                                } else if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 1) {
                                    player.decrement(player.getRight(), this.rightCollisioner, 2);
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);
                                    this.rightCollisioner += 1;
                                    this.rightCntAi += 1;
                                } else if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 2) {
                                    player.decrement(player.getRight(), this.rightCollisioner, 2);
                                    this.rightCollisioner += 1;
                                }
                            } else if (player.getRight().get(this.rightCollisioner).getColorKey() == 2) {
                                if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 0) {
                                    player.decrement(player.getRight(), this.rightCollisioner, 0);
                                    this.rightCollisioner += 1;
                                } else if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 1) {
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);
                                    this.rightCntAi += 1;
                                } else if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 2) {
                                    player.decrement(player.getRight(), this.rightCollisioner, 2);
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);
                                    this.rightCollisioner += 1;
                                    this.rightCntAi += 1;
                                }
                            }
                        }
                    }
                }
            }
    }
}


