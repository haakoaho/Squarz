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

// CLEAN //
public class Menu extends State {
    private Icon play, settings, tutorial;
    private GlyphLayout name, credits;

    public Menu(GameStateManager gsm) {
        super(gsm);

        this.play = new Icon(new Texture(Gdx.files.internal(format+"/menu/play.png")),0,0); // button Play, touch it to go to the mode selection
        this.settings = new Icon(new Texture(Gdx.files.internal(format+"/menu/settings.png")),0,0); // button Settings, touch it to go the the sound&vibration selection
        this.tutorial = new Icon(new Texture(Gdx.files.internal(format+"/menu/history.png")),0,0); // button Tutorial, touch it if you want to read the tutorial

        this.play.setPosX(WIDTH/2-this.play.getTexture().getWidth()/2);
        this.play.setPosY(HEIGHT*3/4-this.play.getTexture().getHeight()/2);
        this.play.setLegend("Play Mode");
        this.settings.setPosX(WIDTH/2-this.settings.getTexture().getWidth()/2);
        this.settings.setPosY(HEIGHT*2/4-this.settings.getTexture().getHeight()/2);
        this.settings.setLegend("Sounds & Vibrations");
        this.tutorial.setPosX(WIDTH/2-this.tutorial.getTexture().getWidth()/2);
        this.tutorial.setPosY(HEIGHT*1/4-this.tutorial.getTexture().getHeight()/2);
        this.tutorial.setLegend("Tutorial");

        this.name = new GlyphLayout(Squarz.font,"Let\'s play to Squarz!");
        this.credits = new GlyphLayout(Squarz.font2,"A NTNU creation");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (this.play.contains(x,y)) {
                gsm.set(new Pref(gsm)); // to go to the screen where you can select the mode you want
                dispose();
            }
            if(this.settings.contains(x,y)){
                gsm.set(new Settings(gsm)); // to go the screen where you can choose the level of sound and vibrations
                dispose();
            }
            if(this.tutorial.contains(x,y)){
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
        Squarz.font.draw(sb, this.name, WIDTH/2 - this.name.width/2,HEIGHT-3/2*this.name.height);
        Squarz.font2.draw(sb, this.credits, WIDTH/2 - this.credits.width/2,HEIGHT-2*this.name.height-2*this.credits.height);
        sb.draw(this.play.getTexture(), this.play.getPosX(), this.play.getPosY());
        font3.draw(sb, this.play.getLegend(), this.play.getPosX() + this.play.getTexture().getWidth()/2 - this.play.getLegend().width/2, this.play.getPosY() - 2*this.play.getLegend().height);
        sb.draw(this.settings.getTexture(), this.settings.getPosX(), this.settings.getPosY());
        font3.draw(sb, this.settings.getLegend(), this.settings.getPosX() + this.settings.getTexture().getWidth()/2 - this.settings.getLegend().width/2, this.settings.getPosY() - 2*this.settings.getLegend().height);
        sb.draw(this.tutorial.getTexture(), this.tutorial.getPosX(), this.tutorial.getPosY());
        font3.draw(sb, this.tutorial.getLegend(), this.tutorial.getPosX() + this.tutorial.getTexture().getWidth()/2 - this.tutorial.getLegend().width/2, this.tutorial.getPosY() - 2*this.tutorial.getLegend().height);
        sb.end();
    }

    @Override
    public void dispose() {
    }
}