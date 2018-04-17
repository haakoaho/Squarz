package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import static com.mygdx.game.Squarz.format;

/**
 * Created by Max on 15/04/2018.
 */

public class Bonus {
    private Integer bonusKey, colorKey;

    private Texture none, punisher, nurse, selectedBonus, mrPropre; // bounce

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

}
