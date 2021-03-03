package com.evist0.models;

public class ResultModel {
    private int _individualCounter, _companyCounter, _timePassed;

    public ResultModel(int individualCounter, int companyCounter, int timePassed) {
        _individualCounter = individualCounter;
        _companyCounter = companyCounter;
        _timePassed = timePassed;
    }

    public int get_individualCounter() {
        return _individualCounter;
    }

    public int get_companyCounter() {
        return _companyCounter;
    }

    public int get_timePassed() {
        return _timePassed;
    }
}
