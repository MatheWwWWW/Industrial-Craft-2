/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.lighting.LevelLightEngine;

public class ClientboundLightUpdatePacketData {
    private final BitSet f_195723_;
    private final BitSet f_195724_;
    private final BitSet f_195725_;
    private final BitSet f_195726_;
    private final List<byte[]> f_195727_;
    private final List<byte[]> f_195728_;
    private final boolean f_195729_;

    public ClientboundLightUpdatePacketData(ChunkPos p_195731_, LevelLightEngine p_195732_, @Nullable BitSet p_195733_, @Nullable BitSet p_195734_, boolean p_195735_) {
        this.f_195729_ = p_195735_;
        this.f_195723_ = new BitSet();
        this.f_195724_ = new BitSet();
        this.f_195725_ = new BitSet();
        this.f_195726_ = new BitSet();
        this.f_195727_ = Lists.newArrayList();
        this.f_195728_ = Lists.newArrayList();
        for (int $$5 = 0; $$5 < p_195732_.m_164446_(); ++$$5) {
            if (p_195733_ == null || p_195733_.get($$5)) {
                this.m_195741_(p_195731_, p_195732_, LightLayer.SKY, $$5, this.f_195723_, this.f_195725_, this.f_195727_);
            }
            if (p_195734_ != null && !p_195734_.get($$5)) continue;
            this.m_195741_(p_195731_, p_195732_, LightLayer.BLOCK, $$5, this.f_195724_, this.f_195726_, this.f_195728_);
        }
    }

    public ClientboundLightUpdatePacketData(FriendlyByteBuf p_195737_, int p_195738_, int p_195739_) {
        this.f_195729_ = p_195737_.readBoolean();
        this.f_195723_ = p_195737_.m_178384_();
        this.f_195724_ = p_195737_.m_178384_();
        this.f_195725_ = p_195737_.m_178384_();
        this.f_195726_ = p_195737_.m_178384_();
        this.f_195727_ = p_195737_.m_236845_(p_195756_ -> p_195756_.m_130101_(2048));
        this.f_195728_ = p_195737_.m_236845_(p_195753_ -> p_195753_.m_130101_(2048));
    }

    public void m_195749_(FriendlyByteBuf p_195750_) {
        p_195750_.writeBoolean(this.f_195729_);
        p_195750_.m_178350_(this.f_195723_);
        p_195750_.m_178350_(this.f_195724_);
        p_195750_.m_178350_(this.f_195725_);
        p_195750_.m_178350_(this.f_195726_);
        p_195750_.m_236828_(this.f_195727_, FriendlyByteBuf::m_130087_);
        p_195750_.m_236828_(this.f_195728_, FriendlyByteBuf::m_130087_);
    }

    private void m_195741_(ChunkPos p_195742_, LevelLightEngine p_195743_, LightLayer p_195744_, int p_195745_, BitSet p_195746_, BitSet p_195747_, List<byte[]> p_195748_) {
        DataLayer $$7 = p_195743_.m_75814_(p_195744_).m_8079_(SectionPos.m_123196_(p_195742_, p_195743_.m_164447_() + p_195745_));
        if ($$7 != null) {
            if ($$7.m_62575_()) {
                p_195747_.set(p_195745_);
            } else {
                p_195746_.set(p_195745_);
                p_195748_.add((byte[])$$7.m_7877_().clone());
            }
        }
    }

    public BitSet m_195740_() {
        return this.f_195723_;
    }

    public BitSet m_195751_() {
        return this.f_195725_;
    }

    public List<byte[]> m_195754_() {
        return this.f_195727_;
    }

    public BitSet m_195757_() {
        return this.f_195724_;
    }

    public BitSet m_195758_() {
        return this.f_195726_;
    }

    public List<byte[]> m_195759_() {
        return this.f_195728_;
    }

    public boolean m_195760_() {
        return this.f_195729_;
    }
}

