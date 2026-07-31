/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Optional
 *  gnu.trove.map.TIntObjectMap
 *  gnu.trove.map.hash.TIntObjectHashMap
 *  net.minecraft.block.properties.PropertyHelper
 */
package ic2.core.block.state;

import com.google.common.base.Optional;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import ic2.core.block.state.IIdProvider;
import ic2.core.profile.Version;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.properties.PropertyHelper;

public class EnumProperty<T extends Enum<T>>
extends PropertyHelper<T> {
    private final List<T> values;
    private final TIntObjectMap<T> reverseMap;

    public EnumProperty(String name, Class<T> cls) {
        super(name, cls);
        Enum[] values = (Enum[])cls.getEnumConstants();
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("No enum constants for " + cls);
        }
        this.values = Arrays.asList(values);
        boolean idsMatchOrdinal = true;
        for (int i = 0; i < values.length; ++i) {
            if (((IIdProvider)((Object)values[i])).getId() == i) continue;
            idsMatchOrdinal = false;
            break;
        }
        if (idsMatchOrdinal) {
            this.reverseMap = null;
        } else {
            this.reverseMap = new TIntObjectHashMap(values.length);
            for (Enum value : values) {
                this.reverseMap.put(((IIdProvider)((Object)value)).getId(), (Object)value);
            }
            if (this.reverseMap.size() != values.length) {
                throw new IllegalArgumentException("The enum " + cls + " provides non-unique ids");
            }
        }
    }

    public List<T> getAllowedValues() {
        return this.values;
    }

    public List<T> getShownValues() {
        Class valueClass = this.func_177699_b();
        boolean defaultState = Version.shouldEnable(valueClass);
        return this.values.stream().filter(value -> {
            try {
                return Version.shouldEnable(valueClass.getField(value.name()), defaultState);
            }
            catch (NoSuchFieldException e) {
                throw new RuntimeException("Impossible missing enum field!", e);
            }
        }).collect(Collectors.toList());
    }

    public String getName(T value) {
        return ((IIdProvider)value).getName();
    }

    public Optional<T> func_185929_b(String value) {
        return Optional.fromNullable(this.getValue(value));
    }

    public T getValue(int id) {
        if (this.reverseMap == null) {
            if (id >= 0 && id < this.values.size()) {
                return (T)((Enum)this.values.get(id));
            }
            return null;
        }
        return (T)((Enum)this.reverseMap.get(id));
    }

    public T getValueOrDefault(int id) {
        T ret = this.getValue(id);
        return ret != null ? ret : this.getDefault();
    }

    public T getValue(String name) {
        for (Enum value : this.values) {
            if (!((IIdProvider)((Object)value)).getName().equals(name)) continue;
            return (T)value;
        }
        return null;
    }

    public T getValueOrDefault(String name) {
        T ret = this.getValue(name);
        return ret != null ? ret : this.getDefault();
    }

    public T getDefault() {
        return (T)((Enum)this.values.get(0));
    }
}

