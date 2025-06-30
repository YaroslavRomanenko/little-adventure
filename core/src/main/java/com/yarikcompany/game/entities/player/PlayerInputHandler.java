package com.yarikcompany.game.entities.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.yarikcompany.game.entities.EntityDirection;

import java.security.Key;

public class PlayerInputHandler extends InputAdapter {
    private Player player;

    public PlayerInputHandler(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
            case Keys.UP:
                player.setIsMovingUp(true);
                return true;
            case Keys.S:
            case Keys.DOWN:
                player.setIsMovingDown(true);
                return true;
            case Keys.A:
            case Keys.LEFT:
                player.setIsMovingLeft(true);
                return true;
            case Keys.D:
            case Keys.RIGHT:
                player.setIsMovingRight(true);
                return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
            case Keys.UP:
                player.setIsMovingUp(false);
                return true;
            case Keys.S:
            case Keys.DOWN:
                player.setIsMovingDown(false);
                return true;
            case Keys.A:
            case Keys.LEFT:
                player.setIsMovingLeft(false);
                return true;
            case Keys.D:
            case Keys.RIGHT:
                player.setIsMovingRight(false);
                return true;
        }
        return false;
    }

}
