package com.toni.entities;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.toni.Game;
import com.toni.guardstates.GuardState;

public class Guard extends GameObjects {

    private boolean left;       // Move left
    private boolean right;      // Move right
    private boolean up;         // Move up
    private boolean down;       // Move down

    private float maxSpeed;     // Player's speed limit
    private float acceleration; // How fast the player accelerates
    private float deceleration; // How fast the player decelerates
    private float friction;     // Friction

    // An instance of the state machine class
    public StateMachine<Guard, GuardState> stateMachine;


    /**
     * Construct a Guard object, Guard objects start in the "PATROL" state
     */
    public Guard(){
        // Finite state machine used by the guard
        stateMachine = new DefaultStateMachine<Guard, GuardState>(this, GuardState.PATROL);

        // Set the player's position to the center of the top left Coordinate
        x = Game.WIDTH/2;
        y = Game.HEIGHT/2;

        maxSpeed = 160;         // Speed limit set to 180 pixels/second
        acceleration = 300;     // Acceleration set to 250 pixels/second
        deceleration = 250;     // Deceleration set to 220 pixels/second
        friction = 210;         // Friction set to 210 pixels/second

        // Player's 3 vertex polygon
        shapex = new float[3];
        shapey = new float[3];

        // Angle and rotation speed
        radians = 3 * 3.1415f / 2;
        rotationSpeed = 3;
    }


    /**
     * Set the vertices of the Player polygon
     */
    private void setShape(){
        // 0 - index of the vertex at the top of the polygon
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;

        // 1 - index of the vertex at the bottom left of the polygon
        shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * 3.1415f / 5) * 8;

        // 2 - index of the vertex at the bottom right of the polygon
        shapex[2] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 8;
        shapey[2] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 8;
    }


    /**
     * Update the data associated with the Guard Based on the Guards current state
     *
     * @param dt Time passed since the last update(dt) method call
     */
    public void update(float dt){
        // set all the commands to false
        this.clearCommands();

        // State Machine sets left, right, up, and down flags
        stateMachine.update();

        // Update ship based on flags set by the State Machine
        // Rotate the Player based on the keyboard input
        if(left) { radians += rotationSpeed * dt; }
        if(right){ radians -= rotationSpeed * dt; }
        if(this.radians > 6.2832f){ radians = radians % 6.2832f; }
        if(this.radians < 0f){ radians += 6.2832f; }

        // Acceleration, move forward a set amount based on the current angle of the Player polygon
        if(up) {
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;
        }

        // Deceleration, move backward a set amount based on the current angle of the Player polygon
        if(down){
            dx -= MathUtils.cos(radians) * deceleration * dt;
            dy -= MathUtils.sin(radians) * deceleration * dt;
        }

        // Friction
        float vec = (float) Math.sqrt(dx * dx + dy * dy);

        // Friction acts on the player while in motion
        if(!(up || down)){
            dx = 0;
            dy = 0;
        }

        // Cap player's speed
        if(vec > maxSpeed){
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        y += dy * dt;

        // set shape
        setShape();

        // Prevent player moving out of bounds
        boundary();
    }


    /**
     * Draw the Player
     *
     * @param sr ShapeRenderer
     */
    public void draw(ShapeRenderer sr){
        // Color - white
        sr.setColor(1,1,1,1);

        // Drawing happens between the sr.begin and sr.end methods
        sr.begin(ShapeRenderer.ShapeType.Line);
        // Draw Guard
        for(int i = 0, j = shapex.length - 1; i < shapex.length; j = i++){
            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }

        sr.end();
    }


    /**
     * left is updated based on the user interaction
     *
     * @param b boolean
     */
    public void setLeft(boolean b){ left = b; }


    /**
     * right is updated based on the user interaction
     *
     * @param b boolean
     */
    public void setRight(boolean b){ right = b; }


    /**
     * up is updated based on user interaction
     *
     * @param b boolean
     */
    public void setUp(boolean b){ up = b; }


    /**
     * down is updated based on user interaction
     *
     * @param b boolean
     */
    public void setDown(boolean b){ down = b; }


    /**
     * Set all the command flags to false
     */
    public void clearCommands(){
        up = false;
        left = false;
        right = false;
        down = false;
    }
}
