package com.evist0.common.dto.settings;

import com.evist0.client.models.AppModel;

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
            case N1 -> _model.setN1(1L, true);
            case N2 -> _model.setN2(1L, true);
            case IndividualTtl -> _model.setIndividualTtl(1L, true);
            case CompanyTtl -> _model.setCompanyTtl(1L, true);
        }
    }

    public void handleException(SettingsException exception, SettingsExceptionCallback callback) {
        handleException(exception);
        callback.run(exception);
    }
}
