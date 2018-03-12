package com.mygdx.game.model;

/**
 * Created by Antoine Dc on 11/03/2018.
 */

public class Score {
<<<<<<< HEAD
    private Integer  userScore;
    private Integer aiScore;

    public Score(Integer userScore, Integer aiScore) {
        this.userScore = userScore;
        this.aiScore = aiScore;
=======
    private Integer userScore;
    private Integer aiScore;

    public Score(){
        this.userScore = 0;
        this.aiScore = 0;
>>>>>>> master
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
}
