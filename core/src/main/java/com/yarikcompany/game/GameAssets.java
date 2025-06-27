package com.yarikcompany.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;

public final class GameAssets {
    private GameAssets() {}

    private static final String ENTITIES_PATH = "entities/";
    private static final String MAPS_PATH = "maps/";

//    public static final AssetDescriptor<Texture> ARCHER_UP = new AssetDescriptor<>(CHARACTERS_PATH + "archer/up.png", Texture.class);
//    public static final AssetDescriptor<Texture> ARCHER_DOWN = new AssetDescriptor<>(CHARACTERS_PATH + "archer/down.png", Texture.class);
//    public static final AssetDescriptor<Texture> ARCHER_LEFT = new AssetDescriptor<>(CHARACTERS_PATH + "archer/left.png", Texture.class);
//    public static final AssetDescriptor<Texture> ARCHER_RIGHT = new AssetDescriptor<>(CHARACTERS_PATH + "archer/right.png", Texture.class);

    public static final AssetDescriptor<TextureAtlas> PLAYER_ATLAS = new AssetDescriptor<>(ENTITIES_PATH + "player/player_animation.atlas", TextureAtlas.class);
    public static final AssetDescriptor<TiledMap> SPAWN_MAP = new AssetDescriptor<>(MAPS_PATH + "spawn.tmx", TiledMap.class);

    public static void load(AssetManager manager) {
//        manager.load(ARCHER_UP);
//        manager.load(ARCHER_DOWN);
//        manager.load(ARCHER_LEFT);
//        manager.load(ARCHER_RIGHT);
        manager.load(PLAYER_ATLAS);
        manager.load(SPAWN_MAP);
    }
}
