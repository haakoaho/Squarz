package com.mygdx.game.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.model.players.AIPlayer;
import com.mygdx.game.model.other.Collision;
import com.mygdx.game.model.other.Icon;
import com.mygdx.game.model.other.PauseScreen;
import com.mygdx.game.model.players.Player;
import com.mygdx.game.model.other.Score;
import com.mygdx.game.Squarz;
import com.mygdx.game.gameStateManager.State;
import com.mygdx.game.gameStateManager.GameStateManager;
import com.mygdx.game.model.ai_settings.PreferencesSettings;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;


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
    private final GlyphLayout readyGlyph;
    private final GlyphLayout redLeft;
    private final GlyphLayout yellowLeft;
    private final GlyphLayout blueLeft;
    private final GlyphLayout scoreUser;
    private final GlyphLayout scoreAi;
    private final GlyphLayout time;

    private final ShapeRenderer shapeRenderer;

    private PreferencesSettings settings;
    private final ICountdownDuration countDown;

    private AIPlayer ai;
    private Player player;

    private final Collision collision;

    private Icon redChoiceSquare;
    private final Icon blueChoiceSquare;
    private final Icon yellowChoiceSquare;
    private final Icon pause;
    private final Icon bonusChoiceSquare1;
    private final Icon bonusChoiceSquare2;
    private Texture texture;
    private Integer colorKey;

    private Score score;

    private Boolean ready = false;

    private Boolean pauseFlag = false;
    private Boolean pauseSettings = false;
    private final PauseScreen pauseScreen;
    private final Integer temporarySpeed;
    private float exTime;
    private Boolean firstIsUsed = false;
    private Boolean secondIsUsed = false;


    public PlayModeAi(GameStateManager gsm, PreferencesSettings settings, ICountdownDuration countDown) {
        super(gsm);

        this.settings = settings;
        this.countDown = countDown;

        this.player = new Player(this.settings,this.countDown);
        this.ai = new AIPlayer(this.settings,this.countDown, this.player);

        this.settings.getBonus1().update(this.getPlayer(), this.getAi());
        this.settings.getBonus2().update(this.getPlayer(), this.getAi());

        this.score = Score.getInstance();
        this.shapeRenderer = new ShapeRenderer();

        this.redLeft = new GlyphLayout(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getRedLeft()));
        this.yellowLeft = new GlyphLayout(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getYellowLeft()));
        this.blueLeft = new GlyphLayout(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getBlueLeft()));
        this.scoreAi = new GlyphLayout(Squarz.font, String.valueOf(score.getOpponentScore()));
        this.scoreUser = new GlyphLayout(Squarz.font, String.valueOf(score.getUserScore()));
        this.time = new GlyphLayout(Squarz.font, String.valueOf(this.countDown.getWorldTimer()));

        this.texture = new Texture(Gdx.files.internal(format + "/square/square_red.png"));

        this.redChoiceSquare = new Icon(new Texture(Gdx.files.internal(format + "/square/square_red_selected.png"))
            , WIDTH / 16, HEIGHT / 2 - this.texture.getHeight() * 3 / 2);
        this.blueChoiceSquare = new Icon(new Texture(Gdx.files.internal(format + "/square/square_blue.png"))
            , WIDTH / 16, HEIGHT / 2 - this.texture.getHeight() * 11 / 4);
        this.yellowChoiceSquare = new Icon(new Texture(Gdx.files.internal(format + "/square/square_yellow.png"))
            , WIDTH / 16, HEIGHT / 2 - this.texture.getHeight() * 4);

        this.bonusChoiceSquare1 = new Icon(new Texture(Gdx.files.internal(format + "/bonuses/none.png"))
                , WIDTH / 16, HEIGHT / 2 - this.texture.getHeight() * 21 / 4);
        this.bonusChoiceSquare1.setTexture(this.settings.getBonus1().getBonustexture(this.settings.getBonus1().getBonusKey()));

        this.bonusChoiceSquare2 = new Icon(new Texture(Gdx.files.internal(format + "/bonuses/none.png"))
                , WIDTH / 16, HEIGHT / 2 - this.texture.getHeight() * 26 / 4);
        this.bonusChoiceSquare2.setTexture(this.settings.getBonus2().getBonustexture(this.settings.getBonus2().getBonusKey()));


        this.colorKey = 0;

    //Pause mode
        this.pause = new Icon(new Texture(Gdx.files.internal(format + "/pause.png")),0,0);
        this.pause.setPosX(redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth()/2 - pause.getTexture().getWidth()/2);
        this.pause.setPosY(HEIGHT * 29 / 32 - pause.getTexture().getHeight()/2);
        this.pauseScreen = new PauseScreen();

        this.collision = new Collision(score);

        this.temporarySpeed = this.settings.getStepX();

    readyGlyph = new GlyphLayout(Squarz.font, "READY ?");

    music = Gdx.audio.newMusic(Gdx.files.internal("sound/reset.mp3"));

        music.setLooping(true);
        music.setVolume(Squarz.valueVolume * 0.15f);


    sound = Gdx.audio.newSound(Gdx.files.internal("sound/goal.mp3"));

        exTime = countDown.getWorldTimer();
}
    @Override
    public void handleInput() {

        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();

            if (!ready) {
                ready = true;
                music.play();
            }
            else {
                if (pause.contains(x, y)) {
                    pauseFlag = true;
                }
                if (pauseFlag && !pauseSettings) {
                    freeze();
                    music.pause();
                    if (pauseScreen.getResume().contains(x, y)) {
                        music.play();
                        defreeze();
                    }
                    if (pauseScreen.getBack().contains(x, y)){
                        defreeze();
                        gsm.set(new EndMode(gsm, settings, score, countDown));
                    }
                    if (pauseScreen.getQuickSetting().contains(x, y)){
                        //let freeze while changing settings then return to pause mode with new settings
                        pauseSettings = true;
                    }
                }
                else if (pauseSettings){
                    if(pauseScreen.getDeleteS().contains(x, y)){
                        dec(0);
                        music.setVolume(Squarz.valueVolume * 0.15f);
                    }
                    if(pauseScreen.getAddS().contains(x, y)){
                        inc(0);
                        music.setVolume(Squarz.valueVolume * 0.15f);
                    }
                    if (pauseScreen.getDeleteV().contains(x, y)) {
                        dec(1);
                    }
                    if (pauseScreen.getAddV().contains(x, y)) {
                        inc(1);
                    }
                    if (pauseScreen.getBackToPause().contains(x, y)){
                        pauseSettings = false;
                    }
                }
                else {
                    //Colour choice button
                    choosingTheColour(x, y);

                    //Implementation for the launcher of each row
                    if (!this.player.getSquareLimiter().isOver(colorKey) && isAllowedToPlay(exTime)) {
                        creatingANewSquare(x);
                    }
                }
            }
        }
    }

    private boolean isAllowedToPlay(float exTime){
        boolean allowed;
        float timeRef = countDown.getWorldTimer()-countDown.getTimeCount();
        allowed = exTime - timeRef > 0.5;
        if(allowed){this.exTime = timeRef;}
        return  allowed;
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
                gsm.set(new EndMode(gsm, settings, score, countDown));
            }

            movingPlayerSquare(dt);
            movingAiSquare(dt);

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
            sb.draw(bonusChoiceSquare1.getTexture(), bonusChoiceSquare1.getPosX(), bonusChoiceSquare1.getPosY());
            sb.draw(bonusChoiceSquare2.getTexture(), bonusChoiceSquare2.getPosX(), bonusChoiceSquare2.getPosY());
            sb.draw(pause.getTexture(), pause.getPosX(), pause.getPosY());

            drawingSquares(sb, player);
            drawingSquares(sb, ai);

            drawTimeLeft(sb);
            drawScore(sb);
            drawCounter(sb);

            if (pauseFlag) {
                pauseScreen.drawPause(sb);
                // drawScorePause(sb);
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


    private void freeze() {
        this.settings.setStepX(0);
    }

    private void defreeze() {
        this.settings.setStepX(this.temporarySpeed);
        this.pauseFlag = false;
        pauseSettings = false;
    }


    private void choosingTheColour(int x, int y) {
        if (this.redChoiceSquare.contains(x, y)) {
            this.setColorKey(0);
            setPLayerTexture(this.getColorKey());
        }

        if (this.blueChoiceSquare.contains(x, y)) {
            this.setColorKey(1);
            setPLayerTexture(this.getColorKey());
        }

        if (this.yellowChoiceSquare.contains(x, y)) {
            this.setColorKey(2);
            setPLayerTexture(this.getColorKey());
        }

        if (this.bonusChoiceSquare1.contains(x, y) && !firstIsUsed) {

            this.settings.getBonus1().update(this.getPlayer(), this.getAi());
            if(this.settings.getBonus1().getBonusKey() == 1){
                this.setColorKey(this.settings.getBonus1().punisherEffect());
                this.setPLayerTexture(4);
            }

            if(this.settings.getBonus1().getBonusKey() == 2){this.settings.getBonus1().nurseEffectPlayer();}
            if(this.settings.getBonus1().getBonusKey() == 3){this.settings.getBonus1().mrPropreEffect();}

            //after utilisation
            this.bonusChoiceSquare1.setTexture(new Texture( Gdx.files.internal(format+"/bonuses/used.png")));
            this.setFirstIsUsed(true);
        }

        if (this.bonusChoiceSquare2.contains(x, y) && !secondIsUsed) {

            this.settings.getBonus2().update(this.getPlayer(), this.getAi());
            if(this.settings.getBonus2().getBonusKey() == 1){
                this.setColorKey(this.settings.getBonus2().punisherEffect());
                this.setPLayerTexture(4);
            }
            if(this.settings.getBonus2().getBonusKey() == 2){this.settings.getBonus2().nurseEffectPlayer();}
            if(this.settings.getBonus2().getBonusKey() == 3){this.settings.getBonus2().mrPropreEffect();}

            this.bonusChoiceSquare2.setTexture(new Texture( Gdx.files.internal(format+"/bonuses/used.png")));
            this.setSecondIsUsed(true);
        }
    }
    private void creatingANewSquare(int x) {
        Integer tempColKey = this.getColumn(x);
        if(tempColKey != -1) {
            player.increment(texture, tempColKey, colorKey);
        }
    }

    private Integer getColumn(int x){
        int tempColKey = -1;
        if (x > WIDTH / 4 && x < WIDTH / 2) {
            tempColKey = 0;
        }
        if (x > WIDTH / 2 && x < WIDTH * 3 / 4) {
            tempColKey = 1;
        }
        if (x > WIDTH * 3 / 4) {
            tempColKey = 2;
        }
        return tempColKey;
    }

    private void movingPlayerSquare(float dt){
        for (Integer columnKey = 0; columnKey < 3; columnKey++) {
           if (!player.getMap(columnKey).isEmpty()) {
               for (int i = player.getFirstSquareKey(columnKey); i < player.getCounter(columnKey); i++) {
                   player.getMap(columnKey).get(i).move(dt);
                   //dealing with the score
                   if (player.getMap(columnKey).get(player.getFirstSquareKey(columnKey)).getPosition().y >= HEIGHT
                           && player.getMap(columnKey).get(player.getFirstSquareKey(columnKey)).getPosition().y < HEIGHT + this.settings.getStepX() * dt) {
                       sound.play(Squarz.valueVolume * 0.15f);
                       Gdx.input.vibrate(Squarz.valueVibration * 50);
                    }
                }

            }
        }
    }
    private void movingAiSquare(float dt) {
    for(int columnKey=0; columnKey<3; columnKey++) {
        if(!ai.getMap(columnKey).isEmpty()) {
            for (int i = ai.getFirstSquareKey(columnKey); i < ai.getCounter(columnKey); i++) {
                ai.getMap(columnKey).get(i).reverseMove(dt);
                //dealing with the score
                if (ai.getMap(columnKey).get(i).getPosition().y <= 0 && ai.getMap(columnKey).get(i).getPosition().y > -this.settings.getStepX()*dt) {
                    sound.play(Squarz.valueVolume * 0.15f);
                    Gdx.input.vibrate(Squarz.valueVibration * 50);
                }
            }
        }
    }
}


    private void drawingSquares(SpriteBatch sb, Player p){
        for(int columnKey = 0; columnKey < 3; columnKey ++) {
            if (!p.getMap(columnKey).isEmpty()) {
                for (int i = p.getFirstSquareKey(columnKey); i < p.getCounter(columnKey); i++) {
                    sb.draw(p.getMap(columnKey).get(i).getTexture(),
                            p.getMap(columnKey).get(i).getPosition().x - p.getMap(columnKey).get(i).getTexture().getWidth() / 2, p.getMap(columnKey).get(i).getPosition().y - p.getMap(columnKey).get(i).getTexture().getHeight() / 2);
                }
            }
        }
    }
    private void drawScore(SpriteBatch sb) {
        scoreAi.setText(Squarz.font, String.valueOf(score.getOpponentScore()));
        scoreUser.setText(Squarz.font, String.valueOf(score.getUserScore()));
        Squarz.font.draw(sb, scoreAi, redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - scoreAi.width / 2, HEIGHT * 45 / 64 - scoreAi.height / 2);
        Squarz.font.draw(sb, scoreUser, redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - scoreUser.width / 2, HEIGHT * 39 / 64 - scoreUser.height / 2);
    }
    /*
    public void drawScorePause(SpriteBatch sb){
        scoreAi.setText(Squarz.font, String.valueOf(score.getOpponentScore()));
        scoreUser.setText(Squarz.font, String.valueOf(score.getUserScore()));
        Squarz.font.draw(sb, scoreAi, WIDTH/2 + pauseScreen.getTexture().getWidth()*1/8 - scoreAi.width/2, HEIGHT/2 + 2*scoreAi.height);
        Squarz.font.draw(sb, scoreUser, WIDTH/2 - pauseScreen.getTexture().getWidth()*1/8 - scoreUser.width/2, HEIGHT/2 + 2*scoreUser.height);
     }
     */
    private void drawCounter(SpriteBatch sb) {
        //number of user squares left
        redLeft.setText(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getRedLeft()));
        blueLeft.setText(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getBlueLeft()));
        yellowLeft.setText(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getYellowLeft()));
        Squarz.font2.draw(sb, redLeft, redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - redLeft.width / 2, redChoiceSquare.getPosY() + redChoiceSquare.getTexture().getHeight() / 2 + redLeft.height / 2);
        Squarz.font2.draw(sb, blueLeft, blueChoiceSquare.getPosX() + blueChoiceSquare.getTexture().getWidth() / 2 - blueLeft.width / 2, blueChoiceSquare.getPosY() + blueChoiceSquare.getTexture().getHeight() / 2 + blueLeft.height / 2);
        Squarz.font2.draw(sb, yellowLeft, yellowChoiceSquare.getPosX() + yellowChoiceSquare.getTexture().getWidth() / 2 - yellowLeft.width / 2, yellowChoiceSquare.getPosY() + yellowChoiceSquare.getTexture().getHeight() / 2 + yellowLeft.height / 2);
    }
    private void drawTimeLeft(SpriteBatch sb) {
        time.setText(Squarz.font, String.valueOf(this.countDown.getWorldTimer()));
        Squarz.font.draw(sb, String.valueOf(this.countDown.getWorldTimer()), redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - time.width / 2, HEIGHT * 27 / 32 - time.height / 2);
    }

    private void setPLayerTexture(int colorKey){
        if (colorKey == 0){
            this.texture = new Texture(Gdx.files.internal(format + "/square/square_red.png"));
            this.redChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_red_selected.png")));
            this.blueChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_blue.png")));
            this.yellowChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_yellow.png")));
        } else if(colorKey == 1){
            this.texture = new Texture(Gdx.files.internal(format + "/square/square_blue.png"));
            this.redChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_red.png")));
            this.blueChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_blue_selected.png")));
            this.yellowChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_yellow.png")));
        } else if(colorKey == 2){
            this.texture = new Texture(Gdx.files.internal(format + "/square/square_yellow.png"));
            this.redChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_red.png")));
            this.blueChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_blue.png")));
            this.yellowChoiceSquare.setTexture(new Texture(Gdx.files.internal(format + "/square/square_yellow_selected.png")));
        } else if(colorKey == 4){
            this.texture = new Texture(Gdx.files.internal(format + "/bonuses/punisher.png"));
        }
    }


    private Integer getColorKey() {
        return colorKey;
    }

    private void setColorKey(Integer colorKey) {
        this.colorKey = colorKey;
    }

    private AIPlayer getAi() {
        return ai;
    }

    private Player getPlayer() {
        return player;
    }

    private void setFirstIsUsed(Boolean firstIsUsed) {
        this.firstIsUsed = firstIsUsed;
    }

    private void setSecondIsUsed(Boolean secondIsUsed) {
        this.secondIsUsed = secondIsUsed;
    }

    private void inc(int i) {
        if(i==0) { //volume
            if (Squarz.valueVolume < 10) {
                Squarz.valueVolume++;
            }
        }
        if(i==1) { //vibration
            if (Squarz.valueVibration < 10) {
                Squarz.valueVibration++;
            }
        }

    }

    private void dec(int i) {
        if(i==0) { //volume
            if (Squarz.valueVolume > 0) {
                Squarz.valueVolume--;
            }
        }
        if(i==1) { //vibration
            if (Squarz.valueVibration > 0) {
                Squarz.valueVibration--;
            }
        }
    }
}