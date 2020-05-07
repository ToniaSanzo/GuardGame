package com.toni.managers;

public class GameKeys {
    private static final int NUM_KEYS = 5;

    private static boolean [] keys = new boolean[NUM_KEYS];
    private static boolean [] prevKeys = new boolean[NUM_KEYS];

    public static final int UP    = 0;
    public static final int DOWN  = 1;
    public static final int LEFT  = 2;
    public static final int RIGHT = 3;
    public static final int SPACE = 4;


    /**
     * Update GameKeys, used to check if a key was just pressed, or has been pressed for a while
     */
    public static void update(){ for(int i = 0; i < NUM_KEYS; i++){ prevKeys[i] = keys[i]; } }


    /**
     * Called by the GameInputProcessor to update the current gamekeys, based on if a key is pressed or released
     *
     * @param k key
     * @param b pressed - true, released - false
     */
    public static void setKey(int k, boolean b){ keys[k] = b; }


    /**
     * Check if a key is currently pressed
     *
     * @param k key
     * @return pressed - true, released - false
     */
    public static boolean isDown(int k){ return keys[k]; }


    /**
     * Check if a key was just pressed
     *
     * @param k key
     * @return just pressed - true, has been pressed OR is released - false
     */
    public static boolean isPressed(int k){ return keys[k] && !prevKeys[k]; }
}
