package com.inteavuthkuch.jankystuff.common;

import java.awt.*;

public final class Textures {
    private Textures() throws IllegalAccessException {
        throw new IllegalAccessException("You're not suppose to create instance of this class");
    }

    public static final Texture CONTAINER_6X9 = Texture.container("container_6x9.png",
            new Point(176, 222),
            new Point(256, 256),
            new Point(7, 17),
            new Point(8, 140),
            new Point(8, 198)
    );

    public static final Texture CONTAINER_8X9 = Texture.container("container_8x9.png",
            new Point(176, 256),
            new Point(256, 256),
            new Point(8, 18),
            new Point(8, 174),
            new Point(8, 232)
    );
}
