package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Antoine Dc on 06/03/2018.
 */

public class PlayModeAi extends State {
    private Texture background;
    private Square square;
    private Map<Integer, Square> leftMap;
    private Map<Integer, Square> centerMap;
    private Map<Integer, Square> rightMap;
    private boolean firstTouch = false;
    private Integer leftCounter;
    private Integer centerCounter;
    private Integer rightCounter;

    public PlayModeAi(GameStateManager gsm) {
        super(gsm);
        this.background=new Texture(Gdx.files.internal("background.png"));
        this.leftMap = new HashMap<Integer, Square>();
        this.centerMap = new HashMap<Integer, Square>();
        this.rightMap = new HashMap<Integer, Square>();
        square = new Square();
        this.leftCounter = 1;

    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()){
            if(Gdx.input.getX()>Gdx.graphics.getWidth()/4 && Gdx.input.getX()<Gdx.graphics.getWidth()/2 ){
                firstTouch = true;
                square.setPosition(new Vector2(Gdx.graphics.getWidth()*5/16, 0));
            }
            else if (Gdx.input.getX()>Gdx.graphics.getWidth()/2 && Gdx.input.getX()<Gdx.graphics.getWidth()*3/4 ){
                firstTouch = true;
                square.setPosition(new Vector2(Gdx.graphics.getWidth()*9/16, 0));
            }
            else if (Gdx.input.getX()>Gdx.graphics.getWidth()*3/4){
                firstTouch = true;
                square.setPosition(new Vector2(Gdx.graphics.getWidth()*13/16, 0));
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
            square.move();
        }
        sb.begin();
        sb.draw(background, 0, 0);
        if (firstTouch) {
            sb.draw(square.getTexture(), square.getPosition().x, square.getPosition().y);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        if (firstTouch){
            square.getTexture().dispose();
        }
    }
}