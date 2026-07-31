/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;

public interface PositionSourceType<T extends PositionSource> {
    public static final PositionSourceType<BlockPositionSource> f_157871_ = PositionSourceType.m_157877_("block", new BlockPositionSource.Type());
    public static final PositionSourceType<EntityPositionSource> f_157872_ = PositionSourceType.m_157877_("entity", new EntityPositionSource.Type());

    public T m_142281_(FriendlyByteBuf var1);

    public void m_142235_(FriendlyByteBuf var1, T var2);

    public Codec<T> m_142341_();

    public static <S extends PositionSourceType<T>, T extends PositionSource> S m_157877_(String p_157878_, S p_157879_) {
        return (S)Registry.m_122961_(Registry.f_175420_, p_157878_, p_157879_);
    }

    public static PositionSource m_157885_(FriendlyByteBuf p_157886_) {
        ResourceLocation $$1 = p_157886_.m_130281_();
        return Registry.f_175420_.m_6612_($$1).orElseThrow(() -> new IllegalArgumentException("Unknown position source type " + $$1)).m_142281_(p_157886_);
    }

    public static <T extends PositionSource> void m_157874_(T p_157875_, FriendlyByteBuf p_157876_) {
        p_157876_.m_130085_(Registry.f_175420_.m_7981_(p_157875_.m_142510_()));
        p_157875_.m_142510_().m_142235_(p_157876_, p_157875_);
    }
}

