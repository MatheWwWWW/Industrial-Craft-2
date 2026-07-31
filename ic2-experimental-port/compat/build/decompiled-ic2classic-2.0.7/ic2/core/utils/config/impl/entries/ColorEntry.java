/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.impl.entries;

import ic2.core.utils.config.api.buffer.IReadBuffer;
import ic2.core.utils.config.api.buffer.IWriteBuffer;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.utils.IEntryDataType;
import ic2.core.utils.config.utils.MultilinePolicy;
import ic2.core.utils.config.utils.ParseResult;

public class ColorEntry
extends ConfigEntry.BasicConfigEntry<ColorWrapper> {
    public ColorEntry(String key, int defaultValue, String ... comment) {
        super(key, new ColorWrapper(defaultValue), comment);
    }

    protected ColorEntry copy() {
        return new ColorEntry(this.getKey(), this.get(), this.getComment());
    }

    public final ColorEntry addSuggestions(int ... values) {
        for (int value : values) {
            this.addSuggestionInternal(Long.toHexString(0xFF00000000L | (long)value).substring(2), this.serializedValue(MultilinePolicy.DISABLED, new ColorWrapper(value)), ColorWrapper.class);
        }
        return this;
    }

    @Override
    public final ColorEntry addSuggestion(String name, int value) {
        return (ColorEntry)this.addSuggestionInternal(name, this.serializedValue(MultilinePolicy.DISABLED, new ColorWrapper(value)), ColorWrapper.class);
    }

    @Override
    public ParseResult<ColorWrapper> parseValue(String value) {
        ParseResult<Integer> result = ColorWrapper.parseInt(value);
        return result.hasError() ? result.onlyError() : ParseResult.success(new ColorWrapper(result.getValue()));
    }

    @Override
    public IEntryDataType getDataType() {
        return IEntryDataType.SimpleDataType.ofVariant(ColorWrapper.class);
    }

    public int get() {
        return ((ColorWrapper)this.getValue()).getColor();
    }

    @Override
    protected String serializedValue(MultilinePolicy policy, ColorWrapper value) {
        return ColorWrapper.serialize(value.getColor());
    }

    @Override
    public char getPrefix() {
        return 'C';
    }

    @Override
    public String getLimitations() {
        return "";
    }

    @Override
    public void serialize(IWriteBuffer buffer) {
        buffer.writeInt(this.get());
    }

    @Override
    protected void deserializeValue(IReadBuffer buffer) {
        this.set(new ColorWrapper(buffer.readInt()));
    }

    public static ParseResult<ColorEntry> parse(String key, String value, String ... comment) {
        ParseResult<Integer> result = ColorWrapper.parseInt(value);
        if (result.hasError()) {
            return result.withDefault(new ColorEntry(key, 0, comment));
        }
        return ParseResult.success(new ColorEntry(key, result.getValue(), comment));
    }

    public static class ColorWrapper {
        int color;

        public ColorWrapper(int color) {
            this.color = color;
        }

        public int getColor() {
            return this.color;
        }

        public static ParseResult<Integer> parseInt(String value) {
            try {
                return ParseResult.success(Long.decode(value).intValue());
            }
            catch (Exception e) {
                return ParseResult.error(value, e, "Couldn't parse Number");
            }
        }

        public static String serialize(long color) {
            return "0x" + Long.toHexString(0xFF00000000L | color & 0xFFFFFFFFL).substring(2);
        }
    }
}

