package com.yarikcompany.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.yarikcompany.game.entities.player.Player;
import com.yarikcompany.game.entities.EntityFactory;
import com.yarikcompany.game.entities.player.PlayerInputHandler;
import com.yarikcompany.game.utils.Interpolator;
import jdk.internal.net.http.common.Log;

public class GameScreen implements Screen {
    private Map map;

    private final LittleAdventure game;

    private EntityFactory entityFactory;
    private Player player;
    private PlayerInputHandler playerInputHandler;

    private SpriteBatch batch;

    private Interpolator cameraInterpolator;

    public GameScreen(LittleAdventure game) {
        this.game = game;
        map = new Map(game.assetManager);

        initializeEntities();

        playerInputHandler = new PlayerInputHandler(player);
        Gdx.input.setInputProcessor(playerInputHandler);

        cameraInterpolator = new Interpolator(new Vector2(player.getSpawnX(), player.getSpawnY()), 1.4f,1.0f,0.0f);

        this.batch = new SpriteBatch();

        Gdx.app.setLogLevel(Log.ALL);
    }

    private void initializeEntities() {
        this.entityFactory = new EntityFactory(game.assetManager);
        this.player = entityFactory.createPlayer();
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
    }

    private void logic() {
        float delta = Gdx.graphics.getDeltaTime();

        player.update(delta);
        cameraInterpolator.step(delta);
    }

    private void draw() {
        Sprite playerSprite = player.getSprite();

        //move to own function
        float playerCenterX = playerSprite.getX() + playerSprite.getWidth() / 2;
        float playerCenterY = playerSprite.getY() + playerSprite.getHeight() / 2;
        map.getViewport().getCamera().position.set(cameraInterpolator.getInterpolatedValue().x,
            cameraInterpolator.getInterpolatedValue().y , 0);
        cameraInterpolator.setTarget(new Vector2(playerCenterX,playerCenterY));

        map.getViewport().getCamera().update();
        ScreenUtils.clear(Color.BLACK);

        //do you need to set view every iteration?
        map.getRenderer().setView((OrthographicCamera) map.getViewport().getCamera());
        map.getRenderer().render();

        batch.setProjectionMatrix(map.getViewport().getCamera().combined);
        batch.begin();

        player.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        map.getViewport().update(width, height);
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
        batch.dispose();
    }
}
