/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.chat.contents;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.contents.DataSource;
import net.minecraft.resources.ResourceLocation;

public record StorageDataSource(ResourceLocation f_237484_) implements DataSource
{
    @Override
    public Stream<CompoundTag> m_213601_(CommandSourceStack p_237491_) {
        CompoundTag $$1 = p_237491_.m_81377_().m_129897_().m_78044_(this.f_237484_);
        return Stream.of($$1);
    }

    @Override
    public String toString() {
        return "storage=" + this.f_237484_;
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{StorageDataSource.class, "id", "f_237484_"}, this);
    }

    @Override
    public final boolean equals(Object p_237489_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{StorageDataSource.class, "id", "f_237484_"}, this, p_237489_);
    }
}

