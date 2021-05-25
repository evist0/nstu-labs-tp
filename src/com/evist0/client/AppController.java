package com.evist0.client;

import com.evist0.client.models.AppModel;
import com.evist0.client.views.console.ConsoleView;
import com.evist0.client.views.objects.ObjectsDialog;
import com.evist0.common.dto.settings.SettingsDTO;
import com.evist0.common.entities.Entity;
import com.evist0.common.simulation.Simulation;
import com.evist0.common.simulation.middlewares.DestroyMiddleware;
import com.evist0.common.simulation.middlewares.FactoryMiddleware;
import com.evist0.common.simulation.middlewares.MoveMiddleware;
import com.evist0.common.factories.TaxpayerFactory;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.*;
import java.util.Vector;

public class AppController {
    private final AppModel _model;
    private final Simulation _simulation;

    private Database _database;
    private final ConsoleView _console;

    public AppController(AppModel model) {
        _model = model;

        _simulation = new Simulation(_model)
                .use(new DestroyMiddleware())
                .use(new FactoryMiddleware(new TaxpayerFactory()))
                .use(new MoveMiddleware());

        _console = new ConsoleView(model);

        try {
            _database = new Database(model);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setModel(SettingsDTO dto) {
        _model.setN1(dto.N1, true);
        _model.setN2(dto.N2, true);
        _model.setP1(dto.P1, true);
        _model.setP2(dto.P2, true);
        _model.setIndividualTtl(dto.individualTtl, true);
        _model.setCompanyTtl(dto.companyTtl, true);

        _model.setTimePassed(0L);
        _model.setIndividualGenerated(0);
        _model.setCompanyGenerated(0);
        _model.resetTaxpayers();
    }

    public void startSimulation(SettingsDTO dto) {
        setModel(dto);
        _simulation.start();
    }

    public void resumeSimulation() {
        _simulation.start();
    }

    public void stopSimulation() {
        _simulation.stop();
    }

    public void toggleSimulation(SettingsDTO dto) {
        var started = _model.getStarted();

        if (!started) {
            startSimulation(dto);
        } else {
            stopSimulation();
        }
    }

    public void toggleTimerVisible() {
        var visible = _model.getTimerVisible();
        _model.setTimerVisible(!visible, true);
    }

    public void setTimerVisible(boolean visible) {
        _model.setTimerVisible(visible, true);
    }

    public void setDialogVisible(boolean visible) {
        _model.setDialogVisible(visible, true);
    }

    public void setIndividualMove(boolean move) {
        _model.setIndividualMove(move, true);
    }

    public void setCompanyMove(boolean move) {
        _model.setCompanyMove(move, true);
    }

    public void updateAvailableArea(Rectangle availableArea) {
        _model.setAvailableArea(availableArea);
    }

    public void showObjectsDialog() {
        new ObjectsDialog(_model.getTaxpayerToTimestamp());
    }

    public void saveObjects() {
        boolean previousSimulationState = false;

        if (_simulation != null) {
            previousSimulationState = _simulation.running();
        }

        try {
            var outputStream = new FileOutputStream("entities.dat");
            var objectOutputStream = new ObjectOutputStream(outputStream);

            if (previousSimulationState) {
                _simulation.stop();
            }

            var taxpayers = _model.getTaxpayers();

            objectOutputStream.writeObject(taxpayers);
            objectOutputStream.close();

            if (previousSimulationState) {
                _simulation.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadObjects() {
        boolean previousSimulationState = false;

        if (_simulation != null) {
            previousSimulationState = _simulation.running();
        }

        try {
            var inputStream = new FileInputStream("entities.dat");
            var objectInputStream = new ObjectInputStream(inputStream);

            if (previousSimulationState) {
                _simulation.stop();
            }

            @SuppressWarnings("unchecked")
            Vector<Entity> taxpayers = (Vector<Entity>) objectInputStream.readObject();

            _model.setTaxpayers(taxpayers);
            objectInputStream.close();

            if (previousSimulationState) {
                _simulation.start();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveObjectsDatabase(boolean individual, boolean company) {
        this._database.saveEntities(individual, company);
    }

    public void loadObjectsDatabase() {
        this._database.loadEntities();
    }

    public void toggleConsole() {
        _console.setVisible(!_console.isVisible());
    }
}
