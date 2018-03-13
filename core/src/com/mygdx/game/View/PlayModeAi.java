package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;
import com.mygdx.game.view.beginning.Pref;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Maxime Dc on 06/03/2018.
 */

public class PlayModeAi extends State implements GestureDetector.GestureListener {
    private BitmapFont fontTxt;
    private BitmapFont timeTxt;
    private Square choiceSquare;
    private Map<Integer, Square> leftMap;
    private Map<Integer, Square> centerMap;
    private Map<Integer, Square> rightMap;
    private boolean firstTouch = false;
    private Integer leftCounter;
    private Integer centerCounter;
    private Integer rightCounter;
    private Texture texture;
    private Integer counter;
    private Score score;
    private CountDown countDown;


    public PlayModeAi(GameStateManager gsm) {
        super(gsm);
        this.fontTxt = new BitmapFont();
        this.fontTxt.getData().setScale(3);
        this.timeTxt = new BitmapFont();
        this.timeTxt.getData().setScale(3);
        this.leftMap = new HashMap<Integer, Square>();
        this.centerMap = new HashMap<Integer, Square>();
        this.rightMap = new HashMap<Integer, Square>();
        this.choiceSquare = new Square();
        this.choiceSquare.setPosition(new Vector2(WIDTH * 1 / 16, HEIGHT * 1 / 5));
        this.leftCounter = 0;
        this.centerCounter = 0;
        this.rightCounter = 0;
        this.texture = new Texture(Gdx.files.internal("square.png"));
        this.score = new Score();
        this.counter = 0;
        this.countDown = new CountDown(300, 0);

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
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
                leftMap.put(leftCounter, new Square());
                leftMap.get(leftCounter).setPosition(new Vector2(WIDTH * 5 / 16, 0));
                leftMap.get(leftCounter).setTexture(texture);
                if (leftCounter != 0 && leftMap.get(leftCounter - 1).getPosition().y < choiceSquare.getTexture().getHeight() + 5) {
                    leftMap.get(leftCounter).setPosition(new Vector2(Gdx.graphics.getWidth() * 5 / 16,
                            leftMap.get(leftCounter - 1).getPosition().y - choiceSquare.getTexture().getHeight() - 5));
                }
                leftCounter = leftCounter + 1;
            } else if (Gdx.input.getX() > WIDTH / 2 && Gdx.input.getX() < WIDTH * 3 / 4) {
                firstTouch = true;
                centerMap.put(centerCounter, new Square());
                centerMap.get(centerCounter).setPosition(new Vector2(WIDTH * 9 / 16, 0));
                centerMap.get(centerCounter).setTexture(texture);
                if (centerCounter != 0 && centerMap.get(centerCounter - 1).getPosition().y < choiceSquare.getTexture().getHeight() + 5) {
                    centerMap.get(centerCounter).setPosition(new Vector2(Gdx.graphics.getWidth() * 9 / 16,
                            centerMap.get(centerCounter - 1).getPosition().y - choiceSquare.getTexture().getHeight() - 5));
                }
                centerCounter = centerCounter + 1;
            } else if (Gdx.input.getX() > WIDTH * 3 / 4) {
                rightMap.put(rightCounter, new Square());
                rightMap.get(rightCounter).setPosition(new Vector2(WIDTH * 13 / 16, 0));
                rightMap.get(rightCounter).setTexture(texture);
                if (rightCounter != 0 && rightMap.get(rightCounter - 1).getPosition().y < choiceSquare.getTexture().getHeight() + 5) {
                    rightMap.get(rightCounter).setPosition(new Vector2(Gdx.graphics.getWidth() * 13 / 16,
                            rightMap.get(rightCounter - 1).getPosition().y - choiceSquare.getTexture().getHeight() - 5));
                }
                rightCounter = rightCounter + 1;
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        countDown.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {



        if (firstTouch) {
            for (int i = 0; i < leftCounter; i++) {
                leftMap.get(i).move();
                if (leftMap.get(i).getPosition().y >= HEIGHT &&
                        leftMap.get(i).getPosition().y < HEIGHT + this.choiceSquare.getSpeed().y){
                    this.score.updateUser();
                }
            }
            for (int i = 0; i < centerCounter; i++) {
                centerMap.get(i).move();
                if ( centerMap.get(i).getPosition().y >= HEIGHT &&
                        centerMap.get(i).getPosition().y < HEIGHT + this.choiceSquare.getSpeed().y){
                    this.score.updateUser();
                }
            }
            for (int i = 0; i < rightCounter; i++) {
                rightMap.get(i).move();
                if (rightMap.get(i).getPosition().y >= HEIGHT &&
                        rightMap.get(i).getPosition().y < HEIGHT + this.choiceSquare.getSpeed().y){
                    this.score.updateUser();
                }
            }
        }
        sb.begin();
        sb.draw(choiceSquare.getTexture(), WIDTH * 1 / 16, HEIGHT * 1 / 5);
        if (firstTouch) {
            for (int i = 0; i < leftCounter; i++) {
                sb.draw(leftMap.get(i).getTexture(), leftMap.get(i).getPosition().x, leftMap.get(i).getPosition().y);

            }
            for (int i = 0; i < centerCounter; i++) {
                sb.draw(centerMap.get(i).getTexture(), centerMap.get(i).getPosition().x, centerMap.get(i).getPosition().y);
            }
            for (int i = 0; i < rightCounter; i++) {
                sb.draw(rightMap.get(i).getTexture(), rightMap.get(i).getPosition().x, rightMap.get(i).getPosition().y);
            }
        }
        fontTxt.draw(sb, String.valueOf(score.getUserScore()),
                WIDTH * 1/ 8 , HEIGHT/2 - HEIGHT/10);

        //timeTxt.draw(sb, String.valueOf(this.countDown.getTime()),
        //        WIDTH * 1/ 8 , HEIGHT/2 + HEIGHT/10);
        sb.end();
    }

    @Override
    public void dispose() {
        choiceSquare.getTexture().dispose();
        if (firstTouch) {
            for (int i = 0; i < leftCounter; i++) {
                leftMap.get(i).getTexture().dispose();
            }
            for (int i = 0; i < centerCounter; i++) {
                centerMap.get(i).getTexture().dispose();
            }
            for (int i = 0; i < rightCounter; i++) {
                rightMap.get(i).getTexture().dispose();
            }
        }
        countDown.dispose();
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
        return leftMap;
    }

    public void setLeftMap(Map<Integer, Square> leftMap) {
        this.leftMap = leftMap;
    }

    public Map<Integer, Square> getCenterMap() {
        return centerMap;
    }

    public void setCenterMap(Map<Integer, Square> centerMap) {
        this.centerMap = centerMap;
    }

    public Map<Integer, Square> getRightMap() {
        return rightMap;
    }

    public void setRightMap(Map<Integer, Square> rightMap) {
        this.rightMap = rightMap;
    }

    public boolean isFirstTouch() {
        return firstTouch;
    }

    public void setFirstTouch(boolean firstTouch) {
        this.firstTouch = firstTouch;
    }

    public Integer getLeftCounter() {
        return leftCounter;
    }

    public void setLeftCounter(Integer leftCounter) {
        this.leftCounter = leftCounter;
    }

    public Integer getCenterCounter() {
        return centerCounter;
    }

    public void setCenterCounter(Integer centerCounter) {
        this.centerCounter = centerCounter;
    }

    public Integer getRightCounter() {
        return rightCounter;
    }

    public void setRightCounter(Integer rightCounter) {
        this.rightCounter = rightCounter;
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

