package com.yarikcompany.game.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.yarikcompany.game.GameScreen;
import com.yarikcompany.game.Map;
import com.yarikcompany.game.entities.Entity;
import com.yarikcompany.game.entities.EntityDirection;
import com.yarikcompany.game.math.CollisionDetection;

import java.lang.Record;

import static com.yarikcompany.game.Map.*;

public class Player extends Entity {
    private static final float PLAYER_WIDTH = 1f;
    private static final float PLAYER_HEIGHT = 1.16f;
    private static final float SPEED = 2.5f;
    private static final float EPSILON = 0.001f;

    private float centerX;
    private float centerY;

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
        super(createInitialSprite(atlas, "down", PLAYER_WIDTH, PLAYER_HEIGHT), EntityDirection.DOWN);

        initializeSpawnCords();

        float frameDuration = .1f;

        walkUpAnimation = new Animation<>(frameDuration, atlas.findRegions("up"), Animation.PlayMode.LOOP);
        walkDownAnimation = new Animation<>(frameDuration, atlas.findRegions("down"), Animation.PlayMode.LOOP);
        walkLeftAnimation = new Animation<>(frameDuration, atlas.findRegions("left"), Animation.PlayMode.LOOP);
        walkRightAnimation = new Animation<>(frameDuration, atlas.findRegions("right"), Animation.PlayMode.LOOP);

        this.currentAnimation = walkDownAnimation;

        initializeHitbox();
        setPosition(spawnX, spawnY);
    }

    private void initializeSpawnCords() {
        MapObject playerObject = Map.getEntity("Player");

        spawnX = playerObject.getProperties().get("x", Float.class) / PPM;
        spawnY = playerObject.getProperties().get("y", Float.class) / PPM;
    }

    private void initializeHitbox() {
        float hitboxX = sprite.getX();
        float hitboxY = sprite.getY();
        float hitboxWidth = sprite.getWidth() * .7f;
        float hitboxHeight = sprite.getHeight() * .45f;

        this.hitbox = new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
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
        centerX = getSprite().getX() + getSprite().getWidth() / 2;
        centerY = getSprite().getY() + getSprite().getHeight() / 2;

        velocity.set(0, 0);
        EntityDirection newDirection = EntityDirection.NONE;

        if (isMovingUp) { velocity.y = 1; newDirection = EntityDirection.UP; }
        if (isMovingDown) { velocity.y = -1; newDirection = EntityDirection.DOWN; }
        if (isMovingLeft) { velocity.x = -1; newDirection = EntityDirection.LEFT; }
        if (isMovingRight) { velocity.x = 1; newDirection = EntityDirection.RIGHT; }

        if (velocity.len2() > 0) {
            stateTime += delta;
            velocity.nor();
        } else {
            if (direction != EntityDirection.NONE) {
                stateTime = 0;
            }
            changeDirection(EntityDirection.NONE);
            return;
        }

        float desiredMoveX = velocity.x * SPEED * delta;
        float finalMoveX = calculateAllowedMoveX(desiredMoveX);
        sprite.translateX(finalMoveX);
        updateHitboxPos();

        float desiredMoveY = velocity.y * SPEED * delta;
        float finalMoveY = calculateAllowedMoveY(desiredMoveY);
        sprite.translateY(finalMoveY);
        updateHitboxPos();

        changeDirection(newDirection);
    }

    private float calculateAllowedMoveX(float desiredMoveX) {
        if (desiredMoveX == 0) return 0;

        float finalMoveX = desiredMoveX;

        float leadingEdgeX = (desiredMoveX > 0) ? hitbox.x + hitbox.width : hitbox.x;
        int targetTileX = (int)(leadingEdgeX + desiredMoveX);

        for (int i = 0; i < 3; i++) {
            float yPos = hitbox.y + (i * hitbox.height / 2.0f);

            if (i == 2) {
                yPos -= EPSILON;
            }

            int tileY = (int)yPos;

            if (Map.isCellBLocked(targetTileX, tileY)) {
                if (desiredMoveX > 0) {
                    finalMoveX = (float)targetTileX - leadingEdgeX;
                } else {
                    finalMoveX = (float)(targetTileX + 1) - leadingEdgeX;
                }
                break;
            }
        }
        return finalMoveX;
    }

    private float calculateAllowedMoveY(float desiredMoveY) {
        if (desiredMoveY == 0) return 0;

        float finalMoveY = desiredMoveY;

        float leadingEdgeY = (desiredMoveY > 0) ? hitbox.y + hitbox.height : hitbox.y;
        int targetTileY = (int)(leadingEdgeY + desiredMoveY);

        for (int i = 0; i < 3; i++) {
            float xPos = hitbox.x + (i * hitbox.width / 2.0f);

            if (i == 2) {
                xPos -= EPSILON;
            }

            int tileX = (int)xPos;

            if (Map.isCellBLocked(tileX, targetTileY)) {
                if (desiredMoveY > 0) {
                    finalMoveY = (float)targetTileY - leadingEdgeY;
                } else {
                    finalMoveY = (float)(targetTileY + 1) - leadingEdgeY;
                }
                break;
            }
        }
        return finalMoveY;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        batch.draw(currentFrame,
            getSprite().getX(),
            getSprite().getY(),
            getSprite().getWidth(),
            getSprite().getHeight()
        );
    }

    public void drawHitbox(ShapeRenderer renderer) {
        renderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        int tileX = Math.round(hitbox.x);
        int tileY = Math.round(hitbox.y);

        renderer.rect(tileX + 1, tileY, 1, 1);
        renderer.rect(tileX - 1, tileY, 1, 1);
        renderer.rect(tileX, tileY + 1, 1, 1);
        renderer.rect(tileX, tileY - 1, 1, 1);
    }

    public float getSpawnX() { return spawnX; }
    public float getSpawnY() { return spawnY; }
    public float getCenterX() { return centerX; }
    public float getCenterY() { return centerY; }
    public Rectangle getHitbox() { return hitbox; }

    public void setIsMovingUp(boolean isMovingUp) { this.isMovingUp = isMovingUp; }
    public void setIsMovingDown(boolean isMovingDown) { this.isMovingDown = isMovingDown; }
    public void setIsMovingLeft(boolean isMovingLeft) { this.isMovingLeft = isMovingLeft; }
    public void setIsMovingRight(boolean isMovingRight) { this.isMovingRight = isMovingRight; }
}
