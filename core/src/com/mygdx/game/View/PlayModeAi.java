package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.AIPlayer;
import com.mygdx.game.model.Collision;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Icon;
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
    private Music music;
    private Sound sound;

    private PreferencesSettings settings;
    private CountDown countDown;

    private AIPlayer ai;
    private Player player;

    private Collision collision;

    private Icon redChoiceSquare, blueChoiceSquare, yellowChoiceSquare;
    private Texture texture;
    private Integer colorKey;

    private Score score;


    public PlayModeAi(GameStateManager gsm, PreferencesSettings settings, CountDown countDown) {
        super(gsm);

        this.settings = settings;
        this.countDown = countDown;

        this.player = new Player(settings);
        this.ai = new AIPlayer(settings);
        this.ai.setSettings(settings);


        this.redChoiceSquare = new Icon(new Texture(Gdx.files.internal("square_red.png"))
                ,WIDTH * 1 / 16, HEIGHT * 3 / 10);
        this.blueChoiceSquare = new Icon(new Texture(Gdx.files.internal("square_blue.png"))
                ,WIDTH * 1 / 16, HEIGHT *  2 / 10);
        this.yellowChoiceSquare = new Icon(new Texture(Gdx.files.internal("square_yellow.png"))
                ,WIDTH * 1 / 16, HEIGHT * 1 / 10);

        this.texture = new Texture(Gdx.files.internal("square_red.png"));
        this.colorKey = 0;

        this.score = new Score();

        this.collision = new Collision();


        music=Gdx.audio.newMusic(Gdx.files.internal("sound/here.mp3"));
        music.setLooping(true);
        music.setVolume(Squarz.valueVolume*0.1f);
        music.play();

        sound = Gdx.audio.newSound(Gdx.files.internal("sound/meuh.mp3"));
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();

            //go to end mode, test only
            if (y > HEIGHT * 3 / 4) {
                music.stop();
                sound.stop();
                gsm.set(new EndModeAI(gsm, settings, score));
            }

            //Colour choice button
            chosingTheColour(x, y);

            //Implementation for the launcher of each row
            //if (!this.player.getSquareLimiter().isOver(colorKey)) {
                creatingANewSquare(x);
            //}
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        //updating the countdown
        this.countDown.update(dt);

        if (this.countDown.isTimeUp()){
            music.stop();
            gsm.set(new EndModeAI(gsm, settings, score));
        }

        //random sending by the AI
        ai.send(this.countDown);

        movingPlayerSquare();

        movingAiSquare();

        collision.collision(this.player, this.ai);
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(redChoiceSquare.getTexture(), redChoiceSquare.getPosX(), redChoiceSquare.getPosY());
        sb.draw(blueChoiceSquare.getTexture(), blueChoiceSquare.getPosX(), blueChoiceSquare.getPosY());
        sb.draw(yellowChoiceSquare.getTexture(), yellowChoiceSquare.getPosX(), yellowChoiceSquare.getPosY());

        drawingPlayerSquares(sb);
        drawingAiSquares(sb);

        drawTimeLeft(sb);
        drawScore(sb);
        drawCounter(sb);

        sb.end();
    }

    @Override
    public void dispose() {
        sound.dispose();
        music.dispose();
    }

    public void chosingTheColour(int x, int y){
        if (this.redChoiceSquare.contains(x, y)) {
            this.setColorKey(0);
            this.texture = new Texture(Gdx.files.internal("square_red.png"));
        }

        if (this.blueChoiceSquare.contains(x, y)) {
            this.setColorKey(1);
            this.texture = new Texture(Gdx.files.internal("square_blue.png"));
        }

        if (this.yellowChoiceSquare.contains(x, y)) {
            this.setColorKey(2);
            this.texture = new Texture(Gdx.files.internal("square_yellow.png"));
        }
    }

    public void creatingANewSquare(int x){
        if (x > WIDTH / 4 && x < WIDTH / 2) {
            player.increment(player.getLeft(), player.getLeftCounter(), texture, 0, colorKey);
            player.setLeftCounter(player.getLeftCounter() + 1);
        }
        if (x > WIDTH / 2 && x < WIDTH * 3 / 4) {
            player.increment(player.getMiddle(), player.getMiddleCounter(), texture, 1, colorKey);
            player.setMiddleCounter(player.getMiddleCounter() + 1);
        }
        if (x > WIDTH * 3 / 4) {
            player.increment(player.getRight(), player.getRightCounter(), texture, 2, colorKey);
            player.setRightCounter(player.getRightCounter() + 1);
        }
    }

    public void movingPlayerSquare(){
        for (int i = 0; i < player.getLeftCounter(); i++) {
            player.getLeft().get(i).move();
            //dealing with the score
            if (player.getLeft().get(i).getPosition().y >= HEIGHT && player.getLeft().get(i).getPosition().y < HEIGHT + this.settings.getStepX()){
                sound.play(Squarz.valueVolume*0.1f);
                Gdx.input.vibrate(Squarz.valueVibration*100);
                this.score.updateUser();
            }
        }
        for (int i = 0; i < player.getMiddleCounter(); i++) {
            player.getMiddle().get(i).move();
            if ( player.getMiddle().get(i).getPosition().y >= HEIGHT && player.getMiddle().get(i).getPosition().y < HEIGHT + this.settings.getStepX()){
                sound.play(Squarz.valueVolume*0.1f);
                Gdx.input.vibrate(Squarz.valueVibration*100);
                this.score.updateUser();
            }
        }
        for (int i = 0; i < player.getRightCounter(); i++) {
            player.getRight().get(i).move();
            if (player.getRight().get(i).getPosition().y >= HEIGHT && player.getRight().get(i).getPosition().y < HEIGHT + this.settings.getStepX()){
                sound.play(Squarz.valueVolume*0.1f);
                Gdx.input.vibrate(Squarz.valueVibration*100);
                this.score.updateUser();
            }
        }
    }

    public void movingAiSquare(){
        for (int i = 0; i < ai.getComputer().getLeftCounter(); i++) {
            ai.getComputer().getLeft().get(i).reverseMove();
            //dealing with the score
            if (ai.getComputer().getLeft().get(i).getPosition().y <= 0 && ai.getComputer().getLeft().get(i).getPosition().y > - this.settings.getStepX()){
                Gdx.input.vibrate(Squarz.valueVibration*100);
                this.score.updateAi();
            }
        }
        for (int i = 0; i < ai.getComputer().getMiddleCounter(); i++) {
            ai.getComputer().getMiddle().get(i).reverseMove();
            //dealing with the score
            if (ai.getComputer().getMiddle().get(i).getPosition().y <= 0 && ai.getComputer().getMiddle().get(i).getPosition().y > - this.settings.getStepX()){
                Gdx.input.vibrate(Squarz.valueVibration*100);
                this.score.updateAi();
            }
        }
        for (int i = 0; i < ai.getComputer().getRightCounter(); i++) {
            ai.getComputer().getRight().get(i).reverseMove();
            if (ai.getComputer().getRight().get(i).getPosition().y <= 0 && ai.getComputer().getRight().get(i).getPosition().y > - this.settings.getStepX()){
                Gdx.input.vibrate(Squarz.valueVibration*100);
                this.score.updateAi();
            }
        }
    }

    public void drawingPlayerSquares(SpriteBatch sb){
        for (int i = 0; i < player.getLeftCounter(); i++) {
            sb.draw(player.getLeft().get(i).getTexture(),
                    player.getLeft().get(i).getPosition().x, player.getLeft().get(i).getPosition().y);
        }
        for (int i = 0; i < player.getMiddleCounter(); i++) {
            sb.draw(player.getMiddle().get(i).getTexture(),
                    player.getMiddle().get(i).getPosition().x, player.getMiddle().get(i).getPosition().y);
        }
        for (int i = 0; i < player.getRightCounter(); i++) {
            sb.draw(player.getRight().get(i).getTexture(),
                    player.getRight().get(i).getPosition().x, player.getRight().get(i).getPosition().y);
        }
    }

    public void drawingAiSquares(SpriteBatch sb){
        for (int i = 0; i < ai.getComputer().getLeftCounter(); i++) {
            sb.draw(ai.getComputer().getLeft().get(i).getTexture(),
                    ai.getComputer().getLeft().get(i).getPosition().x, ai.getComputer().getLeft().get(i).getPosition().y);
        }
        for (int i = 0; i < ai.getComputer().getMiddleCounter(); i++) {
            sb.draw(ai.getComputer().getMiddle().get(i).getTexture(),
                    ai.getComputer().getMiddle().get(i).getPosition().x, ai.getComputer().getMiddle().get(i).getPosition().y);
        }
        for (int i = 0; i < ai.getComputer().getRightCounter(); i++) {
            sb.draw(ai.getComputer().getRight().get(i).getTexture(),
                    ai.getComputer().getRight().get(i).getPosition().x, ai.getComputer().getRight().get(i).getPosition().y);
        }
    }

    public void drawScore(SpriteBatch sb){
        Squarz.font.draw(sb, String.valueOf(score.getUserScore()),
                WIDTH * 1/ 8 , HEIGHT/2 - HEIGHT/15);
        Squarz.font.draw(sb, String.valueOf(score.getAiScore()),
                WIDTH * 1/ 8 , HEIGHT/2 + HEIGHT*3/15);
    }

    public void drawCounter(SpriteBatch sb){
        //number of user squares lefting
        Squarz.font.draw(sb, String.valueOf(this.player.getSquareLimiter().getRedLefting()), WIDTH * 1/4 + 10, HEIGHT/4);
        Squarz.font.draw(sb, String.valueOf(this.player.getSquareLimiter().getBlueLefting()), WIDTH * 2/4 + 10, HEIGHT/4);
        Squarz.font.draw(sb, String.valueOf(this.player.getSquareLimiter().getYellowLefting()), WIDTH * 3/4 + 10, HEIGHT/4);

        //number of Ai squares lefting
        Squarz.font.draw(sb, String.valueOf(this.ai.getComputer().getSquareLimiter().getRedLefting()), WIDTH * 1/4 + 10, HEIGHT*3/4);
        Squarz.font.draw(sb, String.valueOf(this.ai.getComputer().getSquareLimiter().getBlueLefting()), WIDTH * 2/4 + 10, HEIGHT*3/4);
        Squarz.font.draw(sb, String.valueOf(this.ai.getComputer().getSquareLimiter().getBlueLefting()), WIDTH * 3/4 + 10, HEIGHT*3/4);
    }

    public void drawTimeLeft(SpriteBatch sb){
        Squarz.font.draw(sb, String.valueOf(this.countDown.getCountdownLabel().getText()),
                WIDTH * 1/ 8 - 3/2*this.countDown.getCountdownLabel().getWidth() , HEIGHT/2);
    }






    /*public boolean isFirstTouch() {
        return firstTouch;
    }

    public void setFirstTouch(boolean firstTouch) {
        this.firstTouch = firstTouch;
    }
    */

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Integer getColorKey() {
        return colorKey;
    }

    public void setColorKey(Integer colorKey) {
        this.colorKey = colorKey;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public PreferencesSettings getSettings() {
        return settings;
    }

    public void setSettings(PreferencesSettings settings) {
        this.settings = settings;
    }

    public Icon getRedChoiceSquare() {
        return redChoiceSquare;
    }

    public void setRedChoiceSquare(Icon redChoiceSquare) {
        this.redChoiceSquare = redChoiceSquare;
    }

    public AIPlayer getAi() {
        return ai;
    }

    public void setAi(AIPlayer ai) {
        this.ai = ai;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Collision getCollision() {
        return collision;
    }

    public void setCollision(Collision collision) {
        this.collision = collision;
    }
}