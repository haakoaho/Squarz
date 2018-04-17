package com.mygdx.game.view.beginning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.control.GameStateManager;
import com.mygdx.game.model.Icon;
import com.mygdx.game.model.Square;
import com.mygdx.game.model.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font;
import static com.mygdx.game.Squarz.font2;
import static com.mygdx.game.Squarz.font3;
import static com.mygdx.game.Squarz.format;
import static com.mygdx.game.Squarz.valueVolume;

/**
 * Created by Olivier on 06/03/2018.
 */

public class History extends State {
    Icon back;
    private GlyphLayout title, part1, part2, part3, part4, part5, part6, part6b, part7, part8, part9, part10, part11;
    private Texture sRed, sBlue, sYellow;

    public History(GameStateManager gsm) {
        super(gsm);

        this.title = new GlyphLayout(Squarz.font, "Squarz Tutorial");
        this.part1 = new GlyphLayout(font3, "Squarz has two modes: AI or Multiplayer.");
        this.part2 = new GlyphLayout(font3, "The aim of Squarz is the same in each mode:");
        this.part3 = new GlyphLayout(font3, "Score a maximum of goal!");
        this.part4 = new GlyphLayout(font3, "To do that, you can launch 3 kinds of squares:");
        this.part5 = new GlyphLayout(font3, "Red, Blue or Yellow");
        this.part6 = new GlyphLayout(font3, "Your opponent will do the same, care!");
        this.part6b = new GlyphLayout(font3, "Luckily, you can destroy its squares: Play smart!");
        this.part7 = new GlyphLayout(font3, "If one of your square spends the goal line, you score!");
        this.part8 = new GlyphLayout(font3, "Very important, the strength of each color:");
        this.part9 = new GlyphLayout(font, "<");
        this.part10 = new GlyphLayout(font3, "Some Bonuses are also available, with nice effects...");
        this.part11 = new GlyphLayout(font2, "Have fun, with Squarz !");

        this.sRed = new Texture(Gdx.files.internal(format+"/square/square_red.png"));
        this.sBlue = new Texture(Gdx.files.internal(format+"/square/square_blue.png"));
        this.sYellow = new Texture(Gdx.files.internal(format+"/square/square_yellow.png"));

        this.back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);
        this.back.setPosX(back.getTexture().getWidth()/2);
        this.back.setPosY(back.getTexture().getHeight()/2);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if (back.contains(x,y)) {
                gsm.set(new Menu(gsm));
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
        sb.draw(back.getTexture(),back.getPosX(),back.getPosY());
        Squarz.font.draw(sb, this.title, WIDTH/2 - this.title.width/2, HEIGHT - 2* this.title.height);
        Squarz.font3.draw(sb, this.part1, WIDTH/2 - this.part1.width/2, HEIGHT - 5* this.title.height - this.part1.height);
        Squarz.font3.draw(sb, this.part2, WIDTH/2 - this.part2.width/2, HEIGHT - 5* this.title.height - 2*this.part1.height - this.part2.height);
        Squarz.font3.draw(sb, this.part3, WIDTH/2 - this.part3.width/2, HEIGHT - 5* this.title.height - 2*this.part1.height - 2*this.part2.height - this.part3.height);
        Squarz.font3.draw(sb, this.part4, WIDTH/2 - this.part4.width/2, 3*HEIGHT/4);
        Squarz.font3.draw(sb, this.part5, WIDTH/2 - this.part5.width/2, 3*HEIGHT/4 - 2*this.part5.height);
        Squarz.font3.draw(sb, this.part6, WIDTH/2 - this.part6.width/2, 3*HEIGHT/4 - 2*this.part5.height - 2*this.part6.height);
        Squarz.font3.draw(sb, this.part6b, WIDTH/2 - this.part6b.width/2, 3*HEIGHT/4 - 2*this.part5.height - 2*this.part6.height- 2*this.part6b.height);
        Squarz.font3.draw(sb, this.part7, WIDTH/2 - this.part7.width/2, 3*HEIGHT/4 - 2*this.part5.height - 2*this.part6.height - 2*this.part6b.height- 2*this.part7.height);
        Squarz.font3.draw(sb, this.part8, WIDTH/2 - this.part8.width/2, 11*HEIGHT/20);
        sb.draw(sRed, WIDTH/2 - 3*this.sRed.getWidth() - this.sRed.getWidth()/2,11*HEIGHT/20 - this.sRed.getHeight()/2 - 3*this.part9.height);
        Squarz.font.draw(sb, this.part9, WIDTH/2 - this.part9.width/2, 11*HEIGHT/20 + this.sRed.getHeight()/2 - this.part9.height/2 - 3*this.part9.height);
        sb.draw(sBlue, WIDTH/2 - this.sRed.getWidth() - this.sRed.getWidth()/2,11*HEIGHT/20 - this.sRed.getHeight()/2 - 3*this.part9.height);
        Squarz.font.draw(sb, this.part9, WIDTH/2 - 2*this.sRed.getWidth() - this.part9.width/2, 11*HEIGHT/20 + this.sRed.getHeight()/2 - this.part9.height/2 - 3*this.part9.height);
        sb.draw(sYellow, WIDTH/2 + this.sRed.getWidth() - this.sRed.getWidth()/2,11*HEIGHT/20 - this.sRed.getHeight()/2 - 3*this.part9.height);
        Squarz.font.draw(sb, this.part9, WIDTH/2 + 2*this.sRed.getWidth()- this.part9.width/2, 11*HEIGHT/20 + this.sRed.getHeight()/2 - this.part9.height/2 - 3*this.part9.height);
        sb.draw(sRed, WIDTH/2 + 3*this.sRed.getWidth() - this.sRed.getWidth()/2,11*HEIGHT/20 - this.sRed.getHeight()/2 - 3*this.part9.height);
        Squarz.font3.draw(sb, this.part10, WIDTH/2 - this.part10.width/2, HEIGHT/3);
        Squarz.font2.draw(sb, this.part11, WIDTH/2 - this.part11.width/2, HEIGHT/3 - 3*this.part11.height);


        sb.end();
    }

    @Override
    public void dispose() {
        back.getTexture().dispose();
    }
}
