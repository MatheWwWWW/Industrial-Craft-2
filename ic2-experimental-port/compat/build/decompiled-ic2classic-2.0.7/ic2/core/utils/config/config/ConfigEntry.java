/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 */
package ic2.core.utils.config.config;

import ic2.core.utils.config.api.IConfigSerializer;
import ic2.core.utils.config.api.IReloadMode;
import ic2.core.utils.config.api.ISuggestedEnum;
import ic2.core.utils.config.api.buffer.IReadBuffer;
import ic2.core.utils.config.api.buffer.IWriteBuffer;
import ic2.core.utils.config.config.ConfigHandler;
import ic2.core.utils.config.config.MappedConfig;
import ic2.core.utils.config.config.SyncedConfig;
import ic2.core.utils.config.utils.Helpers;
import ic2.core.utils.config.utils.IEntryDataType;
import ic2.core.utils.config.utils.MultilinePolicy;
import ic2.core.utils.config.utils.ParseResult;
import ic2.core.utils.config.utils.SyncType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class ConfigEntry<T> {
    private String key;
    private T value;
    private T defaultValue;
    private T lastValue;
    private String[] comment;
    private boolean used = false;
    private boolean serverSync = false;
    private IReloadMode reload = null;
    private SyncedConfig<ConfigEntry<T>> syncCache;
    private List<Suggestion> suggestions = new ObjectArrayList();

    public ConfigEntry(String key, T defaultValue, String ... comment) {
        if (Helpers.validateString(key)) {
            throw new IllegalArgumentException("ConfigEntry key must not be null, empty or start/end with white spaces");
        }
        if (key.contains(":") || key.contains("=")) {
            throw new IllegalArgumentException("ConfigEntry key must not contain any ':' or '=' signs. Key: " + key);
        }
        if (defaultValue == null) {
            throw new IllegalArgumentException("ConfigEntry default value must not be null. Key: " + key);
        }
        this.key = key;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.comment = Helpers.validateComments(comment);
    }

    public String[] getComment() {
        return this.comment;
    }

    void parseComment(String ... comment) {
        if (this.comment == null) {
            this.comment = Helpers.validateComments(comment);
        }
    }

    public ConfigEntry<T> setComment(String ... comment) {
        this.comment = Helpers.validateComments(comment);
        return this;
    }

    protected ConfigEntry<T> deepCopy() {
        ConfigEntry<T> copy = this.copy();
        copy.suggestions.addAll(this.suggestions);
        return copy;
    }

    protected abstract ConfigEntry<T> copy();

    public T getValue() {
        return this.value;
    }

    public T getDefault() {
        return this.defaultValue;
    }

    public abstract ParseResult<T> parseValue(String var1);

    public ParseResult<Boolean> canSetValue(String value) {
        ParseResult<T> result = this.parseValue(value);
        return result.hasError() ? result.withDefault(false) : this.canSet(result.getValue());
    }

    public ParseResult<Boolean> canSet(T value) {
        return ParseResult.result(value != null, NullPointerException::new, "Value isn't allowed to be null");
    }

    public ConfigEntry<T> set(T value) {
        if (value != null) {
            this.value = value;
        }
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public abstract IEntryDataType getDataType();

    protected final <S extends ConfigEntry<T>> S addSuggestionInternal(String value) {
        return this.addSuggestionInternal(value, value, null);
    }

    protected final <S extends ConfigEntry<T>> S addSuggestionInternal(String name, String value) {
        return this.addSuggestionInternal(name, value, null);
    }

    protected final <S extends ConfigEntry<T>> S addSuggestionInternal(Object extra, String value) {
        return this.addSuggestionInternal(value, value, extra);
    }

    protected final <S extends ConfigEntry<T>> S addSuggestionInternal(String name, String value, Object extra) {
        if (!this.canSetValue(value).getValue().booleanValue()) {
            throw new IllegalArgumentException("Value [" + value + "] is not valid. Meaning it can not be a suggestion");
        }
        this.suggestions.add(new Suggestion(name, value, extra));
        return (S)this;
    }

    public final List<Suggestion> getSuggestions() {
        return this.suggestions;
    }

    public final <S extends ConfigEntry<T>> S clearSuggestions() {
        this.suggestions.clear();
        return (S)this;
    }

    final boolean isUsed() {
        return this.used;
    }

    final ConfigEntry<T> setUsed() {
        this.used = true;
        return this;
    }

    public final boolean hasChanged() {
        return this.used && (this.value.getClass().isArray() ? !Objects.deepEquals(this.lastValue, this.value) : !Objects.equals(this.lastValue, this.value));
    }

    public final boolean isDefault() {
        return this.used && (this.value.getClass().isArray() ? Objects.deepEquals(this.defaultValue, this.value) : Objects.equals(this.defaultValue, this.value));
    }

    public final IReloadMode getReloadState() {
        return this.reload;
    }

    public final void onSynced() {
        this.lastValue = this.value;
    }

    final SyncType getSyncType() {
        return this.syncCache != null ? SyncType.CLIENT_TO_SERVER : (this.serverSync ? SyncType.SERVER_TO_CLIENT : SyncType.NONE);
    }

    public final <S extends ConfigEntry<T>> S setServerSynced() {
        if (this.syncCache != null) {
            throw new IllegalStateException("Client Synced Configs can not Server Sync");
        }
        this.serverSync = true;
        return (S)this;
    }

    public final <S extends ConfigEntry<T>> SyncedConfig<S> setClientSynced() {
        if (this.serverSync) {
            throw new IllegalStateException("Server Synced Configs can not Client Sync");
        }
        if (this.syncCache == null) {
            this.syncCache = new SyncedConfig<ConfigEntry>(() -> this.copy(), this);
        }
        return this.syncCache;
    }

    public final <S extends ConfigEntry<T>> S setRequiredReload(IReloadMode mode) {
        this.reload = mode;
        return (S)this;
    }

    public ConfigEntry<T> setKey(String key) {
        if (Helpers.validateString(key)) {
            throw new IllegalArgumentException("ConfigEntry key must not be null, empty or start/end with white spaces");
        }
        if (key.contains(":") || key.contains("=")) {
            throw new IllegalArgumentException("ConfigEntry key must not contain any ':' or '=' signs. Key: " + key);
        }
        this.key = key;
        return this;
    }

    public abstract char getPrefix();

    public ParseResult<String> deserializeValue(String value) {
        ParseResult<T> result = this.parseValue(value);
        if (result.hasError()) {
            return result.withDefault(value);
        }
        this.set(result.getValue());
        return ParseResult.success(value);
    }

    public void resetDefault() {
        this.value = this.defaultValue;
    }

    public String serializeDefault() {
        return this.serializedValue(MultilinePolicy.DISABLED, this.defaultValue);
    }

    public String serialize() {
        return this.serializedValue(MultilinePolicy.DISABLED, this.value);
    }

    protected String serializedValue(MultilinePolicy policy, T value) {
        return String.valueOf(value);
    }

    protected String serializeArray(MultilinePolicy policy, String ... lines) {
        if (policy == MultilinePolicy.MULTILINE_IF_TO_LONG) {
            StringBuilder builder = new StringBuilder();
            int lineAmount = 0;
            for (String s : lines) {
                if (lineAmount > 0 && lineAmount + s.length() > 75) {
                    builder.append('\n');
                    lineAmount = 0;
                }
                builder.append(s).append(", ");
                lineAmount += s.length() + 2;
            }
            builder.setLength(builder.length() - 2);
            return builder.toString();
        }
        StringJoiner joiner = new StringJoiner(policy == MultilinePolicy.ALWAYS_MULTILINE ? ", \n" : ", ");
        for (String s : lines) {
            joiner.add(s);
        }
        return joiner.toString();
    }

    public abstract String getLimitations();

    public final String serialize(MultilinePolicy policy, int indentationLevel) {
        String limits;
        String indentation = "\n" + Helpers.generateIndent(indentationLevel);
        StringBuilder builder = new StringBuilder();
        if (this.comment != null && this.comment.length > 0) {
            builder.append('\n');
            for (int i = 0; i < this.comment.length; ++i) {
                builder.append(indentation);
                builder.append("# ");
                builder.append(this.comment[i].replaceAll("\\R", indentation + "# "));
            }
        }
        if ((limits = this.getLimitations()) != null && !limits.isEmpty()) {
            if (builder.length() == 0) {
                builder.append("\n");
            }
            builder.append(indentation);
            builder.append("#").append('\u200b').append(" ");
            builder.append(limits);
        }
        builder.append(indentation);
        builder.append(this.getPrefix());
        builder.append(':');
        builder.append(this.key);
        builder.append('=');
        String line = this.serializedValue(policy, this.value);
        if (policy != MultilinePolicy.DISABLED && this instanceof IArrayConfig && line.contains("\n")) {
            String indent = "\n" + Helpers.generateIndent(indentationLevel + 1);
            builder.append(" < ").append(indent).append(line.replaceAll("\\R", indent));
            builder.append(indentation).append(">");
        } else {
            builder.append(line);
        }
        return builder.toString();
    }

    public abstract void serialize(IWriteBuffer var1);

    public void deserialize(IReadBuffer buffer, UUID owner) {
        if (this.syncCache != null) {
            this.syncCache.onSync(buffer, owner);
            return;
        }
        this.deserializeValue(buffer);
    }

    protected abstract void deserializeValue(IReadBuffer var1);

    public static class Suggestion {
        String name;
        String value;
        Object extra;

        public Suggestion(String value) {
            this(value, value, null);
        }

        public Suggestion(String value, Object extra) {
            this(value, value, extra);
        }

        public Suggestion(String name, String value) {
            this(name, value, null);
        }

        public Suggestion(String name, String value, Object extra) {
            this.name = name;
            this.value = value;
            this.extra = extra;
        }

        public String getName() {
            return this.name;
        }

        public String getValue() {
            return this.value;
        }

        public Object getExtra() {
            return this.extra;
        }
    }

    public static interface IArrayConfig {
        public List<String> getEntries();

        public List<String> getDefaults();

        public ParseResult<Boolean> canSetArray(List<String> var1);

        public void setArray(List<String> var1);
    }

    public static class ParsedArray<T>
    extends CollectionConfigEntry<T, List<T>>
    implements IArrayConfig {
        IConfigSerializer<T> serializer;

        public ParsedArray(String key, List<T> defaultValue, IConfigSerializer<T> serializer, String ... comment) {
            super(key, defaultValue, comment);
            this.serializer = serializer;
        }

        public ParsedArray(String key, List<T> defaultValue, IConfigSerializer<T> serializer) {
            super(key, defaultValue, new String[0]);
            this.serializer = serializer;
        }

        @Override
        protected ParsedArray<T> copy() {
            return new ParsedArray<T>(this.getKey(), (List)this.getValue(), this.serializer, this.getComment());
        }

        @Override
        public ParseResult<List<T>> parseValue(String value) {
            ObjectArrayList result = new ObjectArrayList();
            for (String s : Helpers.splitArray(value, ",")) {
                ParseResult<T> entry = this.serializer.deserialize(Helpers.splitArray(s, ";"));
                if (!entry.isValid()) continue;
                result.add(entry.getValue());
            }
            return ParseResult.success(result);
        }

        @Override
        protected String serializedValue(MultilinePolicy policy, List<T> value) {
            String[] result = new String[value.size()];
            int m = value.size();
            for (int i = 0; i < m; ++i) {
                result[i] = Helpers.mergeCompound(this.serializer.serialize(value.get(i)));
            }
            return this.serializeArray(policy, result);
        }

        @Override
        public ParseResult<Boolean> canSet(List<T> value) {
            if (value == null) {
                return ParseResult.partial(false, NullPointerException::new, "Value isn't allowed to be null");
            }
            int m = value.size();
            for (int i = 0; i < m; ++i) {
                T entry = value.get(i);
                if (entry == null) {
                    return ParseResult.partial(false, NullPointerException::new, "Value isn't allowed to be null");
                }
                ParseResult<Boolean> result = this.serializer.isValid(entry);
                if (result.getValue().booleanValue()) continue;
                return result;
            }
            return ParseResult.success(true);
        }

        @Override
        public List<String> getEntries() {
            ObjectArrayList output = new ObjectArrayList();
            for (Object entry : (List)this.getValue()) {
                output.add(Helpers.mergeCompound(this.serializer.serialize(entry)));
            }
            return output;
        }

        @Override
        public List<String> getDefaults() {
            ObjectArrayList output = new ObjectArrayList();
            for (Object entry : (List)this.getDefault()) {
                output.add(Helpers.mergeCompound(this.serializer.serialize(entry)));
            }
            return output;
        }

        @Override
        public ParseResult<Boolean> canSetArray(List<String> entries) {
            if (entries == null) {
                return ParseResult.partial(false, NullPointerException::new, "Value isn't allowed to be null");
            }
            int m = entries.size();
            for (int i = 0; i < m; ++i) {
                ParseResult<T> result = this.serializer.deserialize(Helpers.splitArray(entries.get(i), ";"));
                if (result.hasError()) {
                    return result.onlyError();
                }
                ParseResult<Boolean> valid = this.serializer.isValid(result.getValue());
                if (!valid.hasError()) continue;
                return valid;
            }
            return ParseResult.success(true);
        }

        @Override
        public void setArray(List<String> entries) {
            StringJoiner joiner = new StringJoiner(",");
            for (String s : entries) {
                joiner.add(s);
            }
            this.deserializeValue(joiner.toString());
        }

        @Override
        public IEntryDataType getDataType() {
            return this.serializer.getFormat();
        }

        @Override
        public char getPrefix() {
            return 'P';
        }

        private String buildFormat() {
            StringJoiner joiner = new StringJoiner(";");
            for (Map.Entry<String, IEntryDataType.EntryDataType> entry : this.serializer.getFormat().getCompound()) {
                IEntryDataType.EntryDataType displayType;
                IEntryDataType.EntryDataType type = entry.getValue();
                if (entry.getValue() == IEntryDataType.EntryDataType.CUSTOM && (displayType = this.serializer.getFormat().getDisplay(entry.getKey())) != null) {
                    type = displayType;
                }
                joiner.add(entry.getKey() + "(" + Helpers.firstLetterUppercase(type.name().toLowerCase()) + ")");
            }
            return joiner.toString();
        }

        @Override
        public String getLimitations() {
            return "Format: [" + this.buildFormat() + "], Example: [" + this.serializedValue(MultilinePolicy.DISABLED, (List<T>)ObjectLists.singleton(this.serializer.getExample())) + "]";
        }

        @Override
        public void serialize(IWriteBuffer buffer) {
            List values = (List)this.getValue();
            buffer.writeVarInt(values.size());
            int m = values.size();
            for (int i = 0; i < m; ++i) {
                this.serializer.serialize(buffer, values.get(i));
            }
        }

        @Override
        public void deserializeValue(IReadBuffer buffer) {
            ObjectArrayList values = new ObjectArrayList();
            int size = buffer.readVarInt();
            for (int i = 0; i < size; ++i) {
                T value = this.serializer.deserialize(buffer);
                if (value == null) continue;
                values.add(value);
            }
            this.set(values);
        }

        @Override
        protected List<T> create(T value) {
            return ObjectLists.singleton(value);
        }
    }

    public static class ParsedValue<T>
    extends BasicConfigEntry<T> {
        IConfigSerializer<T> serializer;

        public ParsedValue(String key, T defaultValue, IConfigSerializer<T> serializer, String[] comment) {
            super(key, defaultValue, comment);
            this.serializer = serializer;
        }

        public ParsedValue(String key, T defaultValue, IConfigSerializer<T> serializer) {
            super(key, defaultValue, new String[0]);
            this.serializer = serializer;
        }

        @Override
        protected ParsedValue<T> copy() {
            return new ParsedValue(this.getKey(), this.getDefault(), this.serializer, this.getComment());
        }

        @Override
        public char getPrefix() {
            return 'p';
        }

        @Override
        public IEntryDataType.CompoundDataType getDataType() {
            return this.serializer.getFormat();
        }

        public T get() {
            return this.getValue();
        }

        @Override
        public ParseResult<Boolean> canSet(T value) {
            ParseResult<Boolean> result = super.canSet(value);
            if (result.hasError()) {
                return result;
            }
            return this.serializer.isValid(value);
        }

        @Override
        public ParseResult<T> parseValue(String value) {
            return this.serializer.deserialize(Helpers.splitArray(value, ";"));
        }

        @Override
        protected String serializedValue(MultilinePolicy policy, T value) {
            return Helpers.mergeCompound(this.serializer.serialize(value));
        }

        private String buildFormat() {
            StringJoiner joiner = new StringJoiner(";");
            for (Map.Entry<String, IEntryDataType.EntryDataType> entry : this.serializer.getFormat().getCompound()) {
                IEntryDataType.EntryDataType displayType;
                IEntryDataType.EntryDataType type = entry.getValue();
                if (entry.getValue() == IEntryDataType.EntryDataType.CUSTOM && (displayType = this.serializer.getFormat().getDisplay(entry.getKey())) != null) {
                    type = displayType;
                }
                joiner.add(entry.getKey() + "(" + Helpers.firstLetterUppercase(type.name().toLowerCase()) + ")");
            }
            return joiner.toString();
        }

        @Override
        public String getLimitations() {
            return "Format: [" + this.buildFormat() + "], Example: [" + this.serializedValue(MultilinePolicy.DISABLED, this.serializer.getExample()) + "]";
        }

        @Override
        public void serialize(IWriteBuffer buffer) {
            this.serializer.serialize(buffer, this.getValue());
        }

        @Override
        public void deserializeValue(IReadBuffer buffer) {
            this.set(this.serializer.deserialize(buffer));
        }
    }

    public static class EnumValue<E extends Enum<E>>
    extends BasicConfigEntry<E> {
        private Class<E> enumClass;

        public EnumValue(String key, E defaultValue, Class<E> enumClass, String ... comment) {
            super(key, defaultValue, comment);
            this.enumClass = enumClass;
            this.addSuggestions();
        }

        public EnumValue(String key, E defaultValue, Class<E> enumClass) {
            super(key, defaultValue, new String[0]);
            this.enumClass = enumClass;
            this.addSuggestions();
        }

        private void addSuggestions() {
            for (Enum value : (Enum[])this.enumClass.getEnumConstants()) {
                ISuggestedEnum<Enum> wrapper = ISuggestedEnum.getWrapper(value);
                if (wrapper != null) {
                    this.addSuggestion(wrapper.getName(value), value);
                    continue;
                }
                this.addSuggestion(value);
            }
        }

        @Override
        protected EnumValue<E> copy() {
            return new EnumValue<Enum>(this.getKey(), (Enum)this.getDefault(), this.enumClass, this.getComment());
        }

        @Override
        public char getPrefix() {
            return 'E';
        }

        @Override
        protected String serializedValue(MultilinePolicy policy, E value) {
            return ((Enum)value).name();
        }

        @Override
        public IEntryDataType.SimpleDataType getDataType() {
            return IEntryDataType.EntryDataType.STRING.toSimpleType();
        }

        @Override
        public ParseResult<Boolean> canSet(E value) {
            return ParseResult.result(this.enumClass.isInstance(value), IllegalArgumentException::new, "Value must be one of the following: " + Arrays.toString(this.toArray()));
        }

        public E get() {
            return (E)((Enum)this.getValue());
        }

        @Override
        public String getLimitations() {
            return "Must be one of " + Arrays.toString(this.toArray());
        }

        private String[] toArray() {
            Enum[] array = (Enum[])this.enumClass.getEnumConstants();
            String[] values = new String[array.length];
            int m = array.length;
            for (int i = 0; i < m; ++i) {
                values[i] = array[i].name();
            }
            return values;
        }

        @Override
        public ParseResult<E> parseValue(String value) {
            try {
                return ParseResult.success(Enum.valueOf(this.enumClass, value));
            }
            catch (Exception e) {
                return ParseResult.error(value, e);
            }
        }

        @Override
        public void serialize(IWriteBuffer buffer) {
            buffer.writeEnum((Enum<?>)this.get());
        }

        @Override
        public void deserializeValue(IReadBuffer buffer) {
            this.set(buffer.readEnum(this.enumClass));
        }
    }

    public static class ArrayValue
    extends ArrayConfigEntry<String>
    implements IArrayConfig {
        protected Predicate<String> filter;

        public ArrayValue(String key, String[] defaultValue, String ... comment) {
            super(key, (T[])defaultValue, comment);
        }

        public ArrayValue(String key, String[] defaultValue) {
            super(key, (T[])defaultValue, new String[0]);
        }

        public ArrayValue(String key, String comment) {
            super(key, (T[])new String[0], comment);
        }

        public ArrayValue(String key) {
            super(key, (T[])new String[0], new String[0]);
        }

        public ArrayValue withFilter(Predicate<String> filter) {
            this.filter = filter;
            return this;
        }

        protected ArrayValue copy() {
            return new ArrayValue(this.getKey(), (String[])this.getDefault(), this.getComment());
        }

        @Override
        public char getPrefix() {
            return 'A';
        }

        @Override
        public IEntryDataType.SimpleDataType getDataType() {
            return IEntryDataType.EntryDataType.STRING.toSimpleType();
        }

        public String[] get() {
            return (String[])this.getValue();
        }

        @Override
        public String getLimitations() {
            return "";
        }

        @Override
        public List<String> getEntries() {
            return new ObjectArrayList((Object[])((String[])this.getValue()));
        }

        @Override
        public List<String> getDefaults() {
            return ObjectArrayList.wrap((Object[])((String[])this.getDefault()));
        }

        @Override
        public ParseResult<Boolean> canSetArray(List<String> entries) {
            if (entries == null) {
                return ParseResult.partial(false, NullPointerException::new, "Value isn't allowed to be null");
            }
            if (this.filter != null) {
                for (int i = 0; i < entries.size(); ++i) {
                    if (this.filter.test(entries.get(i))) continue;
                    return ParseResult.partial(false, IllegalArgumentException::new, "Value [" + entries.get(i) + "] isn't valid");
                }
            }
            return ParseResult.success(true);
        }

        @Override
        public void setArray(List<String> entries) {
            this.set(entries.toArray(new String[entries.size()]));
        }

        @Override
        public ParseResult<String[]> parseValue(String value) {
            return ParseResult.success(Helpers.splitArray(value, ","));
        }

        @Override
        protected String serializedValue(MultilinePolicy policy, String[] value) {
            return this.serializeArray(policy, value);
        }

        public static ParseResult<ArrayValue> parse(String key, String value, String ... comment) {
            return ParseResult.success(new ArrayValue(key, Helpers.splitArray(value, ","), comment));
        }

        @Override
        public void serialize(IWriteBuffer buffer) {
            buffer.writeVarInt(this.get().length);
            for (String val : this.get()) {
                buffer.writeString(val);
            }
        }

        @Override
        public void deserializeValue(IReadBuffer buffer) {
            String[] val = new String[buffer.readVarInt()];
            for (int i = 0; i < val.length; ++i) {
                val[i] = buffer.readString();
            }
            this.set(val);
        }
    }

    public static class StringValue
    extends BasicConfigEntry<String> {
        protected Predicate<String> filter;

        public StringValue(String key, String defaultValue, String ... comment) {
            super(key, defaultValue, comment);
        }

        public StringValue(String key, String defaultValue) {
            super(key, defaultValue, new String[0]);
        }

        public StringValue withFilter(Predicate<String> filter) {
            this.filter = filter;
            return this;
        }

        protected StringValue copy() {
            return new StringValue(this.getKey(), (String)this.getDefault(), this.getComment()).withFilter(this.filter);
        }

        @Override
        public char getPrefix() {
            return 'S';
        }

        @Override
        public IEntryDataType.SimpleDataType getDataType() {
            return IEntryDataType.EntryDataType.STRING.toSimpleType();
        }

        public String get() {
            return (String)this.getValue();
        }

        @Override
        public String getLimitations() {
            return "";
        }

        @Override
        public ParseResult<Boolean> canSet(String value) {
            if (value == null) {
                return ParseResult.partial(false, NullPointerException::new, "Value isn't allowed to be null");
            }
            if (this.filter == null || this.filter.test(value)) {
                return ParseResult.success(true);
            }
            return ParseResult.partial(false, IllegalStateException::new, "Value [" + value + "] isn't valid");
        }

        @Override
        public ParseResult<String> parseValue(String value) {
            return ParseResult.successOrError(value, this.filter == null || this.filter.test(value), IllegalArgumentException::new, "Value [" + value + "] is not valid");
        }

        public static ParseResult<StringValue> parse(String key, String value, String ... comment) {
            return ParseResult.success(new StringValue(key, value, comment));
        }

        @Override
        public void serialize(IWriteBuffer buffer) {
            buffer.writeString(this.get());
        }

        @Override
        public void deserializeValue(IReadBuffer buffer) {
            this.set(buffer.readString());
        }
    }

    public static class TempValue
    extends StringValue {
        private TempValue(String key, String defaultValue, String[] comment) {
            super(key, defaultValue, comment);
        }

        public static ParseResult<TempValue> parseTemp(String key, String value, String ... comment) {
            return ParseResult.success(new TempValue(key, value, comment));
        }

        @Override
        public TempValue withFilter(Predicate<String> filter) {
            throw new UnsupportedOperationException("Filters are not supported with Temp Values");
        }

        @Override
        protected TempValue copy() {
            return new TempValue(this.getKey(), (String)this.getDefault(), this.getComment());
        }
    }

    public static class BoolValue
    extends BasicConfigEntry<Boolean> {
        public BoolValue(String key, Boolean defaultValue, String ... comment) {
            super(key, defaultValue, comment);
        }

        public BoolValue(String key, Boolean defaultValue) {
            super(key, defaultValue, new String[0]);
        }

        protected BoolValue copy() {
            return new BoolValue(this.getKey(), (Boolean)this.getDefault(), this.getComment());
        }

        public boolean get() {
            return (Boolean)this.getValue();
        }

        @Override
        public char getPrefix() {
            return 'B';
        }

        @Override
        public IEntryDataType.SimpleDataType getDataType() {
            return IEntryDataType.EntryDataType.BOOLEAN.toSimpleType();
        }

        @Override
        public String getLimitations() {
            return "";
        }

        @Override
        public ParseResult<Boolean> parseValue(String value) {
            return ParseResult.success(Boolean.parseBoolean(value));
        }

        public static ParseResult<BoolValue> parse(String key, String value, String ... comment) {
            return ParseResult.success(new BoolValue(key, Boolean.parseBoolean(value), comment));
        }

        @Override
        public void serialize(IWriteBuffer buffer) {
            buffer.writeBoolean(this.get());
        }

        @Override
        public void deserializeValue(IReadBuffer buffer) {
            this.set(buffer.readBoolean());
        }
    }

    public static class DoubleValue
    extends BasicConfigEntry<Double> {
        private double min = -1.7976931348623157E308;
        private double max = Double.MAX_VALUE;

        public DoubleValue(String key, Double defaultValue, String ... comment) {
            super(key, defaultValue, comment);
        }

        public DoubleValue(String key, Double defaultValue) {
            super(key, defaultValue, new String[0]);
        }

        public DoubleValue setMin(double min) {
            this.min = min;
            return this;
        }

        public DoubleValue setMax(double max) {
            this.max = max;
            return this;
        }

        public DoubleValue setRange(double min, double max) {
            this.min = min;
            this.max = max;
            return this;
        }

        protected DoubleValue copy() {
            return new DoubleValue(this.getKey(), (Double)this.getDefault(), this.getComment()).setRange(this.min, this.max);
        }

        @Override
        public ParseResult<Boolean> canSet(Double value) {
            ParseResult<Boolean> result = super.canSet(value);
            if (result.hasError()) {
                return result;
            }
            return ParseResult.result(value >= this.min && value <= this.max, IllegalArgumentException::new, "Value [" + value + "] has to be within [" + this.min + " ~ " + this.max + "]");
        }

        public DoubleValue set(Double value) {
            super.set(Helpers.clamp(value, this.min, this.max));
            return this;
        }

        @Override
        public char getPrefix() {
            return 'D';
        }

        @Override
        public IEntryDataType.SimpleDataType getDataType() {
            return IEntryDataType.EntryDataType.DOUBLE.toSimpleType();
        }

        public double get() {
            return (Double)this.getValue();
        }

        @Override
        public String getLimitations() {
            if (this.min == Double.MIN_VALUE) {
                if (this.max == Double.MAX_VALUE) {
                    return "";
                }
                return "Range: < " + this.max;
            }
            if (this.max == Double.MAX_VALUE) {
                if (this.min == Double.MIN_VALUE) {
                    return "";
                }
                return "Range: > " + this.min;
            }
            return "Range: " + this.min + " ~ " + this.max;
        }

        @Override
        public ParseResult<Double> parseValue(String value) {
            return Helpers.parseDouble(value);
        }

        public static ParseResult<DoubleValue> parse(String key, String value, String ... comment) {
            ParseResult<Double> result = Helpers.parseDouble(value);
            if (result.hasError()) {
                return result.withDefault(new DoubleValue(key, 0.0, comment));
            }
            return ParseResult.success(new DoubleValue(key, result.getValue(), comment));
        }

        @Override
        public void serialize(IWriteBuffer buffer) {
            buffer.writeDouble(this.get());
        }

        @Override
        public void deserializeValue(IReadBuffer buffer) {
            this.set(buffer.readDouble());
        }
    }

    public static class IntValue
    extends BasicConfigEntry<Integer> {
        private int min = Integer.MIN_VALUE;
        private int max = Integer.MAX_VALUE;

        public IntValue(String key, Integer defaultValue, String ... comment) {
            super(key, defaultValue, comment);
        }

        public IntValue(String key, Integer defaultValue) {
            super(key, defaultValue, new String[0]);
        }

        public IntValue setMin(int min) {
            this.min = min;
            return this;
        }

        public IntValue setMax(int max) {
            this.max = max;
            return this;
        }

        public IntValue setRange(int min, int max) {
            this.min = min;
            this.max = max;
            return this;
        }

        protected IntValue copy() {
            return new IntValue(this.getKey(), (Integer)this.getDefault(), this.getComment()).setRange(this.min, this.max);
        }

        public IntValue set(Integer value) {
            super.set(Helpers.clamp(value, this.min, this.max));
            return this;
        }

        @Override
        public ParseResult<Boolean> canSet(Integer value) {
            ParseResult<Boolean> result = super.canSet(value);
            if (result.hasError()) {
                return result;
            }
            return ParseResult.result(value >= this.min && value <= this.max, IllegalArgumentException::new, "Value [" + value + "] has to be within [" + this.min + " ~ " + this.max + "]");
        }

        @Override
        public String getLimitations() {
            if (this.min == Integer.MIN_VALUE) {
                if (this.max == Integer.MAX_VALUE) {
                    return "";
                }
                return "Range: < " + this.max;
            }
            if (this.max == Integer.MAX_VALUE) {
                if (this.min == Integer.MIN_VALUE) {
                    return "";
                }
                return "Range: > " + this.min;
            }
            return "Range: " + this.min + " ~ " + this.max;
        }

        @Override
        public char getPrefix() {
            return 'I';
        }

        @Override
        public IEntryDataType.SimpleDataType getDataType() {
            return IEntryDataType.EntryDataType.INTEGER.toSimpleType();
        }

        public int get() {
            return (Integer)this.getValue();
        }

        @Override
        public ParseResult<Integer> parseValue(String value) {
            return Helpers.parseInt(value);
        }

        public static ParseResult<IntValue> parse(String key, String value, String ... comment) {
            ParseResult<Integer> result = Helpers.parseInt(value);
            if (result.hasError()) {
                return result.withDefault(new IntValue(key, 0, comment));
            }
            return ParseResult.success(new IntValue(key, result.getValue(), comment));
        }

        @Override
        public void serialize(IWriteBuffer buffer) {
            buffer.writeInt(this.get());
        }

        @Override
        public void deserializeValue(IReadBuffer buffer) {
            this.set(buffer.readInt());
        }
    }

    public static abstract class CollectionConfigEntry<T, E extends Collection<T>>
    extends ConfigEntry<E> {
        public CollectionConfigEntry(String key, E defaultValue, String ... comment) {
            super(key, defaultValue, comment);
        }

        public <K, V> MappedConfig<K, V> createdMappedConfig(ConfigHandler handler, Function<T, K> keyGenerator, Function<T, V> valueGenerator) {
            return MappedConfig.create(handler, this, keyGenerator, valueGenerator);
        }

        public final <S extends CollectionConfigEntry<T, E>> S addSuggestions(T ... values) {
            for (T value : values) {
                this.addSuggestionInternal(this.serializedValue(MultilinePolicy.DISABLED, this.create(value)));
            }
            return (S)this;
        }

        public final <S extends CollectionConfigEntry<T, E>> S addSuggestion(T value) {
            return (S)((CollectionConfigEntry)this.addSuggestionInternal(this.serializedValue(MultilinePolicy.DISABLED, this.create(value))));
        }

        public final <S extends CollectionConfigEntry<T, E>> S addSuggestion(T value, Object extra) {
            return (S)((CollectionConfigEntry)this.addSuggestionInternal(extra, this.serializedValue(MultilinePolicy.DISABLED, this.create(value))));
        }

        public final <S extends CollectionConfigEntry<T, E>> S addSuggestion(String name, T value) {
            return (S)((CollectionConfigEntry)this.addSuggestionInternal(name, this.serializedValue(MultilinePolicy.DISABLED, this.create(value))));
        }

        public final <S extends CollectionConfigEntry<T, E>> S addSuggestion(String name, T value, Object extra) {
            return (S)((CollectionConfigEntry)this.addSuggestionInternal(name, this.serializedValue(MultilinePolicy.DISABLED, this.create(value)), extra));
        }

        protected abstract E create(T var1);
    }

    public static abstract class ArrayConfigEntry<T>
    extends ConfigEntry<T[]> {
        public ArrayConfigEntry(String key, T[] defaultValue, String ... comment) {
            super(key, defaultValue, comment);
        }

        public <K, V> MappedConfig<K, V> createdMappedConfig(ConfigHandler handler, Function<T, K> keyGenerator, Function<T, V> valueGenerator) {
            return MappedConfig.create(handler, this, keyGenerator, valueGenerator);
        }

        public final <S extends ArrayConfigEntry<T>> S addSuggestions(T ... values) {
            for (T value : values) {
                this.addSuggestionInternal(this.serializedValue(MultilinePolicy.DISABLED, this.toArray(value)));
            }
            return (S)this;
        }

        public final <S extends ArrayConfigEntry<T>> S addSuggestion(T value) {
            return (S)((ArrayConfigEntry)this.addSuggestionInternal(this.serializedValue(MultilinePolicy.DISABLED, this.toArray(value))));
        }

        public final <S extends ArrayConfigEntry<T>> S addSuggestion(T value, Object extra) {
            return (S)((ArrayConfigEntry)this.addSuggestionInternal(extra, this.serializedValue(MultilinePolicy.DISABLED, this.toArray(value))));
        }

        public final <S extends ArrayConfigEntry<T>> S addSuggestion(String name, T value) {
            return (S)((ArrayConfigEntry)this.addSuggestionInternal(name, this.serializedValue(MultilinePolicy.DISABLED, this.toArray(value))));
        }

        public final <S extends ArrayConfigEntry<T>> S addSuggestion(String name, T value, Object extra) {
            return (S)((ArrayConfigEntry)this.addSuggestionInternal(name, this.serializedValue(MultilinePolicy.DISABLED, this.toArray(value)), extra));
        }

        T[] toArray(T input) {
            Object[] result = (Object[])Array.newInstance(input.getClass(), 1);
            result[0] = input;
            return result;
        }
    }

    public static abstract class BasicConfigEntry<T>
    extends ConfigEntry<T> {
        public BasicConfigEntry(String key, T defaultValue, String ... comment) {
            super(key, defaultValue, comment);
        }

        public final <S extends BasicConfigEntry<T>> S addSuggestions(T ... values) {
            for (T value : values) {
                this.addSuggestionInternal(this.serializedValue(MultilinePolicy.DISABLED, value));
            }
            return (S)this;
        }

        public final <S extends BasicConfigEntry<T>> S addSuggestion(T value) {
            return (S)((BasicConfigEntry)this.addSuggestionInternal(this.serializedValue(MultilinePolicy.DISABLED, value)));
        }

        public final <S extends BasicConfigEntry<T>> S addSuggestion(T value, Object extra) {
            return (S)((BasicConfigEntry)this.addSuggestionInternal(extra, this.serializedValue(MultilinePolicy.DISABLED, value)));
        }

        public final <S extends BasicConfigEntry<T>> S addSuggestion(String name, T value) {
            return (S)((BasicConfigEntry)this.addSuggestionInternal(name, this.serializedValue(MultilinePolicy.DISABLED, value)));
        }

        public final <S extends BasicConfigEntry<T>> S addSuggestion(String name, T value, Object extra) {
            return (S)((BasicConfigEntry)this.addSuggestionInternal(name, this.serializedValue(MultilinePolicy.DISABLED, value), extra));
        }
    }
}

