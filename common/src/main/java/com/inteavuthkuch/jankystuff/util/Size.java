package com.inteavuthkuch.jankystuff.util;

public record Size<T extends Number>(T width, T height) {
    public static Size<Integer> sizeInt(int width, int height) {
        return new Size<>(width, height);
    }
    public static Size<Float> sizeFloat(float width, float height) {
        return new Size<>(width, height);
    }
    public static Size<Double> sizeDouble(double width, double height) {
        return new Size<>(width, height);
    }
}
