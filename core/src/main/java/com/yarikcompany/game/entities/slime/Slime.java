package com.yarikcompany.game.entities.slime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.yarikcompany.game.entities.Entity;
import com.yarikcompany.game.entities.EntityDirection;

import java.awt.*;

import static com.yarikcompany.game.Map.PPM;

public class Slime extends Entity {
    private final static float SLIME_WIDTH = 1f;
    private final static float SLIME_HEIGHT = 1f;

    private float stateTime = 0f;

    private Animation<TextureRegion> currentAnimation;

    public Slime(TextureAtlas atlas, float spawnX, float spawnY) {
        super(createInitialSprite(atlas, "slime", SLIME_WIDTH, SLIME_HEIGHT), EntityDirection.DOWN);
        this.spawnX = spawnX / PPM;
        this.spawnY = spawnY / PPM;

        this.currentAnimation = new Animation<>(1f, atlas.findRegions("slime"), Animation.PlayMode.LOOP);

        this.hitbox = new Rectangle(getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());
        setPosition(this.spawnX, this.spawnY);
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        batch.draw(currentFrame, getSprite().getX(), getSprite().getY(), getWidth(), getHeight());
    }

    @Override
    public void changeDirection(EntityDirection newDirection) {
    }
}
