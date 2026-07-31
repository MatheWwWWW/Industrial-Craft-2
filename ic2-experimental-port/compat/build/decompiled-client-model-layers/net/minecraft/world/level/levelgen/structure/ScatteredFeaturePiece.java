/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public abstract class ScatteredFeaturePiece
extends StructurePiece {
    protected final int f_72787_;
    protected final int f_72788_;
    protected final int f_72789_;
    protected int f_72790_ = -1;

    protected ScatteredFeaturePiece(StructurePieceType p_209920_, int p_209921_, int p_209922_, int p_209923_, int p_209924_, int p_209925_, int p_209926_, Direction p_209927_) {
        super(p_209920_, 0, StructurePiece.m_163541_(p_209921_, p_209922_, p_209923_, p_209927_, p_209924_, p_209925_, p_209926_));
        this.f_72787_ = p_209924_;
        this.f_72788_ = p_209925_;
        this.f_72789_ = p_209926_;
        this.m_73519_(p_209927_);
    }

    protected ScatteredFeaturePiece(StructurePieceType p_209929_, CompoundTag p_209930_) {
        super(p_209929_, p_209930_);
        this.f_72787_ = p_209930_.m_128451_("Width");
        this.f_72788_ = p_209930_.m_128451_("Height");
        this.f_72789_ = p_209930_.m_128451_("Depth");
        this.f_72790_ = p_209930_.m_128451_("HPos");
    }

    @Override
    protected void m_183620_(StructurePieceSerializationContext p_192471_, CompoundTag p_192472_) {
        p_192472_.m_128405_("Width", this.f_72787_);
        p_192472_.m_128405_("Height", this.f_72788_);
        p_192472_.m_128405_("Depth", this.f_72789_);
        p_192472_.m_128405_("HPos", this.f_72790_);
    }

    protected boolean m_72803_(LevelAccessor p_72804_, BoundingBox p_72805_, int p_72806_) {
        if (this.f_72790_ >= 0) {
            return true;
        }
        int $$3 = 0;
        int $$4 = 0;
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
        for (int $$6 = this.f_73383_.m_162398_(); $$6 <= this.f_73383_.m_162401_(); ++$$6) {
            for (int $$7 = this.f_73383_.m_162395_(); $$7 <= this.f_73383_.m_162399_(); ++$$7) {
                $$5.m_122178_($$7, 64, $$6);
                if (!p_72805_.m_71051_($$5)) continue;
                $$3 += p_72804_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$5).m_123342_();
                ++$$4;
            }
        }
        if ($$4 == 0) {
            return false;
        }
        this.f_72790_ = $$3 / $$4;
        this.f_73383_.m_162367_(0, this.f_72790_ - this.f_73383_.m_162396_() + p_72806_, 0);
        return true;
    }

    protected boolean m_192467_(LevelAccessor p_192468_, int p_192469_) {
        if (this.f_72790_ >= 0) {
            return true;
        }
        int $$2 = p_192468_.m_151558_();
        boolean $$3 = false;
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (int $$5 = this.f_73383_.m_162398_(); $$5 <= this.f_73383_.m_162401_(); ++$$5) {
            for (int $$6 = this.f_73383_.m_162395_(); $$6 <= this.f_73383_.m_162399_(); ++$$6) {
                $$4.m_122178_($$6, 0, $$5);
                $$2 = Math.min($$2, p_192468_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$4).m_123342_());
                $$3 = true;
            }
        }
        if (!$$3) {
            return false;
        }
        this.f_72790_ = $$2;
        this.f_73383_.m_162367_(0, this.f_72790_ - this.f_73383_.m_162396_() + p_192469_, 0);
        return true;
    }
}

