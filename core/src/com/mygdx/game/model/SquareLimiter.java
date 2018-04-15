package com.mygdx.game.model;

/**
 * Created by Max on 17/03/2018.
 */

public class SquareLimiter {
    private Integer redLeft;
    private Integer blueLeft;
    private Integer yellowLeft;
    private Integer bonusLeft;


    public SquareLimiter(Integer nbSquare) {
        this.redLeft = nbSquare;
        this.blueLeft = nbSquare;
        this.yellowLeft = nbSquare;
        this.bonusLeft = 3;
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
        if (colorKey >= 3){
            this.setBonusLeft(this.getBonusLeft() - 1);
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
        if(colorkey>=3){
            if(this.getBonusLeft() == 0){
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

    public Integer getBonusLeft() {
        return bonusLeft;
    }

    public void setBonusLeft(Integer bonusLeft) {
        this.bonusLeft = bonusLeft;
    }
}
