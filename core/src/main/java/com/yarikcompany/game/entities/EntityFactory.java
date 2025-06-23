package com.yarikcompany.game.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yarikcompany.game.GameAssets;

public class EntityFactory {
    private final AssetManager assetManager;

    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Archer createArcher() {
        TextureAtlas atlas = assetManager.get(GameAssets.ARCHER_ATLAS);

        return new Archer(atlas);
    }
}
