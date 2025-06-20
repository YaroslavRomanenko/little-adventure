package com.yarikcompany.game.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.yarikcompany.game.GameAssets;

public class EntityFactory {
    private final AssetManager assetManager;

    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Archer createArcher() {
        Texture up = assetManager.get(GameAssets.ARCHER_UP);
        Texture down = assetManager.get(GameAssets.ARCHER_DOWN);
        Texture left = assetManager.get(GameAssets.ARCHER_LEFT);
        Texture right = assetManager.get(GameAssets.ARCHER_RIGHT);

        return new Archer(up, down, left, right);
    }
}
