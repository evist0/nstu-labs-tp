package com.evist0.client.models;

import com.evist0.client.config.Config;
import com.evist0.common.properties.ModelChangeListener;
import com.evist0.common.properties.ModelChangedEvent;
import com.evist0.common.properties.Property;
import com.evist0.common.entities.Entity;

import java.awt.*;
import java.util.*;

public class AppModel {
    private boolean _started;
    private Long _timePassed;

    private Long _N1, _N2;
    private float _P1, _P2;
    private Long _individualTtl, _companyTtl;

    private int _individualGenerated, _companyGenerated;
    private Vector<Entity> _taxpayers = new Vector<>();
    private HashMap<UUID, Long> _taxpayerToTimestamp = new HashMap<>();

    private Rectangle _availableArea;

    private boolean _timerVisible;
    private boolean _dialogVisible;

    private boolean _individualMove;
    private boolean _companyMove;

    private final ArrayList<ModelChangeListener> _listeners = new ArrayList<>();

    public AppModel(Config config) {
        _started = config.started != null ? config.started : false;
        _timePassed = config.timePassed != null ? config.timePassed : 0;

        _N1 = config.N1 != null ? config.N1 : 1L;
        _N2 = config.N2 != null ? config.N2 : 1L;
        _P1 = config.P1 != null ? config.P1 : 1;
        _P2 = config.P2 != null ? config.P2 : 1;
        _individualTtl = config.individualTtl != null ? config.individualTtl : 1L;
        _companyTtl = config.companyTtl != null ? config.companyTtl : 1L;

        _timerVisible = false;
        _dialogVisible = false;

        _individualMove = config.individualMove != null ? config.individualMove : true;
        _companyMove = config.companyMove != null ? config.companyMove : true;
    }

    //region started
    public void setStarted(boolean started) {
        _started = started;
        notifyListeners(Property.Started, _started);
    }

    public boolean getStarted() {
        return _started;
    }
    //endregion

    //region timePassed
    public void setTimePassed(Long timePassed) {
        _timePassed = timePassed;
        notifyListeners(Property.TimePassed, _timePassed);
    }

    public Long getTimePassed() {
        return _timePassed;
    }
    //endregion

    // region individualGenerated
    public void setIndividualGenerated(int generated) {
        _individualGenerated = generated;
    }

    public int getIndividualGenerated() {
        return _individualGenerated;
    }
    //endregion

    //region companyGenerated
    public void setCompanyGenerated(int generated) {
        _companyGenerated = generated;
    }

    public int getCompanyGenerated() {
        return _companyGenerated;
    }
    //endregion

    //region N1
    public void setN1(Long N1) {
        _N1 = N1;
        notifyListeners(Property.N1, _N1);
    }

    public Long getN1() {
        return _N1;
    }
    //endregion

    //region N2
    public void setN2(Long N2) {
        _N2 = N2;
        notifyListeners(Property.N2, _N2);
    }

    public Long getN2() {
        return _N2;
    }
    //endregion

    //region P1
    public void setP1(float P1) {
        _P1 = P1;
        notifyListeners(Property.P1, _P1);
    }

    public float getP1() {
        return _P1;
    }
    //endregion

    //region P2
    public void setP2(float P2) {
        _P2 = P2;
        notifyListeners(Property.P2, _P2);
    }

    public float getP2() {
        return _P2;
    }
    //endregion

    //region individualTtl
    public void setIndividualTtl(Long ttl) {
        _individualTtl = ttl;
        notifyListeners(Property.IndividualTtl, _individualTtl);
    }

    public Long getIndividualTtl() {
        return _individualTtl;
    }
    //endregion

    //region companyTtl
    public void setCompanyTtl(Long ttl) {
        _companyTtl = ttl;
        notifyListeners(Property.CompanyTtl, _companyTtl);
    }

    public Long getCompanyTtl() {
        return _companyTtl;
    }
    //endregion

    //region taxpayers
    public void setTaxpayers(Vector<Entity> taxpayers) {
        _taxpayers = taxpayers;

        _taxpayerToTimestamp.clear();

        taxpayers.forEach(taxpayer -> {
            _taxpayerToTimestamp.put(taxpayer.getId(), taxpayer.getTimestamp());
        });
    }

    public void addTaxpayer(Entity taxpayer) {
        _taxpayers.add(taxpayer);
        _taxpayerToTimestamp.put(taxpayer.getId(), taxpayer.getTimestamp());

        notifyListeners(Property.Taxpayers, _taxpayers);
    }

    public void removeTaxpayer(Entity taxpayer) {
        _taxpayers.remove(taxpayer);
        _taxpayerToTimestamp.remove(taxpayer.getId());

        notifyListeners(Property.Taxpayers, _taxpayers);
    }

    public Vector<Entity> getTaxpayers() {
        return _taxpayers;
    }

    public HashMap<UUID, Long> getTaxpayerToTimestamp() {
        return _taxpayerToTimestamp;
    }

    public void resetTaxpayers() {
        _taxpayers.clear();
        _taxpayerToTimestamp.clear();

        notifyListeners(Property.Taxpayers, _taxpayers);
    }
    //endregion

    //region timerVisible
    public void setTimerVisible(boolean visible) {
        _timerVisible = visible;
        notifyListeners(Property.TimerVisibility, _timerVisible);
    }

    public boolean getTimerVisible() {
        return _timerVisible;
    }
    //endregion

    //region dialogVisible
    public void setDialogVisible(boolean visible) {
        _dialogVisible = visible;
        notifyListeners(Property.ResultsVisibility, _dialogVisible);
    }

    public boolean getDialogVisible() {
        return _dialogVisible;
    }
    //endregion

    //region availableArea
    public void setAvailableArea(Rectangle availableArea) {
        _availableArea = availableArea;
    }

    public Rectangle getAvailableArea() {
        return _availableArea;
    }
    //endregion

    //region individualMove
    public void setIndividualMove(boolean move) {
        _individualMove = move;
        notifyListeners(Property.IndividualMove, _individualMove);
    }

    public boolean getIndividualMove() {
        return _individualMove;
    }
    //endregion

    //region companyMove
    public void setCompanyMove(boolean move) {
        _companyMove = move;
        notifyListeners(Property.CompanyMove, _companyMove);
    }

    public boolean getCompanyMove() {
        return _companyMove;
    }
    //endregion

    //region modelListeners
    public void addModelChangedListener(ModelChangeListener l) {
        _listeners.add(l);
    }

    public void removeModelChangedListener(ModelChangeListener l) {
        _listeners.remove(l);
    }

    public <T> void notifyListeners(Property property, T value) {
        for (ModelChangeListener l : _listeners) {
            var event = new ModelChangedEvent<>(property, value);
            l.modelChange(event);
        }
    }
    //endregion
}
