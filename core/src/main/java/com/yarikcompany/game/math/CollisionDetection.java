package com.yarikcompany.game.math;

import com.badlogic.gdx.math.Rectangle;

public class CollisionDetection {
    public static boolean isCollisionFound(Rectangle firstHitboxShape, Rectangle secondHitboxShape) {
        if (secondHitboxShape.x < firstHitboxShape.x
            && secondHitboxShape.x + secondHitboxShape.width < firstHitboxShape.x
            || secondHitboxShape.x > firstHitboxShape.x + firstHitboxShape.width
            && secondHitboxShape.x + secondHitboxShape.width > firstHitboxShape.x + firstHitboxShape.width)
        {
            return false;
        }

        if (secondHitboxShape.y < firstHitboxShape.y
            && secondHitboxShape.y + secondHitboxShape.height < firstHitboxShape.y
            || secondHitboxShape.y > firstHitboxShape.y + firstHitboxShape.height
            && secondHitboxShape.y + secondHitboxShape.height > firstHitboxShape.y + firstHitboxShape.height)
        {
            return false;
        }

        return true;
    }
}
