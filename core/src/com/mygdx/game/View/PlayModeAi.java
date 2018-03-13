package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;

import java.util.Map;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Maxime Dc on 06/03/2018.
 */


public class PlayModeAi extends State implements GestureDetector.GestureListener {
    private PreferencesSettings settings;
    private BitmapFont fontTxt;
    private BitmapFont timeTxt;
    private Square choiceSquare;
    private Player player;
    private boolean firstTouch = false;
    private Texture texture;
    private Integer counter;
    private Score score;


    public PlayModeAi(GameStateManager gsm, PreferencesSettings settings) {
        super(gsm);

        this.settings = settings;
        this.fontTxt = new BitmapFont();
        this.fontTxt.getData().setScale(3);
        this.timeTxt = new BitmapFont();
        this.timeTxt.getData().setScale(3);

        player = new Player();

        this.choiceSquare = new Square();
        this.choiceSquare.setPosition(new Vector2(WIDTH * 1 / 16, HEIGHT * 1 / 5));

        this.texture = new Texture(Gdx.files.internal("square.png"));
        this.score = new Score();
        this.counter = 0;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            //go to end mode just to test it
            if(Gdx.input.getY()<HEIGHT/4){

            }


            //Colour choice button
            if ((Gdx.input.getX() < WIDTH / 4) && (HEIGHT - Gdx.input.getY() >= this.choiceSquare.getPosition().y)
                    && (HEIGHT - Gdx.input.getY() <= this.choiceSquare.getPosition().y + this.choiceSquare.getTexture().getHeight()) ) {
                this.counter = this.counter + 1;
                if (this.counter == 3) {
                    this.counter = 0;
                }
                if (this.counter == 0) {
                    this.texture = new Texture(Gdx.files.internal("square_red.png"));
                } else {
                    if (this.counter == 1) {
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
                player.increment(player.getLeft(), player.getLeftCounter(), texture, 0);
                player.setLeftCounter(player.getLeftCounter() + 1);
            } if (Gdx.input.getX() > WIDTH / 2 && Gdx.input.getX() < WIDTH * 3 / 4) {
                firstTouch = true;
                player.increment(player.getMiddle(), player.getMiddleCounter(), texture, 1);
                player.setMiddleCounter(player.getMiddleCounter() + 1);
            } if (Gdx.input.getX() > WIDTH * 3 / 4) {
                firstTouch = true;
                player.increment(player.getRight(), player.getRightCounter(), texture, 2);
                player.setRightCounter(player.getRightCounter() + 1);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (firstTouch) {
            for (int i = 0; i < player.getLeftCounter(); i++) {
                player.getLeft().get(i).move();
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
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
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
        fontTxt.draw(sb, String.valueOf(score.getUserScore()),
                WIDTH * 1/ 8 , HEIGHT/2 - HEIGHT/10);

      // timeTxt.draw(sb, String.valueOf(this.countDown.getTime()),
        //        WIDTH * 1/ 8 , HEIGHT/2 + HEIGHT/10);
        sb.end();
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public BitmapFont getFontTxt() {
        return fontTxt;
    }

    public void setFontTxt(BitmapFont fontTxt) {
        this.fontTxt = fontTxt;
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

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}