package com.toni.guardstates;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.toni.Game;
import com.toni.entities.Guard;
import com.toni.gamestates.PlayState;

public enum GuardState implements State<Guard> {
    
    PATROL(){
        Vector2 desired = null;
        float desiredAngle;


        /**
         * Moves Guard inside building, than to the next patrol point
         *
         * @param guard a.i. GameObject
         */
        @Override
        public void update(Guard guard){

            // If outside, go inside
            if (!inBuilding(guard.getVector())) {
                desired = posA;
                if(guard.getVector().dst(posB) < guard.getVector().dst(desired)){ desired = posB; }
                if(guard.getVector().dst(posC) < guard.getVector().dst(desired)){ desired = posC; }
                if(guard.getVector().dst(posD) < guard.getVector().dst(desired)){ desired = posD; }

                desired = new Vector2(desired.x - guard.getVector().x, desired.y - guard.getVector().y);
                // desiredAngle = rotation the Guard must rotate to align with the desired vector
                desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                        MathUtils.sin(guard.getRadians())));

                // If the desired angle is small enough stop rotating
                if(desiredAngle < .12 && desiredAngle > -.12){ guard.setUp(true); }

                // If desiredAngle is negative rotate to the left
                if(desiredAngle < 0){
                    guard.setLeft(true);
                } else {
                    guard.setRight(true);
                }
                return;
            }

