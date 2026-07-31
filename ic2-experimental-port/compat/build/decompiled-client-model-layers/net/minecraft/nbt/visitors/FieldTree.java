/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt.visitors;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.visitors.FieldSelector;

public record FieldTree(int f_202523_, Map<String, TagType<?>> f_202524_, Map<String, FieldTree> f_202525_) {
    private FieldTree(int p_202527_) {
        this(p_202527_, new HashMap(), new HashMap<String, FieldTree>());
    }

    public static FieldTree m_202532_() {
        return new FieldTree(1);
    }

    public void m_202538_(FieldSelector p_202539_) {
        if (this.f_202523_ <= p_202539_.f_202497_().size()) {
            this.f_202525_.computeIfAbsent(p_202539_.f_202497_().get(this.f_202523_ - 1), p_202534_ -> new FieldTree(this.f_202523_ + 1)).m_202538_(p_202539_);
        } else {
            this.f_202524_.put(p_202539_.f_202499_(), p_202539_.f_202498_());
        }
    }

    public boolean m_202535_(TagType<?> p_202536_, String p_202537_) {
        return p_202536_.equals(this.f_202524_().get(p_202537_));
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{FieldTree.class, "depth;selectedFields;fieldsToRecurse", "f_202523_", "f_202524_", "f_202525_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{FieldTree.class, "depth;selectedFields;fieldsToRecurse", "f_202523_", "f_202524_", "f_202525_"}, this);
    }

    @Override
    public final boolean equals(Object p_202544_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{FieldTree.class, "depth;selectedFields;fieldsToRecurse", "f_202523_", "f_202524_", "f_202525_"}, this, p_202544_);
    }
}

