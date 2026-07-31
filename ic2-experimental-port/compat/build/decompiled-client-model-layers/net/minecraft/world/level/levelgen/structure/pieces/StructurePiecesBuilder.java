/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.pieces;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;

public class StructurePiecesBuilder
implements StructurePieceAccessor {
    private final List<StructurePiece> f_192778_ = Lists.newArrayList();

    @Override
    public void m_142679_(StructurePiece p_192791_) {
        this.f_192778_.add(p_192791_);
    }

    @Override
    @Nullable
    public StructurePiece m_141921_(BoundingBox p_192789_) {
        return StructurePiece.m_192648_(this.f_192778_, p_192789_);
    }

    @Deprecated
    public void m_192781_(int p_192782_) {
        for (StructurePiece $$1 : this.f_192778_) {
            $$1.m_6324_(0, p_192782_, 0);
        }
    }

    @Deprecated
    public int m_226965_(int p_226966_, int p_226967_, RandomSource p_226968_, int p_226969_) {
        int $$4 = p_226966_ - p_226969_;
        BoundingBox $$5 = this.m_192798_();
        int $$6 = $$5.m_71057_() + p_226967_ + 1;
        if ($$6 < $$4) {
            $$6 += p_226968_.m_188503_($$4 - $$6);
        }
        int $$7 = $$6 - $$5.m_162400_();
        this.m_192781_($$7);
        return $$7;
    }

    public void m_226970_(RandomSource p_226971_, int p_226972_, int p_226973_) {
        int $$6;
        BoundingBox $$3 = this.m_192798_();
        int $$4 = p_226973_ - p_226972_ + 1 - $$3.m_71057_();
        if ($$4 > 1) {
            int $$5 = p_226972_ + p_226971_.m_188503_($$4);
        } else {
            $$6 = p_226972_;
        }
        int $$7 = $$6 - $$3.m_162396_();
        this.m_192781_($$7);
    }

    public PiecesContainer m_192780_() {
        return new PiecesContainer(this.f_192778_);
    }

    public void m_192796_() {
        this.f_192778_.clear();
    }

    public boolean m_192797_() {
        return this.f_192778_.isEmpty();
    }

    public BoundingBox m_192798_() {
        return StructurePiece.m_192651_(this.f_192778_.stream());
    }
}

