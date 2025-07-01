package com.yarikcompany.game.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.yarikcompany.game.GameAssets;
import com.yarikcompany.game.entities.player.Player;
import com.yarikcompany.game.entities.slime.Slime;

public class EntityFactory {
    private final AssetManager assetManager;

    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Player createPlayer() {
        TextureAtlas atlas = assetManager.get(GameAssets.PLAYER_ATLAS);

        return new Player(atlas);
    }

    public Slime createSlime(float x, float y) {
        TextureAtlas atlas = assetManager.get(GameAssets.BLUE_SLIME_ATLAS);

        return new Slime(atlas, x, y);
    }
}
