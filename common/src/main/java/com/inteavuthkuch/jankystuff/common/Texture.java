package com.inteavuthkuch.jankystuff.common;

import com.inteavuthkuch.jankystuff.Constants;
import net.minecraft.resources.Identifier;

import java.awt.*;

public record Texture(Identifier location, Point size, Point textureSize, Point inventory, Point playerInventory, Point hotbar, int colSize) {
    public static Texture container(String name, Point size, Point textureSize, Point inventory, Point playerInventory, Point hotbar, int colSize) {
        return new Texture(Constants.modId("textures/gui/container/" + name), size, textureSize, inventory, playerInventory, hotbar, colSize);
    }
    public static Texture container(String name, Point size, Point textureSize, Point inventory, Point playerInventory, Point hotbar) {
        return container(name, size, textureSize, inventory, playerInventory, hotbar, 18);
    }
}
