package com.evist0.properties;

public class ModelChangedEvent<T> {
    private final Property _property;
    private final T _value;

    public ModelChangedEvent(Property property, T newValue) {
        _property = property;
        _value = newValue;
    }

    public Property getProperty() {
        return _property;
    }

    public T getValue() {
        return _value;
    }
}
