package com.yarikcompany.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Archer {
    private Texture archerUpTexture;
    private Texture archerDownTexture;
    private Texture archerRightTexture;
    private Texture archerLeftTexture;

    private Sprite archerSprite;

    public Archer() {
        archerUpTexture = new Texture("characters/archer/up.png");
        archerDownTexture = new Texture("characters/archer/down.png");
        archerRightTexture = new Texture("characters/archer/right.png");
        archerLeftTexture = new Texture("characters/archer/left.png");

        archerSprite = new Sprite(archerDownTexture);
        archerSprite.setSize(1,1);
        archerSprite.setPosition(10,10);
    }

    public Texture getTextureUp() { return archerUpTexture; }
    public Texture getTextureDown() { return archerDownTexture; }
    public Texture getTextureRight() { return archerRightTexture; }
    public Texture getTextureLeft() { return archerLeftTexture; }

    public Sprite getSprite() { return archerSprite; }

    public void setSprite(Texture texture) { archerSprite.setTexture(texture); }

    public void dispose() {
        archerDownTexture.dispose();
        archerRightTexture.dispose();
        archerUpTexture.dispose();
    }
}
