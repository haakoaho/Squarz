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
    private Score score;
    public Collision(Score score) {
        this.score = score;
    }

    public void deleteOncePlayerOut(Player p, Integer column, Score s) {
        //if square goes outside (scores)
        //pourquoi marche alors que pas de rapport a Height
            if (p.getCounter(column) > p.getFirstSquareKey(column) && !p.getMap(column).get(p.getFirstSquareKey(column)).isInUser()) {
                p.decrement(p.getMap(column), p.getFirstSquareKey(column), column);
            }
    }

    public void deleteOnceAiOut(Player p, Integer column, Score s){
        //if square goes outside
            if (p.getCounter(column) > p.getFirstSquareKey(column) && !p.getMap(column).get(p.getFirstSquareKey(column)).isInAi()) {
                p.decrement(p.getMap(column), p.getFirstSquareKey(column), column);
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
            p.decrement(p.getMap(rowKey), p.getFirstSquareKey(rowKey), rowKey);
            c.decrement(c.getMap(rowKey), c.getFirstSquareKey(rowKey), rowKey);

            //if computer blue
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 1) {
            p.decrement(p.getMap(rowKey), p.getFirstSquareKey(rowKey), rowKey);

            //if computer yellow
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 2) {
            c.decrement(c.getMap(rowKey), c.getFirstSquareKey(rowKey), rowKey);
        }
    }
    public  void handleWhenPlayerBlue(Player p, Player c, Integer rowKey){
        // if computer red
        if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 0) {
            c.decrement(c.getMap(rowKey), c.getFirstSquareKey(rowKey), rowKey);

            //if computer blue
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 1) {
            p.decrement(p.getMap(rowKey), p.getFirstSquareKey(rowKey), rowKey);
            c.decrement(c.getMap(rowKey), c.getFirstSquareKey(rowKey), rowKey);

            //if computer yellow
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 2) {
            p.decrement(p.getMap(rowKey), p.getFirstSquareKey(rowKey), rowKey);
        }
    }
    public  void handleWhenPlayerYellow(Player p, Player c, Integer rowKey){
        // if computer red
        if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 0) {
            p.decrement(p.getMap(rowKey), p.getFirstSquareKey(rowKey), rowKey);

            //if computer blue
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 1) {
            c.decrement(c.getMap(rowKey), c.getFirstSquareKey(rowKey), rowKey);

            //if computer yellow
        } else if (c.getMap(rowKey).get(c.getFirstSquareKey(rowKey)).getColorKey() == 2) {
            p.decrement(p.getMap(rowKey), p.getFirstSquareKey(rowKey), rowKey);
            c.decrement(c.getMap(rowKey), c.getFirstSquareKey(rowKey), rowKey);
        }
    }


    public void collision(Player player, AIPlayer computer, Score score) {

        for (int rowKey = 0; rowKey < 3; rowKey++) {
            if (!player.getMap(rowKey).isEmpty() && !computer.getComputer().getMap(rowKey).isEmpty()) {
                //if square goes outside
                deleteOncePlayerOut(player, rowKey, score);
                deleteOnceAiOut(computer.getComputer(), rowKey, score);


                // if a square is in game (avoid null pointer exception) and overlaps
                if (isCollisionPossible(player, computer.getComputer(), rowKey) && isOverlapping(player, computer.getComputer(), rowKey)) {
                    //if player red
                    if (player.getMap(rowKey).get(player.getFirstSquareKey(rowKey)).getColorKey() == 0) {
                        handleWhenPlayerRed(player, computer.getComputer(), rowKey);
                        //si player est bleu
                    } else if (player.getMap(rowKey).get(player.getFirstSquareKey(rowKey)).getColorKey() == 1) {
                        handleWhenPlayerBlue(player, computer.getComputer(), rowKey);
                        //si player est jaune
                    } else if (player.getMap(rowKey).get(player.getFirstSquareKey(rowKey)).getColorKey() == 2) {
                        handleWhenPlayerYellow(player, computer.getComputer(), rowKey);
                    }
                }

            }
        }
    }
}