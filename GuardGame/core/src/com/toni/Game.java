package com.toni;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.toni.managers.GameInputProcessor;
import com.toni.managers.GameKeys;
import com.toni.managers.GameStateManager;

public class Game extends ApplicationAdapter {

	public static int WIDTH;                         // Width of the Game universe
	public static int HEIGHT;                        // Height of the Game universe
	public static OrthographicCamera cam;            // Camera used to view the game universe
	public static GameStateManager gsm;              // GameStateManager, used to switch between different GameState's

	@Override
	public void create () {
		WIDTH  = Gdx.graphics.getWidth();                       // Set WIDTH on startup
		HEIGHT = Gdx.graphics.getHeight();                      // Set HEIGHT on startup

		cam = new OrthographicCamera(WIDTH, HEIGHT);            // Specify camera dimensions
		cam.translate(WIDTH/2, HEIGHT/2);                // Move Camera Right: WIDTH/2, Move Camera Up: HEIGHT/2
		cam.update();                                           // Update cam to reflect the changes made in translate method

		Gdx.input.setInputProcessor(new GameInputProcessor());  // Initialize input handler
		gsm = new GameStateManager();                           // Initialize the GameStateManager
	}

	@Override
	public void render () {
		// Clear Screen to dark green
		Gdx.gl.glClearColor(.1f,.19f, .08f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime()); // Update entities within the gsm
		gsm.draw();                              // Draw entities in gsm
		GameKeys.update();                       // Update GameKeys

	}
	
	@Override
	public void dispose () {

	}
}
