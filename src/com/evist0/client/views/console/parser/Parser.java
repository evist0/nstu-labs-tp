package com.evist0.client.views.console.parser;

public interface Parser<T> {
    Result<T> parse(Input input);

    static Parser<Character> match(char character) {
        return input -> {
            Result<Character> result = input.getCharacter();

            if (result.hasValue() && result.getValue() == character)
                return Result.fromValue(character, result.getRemainder());

            return Result.empty(input);
        };
    }

    static Parser<String> match(String string) {
        return input -> {
            Input remainder = input;
            for (int i = 0; i < string.length(); i++) {

                Result<Character> result = remainder.getCharacter();

                if (!result.hasValue() || result.getValue() != string.charAt(i)) {
                    return Result.empty(remainder);
                }

                remainder = remainder.skip(1);
            }

            return Result.fromValue(string, remainder);
        };
    }

    static Parser<Object> ignore(IgnorePredicate ignorePredicate) {
        return input -> {
            Input remainder = input;

            while (remainder.getCharacter().hasValue() && ignorePredicate.predicate(remainder.getCharacter().getValue())) {
                remainder = input.skip(1);
            }

            return Result.fromValue(new Object(), remainder);
        };
    }

    static <T, U> Parser<U> pipe(Parser<T> source, ParserFactory<T, U> destinationFactory) {
        return input -> {
            Result<T> result = source.parse(input);

            if (result.hasValue()) {
                return destinationFactory.create(result.getValue()).parse(result.getRemainder());
            }

            return Result.empty(input);
        };
    }

    static <T> Parser<T> value(T value) {
        return input -> Result.fromValue(value, input);
    }

    static <T, U> Parser<U> then(Parser<T> lhs, Parser<U> rhs) {
        return input -> {
            Result<T> result = lhs.parse(input);

            if (result.hasValue()) {
                return rhs.parse(result.getRemainder());
            }

            return Result.empty(input);
        };
    }

    static <T> Parser<T> either(Parser<T> lhs, Parser<T> rhs) {
        return input -> {
            Result<T> result = lhs.parse(input);

            if (result.hasValue()) {
                return result;
            }

            return rhs.parse(input);
        };
    }
}
