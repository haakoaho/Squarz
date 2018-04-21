package com.mygdx.game.view.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import com.mygdx.game.view.AIPreferences;
import com.mygdx.game.view.Preferences.BonusSelectionMulti;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font3;
import static com.mygdx.game.Squarz.format;

// CLEAN //
public class Pref extends State {
    private final Icon ai;
    private final Icon quick;
    private final Icon invite;
    private final Icon answer;
    private final Icon back;

    public Pref(GameStateManager gsm) {
        super(gsm);

        this.ai = new Icon(new Texture(Gdx.files.internal(format+"/modes/ai.png")),0,0); // button AI, touch it if you want to play a local game, against the AI
        this.quick = new Icon(new Texture(Gdx.files.internal(format+"/modes/quick.png")),0,0); // button Quick, touch it if you want to play against a random opponent
        this.invite = new Icon(new Texture(Gdx.files.internal(format+"/modes/invite.png")),0,0); // button Invite, touch it if you want to invite a friend to play with you
        this.answer = new Icon(new Texture(Gdx.files.internal(format+"/modes/answer.png")),0,0); // button Answer, touch it if you want ti answer to an invitation from a friend
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);

        this.ai.setPosX(WIDTH/4-this.ai.getTexture().getWidth()/2);
        this.ai.setPosY(7*HEIGHT/10-this.ai.getTexture().getHeight()/2);
        this.ai.setLegend("AI Mode");
        this.quick.setPosX(3*WIDTH/4-this.quick.getTexture().getWidth()/2);
        this.quick.setPosY(7*HEIGHT/10-this.quick.getTexture().getHeight()/2);
        this.quick.setLegend("Online Quick Game");
        this.invite.setPosX(WIDTH/4-this.invite.getTexture().getWidth()/2);
        this.invite.setPosY(3*HEIGHT/10-this.invite.getTexture().getHeight()/2);
        this.invite.setLegend("Send an invitation");
        this.answer.setPosX(3*WIDTH/4-this.answer.getTexture().getWidth()/2);
        this.answer.setPosY(3*HEIGHT/10-this.answer.getTexture().getHeight()/2);
        this.answer.setLegend("Answer to an invitation");
        this.back.setPosX(this.back.getTexture().getWidth()/2);
        this.back.setPosY(this.back.getTexture().getHeight()/2);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(this.ai.contains(x,y)){
                gsm.set(new AIPreferences(gsm)); // to go to the AI preferences selection, you will be able to change some settings
            }
            if (this.quick.contains(x,y)){
                gsm.getMultiplayerInterface().startQuickGame(); // to start a quick game
            }
            if (this.answer.contains(x,y)){
                gsm.getMultiplayerInterface().checkForInvitation(); // to answer to an invitation
            }
            if(this.invite.contains(x,y)){
                gsm.getMultiplayerInterface().invite(); // to invite someone to play
            }
            if (this.back.contains(x,y)) {
                gsm.set(new Menu(gsm)); // to go back to the menu
            }
        }
    }
    //
    @Override
    public void update(float dt) {
        handleInput();
        if (gsm.getMultiplayerInterface().isGameReady()) {
            gsm.set(new BonusSelectionMulti(gsm)); //if the game is ready to be played, we go to the bonus selection screen
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(this.ai.getTexture(), this.ai.getPosX(),this.ai.getPosY() );
        font3.draw(sb,this.ai.getLegend(), this.ai.getPosX() + this.ai.getTexture().getWidth()/2 - this.ai.getLegend().width/2, this.ai.getPosY() - 2*this.ai.getLegend().height);
        sb.draw(this.quick.getTexture(),quick.getPosX() ,quick.getPosY() );
        font3.draw(sb,this.quick.getLegend(), quick.getPosX() + this.quick.getTexture().getWidth()/2 - this.quick.getLegend().width/2, this.quick.getPosY() - 2*this.quick.getLegend().height);
        sb.draw(this.invite.getTexture(), this.invite.getPosX(), this.invite.getPosY());
        font3.draw(sb,this.invite.getLegend(), this.invite.getPosX() + this.invite.getTexture().getWidth()/2 - this.invite.getLegend().width/2, this.invite.getPosY() - 2*this.invite.getLegend().height);
        sb.draw(this.answer.getTexture(),this.answer.getPosX(),this.answer.getPosY());
        font3.draw(sb,this.answer.getLegend(), this.answer.getPosX() + this.answer.getTexture().getWidth()/2 - this.answer.getLegend().width/2, this.answer.getPosY() - 2*this.answer.getLegend().height);
        sb.draw(this.back.getTexture(),this.back.getPosX(),this.back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {
    }
}
