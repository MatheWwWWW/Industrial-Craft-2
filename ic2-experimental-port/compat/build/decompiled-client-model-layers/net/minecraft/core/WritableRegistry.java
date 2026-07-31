/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 */
package net.minecraft.core;

import com.mojang.serialization.Lifecycle;
import java.util.OptionalInt;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public abstract class WritableRegistry<T>
extends Registry<T> {
    public WritableRegistry(ResourceKey<? extends Registry<T>> p_123346_, Lifecycle p_123347_) {
        super(p_123346_, p_123347_);
    }

    public abstract Holder<T> m_203704_(int var1, ResourceKey<T> var2, T var3, Lifecycle var4);

    public abstract Holder<T> m_203505_(ResourceKey<T> var1, T var2, Lifecycle var3);

    public abstract Holder<T> m_203384_(OptionalInt var1, ResourceKey<T> var2, T var3, Lifecycle var4);

    public abstract boolean m_142427_();
}

