package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Maxime Dc on 06/03/2018.
 */

public class PlayModeAi extends State implements GestureDetector.GestureListener {
    private Texture background;
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


    public PlayModeAi(GameStateManager gsm) {
        super(gsm);
        this.background = new Texture(Gdx.files.internal("background.png"));
        this.leftMap = new HashMap<Integer, Square>();
        this.centerMap = new HashMap<Integer, Square>();
        this.rightMap = new HashMap<Integer, Square>();
        choiceSquare = new Square();
        this.choiceSquare.setPosition(new Vector2(WIDTH * 1 / 16, HEIGHT * 1 / 5));
        this.leftCounter = 0;
        this.centerCounter = 0;
        this.rightCounter = 0;
        this.texture = new Texture(Gdx.files.internal("square.png"));
        score = new Score();
        this.counter = 0;

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
    }

    @Override
    public void render(SpriteBatch sb) {
        if (firstTouch) {
            for (int i = 0; i < leftCounter; i++) {
                leftMap.get(i).move();
                if (leftMap.get(i).getPosition().y == HEIGHT){
                    this.score.setUserScore(this.score.getUserScore()+1);
                }
            }
            for (int i = 0; i < centerCounter; i++) {
                centerMap.get(i).move();
                if (centerMap.get(i).getPosition().y == HEIGHT){
                    this.score.setUserScore(this.score.getUserScore()+1);
                }
            }
            for (int i = 0; i < rightCounter; i++) {
                rightMap.get(i).move();
                if (rightMap.get(i).getPosition().y == HEIGHT){
                    this.score.setUserScore(this.score.getUserScore()+1);
                }
            }
        }
        sb.begin();
        sb.draw(background, 0, 0);
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
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
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
}

