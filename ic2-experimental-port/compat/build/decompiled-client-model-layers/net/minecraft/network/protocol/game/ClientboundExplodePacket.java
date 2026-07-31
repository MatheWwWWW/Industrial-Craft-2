/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class ClientboundExplodePacket
implements Packet<ClientGamePacketListener> {
    private final double f_132105_;
    private final double f_132106_;
    private final double f_132107_;
    private final float f_132108_;
    private final List<BlockPos> f_132109_;
    private final float f_132110_;
    private final float f_132111_;
    private final float f_132112_;

    public ClientboundExplodePacket(double p_132115_, double p_132116_, double p_132117_, float p_132118_, List<BlockPos> p_132119_, @Nullable Vec3 p_132120_) {
        this.f_132105_ = p_132115_;
        this.f_132106_ = p_132116_;
        this.f_132107_ = p_132117_;
        this.f_132108_ = p_132118_;
        this.f_132109_ = Lists.newArrayList(p_132119_);
        if (p_132120_ != null) {
            this.f_132110_ = (float)p_132120_.f_82479_;
            this.f_132111_ = (float)p_132120_.f_82480_;
            this.f_132112_ = (float)p_132120_.f_82481_;
        } else {
            this.f_132110_ = 0.0f;
            this.f_132111_ = 0.0f;
            this.f_132112_ = 0.0f;
        }
    }

    public ClientboundExplodePacket(FriendlyByteBuf p_178845_) {
        this.f_132105_ = p_178845_.readFloat();
        this.f_132106_ = p_178845_.readFloat();
        this.f_132107_ = p_178845_.readFloat();
        this.f_132108_ = p_178845_.readFloat();
        int $$1 = Mth.m_14107_(this.f_132105_);
        int $$2 = Mth.m_14107_(this.f_132106_);
        int $$3 = Mth.m_14107_(this.f_132107_);
        this.f_132109_ = p_178845_.m_236845_(p_178850_ -> {
            int $$4 = p_178850_.readByte() + $$1;
            int $$5 = p_178850_.readByte() + $$2;
            int $$6 = p_178850_.readByte() + $$3;
            return new BlockPos($$4, $$5, $$6);
        });
        this.f_132110_ = p_178845_.readFloat();
        this.f_132111_ = p_178845_.readFloat();
        this.f_132112_ = p_178845_.readFloat();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132129_) {
        p_132129_.writeFloat((float)this.f_132105_);
        p_132129_.writeFloat((float)this.f_132106_);
        p_132129_.writeFloat((float)this.f_132107_);
        p_132129_.writeFloat(this.f_132108_);
        int $$1 = Mth.m_14107_(this.f_132105_);
        int $$2 = Mth.m_14107_(this.f_132106_);
        int $$3 = Mth.m_14107_(this.f_132107_);
        p_132129_.m_236828_(this.f_132109_, (p_178855_, p_178856_) -> {
            int $$5 = p_178856_.m_123341_() - $$1;
            int $$6 = p_178856_.m_123342_() - $$2;
            int $$7 = p_178856_.m_123343_() - $$3;
            p_178855_.writeByte($$5);
            p_178855_.writeByte($$6);
            p_178855_.writeByte($$7);
        });
        p_132129_.writeFloat(this.f_132110_);
        p_132129_.writeFloat(this.f_132111_);
        p_132129_.writeFloat(this.f_132112_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132126_) {
        p_132126_.m_7345_(this);
    }

    public float m_132127_() {
        return this.f_132110_;
    }

    public float m_132130_() {
        return this.f_132111_;
    }

    public float m_132131_() {
        return this.f_132112_;
    }

    public double m_132132_() {
        return this.f_132105_;
    }

    public double m_132133_() {
        return this.f_132106_;
    }

    public double m_132134_() {
        return this.f_132107_;
    }

    public float m_132135_() {
        return this.f_132108_;
    }

    public List<BlockPos> m_132136_() {
        return this.f_132109_;
    }
}

