package com.toni.entities;

import com.badlogic.gdx.math.Vector2;
import com.toni.Game;

public class GameObjects {
    private static final float x1 = (Game.WIDTH * 2 / 7) - 15, x2 = (Game.WIDTH * 5 / 7) + 15;
    private static final float y1 = (Game.HEIGHT * 1 / 3) - 15, y2 = (Game.HEIGHT * 2 / 3) + 15;

    protected float x;             // x             - coordinate of GameObject
    protected float y;             // y             - coordinate of GameObject
    protected float dx;            // dx            - direction GameObject is moving (horizontally)
    protected float dy;            // dy            - direction GameObject is moving (vertically)
    protected float radians;       // radians       - angle GameObject's facing
    protected float speed;         // speed         - GameOjects current speed
    protected float rotationSpeed; // rotationSpeed - rotation speed
    protected int width;           // width         - GameObjects width
    protected int height;          // height        - GameObjects height
    protected float[] shapex;      // An array of vertices
    protected float[] shapey;      // An array of vertices



    /**
     * If the GameObject will not be able to move outside the boundaries of the game
     */
    protected void boundary(){
        // prevent object from moving outside of the screen
        if(x < 0){
            x = 0;
            dx = 0;
            return;
        }
        if(x > Game.WIDTH){
            x = Game.WIDTH;
            dx = 0;
            return;
        }
        if(y < 0){
            y = 0;
            dy = 0;
            return;
        }
        if(y > Game.HEIGHT){
            y = Game.HEIGHT;
            dy = 0;
            return;
        }

        // prevent object from moving through left wall
        if((x < x1 + 7 && x > x1) && (y > y1 + 30 && y < y2 - 30)){
            x = x1 + 8;
            dx = -1 * dx / 2;
            return;
        }
        if((x > x1 - 7 && x < x1) && (y > y1 + 30 && y < y2 - 30)){
            x = x1 - 8;
            dx = -1 * dx / 2;
            return;
        }

        // prevent object from moving through right wall
        if((x < x2 + 7 && x > x2) && (y > y1 + 30 && y < y2 - 30)){
            x = x2 + 8;
            dx = -1 * dx / 2;
            return;
        }
        if((x > x2 - 7 && x < x2) && (y < y2 - 30 && y > y1 + 30)){
            x = x2 - 8;
            dx = -1 * dx / 2;
            return;
        }

        // prevent object from moving through top wall
        if((y < y2 + 7 && y > y2) && (x > x1 + 30 && x < x2 - 30)){
            y = y2 + 8;
            dy = -1 * dy / 2;
            return;
        }
        if((y > y2 - 7 && y < y2) && (x < x2 - 30 && x > x1 + 30)){
            y = y2 - 8;
            dy = -1 * dy / 2;
            return;
        }

        // prevent object from moving through bottom wall
        if((y < y1 + 7 && y > y1) && (x > x1 + 30 && x < x2 - 30)){
            y = y1 + 8;
            dy = -1 * dy / 2;
            return;
        }
        if((y > y1 - 7 && y < y1) && (x < x2 - 30 && x > x1 + 30)){
            y = y1 - 8;
            dy = -1 * dy / 2;
            return;
        }
    }


    /**
     * @return x-coordinate
     */
    public float getX(){ return x; }


    /**
     * @return y-coordinate
     */
    public float getY(){ return y; }


    /**
     * @return angle the GameObject is facing
     */
    public float getRadians(){ return radians; }


    /**
     * @return GameObjects 2-D Vector
     */
    public Vector2 getVector(){ return new Vector2(x, y); }


    /**
     * Set x-coordinate
     *
     * @param x x-coordinate
     */
    public void setX(int x){ this.x = x; }


    /**
     * Set y-coordinate
     *
     * @param y y-coordinate
     */
    public void setY(int y){ this.y = y; }


    /**
     * Set radians
     *
     * @param radians (float) - radians
     */
    public void setRadians(float radians){ this.radians = radians; }


    /**
     * @return shapex
     */
    public float[] getShapex(){ return shapex; }


    /**
     * @return shapey
     */
    public float[] getShapey(){ return shapey; }
}
