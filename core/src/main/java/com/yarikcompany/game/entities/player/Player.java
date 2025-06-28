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

    private float spawnX;
    private float spawnY;

    private boolean isMovingUp = false;
    private boolean isMovingDown = false;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;

    private final Animation<TextureRegion> walkUpAnimation;
    private final Animation<TextureRegion> walkDownAnimation;
    private final Animation<TextureRegion> walkLeftAnimation;
    private final Animation<TextureRegion> walkRightAnimation;

    private Animation<TextureRegion> currentAnimation;

    private float stateTime = 0f;

    private Vector2 velocity = new Vector2(0, 0);

    public Player(TextureAtlas atlas) {
        super(createInitialSprite(atlas), EntityDirection.DOWN);

        float frameDuration = .1f;

        walkUpAnimation = new Animation<>(frameDuration, atlas.findRegions("up"), Animation.PlayMode.LOOP);
        walkDownAnimation = new Animation<>(frameDuration, atlas.findRegions("down"), Animation.PlayMode.LOOP);
        walkLeftAnimation = new Animation<>(frameDuration, atlas.findRegions("left"), Animation.PlayMode.LOOP);
        walkRightAnimation = new Animation<>(frameDuration, atlas.findRegions("right"), Animation.PlayMode.LOOP);

        this.currentAnimation = walkDownAnimation;

        this.hitbox = new Rectangle(getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());

        this.spawnX = Map.getMapWidth() / 2f - getWidth() / 2f;
        this.spawnY = Map.getMapHeight() / 2f - getHeight() / 2f;
        setPosition(spawnX, spawnY);
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
        this.direction = newDirection;

        switch (direction) {
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

    public void update(float delta) {
        velocity.set(0, 0);
        EntityDirection newDirection = EntityDirection.NONE;

        if (isMovingUp) {
            velocity.y = 1;
            newDirection = EntityDirection.UP;
        }
        if (isMovingDown) {
            velocity.y = -1;
            newDirection = EntityDirection.DOWN;
        }
        if (isMovingLeft) {
            velocity.x = -1;
            newDirection = EntityDirection.LEFT;
        }
        if (isMovingRight) {
            velocity.x = 1;
            newDirection = EntityDirection.RIGHT;
        }

        if (velocity.len2() > 0) {
            stateTime += delta;
        } else if (direction != EntityDirection.NONE) {
            changeDirection(EntityDirection.NONE);
            stateTime = 0;
        }

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

        changeDirection(newDirection);
        updateHitboxPos();
    }

    public boolean isMoveValid(float nextX, float nextY) {
        float playerRealWidth = getHitbox().getWidth();
        float playerRealHeight = getHitbox().getHeight();

        if (nextX < 0) {
            return false;
        }

        if (nextX + playerRealWidth > Map.getMapWidth()) {
            return false;
        }

        if (nextY < 0) {
            return false;
        }

        if (nextY + playerRealHeight > Map.getMapHeight()) {
            return false;
        }

        return true;
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

    public float getSpawnX() { return spawnX; }
    public float getSpawnY() { return spawnY; }
    public Rectangle getHitbox() { return hitbox; }

    public void setIsMovingUp(boolean isMovingUp) { this.isMovingUp = isMovingUp; }
    public void setIsMovingDown(boolean isMovingDown) { this.isMovingDown = isMovingDown; }
    public void setIsMovingLeft(boolean isMovingLeft) { this.isMovingLeft = isMovingLeft; }
    public void setIsMovingRight(boolean isMovingRight) { this.isMovingRight = isMovingRight; }
}
