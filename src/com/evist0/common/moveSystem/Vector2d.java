package com.evist0.common.moveSystem;

import java.awt.*;
import java.io.Serializable;

public class Vector2d implements Serializable {
    public float x;
    public float y;

    public Vector2d(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d normalize() {
        var magnitude = Math.sqrt(x * x + y * y);

        var normalized = new Vector2d(x, y);
        normalized.x /= magnitude;
        normalized.y /= magnitude;

        return normalized;
    }

    public Vector2d multiply(float multiplier) {
        var multiplied = new Vector2d(x, y);

        multiplied.x *= multiplier;
        multiplied.y *= multiplier;

        return multiplied;
    }

    public Point toPoint() {
        return new Point(Math.round(this.x), Math.round(this.y));
    }
}
