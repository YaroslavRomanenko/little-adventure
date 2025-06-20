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

        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
        System.out.println("GameScreen is shown. Getting assets from manager...");
        this.map = game.assetManager.get(GameAssets.SPAWN_MAP);

        float unitScale = 1f / PPM;
        this.renderer = new OrthogonalTiledMapRenderer(this.map, unitScale);

        this.entityFactory = new EntityFactory(game.assetManager);

        this.archer = entityFactory.createArcher();
    }

    @Override
    public void render(float v) {
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 2.5f;
        float delta = Gdx.graphics.getDeltaTime();
        Sprite archerSprite = archer.getSprite();

        Vector2 velocity = new Vector2(0, 0);
        EntityDirection newDirection = EntityDirection.NONE;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) { newDirection = EntityDirection.UP; }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) { newDirection = EntityDirection.DOWN; }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) { newDirection = EntityDirection.LEFT; }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { newDirection = EntityDirection.RIGHT; }

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) { velocity.y = 1; }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) { velocity.y = -1; }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) { velocity.x = -1; }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { velocity.x = 1; }

        if (newDirection != archer.getCurrentDirection()) {
            archer.changeDirection(newDirection);
        }

        if (velocity.len2() > 0) {
            velocity.nor();

            float moveX = velocity.x * speed * delta;

            if (isMoveValid(archerSprite.getX() + moveX, archerSprite.getY())) {
                archerSprite.translateX(moveX);
            }

            float moveY = velocity.y * speed * delta;

            if (isMoveValid(archerSprite.getX(), archerSprite.getY() + moveY)) {
                archerSprite.translateY(moveY);
            }
        }

        archer.getHitbox().setPosition(archerSprite.getX(), archerSprite.getY());
    }

    private boolean isMoveValid(float nextX, float nextY) {
        float mapWidth = map.getProperties().get("width", Integer.class);
        float mapHeight = map.getProperties().get("height", Integer.class);

        float archerWidth = archer.getHitbox().width;
        float archerHeight = archer.getHitbox().height;

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

        archerSprite.draw(batch);

        batch.end();
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
