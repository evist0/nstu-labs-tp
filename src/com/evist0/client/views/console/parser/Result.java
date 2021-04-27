package com.evist0.client.views.console.parser;

public class Result<T> {
    private final T value;
    private final Input remainder;

    private Result(T value, Input remainder) {
        this.value = value;
        this.remainder = remainder;
    }

    public static <T> Result<T> empty(Input remainder) {
        return new Result<>(null, remainder);
    }

    public static <T> Result<T> fromValue(T value, Input remainder) {
        if (value == null)
            throw new NullPointerException("value");

        return new Result<>(value, remainder);
    }

    public boolean hasValue() {
        return value != null;
    }

    public T getValue() {
        return value;
    }

    public Input getRemainder() {
        return remainder;
    }
}
