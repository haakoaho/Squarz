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

import java.util.ArrayList;
import java.util.Queue;


import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.format;
import static com.mygdx.game.Squarz.valueVolume;

public class PlayModeMulti extends State {
    private Music music;
    private Sound sound;
    private GlyphLayout readyGlyph, redLeft, yellowLeft, blueLeft, scoreUser, scoreOpponent, time;
    private boolean varMute;

    private ShapeRenderer shapeRenderer;

    private PreferencesSettings set;
    private CountDown countDown;

    private AIPlayer opponent;
    private Player player;

    private Collision collision;

    private Square choiceSquare;

    private Icon redChoiceSquare, blueChoiceSquare, yellowChoiceSquare, mute;
    private Texture texture;
    private Integer colorKey;
    private Integer columnKey;

    private Score score;

    private float exTime;


    public PlayModeMulti(GameStateManager gsm) {
        super(gsm);
        this.set = new PreferencesSettings();
        this.countDown = new CountDown(60);
        player = new Player(set, countDown);
        opponent = new AIPlayer(set, countDown);


        choiceSquare = new Square(set);
        choiceSquare.setPosition(new Vector2(WIDTH * 1 / 16, HEIGHT * 1 / 5));

        if(valueVolume==0) {
            this.mute = new Icon(new Texture(Gdx.files.internal(format+"/mute.png")),0,0);
            this.varMute=true;
        } else {
            this.mute = new Icon(new Texture(Gdx.files.internal(format+"/settings/sound.png")),0,0);
            this.varMute=false;
        }


        this.colorKey = 0;
        score = new Score();
        countDown = new CountDown(60);
        collision = new Collision();

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
        this.redLeft = new GlyphLayout(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getRedLefting()));
        this.yellowLeft = new GlyphLayout(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getYellowLefting()));
        this.blueLeft = new GlyphLayout(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getBlueLefting()));
        this.scoreOpponent = new GlyphLayout(Squarz.font, String.valueOf(score.getOpponentScore()));
        this.scoreUser = new GlyphLayout(Squarz.font, String.valueOf(score.getUserScore()));
        this.time = new GlyphLayout(Squarz.font, String.valueOf(this.countDown.getWorldTimer()));

        this.mute.setPosX(redChoiceSquare.getPosX()+redChoiceSquare.getTexture().getWidth()/2-this.mute.getTexture().getWidth()/2);
        this.mute.setPosY(HEIGHT * 29 / 32 - this.mute.getTexture().getHeight()/2);

        this.readyGlyph = new GlyphLayout(Squarz.font, "READY ?");

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

            if(mute.contains(x,y)) {
                if(varMute) {
                    valueVolume=5;
                    music.setVolume(valueVolume);
                    this.mute.setTexture(new Texture(Gdx.files.internal(format+"/settings/sound.png")));
                    varMute=false;
                } else {
                    valueVolume=0;
                    music.setVolume(valueVolume);
                    this.mute.setTexture(new Texture(Gdx.files.internal(format+"/mute.png")));
                    varMute=true;
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
        if (lastMove.size()>0) {
            decryptMessage(lastMove);
            System.out.println("Detected");
        }

        //updating the countdown
        this.countDown.update(dt);
        if (this.countDown.isTimeUp()) {
            music.stop();
            gsm.set(new EndModeAI(gsm, set, score, countDown));
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

        sb.draw(mute.getTexture(),mute.getPosX(),mute.getPosY());

        sb.end();
        }

    @Override
    public void dispose() {

    }


    public boolean isAllowedToPlay(float exTime){
        boolean allowed = false;
        //initialisation
        if(exTime == countDown.getWorldTimer()) {
            allowed = true;
        }
        else {
            float timeRef = countDown.getTimeCount();
            allowed = exTime - timeRef > 0.2;
            this.exTime = timeRef;
        }
        return  allowed;
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

    public void creatingAndSendingANewSquare(int x) {
        if (x > WIDTH / 4 && x < WIDTH / 2) {
            player.increment(texture, 0, colorKey);
            send(encryption(0, colorKey));
            System.out.println(encryption(0, colorKey).toString());
        }
        if (x > WIDTH / 2 && x < WIDTH * 3 / 4) {
            player.increment(texture, 1, colorKey);
            send(encryption(1, colorKey));
            System.out.println(encryption(1, colorKey).toString());
        }
        if (x > WIDTH * 3 / 4) {
            player.increment(texture, 2, colorKey);
            send(encryption(2, colorKey));
            System.out.println(encryption(2, colorKey).toString());
        }
    }

    public void movingPlayerSquare(float dt) {
        if (!player.getLeft().isEmpty()) {
            for (int i = player.getFirstLeftSquaresKey(); i < player.getLeftCounter(); i++) {
                player.getLeft().get(i).move(dt);
                //dealing with the score
                if (player.getLeft().get(i).getPosition().y >= HEIGHT && player.getLeft().get(i).getPosition().y < HEIGHT + this.set.getStepX()*dt) {
                    sound.play(Squarz.valueVolume * 0.15f);
                    Gdx.input.vibrate(Squarz.valueVibration * 50);
                    this.score.updateUser();
                }
            }
        }
        if (!player.getMiddle().isEmpty()) {
            for (int i = player.getFirstMiddleSquaresKey(); i < player.getMiddleCounter(); i++) {
                player.getMiddle().get(i).move(dt);
                if (player.getMiddle().get(i).getPosition().y >= HEIGHT && player.getMiddle().get(i).getPosition().y < HEIGHT + this.set.getStepX()*dt) {
                    sound.play(Squarz.valueVolume * 0.15f);
                    Gdx.input.vibrate(Squarz.valueVibration * 50);
                    this.score.updateUser();
                }
            }
        }
        if (!player.getRight().isEmpty()) {
            for (int i = player.getFirstRightSquaresKey(); i < player.getRightCounter(); i++) {
                player.getRight().get(i).move(dt);
                if (player.getRight().get(i).getPosition().y >= HEIGHT && player.getRight().get(i).getPosition().y < HEIGHT + this.set.getStepX()*dt) {
                    sound.play(Squarz.valueVolume * 0.15f);
                    Gdx.input.vibrate(Squarz.valueVibration * 50);
                    this.score.updateUser();
                }
            }
        }
    }

    public void movingOpponentSquare(float dt) {
        if (!opponent.getLeft().isEmpty()) {
            for (int i = opponent.getFirstLeftSquaresKey(); i < opponent.getLeftCounter(); i++) {
                opponent.getLeft().get(i).reverseMove(dt);
                //dealing with the score
                if (opponent.getLeft().get(i).getPosition().y <= 0 && opponent.getLeft().get(i).getPosition().y > -this.set.getStepX()*dt) {
                    sound.play(Squarz.valueVolume * 0.15f);
                    Gdx.input.vibrate(Squarz.valueVibration * 50);
                    this.score.updateAi();
                }
            }
        }
        if (!opponent.getMiddle().isEmpty()) {
            for (int i = opponent.getFirstMiddleSquaresKey(); i < opponent.getMiddleCounter(); i++) {
                opponent.getMiddle().get(i).reverseMove(dt);
                //dealing with the score
                if (opponent.getMiddle().get(i).getPosition().y <= 0 && opponent.getMiddle().get(i).getPosition().y > -this.set.getStepX()*dt) {
                    sound.play(Squarz.valueVolume * 0.15f);
                    Gdx.input.vibrate(Squarz.valueVibration * 50);
                    this.score.updateAi();
                }
            }
        }
        if (!opponent.getRight().isEmpty()) {
            for (int i = opponent.getFirstRightSquaresKey(); i < opponent.getRightCounter(); i++) {
                opponent.getRight().get(i).reverseMove(dt);
                if (opponent.getRight().get(i).getPosition().y <= 0 && opponent.getRight().get(i).getPosition().y > -this.set.getStepX()*dt) {
                    sound.play(Squarz.valueVolume * 0.15f);
                    Gdx.input.vibrate(Squarz.valueVibration * 50);
                    this.score.updateAi();
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
        redLeft.setText(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getRedLefting()));
        blueLeft.setText(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getBlueLefting()));
        yellowLeft.setText(Squarz.font2, String.valueOf(this.player.getSquareLimiter().getYellowLefting()));
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

    //---------------------------------- sending ------------------------------------

    public Byte encryption(int columnKey, int colorKey){
        int number =  columnKey*5 + colorKey * 2;
        return new Byte(""+number);
    }


    //---------------------------------- detection ----------------------------------

    public void handleReceivedMessage() {
        Queue<Byte> moves = receive();
        for (int i = 0; i < moves.size(); i++) {
            Byte b = moves.remove();
        }
    }

    /**
     * called when a new message has been detected.
     */
    public void decryptMessage(Queue<Byte> lastMove) {
        System.out.println("Move SIZE:"+lastMove.size());
        Byte b = lastMove.peek();
        System.out.println("The byte: "+b.toString());
        if(getInformation(b).size()>0) {
            opponent.incrementOpponent(getTexture(getInformation(b).get(1)), getInformation(b).get(0), getInformation(b).get(1));
        }

    }

    public ArrayList<Integer> getInformation(Byte b) {
        //Integer.parseInt(b.toString().substring(1,2));
        ArrayList<Integer> information = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i * 5 + j * 2 == b.floatValue()) {
                    information.add(i); //column key
                    information.add(j); //colour key
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
        return t;
    }


    void send(byte b) {
        gsm.getMultiplayerInterface().sendIncrement(b);
    }

    Queue<Byte> receive() {
        return gsm.getMultiplayerInterface().popMoves();
    }




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
        return set;
    }

    public void setSettings(PreferencesSettings settings) {
        this.set = settings;
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
}
