package com.yarikcompany.game.math;

import com.badlogic.gdx.math.Rectangle;

public class CollisionDetection {
    public static boolean isCollisionFound(Rectangle firstHitboxShape, Rectangle secondHitboxShape) {
        if (firstHitboxShape.x + firstHitboxShape.width < secondHitboxShape.x) return false;
        if (firstHitboxShape.x > secondHitboxShape.x + secondHitboxShape.width) return false;
        if (firstHitboxShape.y + firstHitboxShape.height < secondHitboxShape.y) return false;
        if (firstHitboxShape.y > secondHitboxShape.y + secondHitboxShape.height) return false;

        return true;
    }
}
