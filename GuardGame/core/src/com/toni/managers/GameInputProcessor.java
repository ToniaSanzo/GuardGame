package com.toni.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {


    /**
     * Event handler, called while a key is pressed
     *
     * @param k key
     * @return true
     */
    public boolean keyDown(int k){
        if(k == Input.Keys.W || k == Input.Keys.UP){ GameKeys.setKey(GameKeys.UP, true); }

        if(k == Input.Keys.S || k == Input.Keys.DOWN){ GameKeys.setKey(GameKeys.DOWN, true); }

        if(k == Input.Keys.A || k == Input.Keys.LEFT){ GameKeys.setKey(GameKeys.LEFT, true); }

        if(k == Input.Keys.D || k == Input.Keys.RIGHT){ GameKeys.setKey(GameKeys.RIGHT, true); }

        if(k == Input.Keys.SPACE || k == Input.Keys.ENTER){ GameKeys.setKey(GameKeys.SPACE, true); }

        return true;
    }


    /**
     * Event handler, called when a key is released
     *
     * @param k key
     * @return true
     */
    public boolean keyUp(int k){
        if(k == Input.Keys.W || k == Input.Keys.UP){ GameKeys.setKey(GameKeys.UP, false); }

        if(k == Input.Keys.S || k == Input.Keys.DOWN){ GameKeys.setKey(GameKeys.DOWN, false); }

        if(k == Input.Keys.A || k == Input.Keys.LEFT){ GameKeys.setKey(GameKeys.LEFT, false); }

        if(k == Input.Keys.D || k == Input.Keys.RIGHT){ GameKeys.setKey(GameKeys.RIGHT, false); }

        if(k == Input.Keys.SPACE || k == Input.Keys.ENTER){ GameKeys.setKey(GameKeys.SPACE, false); }

        return true;
    }
}
