package com.evist0.common.properties;

public interface ModelChangeListener {
    <T> void modelChange(ModelChangedEvent<T> evt);
}
