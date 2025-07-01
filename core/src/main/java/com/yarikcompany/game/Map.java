package com.yarikcompany.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.yarikcompany.game.entities.Entity;
import com.yarikcompany.game.entities.EntityFactory;
import com.yarikcompany.game.entities.slime.Slime;

import java.util.ArrayList;
import java.util.List;

public class Map {
    public static final float PPM = 16f;
    public static final int TILE_PX_WIDTH = 16;
    public static final int TILE_PX_HEIGHT = 16;
    public static final int TILE_WIDTH = 1;
    public static final int TILE_HEIGHT = 1;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private static ExtendViewport viewport;

    private static MapLayers layers;
    private static EntityFactory entityFactory;

    private static int mapWidth;
    private static int mapHeight;

    public Map(AssetManager assetManager) {
        OrthographicCamera camera = new OrthographicCamera();
        camera.update();

        viewport = new ExtendViewport(8f, 8f, camera);

        this.map = assetManager.get(GameAssets.SPAWN_MAP);

        float unitScale = 1f / PPM;
        this.renderer = new OrthogonalTiledMapRenderer(this.map, unitScale);

        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);

        layers = map.getLayers();
        entityFactory = new EntityFactory(assetManager);
    }

    public void spawnMobs(List<Entity> mobList) {
        MapLayer mobLayer = map.getLayers().get("Entities");
        if (mobLayer == null) return;

        for (MapObject object : mobLayer.getObjects()) {
            String type = object.getProperties().get("type", String.class);
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);

            if ("slime".equals(type)) {
                Slime slime = entityFactory.createSlime(x, y);
                mobList.add(slime);
            }
        }
    }

    public static boolean isCellBLocked(int tileX, int tileY) {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer)layers.get("Collisions");

        TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);
        if (cell == null || cell.getTile() == null) {
            return false;
        }

        return cell.getTile().getProperties().containsKey("collidable");
    }

    public static OrthographicCamera getCamera() {
        return (OrthographicCamera) viewport.getCamera();
    }

    public void dispose() {
        renderer.dispose();
    }

    public static MapObject getEntity(String entityName) {
        MapLayer layer = layers.get("Entities");
        return layer.getObjects().get(entityName);
    }

    public static int getMapWidth() { return mapWidth; }
    public static int getMapHeight() { return mapHeight; }
    public OrthogonalTiledMapRenderer getRenderer() { return renderer; }
    public ExtendViewport getViewport() { return viewport; }
}
