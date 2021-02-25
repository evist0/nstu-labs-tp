package com.evist0.models;

public class Model {
    private boolean _started;
    private int _N1, _N2;
    private float _P1, _P2;

    public Model() {
        _started = false;
        _N1 = 1;
        _N2 = 1;
        _P1 = 1;
        _P2 = 1;
    }

    public void set_started(boolean value) {
        _started = value;
    }

    public boolean get_started() {
        return _started;
    }

    public void set_N1(int _N1) {
        this._N1 = _N1;
    }

    public int get_N1() {
        return _N1;
    }

    public void set_N2(int _N2) {
        this._N2 = _N2;
    }

    public int get_N2() {
        return _N2;
    }

    public void set_P1(float _P1) {
        this._P1 = _P1;
    }

    public float get_P1() {
        return _P1;
    }

    public void set_P2(float _P2) {
        this._P2 = _P2;
    }

    public float get_P2() {
        return _P2;
    }

}
