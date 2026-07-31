/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

public interface LevelReader
extends BlockAndTintGetter,
CollisionGetter,
BiomeManager.NoiseBiomeSource {
    @Nullable
    public ChunkAccess m_6522_(int var1, int var2, ChunkStatus var3, boolean var4);

    @Deprecated
    public boolean m_7232_(int var1, int var2);

    public int m_6924_(Heightmap.Types var1, int var2, int var3);

    public int m_7445_();

    public BiomeManager m_7062_();

    default public Holder<Biome> m_204166_(BlockPos p_204167_) {
        return this.m_7062_().m_204214_(p_204167_);
    }

    default public Stream<BlockState> m_46847_(AABB p_46848_) {
        int $$6;
        int $$1 = Mth.m_14107_(p_46848_.f_82288_);
        int $$2 = Mth.m_14107_(p_46848_.f_82291_);
        int $$3 = Mth.m_14107_(p_46848_.f_82289_);
        int $$4 = Mth.m_14107_(p_46848_.f_82292_);
        int $$5 = Mth.m_14107_(p_46848_.f_82290_);
        if (this.m_46812_($$1, $$3, $$5, $$2, $$4, $$6 = Mth.m_14107_(p_46848_.f_82293_))) {
            return this.m_45556_(p_46848_);
        }
        return Stream.empty();
    }

    @Override
    default public int m_6171_(BlockPos p_46836_, ColorResolver p_46837_) {
        return p_46837_.m_130045_(this.m_204166_(p_46836_).m_203334_(), p_46836_.m_123341_(), p_46836_.m_123343_());
    }

    @Override
    default public Holder<Biome> m_203495_(int p_204163_, int p_204164_, int p_204165_) {
        ChunkAccess $$3 = this.m_6522_(QuartPos.m_175406_(p_204163_), QuartPos.m_175406_(p_204165_), ChunkStatus.f_62317_, false);
        if ($$3 != null) {
            return $$3.m_203495_(p_204163_, p_204164_, p_204165_);
        }
        return this.m_203675_(p_204163_, p_204164_, p_204165_);
    }

    public Holder<Biome> m_203675_(int var1, int var2, int var3);

    public boolean m_5776_();

    @Deprecated
    public int m_5736_();

    public DimensionType m_6042_();

    @Override
    default public int m_141937_() {
        return this.m_6042_().f_156647_();
    }

    @Override
    default public int m_141928_() {
        return this.m_6042_().f_156648_();
    }

    default public BlockPos m_5452_(Heightmap.Types p_46830_, BlockPos p_46831_) {
        return new BlockPos(p_46831_.m_123341_(), this.m_6924_(p_46830_, p_46831_.m_123341_(), p_46831_.m_123343_()), p_46831_.m_123343_());
    }

    default public boolean m_46859_(BlockPos p_46860_) {
        return this.m_8055_(p_46860_).m_60795_();
    }

    default public boolean m_46861_(BlockPos p_46862_) {
        if (p_46862_.m_123342_() >= this.m_5736_()) {
            return this.m_45527_(p_46862_);
        }
        BlockPos $$1 = new BlockPos(p_46862_.m_123341_(), this.m_5736_(), p_46862_.m_123343_());
        if (!this.m_45527_($$1)) {
            return false;
        }
        $$1 = $$1.m_7495_();
        while ($$1.m_123342_() > p_46862_.m_123342_()) {
            BlockState $$2 = this.m_8055_($$1);
            if ($$2.m_60739_(this, $$1) > 0 && !$$2.m_60767_().m_76332_()) {
                return false;
            }
            $$1 = $$1.m_7495_();
        }
        return true;
    }

    default public float m_220419_(BlockPos p_220420_) {
        return this.m_220417_(p_220420_) - 0.5f;
    }

    @Deprecated
    default public float m_220417_(BlockPos p_220418_) {
        float $$1 = (float)this.m_46803_(p_220418_) / 15.0f;
        float $$2 = $$1 / (4.0f - 3.0f * $$1);
        return Mth.m_14179_(this.m_6042_().f_63838_(), $$2, 1.0f);
    }

    default public int m_46852_(BlockPos p_46853_, Direction p_46854_) {
        return this.m_8055_(p_46853_).m_60775_(this, p_46853_, p_46854_);
    }

    default public ChunkAccess m_46865_(BlockPos p_46866_) {
        return this.m_6325_(SectionPos.m_123171_(p_46866_.m_123341_()), SectionPos.m_123171_(p_46866_.m_123343_()));
    }

    default public ChunkAccess m_6325_(int p_46807_, int p_46808_) {
        return this.m_6522_(p_46807_, p_46808_, ChunkStatus.f_62326_, true);
    }

    default public ChunkAccess m_46819_(int p_46820_, int p_46821_, ChunkStatus p_46822_) {
        return this.m_6522_(p_46820_, p_46821_, p_46822_, true);
    }

    @Override
    @Nullable
    default public BlockGetter m_7925_(int p_46845_, int p_46846_) {
        return this.m_6522_(p_46845_, p_46846_, ChunkStatus.f_62314_, false);
    }

    default public boolean m_46801_(BlockPos p_46802_) {
        return this.m_6425_(p_46802_).m_205070_(FluidTags.f_13131_);
    }

    default public boolean m_46855_(AABB p_46856_) {
        int $$1 = Mth.m_14107_(p_46856_.f_82288_);
        int $$2 = Mth.m_14165_(p_46856_.f_82291_);
        int $$3 = Mth.m_14107_(p_46856_.f_82289_);
        int $$4 = Mth.m_14165_(p_46856_.f_82292_);
        int $$5 = Mth.m_14107_(p_46856_.f_82290_);
        int $$6 = Mth.m_14165_(p_46856_.f_82293_);
        BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
        for (int $$8 = $$1; $$8 < $$2; ++$$8) {
            for (int $$9 = $$3; $$9 < $$4; ++$$9) {
                for (int $$10 = $$5; $$10 < $$6; ++$$10) {
                    BlockState $$11 = this.m_8055_($$7.m_122178_($$8, $$9, $$10));
                    if ($$11.m_60819_().m_76178_()) continue;
                    return true;
                }
            }
        }
        return false;
    }

    default public int m_46803_(BlockPos p_46804_) {
        return this.m_46849_(p_46804_, this.m_7445_());
    }

    default public int m_46849_(BlockPos p_46850_, int p_46851_) {
        if (p_46850_.m_123341_() < -30000000 || p_46850_.m_123343_() < -30000000 || p_46850_.m_123341_() >= 30000000 || p_46850_.m_123343_() >= 30000000) {
            return 15;
        }
        return this.m_45524_(p_46850_, p_46851_);
    }

    @Deprecated
    default public boolean m_151577_(int p_151578_, int p_151579_) {
        return this.m_7232_(SectionPos.m_123171_(p_151578_), SectionPos.m_123171_(p_151579_));
    }

    @Deprecated
    default public boolean m_46805_(BlockPos p_46806_) {
        return this.m_151577_(p_46806_.m_123341_(), p_46806_.m_123343_());
    }

    @Deprecated
    default public boolean m_46832_(BlockPos p_46833_, BlockPos p_46834_) {
        return this.m_46812_(p_46833_.m_123341_(), p_46833_.m_123342_(), p_46833_.m_123343_(), p_46834_.m_123341_(), p_46834_.m_123342_(), p_46834_.m_123343_());
    }

    @Deprecated
    default public boolean m_46812_(int p_46813_, int p_46814_, int p_46815_, int p_46816_, int p_46817_, int p_46818_) {
        if (p_46817_ < this.m_141937_() || p_46814_ >= this.m_151558_()) {
            return false;
        }
        return this.m_151572_(p_46813_, p_46815_, p_46816_, p_46818_);
    }

    @Deprecated
    default public boolean m_151572_(int p_151573_, int p_151574_, int p_151575_, int p_151576_) {
        int $$4 = SectionPos.m_123171_(p_151573_);
        int $$5 = SectionPos.m_123171_(p_151575_);
        int $$6 = SectionPos.m_123171_(p_151574_);
        int $$7 = SectionPos.m_123171_(p_151576_);
        for (int $$8 = $$4; $$8 <= $$5; ++$$8) {
            for (int $$9 = $$6; $$9 <= $$7; ++$$9) {
                if (this.m_7232_($$8, $$9)) continue;
                return false;
            }
        }
        return true;
    }
}

