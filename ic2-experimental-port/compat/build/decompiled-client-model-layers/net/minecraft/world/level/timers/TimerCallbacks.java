/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.timers;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.timers.FunctionCallback;
import net.minecraft.world.level.timers.FunctionTagCallback;
import net.minecraft.world.level.timers.TimerCallback;
import org.slf4j.Logger;

public class TimerCallbacks<C> {
    private static final Logger f_82227_ = LogUtils.getLogger();
    public static final TimerCallbacks<MinecraftServer> f_82226_ = new TimerCallbacks<MinecraftServer>().m_82232_(new FunctionCallback.Serializer()).m_82232_(new FunctionTagCallback.Serializer());
    private final Map<ResourceLocation, TimerCallback.Serializer<C, ?>> f_82228_ = Maps.newHashMap();
    private final Map<Class<?>, TimerCallback.Serializer<C, ?>> f_82229_ = Maps.newHashMap();

    @VisibleForTesting
    public TimerCallbacks() {
    }

    public TimerCallbacks<C> m_82232_(TimerCallback.Serializer<C, ?> p_82233_) {
        this.f_82228_.put(p_82233_.m_82221_(), p_82233_);
        this.f_82229_.put(p_82233_.m_82224_(), p_82233_);
        return this;
    }

    private <T extends TimerCallback<C>> TimerCallback.Serializer<C, T> m_82236_(Class<?> p_82237_) {
        return this.f_82229_.get(p_82237_);
    }

    public <T extends TimerCallback<C>> CompoundTag m_82234_(T p_82235_) {
        TimerCallback.Serializer<T, T> $$1 = this.m_82236_(p_82235_.getClass());
        CompoundTag $$2 = new CompoundTag();
        $$1.m_6585_($$2, p_82235_);
        $$2.m_128359_("Type", $$1.m_82221_().toString());
        return $$2;
    }

    @Nullable
    public TimerCallback<C> m_82238_(CompoundTag p_82239_) {
        ResourceLocation $$1 = ResourceLocation.m_135820_(p_82239_.m_128461_("Type"));
        TimerCallback.Serializer<C, ?> $$2 = this.f_82228_.get($$1);
        if ($$2 == null) {
            f_82227_.error("Failed to deserialize timer callback: {}", (Object)p_82239_);
            return null;
        }
        try {
            return $$2.m_6006_(p_82239_);
        }
        catch (Exception $$3) {
            f_82227_.error("Failed to deserialize timer callback: {}", (Object)p_82239_, (Object)$$3);
            return null;
        }
    }
}

