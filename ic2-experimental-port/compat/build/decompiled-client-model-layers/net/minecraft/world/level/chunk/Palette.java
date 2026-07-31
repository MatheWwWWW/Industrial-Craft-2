/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.chunk.PaletteResize;

public interface Palette<T> {
    public int m_6796_(T var1);

    public boolean m_6419_(Predicate<T> var1);

    public T m_5795_(int var1);

    public void m_5680_(FriendlyByteBuf var1);

    public void m_5678_(FriendlyByteBuf var1);

    public int m_6429_();

    public int m_62680_();

    public Palette<T> m_199814_();

    public static interface Factory {
        public <A> Palette<A> m_188026_(int var1, IdMap<A> var2, PaletteResize<A> var3, List<A> var4);
    }
}

