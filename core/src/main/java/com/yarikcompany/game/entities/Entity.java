package com.yarikcompany.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    protected EntityDirection direction;
    protected Sprite entitySprite;
    protected Rectangle hitbox;

    protected final float width;
    protected final float height;

    public Entity(Sprite startingSprite, EntityDirection startingDirection) {
        this.entitySprite = startingSprite;
        this.direction = startingDirection;

        width = entitySprite.getWidth();
        height = entitySprite.getHeight();
    }

    public void setPosition(float x, float y) {
        entitySprite.setPosition(x, y);
        updateHitboxPos();
    }

    protected void updateHitboxPos() {
        float hitboxOffsetX = (entitySprite.getWidth() - hitbox.width) / 2.0f;
        hitbox.setPosition(entitySprite.getX() + hitboxOffsetX, entitySprite.getY());
    }

    public abstract void changeDirection(EntityDirection newDirection);

    public EntityDirection getCurrentDirection() { return direction; }
    public Sprite getSprite() { return entitySprite; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public void setCurrentDirection(EntityDirection direction) { this.direction = direction; }
}
