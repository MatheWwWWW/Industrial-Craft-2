/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.DataResult
 */
package net.minecraft.world.level.chunk;

import com.mojang.serialization.DataResult;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.chunk.PalettedContainer;

public interface PalettedContainerRO<T> {
    public T m_63087_(int var1, int var2, int var3);

    public void m_196879_(Consumer<T> var1);

    public void m_63135_(FriendlyByteBuf var1);

    public int m_63137_();

    public boolean m_63109_(Predicate<T> var1);

    public void m_63099_(PalettedContainer.CountConsumer<T> var1);

    public PalettedContainer<T> m_238334_();

    public PackedData<T> m_188064_(IdMap<T> var1, PalettedContainer.Strategy var2);

    public static interface Unpacker<T, C extends PalettedContainerRO<T>> {
        public DataResult<C> m_238363_(IdMap<T> var1, PalettedContainer.Strategy var2, PackedData<T> var3);
    }

    public record PackedData<T>(List<T> f_238184_, Optional<LongStream> f_238179_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{PackedData.class, "paletteEntries;storage", "f_238184_", "f_238179_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PackedData.class, "paletteEntries;storage", "f_238184_", "f_238179_"}, this);
        }

        @Override
        public final boolean equals(Object p_238439_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PackedData.class, "paletteEntries;storage", "f_238184_", "f_238179_"}, this, p_238439_);
        }
    }
}

