/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.timers;

import net.minecraft.commands.CommandFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.world.level.timers.TimerCallback;
import net.minecraft.world.level.timers.TimerQueue;

public class FunctionCallback
implements TimerCallback<MinecraftServer> {
    final ResourceLocation f_82162_;

    public FunctionCallback(ResourceLocation p_82164_) {
        this.f_82162_ = p_82164_;
    }

    @Override
    public void m_5821_(MinecraftServer p_82172_, TimerQueue<MinecraftServer> p_82173_, long p_82174_) {
        ServerFunctionManager $$3 = p_82172_.m_129890_();
        $$3.m_136118_(this.f_82162_).ifPresent(p_82177_ -> $$3.m_136112_((CommandFunction)p_82177_, $$3.m_136129_()));
    }

    @Override
    public /* synthetic */ void m_5821_(Object object, TimerQueue timerQueue, long l) {
        this.m_5821_((MinecraftServer)object, (TimerQueue<MinecraftServer>)timerQueue, l);
    }

    public static class Serializer
    extends TimerCallback.Serializer<MinecraftServer, FunctionCallback> {
        public Serializer() {
            super(new ResourceLocation("function"), FunctionCallback.class);
        }

        @Override
        public void m_6585_(CompoundTag p_82182_, FunctionCallback p_82183_) {
            p_82182_.m_128359_("Name", p_82183_.f_82162_.toString());
        }

        @Override
        public FunctionCallback m_6006_(CompoundTag p_82180_) {
            ResourceLocation $$1 = new ResourceLocation(p_82180_.m_128461_("Name"));
            return new FunctionCallback($$1);
        }

        @Override
        public /* synthetic */ TimerCallback m_6006_(CompoundTag compoundTag) {
            return this.m_6006_(compoundTag);
        }
    }
}

