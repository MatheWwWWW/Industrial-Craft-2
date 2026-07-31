/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.ticks;

import java.util.function.Function;
import net.minecraft.nbt.Tag;

public interface SerializableTickContainer<T> {
    public Tag m_183237_(long var1, Function<T, String> var3);
}

