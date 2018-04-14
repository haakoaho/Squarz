package com.mygdx.game.view.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;
import com.mygdx.game.view.PlayModeMulti;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font3;
import static com.mygdx.game.Squarz.format;

/**
 * Created by Lucas on 06/03/2018.
 */

public class Pref extends State {
    private Icon ai, quick, invite, answer, back;

    public Pref(GameStateManager gsm) {
        super(gsm);
        this.ai = new Icon(new Texture(Gdx.files.internal(format+"/modes/ai.png")),0,0);
        this.quick = new Icon(new Texture(Gdx.files.internal(format+"/modes/quick.png")),0,0);
        this.invite = new Icon(new Texture(Gdx.files.internal(format+"/modes/invite.png")),0,0);
        this.answer = new Icon(new Texture(Gdx.files.internal(format+"/modes/answer.png")),0,0);
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);

        this.ai.setPosX(WIDTH/4-ai.getTexture().getWidth()/2);
        this.ai.setPosY(7*HEIGHT/10-ai.getTexture().getHeight()/2);
        this.ai.setLegend("AI Mode");
        this.quick.setPosX(3*WIDTH/4-quick.getTexture().getWidth()/2);
        this.quick.setPosY(7*HEIGHT/10-quick.getTexture().getHeight()/2);
        this.quick.setLegend("Online Quick Game");
        this.invite.setPosX(WIDTH/4-invite.getTexture().getWidth()/2);
        this.invite.setPosY(3*HEIGHT/10-invite.getTexture().getHeight()/2);
        this.invite.setLegend("Send an invitation");
        this.answer.setPosX(3*WIDTH/4-answer.getTexture().getWidth()/2);
        this.answer.setPosY(3*HEIGHT/10-answer.getTexture().getHeight()/2);
        this.answer.setLegend("Answer to an invitation");
        this.back.setPosX(back.getTexture().getWidth()/2);
        this.back.setPosY(back.getTexture().getHeight()/2);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(ai.contains(x,y)){
                gsm.set(new com.mygdx.game.view.AIPreferences(gsm));
                dispose();
            }
            if (quick.contains(x,y)){
                gsm.getMultiplayerInterface().startQuickGame();
            }
            if (answer.contains(x,y)){
                gsm.getMultiplayerInterface().checkForInvitation();
            }
            if(invite.contains(x,y)){
                gsm.getMultiplayerInterface().invite();

            }
            if (back.contains(x,y)) {
                gsm.set(new Menu(gsm));
                dispose();
            }
        }
    }
    //
    @Override
    public void update(float dt) {
        handleInput();

        // pushes to the multiplayer screen if room was succsesfully created
        if (gsm.getMultiplayerInterface().isGameReady()) {
            gsm.set(new PlayModeMulti(gsm));
        }


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(ai.getTexture(), ai.getPosX(),ai.getPosY() );
        font3.draw(sb,this.ai.getLegend(), ai.getPosX() + ai.getTexture().getWidth()/2 - this.ai.getLegend().width/2, ai.getPosY() - 2*this.ai.getLegend().height);
        sb.draw(quick.getTexture(),quick.getPosX() ,quick.getPosY() );
        font3.draw(sb,this.quick.getLegend(), quick.getPosX() + quick.getTexture().getWidth()/2 - this.quick.getLegend().width/2, quick.getPosY() - 2*this.quick.getLegend().height);
        sb.draw(invite.getTexture(), invite.getPosX(), invite.getPosY());
        font3.draw(sb,this.invite.getLegend(), invite.getPosX() + invite.getTexture().getWidth()/2 - this.invite.getLegend().width/2, invite.getPosY() - 2*this.invite.getLegend().height);
        sb.draw(answer.getTexture(),answer.getPosX(),answer.getPosY());
        font3.draw(sb,this.answer.getLegend(), answer.getPosX() + answer.getTexture().getWidth()/2 - this.answer.getLegend().width/2, answer.getPosY() - 2*this.answer.getLegend().height);
        sb.draw(back.getTexture(),back.getPosX(),back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {
        ai.getTexture().dispose();
        quick.getTexture().dispose();
        invite.getTexture().dispose();
        answer.getTexture().dispose();
        back.getTexture().dispose();
    }
}
