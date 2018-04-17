package com.mygdx.game.model;

/**
 * Created by Max on 17/03/2018.
 */

public class SquareLimiter {
    private Integer redLeft;
    private Integer blueLeft;
    private Integer yellowLeft;
    private Integer bonusPunisherLeft;


    public SquareLimiter(Integer nbSquare, Integer bonusLeft) {
        this.redLeft = nbSquare;
        this.blueLeft = nbSquare;
        this.yellowLeft = nbSquare;
        this.bonusPunisherLeft = bonusLeft;
    }

    public SquareLimiter(Integer redLeft, Integer blueLeft, Integer yellowLeft, Integer bonusLeft){
        this.redLeft = redLeft;
        this.blueLeft = blueLeft;
        this.yellowLeft = yellowLeft;
        this.bonusPunisherLeft = bonusLeft;
    }

    public void minusOne(Integer colorKey){
        if (colorKey == 0){
            this.setRedLeft(this.getRedLeft() - 1);
        }
        if (colorKey == 1){
            this.setBlueLeft(this.getBlueLeft() - 1);
        }
        if (colorKey == 2){
            this.setYellowLeft(this.getYellowLeft() - 1);
        }
        if (colorKey == 4){
            this.setBonusPunisherLeft(this.bonusPunisherLeft -1);
        }
    }

    public boolean isOver(int colorkey) {
        if(colorkey == 0){
            if(this.getRedLeft() == 0){
                return true;
            }
        }
        if(colorkey == 1){
            if(this.getBlueLeft() == 0){
                return true;
            }
        }
        if(colorkey == 2){
            if(this.getYellowLeft() == 0){
                return true;
            }
        }
        if (colorkey == 4){
            if(this.getBonusPunisherLeft() == 0){
                return true;
            }
        }
        return false;
    }


    //Getters & Setters

    public Integer getRedLeft() {
        return redLeft;
    }

    public void setRedLeft(Integer redLeft) {
        this.redLeft = redLeft;
    }

    public Integer getBlueLeft() {
        return blueLeft;
    }

    public void setBlueLeft(Integer blueLeft) {
        this.blueLeft = blueLeft;
    }

    public Integer getYellowLeft() {
        return yellowLeft;
    }

    public void setYellowLeft(Integer yellowLeft) {
        this.yellowLeft = yellowLeft;
    }

    public Integer getBonusPunisherLeft() {
        return bonusPunisherLeft;
    }

    public void setBonusPunisherLeft(Integer bonusLeft1) {
        this.bonusPunisherLeft = bonusLeft1;
    }

}
