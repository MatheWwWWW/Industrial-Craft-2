/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.phys.Vec3;

public interface PositionSource {
    public static final Codec<PositionSource> f_157868_ = Registry.f_175420_.m_194605_().dispatch(PositionSource::m_142510_, PositionSourceType::m_142341_);

    public Optional<Vec3> m_142502_(Level var1);

    public PositionSourceType<?> m_142510_();
}

