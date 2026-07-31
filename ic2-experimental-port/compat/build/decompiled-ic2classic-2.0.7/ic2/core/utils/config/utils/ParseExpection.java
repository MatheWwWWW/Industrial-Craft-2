/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.utils;

public class ParseExpection {
    final String value;
    final Exception expection;
    final String message;
    final int index;

    public ParseExpection(String value, Exception expection, String message) {
        this(value, expection, message, -1);
    }

    public ParseExpection(String value, Exception expection, String message, int index) {
        this.value = value;
        this.expection = expection;
        this.message = message;
        this.index = index;
    }

    public String getValue() {
        return this.value;
    }

    public Exception getExpection() {
        return this.expection;
    }

    public String getMessage() {
        return this.message;
    }

    public int getIndex() {
        return this.index;
    }

    public ParseExpection withIndex(int index) {
        return new ParseExpection(this.value, this.expection, this.message, index);
    }
}

