package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Ai;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;
import com.mygdx.game.view.beginning.Menu;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.Player;



import java.util.Map;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Maxime Dc on 06/03/2018.
 */


public class PlayModeAi extends State {
    private Music music;
    private Sound sound;

    private PreferencesSettings settings;

    private Square choiceSquare;
    private Boolean firstTouch = false;
    private Texture texture;
    private Integer counter;

    private Score score;
    private CountDown countDown;

    private Ai ai;
    private Player player;



    public PlayModeAi(GameStateManager gsm, PreferencesSettings settings) {
        super(gsm);

        this.settings = settings;

        player = new Player();
        this.ai = new Ai();

        this.choiceSquare = new Square();
        this.choiceSquare.setPosition(new Vector2(WIDTH * 1 / 16, HEIGHT * 1 / 5));

        this.texture = new Texture(Gdx.files.internal("square.png"));
        this.counter = 0;

        this.score = new Score();

        this.countDown = new CountDown(10, 0);

        music=Gdx.audio.newMusic(Gdx.files.internal("sound/dkr.mp3"));
        music.setLooping(true);
        music.setVolume(Squarz.valueVolume*0.1f);
        music.play();

        sound = Gdx.audio.newSound(Gdx.files.internal("sound/meuh.mp3"));

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            //go to end mode just to test it
            if(Gdx.input.getY()<HEIGHT/4){
                music.stop();
                gsm.set(new EndModeAI(gsm, settings, score));
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

        countDown.update(dt);
        if (this.countDown.getTimeUp()){
            music.stop();
            gsm.set(new Menu(gsm));
        }

        ai.send(countDown);


        if (firstTouch) {
            for (int i = 0; i < player.getLeftCounter(); i++) {
                player.getLeft().get(i).move();
                if (player.getLeft().get(i).getPosition().y >= HEIGHT &&
                        player.getLeft().get(i).getPosition().y < HEIGHT + this.choiceSquare.getSpeed().y){
                    sound.play(Squarz.valueVolume*0.1f);
                    Gdx.input.vibrate(Squarz.valueVibration*100);
                    this.score.updateUser();
                }
            }
            for (int i = 0; i < player.getMiddleCounter(); i++) {
                player.getMiddle().get(i).move();
                if ( player.getMiddle().get(i).getPosition().y >= HEIGHT &&
                        player.getMiddle().get(i).getPosition().y < HEIGHT + this.choiceSquare.getSpeed().y){
                    sound.play(Squarz.valueVolume*0.1f);
                    Gdx.input.vibrate(Squarz.valueVibration*100);
                    this.score.updateUser();
                }
            }
            for (int i = 0; i < player.getRightCounter(); i++) {
                player.getRight().get(i).move();
                if (player.getRight().get(i).getPosition().y >= HEIGHT &&
                        player.getRight().get(i).getPosition().y < HEIGHT + this.choiceSquare.getSpeed().y){
                    sound.play(Squarz.valueVolume*0.1f);
                    Gdx.input.vibrate(Squarz.valueVibration*100);
                    this.score.updateUser();
                }
            }
        }
        //Ai squares
        for (int i = 0; i < ai.getLeftCounter(); i++) {
            ai.getLeftMap().get(i).reverseMove();
            if (ai.getLeftMap().get(i).getPosition().y <= 0 &&
                    ai.getLeftMap().get(i).getPosition().y > - this.choiceSquare.getSpeed().y){
                sound.play(Squarz.valueVolume*0.1f);
                this.score.updateAi();
            }
        }
        for (int i = 0; i < ai.getCenterCounter(); i++) {
            ai.getCenterMap().get(i).reverseMove();
            if ( ai.getCenterMap().get(i).getPosition().y <= 0 &&
                    ai.getCenterMap().get(i).getPosition().y > - this.choiceSquare.getSpeed().y){
                sound.play(Squarz.valueVolume*0.1f);
                this.score.updateAi();
            }
        }
        for (int i = 0; i < ai.getRightCounter(); i++) {
            ai.getRightMap().get(i).reverseMove();
            if (ai.getRightMap().get(i).getPosition().y <= 0 &&
                    ai.getRightMap().get(i).getPosition().y > - this.choiceSquare.getSpeed().y){
                sound.play(Squarz.valueVolume*0.1f);
                this.score.updateAi();
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

        //Ai drawing
        for (int i = 0; i < ai.getLeftCounter(); i++) {
            sb.draw(ai.getLeftMap().get(i).getTexture(), ai.getLeftMap().get(i).getPosition().x, ai.getLeftMap().get(i).getPosition().y);
        }
        for (int i = 0; i < ai.getCenterCounter(); i++) {
            sb.draw(ai.getCenterMap().get(i).getTexture(), ai.getCenterMap().get(i).getPosition().x, ai.getCenterMap().get(i).getPosition().y);
        }
        for (int i = 0; i < ai.getRightCounter(); i++) {
            sb.draw(ai.getRightMap().get(i).getTexture(), ai.getRightMap().get(i).getPosition().x, ai.getRightMap().get(i).getPosition().y);
        }

        Squarz.font.draw(sb, String.valueOf(score.getUserScore()),
                WIDTH * 1/ 8 , HEIGHT/2 - HEIGHT/10);
        Squarz.font.draw(sb, String.valueOf(score.getAiScore()),
                WIDTH * 1/ 8 , HEIGHT/2 + HEIGHT*3/10);
        Squarz.font.draw(sb, String.valueOf(this.countDown.getCountdownLabel().getText()),
                WIDTH * 1/ 8 - 3/2*this.countDown.getCountdownLabel().getWidth() , HEIGHT*3/4);
        sb.end();
    }

    @Override
    public void dispose() {
        sound.dispose();
        music.dispose();
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