package com.yarikcompany.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entity {
    protected EntityDirection direction;
    protected Sprite entitySprite;

    public Entity(Sprite startingSprite, EntityDirection startingDirection) {
        this.entitySprite = startingSprite;
        this.direction = startingDirection;
    }

    public abstract void changeDirection(EntityDirection newDirection);

    public EntityDirection getCurrentDirection() { return direction; }

    public Sprite getSprite() { return entitySprite; }

    public void setCurrentDirection(EntityDirection direction) { this.direction = direction; }
    public void setSprite(Texture texture) { entitySprite.setTexture(texture); }
}
