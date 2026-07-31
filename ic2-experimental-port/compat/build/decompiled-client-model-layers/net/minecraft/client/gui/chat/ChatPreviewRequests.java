/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ServerboundChatPreviewPacket;
import net.minecraft.util.RandomSource;

public class ChatPreviewRequests {
    private static final long f_232367_ = 100L;
    private static final long f_232368_ = 1000L;
    private final Minecraft f_232369_;
    private final QueryIdGenerator f_232370_ = new QueryIdGenerator();
    @Nullable
    private PendingPreview f_232371_;
    private long f_232372_;

    public ChatPreviewRequests(Minecraft p_232374_) {
        this.f_232369_ = p_232374_;
    }

    public boolean m_232380_(String p_232381_, long p_232382_) {
        ClientPacketListener $$2 = this.f_232369_.m_91403_();
        if ($$2 == null) {
            this.m_232375_();
            return true;
        }
        if (this.f_232371_ != null && this.f_232371_.m_232392_(p_232381_)) {
            return true;
        }
        if (this.f_232369_.m_91090_() || this.m_232378_(p_232382_)) {
            PendingPreview $$3;
            this.f_232371_ = $$3 = new PendingPreview(this.f_232370_.m_232403_(), p_232381_);
            this.f_232372_ = p_232382_;
            $$2.m_104955_(new ServerboundChatPreviewPacket($$3.f_232384_(), $$3.f_232385_()));
            return true;
        }
        return false;
    }

    @Nullable
    public String m_232376_(int p_232377_) {
        if (this.f_232371_ != null && this.f_232371_.m_232390_(p_232377_)) {
            String $$1 = this.f_232371_.f_232385_;
            this.f_232371_ = null;
            return $$1;
        }
        return null;
    }

    private boolean m_232378_(long p_232379_) {
        long $$1 = this.f_232372_ + 100L;
        if (p_232379_ >= $$1) {
            long $$2 = this.f_232372_ + 1000L;
            return this.f_232371_ == null || p_232379_ >= $$2;
        }
        return false;
    }

    public void m_232375_() {
        this.f_232371_ = null;
        this.f_232372_ = 0L;
    }

    public boolean m_232383_() {
        return this.f_232371_ != null;
    }

    static class QueryIdGenerator {
        private static final int f_232399_ = 100;
        private final RandomSource f_232400_ = RandomSource.m_216343_();
        private int f_232401_;

        QueryIdGenerator() {
        }

        public int m_232403_() {
            int $$0;
            this.f_232401_ = $$0 = this.f_232401_ + this.f_232400_.m_188503_(100);
            return $$0;
        }
    }

    record PendingPreview(int f_232384_, String f_232385_) {
        public boolean m_232390_(int p_232391_) {
            return this.f_232384_ == p_232391_;
        }

        public boolean m_232392_(String p_232393_) {
            return this.f_232385_.equals(p_232393_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{PendingPreview.class, "id;query", "f_232384_", "f_232385_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PendingPreview.class, "id;query", "f_232384_", "f_232385_"}, this);
        }

        @Override
        public final boolean equals(Object p_232396_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PendingPreview.class, "id;query", "f_232384_", "f_232385_"}, this, p_232396_);
        }
    }
}

