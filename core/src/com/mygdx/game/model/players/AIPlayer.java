package com.mygdx.game.model.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.other.Bonus;
import com.mygdx.game.model.other.Square;
import com.mygdx.game.model.ai_settings.PreferencesSettings;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;

import java.util.Map;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.format;

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class AIPlayer extends Player {
    private final Player player;
    private Texture texture;
    private Integer launcherCounter;

    private final Bonus bonus1;
    private final Bonus bonus2;
    private Integer nbofBonusesUsed;

/**
 * part of the improvement of the Ai
 *
    private boolean wave1 = true;
    private boolean wave2 = true;
    private boolean wave3 = true;
    private boolean wave4 = true;
    private boolean wave5 = true;
 */

    public AIPlayer (PreferencesSettings set, ICountdownDuration countDown, Player player){
        super(set, countDown);
        this.player = player;
        this.texture = new Texture (Gdx.files.internal(format+"/square/square.png"));

        this.launcherCounter = 0;


        this.bonus1 = set.getBonus1();
        this.bonus2 = set.getBonus2();

        this.nbofBonusesUsed = 0;
    }

    public void send(ICountdownDuration countDown){
        this.launcherCounter += 1;
        if (countDown.getWorldTimer() > 0) {
            if (this.launcherCounter == this.getSet().getDtLaunching() ) {
                this.launcherCounter = 0;

                if (random(15) == 1) {
                    boolean playerResistance = false;
                    for (int columnKey = 0; columnKey<3; columnKey++){
                        if(this.player.getMap(columnKey) != null){
                            playerResistance = true;
                        }
                    }
                    if (playerResistance){
                        bonusSending();
                    }
                }else {
                    //setting the random color
                    int colorKey = random(2);
                    setTheRandomTexture(colorKey);

                    //setting the random Texture in a random column
                    int column = random(2);
                    setTheRandomColumn(column, colorKey);
                }
            }
        }
    }

    private void bonusSending(){

        if(nbofBonusesUsed==0) {
            this.nbofBonusesUsed = 1;
            int colorkey = this.bonus1.getColorKey();
            setTheRandomTexture(colorkey);
            int column = random(2);
            setTheRandomColumn(column, colorkey);
            this.bonus1.chosenAiEffect(this.bonus1.getBonusKey());
        } else {
            this.nbofBonusesUsed = 2;
            int colorkey = this.bonus2.getColorKey();
            setTheRandomTexture(colorkey);
            int column = random(2);
            setTheRandomColumn(column, colorkey);
            System.out.println(this.bonus2.getBonusKey());
            this.bonus2.chosenAiEffect(this.bonus2.getBonusKey());
        }
    }




    private void setTheRandomTexture(int colorKey){
        if (colorKey == 0) {
            this.texture = new Texture(Gdx.files.internal(format+"/square/square_red.png"));
        } else if (colorKey == 1) {
            this.texture = new Texture(Gdx.files.internal(format+"/square/square_blue.png"));
        } else if(colorKey == 2){
            this.texture = new Texture(Gdx.files.internal(format+"/square/square_yellow.png"));
        } else if(colorKey == 4){
            this.texture = new Texture(Gdx.files.internal(format+"/bonuses/punisher.png"));
        }
    }

    private void setTheRandomColumn(int columnKey, int colorKey) {
        if(!this.getSquareLimiter().isOver(colorKey)) {

            if (columnKey == 0) {
                incrementOpponent(texture, columnKey, colorKey);
            }
            if (columnKey == 1) {
                incrementOpponent(texture, columnKey, colorKey);

            }
            if (columnKey == 2) {
                incrementOpponent(texture, columnKey, colorKey);
            }
        }
    }

    public void incrementOpponent(Texture t, Integer columnKey, Integer colorKey) {
        Integer counter = this.getCounter(columnKey);
        Map<Integer, Square> row = this.getMap(columnKey);
        //back end
        row.put(counter, new Square(this.getSet()));
        incrementCounter(columnKey);
        this.getSquareLimiter().minusOne(colorKey);

        //front end
        row.get(counter).setPosition(new Vector2(WIDTH * (3+(2*columnKey))/8, HEIGHT));
        row.get(counter).setTexture(t);
        row.get(counter).setColorKey(colorKey);

        handleAIOverLapping(columnKey, t, counter, row);
    }

    private void handleAIOverLapping(Integer columnKey, Texture t, Integer counter, Map<Integer, Square> map){
        if (counter != this.getFirstSquareKey(columnKey) && counter > 0 && map.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
            map.get(counter).setPosition(new Vector2(WIDTH * 3 / 8,
                    map.get(counter - 1).getPosition().y + t.getHeight() + 5));
        }
    }

    // ---------  general getters and setters

    /**
    The beginning of  the implementation of an Ai more intelligent and reactuve for a more chaleenging game !

    public void prgrmdSending(ICountdownDuration countDown) {
        this.launcherCounter += 1;
        if (countDown.getWorldTimer() > 0) {
            //random flow
            if (this.launcherCounter == this.getSet().getDtLaunching()) {
                this.launcherCounter = 0;

                //setting the random color
                int colorKey = random(2);
                setTheRandomTexture(colorKey);

                //setting the random Texture in a random row
                int row = random(2);

                setTheRandomColumn(row, colorKey);
            }
            myWave(countDown);
        }

    }

    public void myWave(ICountdownDuration countDown){
        if (wave1) {
            wave1 =  createAWave(55, countDown);
        }
        if (wave2) {
            wave2 =  createAWave(40, countDown);
            //createAWave(40, countDown);
        }
        if (wave3) {
            wave3 =  createAWave(30, countDown);
        }
        if (wave4) {
            wave4 =  createAWave(25, countDown);
        }
        if (wave5) {
            wave5 =  createAWave(10, countDown);
            //createAWave(40, countDown);
        }

    }


    public void setOneSquare(int row, int colorkey){
        setTheRandomTexture(colorkey);
        setTheRandomColumn(row,colorkey);
    }

    public boolean createAWave(int time, ICountdownDuration countDown){

        if (time == countDown.getWorldTimer()){
            setOneSquare(random(2),random(2));
            setOneSquare(random(2),random(2));
            setOneSquare(random(2),random(2));
            return false;
        }
        return true;
    }
*/

}