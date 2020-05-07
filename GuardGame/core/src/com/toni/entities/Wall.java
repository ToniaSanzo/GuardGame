package com.toni.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Wall {
    // Wall's endpoint coordinates
    private Vector2 p1, p2;


    /**
     * Construct a Wall giving the coordinates
     *
     * @param p1 (Vector2) - xy-coordinates of endpoint 1
     * @param p2 (Vector2) - xy-ccordinates of endpoint 2
     */
    public Wall(Vector2 p1, Vector2 p2){
        this.p1 = p1;
        this.p2 = p2;
    }


    /**
     * Draws the wall
     *
     * @param sr (ShapeRenderer) - Draws wall
     */
    public void draw(ShapeRenderer sr){
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.line(p1, p2);
        sr.end();

    }


    /**
     * Get first xt-coordinate of the wall
     *
     * @return (Vector2) - xy-coordinate of the wall
     */
    public Vector2 getP1(){ return p1; }


    /**
     * Get second xy-coordinate of the wall
     *
     * @return (Vector2) - xy-coordinate of the wall
     */
    public Vector2 getP2(){ return p2; }
}
