package com.toni.gamestates;

import com.toni.managers.GameStateManager;

public abstract class GameState {
    protected GameStateManager gsm;

    /**
     * Constructor, sets the GameStateManager, and initializes the current GameState
     *
     * @param gsm GameStateManager
     */
    protected GameState(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }


    /**
     * init is called when the GameState first starts up
     */
    public abstract void init();


    /**
     * Update is called during the render() method
     *
     * @param dt Delta Time, time passed since the last update(dt) method call
     */
    public abstract void update(float dt);


    /**
     * Draws the GameState to the scene
     */
    public abstract void draw();


    /**
     * Handles user input
     */
    public abstract void handleInput();


    /**
     * Method called when a GameState is ended
     */
    public abstract void dispose();
}