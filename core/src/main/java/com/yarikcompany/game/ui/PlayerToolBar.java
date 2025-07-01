package com.yarikcompany.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.yarikcompany.game.GameAssets;

import static com.yarikcompany.game.GameScreen.MIN_WINDOW_WIDTH;

public class PlayerToolBar {
    public final static int SLOTS_AMOUNT = 7;
    public final static int SLOT_SIZE = 40;
    private final static float PADDING = 10f;

    private float startX;
    private float startY = 20f;
    private float initialSlotSize;
    private float initialPadding;
    private float toolBarWidth;

    private Texture slotIcon;

    private static ScreenViewport uiViewport;

    public PlayerToolBar(AssetManager assetManager) {
        this.slotIcon = assetManager.get(GameAssets.TOOL_BAR_SLOT);

        OrthographicCamera uiCamera = new OrthographicCamera();
        uiViewport = new ScreenViewport(uiCamera);
    }

    public void update() {
        float scale = uiViewport.getWorldWidth() / MIN_WINDOW_WIDTH;

        this.initialSlotSize = SLOT_SIZE * scale;
        this.initialPadding = PADDING * scale;

        this.toolBarWidth = (SLOTS_AMOUNT * initialSlotSize) + ((SLOTS_AMOUNT - 1) * initialPadding);
        this.startX = (Gdx.graphics.getWidth() / 2f) - (toolBarWidth / 2f);
    }

    public void draw(Batch batch) {
        for (int i = 0; i < SLOTS_AMOUNT; i++) {
            float currentX = startX + i * (initialSlotSize + initialPadding);
            batch.draw(slotIcon, currentX, startY, initialSlotSize, initialSlotSize);
        }
    }

    public static ScreenViewport getViewport() { return uiViewport; }
}
