package com.mygdx.game.model;


/**
 * Created by Max on 14/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class Collision {
    private Integer leftCnt, middleCnt, rightCnt, leftCntAi, middleCntAi, rightCntAi;

    public Collision() {
        this.leftCnt = 0;
        this.middleCnt = 0;
        this.rightCnt = 0;
        this.leftCntAi = 0;
        this.middleCntAi = 0;
        this.rightCntAi = 0;
    }

    public void collision(Player player, AIPlayer computer){

            //left row
            if (!player.getLeft().isEmpty() && !computer.getComputer().getLeft().isEmpty()) {

                if (player.getLeftCounter() > this.leftCnt
                        && computer.getComputer().getLeftCounter() > this.leftCntAi) {


                    while (player.getLeftCounter() > this.leftCnt && !player.getLeft().get(this.leftCnt).isInUser()) {
                        this.leftCnt = this.leftCnt + 1;
                    }

                    while (computer.getComputer().getLeftCounter() > this.leftCntAi && !computer.getComputer().getLeft().get(this.leftCntAi).isInAi()) {
                        this.leftCntAi = this.leftCntAi + 1;
                    }

                    if (player.getLeftCounter() > this.leftCnt
                            && computer.getComputer().getLeftCounter() > this.leftCntAi) {

                        if (player.getLeft().get(this.leftCnt).getRectangle().overlaps(
                                computer.getComputer().getLeft().get(this.leftCntAi).getRectangle())) {

                            if (player.getLeft().get(leftCnt).getColorKey() == 0) {
                                if (computer.getComputer().getLeft().get(leftCntAi).getColorKey() == 0) {

                                    player.decrement(player.getLeft(), this.leftCnt, 0);
                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);

                                    this.leftCnt = this.leftCnt + 1;
                                    this.leftCntAi = this.leftCntAi + 1;


                                } else if (computer.getComputer().getLeft().get(leftCntAi).getColorKey() == 1) {

                                    player.decrement(player.getLeft(), this.leftCnt, 0);

                                    this.leftCnt = this.leftCnt + 1;

                                } else if (computer.getComputer().getLeft().get(leftCntAi).getColorKey() == 2) {

                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);

                                    this.leftCntAi = this.leftCntAi + 1;
                                }
                            } else if (player.getLeft().get(this.leftCnt).getColorKey() == 1) {

                                if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 0) {
                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);
                                    this.leftCntAi += 1;
                                } else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 1) {
                                    player.decrement(player.getLeft(), this.leftCnt, 0);
                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);
                                    this.leftCnt += 1;
                                    this.leftCntAi += 1;
                                } else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 2) {
                                    player.decrement(player.getLeft(), this.leftCnt, 0);
                                    this.leftCnt += 1;
                                }
                            } else if (player.getLeft().get(this.leftCnt).getColorKey() == 2) {
                                if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 0) {
                                    player.decrement(player.getLeft(), this.leftCnt, 0);
                                    this.leftCnt += 1;
                                } else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 1) {
                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);
                                    this.leftCntAi += 1;
                                } else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 2) {
                                    player.decrement(player.getLeft(), this.leftCnt, 0);
                                    computer.getComputer().decrement(computer.getComputer().getLeft(),
                                            this.leftCntAi, 0);
                                    this.leftCnt += 1;
                                    this.leftCntAi += 1;
                                }
                            }
                        }
                    }
                }
            }

            //middle row
            if (!player.getMiddle().isEmpty() && !computer.getComputer().getMiddle().isEmpty()) {

                if (player.getMiddleCounter() > this.middleCnt
                        && computer.getComputer().getMiddleCounter() > this.middleCntAi) {


                    while (player.getMiddleCounter() > this.middleCnt && !player.getMiddle().get(this.middleCnt).isInUser()) {
                        this.middleCnt = this.middleCnt + 1;
                    }

                    while (computer.getComputer().getMiddleCounter() > this.middleCntAi && !computer.getComputer().getMiddle().get(this.middleCntAi).isInAi()) {
                        this.middleCntAi = this.middleCntAi + 1;
                    }

                    if (player.getMiddleCounter() > this.middleCnt
                            && computer.getComputer().getMiddleCounter() > this.middleCntAi) {

                        if (player.getMiddle().get(this.middleCnt).getRectangle().overlaps(
                                computer.getComputer().getMiddle().get(this.middleCntAi).getRectangle())) {

                            if (player.getMiddle().get(middleCnt).getColorKey() == 0) {
                                if (computer.getComputer().getMiddle().get(middleCntAi).getColorKey() == 0) {

                                    player.decrement(player.getMiddle(), this.middleCnt, 1);
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);

                                    this.middleCnt = this.middleCnt + 1;
                                    this.middleCntAi = this.middleCntAi + 1;


                                } else if (computer.getComputer().getMiddle().get(middleCntAi).getColorKey() == 1) {

                                    player.decrement(player.getMiddle(), this.middleCnt, 1);

                                    this.middleCnt = this.middleCnt + 1;

                                } else if (computer.getComputer().getMiddle().get(middleCntAi).getColorKey() == 2) {
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);


                                    this.middleCntAi = this.middleCntAi + 1;
                                }
                            } else if (player.getMiddle().get(this.middleCnt).getColorKey() == 1) {

                                if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 0) {
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);

                                    this.middleCntAi += 1;
                                } else if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 1) {
                                    player.decrement(player.getMiddle(), this.middleCnt, 1);
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);
                                    this.middleCnt += 1;
                                    this.middleCntAi += 1;
                                } else if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 2) {
                                    player.decrement(player.getMiddle(), this.middleCnt, 1);
                                    this.middleCnt += 1;
                                }
                            } else if (player.getMiddle().get(this.middleCnt).getColorKey() == 2) {
                                if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 0) {
                                    player.decrement(player.getMiddle(), this.middleCnt, 1);
                                    this.middleCnt += 1;
                                } else if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 1) {
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);
                                    this.middleCntAi += 1;
                                } else if (computer.getComputer().getMiddle().get(this.middleCntAi).getColorKey() == 2) {
                                    player.decrement(player.getMiddle(), this.middleCnt, 1);
                                    computer.getComputer().decrement(computer.getComputer().getMiddle(),
                                            this.middleCntAi, 1);
                                    this.middleCnt += 1;
                                    this.middleCntAi += 1;
                                }
                            }
                        }
                    }
                }
            }

            //right row

            if (!player.getRight().isEmpty() && !computer.getComputer().getRight().isEmpty()) {

                if (player.getRightCounter() > this.rightCnt
                        && computer.getComputer().getRightCounter() > this.rightCntAi) {


                    while (player.getRightCounter() > this.rightCnt && !player.getRight().get(this.rightCnt).isInUser()) {
                        this.rightCnt = this.rightCnt + 1;
                    }

                    while (computer.getComputer().getRightCounter() > this.rightCntAi && !computer.getComputer().getRight().get(this.rightCntAi).isInAi()) {
                        this.rightCntAi = this.rightCntAi + 1;
                    }

                    if (player.getRightCounter() > this.rightCnt
                            && computer.getComputer().getRightCounter() > this.rightCntAi) {

                        if (player.getRight().get(this.rightCnt).getRectangle().overlaps(
                                computer.getComputer().getRight().get(this.rightCntAi).getRectangle())) {

                            if (player.getRight().get(rightCnt).getColorKey() == 0) {
                                if (computer.getComputer().getRight().get(rightCntAi).getColorKey() == 0) {

                                    player.decrement(player.getRight(), this.rightCnt, 2);
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);

                                    this.rightCnt = this.rightCnt + 1;
                                    this.rightCntAi = this.rightCntAi + 1;


                                } else if (computer.getComputer().getRight().get(rightCntAi).getColorKey() == 1) {

                                    player.decrement(player.getRight(), this.rightCnt, 2);

                                    this.rightCnt = this.rightCnt + 1;

                                } else if (computer.getComputer().getRight().get(rightCntAi).getColorKey() == 2) {
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);


                                    this.rightCntAi = this.rightCntAi + 1;
                                }
                            } else if (player.getRight().get(this.rightCnt).getColorKey() == 1) {

                                if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 0) {
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);

                                    this.rightCntAi += 1;
                                } else if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 1) {
                                    player.decrement(player.getRight(), this.rightCnt, 2);
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);
                                    this.rightCnt += 1;
                                    this.rightCntAi += 1;
                                } else if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 2) {
                                    player.decrement(player.getRight(), this.rightCnt, 2);
                                    this.rightCnt += 1;
                                }
                            } else if (player.getRight().get(this.rightCnt).getColorKey() == 2) {
                                if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 0) {
                                    player.decrement(player.getRight(), this.rightCnt, 0);
                                    this.rightCnt += 1;
                                } else if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 1) {
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);
                                    this.rightCntAi += 1;
                                } else if (computer.getComputer().getRight().get(this.rightCntAi).getColorKey() == 2) {
                                    player.decrement(player.getRight(), this.rightCnt, 2);
                                    computer.getComputer().decrement(computer.getComputer().getRight(),
                                            this.rightCntAi, 2);
                                    this.rightCnt += 1;
                                    this.rightCntAi += 1;
                                }
                            }
                        }
                    }
                }
            }
    }
}


