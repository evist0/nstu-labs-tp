package com.evist0.dto.settings;

public class SettingsException extends Exception {
    private final SettingsExceptionField _field;

    SettingsException(SettingsExceptionField field, String message) {
        super(message);

        _field = field;
    }

    public SettingsExceptionField getExceptionField() {
        return _field;
    }
}
