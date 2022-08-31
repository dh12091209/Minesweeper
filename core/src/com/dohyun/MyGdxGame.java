package com.dohyun;


import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
