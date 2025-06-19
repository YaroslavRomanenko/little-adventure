package com.yarikcompany.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LittleAdventure extends Game {

    @Override
    public void create() {
        this.setScreen(new GameScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
    }
}
