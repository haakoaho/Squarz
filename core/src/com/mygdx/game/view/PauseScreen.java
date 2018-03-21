package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.model.Icon;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.model.Score;

import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.format;

/**
 * Created by Antoine Dc on 21/03/2018.
 */

public class PauseScreen {
    private Texture texture;
    private Icon resume;
    private GlyphLayout pauseGlyph;


    public PauseScreen() {
        this.texture = new Texture(Gdx.files.internal("rectangle.png"));
        this.resume = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/play.png")), Gdx.graphics.getWidth()/2 , Gdx.graphics.getHeight()/2 );
        this.setResume(new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/play.png"))
                , Gdx.graphics.getWidth()/2 - getResume().getTexture().getWidth()/2
                , Gdx.graphics.getHeight()/2 - getResume().getTexture().getHeight()/2));
        this.pauseGlyph = new GlyphLayout(Squarz.font, "PAUSE");

    }

    public void DrawPause(SpriteBatch sb){
        sb.draw(this.getTexture(), WIDTH/2 - this.getTexture().getWidth()/2
                , HEIGHT/2 - this.getTexture().getHeight()/2);
        sb.draw(this.getResume().getTexture(), WIDTH/2 - this.getResume().getTexture().getWidth()/2
                , HEIGHT/2 - this.getResume().getTexture().getHeight() );
        Squarz.font.draw(sb, pauseGlyph,
                 (WIDTH/2 - pauseGlyph.width/2), HEIGHT/2 + this.getTexture().getHeight()*1/4);




    }


    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public Icon getResume() {
        return resume;
    }
    public void setResume(Icon resume) {
        this.resume = resume;
    }
    public GlyphLayout getPauseGlyph() {
        return pauseGlyph;
    }
    public void setPauseGlyph(GlyphLayout pauseGlyph) {
        this.pauseGlyph = pauseGlyph;
    }

}
