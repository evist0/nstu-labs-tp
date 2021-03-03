package com.evist0.properties;

public interface ModelChangeListener {
    <T> void modelChange(ModelChangedEvent<T> evt);
}
