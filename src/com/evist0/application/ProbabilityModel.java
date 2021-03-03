package com.evist0.application;

public class ProbabilityModel {
    private final float _value;
    private final String _string;

    public ProbabilityModel(float value, String string) {
        _value = value;
        _string = string;
    }

    public float getValue() {
        return _value;
    }

    @Override
    public String toString() {
        return _string;
    }
}
