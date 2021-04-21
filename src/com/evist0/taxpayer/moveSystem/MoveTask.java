package com.evist0.taxpayer.moveSystem;

public class MoveTask {
    private final Vector2d _destination;
    private final Vector2d _speed;

    public MoveTask(Vector2d destination, Vector2d speed) {
        _destination = destination;
        _speed = speed;
    }

    public Vector2d getDestination() {
        return _destination;
    }

    public Vector2d getSpeed() {
        return _speed;
    }
}
