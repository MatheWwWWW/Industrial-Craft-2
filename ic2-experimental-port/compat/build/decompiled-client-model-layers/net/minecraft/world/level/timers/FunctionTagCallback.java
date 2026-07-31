/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.timers;

import java.util.Collection;
import net.minecraft.commands.CommandFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.world.level.timers.TimerCallback;
import net.minecraft.world.level.timers.TimerQueue;

public class FunctionTagCallback
implements TimerCallback<MinecraftServer> {
    final ResourceLocation f_82189_;

    public FunctionTagCallback(ResourceLocation p_82191_) {
        this.f_82189_ = p_82191_;
    }

    @Override
    public void m_5821_(MinecraftServer p_82199_, TimerQueue<MinecraftServer> p_82200_, long p_82201_) {
        ServerFunctionManager $$3 = p_82199_.m_129890_();
        Collection<CommandFunction> $$4 = $$3.m_214331_(this.f_82189_);
        for (CommandFunction $$5 : $$4) {
            $$3.m_136112_($$5, $$3.m_136129_());
        }
    }

    @Override
    public /* synthetic */ void m_5821_(Object object, TimerQueue timerQueue, long l) {
        this.m_5821_((MinecraftServer)object, (TimerQueue<MinecraftServer>)timerQueue, l);
    }

    public static class Serializer
    extends TimerCallback.Serializer<MinecraftServer, FunctionTagCallback> {
        public Serializer() {
            super(new ResourceLocation("function_tag"), FunctionTagCallback.class);
        }

        @Override
        public void m_6585_(CompoundTag p_82206_, FunctionTagCallback p_82207_) {
            p_82206_.m_128359_("Name", p_82207_.f_82189_.toString());
        }

        @Override
        public FunctionTagCallback m_6006_(CompoundTag p_82204_) {
            ResourceLocation $$1 = new ResourceLocation(p_82204_.m_128461_("Name"));
            return new FunctionTagCallback($$1);
        }

        @Override
        public /* synthetic */ TimerCallback m_6006_(CompoundTag compoundTag) {
            return this.m_6006_(compoundTag);
        }
    }
}

