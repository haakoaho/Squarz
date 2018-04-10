package com.mygdx.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.AIPlayer;
import com.mygdx.game.model.Collision;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;

import java.util.ArrayList;
import java.util.Queue;


import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.format;

public class PlayModeMulti extends State {

    private Music music;
    private Sound sound;

    private Square choiceSquare;
    private Texture texture;
    private Integer colorkey;
    private Integer columnKey;

    private Score score;
    private PreferencesSettings set;
    private CountDown countDown;

    private AIPlayer opponent;
    private Queue<Byte> opponentMoves;
    private Player player;

    private Collision collision;

    public PlayModeMulti(GameStateManager gsm){
        super(gsm);
        this.set = new PreferencesSettings();
        this.countDown = new CountDown(60);
        player = new Player(set, countDown);
        opponent = new AIPlayer(set, countDown);

        choiceSquare = new Square(set);
        choiceSquare.setPosition(new Vector2(WIDTH * 1 / 16, HEIGHT * 1 / 5));

        texture = new Texture(Gdx.files.internal("square.png"));
        colorkey = 0;

        score = new Score();

        countDown = new CountDown(60);

        collision = new Collision();


        music=Gdx.audio.newMusic(Gdx.files.internal("sound/here.mp3"));
        music.setLooping(true);
        music.setVolume(Squarz.valueVolume*0.1f);
        music.play();

        sound = Gdx.audio.newSound(Gdx.files.internal("sound/meuh.mp3"));

    }

    @Override
    public void handleInput() {

        //Colour choice button
        if ((Gdx.input.getX() < WIDTH / 4) && (HEIGHT - Gdx.input.getY() >= this.choiceSquare.getPosition().y)
                && (HEIGHT - Gdx.input.getY() <= this.choiceSquare.getPosition().y + this.choiceSquare.getTexture().getHeight())) {
            this.colorkey = this.colorkey + 1;
            if (this.colorkey == 3) {
                this.colorkey = 0;
            }
            if (this.colorkey == 0) {
                this.texture = new Texture(Gdx.files.internal("square_red.png"));
            } else {
                if (this.colorkey == 1) {
                    this.texture = new Texture(Gdx.files.internal("square_blue.png"));
                } else {
                    this.texture = new Texture(Gdx.files.internal("square_yellow.png"));
                }
            }
            this.choiceSquare.setTexture(texture);
        }
    }






    public void handleReceivedMessage(){
        Queue<Byte> moves = receive();
        for (int i = 0; i < moves.size(); i++) {
         Byte b = moves.remove();
        }
    }


    public boolean newMessageDetected(){
        boolean detected = false;
        Queue<Byte> moves = receive();
        if(moves.size()>opponentMoves.size()){
            opponentMoves.add(moves.remove());
            detected = true;
        }
        return detected;
    }

    /**
     * called when a new message has been detected.
     */
    public void decryptMessage(){
        Queue<Byte> moves = receive();
        Byte b = moves.remove();
        opponent.incrementAI(getTexture(getInformation(b).get(1)), getInformation(b).get(0), getInformation(b).get(1));
    }

    public ArrayList<Integer> getInformation(Byte b){
        //Integer.parseInt(b.toString().substring(1,2));
        ArrayList<Integer> information = new ArrayList<Integer>();
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++) {
                if(i*64 + j*16 == b.floatValue()){
                    information.add(i); //column key
                    information.add(j); //colour key
                }
            }
        }
        return information;
    }

    public Texture getTexture(Integer colourKey){
        Texture t = new Texture(Gdx.files.internal(format + "/square/square_red.png"));

        if(colourKey == 0){
            t = new Texture(Gdx.files.internal(format + "/square/square_red.png"));
        }
        if(colourKey == 1){
            t = new Texture(Gdx.files.internal(format + "/square/square_blue.png"));
        }
        if(colourKey == 2){
            t = new Texture(Gdx.files.internal(format + "/square/square_yellow.png"));
        }
        return t;
    }


    @Override
    public void update(float dt) {
        handleInput();
        if(newMessageDetected()){
            decryptMessage();
        }
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }


    void send(byte b){
        gsm.getMultiplayerInterface().sendIncrement(b);

    }

    Queue<Byte> receive(){
        return gsm.getMultiplayerInterface().popMoves();
    }




    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Integer getColorkey() {
        return colorkey;
    }

    public void setColorkey(Integer colorkey) {
        this.colorkey = colorkey;
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
}
