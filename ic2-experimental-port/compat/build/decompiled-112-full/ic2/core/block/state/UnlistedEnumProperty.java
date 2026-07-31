/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.state;

import ic2.core.block.state.UnlistedProperty;

public class UnlistedEnumProperty<V extends Enum<V>>
extends UnlistedProperty<V> {
    public UnlistedEnumProperty(String name, Class<V> cls) {
        super(name, cls);
    }

    @Override
    public String valueToString(V value) {
        return ((Enum)value).name();
    }
}

