package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.view.BonusSelection;

import static com.mygdx.game.Squarz.format;

/**
 * Created by Max on 15/04/2018.
 */

public class Bonus {
    private Integer bonusKey, colorKey;

    private Texture none, killer, multi, selectedBonus, cleaner; // bounce

    public Bonus(){
        this.bonusKey = 0;
        this.colorKey = 3;

        //setting Textures;
        this.selectedBonus =  new Texture(Gdx.files.internal(format+"/bonuses/none.png"));
        this.none = new Texture(Gdx.files.internal(format+"/bonuses/none.png"));
        this.killer = new Texture(Gdx.files.internal(format+"/bonuses/killer.png"));
        this.multi = new Texture(Gdx.files.internal(format+"/bonuses/multi.png"));
        this.cleaner = new Texture((Gdx.files.internal(format+"/bonuses/cleaner.png")));
    }

    /*public Bonus(int bonusKey, int colorKey, Texture texture){
        this.bonusKey = bonusKey;
        this.colorKey = colorKey;

        //setting Textures;
        //this.selectedBonus =  new Texture(Gdx.files.internal(format+"/bonuses/none.png"));
        this.setSelectedBonus(texture);
    }*/

    public Texture getBonustexture(Integer bonusKey){
        if(bonusKey == 0){
            //none
            this.setSelectedBonus(this.getNone());
            this.colorKey = 3;
        }
        if(bonusKey == 1){
            //killer
            this.setSelectedBonus(this.getKiller());
            this.colorKey = 4;
        }
        if(bonusKey == 2){
            //multi
            this.setSelectedBonus(this.getMulti());
            this.colorKey = 5;
        }
        if(bonusKey == 3){
            //cleaner
            this.setSelectedBonus(this.getCleaner());
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
    public Texture getKiller() {
        return killer;
    }
    public void setKiller(Texture killer) {
        this.killer = killer;
    }
    public Texture getMulti() {
        return multi;
    }
    public void setMulti(Texture multi) {
        this.multi = multi;
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
    public Texture getCleaner() {
        return cleaner;
    }
    public void setCleaner(Texture cleaner) {
        this.cleaner = cleaner;
    }
}
