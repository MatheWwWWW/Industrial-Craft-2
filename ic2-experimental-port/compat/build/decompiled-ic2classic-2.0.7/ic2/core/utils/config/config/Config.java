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

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.config.ConfigSection;
import ic2.core.utils.config.utils.Helpers;
import ic2.core.utils.config.utils.MultilinePolicy;
import ic2.core.utils.config.utils.SyncType;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class Config {
    private String name;
    private Object2ObjectMap<String, ConfigSection> sections = new Object2ObjectLinkedOpenHashMap();

    public Config(String name) {
        if (Helpers.validateString(name)) {
            throw new IllegalArgumentException("Config name must not be null, empty or start/end with white spaces");
        }
        this.name = name;
    }

    public ConfigSection add(ConfigSection section) {
        if (section.getParent() != null) {
            throw new IllegalStateException("ConfigSection must not be added to multiple sections. Section: " + section.getName());
        }
        this.sections.putIfAbsent((Object)section.getName(), (Object)section);
        return section;
    }

    public ConfigSection add(String name) {
        return ((ConfigSection)this.sections.computeIfAbsent((Object)name, ConfigSection::new)).setUsed();
    }

    public ConfigSection getSection(String name) {
        return (ConfigSection)this.sections.get((Object)name);
    }

    ConfigSection getSectionRecursive(String[] names) {
        if (names.length == 0) {
            return null;
        }
        ConfigSection section = (ConfigSection)this.sections.computeIfAbsent((Object)names[0], ConfigSection::new);
        for (int i = 1; i < names.length && section != null; section = section.parseSubSection(names[i]), ++i) {
        }
        return section;
    }

    public List<ConfigSection> getChildren() {
        return new ObjectArrayList(this.sections.values());
    }

    public Map<String, ConfigEntry<?>> getSyncedEntries(SyncType type) {
        if (type == SyncType.NONE) {
            return Collections.emptyMap();
        }
        Object2ObjectLinkedOpenHashMap result = new Object2ObjectLinkedOpenHashMap();
        for (ConfigSection section : this.sections.values()) {
            section.getSyncedEntries((Map<String, ConfigEntry<?>>)result, type);
        }
        return result;
    }

    public String getName() {
        return this.name;
    }

    public Config copy() {
        Config config = new Config(this.name);
        for (ConfigSection sub : this.sections.values()) {
            config.add(sub.copy());
        }
        return config;
    }

    public void resetDefault() {
        this.sections.values().forEach(ConfigSection::resetDefault);
    }

    public boolean hasChanged() {
        for (ConfigSection section : this.sections.values()) {
            if (!section.hasChanged()) continue;
            return true;
        }
        return false;
    }

    public boolean isDefault() {
        for (ConfigSection section : this.sections.values()) {
            if (section.isDefault()) continue;
            return false;
        }
        return true;
    }

    public String serialize(MultilinePolicy policy) {
        if (this.sections.size() == 0) {
            return "";
        }
        StringJoiner joiner = new StringJoiner("\n\n");
        Object2ObjectMaps.fastForEach(this.sections, entry -> {
            String val = ((ConfigSection)entry.getValue()).serialize(policy);
            if (val != null) {
                joiner.add(val);
            }
        });
        return joiner.toString();
    }
}

