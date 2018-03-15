package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;


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

    public void collision( Player player, AIPlayer computer, Boolean firstTouch) {
        if (firstTouch) {
            if (!player.getLeft().isEmpty() && !computer.getComputer().getLeft().isEmpty()
                    && player.getLeftCounter() >= this.leftCnt
                    && computer.getComputer().getLeftCounter() >= this.leftCntAi
                    && player.getLeft().get(this.leftCnt).getRectangle().overlaps(
                            computer.getComputer().getLeft().get(this.leftCntAi).getRectangle())) {

                if (player.getLeft().get(leftCnt).getColorKey() == 0) {
                    //player.decrement(player.getLeft(), this.leftCnt, 0);
                    //computer.getComputer().decrement(computer.getComputer().getLeft(),
                    //        this.leftCntAi, 0);

                    if (computer.getComputer().getLeft().get(leftCntAi).getColorKey() == 0) {

                        player.decrement(player.getLeft(), this.leftCnt, 0);
                        computer.getComputer().decrement(computer.getComputer().getLeft(),
                               this.leftCntAi, 0);

                        //player.getLeft().remove(player.getLeft().get(0));
                        //computer.getComputer().getLeft().remove(computer.getComputer().getLeft().get(0));
                        this.leftCnt = this.leftCnt + 1;
                        this.leftCntAi = this.leftCntAi + 1;

                    } else if (computer.getComputer().getLeft().get(leftCntAi).getColorKey() == 1) {
                        //player.getLeft().remove(player.getLeft().get(0));
                        //player.decrement(player.getLeft(), player.getLeftCounter(), 0);

                        player.decrement(player.getLeft(), this.leftCnt, 0);
                        computer.getComputer().decrement(computer.getComputer().getLeft(),
                                this.leftCntAi, 0);

                        this.leftCnt = this.leftCnt + 1;
                        this.leftCntAi = this.leftCntAi + 1;


                        //this.leftCnt += 1;

                    }else if (computer.getComputer().getLeft().get(leftCntAi).getColorKey() == 2) {
                        //computer.getComputer().getLeft().remove(player.getLeft().get(0));
                        //computer.getComputer().decrement(computer.getComputer().getLeft(),
                        //        computer.getComputer().getLeftCounter(), 0);

                        player.decrement(player.getLeft(), this.leftCnt, 0);
                        computer.getComputer().decrement(computer.getComputer().getLeft(),
                                this.leftCntAi, 0);

                        this.leftCnt = this.leftCnt + 1;
                        this.leftCntAi = this.leftCntAi + 1;


                        //this.leftCntAi += 1;
                    }
                } else

                if (player.getLeft().get(this.leftCnt).getColorKey() == 1) {

                    if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 0) {
                        computer.getComputer().getLeft().remove(computer.getComputer().getLeft().get(this.leftCntAi));
                        this.leftCntAi += 1;
                    }else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 1) {
                        player.getLeft().remove(player.getLeft().get(this.leftCnt));
                        computer.getComputer().getLeft().remove(computer.getComputer().getLeft().get(this.leftCntAi));
                        this.leftCnt += 1;
                        this.leftCntAi += 1;
                    }else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 2) {
                        player.getLeft().remove(player.getLeft().get(this.leftCnt));
                        this.leftCnt += 1;
                    }
                } else

                if (player.getLeft().get(this.leftCnt).getColorKey() == 2) {
                    if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 0) {
                        player.getLeft().remove(player.getLeft().get(this.leftCnt));
                        this.leftCnt += 1;
                    }else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 1) {
                        computer.getComputer().getLeft().remove(computer.getComputer().getLeft().get(this.leftCntAi));
                        this.leftCntAi += 1;
                    }else if (computer.getComputer().getLeft().get(this.leftCntAi).getColorKey() == 2) {
                        player.getLeft().remove(player.getLeft().get(this.leftCnt));
                        computer.getComputer().getLeft().remove(computer.getComputer().getLeft().get(this.leftCntAi));
                        this.leftCnt += 1;
                        this.leftCntAi += 1;
                    }
                }
            }


            if (!player.getMiddle().isEmpty() && !computer.getComputer().getMiddle().isEmpty() &&
                    player.getMiddle().get(0).getRectangle().overlaps(computer.getComputer().getMiddle().get(0).getRectangle())) {

                if (player.getMiddle().get(0).getColorKey() == 0) {
                    if (computer.getComputer().getMiddle().get(0).getColorKey() == 0) {
                        player.getMiddle().remove(player.getMiddle().get(0));
                        computer.getComputer().getMiddle().remove(computer.getComputer().getMiddle().get(0));
                    }
                    if (computer.getComputer().getMiddle().get(0).getColorKey() == 1) {
                        player.getMiddle().remove(player.getMiddle().get(0));
                    }
                    if (computer.getComputer().getMiddle().get(0).getColorKey() == 2) {
                        player.getMiddle().remove(player.getMiddle().get(0));
                    }
                }

                if (player.getMiddle().get(0).getColorKey() == 1) {
                    if (computer.getComputer().getMiddle().get(0).getColorKey() == 0) {
                        computer.getComputer().getMiddle().remove(computer.getComputer().getMiddle().get(0));
                    }

                    if (computer.getComputer().getMiddle().get(0).getColorKey() == 1) {
                        player.getMiddle().remove(player.getMiddle().get(0));
                        computer.getComputer().getMiddle().remove(computer.getComputer().getMiddle().get(0));
                    }

                    if (computer.getComputer().getMiddle().get(0).getColorKey() == 2) {
                        player.getMiddle().remove(player.getMiddle().get(0));
                    }
                }

                if (player.getMiddle().get(0).getColorKey() == 2) {
                    if (computer.getComputer().getMiddle().get(0).getColorKey() == 0) {
                        player.getMiddle().remove(player.getMiddle().get(0));
                    }

                    if (computer.getComputer().getMiddle().get(0).getColorKey() == 0) {
                        computer.getComputer().getMiddle().remove(computer.getComputer().getMiddle().get(0));
                    }

                    if (computer.getComputer().getMiddle().get(0).getColorKey() == 0) {
                        player.getMiddle().remove(player.getMiddle().get(0));
                        computer.getComputer().getMiddle().remove(computer.getComputer().getMiddle().get(0));
                    }
                }
            }

            if (!player.getRight().isEmpty() && !computer.getComputer().getRight().isEmpty() &&
                    player.getRight().get(0).getRectangle().overlaps(computer.getComputer().getRight().get(0).getRectangle())) {

                if (player.getRight().get(0).getColorKey() == 0) {
                    if (computer.getComputer().getRight().get(0).getColorKey() == 0) {
                        player.getRight().remove(player.getRight().get(0));
                        computer.getComputer().getRight().remove(computer.getComputer().getRight().get(0));
                    }
                    if (computer.getComputer().getRight().get(0).getColorKey() == 1) {
                        player.getRight().remove(player.getRight().get(0));
                    }
                    if (computer.getComputer().getRight().get(0).getColorKey() == 2) {
                        player.getRight().remove(player.getRight().get(0));
                    }
                }

                if (player.getRight().get(0).getColorKey() == 1) {
                    if (computer.getComputer().getRight().get(0).getColorKey() == 0) {
                        computer.getComputer().getRight().remove(computer.getComputer().getRight().get(0));
                    }

                    if (computer.getComputer().getRight().get(0).getColorKey() == 1) {
                        player.getRight().remove(player.getRight().get(0));
                        computer.getComputer().getRight().remove(computer.getComputer().getRight().get(0));
                    }

                    if (computer.getComputer().getRight().get(0).getColorKey() == 2) {
                        player.getRight().remove(player.getRight().get(0));
                    }
                }

                if (player.getRight().get(0).getColorKey() == 2) {
                    if (computer.getComputer().getRight().get(0).getColorKey() == 0) {
                        player.getRight().remove(player.getRight().get(0));
                    }

                    if (computer.getComputer().getRight().get(0).getColorKey() == 0) {
                        computer.getComputer().getRight().remove(computer.getComputer().getRight().get(0));
                    }

                    if (computer.getComputer().getRight().get(0).getColorKey() == 0) {
                        player.getRight().remove(player.getRight().get(0));
                        computer.getComputer().getRight().remove(computer.getComputer().getRight().get(0));
                    }
                }
            }
        }
    }


}

