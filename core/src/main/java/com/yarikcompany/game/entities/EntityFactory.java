package com.yarikcompany.game.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.yarikcompany.game.GameAssets;
import com.yarikcompany.game.entities.player.Player;

public class EntityFactory {
    private final AssetManager assetManager;

    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Player createPlayer() {
        TextureAtlas atlas = assetManager.get(GameAssets.PLAYER_ATLAS);

        return new Player(atlas);
    }
}
