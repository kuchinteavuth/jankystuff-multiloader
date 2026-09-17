package com.inteavuthkuch.jankystuff.common;

public class CrateMaterials {
    private CrateMaterials() {}

    public static final CrateMaterial WOODEN = new CrateMaterial("wood", 6, 9, Textures.CONTAINER_6X9);
    public static final CrateMaterial METAL = new CrateMaterial("metal", 8, 9, Textures.CONTAINER_8X9);
}
