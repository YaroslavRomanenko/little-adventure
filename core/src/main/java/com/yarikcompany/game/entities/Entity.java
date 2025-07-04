package com.yarikcompany.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    protected EntityDirection direction;
    protected Sprite sprite;
    protected Rectangle hitbox;

    protected final float width;
    protected final float height;

    protected float spawnX;
    protected float spawnY;

    public Entity(Sprite startingSprite, EntityDirection startingDirection) {
        this.sprite = startingSprite;
        this.direction = startingDirection;

        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
        updateHitboxPos();
    }

    protected void updateHitboxPos() {
        float hitboxOffsetX = (sprite.getWidth() - hitbox.width) / 2.0f;
        hitbox.setPosition(sprite.getX() + hitboxOffsetX, sprite.getY());
    }

    protected static Sprite createInitialSprite(TextureAtlas atlas, String region, float width, float height) {
        Animation<TextureRegion> initialAnimation = new Animation<>(
            0.1f,
            atlas.findRegions(region)
        );

        TextureRegion firstFrame = initialAnimation.getKeyFrame(0);
        Sprite initialSprite = new Sprite(firstFrame);

        initialSprite.setSize(width, height);

        return initialSprite;
    }

    public abstract void changeDirection(EntityDirection newDirection);

    public abstract void update(float delta);
    public abstract void draw(SpriteBatch batch);

    public EntityDirection getCurrentDirection() { return direction; }
    public Sprite getSprite() { return sprite; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public void setCurrentDirection(EntityDirection direction) { this.direction = direction; }
}
