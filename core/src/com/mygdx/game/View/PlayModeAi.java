package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.AIPlayer;
import com.mygdx.game.model.Collision;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.Player;

import java.util.Map;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Max on 06/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class PlayModeAi extends State {
    private PreferencesSettings settings;

    private BitmapFont userScoreTxt;
    private BitmapFont aiScoreTxt;
    private BitmapFont timeTxt;

    private Square choiceSquare;
    private Boolean firstTouch = false;
    private Texture texture;
    private Integer colorkey;

    private Score score;
    private CountDown countDown;

    private AIPlayer ai;
    private Player player;

    private Collision collision;



    public PlayModeAi(GameStateManager gsm, PreferencesSettings settings) {
        super(gsm);
        this.userScoreTxt = new BitmapFont();
        this.userScoreTxt.getData().setScale(3);
        this.aiScoreTxt = new BitmapFont();
        this.aiScoreTxt.getData().setScale(3);
        this.timeTxt = new BitmapFont();
        this.timeTxt.getData().setScale(3);

        this.settings = settings;

        player = new Player();
        this.ai = new AIPlayer();

        this.choiceSquare = new Square();
        this.choiceSquare.setPosition(new Vector2(WIDTH * 1 / 16, HEIGHT * 1 / 5));

        this.texture = new Texture(Gdx.files.internal("square.png"));
        this.colorkey = 0;

        this.score = new Score();

        this.countDown = new CountDown(30, 0);

        this.collision = new Collision();


    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            //go to end mode just to test it
            if(Gdx.input.getY()<HEIGHT/4){
                gsm.set(new EndModeAI(gsm, settings, score));
            }

            //Colour choice button
            if ((Gdx.input.getX() < WIDTH / 4) && (HEIGHT - Gdx.input.getY() >= this.choiceSquare.getPosition().y)
                    && (HEIGHT - Gdx.input.getY() <= this.choiceSquare.getPosition().y + this.choiceSquare.getTexture().getHeight()) ) {
                this.colorkey = this.colorkey + 1;
                if (this.colorkey == 3) {
                    this.colorkey = 0;
                }
                if (this.colorkey == 0) {
                    this.texture = new Texture(Gdx.files.internal("square_red.png"));
                } else {
                    if (this.colorkey == 1) {
                        this.texture = new Texture(Gdx.files.internal("square_blue.png"));
                    } else {
                        this.texture = new Texture(Gdx.files.internal("square_yellow.png"));
                    }
                }
                this.choiceSquare.setTexture(texture);
            }
            //Implementation for the launcher of each row
            if (Gdx.input.getX() > WIDTH / 4 && Gdx.input.getX() < WIDTH / 2) {
                firstTouch = true;
                player.increment(player.getLeft(), player.getLeftCounter(), texture, 0, colorkey);
                player.setLeftCounter(player.getLeftCounter() + 1);
            } if (Gdx.input.getX() > WIDTH / 2 && Gdx.input.getX() < WIDTH * 3 / 4) {
                firstTouch = true;
                player.increment(player.getMiddle(), player.getMiddleCounter(), texture, 1, colorkey);
                player.setMiddleCounter(player.getMiddleCounter() + 1);
            } if (Gdx.input.getX() > WIDTH * 3 / 4) {
                firstTouch = true;
                player.increment(player.getRight(), player.getRightCounter(), texture, 2, colorkey);
                player.setRightCounter(player.getRightCounter() + 1);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        //updating the countdown
        countDown.update(dt);

        if (this.countDown.isTimeUp()){
            gsm.set(new EndModeAI(gsm, settings, score));
        }



        ai.send(countDown);


        //mooving the player's square;
        if (firstTouch) {
            for (int i = 0; i < player.getLeftCounter(); i++) {
                player.getLeft().get(i).move();
                //dealing with the score
                if (player.getLeft().get(i).getPosition().y >= HEIGHT &&
                        player.getLeft().get(i).getPosition().y < HEIGHT + this.choiceSquare.getSpeed().y){
                    this.score.updateUser();
                }
            }
            for (int i = 0; i < player.getMiddleCounter(); i++) {
                player.getMiddle().get(i).move();
                if ( player.getMiddle().get(i).getPosition().y >= HEIGHT &&
                        player.getMiddle().get(i).getPosition().y < HEIGHT + this.choiceSquare.getSpeed().y){
                    this.score.updateUser();
                }
            }
            for (int i = 0; i < player.getRightCounter(); i++) {
                player.getRight().get(i).move();
                if (player.getRight().get(i).getPosition().y >= HEIGHT &&
                        player.getRight().get(i).getPosition().y < HEIGHT + this.choiceSquare.getSpeed().y){
                    this.score.updateUser();
                }
            }

            //collision.collision(this.player, this.ai, this.firstTouch);
        }


        //mooving the Ai's squares
        for (int i = 0; i < ai.getComputer().getLeftCounter(); i++) {
            ai.getComputer().getLeft().get(i).reverseMove();
            //dealing with the score
            if (ai.getComputer().getLeft().get(i).getPosition().y <= 0 &&
                    ai.getComputer().getLeft().get(i).getPosition().y > - this.choiceSquare.getSpeed().y){
                this.score.updateAi();
            }
        }
        for (int i = 0; i < ai.getComputer().getMiddleCounter(); i++) {
            ai.getComputer().getMiddle().get(i).reverseMove();
            //dealing with the score
            if (ai.getComputer().getMiddle().get(i).getPosition().y <= 0 &&
                    ai.getComputer().getMiddle().get(i).getPosition().y > - this.choiceSquare.getSpeed().y){
                this.score.updateAi();
            }
        }
        for (int i = 0; i < ai.getComputer().getRightCounter(); i++) {
            ai.getComputer().getRight().get(i).reverseMove();
            if (ai.getComputer().getRight().get(i).getPosition().y <= 0 &&
                    ai.getComputer().getRight().get(i).getPosition().y > - this.choiceSquare.getSpeed().y){
                this.score.updateAi();
            }
        }

    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        //player's square drawing
        sb.draw(choiceSquare.getTexture(), WIDTH * 1 / 16, HEIGHT * 1 / 5);
        if (firstTouch) {
            for (int i = 0; i < player.getLeftCounter(); i++) {
                sb.draw(player.getLeft().get(i).getTexture(), player.getLeft().get(i).getPosition().x, player.getLeft().get(i).getPosition().y);
            }
            for (int i = 0; i < player.getMiddleCounter(); i++) {
                sb.draw(player.getMiddle().get(i).getTexture(), player.getMiddle().get(i).getPosition().x, player.getMiddle().get(i).getPosition().y);
            }
            for (int i = 0; i < player.getRightCounter(); i++) {
                sb.draw(player.getRight().get(i).getTexture(), player.getRight().get(i).getPosition().x, player.getRight().get(i).getPosition().y);
            }
        }

        //Ai's square drawing
        for (int i = 0; i < ai.getComputer().getLeftCounter(); i++) {
            sb.draw(ai.getComputer().getLeft().get(i).getTexture(), ai.getComputer().getLeft().get(i).getPosition().x, ai.getComputer().getLeft().get(i).getPosition().y);
        }
        for (int i = 0; i < ai.getComputer().getMiddleCounter(); i++) {
            sb.draw(ai.getComputer().getMiddle().get(i).getTexture(), ai.getComputer().getMiddle().get(i).getPosition().x, ai.getComputer().getMiddle().get(i).getPosition().y);
        }
        for (int i = 0; i < ai.getComputer().getRightCounter(); i++) {
            sb.draw(ai.getComputer().getRight().get(i).getTexture(), ai.getComputer().getRight().get(i).getPosition().x, ai.getComputer().getRight().get(i).getPosition().y);
        }

        //drawing the score and time
        userScoreTxt.draw(sb, String.valueOf(score.getUserScore()),
                WIDTH * 1/ 8 , HEIGHT/2 - HEIGHT*1/10);
        aiScoreTxt.draw(sb, String.valueOf(score.getAiScore()),
                WIDTH * 1/ 8 , HEIGHT/2 + HEIGHT*1/10);

        timeTxt.draw(sb, String.valueOf(this.countDown.getCountdownLabel().getText()),
                WIDTH * 1/ 8 - 3/2*this.countDown.getCountdownLabel().getWidth() , HEIGHT/2);
        sb.end();
    }

    @Override
    public void dispose() {
    }





    public BitmapFont getUserScoreTxt() {
        return userScoreTxt;
    }

    public void setUserScoreTxt(BitmapFont userScoreTxt) {
        this.userScoreTxt = userScoreTxt;
    }

    public Square getChoiceSquare() {
        return choiceSquare;
    }

    public void setChoiceSquare(Square choiceSquare) {
        this.choiceSquare = choiceSquare;
    }

    public Map<Integer, Square> getLeftMap() {
        return player.getLeft();
    }

    public boolean isFirstTouch() {
        return firstTouch;
    }

    public void setFirstTouch(boolean firstTouch) {
        this.firstTouch = firstTouch;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Integer getColorkey() {
        return colorkey;
    }

    public void setColorkey(Integer colorkey) {
        this.colorkey = colorkey;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}