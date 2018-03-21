package com.mygdx.game.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MultiPlayer {
    private Player opponent;
    private Texture texture;
    private Square square;
    private Integer launcherCounter;
    private Integer deltaLauncher;
    private Integer renderCounter;


    public MultiPlayer (){
        this.opponent = new Player();
        this.texture = new Texture (Gdx.files.internal("square.png"));
        this.square = new Square();

        this.launcherCounter = 0;
        this.deltaLauncher = 70;

        this.renderCounter = 0;
    }

    public void send(){


            }
        }