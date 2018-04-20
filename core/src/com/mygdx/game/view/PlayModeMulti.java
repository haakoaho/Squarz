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
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.VeryLongCountdown;
import com.mygdx.game.model.Collision;
import com.mygdx.game.model.Countdown;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;

import java.util.ArrayList;
import java.util.Queue;


import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.format;
import static com.mygdx.game.Squarz.valueVolume;

public class PlayModeMulti extends State {
    private Music music;
    private Sound sound;

    private GlyphLayout redLeft, yellowLeft, blueLeft, scoreUser, scoreOpponent, time;
    private ShapeRenderer shapeRenderer;

    private PreferencesSettings settings;
    private ICountdownDuration countDown;

    private AIPlayer opponent;
    private Player player;

    private Collision collision;

    private Square choiceSquare;

    private Icon redChoiceSquare, blueChoiceSquare, yellowChoiceSquare, mute, bonusChoiceSquare1, bonusChoiceSquare2;
    private Texture texture;
    private Integer colorKey, columnKey;

    private Score score;

    private float exTime;
    private Boolean varMute, firstIsUsed = false, secondIsUsed = false;

<<<<<<< HEAD
    public PlayModeMulti(GameStateManager gsm, PreferencesSettings settings, CountDown countDown) {
=======
    public PlayModeMulti(GameStateManager gsm, PreferencesSettings settings, ICountdownDuration countDown) {

>>>>>>> 2cd1869f471b46c99867f7d31763bde5d5bd28e6
        super(gsm);

        this.settings = settings;
        this.countDown = countDown;

        player = new Player(settings, countDown);
        opponent = new AIPlayer(settings, countDown);

        this.settings.getBonus1().update(this.getPlayer(), this.getAi());
        this.settings.getBonus2().update(this.getPlayer(), this.getAi());

        choiceSquare = new Square(settings);
        choiceSquare.setPosition(new Vector2(WIDTH * 1 / 16, HEIGHT * 1 / 5));

        if (valueVolume == 0) {
            this.mute = new Icon(new Texture(Gdx.files.internal(format + "/mute.png")), 0, 0);
            this.varMute = true;
        } else {
            this.mute = new Icon(new Texture(Gdx.files.internal(format + "/settings/sound.png")), 0, 0);
            this.varMute = false;
        }


        this.colorKey = 0;
        score = new Score();
        countDown = new VeryLongCountdown();
        collision = new Collision(score);

        //all the texture
        this.shapeRenderer = new ShapeRenderer();

        this.texture = new Texture(Gdx.files.internal(format + "/square/square_red.png"));

        this.redChoiceSquare = new Icon(new Texture(Gdx.files.internal(format + "/square/square_red_selected.png"))
                , WIDTH * 1 / 16, HEIGHT / 2 - this.texture.getHeight() * 3 / 2);
        this.blueChoiceSquare = new Icon(new Texture(Gdx.files.internal(format + "/square/square_blue.png"))
                , WIDTH * 1 / 16, HEIGHT / 2 - this.texture.getHeight() * 11 / 4);
        this.yellowChoiceSquare = new Icon(new Texture(Gdx.files.internal(format + "/square/square_yellow.png"))
                , WIDTH * 1 / 16, HEIGHT / 2 - this.texture.getHeight() * 4);

        //texts on the screen
        this.redLeft = new GlyphLayout(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getRedLeft()));
        this.yellowLeft = new GlyphLayout(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getYellowLeft()));
        this.blueLeft = new GlyphLayout(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getBlueLeft()));
        this.scoreOpponent = new GlyphLayout(Squarz.font, String.valueOf(score.getOpponentScore()));
        this.scoreUser = new GlyphLayout(Squarz.font, String.valueOf(score.getUserScore()));
        this.time = new GlyphLayout(Squarz.font, String.valueOf(this.countDown.getWorldTimer()));

        this.bonusChoiceSquare1 = new Icon(new Texture(Gdx.files.internal(format + "/bonuses/none.png"))
                , WIDTH * 1 / 16, HEIGHT / 2 - this.texture.getHeight() * 21 / 4);
        this.bonusChoiceSquare1.setTexture(this.settings.getBonus1().getBonustexture(this.settings.getBonus1().getBonusKey()));

        this.bonusChoiceSquare2 = new Icon(new Texture(Gdx.files.internal(format + "/bonuses/none.png"))
                , WIDTH * 1 / 16, HEIGHT / 2 - this.texture.getHeight() * 26 / 4);
        this.bonusChoiceSquare2.setTexture(this.settings.getBonus2().getBonustexture(this.settings.getBonus2().getBonusKey()));

        this.mute.setPosX(redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - this.mute.getTexture().getWidth() / 2);
        this.mute.setPosY(HEIGHT * 29 / 32 - this.mute.getTexture().getHeight() / 2);

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

            //Colour choice button
            chosingTheColour(x, y);
            choosingTheBonuses(x, y);

            if (mute.contains(x, y)) {
                if (varMute) {
                    valueVolume = 5;
                    music.setVolume(valueVolume);
                    this.mute.setTexture(new Texture(Gdx.files.internal(format + "/settings/sound.png")));
                    varMute = false;
                } else {
                    valueVolume = 0;
                    music.setVolume(valueVolume);
                    this.mute.setTexture(new Texture(Gdx.files.internal(format + "/mute.png")));
                    varMute = true;
                }
            }

            //Implementation for the launcher of each row
            if (!this.player.getSquareLimiter().isOver(colorKey) && isAllowedToPlay(exTime)) {
                creatingAndSendingANewSquare(x);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        Queue<Byte> lastMove = receive();
        if (lastMove.size() > 0) {
            decryptMessage(lastMove);
        }


        //updating the countdown
        this.countDown.update(dt);
        if (this.countDown.isTimeUp()) {
            music.stop();
            gsm.set(new EndModeAI(gsm, settings, score, countDown));
            // leaveRoom();
        }

        movingPlayerSquare(dt);
        movingOpponentSquare(dt);

        collision.collision(this.player, this.opponent);
    }

    @Override
    public void render(SpriteBatch sb) {

        drawLines();
        sb.begin();


        drawChosingColorSquares(sb);
        drawingSquares(sb, player);
        drawingSquares(sb, opponent);
        drawTimeLeft(sb);
        drawScore(sb);
        drawCounter(sb);

        sb.draw(bonusChoiceSquare1.getTexture(), bonusChoiceSquare1.getPosX(), bonusChoiceSquare1.getPosY());
        sb.draw(bonusChoiceSquare2.getTexture(), bonusChoiceSquare2.getPosX(), bonusChoiceSquare2.getPosY());

        sb.draw(mute.getTexture(), mute.getPosX(), mute.getPosY());


        drawTimeLeft(sb);
        drawScore(sb);
        drawCounter(sb);

        sb.end();
    }

    @Override
    public void dispose() {

    }



    //to avoid people sending too many squares
    public boolean isAllowedToPlay(float exTime){
        boolean allowed = false;
        float timeRef = countDown.getWorldTimer()-countDown.getTimeCount();
        allowed = exTime - timeRef > 0.45;
        if(allowed){this.exTime = timeRef;}
        return  allowed;
    }

    public void chosingTheColour(int x, int y) {
        if (this.redChoiceSquare.contains(x, y)) {
            this.setColorKey(0);
            this.setPLayerTexture(0);
        }

        if (this.blueChoiceSquare.contains(x, y)) {
            this.setColorKey(1);
            this.setPLayerTexture(1);
        }

        if (this.yellowChoiceSquare.contains(x, y)) {
            this.setColorKey(2);
            this.setPLayerTexture(2);
        }
    }

    /**
     * choosingTheBonus sends the information of clearance in the same time. (M propre effect)
     */
    public void choosingTheBonuses(int x, int y) {
        if (this.bonusChoiceSquare1.contains(x, y) && !firstIsUsed) {

            if (this.settings.getBonus1().getBonusKey() == 1) {
                this.colorKey = this.settings.getBonus1().punisherEffect();
                this.setPLayerTexture(4); //le mettre dans punishereffect
            }
            if (this.settings.getBonus1().getBonusKey() == 2) {
                this.settings.getBonus1().nurseEffectPlayer();
            }
            if (this.settings.getBonus1().getBonusKey() == 3) {
                this.settings.getBonus1().mrPropreEffect();
                send(new Byte("25"));
                System.out.print("Ecryption is 25");
                send(new Byte("25")); //Mr propre encryption
            }

                //after utilisation
            this.bonusChoiceSquare1.setTexture(new Texture(Gdx.files.internal(format + "/bonuses/used.png")));
            this.setFirstIsUsed(true);
        }

        if (this.bonusChoiceSquare2.contains(x, y) && !secondIsUsed) {


            if (this.settings.getBonus2().getBonusKey() == 1) {
                this.colorKey = this.settings.getBonus2().punisherEffect();
                this.setPLayerTexture(4); //lemettre dans le punisherEffect()
            }
            if (this.settings.getBonus2().getBonusKey() == 2) {
                this.settings.getBonus2().nurseEffectPlayer();
            }
            if (this.settings.getBonus2().getBonusKey() == 3) {
                this.settings.getBonus2().mrPropreEffect();
                send(new Byte("" + 25));
                System.out.print("Ecryption is 25");
            } //M Propre encryption


            this.bonusChoiceSquare2.setTexture(new Texture(Gdx.files.internal(format + "/bonuses/used.png")));
            this.setSecondIsUsed(true);
        }

    }

    public void creatingAndSendingANewSquare(int x) {
        if(this.getColumn(x) != -1) {
            player.increment(texture, this.getColumn(x), colorKey);
        }
        //punisher bonus encrytion:
        if(colorKey == 4){
            send(encryption(this.getColumn(x), 15)); //15*2 15*2+5 = 35   15*2 +5*2 = 40
        }
        else {
            send(encryption(this.getColumn(x), colorKey));
        }
    }

    public Integer getColumn(int x){
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


    public void movingPlayerSquare(float dt) {
        for(Integer columnKey=0; columnKey<3; columnKey++) {
            if (!player.getMap(columnKey).isEmpty()) {
                for (int i = player.getFirstSquareKey(columnKey); i < player.getCounter(columnKey); i++) {
                    player.getMap(columnKey).get(i).move(dt);

                    //dealing with the score
                    if (player.getMap(columnKey).get(player.getFirstSquareKey(columnKey)).getPosition().y >= HEIGHT
                            && player.getMap(columnKey).get(player.getFirstSquareKey(columnKey)).getPosition().y < HEIGHT + this.settings.getStepX()*dt) {
                        sound.play(Squarz.valueVolume * 0.15f);
                        Gdx.input.vibrate(Squarz.valueVibration * 50);
                    }
                }
            }
        }
    }
    public void movingOpponentSquare(float dt) {
        for(int columnKey=0; columnKey<3; columnKey++){
            if (!opponent.getMap(columnKey).isEmpty()) {
                for (int i = opponent.getFirstSquareKey(columnKey); i < opponent.getCounter(columnKey); i++) {
                    opponent.getMap(columnKey).get(i).reverseMove(dt);
                    //dealing with the score
                    if (opponent.getMap(columnKey).get(i).getPosition().y <= 0 && opponent.getMap(columnKey).get(i).getPosition().y > - this.settings.getStepX()*dt) {
                        sound.play(Squarz.valueVolume * 0.15f);
                        Gdx.input.vibrate(Squarz.valueVibration * 50);
                    }
                }
            }
        }
    }

    public void drawingSquares(SpriteBatch sb, Player p) {
        for (int columnKey = 0; columnKey < 3; columnKey++) {
            if (!p.getMap(columnKey).isEmpty()) {
                for (int i = p.getFirstSquareKey(columnKey); i < p.getCounter(columnKey); i++) {
                    sb.draw(p.getMap(columnKey).get(i).getTexture(),
                            p.getMap(columnKey).get(i).getPosition().x - p.getMap(columnKey).get(i).getTexture().getWidth() / 2, p.getMap(columnKey).get(i).getPosition().y - p.getMap(columnKey).get(i).getTexture().getHeight() / 2);
                }
            }
        }
    }
    public void drawScore(SpriteBatch sb) {
        scoreOpponent.setText(Squarz.font, String.valueOf(score.getOpponentScore()));
        scoreUser.setText(Squarz.font, String.valueOf(score.getUserScore()));
        Squarz.font.draw(sb, scoreOpponent, redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - scoreOpponent.width / 2, HEIGHT * 45 / 64 - scoreOpponent.height / 2);
        Squarz.font.draw(sb, scoreUser, redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - scoreUser.width / 2, HEIGHT * 39 / 64 - scoreUser.height / 2);
    }
    public void drawCounter(SpriteBatch sb) {
        //number of user squares lefting
        redLeft.setText(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getRedLeft()));
        blueLeft.setText(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getBlueLeft()));
        yellowLeft.setText(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getYellowLeft()));
        Squarz.font2.draw(sb, redLeft, redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - redLeft.width / 2, redChoiceSquare.getPosY() + redChoiceSquare.getTexture().getHeight() / 2 + redLeft.height / 2);
        Squarz.font2.draw(sb, blueLeft, blueChoiceSquare.getPosX() + blueChoiceSquare.getTexture().getWidth() / 2 - blueLeft.width / 2, blueChoiceSquare.getPosY() + blueChoiceSquare.getTexture().getHeight() / 2 + blueLeft.height / 2);
        Squarz.font2.draw(sb, yellowLeft, yellowChoiceSquare.getPosX() + yellowChoiceSquare.getTexture().getWidth() / 2 - yellowLeft.width / 2, yellowChoiceSquare.getPosY() + yellowChoiceSquare.getTexture().getHeight() / 2 + yellowLeft.height / 2);
    }

    public void drawTimeLeft(SpriteBatch sb) {
        time.setText(Squarz.font, String.valueOf(this.countDown.getWorldTimer()));
        Squarz.font.draw(sb, String.valueOf(this.countDown.getWorldTimer()), redChoiceSquare.getPosX() + redChoiceSquare.getTexture().getWidth() / 2 - time.width / 2, HEIGHT * 27 / 32 - time.height / 2);
    }

    public void drawLines() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.line(WIDTH / 4, 0, WIDTH / 4, HEIGHT);
        shapeRenderer.line(WIDTH / 4 - 10, 0, WIDTH / 4 - 10, HEIGHT);
        shapeRenderer.line(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
        shapeRenderer.line(3 * WIDTH / 4, 0, 3 * WIDTH / 4, HEIGHT);
        shapeRenderer.line(0, HEIGHT / 2, WIDTH / 4 - 10, HEIGHT / 2);
        shapeRenderer.line(0, 3 * HEIGHT / 4, WIDTH / 4 - 10, 3 * HEIGHT / 4);
        shapeRenderer.end();

    }

    public void drawChosingColorSquares(SpriteBatch sb) {
        sb.draw(redChoiceSquare.getTexture(), redChoiceSquare.getPosX(), redChoiceSquare.getPosY());
        sb.draw(blueChoiceSquare.getTexture(), blueChoiceSquare.getPosX(), blueChoiceSquare.getPosY());
        sb.draw(yellowChoiceSquare.getTexture(), yellowChoiceSquare.getPosX(), yellowChoiceSquare.getPosY());
    }

    //---------------------------------- sending and receiving ------------------------------------

    public Byte encryption(int columnKey, int colorKey){
        int number = columnKey * 5 + colorKey * 2;
        System.out.println("encryption is: " + number);
        return new Byte(""+number);
    }

    void send(byte b){
        gsm.getMultiplayerInterface().sendIncrement(b);
    }

    public void decryptMessage(Queue<Byte> lastMove) {
        Byte b = lastMove.peek();
        ArrayList<Integer> list = getInformation(b);
        if(list.size()>0) {
            if(list.get(0) >= 0){ //if <0 it is Mr Propre, already handled in getInformation.
                opponent.incrementOpponent(getTexture(list.get(1)), list.get(0), list.get(1));
            }
        }
    }

    public ArrayList<Integer> getInformation(Byte b) {
        System.out.println("the byte I just received: " + b.floatValue());
        ArrayList<Integer> information = new ArrayList<Integer>();
        //handle with bonuses:
        if(b.floatValue()>= 25) {
            //handle M propre (byte = 25)
            if (b.floatValue() == 25) {
                this.settings.getBonus1().mrPropreEffect();
                information.add(-1);
                information.add( -1);
            }
            else {
                //handle with punisher:
                for (int j = 0; j < 3; j++) {
                    if (30 + j * 5 == b.floatValue()) {
                        information.add(j);
                        information.add(4);
                    }
                }
            }
        }
        //otherwise:
        else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i * 5 + j * 2 == b.floatValue()) {
                        information.add(i); //column key
                        information.add(j); //colour key
                    }
                }
            }
        }
        return information;
    }

    public Texture getTexture(Integer colourKey) {
        Texture t = new Texture(Gdx.files.internal(format + "/square/square_red.png"));

        if (colourKey == 0) {
            t = new Texture(Gdx.files.internal(format + "/square/square_red.png"));
        }
        if (colourKey == 1) {
            t = new Texture(Gdx.files.internal(format + "/square/square_blue.png"));
        }
        if (colourKey == 2) {
            t = new Texture(Gdx.files.internal(format + "/square/square_yellow.png"));
        }
        if (colourKey == 4) {
            t = new Texture(Gdx.files.internal(format + "/bonuses/punisher.png"));
        }
        return t;
    }

    public void setPLayerTexture(int colorKey){
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

    Queue<Byte> receive() {
        return gsm.getMultiplayerInterface().popMoves();
    }

    /**
     * General getters and setters.
     */

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Integer getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(Integer columnKey) {
        this.columnKey = columnKey;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(AIPlayer opponent) {
        this.opponent = opponent;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        return opponent;
    }

    public void setAi(AIPlayer opponent) {
        this.opponent = opponent;
    }

    public Boolean getFirstIsUsed() {
        return firstIsUsed;
    }

    public void setFirstIsUsed(Boolean firstIsUsed) {
        this.firstIsUsed = firstIsUsed;
    }

    public Boolean getSecondIsUsed() {
        return secondIsUsed;
    }

    public void setSecondIsUsed(Boolean secondIsUsed) {
        this.secondIsUsed = secondIsUsed;
    }
}
