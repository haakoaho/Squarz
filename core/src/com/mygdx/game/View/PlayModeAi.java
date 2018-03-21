package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import static com.mygdx.game.Squarz.format;

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
    private GlyphLayout readyGlyph, redLeft, yellowLeft, blueLeft, scoreUser, scoreAi, time;

    private ShapeRenderer shapeRenderer;

    private PreferencesSettings settings;
    private CountDown countDown;

    private AIPlayer ai;
    private Player player;

    private Collision collision;

    private Icon redChoiceSquare, blueChoiceSquare, yellowChoiceSquare, pause;
    private Texture texture;
    private Integer colorKey;

    private Score score;

    private Boolean ready = false;
    private Boolean pauseFlag = false;


    public PlayModeAi(GameStateManager gsm, PreferencesSettings settings, CountDown countDown) {
        super(gsm);

        this.settings = settings;
        this.countDown = countDown;

        this.player = new Player(settings);
        this.ai = new AIPlayer(settings);
        this.ai.setSettings(settings);

        this.score = new Score();
        this.shapeRenderer = new ShapeRenderer();

        this.redLeft = new GlyphLayout(Squarz.font, String.valueOf(this.player.getSquareLimiter().getRedLefting()));
        this.yellowLeft = new GlyphLayout(Squarz.font, String.valueOf(this.player.getSquareLimiter().getYellowLefting()));
        this.blueLeft = new GlyphLayout(Squarz.font, String.valueOf(this.player.getSquareLimiter().getBlueLefting()));
        this.scoreAi = new GlyphLayout(Squarz.font, String.valueOf(score.getAiScore()));
        this.scoreUser = new GlyphLayout(Squarz.font, String.valueOf(score.getUserScore()));
        this.time = new GlyphLayout(Squarz.font, String.valueOf(this.countDown.getCountdownLabel().getText()));

        this.texture = new Texture(Gdx.files.internal(format+"/square/square_red.png"));

        this.redChoiceSquare = new Icon(new Texture(Gdx.files.internal(format+"/square/square_red.png"))
                ,WIDTH * 1/16, HEIGHT/2 - this.texture.getHeight() * 3/2);
        this.blueChoiceSquare = new Icon(new Texture(Gdx.files.internal(format+"/square/square_blue.png"))
                ,WIDTH * 1/16, HEIGHT/2 - this.texture.getHeight() * 11/4);
        this.yellowChoiceSquare = new Icon(new Texture(Gdx.files.internal(format+"/square/square_yellow.png"))
                ,WIDTH * 1/16, HEIGHT/2 - this.texture.getHeight() * 4);
        this.pause = new Icon(new Texture(Gdx.files.internal(format+"/pause.png"))
                ,WIDTH * 1/16, HEIGHT * 15/16 - this.texture.getHeight()/2);
        this.colorKey = 0;


        this.collision = new Collision();

        readyGlyph = new GlyphLayout(Squarz.font, "READY ?");

        music=Gdx.audio.newMusic(Gdx.files.internal("sound/reset.mp3"));
        music.setLooping(true);
        music.setVolume(Squarz.valueVolume*0.15f);
        music.play();

        sound = Gdx.audio.newSound(Gdx.files.internal("sound/goal.mp3"));
    }

    @Override
    public void handleInput() {


        if (Gdx.input.justTouched()) {
            if (!ready) {
                ready = true;

            } else {
                int x = Gdx.input.getX();
                int y = HEIGHT - Gdx.input.getY();

                //go to end mode, test only
                if (y > HEIGHT * 3 / 4) {
                    music.stop();
                    sound.stop();
                    gsm.set(new EndModeAI(gsm, settings, score, countDown));
                }
                /*
                if (pause.contains(x, y)){
                    pauseFlag = true;
                }
                */

                chosingTheColour(x, y);

                if (!this.player.getSquareLimiter().isOver(colorKey)) {
                    creatingANewSquare(x);
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        if (pauseFlag)    {
            dt = 0;
            pauseFlag = false;}

        if (ready) {

            //updating the countdown
            this.countDown.update(dt);

            if (this.countDown.isTimeUp()) {
                music.stop();
                gsm.set(new EndModeAI(gsm, settings, score, countDown));
            }

            //random sending by the AI
            ai.send(this.countDown);
            //ai.prgrmdSending(this.countDown);

            movingPlayerSquare();

            movingAiSquare();

            collision.collision(this.player, this.ai);
        }
    }


    @Override
    public void render(SpriteBatch sb) {

        if(!ready){
            sb.begin();
            Squarz.font.draw(sb, readyGlyph,
                    (float) (WIDTH/2 - readyGlyph.width/2.), HEIGHT/2 - readyGlyph.height/2);
            sb.end();
        } else {
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

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.line(WIDTH/4,0,WIDTH/4,HEIGHT);
            shapeRenderer.line(WIDTH/4-10,0,WIDTH/4-10,HEIGHT);
            shapeRenderer.line(WIDTH/2,0,WIDTH/2,HEIGHT);
            shapeRenderer.line(3*WIDTH/4,0,3*WIDTH/4,HEIGHT);
            shapeRenderer.line(0,HEIGHT/2,WIDTH/4-10,HEIGHT/2);
            shapeRenderer.line(0,3*HEIGHT/4,WIDTH/4-10,3*HEIGHT/4);
            shapeRenderer.end();

        }

    }

    @Override
    public void dispose() {
        sound.dispose();
        music.dispose();
    }

    public void chosingTheColour(int x, int y){
        if (this.redChoiceSquare.contains(x, y)) {
            this.setColorKey(0);
            this.texture = new Texture(Gdx.files.internal(format+"/square/square_red.png"));
        }

        if (this.blueChoiceSquare.contains(x, y)) {
            this.setColorKey(1);
            this.texture = new Texture(Gdx.files.internal(format+"/square/square_blue.png"));
        }

        if (this.yellowChoiceSquare.contains(x, y)) {
            this.setColorKey(2);
            this.texture = new Texture(Gdx.files.internal(format+"/square/square_yellow.png"));
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
                sound.play(Squarz.valueVolume*0.15f);
                Gdx.input.vibrate(Squarz.valueVibration*10);
                this.score.updateUser();
            }
        }
        for (int i = 0; i < player.getMiddleCounter(); i++) {
            player.getMiddle().get(i).move();
            if ( player.getMiddle().get(i).getPosition().y >= HEIGHT && player.getMiddle().get(i).getPosition().y < HEIGHT + this.settings.getStepX()){
                sound.play(Squarz.valueVolume*0.15f);
                Gdx.input.vibrate(Squarz.valueVibration*10);
                this.score.updateUser();
            }
        }
        for (int i = 0; i < player.getRightCounter(); i++) {
            player.getRight().get(i).move();
            if (player.getRight().get(i).getPosition().y >= HEIGHT && player.getRight().get(i).getPosition().y < HEIGHT + this.settings.getStepX()){
                sound.play(Squarz.valueVolume*0.15f);
                Gdx.input.vibrate(Squarz.valueVibration*10);
                this.score.updateUser();
            }
        }
    }

    public void movingAiSquare(){
        for (int i = 0; i < ai.getComputer().getLeftCounter(); i++) {
            ai.getComputer().getLeft().get(i).reverseMove();
            //dealing with the score
            if (ai.getComputer().getLeft().get(i).getPosition().y <= 0 && ai.getComputer().getLeft().get(i).getPosition().y > - this.settings.getStepX()){
                sound.play(Squarz.valueVolume*0.15f);
                Gdx.input.vibrate(Squarz.valueVibration*10);
                this.score.updateAi();
            }
        }
        for (int i = 0; i < ai.getComputer().getMiddleCounter(); i++) {
            ai.getComputer().getMiddle().get(i).reverseMove();
            //dealing with the score
            if (ai.getComputer().getMiddle().get(i).getPosition().y <= 0 && ai.getComputer().getMiddle().get(i).getPosition().y > - this.settings.getStepX()){
                sound.play(Squarz.valueVolume*0.15f);
                Gdx.input.vibrate(Squarz.valueVibration*10);
                this.score.updateAi();
            }
        }
        for (int i = 0; i < ai.getComputer().getRightCounter(); i++) {
            ai.getComputer().getRight().get(i).reverseMove();
            if (ai.getComputer().getRight().get(i).getPosition().y <= 0 && ai.getComputer().getRight().get(i).getPosition().y > - this.settings.getStepX()){
                sound.play(Squarz.valueVolume*0.15f);
                Gdx.input.vibrate(Squarz.valueVibration*10);
                this.score.updateAi();
            }
        }
    }

    public void drawingPlayerSquares(SpriteBatch sb){
        for (int i = 0; i < player.getLeftCounter(); i++) {
            sb.draw(player.getLeft().get(i).getTexture(),
                    player.getLeft().get(i).getPosition().x - player.getLeft().get(i).getTexture().getWidth()/2, player.getLeft().get(i).getPosition().y - player.getLeft().get(i).getTexture().getHeight()/2);
        }
        for (int i = 0; i < player.getMiddleCounter(); i++) {
            sb.draw(player.getMiddle().get(i).getTexture(),
                    player.getMiddle().get(i).getPosition().x  - player.getMiddle().get(i).getTexture().getWidth()/2, player.getMiddle().get(i).getPosition().y - player.getMiddle().get(i).getTexture().getHeight()/2);
        }
        for (int i = 0; i < player.getRightCounter(); i++) {
            sb.draw(player.getRight().get(i).getTexture(),
                    player.getRight().get(i).getPosition().x - player.getRight().get(i).getTexture().getWidth()/2, player.getRight().get(i).getPosition().y - player.getRight().get(i).getTexture().getHeight()/2);
        }
    }

    public void drawingAiSquares(SpriteBatch sb){
        for (int i = 0; i < ai.getComputer().getLeftCounter(); i++) {
            sb.draw(ai.getComputer().getLeft().get(i).getTexture(),
                    ai.getComputer().getLeft().get(i).getPosition().x - ai.getComputer().getLeft().get(i).getTexture().getWidth()/2, ai.getComputer().getLeft().get(i).getPosition().y - ai.getComputer().getLeft().get(i).getTexture().getHeight()/2);
        }
        for (int i = 0; i < ai.getComputer().getMiddleCounter(); i++) {
            sb.draw(ai.getComputer().getMiddle().get(i).getTexture(),
                    ai.getComputer().getMiddle().get(i).getPosition().x  - ai.getComputer().getMiddle().get(i).getTexture().getWidth()/2, ai.getComputer().getMiddle().get(i).getPosition().y - ai.getComputer().getMiddle().get(i).getTexture().getHeight()/2);
        }
        for (int i = 0; i < ai.getComputer().getRightCounter(); i++) {
            sb.draw(ai.getComputer().getRight().get(i).getTexture(),
                    ai.getComputer().getRight().get(i).getPosition().x - ai.getComputer().getRight().get(i).getTexture().getWidth()/2, ai.getComputer().getRight().get(i).getPosition().y - ai.getComputer().getRight().get(i).getTexture().getHeight()/2);
        }
    }

    public void drawScore(SpriteBatch sb){
        scoreAi.setText(Squarz.font, String.valueOf(score.getAiScore()));
        scoreUser.setText(Squarz.font, String.valueOf(score.getUserScore()));
        Squarz.font.draw(sb, scoreAi,redChoiceSquare.getPosX()+redChoiceSquare.getTexture().getWidth()/2-scoreAi.width/2 , HEIGHT * 45/64 - scoreAi.height/2);
        Squarz.font.draw(sb, scoreUser,redChoiceSquare.getPosX()+redChoiceSquare.getTexture().getWidth()/2-scoreUser.width/2 , HEIGHT * 39/64 - scoreUser.height/2);
    }

    public void drawCounter(SpriteBatch sb){
        //number of user squares lefting
        redLeft.setText(Squarz.font, String.valueOf(this.player.getSquareLimiter().getRedLefting()));
        blueLeft.setText(Squarz.font, String.valueOf(this.player.getSquareLimiter().getBlueLefting()));
        yellowLeft.setText(Squarz.font, String.valueOf(this.player.getSquareLimiter().getYellowLefting()));
        Squarz.font.draw(sb, redLeft, redChoiceSquare.getPosX()+redChoiceSquare.getTexture().getWidth()/2-redLeft.width/2, redChoiceSquare.getPosY()+redChoiceSquare.getTexture().getHeight()/2+redLeft.height/2);
        Squarz.font.draw(sb, blueLeft, blueChoiceSquare.getPosX()+blueChoiceSquare.getTexture().getWidth()/2-blueLeft.width/2, blueChoiceSquare.getPosY()+blueChoiceSquare.getTexture().getHeight()/2+blueLeft.height/2);
        Squarz.font.draw(sb, yellowLeft, yellowChoiceSquare.getPosX()+yellowChoiceSquare.getTexture().getWidth()/2-yellowLeft.width/2, yellowChoiceSquare.getPosY()+yellowChoiceSquare.getTexture().getHeight()/2+yellowLeft.height/2);

        //number of Ai squares lefting
        //Squarz.font.draw(sb, String.valueOf(this.ai.getComputer().getSquareLimiter().getRedLefting()), WIDTH * 1/4 + 10, HEIGHT*3/4);
        //Squarz.font.draw(sb, String.valueOf(this.ai.getComputer().getSquareLimiter().getBlueLefting()), WIDTH * 2/4 + 10, HEIGHT*3/4);
        //Squarz.font.draw(sb, String.valueOf(this.ai.getComputer().getSquareLimiter().getYellowLefting()), WIDTH * 3/4 + 10, HEIGHT*3/4);
    }

    public void drawTimeLeft(SpriteBatch sb){
        time.setText(Squarz.font, String.valueOf(this.countDown.getCountdownLabel().getText()));
        Squarz.font.draw(sb, String.valueOf(this.countDown.getCountdownLabel().getText()),redChoiceSquare.getPosX()+redChoiceSquare.getTexture().getWidth()/2-time.width/2 , HEIGHT*28/32 - time.height/2);
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