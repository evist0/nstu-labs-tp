package com.evist0.dto.settings;

import com.evist0.application.AppModel;

interface SettingsExceptionCallback {
    void run(SettingsException exception);
}

public class SettingsExceptionHandler {
    private final AppModel _model;

    public SettingsExceptionHandler(AppModel model) {
        _model = model;
    }

    public void handleException(SettingsException exception) {
        switch (exception.getExceptionField()) {
            case N1 -> _model.setN1(1L);
            case N2 -> _model.setN2(1L);
            case IndividualTtl -> _model.setIndividualTtl(1L);
            case CompanyTtl -> _model.setCompanyTtl(1L);
        }
    }

    public void handleException(SettingsException exception, SettingsExceptionCallback callback) {
        handleException(exception);
        callback.run(exception);
    }
}
