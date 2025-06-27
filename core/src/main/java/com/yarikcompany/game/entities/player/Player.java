package com.yarikcompany.game.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.yarikcompany.game.GameScreen;
import com.yarikcompany.game.Map;
import com.yarikcompany.game.entities.Entity;
import com.yarikcompany.game.entities.EntityDirection;

public class Player extends Entity {
    private static final float PLAYER_WIDTH = 1f;
    private static final float PLAYER_HEIGHT = 1.16f;
    private static final float SPEED = 2.5f;

    private final Animation<TextureRegion> walkUpAnimation;
    private final Animation<TextureRegion> walkDownAnimation;
    private final Animation<TextureRegion> walkLeftAnimation;
    private final Animation<TextureRegion> walkRightAnimation;

    private Animation<TextureRegion> currentAnimation;

    private float stateTime = 0f;

    public Player(TextureAtlas atlas) {
        super(createInitialSprite(atlas), EntityDirection.DOWN);

        float frameDuration = .1f;

        walkUpAnimation = new Animation<>(frameDuration, atlas.findRegions("up"), Animation.PlayMode.LOOP);
        walkDownAnimation = new Animation<>(frameDuration, atlas.findRegions("down"), Animation.PlayMode.LOOP);
        walkLeftAnimation = new Animation<>(frameDuration, atlas.findRegions("left"), Animation.PlayMode.LOOP);
        walkRightAnimation = new Animation<>(frameDuration, atlas.findRegions("right"), Animation.PlayMode.LOOP);

        this.currentAnimation = walkDownAnimation;

        this.hitbox = new Rectangle(getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());
    }

    private static Sprite createInitialSprite(TextureAtlas atlas) {
        Animation<TextureRegion> initialAnimation = new Animation<>(
            0.1f,
            atlas.findRegions("down")
        );

        TextureRegion firstFrame = initialAnimation.getKeyFrame(0);
        Sprite initialSprite = new Sprite(firstFrame);

        initialSprite.setSize(PLAYER_WIDTH, PLAYER_HEIGHT);

        return initialSprite;
    }

    @Override
    public void changeDirection(EntityDirection newDirection) {
        this.setCurrentDirection(newDirection);
        switch (newDirection) {
            case UP:
                this.currentAnimation = this.walkUpAnimation;
                break;
            case DOWN:
                this.currentAnimation = this.walkDownAnimation;
                break;
            case LEFT:
                this.currentAnimation = this.walkLeftAnimation;
                break;
            case RIGHT:
                this.currentAnimation = this.walkRightAnimation;
                break;
            case NONE:
                break;
        }
    }

    public void draw(Batch batch) {
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        batch.draw(currentFrame,
            getSprite().getX(),
            getSprite().getY(),
            getSprite().getWidth(),
            getSprite().getHeight()
            );
    }

    public void update(float delta, GameScreen world) {
        Vector2 velocity = new Vector2(0, 0);
        EntityDirection newDirection = EntityDirection.NONE;

        //if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) { newDirection = EntityDirection.UP; }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) { newDirection = EntityDirection.DOWN; }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) { newDirection = EntityDirection.LEFT; }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { newDirection = EntityDirection.RIGHT; }

        //if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) { velocity.y = 1; }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) { velocity.y = -1; }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) { velocity.x = -1; }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { velocity.x = 1; }

        if (newDirection != direction) {
            changeDirection(newDirection);
        }

        if (velocity.len2() > 0) {
            stateTime += delta;
        } else if (direction != EntityDirection.NONE) {
            changeDirection(EntityDirection.NONE);
            stateTime = 0;
        }
    }

    public void moveUp() {
        this.currentAnimation = walkUpAnimation;
        Vector2 velocity = new Vector2(0, 0);
        velocity.y = 1;

        walk(velocity);
    }

    public void walk(Vector2 velocity) {
        float delta = Gdx.graphics.getDeltaTime();

        if (velocity.len2() > 0) {
            velocity.nor();

            float moveX = velocity.x * SPEED * delta;

            if (isMoveValid(entitySprite.getX() + moveX, entitySprite.getY())) {
                entitySprite.translateX(moveX);
            }

            float moveY = velocity.y * SPEED * delta;

            if (isMoveValid(entitySprite.getX(), entitySprite.getY() + moveY)) {
                entitySprite.translateY(moveY);
            }
        }

        updateHitboxPos();
    }

    public boolean isMoveValid(float nextX, float nextY) {

        float archerRealWidth = getHitbox().getWidth();
        float archerRealHeight = getHitbox().getHeight();

        if (nextX < 0) {
            return false;
        }

        if (nextX + archerRealWidth > Map.getMapWidth()) {
            return false;
        }

        if (nextY < 0) {
            return false;
        }

        if (nextY + archerRealHeight > Map.getMapHeight()) {
            return false;
        }

        return true;
    }

    public Rectangle getHitbox() { return hitbox; }
}
