package com.evist0.client.views.console.parser;

public class Input {
    private final String value;

    public Input(String value) {
        this.value = value;
    }

    public Result<Character> getCharacter() {
        if (value.isEmpty()) {
            return Result.empty(this);
        }

        return Result.fromValue(value.charAt(0), skip(1));
    }

    public Input skip(int count) {
        return new Input(value.substring(count));
    }

    public String getValue() {
        return value;
    }
}
