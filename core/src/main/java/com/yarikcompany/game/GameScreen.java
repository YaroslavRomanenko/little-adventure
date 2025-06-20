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
        float speed = 2f;
        float delta = Gdx.graphics.getDeltaTime();
        Sprite archerSprite = archer.getSprite();

        EntityDirection newDirection = EntityDirection.NONE;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            archerSprite.translateY(speed * delta);
            newDirection = EntityDirection.UP;

        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            archerSprite.translateY(-speed * delta);
            newDirection = EntityDirection.DOWN;

        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            archerSprite.translateX(speed * delta);
            newDirection = EntityDirection.RIGHT;

        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            archerSprite.translateX(-speed * delta);
            newDirection = EntityDirection.LEFT;
        }

        if (newDirection != archer.getCurrentDirection()) {
            archer.changeDirection(newDirection);
        }
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
