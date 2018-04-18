package com.mygdx.game.model;


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

    public void deleteOncePlayerOut(Player p, Integer column) {
        //if square goes outside (scores)
        //revoir !
            if (p.getCounter(column) >= p.getFirstSquareKey(column) && !p.getMap(column).get(p.getFirstSquareKey(column)).isInUser()) {
                p.decrement(p.getFirstSquareKey(column), column);
                score.updateUser();
            }
    }

    public void deleteOnceOpponentOut(Player p, Integer column){
        //if square goes outside
        if (p.getCounter(column) > p.getFirstSquareKey(column) && !p.getMap(column).get(p.getFirstSquareKey(column)).isInAi()) {
            p.decrement(p.getFirstSquareKey(column), column);
            score.updateAi();
        }
    }

    public boolean isCollisionPossible(Player p, Player c, Integer columnKey){
        return p.getCounter(columnKey) > p.getFirstSquareKey(columnKey)
                && c.getCounter(columnKey) > c.getFirstSquareKey(columnKey);
    }
    public boolean isOverlapping(Player p, Player c, Integer columnKey){
        return (p.getMap(columnKey).get(p.getFirstSquareKey(columnKey)).getRectangle().overlaps(
                c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getRectangle()) || c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getRectangle().overlaps(
                p.getMap(columnKey).get(p.getFirstSquareKey(columnKey)).getRectangle()));
    }

    public void handleWhenPlayerRed(Player p, Player c, Integer columnKey){
        // if computer red
        if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 0) {
            p.decrement(p.getFirstSquareKey(columnKey), columnKey);
            c.decrement(c.getFirstSquareKey(columnKey), columnKey);

            //if computer blue
        } else if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 1) {
            p.decrement(p.getFirstSquareKey(columnKey), columnKey);

            //if computer yellow
        } else if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 2) {
            c.decrement(c.getFirstSquareKey(columnKey), columnKey);

            //if computer punisher
        } else if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 4) {
            p.decrement(p.getFirstSquareKey(columnKey), columnKey);
        }
    }
    public  void handleWhenPlayerBlue(Player p, Player c, Integer columnKey){
        // if computer red
        if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 0) {
            c.decrement(c.getFirstSquareKey(columnKey), columnKey);

            //if computer blue
        } else if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 1) {
            p.decrement(p.getFirstSquareKey(columnKey), columnKey);
            c.decrement(c.getFirstSquareKey(columnKey), columnKey);

            //if computer yellow
        } else if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 2) {
            p.decrement(p.getFirstSquareKey(columnKey), columnKey);

            //if computer punisher
        } else if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 4) {
            p.decrement(p.getFirstSquareKey(columnKey), columnKey);
        }
    }
    public  void handleWhenPlayerYellow(Player p, Player c, Integer columnKey){
        // if computer red
        if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 0) {
            p.decrement(p.getFirstSquareKey(columnKey), columnKey);

            //if computer blue
        } else if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 1) {
            c.decrement(c.getFirstSquareKey(columnKey), columnKey);

            //if computer yellow
        } else if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 2) {
            p.decrement(p.getFirstSquareKey(columnKey), columnKey);
            c.decrement(c.getFirstSquareKey(columnKey), columnKey);

            //if computer punisher
        } else if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 4) {
            p.decrement(p.getFirstSquareKey(columnKey), columnKey);
        }
    }

    public void handleWhenPlayerPunisherBonus(Player p, Player c, Integer columnKey){
        // if computer also sent a punisher
        if (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey() == 4) {
            p.decrement(p.getFirstSquareKey(columnKey), columnKey);
            c.decrement(c.getFirstSquareKey(columnKey), columnKey);

        } else {
            c.decrement(p.getFirstSquareKey(columnKey), columnKey);
        }
    }

    public void collision(Player player, AIPlayer opponent) {
        for (int columnKey = 0; columnKey < 3; columnKey++) {
            //if square goes outside
            if(!player.getMap(columnKey).isEmpty()) {
                deleteOncePlayerOut(player, columnKey);
            }
            if(!opponent.getMap(columnKey).isEmpty()) {
                deleteOnceOpponentOut(opponent, columnKey);
            }

            if (!player.getMap(columnKey).isEmpty() && !opponent.getMap(columnKey).isEmpty()) {
                // if a square is in game (avoid null pointer exception) and overlaps
                if (isCollisionPossible(player, opponent, columnKey) && isOverlapping(player, opponent, columnKey)) {
                    //if player red
                    if (player.getMap(columnKey).get(player.getFirstSquareKey(columnKey)).getColorKey() == 0) {
                        handleWhenPlayerRed(player, opponent, columnKey);
                        //si player est bleu
                    } else if (player.getMap(columnKey).get(player.getFirstSquareKey(columnKey)).getColorKey() == 1) {
                        handleWhenPlayerBlue(player, opponent, columnKey);
                        //si player est jaune
                    } else if (player.getMap(columnKey).get(player.getFirstSquareKey(columnKey)).getColorKey() == 2) {
                        handleWhenPlayerYellow(player, opponent, columnKey);
                        //si player uses the punisher bonus
                    } else if (player.getMap(columnKey).get(player.getFirstSquareKey(columnKey)).getColorKey() == 4) {
                        handleWhenPlayerPunisherBonus(player, opponent, columnKey);
                        //si player uses the Mr.Propre bonus
                    }
                }
            }
        }
    }
}