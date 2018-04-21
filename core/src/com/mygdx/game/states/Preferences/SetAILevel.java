package com.mygdx.game.states.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Squarz;
import com.mygdx.game.model.ai_settings.PreferencesSettings;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;
import com.mygdx.game.model.other.Icon;
import com.mygdx.game.gameStateManager.GameStateManager;
import com.mygdx.game.gameStateManager.State;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.font2;
import static com.mygdx.game.Squarz.format;

// CLEAN //
public class SetAILevel extends State{
    private final Icon add;
    private final Icon delete;
    private final Icon levelToDraw;
    private final Icon back;
    private final PreferencesSettings settings;
    private final ICountdownDuration countDown;
    private GlyphLayout choose;
    private final GlyphLayout levelTitle;


    public SetAILevel(GameStateManager gsm, PreferencesSettings settings, ICountdownDuration countDown){
        super(gsm);

        this.settings = settings;
        this.countDown = countDown;

        this.add = new Icon(new Texture(Gdx.files.internal(format+"/add.png")),0,0); // to increase the level of the AI (until Expert)
        this.delete = new Icon(new Texture(Gdx.files.internal(format+"/delete.png")),0,0); // to decrease the level of the AI (until Beginner)
        this.levelToDraw = new Icon(new Texture(Gdx.files.internal(format+"/ai_settings/ai_levels/beginer.png")),0,0);
        this.back = new Icon(new Texture(Gdx.files.internal(format+"/back.png")),0,0);

        this.add.setPosX(WIDTH/2-this.add.getTexture().getWidth()/2);
        this.add.setPosY(HEIGHT*2/3-this.add.getTexture().getHeight()/2);
        this.delete.setPosX(WIDTH/2-this.delete.getTexture().getWidth()/2);
        this.delete.setPosY(HEIGHT/3-this.delete.getTexture().getHeight()/2);
        this.levelToDraw.setPosX(WIDTH/2-this.levelToDraw.getTexture().getWidth()/2);
        this.levelToDraw.setPosY(HEIGHT/2-this.levelToDraw.getTexture().getHeight()/2);
        this.back.setPosX(this.back.getTexture().getWidth()/2);
        this.back.setPosY(this.back.getTexture().getHeight()/2);

        this.levelTitle = new GlyphLayout(Squarz.font, "CHOOSE AI LEVEL");
        this.choose = new GlyphLayout(font2,this.settings.getStringLevel());

        setTextureToDraw();
    }
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = HEIGHT - Gdx.input.getY();
            if(this.add.contains(x,y)){ //add
                this.settings.AILevelUp();
                this.choose = new GlyphLayout(font2,this.settings.getStringLevel());
                setTextureToDraw();
            }
            if(this.delete.contains(x,y)){ //delete
                this.settings.AILevelDown();
                this.choose = new GlyphLayout(font2,this.settings.getStringLevel());
                setTextureToDraw();
            }
            if(this.levelToDraw.contains(x,y)){//go back
                gsm.set(new AIPreferences(gsm, settings, countDown));
            }
            if(this.back.contains(x,y)) {
                gsm.set(new AIPreferences(gsm, settings, countDown));
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
        Squarz.font.draw(sb, this.levelTitle, WIDTH/2 - this.levelTitle.width/2,HEIGHT-2*this.levelTitle.height);
        font2.draw(sb, this.choose, WIDTH/2 - this.choose.width/2, 8*HEIGHT/10 - this.choose.height/2);
        sb.draw(this.add.getTexture(), this.add.getPosX(),this.add.getPosY() );
        sb.draw(this.delete.getTexture(),this.delete.getPosX() ,this.delete.getPosY() );
        sb.draw(this.levelToDraw.getTexture(),this.levelToDraw.getPosX() , this.levelToDraw.getPosY());
        sb.draw(this.back.getTexture(),this.back.getPosX(),this.back.getPosY());
        sb.end();
    }

    @Override
    public void dispose() {
    }

    private void setTextureToDraw(){ // draw the good texture according to the level, to give a good visual experience
        if(this.settings.getLevelKey()==0){
            this.levelToDraw.setTexture(new Texture(Gdx.files.internal(format+"/ai_settings/ai_levels/beginer.png")));
        }
        if(this.settings.getLevelKey()==1){
            this.levelToDraw.setTexture(new Texture(Gdx.files.internal(format+"/ai_settings/ai_levels/medium.png")));
        }
        if(this.settings.getLevelKey()==2){
            this.levelToDraw.setTexture(new Texture(Gdx.files.internal(format+"/ai_settings/ai_levels/advanced.png")));
        }
        if(this.settings.getLevelKey()==3){
            this.levelToDraw.setTexture(new Texture(Gdx.files.internal(format+"/ai_settings/ai_levels/expert.png")));
        }
    }
}