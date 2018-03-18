package com.mygdx.game.models;

/**
 * Created by Antoine Dc on 17/03/2018.
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

    public void counter(Integer colorKey){
        switch(colorKey){
            case 0:
                this.setRedLefting(this.getRedLefting() - 1);
                break;
            case 1:
                this.setBlueLefting(this.getBlueLefting() - 1);
                break;
            case 2:
                this.setYellowLefting(this.getYellowLefting() - 1);
                break;
            default:
                break;
        }
    }

    public boolean isOver(int colorkey){
        switch (colorkey){
            case 0:
                if (this.getRedLefting() == 0){
                    return true;
                }
            case 1:
                if (this.getBlueLefting() == 0){
                    return true;
                }
            case 2:
                if (this.getYellowLefting() == 0){
                    return true;
                }
            default:
                    return false;
        }
    }



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
