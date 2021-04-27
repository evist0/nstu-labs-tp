package com.evist0.client.views.console.parser;

public interface ParserFactory<T, U> {
    Parser<U> create(T value);
}
