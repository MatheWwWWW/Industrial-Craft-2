/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.syncher;

import java.util.Optional;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;

public interface EntityDataSerializer<T> {
    public void m_6856_(FriendlyByteBuf var1, T var2);

    public T m_6709_(FriendlyByteBuf var1);

    default public EntityDataAccessor<T> m_135021_(int p_135022_) {
        return new EntityDataAccessor(p_135022_, this);
    }

    public T m_7020_(T var1);

    public static <T> EntityDataSerializer<T> m_238095_(final FriendlyByteBuf.Writer<T> p_238096_, final FriendlyByteBuf.Reader<T> p_238097_) {
        return new ForValueType<T>(){

            @Override
            public void m_6856_(FriendlyByteBuf p_238109_, T p_238110_) {
                p_238096_.accept(p_238109_, p_238110_);
            }

            @Override
            public T m_6709_(FriendlyByteBuf p_238107_) {
                return p_238097_.apply(p_238107_);
            }
        };
    }

    public static <T> EntityDataSerializer<Optional<T>> m_238098_(FriendlyByteBuf.Writer<T> p_238099_, FriendlyByteBuf.Reader<T> p_238100_) {
        return EntityDataSerializer.m_238095_(p_238099_.m_236883_(), p_238100_.m_236879_());
    }

    public static <T extends Enum<T>> EntityDataSerializer<T> m_238090_(Class<T> p_238091_) {
        return EntityDataSerializer.m_238095_(FriendlyByteBuf::m_130068_, p_238094_ -> p_238094_.m_130066_(p_238091_));
    }

    public static <T> EntityDataSerializer<T> m_238081_(IdMap<T> p_238082_) {
        return EntityDataSerializer.m_238095_((p_238088_, p_238089_) -> p_238088_.m_236818_(p_238082_, p_238089_), p_238085_ -> p_238085_.m_236816_(p_238082_));
    }

    public static interface ForValueType<T>
    extends EntityDataSerializer<T> {
        @Override
        default public T m_7020_(T p_238112_) {
            return p_238112_;
        }
    }
}

