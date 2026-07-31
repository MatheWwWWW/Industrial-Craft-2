/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 */
package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public interface ParticleOptions {
    public ParticleType<?> m_6012_();

    public void m_7711_(FriendlyByteBuf var1);

    public String m_5942_();

    @Deprecated
    public static interface Deserializer<T extends ParticleOptions> {
        public T m_5739_(ParticleType<T> var1, StringReader var2) throws CommandSyntaxException;

        public T m_6507_(ParticleType<T> var1, FriendlyByteBuf var2);
    }
}

