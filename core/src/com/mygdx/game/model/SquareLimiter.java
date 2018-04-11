package com.mygdx.game.model;

/**
 * Created by Max on 17/03/2018.
 */

public class SquareLimiter {
    private Integer redLefting;
    private Integer blueLefting;
    private Integer yellowLefting;


    public SquareLimiter(Integer nbSquare) {
        this.redLefting = nbSquare;
        this.blueLefting = nbSquare;
        this.yellowLefting = nbSquare;
    }

    public void minusOne(Integer colorKey){
        if (colorKey == 0){
            this.setRedLefting(this.getRedLefting() - 1);
        }
        if (colorKey == 1){
            this.setBlueLefting(this.getBlueLefting() - 1);
        }
        if (colorKey == 2){
            this.setYellowLefting(this.getYellowLefting() - 1);
        }
    }

    public boolean isOver(int colorkey) {
        if(colorkey == 0){
            if(this.getRedLefting() == 0){
                return true;
            }
        }
        if(colorkey == 1){
            if(this.getBlueLefting() == 0){
                return true;
            }
        }
        if(colorkey == 2){
            if(this.getYellowLefting() == 0){
                return true;
            }
        }
        return false;
    }


    //Getters & Setters

    public Integer getRedLefting() {
        return redLefting;
    }

    public void setRedLefting(Integer redLefting) {
        this.redLefting = redLefting;
    }

    public Integer getBlueLefting() {
        return blueLefting;
    }

    public void setBlueLefting(Integer blueLefting) {
        this.blueLefting = blueLefting;
    }

    public Integer getYellowLefting() {
        return yellowLefting;
    }

    public void setYellowLefting(Integer yellowLefting) {
        this.yellowLefting = yellowLefting;
    }
}
