package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mathi on 12/03/2018.
 */

public class Bonus {
    private Map<Integer, Integer> bonusKey;
    private Map<Integer, Texture> bonusTex;
    private Integer selectedBonusKey;

    private Texture speedUp, clearAll, bounce, killer;

    public Bonus(){
        this.bonusKey = new HashMap<Integer, Integer>();
        this.bonusTex = new HashMap<Integer, Texture>();
        this.bonusKey = new HashMap<Integer, Integer>();

        //setting Textures;
    }

    //getters and setters
    public Map<Integer, Integer> getBonusKey() {
        return bonusKey;
    }
    public void setBonusKey(Map<Integer, Integer> bonusKey) {
        this.bonusKey = bonusKey;
    }
    public Map<Integer, Texture> getBonusTex() {
        return bonusTex;
    }
    public void setBonusTex(Map<Integer, Texture> bonusTex) {
        this.bonusTex = bonusTex;
    }
    public Integer getSelectedBonusKey() {
        return selectedBonusKey;
    }
    public void setSelectedBonusKey(Integer selectedBonusKey) {
        this.selectedBonusKey = selectedBonusKey;
    }



    public void getBonusEffect(Integer bonusKey){
        if(bonusKey == 0){

        }
        if(bonusKey == 1){

        }
        if(bonusKey == 2){

        }
        if(bonusKey == 3){

        }
    }
}
