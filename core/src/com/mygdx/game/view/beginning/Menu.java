package com.mygdx.game.view.beginning;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font3;
import static com.mygdx.game.Squarz.format;

public class Menu extends State {
    private Icon play, settings, history;
    private GlyphLayout name, credits;

    public Menu(GameStateManager gsm) {
        super(gsm);

        this.play = new Icon(new Texture(Gdx.files.internal(format+"/menu/play.png")),0,0);
        this.settings = new Icon(new Texture(Gdx.files.internal(format+"/menu/settings.png")),0,0);
        this.history = new Icon(new Texture(Gdx.files.internal(format+"/menu/history.png")),0,0);

        this.play.setPosX(WIDTH/2-play.getTexture().getWidth()/2);
        this.play.setPosY(HEIGHT*3/4-play.getTexture().getHeight()/2);
        this.play.setLegend("Play Mode");
        this.settings.setPosX(WIDTH/2-settings.getTexture().getWidth()/2);
        this.settings.setPosY(HEIGHT*2/4-settings.getTexture().getHeight()/2);
        this.settings.setLegend("Sounds & Vibrations");
        this.history.setPosX(WIDTH/2-history.getTexture().getWidth()/2);
        this.history.setPosY(HEIGHT*1/4-history.getTexture().getHeight()/2);
        this.history.setLegend("Tutorial");

        this.name = new GlyphLayout(Squarz.font,"Let\'s play to Squarz!");
        this.credits = new GlyphLayout(Squarz.font2,"A NTNU creation");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (play.contains(x,y)) {
                gsm.set(new Pref(gsm)); // to go to the screen where you can select the mode you want
                dispose();
            }
            if(settings.contains(x,y)){
                gsm.set(new Settings(gsm)); // to go the screen where you can choose the level of sound and vibrations
                dispose();
            }
            if(history.contains(x,y)){
                gsm.set(new Tutorial(gsm)); // to go to the tutorial
                dispose();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        Squarz.font.draw(sb, name, WIDTH/2 - name.width/2,HEIGHT-2*name.height);
        Squarz.font2.draw(sb, credits, WIDTH/2 - credits.width/2,HEIGHT-3*name.height-2*credits.height);
        sb.draw(play.getTexture(), play.getPosX(), play.getPosY());
        font3.draw(sb, this.play.getLegend(), this.play.getPosX() + this.play.getTexture().getWidth()/2 - this.play.getLegend().width/2, this.play.getPosY() - 2*this.play.getLegend().height);
        sb.draw(settings.getTexture(), settings.getPosX(), settings.getPosY());
        font3.draw(sb, this.settings.getLegend(), this.settings.getPosX() + this.settings.getTexture().getWidth()/2 - this.settings.getLegend().width/2, this.settings.getPosY() - 2*this.settings.getLegend().height);
        sb.draw(history.getTexture(), history.getPosX(), history.getPosY());
        font3.draw(sb, this.history.getLegend(), this.history.getPosX() + this.history.getTexture().getWidth()/2 - this.history.getLegend().width/2, this.history.getPosY() - 2*this.history.getLegend().height);
        sb.end();
    }

    @Override
    public void dispose() {
    }
}