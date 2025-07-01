package com.yarikcompany.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;

public final class GameAssets {
    private GameAssets() {}

    private static final String ENTITIES_PATH = "entities/";
    private static final String UI_PATH = "ui/";
    private static final String MAPS_PATH = "maps/";

    public static final AssetDescriptor<TextureAtlas> PLAYER_ATLAS = new AssetDescriptor<>(ENTITIES_PATH + "player/player_animation.atlas", TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> BLUE_SLIME_ATLAS = new AssetDescriptor<>(ENTITIES_PATH + "blue_slime/blue_slime_animation.atlas", TextureAtlas.class);

    public static final AssetDescriptor<Texture> TOOL_BAR_SLOT = new AssetDescriptor<>(UI_PATH + "slot_icon.png", Texture.class);
    public static final AssetDescriptor<Texture> TOOL_BAR_LEFT_HAND_SLOT = new AssetDescriptor<>(UI_PATH + "slot_left_hand_icon.png", Texture.class);

    public static final AssetDescriptor<TiledMap> SPAWN_MAP = new AssetDescriptor<>(MAPS_PATH + "spawn.tmx", TiledMap.class);

    public static void load(AssetManager manager) {
        manager.load(PLAYER_ATLAS);
        manager.load(BLUE_SLIME_ATLAS);

        manager.load(TOOL_BAR_SLOT);
        manager.load(TOOL_BAR_LEFT_HAND_SLOT);

        manager.load(SPAWN_MAP);
    }
}
