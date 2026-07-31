/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.utils;

import ic2.core.utils.config.utils.ParseExpection;
import java.util.Objects;
import java.util.function.Function;

public class ParseResult<T> {
    private final T value;
    private final ParseExpection error;

    private ParseResult(T value, ParseExpection error) {
        this.value = value;
        this.error = error;
    }

    public static ParseResult<Boolean> result(boolean isSuccess, Function<String, Exception> error, String message) {
        return new ParseResult<Boolean>(isSuccess, isSuccess ? null : new ParseExpection(null, error.apply(message), message));
    }

    public static <T> ParseResult<T> successOrError(T value, boolean isSuccess, Function<String, Exception> error, String message) {
        return new ParseResult<T>(value, isSuccess ? null : new ParseExpection(null, error.apply(message), message));
    }

    public static <T> ParseResult<T> success(T value) {
        return new ParseResult<T>(value, null);
    }

    public static <T> ParseResult<T> partial(T value, Exception e) {
        return new ParseResult<T>(value, new ParseExpection(null, e, null));
    }

    public static <T> ParseResult<T> partial(T value, Function<String, Exception> e, String message) {
        return new ParseResult<T>(value, new ParseExpection(null, e.apply(message), message));
    }

    public static <T> ParseResult<T> partial(T value, ParseExpection error) {
        return new ParseResult<T>(value, error);
    }

    public static <T> ParseResult<T> error(String value, Exception excpetion) {
        return new ParseResult<Object>(null, new ParseExpection(value, excpetion, null));
    }

    public static <T> ParseResult<T> error(String value, String message) {
        return new ParseResult<Object>(null, new ParseExpection(value, null, message));
    }

    public static <T> ParseResult<T> error(String value, Exception excpetion, String message) {
        return new ParseResult<Object>(null, new ParseExpection(value, excpetion, message));
    }

    public static <T> ParseResult<T> error(ParseExpection error) {
        return new ParseResult<Object>(null, error);
    }

    public T getValue() {
        return this.value;
    }

    public ParseExpection getError() {
        return this.error;
    }

    public boolean isValid() {
        return this.error == null;
    }

    public boolean hasError() {
        return this.error != null;
    }

    public <S> ParseResult<S> onlyError() {
        return new ParseResult<Object>(null, this.error);
    }

    public <S> ParseResult<S> withDefault(S value) {
        return new ParseResult<S>(value, this.error);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ParseResult pair = (ParseResult)o;
        return Objects.equals(this.value, pair.value) && Objects.equals(this.error, pair.error);
    }

    public int hashCode() {
        return Objects.hash(this.value, this.error);
    }

    public String toString() {
        return "ParseResult{value=" + this.value + ", error=" + this.error + "}";
    }
}

