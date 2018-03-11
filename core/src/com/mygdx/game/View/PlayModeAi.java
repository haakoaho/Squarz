package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Antoine Dc on 06/03/2018.
 */

public class PlayModeAi extends State {
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
    private Integer count;
    private Score score;

    public PlayModeAi(GameStateManager gsm) {
        super(gsm);
        this.background=new Texture(Gdx.files.internal("background.png"));
        this.leftMap = new HashMap<Integer, Square>();
        this.centerMap = new HashMap<Integer, Square>();
        this.rightMap = new HashMap<Integer, Square>();
        this.choiceSquare = new Square();
        this.leftCounter = 0;
        this.centerCounter = 0;
        this.rightCounter = 0;
        this.texture=new  Texture(Gdx.files.internal("square.png"));
        this.count = 0;
        this.score = new Score(0,0);

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            if(Gdx.input.getX()<WIDTH/4){
                this.count = this.count + 1;
                if (this.count == 3){
                    this.count = 0;
                }
                if(this.count == 0) {
                    this.texture = new Texture(Gdx.files.internal("square_red.png"));
                }else {
                    if(this.count == 1){
                        this.texture = new Texture(Gdx.files.internal("square_blue.png"));
                    }
                    else {
                        this.texture = new Texture(Gdx.files.internal("square_yellow.png"));
                    }
                }
                this.choiceSquare.setTexture(texture);
            }

            if(Gdx.input.getX()>WIDTH/4 && Gdx.input.getX()<WIDTH/2 ){
                firstTouch = true;
                leftMap.put(leftCounter, new Square());
                leftMap.get(leftCounter).setPosition(new Vector2(WIDTH*5/16, 0));
                leftMap.get(leftCounter).setTexture(texture);
                if (leftCounter != 0 && leftMap.get(leftCounter-1).getPosition().y < choiceSquare.getTexture().getHeight() + 5 ) {
                    leftMap.get(leftCounter).setPosition(new Vector2(Gdx.graphics.getWidth()*5/16,
                            leftMap.get(leftCounter-1).getPosition().y-choiceSquare.getTexture().getHeight()-5));
                }
                leftCounter = leftCounter +1 ;
            }
            else if (Gdx.input.getX()>WIDTH/2 && Gdx.input.getX()<WIDTH*3/4 ){
                firstTouch = true;
                centerMap.put(centerCounter, new Square());
                centerMap.get(centerCounter).setPosition(new Vector2(Gdx.graphics.getWidth()*9/16, 0));
                centerMap.get(centerCounter).setTexture(texture);
                if (centerCounter != 0 && centerMap.get(centerCounter-1).getPosition().y < choiceSquare.getTexture().getHeight() + 5 ) {
                    centerMap.get(centerCounter).setPosition(new Vector2(Gdx.graphics.getWidth()*9/16,
                            centerMap.get(centerCounter-1).getPosition().y-choiceSquare.getTexture().getHeight()-5));
                }
                centerCounter = centerCounter +1 ;
            }
            else if (Gdx.input.getX()>WIDTH*3/4){
                rightMap.put(rightCounter, new Square());
                rightMap.get(rightCounter).setPosition(new Vector2(Gdx.graphics.getWidth()*13/16, 0));
                rightMap.get(rightCounter).setTexture(texture);
                if (rightCounter != 0 && rightMap.get(rightCounter-1).getPosition().y < choiceSquare.getTexture().getHeight() + 5 ) {
                    rightMap.get(rightCounter).setPosition(new Vector2(Gdx.graphics.getWidth()*13/16,
                            rightMap.get(rightCounter-1).getPosition().y-choiceSquare.getTexture().getHeight()-5));
                }
                rightCounter = rightCounter +1 ;
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (firstTouch) {
            for (int i=0; i<leftCounter; i++) {
                leftMap.get(i).move();
            }
            for (int i=0; i<centerCounter; i++) {
                centerMap.get(i).move();
            }
            for (int i=0; i<rightCounter; i++) {
                rightMap.get(i).move();
            }
        }
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(choiceSquare.getTexture(), (float) Gdx.graphics.getWidth()*1/16, (float) Gdx.graphics.getHeight()*1/5);
        if (firstTouch) {
            for (int i=0; i<leftCounter; i++) {
                sb.draw(leftMap.get(i).getTexture(), leftMap.get(i).getPosition().x, leftMap.get(i).getPosition().y);
            }
            for (int i=0; i<centerCounter; i++) {
                sb.draw(centerMap.get(i).getTexture(), centerMap.get(i).getPosition().x, centerMap.get(i).getPosition().y);
            }
            for (int i=0; i<rightCounter; i++) {
                sb.draw(rightMap.get(i).getTexture(), rightMap.get(i).getPosition().x, rightMap.get(i).getPosition().y);
            }
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        choiceSquare.getTexture().dispose();
        if (firstTouch){
            for (int i=0; i<leftCounter; i++) {
                leftMap.get(i).getTexture().dispose();
            }
            for (int i=0; i<centerCounter; i++) {
                centerMap.get(i).getTexture().dispose();
            }
            for (int i=0; i<rightCounter; i++) {
                rightMap.get(i).getTexture().dispose();
            }
        }
    }
}
