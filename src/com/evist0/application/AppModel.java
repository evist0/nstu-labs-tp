package com.evist0.application;

import com.evist0.properties.ModelChangeListener;
import com.evist0.properties.ModelChangedEvent;
import com.evist0.properties.Property;
import com.evist0.taxpayer.AbstractTaxpayer;

import java.util.ArrayList;

public class AppModel {
    private boolean _started;

    private int _N1, _N2;
    private float _P1, _P2;

    private final ArrayList<AbstractTaxpayer> _taxpayers = new ArrayList<>();

    private final ArrayList<ModelChangeListener> _listeners = new ArrayList<>();

    public AppModel() {
        _started = false;
        _N1 = 1;
        _N2 = 1;
        _P1 = 1;
        _P2 = 1;
    }

    public void addModelChangedListener(ModelChangeListener l) {
        _listeners.add(l);
    }

    public void removeModelChangedListener(ModelChangeListener l) {
        _listeners.remove(l);
    }

    public void setStarted(boolean started) {
        _started = started;
        notifyListeners(Property.Started, _started);
    }

    public boolean getStarted() {
        return _started;
    }

    public void setN1(int N1) {
        _N1 = N1;
        notifyListeners(Property.N1, _N1);
    }

    public int getN1() {
        return _N1;
    }

    public void setN2(int N2) {
        _N2 = N2;
        notifyListeners(Property.N2, _N2);
    }

    public int getN2() {
        return _N2;
    }

    public void setP1(float P1) {
        _P1 = P1;
        notifyListeners(Property.P1, _P1);
    }

    public float getP1() {
        return _P1;
    }

    public void setP2(float P2) {
        _P2 = P2;
        notifyListeners(Property.P2, _P2);
    }

    public float getP2() {
        return _P2;
    }

    public void addTaxpayer(AbstractTaxpayer taxpayer) {
        _taxpayers.add(taxpayer);
        notifyListeners(Property.Taxpayers, _taxpayers);
    }

    public void removeTaxpayer(AbstractTaxpayer taxpayer) {
        _taxpayers.remove(taxpayer);
        notifyListeners(Property.Taxpayers, _taxpayers);
    }

    private <T> void notifyListeners(Property property, T value) {
        for (ModelChangeListener l : _listeners) {
            var event = new ModelChangedEvent<>(property, value);
            l.modelChange(event);
        }
    }
}
