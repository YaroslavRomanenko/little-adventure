package com.yarikcompany.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.yarikcompany.game.entities.Archer;
import com.yarikcompany.game.entities.EntityDirection;
import com.yarikcompany.game.entities.EntityFactory;

import java.awt.*;

public class GameScreen implements Screen {
    public static final float PPM = 16f;

    private final int mapWidth;
    private final int mapHeight;

    private final LittleAdventure game;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private ExtendViewport viewport;

    private EntityFactory entityFactory;
    private Archer archer;

    private SpriteBatch batch;

    public GameScreen(LittleAdventure game) {
        this.game = game;

        OrthographicCamera camera = new OrthographicCamera();
        camera.update();

        this.viewport = new ExtendViewport(8f, 8f, camera);

        initializeMap();
        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);

        initializeEntities();

         float spawnX = mapWidth / 2f - archer.getWidth() / 2f;
         float spawnY = mapHeight / 2f - archer.getHeight() / 2f;
        archer.setPosition(spawnX, spawnY);

        this.batch = new SpriteBatch();
    }

    private void initializeMap() {
        this.map = game.assetManager.get(GameAssets.SPAWN_MAP);

        float unitScale = 1f / PPM;
        this.renderer = new OrthogonalTiledMapRenderer(this.map, unitScale);
    }

    private void initializeEntities() {
        this.entityFactory = new EntityFactory(game.assetManager);
        this.archer = entityFactory.createArcher();
    }

    @Override
    public void show() {
        System.out.println("GameScreen is shown. Getting assets from manager...");
    }

    @Override
    public void render(float v) {
        input();
        logic();
        draw();
    }

    private void input() {
        float delta = Gdx.graphics.getDeltaTime();
        archer.update(delta, this);
    }

    private void logic() {
        Sprite archerSprite = archer.getSprite();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float archerWidth = archerSprite.getWidth();
        float archerHeight = archerSprite.getHeight();
        float delta = Gdx.graphics.getDeltaTime();

        // archerSprite.setX(MathUtils.clamp(archerSprite.getX(), 0, worldWidth - archerWidth));
        // archerSprite.setY(MathUtils.clamp(archerSprite.getY(), 0, worldHeight - archerHeight));
    }

    private void draw() {
        Sprite archerSprite = archer.getSprite();

        float archerCenterX = archerSprite.getX() + archerSprite.getWidth() / 2;
        float archerCenterY = archerSprite.getY() + archerSprite.getHeight() / 2;

        viewport.getCamera().position.set(archerCenterX, archerCenterY, 0);

        viewport.getCamera().update();
        ScreenUtils.clear(Color.BLACK);

        renderer.setView((OrthographicCamera) viewport.getCamera());
        renderer.render();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        archer.draw(batch);

        batch.end();
    }

    public boolean isMoveValid(float nextX, float nextY) {

        float archerWidth = archer.getHitbox().getWidth();
        float archerHeight = archer.getHitbox().getHeight();

        if (nextX < 0) {
            return false;
        }

        if (nextX + archerWidth > mapWidth) {
            return false;
        }

        if (nextY < 0) {
            return false;
        }

        if (nextY + archerHeight > mapHeight) {
            return false;
        }

        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
    }
}
