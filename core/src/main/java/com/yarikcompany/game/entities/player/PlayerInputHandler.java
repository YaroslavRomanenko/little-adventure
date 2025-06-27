package com.yarikcompany.game.entities.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

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
                player.moveUp();
                return true;
        }

        return false;
    }

}
