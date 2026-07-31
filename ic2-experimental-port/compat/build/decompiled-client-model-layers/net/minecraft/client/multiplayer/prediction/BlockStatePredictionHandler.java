/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 */
package net.minecraft.client.multiplayer.prediction;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BlockStatePredictionHandler
implements AutoCloseable {
    private final Long2ObjectOpenHashMap<ServerVerifiedState> f_233851_ = new Long2ObjectOpenHashMap();
    private int f_233852_;
    private boolean f_233853_;

    public void m_233867_(BlockPos p_233868_, BlockState p_233869_, LocalPlayer p_233870_) {
        this.f_233851_.compute(p_233868_.m_121878_(), (p_233862_, p_233863_) -> {
            if (p_233863_ != null) {
                return p_233863_.m_233881_(this.f_233852_);
            }
            return new ServerVerifiedState(this.f_233852_, p_233869_, p_233870_.m_20182_());
        });
    }

    public boolean m_233864_(BlockPos p_233865_, BlockState p_233866_) {
        ServerVerifiedState $$2 = (ServerVerifiedState)this.f_233851_.get(p_233865_.m_121878_());
        if ($$2 == null) {
            return false;
        }
        $$2.m_233883_(p_233866_);
        return true;
    }

    public void m_233856_(int p_233857_, ClientLevel p_233858_) {
        ObjectIterator $$2 = this.f_233851_.long2ObjectEntrySet().iterator();
        while ($$2.hasNext()) {
            Long2ObjectMap.Entry $$3 = (Long2ObjectMap.Entry)$$2.next();
            ServerVerifiedState $$4 = (ServerVerifiedState)$$3.getValue();
            if ($$4.f_233875_ > p_233857_) continue;
            BlockPos $$5 = BlockPos.m_122022_($$3.getLongKey());
            $$2.remove();
            p_233858_.m_233647_($$5, $$4.f_233876_, $$4.f_233874_);
        }
    }

    public BlockStatePredictionHandler m_233855_() {
        ++this.f_233852_;
        this.f_233853_ = true;
        return this;
    }

    @Override
    public void close() {
        this.f_233853_ = false;
    }

    public int m_233871_() {
        return this.f_233852_;
    }

    public boolean m_233872_() {
        return this.f_233853_;
    }

    static class ServerVerifiedState {
        final Vec3 f_233874_;
        int f_233875_;
        BlockState f_233876_;

        ServerVerifiedState(int p_233878_, BlockState p_233879_, Vec3 p_233880_) {
            this.f_233875_ = p_233878_;
            this.f_233876_ = p_233879_;
            this.f_233874_ = p_233880_;
        }

        ServerVerifiedState m_233881_(int p_233882_) {
            this.f_233875_ = p_233882_;
            return this;
        }

        void m_233883_(BlockState p_233884_) {
            this.f_233876_ = p_233884_;
        }
    }
}

