package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.Squarz.format;

/**
 * Created by Max on 15/04/2018.
 */

public class Bonus {
    private Integer bonusKey, colorKey;

    private Texture none, punisher, nurse, selectedBonus, mrPropre; // bounce

    private Player player;
    private AIPlayer opponent;

    public Bonus(){
        this.bonusKey = 0;
        this.colorKey = 3;

        //setting Textures;
        this.selectedBonus =  new Texture(Gdx.files.internal(format+"/bonuses/none.png"));
        this.none = new Texture(Gdx.files.internal(format+"/bonuses/none.png"));
        this.punisher = new Texture(Gdx.files.internal(format+"/bonuses/punisher.png"));
        this.nurse = new Texture(Gdx.files.internal(format+"/bonuses/nurse.png"));
        this.mrPropre = new Texture((Gdx.files.internal(format+"/bonuses/mrPropre.png")));
    }

    public void update(Player player, AIPlayer opponent){
        this.player=player;
        this.opponent=opponent;
    }

    public Texture getBonustexture(Integer bonusKey){
        if(bonusKey == 0){
            //none
            this.setSelectedBonus(this.getNone());
            this.colorKey = 3;
        }
        if(bonusKey == 1){
            //punisher
            this.setSelectedBonus(this.getPunisher());
            this.colorKey = 4;
        }
        if(bonusKey == 2){
            //nurse
            this.setSelectedBonus(this.getNurse());
            this.colorKey = 5;
        }
        if(bonusKey == 3){
            //mrPropre
            this.setSelectedBonus(this.getMrPropre());
            this.colorKey = 6;
        }
        return selectedBonus;
    }

    public void punisherEffect(){
        this.setColorKey(4);
    }

    public void nurseEffectPlayer(){
        this.getPlayer().getSquareLimiter().setRedLeft(this.getPlayer().getSquareLimiter().getRedLeft()+3);
        this.getPlayer().getSquareLimiter().setBlueLeft(this.getPlayer().getSquareLimiter().getBlueLeft()+3);
        this.getPlayer().getSquareLimiter().setYellowLeft(this.getPlayer().getSquareLimiter().getYellowLeft()+3);
        this.getPlayer().getSquareLimiter().setBonusPunisherLeft(this.getPlayer().getSquareLimiter().getBonusPunisherLeft());
        //this.getPlayer().setSquareLimiter(new SquareLimiter(this.getPlayer().getSquareLimiter().getRedLeft() + 3, this.getPlayer().getSquareLimiter().getBlueLeft() + 3, this.getPlayer().getSquareLimiter().getYellowLeft() + 3, bonusLeft));
    }

    public void mrPropreEffect(){
        for (int columnKey = 0; columnKey<3; columnKey ++ ) {
            //this.getPlayer().setFirstSquareKey(columnKey, this.getPlayer().getCounter(columnKey));
            //this.getOpponent().setFirstSquareKey(columnKey, this.getOpponent().getCounter(columnKey));
            this.getPlayer().getMap(columnKey).clear();
            this.getPlayer().setCounter(columnKey, 0);
            this.getPlayer().setFirstSquareKey(columnKey, 0);
            this.getOpponent().getMap(columnKey).clear();
            this.getOpponent().setCounter(columnKey, 0);
            this.getOpponent().setFirstSquareKey(columnKey, 0);
        }
    }


    //getters and setters

    public Integer getBonusKey() {
        return bonusKey;
    }
    public void setBonusKey(Integer bonusKey) {
        this.bonusKey = bonusKey;
    }
    public Texture getNone() {
        return none;
    }
    public void setNone(Texture none) {
        this.none = none;
    }
    public Texture getPunisher() {
        return punisher;
    }
    public void setPunisher(Texture punisher) {
        this.punisher = punisher;
    }
    public Texture getNurse() {
        return nurse;
    }
    public void setNurse(Texture nurse) {
        this.nurse = nurse;
    }
    public Texture getSelectedBonus() {
        return selectedBonus;
    }
    public void setSelectedBonus(Texture selectedBonus) {
        this.selectedBonus = selectedBonus;
    }
    public Integer getColorKey() {
        return colorKey;
    }
    public void setColorKey(Integer colorKey) {
        this.colorKey = colorKey;
    }
    public Texture getMrPropre() {
        return mrPropre;
    }
    public void setMrPropre(Texture mrPropre) {
        this.mrPropre = mrPropre;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public AIPlayer getOpponent() {
        return opponent;
    }
    public void setOpponent(AIPlayer opponent) {
        this.opponent = opponent;
    }
}
