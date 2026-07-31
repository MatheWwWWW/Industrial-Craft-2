/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.shorts.ShortIterator
 *  it.unimi.dsi.fastutil.shorts.ShortSet
 */
package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class ClientboundSectionBlocksUpdatePacket
implements Packet<ClientGamePacketListener> {
    private static final int f_179194_ = 12;
    private final SectionPos f_132980_;
    private final short[] f_132981_;
    private final BlockState[] f_132982_;
    private final boolean f_132983_;

    public ClientboundSectionBlocksUpdatePacket(SectionPos p_132986_, ShortSet p_132987_, LevelChunkSection p_132988_, boolean p_132989_) {
        this.f_132980_ = p_132986_;
        this.f_132983_ = p_132989_;
        int $$4 = p_132987_.size();
        this.f_132981_ = new short[$$4];
        this.f_132982_ = new BlockState[$$4];
        int $$5 = 0;
        ShortIterator shortIterator = p_132987_.iterator();
        while (shortIterator.hasNext()) {
            short $$6;
            this.f_132981_[$$5] = $$6 = ((Short)shortIterator.next()).shortValue();
            this.f_132982_[$$5] = p_132988_.m_62982_(SectionPos.m_123204_($$6), SectionPos.m_123220_($$6), SectionPos.m_123227_($$6));
            ++$$5;
        }
    }

    public ClientboundSectionBlocksUpdatePacket(FriendlyByteBuf p_179196_) {
        this.f_132980_ = SectionPos.m_123184_(p_179196_.readLong());
        this.f_132983_ = p_179196_.readBoolean();
        int $$1 = p_179196_.m_130242_();
        this.f_132981_ = new short[$$1];
        this.f_132982_ = new BlockState[$$1];
        for (int $$2 = 0; $$2 < $$1; ++$$2) {
            long $$3 = p_179196_.m_130258_();
            this.f_132981_[$$2] = (short)($$3 & 0xFFFL);
            this.f_132982_[$$2] = Block.f_49791_.m_7942_((int)($$3 >>> 12));
        }
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133002_) {
        p_133002_.writeLong(this.f_132980_.m_123252_());
        p_133002_.writeBoolean(this.f_132983_);
        p_133002_.m_130130_(this.f_132981_.length);
        for (int $$1 = 0; $$1 < this.f_132981_.length; ++$$1) {
            p_133002_.m_130103_(Block.m_49956_(this.f_132982_[$$1]) << 12 | this.f_132981_[$$1]);
        }
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132999_) {
        p_132999_.m_5771_(this);
    }

    public void m_132992_(BiConsumer<BlockPos, BlockState> p_132993_) {
        BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
        for (int $$2 = 0; $$2 < this.f_132981_.length; ++$$2) {
            short $$3 = this.f_132981_[$$2];
            $$1.m_122178_(this.f_132980_.m_123232_($$3), this.f_132980_.m_123237_($$3), this.f_132980_.m_123242_($$3));
            p_132993_.accept($$1, this.f_132982_[$$2]);
        }
    }

    public boolean m_133000_() {
        return this.f_132983_;
    }
}

