package com.toni.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.toni.Game;
import com.toni.managers.GameKeys;
import com.toni.managers.GameStateManager;

public class GameOverState extends GameState {
    SpriteBatch batch;
    BitmapFont font;
    CharSequence str = "[ SPACE ] to play again";

    /**
     * Link GameOverState to GameStateManager
     *
     * @param gsm GameStateManager
     */
    public GameOverState(GameStateManager gsm){
        super(gsm);
    }


    /**
     * init is called when the GameOverState first starts
     */
    public void init(){
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("myfont.fnt"), false);
    }


    /**
     * Update is called during the render() method
     *
     * @param dt Delta Time, time passed since the last update(dt) method call
     */
    public void update(float dt){
        handleInput();
    }


    /**
     * Draws the GameOverState to the scene
     */
    public void draw(){
        batch.begin();
        font.draw(batch, str, Game.WIDTH/4, Game.HEIGHT/2);
        batch.end();

    }


    /**
     * Handles user input
     */
    public void handleInput(){
        if(GameKeys.isPressed(GameKeys.SPACE)){
            gsm.setGameState(GameStateManager.PLAY);
        }
    }


    /**
     * Method called when GameOverState is ended
     */
    public void dispose(){

    }
}