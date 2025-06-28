package com.yarikcompany.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Map {
    public static final float PPM = 16f;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private ExtendViewport viewport;

    private static MapLayers layers;

    private static int mapWidth;
    private static int mapHeight;

    public Map(AssetManager assetManager) {
        OrthographicCamera camera = new OrthographicCamera();
        camera.update();

        this.viewport = new ExtendViewport(8f, 8f, camera);

        this.map = assetManager.get(GameAssets.SPAWN_MAP);

        float unitScale = 1f / PPM;
        this.renderer = new OrthogonalTiledMapRenderer(this.map, unitScale);

        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);

        layers = map.getLayers();
    }

    public static MapObject getEntity(String entityName) {
        MapLayer layer = layers.get("Entities");
        return layer.getObjects().get(entityName);
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    public static int getMapWidth() { return mapWidth; }
    public static int getMapHeight() { return mapHeight; }
    public OrthogonalTiledMapRenderer getRenderer() { return renderer; }
    public ExtendViewport getViewport() { return viewport; }
}
