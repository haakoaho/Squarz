package com.mygdx.game.model;


/**
 * Created by Max on 14/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class Collision {
    private final Score score;
    public Collision(Score score) {
        this.score = score;
    }

    private void deleteOncePlayerOut(Player p, Integer column) {
        //if square goes outside (scores)
        //revoir !
            if (p.getCounter(column) >= p.getFirstSquareKey(column) && !p.getMap(column).get(p.getFirstSquareKey(column)).isInUser()) {
                p.decrement(p.getFirstSquareKey(column), column);
                score.updateUser();
            }
    }

    private void deleteOnceOpponentOut(Player p, Integer column){
        //if square goes outside
        if (p.getCounter(column) > p.getFirstSquareKey(column) && !p.getMap(column).get(p.getFirstSquareKey(column)).isInAi()) {
            p.decrement(p.getFirstSquareKey(column), column);
            score.updateAi();
        }
    }

    private boolean isCollisionPossible(Player p, Player c, Integer columnKey){
        return p.getCounter(columnKey) > p.getFirstSquareKey(columnKey)
                && c.getCounter(columnKey) > c.getFirstSquareKey(columnKey);
    }
    private boolean isOverlapping(Player p, Player c, Integer columnKey){
        return (p.getMap(columnKey).get(p.getFirstSquareKey(columnKey)).getRectangle().overlaps(
                c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getRectangle()) || c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getRectangle().overlaps(
                p.getMap(columnKey).get(p.getFirstSquareKey(columnKey)).getRectangle()));
    }

    private void handleWhenPlayerRed(Player p, Player c, Integer columnKey){
        // if computer red
        switch (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey()) {
            case 0:
                p.decrement(p.getFirstSquareKey(columnKey), columnKey);
                c.decrement(c.getFirstSquareKey(columnKey), columnKey);

                //if computer blue
                break;
            case 1:
                p.decrement(p.getFirstSquareKey(columnKey), columnKey);

                //if computer yellow
                break;
            case 2:
                c.decrement(c.getFirstSquareKey(columnKey), columnKey);

                //if computer punisher
                break;
            case 4:
                p.decrement(p.getFirstSquareKey(columnKey), columnKey);
                break;
        }
    }
    private void handleWhenPlayerBlue(Player p, Player c, Integer columnKey){
        // if computer red
        switch (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey()) {
            case 0:
                c.decrement(c.getFirstSquareKey(columnKey), columnKey);

                //if computer blue
                break;
            case 1:
                p.decrement(p.getFirstSquareKey(columnKey), columnKey);
                c.decrement(c.getFirstSquareKey(columnKey), columnKey);

                //if computer yellow
                break;
            case 2:
                p.decrement(p.getFirstSquareKey(columnKey), columnKey);

                //if computer punisher
                break;
            case 4:
                p.decrement(p.getFirstSquareKey(columnKey), columnKey);
                break;
        }
    }
    private void handleWhenPlayerYellow(Player p, Player c, Integer columnKey){
        // if computer red
        switch (c.getMap(columnKey).get(c.getFirstSquareKey(columnKey)).getColorKey()) {
            case 0:
                p.decrement(p.getFirstSquareKey(columnKey), columnKey);

                //if computer blue
                break;
            case 1:
                c.decrement(c.getFirstSquareKey(columnKey), columnKey);

                //if computer yellow
                break;
            case 2:
                p.decrement(p.getFirstSquareKey(columnKey), columnKey);
                c.decrement(c.getFirstSquareKey(columnKey), columnKey);

                //if computer punisher
                break;
            case 4:
                p.decrement(p.getFirstSquareKey(columnKey), columnKey);
                break;
        }
    }

    private void handleWhenPlayerPunisherBonus(Player p, Player c, Integer columnKey){
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
                    switch (player.getMap(columnKey).get(player.getFirstSquareKey(columnKey)).getColorKey()) {
                        case 0:
                            handleWhenPlayerRed(player, opponent, columnKey);
                            //si player est bleu
                            break;
                        case 1:
                            handleWhenPlayerBlue(player, opponent, columnKey);
                            //si player est jaune
                            break;
                        case 2:
                            handleWhenPlayerYellow(player, opponent, columnKey);
                            //si player uses the punisher bonus
                            break;
                        case 4:
                            handleWhenPlayerPunisherBonus(player, opponent, columnKey);
                            //si player uses the Mr.Propre bonus
                            break;
                    }
                }
            }
        }
    }
}