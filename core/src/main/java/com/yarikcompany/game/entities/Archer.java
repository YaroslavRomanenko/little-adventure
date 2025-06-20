package com.yarikcompany.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.yarikcompany.game.entities.EntityDirection.*;

public class Archer extends Entity {
    private Texture textureUP;
    private Texture textureDown;
    private Texture textureRight;
    private Texture textureLeft;


    public Archer(Texture up, Texture down, Texture left, Texture right) {
        super(new Sprite(down), EntityDirection.DOWN);

        this.textureUP = up;
        this.textureDown = down;
        this.textureLeft = left;
        this.textureRight = right;

        entitySprite = new Sprite(textureDown);
        entitySprite.setSize(1,1);
        entitySprite.setPosition(10,10);
    }

    @Override
    public void changeDirection(EntityDirection newDirection) {
        this.setCurrentDirection(newDirection);
        switch (newDirection) {
            case UP:
                this.setSprite(this.getTextureUp());
                break;
            case DOWN:
                this.setSprite(this.getTextureDown());
                break;
            case LEFT:
                this.setSprite(this.getTextureLeft());
                break;
            case RIGHT:
                this.setSprite(this.getTextureRight());
                break;
            case NONE:
                break;
        }
    }

    public void dispose() {
        textureUP.dispose();
        textureDown.dispose();
        textureRight.dispose();
        textureLeft.dispose();
    }

    public Texture getTextureUp() { return textureUP; }
    public Texture getTextureDown() { return textureDown; }
    public Texture getTextureRight() { return textureRight; }
    public Texture getTextureLeft() { return textureLeft; }
}
