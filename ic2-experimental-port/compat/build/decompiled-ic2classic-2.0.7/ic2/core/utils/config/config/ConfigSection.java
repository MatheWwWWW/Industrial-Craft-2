/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package ic2.core.utils.config.config;

import ic2.core.utils.config.api.IConfigSerializer;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.utils.Helpers;
import ic2.core.utils.config.utils.MultilinePolicy;
import ic2.core.utils.config.utils.SyncType;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfigSection {
    private String name;
    private String[] comment;
    private ConfigSection parent = null;
    private boolean used = false;
    private Object2ObjectMap<String, ConfigEntry<?>> entries = new Object2ObjectLinkedOpenHashMap();
    private Object2ObjectMap<String, ConfigSection> subSections = new Object2ObjectLinkedOpenHashMap();

    public ConfigSection(String name) {
        if (Helpers.validateString(name)) {
            throw new IllegalArgumentException("ConfigSection name must not be null, empty or start/end with white spaces");
        }
        if (name.contains(".")) {
            throw new IllegalArgumentException("ConfigSection name must not contain period signs. Name: " + name);
        }
        this.name = name;
    }

    public ConfigSection setComment(String ... comment) {
        this.comment = Helpers.validateComments(comment);
        return this;
    }

    void parseComment(String ... comment) {
        if (comment != null) {
            return;
        }
        this.comment = Helpers.validateComments(comment);
    }

    public <V, T extends ConfigEntry<V>> T add(T entry) {
        ConfigEntry presentKey = (ConfigEntry)this.entries.get((Object)entry.getKey());
        if (presentKey != null) {
            if (presentKey instanceof ConfigEntry.TempValue) {
                entry.deserializeValue(presentKey.serialize());
            } else {
                return (T)((ConfigEntry)(presentKey.getPrefix() != entry.getPrefix() ? entry : presentKey)).setUsed();
            }
        }
        this.entries.put((Object)entry.getKey(), entry.setUsed());
        return entry;
    }

    <V, T extends ConfigEntry<V>> T addParsed(T entry) {
        this.entries.putIfAbsent((Object)entry.getKey(), entry);
        return entry;
    }

    public ConfigEntry.BoolValue addBool(String key, boolean value, String comment) {
        return this.add(new ConfigEntry.BoolValue(key, value, comment));
    }

    public ConfigEntry.BoolValue addBool(String key, boolean value) {
        return this.add(new ConfigEntry.BoolValue(key, value));
    }

    public ConfigEntry.IntValue addInt(String key, int value, String comment) {
        return this.add(new ConfigEntry.IntValue(key, value, comment));
    }

    public ConfigEntry.IntValue addInt(String key, int value) {
        return this.add(new ConfigEntry.IntValue(key, value));
    }

    public ConfigEntry.DoubleValue addDouble(String key, double value, String ... comment) {
        return this.add(new ConfigEntry.DoubleValue(key, value, comment));
    }

    public ConfigEntry.DoubleValue addDouble(String key, double value) {
        return this.add(new ConfigEntry.DoubleValue(key, value));
    }

    public ConfigEntry.StringValue addString(String key, String value, String ... comment) {
        return this.add(new ConfigEntry.StringValue(key, value, comment));
    }

    public ConfigEntry.StringValue addString(String key, String value) {
        return this.add(new ConfigEntry.StringValue(key, value));
    }

    public ConfigEntry.ArrayValue addArray(String key, String[] value, String ... comment) {
        return this.add(new ConfigEntry.ArrayValue(key, value, comment));
    }

    public ConfigEntry.ArrayValue addArray(String key, String[] value) {
        return this.add(new ConfigEntry.ArrayValue(key, value));
    }

    public ConfigEntry.ArrayValue addArray(String key, String comment) {
        return this.add(new ConfigEntry.ArrayValue(key, comment));
    }

    public ConfigEntry.ArrayValue addArray(String key) {
        return this.add(new ConfigEntry.ArrayValue(key));
    }

    public <E extends Enum<E>> ConfigEntry.EnumValue<E> addEnum(String key, E value, Class<E> enumClass, String ... comment) {
        return this.add(new ConfigEntry.EnumValue<E>(key, value, enumClass, comment));
    }

    public <E extends Enum<E>> ConfigEntry.EnumValue<E> addEnum(String key, E value, Class<E> enumClass) {
        return this.add(new ConfigEntry.EnumValue<E>(key, value, enumClass));
    }

    public <T> ConfigEntry.ParsedValue<T> addParsed(String key, T value, IConfigSerializer<T> parsers, String ... comment) {
        return this.add(new ConfigEntry.ParsedValue<T>(key, value, parsers, comment));
    }

    public <T> ConfigEntry.ParsedValue<T> addParsed(String key, T value, IConfigSerializer<T> parsers) {
        return this.add(new ConfigEntry.ParsedValue<T>(key, value, parsers));
    }

    public <T> ConfigEntry.ParsedArray<T> addParsedArray(String key, List<T> value, IConfigSerializer<T> parsers, String ... comment) {
        return this.add(new ConfigEntry.ParsedArray<T>(key, value, parsers, comment));
    }

    public <T> ConfigEntry.ParsedArray<T> addParsedArray(String key, List<T> value, IConfigSerializer<T> parsers) {
        return this.add(new ConfigEntry.ParsedArray<T>(key, value, parsers));
    }

    public ConfigSection addSubSection(String name) {
        ConfigSection subSection = (ConfigSection)this.subSections.get((Object)name);
        if (subSection == null) {
            subSection = new ConfigSection(name);
            subSection.parent = this;
            this.subSections.put((Object)name, (Object)subSection);
        }
        return subSection.setUsed();
    }

    public ConfigSection add(ConfigSection section) {
        if (section.parent != null) {
            throw new IllegalStateException("ConfigSection must not be added to multiple sections. Section name: " + section.getName());
        }
        section.parent = this;
        this.subSections.putIfAbsent((Object)section.name, (Object)section);
        return section.setUsed();
    }

    public ConfigEntry<?> getEntry(String name) {
        return (ConfigEntry)this.entries.get((Object)name);
    }

    public ConfigSection getSubSection(String name) {
        return (ConfigSection)this.subSections.get((Object)name);
    }

    ConfigSection parseSubSection(String name) {
        ConfigSection subSection = (ConfigSection)this.subSections.get((Object)name);
        if (subSection == null) {
            subSection = new ConfigSection(name);
            subSection.parent = this;
            this.subSections.put((Object)name, (Object)subSection);
        }
        return subSection;
    }

    public List<ConfigSection> getChildren() {
        return new ObjectArrayList(this.subSections.values());
    }

    public List<ConfigEntry<?>> getEntries() {
        return new ObjectArrayList(this.entries.values());
    }

    protected ConfigSection copy() {
        ConfigSection copy = new ConfigSection(this.name);
        for (ConfigSection sub : this.subSections.values()) {
            copy.add(sub.copy());
        }
        for (ConfigEntry entry : this.entries.values()) {
            copy.add(entry.deepCopy());
        }
        return copy;
    }

    public void resetDefault() {
        this.subSections.values().forEach(ConfigSection::resetDefault);
        this.entries.values().forEach(ConfigEntry::resetDefault);
    }

    boolean hasChanged() {
        for (ConfigEntry entry : this.entries.values()) {
            if (!entry.hasChanged()) continue;
            return true;
        }
        for (ConfigSection section : this.subSections.values()) {
            if (!section.hasChanged()) continue;
            return true;
        }
        return false;
    }

    boolean isDefault() {
        for (ConfigEntry entry : this.entries.values()) {
            if (entry.isDefault()) continue;
            return false;
        }
        for (ConfigSection section : this.subSections.values()) {
            if (section.isDefault()) continue;
            return false;
        }
        return true;
    }

    public ConfigSection getParent() {
        return this.parent;
    }

    public String getName() {
        return this.name;
    }

    public String getSectionPath() {
        return (String)(this.parent != null ? this.parent.getSectionPath() + "." : "") + this.name;
    }

    boolean isUsed() {
        return this.used;
    }

    ConfigSection setUsed() {
        this.used = true;
        return this;
    }

    void getSyncedEntries(Map<String, ConfigEntry<?>> syncedEntries, SyncType type) {
        for (ConfigEntry entry : this.entries.values()) {
            if (entry.getSyncType() != type) continue;
            syncedEntries.put(this.getSectionPath() + "." + entry.getKey(), entry);
        }
        for (ConfigSection section : this.subSections.values()) {
            section.getSyncedEntries(syncedEntries, type);
        }
    }

    public String serialize(MultilinePolicy policy) {
        return this.serialize(policy, 0);
    }

    public String serialize(MultilinePolicy policy, int indentationLevel) {
        if (this.entries.size() == 0 && this.subSections.size() == 0) {
            return null;
        }
        AtomicInteger written = new AtomicInteger(0);
        StringBuilder builder = new StringBuilder((String)(this.parent == null ? "" : "\n" + Helpers.generateIndent(indentationLevel)));
        if (this.comment != null && this.comment.length > 0) {
            String indentation = "\n" + Helpers.generateIndent(indentationLevel);
            for (int i = 0; i < this.comment.length; ++i) {
                builder.append(indentation);
                builder.append("# ");
                builder.append(this.comment[i].replaceAll("\\R", indentation + "# "));
            }
            builder.append(indentation);
            builder.delete(0, 1);
        }
        builder.append('[');
        builder.append(this.getSectionPath());
        builder.append(']');
        int finalIndentationLevel = indentationLevel + 1;
        Object2ObjectMaps.fastForEach(this.entries, entry -> {
            if (!((ConfigEntry)entry.getValue()).isUsed()) {
                return;
            }
            builder.append(((ConfigEntry)entry.getValue()).serialize(policy, finalIndentationLevel));
            written.incrementAndGet();
        });
        StringJoiner joiner = new StringJoiner("\n", this.entries.size() == 0 || this.subSections.size() == 0 ? "" : "\n", "");
        Object2ObjectMaps.fastForEach(this.subSections, entry -> {
            if (!((ConfigSection)entry.getValue()).isUsed()) {
                return;
            }
            String val = ((ConfigSection)entry.getValue()).serialize(policy, finalIndentationLevel);
            if (val == null) {
                return;
            }
            joiner.add(val);
            written.getAndIncrement();
        });
        builder.append(joiner.toString());
        return written.get() > 0 ? builder.toString() : null;
    }
}

