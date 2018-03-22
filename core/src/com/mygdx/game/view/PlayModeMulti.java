package com.mygdx.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Collision;
import com.mygdx.game.model.CountDown;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.Score;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;

import java.util.Queue;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

public class PlayModeMulti extends State {

    private Music music;
    private Sound sound;

    private Square choiceSquare;
    private Texture texture;
    private Integer colorkey;
    private Integer collomnKey;

    private Score score;
    private CountDown countDown;

    private Player opponent;
    private Player player;

    private Collision collision;

    public PlayModeMulti(GameStateManager gsm){
        super(gsm);


        player = new Player();
        opponent = new Player();

        choiceSquare = new Square();
        choiceSquare.setPosition(new Vector2(WIDTH * 1 / 16, HEIGHT * 1 / 5));

        texture = new Texture(Gdx.files.internal("square.png"));
        colorkey = 0;

        score = new Score();

        countDown = new CountDown(60, 0);

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
        Queue<Byte> moves = gsm.getMultiplayerInterface().popMoves();
        for (int i = 0; i < moves.size(); i++) {
         Byte b = moves.remove();
        }
    }


    @Override
    public void update(float dt) {
        handleInput();
        handleReceivedMessage();

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

    Queue<Byte> recieve(){
        return gsm.getMultiplayerInterface().popMoves();
    }
}
