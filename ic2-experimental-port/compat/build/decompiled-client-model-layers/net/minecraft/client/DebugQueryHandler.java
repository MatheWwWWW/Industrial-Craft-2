/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;

public class DebugQueryHandler {
    private final ClientPacketListener f_90697_;
    private int f_90698_ = -1;
    @Nullable
    private Consumer<CompoundTag> f_90699_;

    public DebugQueryHandler(ClientPacketListener p_90701_) {
        this.f_90697_ = p_90701_;
    }

    public boolean m_90705_(int p_90706_, @Nullable CompoundTag p_90707_) {
        if (this.f_90698_ == p_90706_ && this.f_90699_ != null) {
            this.f_90699_.accept(p_90707_);
            this.f_90699_ = null;
            return true;
        }
        return false;
    }

    private int m_90711_(Consumer<CompoundTag> p_90712_) {
        this.f_90699_ = p_90712_;
        return ++this.f_90698_;
    }

    public void m_90702_(int p_90703_, Consumer<CompoundTag> p_90704_) {
        int $$2 = this.m_90711_(p_90704_);
        this.f_90697_.m_104955_(new ServerboundEntityTagQuery($$2, p_90703_));
    }

    public void m_90708_(BlockPos p_90709_, Consumer<CompoundTag> p_90710_) {
        int $$2 = this.m_90711_(p_90710_);
        this.f_90697_.m_104955_(new ServerboundBlockEntityTagQuery($$2, p_90709_));
    }
}

