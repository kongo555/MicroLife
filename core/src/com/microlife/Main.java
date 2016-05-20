package com.microlife;

import com.badlogic.gdx.Game;
import com.microlife.screens.GameScreen;

public class Main extends Game {
	//public final static float width = 8;
	public final static int width = 1280;
	public final static int height = 720;
	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}