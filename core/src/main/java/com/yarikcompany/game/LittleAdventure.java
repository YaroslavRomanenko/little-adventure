package com.yarikcompany.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LittleAdventure extends Game {
    public AssetManager assetManager;

    @Override
    public void create() {
        assetManager = new AssetManager();

        this.setScreen(new LoadingScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        assetManager.dispose();
    }
}
