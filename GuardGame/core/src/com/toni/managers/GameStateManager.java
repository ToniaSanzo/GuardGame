package com.toni.managers;

import com.toni.gamestates.GameOverState;
import com.toni.gamestates.GameState;
import com.toni.gamestates.PlayState;

public class GameStateManager {
    public static final int PLAY      = 333;
    public static final int GAME_OVER = 13;

    private GameState gameState; // Current game state


    /**
     * GameStateManager, initiliazes the GameState to the PlayState
     */
    public GameStateManager(){ setGameState(PLAY); }


    public void setGameState(int state){
        // Dispose current GameState
        if(gameState != null) gameState.dispose();

        // Set GameState
        if(state == PLAY){ gameState = new PlayState(this); }
        if(state == GAME_OVER){ gameState = new GameOverState(this); }
    }


    /**
     * Update the current game state
     *
     * @param dt delta time, time passed since the last update(dt) method call
     */
    public void update(float dt){ gameState.update(dt); }


    /**
     * Draw current game state
     */
    public void draw(){ gameState.draw(); }
}
