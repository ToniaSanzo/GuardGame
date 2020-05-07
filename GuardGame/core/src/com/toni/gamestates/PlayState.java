package com.toni.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.toni.Game;
import com.toni.entities.Guard;
import com.toni.entities.Player;
import com.toni.entities.Ray;
import com.toni.entities.Wall;
import com.toni.guardstates.GuardState;
import com.toni.managers.GameKeys;
import com.toni.managers.GameStateManager;

public class PlayState extends GameState {
    // NUMB_RAYS is the number of ray's, should be even
    private static final int NUMB_RAYS = 1024;
    private static boolean playerVisible = false;

    private ShapeRenderer sr;                           // Used for drawing entities in the scene
    private Player player;                              // The player
    private Guard guard;                                // The guard
    private Wall[] walls;                               // The walls
    private Ray[] rays;                                 // The vision rays
    private Vector2[] collPt;                           // Collision Points
    private boolean vision = false;                     // Whether to display the vision rays


    /**
     * Link PlayState to the GameStateManager
     *
     * @param gsm GameStateManager, allows switching of GameStates
     */
    public PlayState(GameStateManager gsm){
        super(gsm);
    }


    /**
     * init() initialize's the PlayState
     */
    public void init(){
        float x1 = (Game.WIDTH * 2 / 7), x2 = (Game.WIDTH * 5 / 7);
        float y1 = (Game.HEIGHT * 1 / 3), y2 = (Game.HEIGHT * 2 / 3);

        sr = new ShapeRenderer(); // Initialize ShapeRenderer
        player = new Player();    // Initialize Player
        guard = new Guard();      // Initialize Guard
        walls = new Wall[4];      // Initialize Walls
        walls[0] = new Wall(new Vector2(x1 - 15, y1 + 15), new Vector2(x1 - 15, y2 - 15));
        walls[1] = new Wall(new Vector2( x1 + 15, y2 + 15), new Vector2(x2 - 15, y2 + 15));
        walls[2] = new Wall(new Vector2(x2 + 15, y2 - 15), new Vector2(x2 + 15, y1 + 15));
        walls[3] = new Wall(new Vector2(x1 + 15, y1 - 15), new Vector2(x2 - 15, y1 - 15));
        collPt = new Vector2[4];

        // Initialize guard's vision rays
        rays = new Ray[NUMB_RAYS];
        for(int i = 0; i < NUMB_RAYS; i++) {
            rays[i] = new Ray(guard.getVector(), (i * (6.2832f / NUMB_RAYS)));
        }
    }


    /**
     * Update data of objects currently in the PlayState
     *
     * @param dt Delta Time, time passed since the last update(dt) method call
     */
    public void update(float dt){
        handleInput();      // Handle user input

        player.update(dt);  // Update player
        guard.update(dt);   // Update guard
        if(player.getVector().dst(guard.getVector()) < 15){ gsm.setGameState(GameStateManager.GAME_OVER); }


        playerVisible = false;
        // Update rays
        for(int i = 0; i < NUMB_RAYS; i += 4){
            rays[i].update(guard, (i * (6.28319f / NUMB_RAYS)));
            rays[i + 1].update(guard, (i * (6.2832f / NUMB_RAYS)));
            rays[i + 2].update(guard, (i * (6.2832f / NUMB_RAYS)));
            rays[i + 3].update(guard, (i * (6.2832f / NUMB_RAYS)));


            // check collision points
            collPt[0] = cast(rays[i]);
            collPt[1] = cast(rays[i + 1]);
            collPt[2] = cast(rays[i + 2]);
            collPt[3] = cast(rays[i + 3]);


            if(collPt[0] != null) { rays[i].setCollisionPoint(collPt[0]); }
            else { rays[i].resetCollisionPoint();}

            if(collPt[1] != null) { rays[i + 1].setCollisionPoint(collPt[1]); }
            else { rays[i + 1].resetCollisionPoint(); }

            if(collPt[2] != null) { rays[i + 2].setCollisionPoint(collPt[2]); }
            else { rays[i + 2].resetCollisionPoint(); }

            if(collPt[3] != null) { rays[i + 3].setCollisionPoint(collPt[3]); }
            else { rays[i + 3].resetCollisionPoint(); }
        }
    }


    /**
     * Draw/Display the current PlayState
     */
    public void draw(){
        player.draw(sr);   // Draw Player
        guard.draw(sr);    // Draw Guard

        for(Wall wall: walls){ wall.draw(sr); }

        if(vision) {
            sr.begin(ShapeRenderer.ShapeType.Line);
            // Draw Guards vision
            for (int j = 0; j < NUMB_RAYS; j += 4) { rays[j].draw(sr); }
            sr.end();
        }
    }


