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

import java.awt.*;

public class GameScreen implements Screen {
    public static final float PPM = 16f;

    private final LittleAdventure game;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private ExtendViewport viewport;

    private Archer archer;
    private SpriteBatch batch;

    public GameScreen(LittleAdventure game) {
        this.game = game;

        map = new TmxMapLoader().load("maps/Level_0.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f / PPM);

        OrthographicCamera camera = new OrthographicCamera();
        camera.update();

        viewport = new ExtendViewport(8f, 8f, camera);


        archer = new Archer();
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

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

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            archerSprite.translateY(speed * delta);
            archer.setSprite(archer.getTextureUp());

        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            archerSprite.translateY(-speed * delta);
            archer.setSprite(archer.getTextureDown());

        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            archerSprite.translateX(speed * delta);
            archer.setSprite(archer.getTextureRight());

        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            archerSprite.translateX(-speed * delta);
            archer.setSprite(archer.getTextureLeft());
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

        ScreenUtils.clear(Color.BLACK);
        viewport.getCamera().update();
        renderer.setView((OrthographicCamera) viewport.getCamera());
        renderer.render();
        viewport.getCamera().position.set(archerSprite.getX(), archerSprite.getY(), 0);
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
        map.dispose();
        renderer.dispose();
        archer.dispose();
        batch.dispose();
    }
}
