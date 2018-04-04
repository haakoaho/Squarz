package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.AIPlayer;
import com.mygdx.game.model.Collision;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;


import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.format;
import static com.mygdx.game.Squarz.valueVibration;
import static com.mygdx.game.Squarz.valueVolume;

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

    private Square choiceSquare;

    private Icon redChoiceSquare, blueChoiceSquare, yellowChoiceSquare, pause;
    private Texture texture;
    private Integer colorKey;

    private Score score;

    private Boolean ready = false;

    private Boolean pauseFlag = false;
    private Boolean pauseSettings = false;
    private PauseScreen pauseScreen;
    private Integer temporarySpeed;


    public PlayModeAi(GameStateManager gsm, PreferencesSettings settings, CountDown countDown) {
        super(gsm);

        this.settings = settings;
        this.countDown = countDown;

        this.player = new Player(settings,countDown);
        this.ai = new AIPlayer(settings, countDown);
        this.ai.setSet(settings);

        this.score = new Score();
        this.shapeRenderer = new ShapeRenderer();

        choiceSquare = new Square(settings);
        choiceSquare.setPosition(new Vector2(WIDTH/16, HEIGHT/5));

        this.redLeft = new GlyphLayout(Squarz.font, String.valueOf(this.player.getSquareLimiter().getRedLefting()));
        this.yellowLeft = new GlyphLayout(Squarz.font, String.valueOf(this.player.getSquareLimiter().getYellowLefting()));
        this.blueLeft = new GlyphLayout(Squarz.font, String.valueOf(this.player.getSquareLimiter().getBlueLefting()));
        this.scoreAi = new GlyphLayout(Squarz.font, String.valueOf(score.getAiScore()));
        this.scoreUser = new GlyphLayout(Squarz.font, String.valueOf(score.getUserScore()));
        this.time = new GlyphLayout(Squarz.font, String.valueOf(this.countDown.getWorldTimer()));


        this.texture = new Texture(Gdx.files.internal(format + "/square/square_red.png"));


        this.redChoiceSquare = new Icon(new Texture(Gdx.files.internal(format + "/square/square_red_selected.png"))
                , WIDTH * 1 / 16, HEIGHT / 2 - this.texture.getHeight() * 3 / 2);
        this.blueChoiceSquare = new Icon(new Texture(Gdx.files.internal(format + "/square/square_blue.png"))
                , WIDTH * 1 / 16, HEIGHT / 2 - this.texture.getHeight() * 11 / 4);
        this.yellowChoiceSquare = new Icon(new Texture(Gdx.files.internal(format + "/square/square_yellow.png"))
                , WIDTH * 1 / 16, HEIGHT / 2 - this.texture.getHeight() * 4);
        this.pause = new Icon(new Texture(Gdx.files.internal(format + "/pause.png"))
                , WIDTH * 1 / 16, HEIGHT * 15 / 16 - this.texture.getHeight() / 2);

        this.colorKey = 0;

        //Pause mode
        this.pause = new Icon(new Texture(Gdx.files.internal(format + "/pause.png"))
                , WIDTH * 1 / 16, HEIGHT * 15 / 16 - this.texture.getHeight() / 2);
        this.pauseScreen = new PauseScreen();

        this.collision = new Collision();

        this.temporarySpeed = this.settings.getStepX();

        readyGlyph = new GlyphLayout(Squarz.font, "READY ?");

        music = Gdx.audio.newMusic(Gdx.files.internal("sound/reset.mp3"));

        music.setLooping(true);
        music.setVolume(Squarz.valueVolume * 0.15f);
        music.play();

        sound = Gdx.audio.newSound(Gdx.files.internal("sound/goal.mp3"));
    }

    @Override
    public void handleInput() {

        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();

            if (!ready) {
                ready = true;
            }
            else {
                if (pause.contains(x, y)) {
                    pauseFlag = true;
                }
                if (pauseFlag && !pauseSettings) {
                    freeze();
                    if (pauseScreen.getResume().contains(x, y)) {
                        defreeze();
                    }
                    if (pauseScreen.getBack().contains(x, y)){
                        defreeze();
                        gsm.set(new EndModeAI(gsm, settings, score, countDown));
                    }
                    if (pauseScreen.getQuickSetting().contains(x, y)){
                        //let freeze while changing settings then return to pause mode with new settings
                        pauseSettings = true;
                    }
                }
                if (pauseSettings){
                    if(pauseScreen.getDeleteS().contains(x, y)){
                        valueVolume --;
                        music.setVolume(Squarz.valueVolume * 0.15f);
                    }
                    if(pauseScreen.getAddS().contains(x, y)){
                        valueVolume ++;
                        music.setVolume(Squarz.valueVolume * 0.15f);
                    }
                    if (pauseScreen.getDeleteV().contains(x, y)) {
                        valueVibration --;
                    }
                    if (pauseScreen.getAddV().contains(x, y)) {
                        valueVibration ++;
                    }
                    if (pauseScreen.getBackToPause().contains(x, y)){
                        pauseSettings = false;
                    }
                }
                else {
                    //Colour choice button
                    chosingTheColour(x, y);

                    //go to end mode, test only
                    if (y > HEIGHT * 3 / 4 && x > HEIGHT / 2) {
                        music.stop();
                        sound.stop();
                        gsm.set(new EndModeAI(gsm, settings, score, countDown));
                    }

                    //Implementation for the launcher of each row
                    if (!this.player.getSquareLimiter().isOver(colorKey)) {
                        creatingANewSquare(x);
                    }
                }
            }
        }
    }

    private Integer getColumnKey(){
        if (Gdx.input.getX() > WIDTH / 4 && Gdx.input.getX() < WIDTH / 2) {
           return 0;
        }
        if (Gdx.input.getX() > WIDTH / 2 && Gdx.input.getX() < WIDTH * 3 / 4) {
           return 1;
        }
        if (Gdx.input.getX() > WIDTH * 3 / 4) {
          return 2;

        }
        return 3; // User didn't click on a row
    }

    @Override
    public void update(float dt) {
        handleInput();

        if (ready) {
            if(!pauseFlag){
                //updating the countdown
                this.countDown.update(dt);
                //random sending by the AI
                ai.send(this.countDown);
            }

            if (this.countDown.isTimeUp()) {
                music.stop();
                gsm.set(new EndModeAI(gsm, settings, score, countDown));
            }

            movingPlayerSquare();
            movingAiSquare();

            collision.collision(this.player, this.ai);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!ready) {
            sb.begin();
            Squarz.font.draw(sb, readyGlyph,
                    (float) (WIDTH / 2 - readyGlyph.width / 2.), HEIGHT / 2 - readyGlyph.height / 2);
            sb.end();
        } else {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.line(WIDTH / 4, 0, WIDTH / 4, HEIGHT);
            shapeRenderer.line(WIDTH / 4 - 10, 0, WIDTH / 4 - 10, HEIGHT);
            shapeRenderer.line(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
            shapeRenderer.line(3 * WIDTH / 4, 0, 3 * WIDTH / 4, HEIGHT);
            shapeRenderer.line(0, HEIGHT / 2, WIDTH / 4 - 10, HEIGHT / 2);
            shapeRenderer.line(0, 3 * HEIGHT / 4, WIDTH / 4 - 10, 3 * HEIGHT / 4);
            shapeRenderer.end();

            sb.begin();
            sb.draw(redChoiceSquare.getTexture(), redChoiceSquare.getPosX(), redChoiceSquare.getPosY());
            sb.draw(blueChoiceSquare.getTexture(), blueChoiceSquare.getPosX(), blueChoiceSquare.getPosY());
            sb.draw(yellowChoiceSquare.getTexture(), yellowChoiceSquare.getPosX(), yellowChoiceSquare.getPosY());
            sb.draw(pause.getTexture(), pause.getPosX(), pause.getPosY());

            drawingSquares(sb, player);
            drawingSquares(sb, ai);

            drawTimeLeft(sb);
            drawScore(sb);
            drawCounter(sb);

            if (pauseFlag) {
                pauseScreen.drawPause(sb);
                drawScorePause(sb);
            }
            if (pauseSettings) {
                pauseScreen.drawPauseSetting(sb);
            }

            sb.end();


        }
    }

    @Override
    public void dispose() {
        sound.dispose();
        music.dispose();
    }


    public void freeze() {
        this.settings.setStepX(0);
    }

    public void defreeze() {
        this.settings.setStepX(this.temporarySpeed);
        this.pauseFlag = false;
        pauseSettings = false;
    }


    public void chosingTheColour(int x, int y) {
        if (this.redChoiceSquare.contains(x, y)) {
            this.setColorKey(0);
            this.texture = new Texture(Gdx.files.internal(format + "/square/square_red.png"));
            this.redChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_red_selected.png")));
            this.yellowChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_yellow.png")));
            this.blueChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_blue.png")));
        }

        if (this.blueChoiceSquare.contains(x, y)) {
            this.setColorKey(1);
            this.texture = new Texture(Gdx.files.internal(format + "/square/square_blue.png"));
            this.redChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_red.png")));
            this.yellowChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_yellow.png")));
            this.blueChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_blue_selected.png")));
        }

        if (this.yellowChoiceSquare.contains(x, y)) {
            this.setColorKey(2);
            this.texture = new Texture(Gdx.files.internal(format + "/square/square_yellow.png"));
            this.redChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_red.png")));
            this.yellowChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_yellow_selected.png")));
            this.blueChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_blue.png")));
        }
    }

    public void creatingANewSquare(int x) {
        if (x > WIDTH / 4 && x < WIDTH / 2) {
            player.increment(texture, 0, colorKey);
        }
        if (x > WIDTH / 2 && x < WIDTH * 3 / 4) {
            player.increment(texture, 1, colorKey);
        }
        if (x > WIDTH * 3 / 4) {
            player.increment(texture, 2, colorKey);
        }
    }


    /*public void movingSquare(Player p, boolean goingUp) {
        for (int rowKey = 0; rowKey < 3; rowKey++) {
            for (int i = p.getFirstSquareKey(rowKey); i < p.getCounter(rowKey); i++) {
                if(goingUp) {
                    p.getMap(rowKey).get(i).move();
                }
                else{
                    p.getMap(rowKey).get(i).reverseMove();
                }
                //dealing with the score
                /*if (p.getMap(rowKey).get(i).getPosition().y >= HEIGHT && p.getMap(rowKey).get(i).getPosition().y < HEIGHT + this.settings.getStepX()) {
                    sound.play(Squarz.valueVolume * 0.1f);
                    Gdx.input.vibrate(Squarz.valueVibration * 100);
                    if(goingUp){
                        this.score.updateUser();
                    }
                    if(!goingUp){
                        this.score.updateAi();
                    }
                }
            }
        }
    }
*/


public void movingPlayerSquare(){
    if(!player.getLeft().isEmpty()) {
        for (int i = player.getFirstLeftSquaresKey(); i < player.getLeftCounter(); i++) {
            player.getLeft().get(i).move();
            //dealing with the score
            if (player.getLeft().get(i).getPosition().y >= HEIGHT && player.getLeft().get(i).getPosition().y < HEIGHT + this.settings.getStepX()) {
                sound.play(Squarz.valueVolume * 0.15f);
                Gdx.input.vibrate(Squarz.valueVibration * 100);
                this.score.updateUser();
            }
        }
    }
    if(!player.getMiddle().isEmpty()) {
        for (int i = player.getFirstMiddleSquaresKey(); i < player.getMiddleCounter(); i++) {
            player.getMiddle().get(i).move();
            if (player.getMiddle().get(i).getPosition().y >= HEIGHT && player.getMiddle().get(i).getPosition().y < HEIGHT + this.settings.getStepX()) {
                sound.play(Squarz.valueVolume * 0.15f);
                Gdx.input.vibrate(Squarz.valueVibration * 100);
                this.score.updateUser();
            }
        }
    }
    if(!player.getRight().isEmpty()) {
        for (int i = player.getFirstRightSquaresKey(); i < player.getRightCounter(); i++) {
            player.getRight().get(i).move();
            if (player.getRight().get(i).getPosition().y >= HEIGHT && player.getRight().get(i).getPosition().y < HEIGHT + this.settings.getStepX()) {
                sound.play(Squarz.valueVolume * 0.15f);
                Gdx.input.vibrate(Squarz.valueVibration * 100);
                this.score.updateUser();
            }
        }
    }
}

public void movingAiSquare() {
    if(!ai.getLeft().isEmpty()) {
        for (int i = ai.getFirstLeftSquaresKey(); i < ai.getLeftCounter(); i++) {
            ai.getLeft().get(i).reverseMove();
            //dealing with the score
            if (ai.getLeft().get(i).getPosition().y <= 0 && ai.getLeft().get(i).getPosition().y > -this.settings.getStepX()) {
                sound.play(Squarz.valueVolume * 0.15f);
                Gdx.input.vibrate(Squarz.valueVibration * 100);
                this.score.updateAi();
            }
        }
    }
    if(!ai.getMiddle().isEmpty()) {
        for (int i = ai.getFirstMiddleSquaresKey(); i < ai.getMiddleCounter(); i++) {
            ai.getMiddle().get(i).reverseMove();
            //dealing with the score
            if (ai.getMiddle().get(i).getPosition().y <= 0 && ai.getMiddle().get(i).getPosition().y > -this.settings.getStepX()) {
                sound.play(Squarz.valueVolume * 0.15f);
                Gdx.input.vibrate(Squarz.valueVibration * 100);
                this.score.updateAi();
            }
        }
    }
    if(!ai.getRight().isEmpty()) {
        for (int i = ai.getFirstRightSquaresKey(); i < ai.getRightCounter(); i++) {
            ai.getRight().get(i).reverseMove();
            if (ai.getRight().get(i).getPosition().y <= 0 && ai.getRight().get(i).getPosition().y > -this.settings.getStepX()) {
                sound.play(Squarz.valueVolume * 0.15f);
                Gdx.input.vibrate(Squarz.valueVibration * 100);
                this.score.updateAi();
            }
        }
    }
}


    public void drawingSquares(SpriteBatch sb, Player p){
        for(int rowKey = 0; rowKey < 3; rowKey ++) {
            if (!p.getMap(rowKey).isEmpty()) {
                for (int i = p.getFirstSquareKey(rowKey); i < p.getCounter(rowKey); i++) {
                    sb.draw(p.getMap(rowKey).get(i).getTexture(),
                            p.getMap(rowKey).get(i).getPosition().x - p.getMap(rowKey).get(i).getTexture().getWidth() / 2, p.getMap(rowKey).get(i).getPosition().y - p.getMap(rowKey).get(i).getTexture().getHeight() / 2);
                }
            }
        }
    }

    public void drawScore(SpriteBatch sb) {
        scoreAi.setText(Squarz.font, String.valueOf(score.getAiScore()));
        scoreUser.setText(Squarz.font, String.valueOf(score.getUserScore()));
        Squarz.font.draw(sb, scoreAi, redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - scoreAi.width / 2, HEIGHT * 45 / 64 - scoreAi.height / 2);
        Squarz.font.draw(sb, scoreUser, redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - scoreUser.width / 2, HEIGHT * 39 / 64 - scoreUser.height / 2);
    }
    public void drawScorePause(SpriteBatch sb){
        scoreAi.setText(Squarz.font, String.valueOf(score.getAiScore()));
        scoreUser.setText(Squarz.font, String.valueOf(score.getUserScore()));
        Squarz.font.draw(sb, scoreAi, WIDTH/2 + pauseScreen.getTexture().getWidth()*1/8 - scoreAi.width/2, HEIGHT/2 + 2*scoreAi.height);
        Squarz.font.draw(sb, scoreUser, WIDTH/2 - pauseScreen.getTexture().getWidth()*1/8 - scoreUser.width/2, HEIGHT/2 + 2*scoreUser.height);
     }
    public void drawCounter(SpriteBatch sb) {
        //number of user squares lefting
        redLeft.setText(Squarz.font, String.valueOf(this.player.getSquareLimiter().getRedLefting()));
        blueLeft.setText(Squarz.font, String.valueOf(this.player.getSquareLimiter().getBlueLefting()));
        yellowLeft.setText(Squarz.font, String.valueOf(this.player.getSquareLimiter().getYellowLefting()));
        Squarz.font.draw(sb, redLeft, redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - redLeft.width / 2, redChoiceSquare.getPosY() + redChoiceSquare.getTexture().getHeight() / 2 + redLeft.height / 2);
        Squarz.font.draw(sb, blueLeft, blueChoiceSquare.getPosX() + blueChoiceSquare.getTexture().getWidth() / 2 - blueLeft.width / 2, blueChoiceSquare.getPosY() + blueChoiceSquare.getTexture().getHeight() / 2 + blueLeft.height / 2);
        Squarz.font.draw(sb, yellowLeft, yellowChoiceSquare.getPosX() + yellowChoiceSquare.getTexture().getWidth() / 2 - yellowLeft.width / 2, yellowChoiceSquare.getPosY() + yellowChoiceSquare.getTexture().getHeight() / 2 + yellowLeft.height / 2);
    }
    public void drawTimeLeft(SpriteBatch sb) {
        time.setText(Squarz.font, String.valueOf(this.countDown.getWorldTimer()));
        Squarz.font.draw(sb, String.valueOf(this.countDown.getWorldTimer()), redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - time.width / 2, HEIGHT * 28 / 32 - time.height / 2);
    }



    
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
}