    /**
     * Update player's actions based on the GameKeys
     */
    public void handleInput(){
        // UP
        player.setUp(GameKeys.isDown(GameKeys.UP));

        // DOWN
        player.setDown(GameKeys.isDown(GameKeys.DOWN));

        // LEFT
        player.setLeft(GameKeys.isDown(GameKeys.LEFT));

        // RIGHT
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));

        if(GameKeys.isPressed(GameKeys.SPACE)){
            toggleVision();
        }
    }


    /**
     * Method called when PlayState is finished
     */
    public void dispose(){}


    /**
     * Check to see if the ray intersects with a GameObject
     *
     * @param ray (Ray) - Ray being checked for line intersection
     * @return Coordinate's of an intersection, or null if no intersection
     */
    public Vector2 cast(Ray ray){
        Vector2 closestPt = null; // Return vector
        Vector2 collPt;           // If a collision occurs
        Vector2 p1, p2;           // line endpoint coordinates
        float t, u;               // t and u value used in line-line intersection
        float x1, x2;             // Coordinates of the wall
        float y1, y2;             // Coordinates of the wall

        // Check the ray against each wall
        for(Wall wall: walls){
            p1 = wall.getP1();
            p2 = wall .getP2();
            t = getT(p1, p2, ray);
            u = getU(p1, p2, ray);

            // return a vector if a wall collision occurs
            if((t > 0 && t < 1) && (u > 0 && u < 1)){
                x1 = p1.x;
                x2 = p2.x;
                y1 = p1.y;
                y2 = p2.y;
                collPt = new Vector2(x1 + (t * (x2 - x1)), y1 + (t * (y2 - y1)));

                // update closestPt
                if(closestPt == null){
                    closestPt = collPt;
                }

                if(ray.getPos().dst(collPt) <= ray.getPos().dst(closestPt)){
                    closestPt = collPt;
                }
            }
        }

        // Check the ray against the player
        for(int i = 0, j = player.getShapex().length - 1; i < player.getShapex().length; j = i++){
            p1 = new Vector2(player.getShapex()[i], player.getShapey()[i]);
            p2 = new Vector2(player.getShapex()[j], player.getShapey()[j]);

            t = getT(p1, p2, ray);
            u = getU(p1, p2, ray);

            // return a vector if a wall collision occurs
            if((t > 0 && t < 1) && (u > 0 && u < 1)){
                x1 = p1.x;
                x2 = p2.x;
                y1 = p1.y;
                y2 = p2.y;
                collPt = new Vector2(x1 + (t * (x2 - x1)), y1 + (t * (y2 - y1)));

                // update closestPt
                if(closestPt == null){
                    closestPt = collPt;
                    playerVisible = true;
                    guard.stateMachine.changeState(GuardState.CHASE);
                    GuardState.setPlayerPos(player.getVector());
                }

                if(ray.getPos().dst(collPt) < ray.getPos().dst(closestPt)){
                    closestPt = collPt;
                    playerVisible = true;
                    guard.stateMachine.changeState(GuardState.CHASE);
                    GuardState.setPlayerPos(player.getVector());
                }
            }
        }

        return closestPt;
    }


    /**
     * Return t-value used in line-line intersection
     *
     * @param p1 (Vector2) - First xy-coordinate of line
     * @param p2 (Vector2) - Second xy-coordinate of line
     * @param ray (Ray) - Ray being checked for line intersection
     * @return (float) - t
     */
    public float getT(Vector2 p1, Vector2 p2, Ray ray){
        float x1 = p1.x, x2 = p2.x, x3 = ray.getPos().x, x4 = ray.getPos().x + ray.getAngle().x;
        float y1 = p1.y, y2 = p2.y, y3 = ray.getPos().y, y4 = ray.getPos().y + ray.getAngle().y;
        float numerator = ((x1 - x3) * (y3 - y4)) - ((y1 - y3) * (x3 - x4));
        float denominator = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));

        // Denominator is 0 check
        if(denominator < .0001f && denominator > -.0001f){ return Float.MAX_VALUE; }

        return numerator/denominator;
    }


    /**
     * Return u-value in line-line intersection
     *
     * @param p1 (Vector2) - First xy-coordinate of line
     * @param p2 (Vector2) - Second xy-coordinate of line
     * @param ray (Ray) - Ray being checked for line intersection
     * @return (float) - u
     */
    public float getU(Vector2 p1, Vector2 p2, Ray ray){
        float x1 = p1.x, x2 = p2.x, x3 = ray.getPos().x, x4 = ray.getPos().x + ray.getAngle().x;
        float y1 = p1.y, y2 = p2.y, y3 = ray.getPos().y, y4 = ray.getPos().y + ray.getAngle().y;
        float numerator = ((x1 - x2) * (y1 - y3)) - ((y1 - y2) * (x1 - x3));
        float denominator = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));

        // Denominator is 0 check
        if(denominator < .0001f && denominator > -.0001f){ return Float.MAX_VALUE; }

        return -1f * (numerator/denominator);
    }


    /**
     * Toggle vision boolean flag between true and false
     */
    public void toggleVision(){
        if(vision){
            vision = false;
        } else {
            vision = true;
        }
    }


    /**
     * Check if the player's visible to the guard
     *
     * @return (boolean) - true if visible, false if not visible
     */
    public static boolean getPlayerVisible(){ return playerVisible;}
}