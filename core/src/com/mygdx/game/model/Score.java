package com.mygdx.game.model;

/**
 * Created by Max on 11/03/2018.
 */

public class Score {
    private Integer userScore;
    private Integer aiScore;

    public Score(){
        this.userScore = 0;
        this.aiScore = 0;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Integer getAiScore() {
        return aiScore;
    }

    public void setAiScore(Integer aiScore) {
        this.aiScore = aiScore;
    }

    public void updateUser(){
        this.setUserScore(this.getUserScore()+1);
    }

    public void updateAi() {
        this.setAiScore(this.getAiScore() + 1);
    }
}
