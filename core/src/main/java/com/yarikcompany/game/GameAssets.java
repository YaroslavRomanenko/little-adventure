package com.yarikcompany.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public final class GameAssets {
    private GameAssets() {}

    private static final String CHARACTERS_PATH = "characters/";
    private static final String MAPS_PATH = "maps/";
    private static final String TILE_SETS_PATH = "tileSets/";

    public static final AssetDescriptor<Texture> ARCHER_UP = new AssetDescriptor<>(CHARACTERS_PATH + "archer/up.png", Texture.class);
    public static final AssetDescriptor<Texture> ARCHER_DOWN = new AssetDescriptor<>(CHARACTERS_PATH + "archer/down.png", Texture.class);
    public static final AssetDescriptor<Texture> ARCHER_LEFT = new AssetDescriptor<>(CHARACTERS_PATH + "archer/left.png", Texture.class);
    public static final AssetDescriptor<Texture> ARCHER_RIGHT = new AssetDescriptor<>(CHARACTERS_PATH + "archer/right.png", Texture.class);

    public static void load(AssetManager manager) {
        manager.load(ARCHER_UP);
        manager.load(ARCHER_DOWN);
        manager.load(ARCHER_LEFT);
        manager.load(ARCHER_RIGHT);
    }
}
