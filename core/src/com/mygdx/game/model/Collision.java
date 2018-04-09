package com.mygdx.game.model;


import static com.mygdx.game.Squarz.HEIGHT;

/**
 * Created by Max on 14/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class Collision {

    public Collision() {
    }

    public void deleteOncePlayerOut(Player p, Integer column) {
        //if square goes outside (scores)

        //revoir !
            if (p.getCounter(column) > p.getFirstSquareKey(column) && !p.getMap(column).get(p.getFirstSquareKey(column)).isInUser()) {
                p.decrement(p.getFirstSquareKey(column), column);
            }
    }

    public void deleteOnceAiOut(Player p, Integer column){
        //if square goes outside
        if (p.getCounter(column) > p.getFirstSquareKey(column) && !p.getMap(column).get(p.getFirstSquareKey(column)).isInAi()) {
            p.decrement(p.getFirstSquareKey(column), column);
        }
    }

    public boolean isCollisionPossible(Player p, Player c, Integer rowKey){
        return p.getCounter(rowKey) > p.getFirstSquareKey(rowKey)
                && c.getCounter(rowKey) > c.getFirstSquareKey(rowKey);
    }
    public boolean isOverlapping(Player p, Player c, Integer rowKey){
        return (p.getMap(rowKey).get(p.getFirstSquareKey(rowKey)).getRectangle().overlaps(
                c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getRectangle()));
    }

    public void handleWhenPlayerRed(Player p, Player c, Integer rowKey){
        // if computer red
        if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 0) {
            p.decrement(p.getFirstSquareKey(rowKey), rowKey);
            c.decrement(c.getFirstSquareKey(rowKey), rowKey);

            //if computer blue
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 1) {
            p.decrement(p.getFirstSquareKey(rowKey), rowKey);

            //if computer yellow
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 2) {
            c.decrement(c.getFirstSquareKey(rowKey), rowKey);
        }
    }
    public  void handleWhenPlayerBlue(Player p, Player c, Integer rowKey){
        // if computer red
        if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 0) {
            c.decrement(c.getFirstSquareKey(rowKey), rowKey);

            //if computer blue
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 1) {
            p.decrement(p.getFirstSquareKey(rowKey), rowKey);
            c.decrement(c.getFirstSquareKey(rowKey), rowKey);

            //if computer yellow
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 2) {
            p.decrement(p.getFirstSquareKey(rowKey), rowKey);
        }
    }
    public  void handleWhenPlayerYellow(Player p, Player c, Integer rowKey){
        // if computer red
        if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 0) {
            p.decrement(p.getFirstSquareKey(rowKey), rowKey);

            //if computer blue
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 1) {
            c.decrement(c.getFirstSquareKey(rowKey), rowKey);

            //if computer yellow
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 2) {
            p.decrement(p.getFirstSquareKey(rowKey), rowKey);
            c.decrement(c.getFirstSquareKey(rowKey), rowKey);
        }
    }


    public void collision(Player player, AIPlayer computer) {

        for (int rowKey = 0; rowKey < 3; rowKey++) {
            if (!player.getMap(rowKey).isEmpty() && !computer.getMap(rowKey).isEmpty()) {
                //if square goes outside
                deleteOncePlayerOut(player, rowKey);
                deleteOnceAiOut(computer, rowKey);


                // if a square is in game (avoid null pointer exception) and overlaps
                if (isCollisionPossible(player, computer, rowKey) && isOverlapping(player, computer, rowKey)) {
                    //if player red
                    if (player.getMap(rowKey).get(player.getFirstSquareKey(rowKey)).getColorKey() == 0) {
                        handleWhenPlayerRed(player, computer, rowKey);
                        //si player est bleu
                    } else if (player.getMap(rowKey).get(player.getFirstSquareKey(rowKey)).getColorKey() == 1) {
                        handleWhenPlayerBlue(player, computer, rowKey);
                        //si player est jaune
                    } else if (player.getMap(rowKey).get(player.getFirstSquareKey(rowKey)).getColorKey() == 2) {
                        handleWhenPlayerYellow(player, computer, rowKey);
                    }
                }

            }
        }
    }
}