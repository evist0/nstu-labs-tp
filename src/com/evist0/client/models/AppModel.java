package com.evist0.client.models;

import com.evist0.client.Client;
import com.evist0.client.config.Config;
import com.evist0.common.properties.ModelChangeListener;
import com.evist0.common.properties.ModelChangedEvent;
import com.evist0.common.properties.Property;
import com.evist0.common.entities.Entity;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class AppModel {
    private Config _config;

    private boolean _started;
    private Long _timePassed;

    private Long _N1, _N2;
    private float _P1, _P2;
    private Long _individualTtl, _companyTtl;

    private int _individualGenerated, _companyGenerated;
    private Vector<Entity> _taxpayers = new Vector<>();
    private final HashMap<UUID, Long> _taxpayerToTimestamp = new HashMap<>();

    private Rectangle _availableArea;

    private boolean _timerVisible;
    private boolean _dialogVisible;

    private boolean _individualMove;
    private boolean _companyMove;

    private final ArrayList<ModelChangeListener> _listeners = new ArrayList<>();

    public AppModel(Config config) {
        _config = config;

        _started = config.getStarted() != null ? config.getStarted() : false;
        _timePassed = config.getTimePassed() != null ? config.getTimePassed() : 0;

        _N1 = config.getN1() != null ? config.getN1() : 1L;
        _N2 = config.getN2() != null ? config.getN2() : 1L;
        _P1 = config.getP1() != null ? config.getP1() : 1;
        _P2 = config.getP2() != null ? config.getP2() : 1;
        _individualTtl = config.getIndividualTtl() != null ? config.getIndividualTtl() : 1L;
        _companyTtl = config.getCompanyTtl() != null ? config.getCompanyTtl() : 1L;

        _timerVisible = false;
        _dialogVisible = false;

        _individualMove = config.getIndividualMove() != null ? config.getIndividualMove() : true;
        _companyMove = config.getCompanyMove() != null ? config.getCompanyMove() : true;
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
    public void setN1(Long N1, boolean shouldNotify) {
        if (shouldNotify) {
            notifyServer(Property.N1, N1, _N1);
        }

        _N1 = N1;
        notifyListeners(Property.N1, _N1);
    }

    public Long getN1() {
        return _N1;
    }
    //endregion

    //region N2
    public void setN2(Long N2, boolean shouldNotify) {
        if (shouldNotify) {
            notifyServer(Property.N2, N2, _N2);
        }

        _N2 = N2;
        notifyListeners(Property.N2, _N2);
    }

    public Long getN2() {
        return _N2;
    }
    //endregion

    //region P1
    public void setP1(float P1, boolean shouldNotify) {
        if (shouldNotify) {
            notifyServer(Property.P1, P1, _P1);
        }

        _P1 = P1;
        notifyListeners(Property.P1, _P1);
    }

    public float getP1() {
        return _P1;
    }
    //endregion

    //region P2
    public void setP2(float P2, boolean shouldNotify) {
        if (shouldNotify) {
            notifyServer(Property.P2, P2, _P2);
        }

        _P2 = P2;
        notifyListeners(Property.P2, _P2);
    }

    public float getP2() {
        return _P2;
    }
    //endregion

    //region individualTtl
    public void setIndividualTtl(Long ttl, boolean shouldNotify) {
        if (shouldNotify) {
            notifyServer(Property.IndividualTtl, ttl, _individualTtl);
        }

        _individualTtl = ttl;
        notifyListeners(Property.IndividualTtl, _individualTtl);
    }

    public Long getIndividualTtl() {
        return _individualTtl;
    }
    //endregion

    //region companyTtl
    public void setCompanyTtl(Long ttl, boolean shouldNotify) {
        if (shouldNotify) {
            notifyServer(Property.CompanyTtl, ttl, _companyTtl);
        }

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

        taxpayers.forEach(taxpayer -> _taxpayerToTimestamp.put(taxpayer.getId(), taxpayer.getTimestamp()));
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
    public void setTimerVisible(boolean visible, boolean shouldNotify) {
        if (shouldNotify) {
            notifyServer(Property.TimerVisibility, visible, _timerVisible);
        }

        _timerVisible = visible;
        notifyListeners(Property.TimerVisibility, _timerVisible);
    }

    public boolean getTimerVisible() {
        return _timerVisible;
    }
    //endregion

    //region dialogVisible
    public void setDialogVisible(boolean visible, boolean shouldNotify) {
        if (shouldNotify) {
            notifyServer(Property.ResultsVisibility, visible, _dialogVisible);
        }

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
    public void setIndividualMove(boolean move, boolean shouldNotify) {
        if (shouldNotify) {
            notifyServer(Property.IndividualMove, move, _individualMove);
        }

        _individualMove = move;
        notifyListeners(Property.IndividualMove, _individualMove);
    }

    public boolean getIndividualMove() {
        return _individualMove;
    }
    //endregion

    //region companyMove
    public void setCompanyMove(boolean move, boolean shouldNotify) {
        if (shouldNotify) {
            notifyServer(Property.CompanyMove, move, _companyMove);
        }

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

    private <T> void notifyServer(Property property, T value, T previous) {
        if (value.equals(previous))
            return;

        var client = Client.getInstance();

        if (client == null)
            return;

        try {
            client.sendMessage("[set]" + property.toString() + ' ' + value.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void notifyListeners(Property property, T value) {
        for (ModelChangeListener l : _listeners) {
            var event = new ModelChangedEvent<>(property, value);
            l.modelChange(event);
        }
    }
    //endregion

    public void save() throws Exception {
        _config.save(this);
    }
}
