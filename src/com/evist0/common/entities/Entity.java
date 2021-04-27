package com.evist0.common.entities;

import com.evist0.common.dto.taxpayers.TaxpayerDTO;
import com.evist0.common.moveSystem.MoveTask;
import com.evist0.common.moveSystem.Vector2d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

public abstract class Entity implements Serializable {
    private UUID _id;

    private BufferedImage _image;

    private Vector2d _position;
    private MoveTask _moveTask;

    private Long _timestamp;
    private Long _ttl;

    Entity(TaxpayerDTO dto) {
        _id = UUID.randomUUID();

        _timestamp = dto.timestamp;
        _ttl = dto.ttl;

        _position = dto.position;
        _image = dto.image;

        this.moveTo(dto.destination, dto.speed);
    }

    public void draw(Graphics g) {
        var point = _position.toPoint();

        g.drawImage(_image, point.x, point.y, null);
    }

    public UUID getId() {
        return _id;
    }

    public void moveTo(Vector2d destination, float speed) {
        if (destination.x == _position.x && destination.y == _position.y) {
            return;
        }

        var delta = new Vector2d(destination.x - _position.x, destination.y - _position.y)
                .normalize()
                .multiply(speed);

        _moveTask = new MoveTask(destination, delta);
    }

    public void doMoveTask(float deltaTime) {
        if (this._moveTask != null) {
            var destination = this._moveTask.getDestination();
            var delta = this._moveTask.getSpeed().multiply(deltaTime);

            if (delta.x >= 0) {
                if (this._position.x + delta.x >= destination.x) {
                    this._position.x = destination.x;
                } else {
                    this._position.x += delta.x;
                }
            } else {
                if (this._position.x + delta.x <= destination.x) {
                    this._position.x = destination.x;
                } else {
                    this._position.x += delta.x;
                }
            }

            if (delta.y >= 0) {
                if (this._position.y + delta.y >= destination.y) {
                    this._position.y = destination.y;
                } else {
                    this._position.y += delta.y;
                }
            } else {
                if (this._position.y + delta.y <= destination.y) {
                    this._position.y = destination.y;
                } else {
                    this._position.y += delta.y;
                }
            }
        }
    }

    public boolean isMoving() {
        return _moveTask != null;
    }

    public Long getTimestamp() {
        return _timestamp;
    }

    public Long getTtl() {
        return _ttl;
    }

    @Serial
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(_id);
        stream.writeObject(_position);
        stream.writeObject(_moveTask);
        stream.writeObject(_timestamp);
        stream.writeObject(_ttl);
        ImageIO.write(_image, "png", stream);
    }

    @Serial
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        _id = (UUID) stream.readObject();
        _position = (Vector2d) stream.readObject();
        _moveTask = (MoveTask) stream.readObject();
        _timestamp = (Long) stream.readObject();
        _ttl = (Long) stream.readObject();
        _image = ImageIO.read(stream);
    }
}
