/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt.visitors;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.nbt.TagType;

public record FieldSelector(List<String> f_202497_, TagType<?> f_202498_, String f_202499_) {
    public FieldSelector(TagType<?> p_202514_, String p_202515_) {
        this(List.of(), p_202514_, p_202515_);
    }

    public FieldSelector(String p_202506_, TagType<?> p_202507_, String p_202508_) {
        this(List.of(p_202506_), p_202507_, p_202508_);
    }

    public FieldSelector(String p_202501_, String p_202502_, TagType<?> p_202503_, String p_202504_) {
        this(List.of(p_202501_, p_202502_), p_202503_, p_202504_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{FieldSelector.class, "path;type;name", "f_202497_", "f_202498_", "f_202499_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{FieldSelector.class, "path;type;name", "f_202497_", "f_202498_", "f_202499_"}, this);
    }

    @Override
    public final boolean equals(Object p_202520_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{FieldSelector.class, "path;type;name", "f_202497_", "f_202498_", "f_202499_"}, this, p_202520_);
    }
}

