package com.yarikcompany.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class LoadingScreen extends ScreenAdapter {
    private final LittleAdventure game;

    public LoadingScreen(final LittleAdventure game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("LOADING...");
        GameAssets.load(game.assetManager);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if (game.assetManager.update()) {
            System.out.println("LOADING FINISHED!");
            game.setScreen(new GameScreen(game));
        }

        float progress = game.assetManager.getProgress();
        System.out.println("Loading progress: " + (progress * 100) + "%");
    }
}
