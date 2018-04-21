package com.mygdx.game.model;

public class Score {
    private static final Score ourInstance = new Score();
    private Integer userScore;
    private Integer opponentScore;

    public static Score getInstance() {
        return ourInstance;
    }

    public Score(){
        this.userScore = 0;
        this.opponentScore = 0;
    }

    public void reset() {
        this.userScore=0;
        this.opponentScore=0;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Integer getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(Integer opponentScore) {
        this.opponentScore = opponentScore;
    }

    public void updateUser(){
        this.setUserScore(this.getUserScore()+1);
    }

    public void updateAi() {
        this.setOpponentScore(this.getOpponentScore() + 1);
    }
}