            // Go to current patrol corner
            switch(currentCorner){
                case A:
                    if(inA(guard.getVector())){
                        guard.stateMachine.changeState(SCAN);
                    }
                    desired = new Vector2(posA.x - guard.getVector().x, posA.y - guard.getVector().y);

                    // desiredAngle = rotation the Guard must rotate to align with the desired vector
                    desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                            MathUtils.sin(guard.getRadians())));

                    // If the desired angle is small enough stop rotating
                    if(desiredAngle < .1 && desiredAngle > -.1){
                        guard.setUp(true);
                    }

                    // If desiredAngle is negative rotate to the left
                    if(desiredAngle < 0){
                        guard.setLeft(true);
                    } else {
                        guard.setRight(true);
                    }

                    break;
                case B:
                    if(inB(guard.getVector())){
                        guard.stateMachine.changeState(SCAN);
                    }
                    desired = new Vector2(posB.x - guard.getVector().x, posB.y - guard.getVector().y);


                    // desiredAngle = rotation the Guard must rotate to align with the desired vector
                    desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                            MathUtils.sin(guard.getRadians())));

                    // If the desired angle is small enough stop rotating
                    if(desiredAngle < .2 && desiredAngle > -.2){
                        guard.setUp(true);
                    }

                    // If desiredAngle is negative rotate to the left
                    if(desiredAngle < 0){
                        guard.setLeft(true);
                    } else {
                        guard.setRight(true);
                    }

                    break;
                case C:
                    if(inC(guard.getVector())){
                        guard.stateMachine.changeState(SCAN);
                    }
                    desired = new Vector2(posC.x - guard.getVector().x, posC.y - guard.getVector().y);


                    // desiredAngle = rotation the Guard must rotate to align with the desired vector
                    desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                            MathUtils.sin(guard.getRadians())));

                    // If the desired angle is small enough stop rotating
                    if(desiredAngle < .2 && desiredAngle > -.2){
                        guard.setUp(true);
                    }

                    // If desiredAngle is negative rotate to the left
                    if(desiredAngle < 0){
                        guard.setLeft(true);
                    } else {
                        guard.setRight(true);
                    }

                    break;
                case D:
                    if(inD(guard.getVector())){
                        guard.stateMachine.changeState(SCAN);
                    }
                    desired = new Vector2(posD.x - guard.getVector().x, posD.y - guard.getVector().y);


                    // desiredAngle = rotation the Guard must rotate to align with the desired vector
                    desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                            MathUtils.sin(guard.getRadians())));

                    // If the desired angle is small enough stop rotating
                    if(desiredAngle < .2 && desiredAngle > -.2){
                        guard.setUp(true);
                    }

                    // If desiredAngle is negative rotate to the left
                    if(desiredAngle < 0){
                        guard.setLeft(true);
                    } else {
                        guard.setRight(true);
                    }

                    break;
            }
        }
    },
    SCAN(){
        boolean pointChecked;
        float desiredAngle;
        Vector2 desired;
        long systemTime;

        @Override
        public void enter(Guard guard){
            systemTime  = -1;
            pointChecked = false;
        }

        @Override
        public void update(Guard guard){

            switch (currentCorner){
                case A:
                    // If the first point has not been scanned yet rotate to the first point
                    if(!pointChecked){
                        desired = new Vector2(0, 1);

                        // desiredAngle = rotation the Guard must rotate to align with the desired vector
                        desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                                MathUtils.sin(guard.getRadians())));

                        // If the desired angle is small enough stop rotating
                        if(desiredAngle < .01 && desiredAngle > -.01){
                            if(systemTime == -1){
                                systemTime = System.currentTimeMillis();
                            }
                            else {
                                if(System.currentTimeMillis() - systemTime > 500) {
                                    systemTime = System.currentTimeMillis();
                                    pointChecked = true;
                                    return;
                                }
                            }
                        }

                        // If desiredAngle is negative rotate to the left
                        if(desiredAngle < 0){
                            guard.setLeft(true);
                        } else {
                            guard.setRight(true);
                        }
                    } else {
                        desired = new Vector2(-1, 0);

                        // desiredAngle = rotation the Guard must rotate to align with the desired vector
                        desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                                MathUtils.sin(guard.getRadians())));

                        // If the desired angle is small enough stop rotating
                        if(desiredAngle < .01 && desiredAngle > -.01){
                            if(systemTime == -1){
                                systemTime = System.currentTimeMillis();
                            }
                            else {
                                if(System.currentTimeMillis() - systemTime > 500) {
                                    systemTime = -1;
                                    pointChecked  = false;
                                    currentCorner = B;
                                    guard.stateMachine.changeState(PATROL);
                                    return;
                                }
                            }
                        }

                        // If desiredAngle is negative rotate to the left
                        if(desiredAngle < 0){
                            guard.setLeft(true);
                        } else {
                            guard.setRight(true);
                        }
                    }
                    break;
                case B:
                    // If the first point has not been scanned yet rotate to the first point
                    if(!pointChecked){
                        desired = new Vector2(0, 1);

                        // desiredAngle = rotation the Guard must rotate to align with the desired vector
                        desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                                MathUtils.sin(guard.getRadians())));

                        // If the desired angle is small enough stop rotating
                        if(desiredAngle < .01 && desiredAngle > -.01){
                            if(systemTime == -1){
                                systemTime = System.currentTimeMillis();
                            }
                            else {
                                if(System.currentTimeMillis() - systemTime > 500) {
                                    systemTime = -1;
                                    pointChecked = true;
                                    return;
                                }
                            }
                        }

                        // If desiredAngle is negative rotate to the left
                        if(desiredAngle < 0){
                            guard.setLeft(true);
                        } else {
                            guard.setRight(true);
                        }
                    } else {
                        desired = new Vector2(1, 0);

                        // desiredAngle = rotation the Guard must rotate to align with the desired vector
                        desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                                MathUtils.sin(guard.getRadians())));

                        // If the desired angle is small enough stop rotating
                        if(desiredAngle < .01 && desiredAngle > -.01){
                            if(systemTime == -1){
                                systemTime = System.currentTimeMillis();
                            }
                            else {
                                if(System.currentTimeMillis() - systemTime > 500) {
                                    systemTime = -1;
                                    pointChecked  = false;
                                    currentCorner = D;
                                    guard.stateMachine.changeState(PATROL);
                                    return;
                                }
                            }
                        }

                        // If desiredAngle is negative rotate to the left
                        if(desiredAngle < 0){
                            guard.setLeft(true);
                        } else {
                            guard.setRight(true);
                        }
                    }
                    break;
                case C:
                    // If the first point has not been scanned yet rotate to the first point
                    if(!pointChecked){
                        desired = new Vector2(0, -1);

                        // desiredAngle = rotation the Guard must rotate to align with the desired vector
                        desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                                MathUtils.sin(guard.getRadians())));

                        // If the desired angle is small enough stop rotating
                        if(desiredAngle < .01 && desiredAngle > -.01){
                            if(systemTime == -1){
                                systemTime = System.currentTimeMillis();
                            }
                            else {
                                if(System.currentTimeMillis() - systemTime > 500) {
                                    systemTime = -1;
                                    pointChecked = true;
                                    return;
                                }
                            }
                        }

                        // If desiredAngle is negative rotate to the left
                        if(desiredAngle < 0){
                            guard.setLeft(true);
                        } else {
                            guard.setRight(true);
                        }
                    } else {
                        desired = new Vector2(-1, 0);

                        // desiredAngle = rotation the Guard must rotate to align with the desired vector
                        desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                                MathUtils.sin(guard.getRadians())));

                        // If the desired angle is small enough stop rotating
                        if(desiredAngle < .01 && desiredAngle > -.01){
                            if(systemTime == -1){
                                systemTime = System.currentTimeMillis();
                            }
                            else {
                                if(System.currentTimeMillis() - systemTime > 500) {
                                    systemTime = -1;
                                    pointChecked  = false;
                                    currentCorner = A;
                                    guard.stateMachine.changeState(PATROL);
                                    return;
                                }
                            }
                        }

                        // If desiredAngle is negative rotate to the left
                        if(desiredAngle < 0){
                            guard.setLeft(true);
                        } else {
                            guard.setRight(true);
                        }
                    }
                    break;
                case D:
                    // If the first point has not been scanned yet rotate to the first point
                    if(!pointChecked){
                        desired = new Vector2(0, -1);

                        // desiredAngle = rotation the Guard must rotate to align with the desired vector
                        desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                                MathUtils.sin(guard.getRadians())));

                        // If the desired angle is small enough stop rotating
                        if(desiredAngle < .01 && desiredAngle > -.01){
                            if(systemTime == -1){
                                systemTime = System.currentTimeMillis();
                            }
                            else {
                                if(System.currentTimeMillis() - systemTime > 500) {
                                    systemTime = -1;
                                    pointChecked = true;
                                    return;
                                }
                            }
                        }

                        // If desiredAngle is negative rotate to the left
                        if(desiredAngle < 0){
                            guard.setLeft(true);
                        } else {
                            guard.setRight(true);
                        }
                    } else {
                        desired = new Vector2(1, 0);

                        // desiredAngle = rotation the Guard must rotate to align with the desired vector
                        desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                                MathUtils.sin(guard.getRadians())));

                        // If the desired angle is small enough stop rotating
                        if(desiredAngle < .01 && desiredAngle > -.01){
                            if(systemTime == -1){
                                systemTime = System.currentTimeMillis();
                            }
                            else {
                                if(System.currentTimeMillis() - systemTime > 500) {
                                    systemTime = -1;
                                    pointChecked  = false;
                                    currentCorner = C;
                                    guard.stateMachine.changeState(PATROL);
                                    return;
                                }
                            }
                        }

                        // If desiredAngle is negative rotate to the left
                        if(desiredAngle < 0){
                            guard.setLeft(true);
                        } else {
                            guard.setRight(true);
                        }
                    }
                    break;
            }
        }
    },
    CHASE(){
        Vector2 desired = null;
        float desiredAngle;

        @Override
        public void update(Guard guard){
            // update the chasing guard
            if(!PlayState.getPlayerVisible()){
                guard.stateMachine.changeState(PATROL);
                return;
            }

            desired = new Vector2(playerPos.x - guard.getVector().x, playerPos.y - guard.getVector().y);

            // desiredAngle = rotation the Guard must rotate to align with the desired vector
            desiredAngle = desired.angleRad(new Vector2(MathUtils.cos(guard.getRadians()),
                    MathUtils.sin(guard.getRadians())));

            // If the desired angle is small enough stop rotating
            if(desiredAngle < .85 && desiredAngle > -.85){ guard.setUp(true); }

            // If desiredAngle is negative rotate to the left
            if(desiredAngle < 0){
                guard.setLeft(true);
            } else {
                guard.setRight(true);
            }
        }
    };
    // Represent the four patrol corners
    private static final int A = 0;
    private static final int B = 1;
    private static final int C = 2;
    private static final int D = 3;

    // Building boundary coordinates
    private static float x1 = Game.WIDTH * 2 / 7, x2 = Game.WIDTH * 5 / 7;
    private static float y1 = Game.HEIGHT * 1 / 3, y2 = Game.HEIGHT * 2 / 3;
    private static float pauseTime = 1.1f;
    private static Vector2 posA      = new Vector2(x1 - 2f, y2 + 2f);
    private static Vector2 posB      = new Vector2(x2 + 2f, y2 + 2f);
    private static Vector2 posC      = new Vector2(x1 - 2f, y1 - 2f);
    private static Vector2 posD      = new Vector2(x2 + 2f, y1 - 2f);
    private static Vector2 playerPos = null;
    private static int currentCorner = A;

    @Override
    public void enter(Guard guard){}


    @Override
    public void exit(Guard guard){}


    @Override
    public boolean onMessage(Guard guard, Telegram telegram){
        return false;
    }


    /**
     * True - vector's in building, False - vector's outside building
     *
     * @param vec 2d vector
     * @return boolean
     */
    public static boolean inBuilding(Vector2 vec){
        // Check if in building coordinates
        if((vec.x > x1 - 15 && vec.x < x2 + 15) && (vec.y > y1 - 15 && vec.y < y2 + 15)){ return true; }
        return false;
    }


    /**
     * True - vector's in patrol position A, False - vector's outside patrol position A
     *
     * @param vec 2d vector
     * @return boolean
     */
    public static boolean inA(Vector2 vec){
        // Check if in patrol position A
        if((vec.x > x1 - 15 && vec.x < x1 + 15) && (vec.y > y2 - 15 && vec.y < y2 + 15)){ return true; }
        return false;
    }


    /**
     * True - vector's in patrol position B, False - vector's outside patrol position B
     *
     * @param vec 2d vector
     * @return boolean
     */
    public static boolean inB(Vector2 vec){
        // Check if in patrol position B
        if((vec.x > x2 - 15 && vec.x < x2 + 15) && (vec.y > y2 - 15 && vec.y < y2 + 15)){ return true; }
        return false;
    }


    /**
     * True - vector's in patrol position C, False - vector's outside patrol position C
     *
     * @param vec 2d vector
     * @return boolean
     */
    public static boolean inC(Vector2 vec){
        // Check if in patrol position C
        if((vec.x > x1 - 15 && vec.x < x1 + 15) && (vec.y > y1 - 15 && vec.y < y1 + 15)){ return true; }
        return false;
    }


    /**
     * True - vector's in patrol position D, False - vector's outside patrol position D
     *
     * @param vec 2d vector
     * @return boolean
     */
    public static boolean inD(Vector2 vec){
        // Check if in patrol position A
        if((vec.x > x2 - 15 && vec.x < x2 + 15) && (vec.y > y1 - 15 && vec.y < y1 + 15)){ return true; }
        return false;
    }


    /**
     * Update the player position
     *
     * @param pos (Vector2) - xy-coordinate of player's last seen position
     */
    public static void setPlayerPos(Vector2 pos){ playerPos = pos; }
}