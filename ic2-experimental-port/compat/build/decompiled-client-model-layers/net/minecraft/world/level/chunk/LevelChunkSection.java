/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.chunk;

import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.level.material.FluidState;

public class LevelChunkSection {
    public static final int f_156455_ = 16;
    public static final int f_156456_ = 16;
    public static final int f_156457_ = 4096;
    public static final int f_187994_ = 2;
    private final int f_62968_;
    private short f_62969_;
    private short f_62970_;
    private short f_62971_;
    private final PalettedContainer<BlockState> f_62972_;
    private PalettedContainerRO<Holder<Biome>> f_187995_;

    public LevelChunkSection(int p_238255_, PalettedContainer<BlockState> p_238256_, PalettedContainerRO<Holder<Biome>> p_238257_) {
        this.f_62968_ = LevelChunkSection.m_156458_(p_238255_);
        this.f_62972_ = p_238256_;
        this.f_187995_ = p_238257_;
        this.m_63018_();
    }

    public LevelChunkSection(int p_188001_, Registry<Biome> p_188002_) {
        this.f_62968_ = LevelChunkSection.m_156458_(p_188001_);
        this.f_62972_ = new PalettedContainer<BlockState>(Block.f_49791_, Blocks.f_50016_.m_49966_(), PalettedContainer.Strategy.f_188137_);
        this.f_187995_ = new PalettedContainer<Holder<Biome>>(p_188002_.m_206115_(), p_188002_.m_206081_(Biomes.f_48202_), PalettedContainer.Strategy.f_188138_);
    }

    public static int m_156458_(int p_156459_) {
        return p_156459_ << 4;
    }

    public BlockState m_62982_(int p_62983_, int p_62984_, int p_62985_) {
        return this.f_62972_.m_63087_(p_62983_, p_62984_, p_62985_);
    }

    public FluidState m_63007_(int p_63008_, int p_63009_, int p_63010_) {
        return this.f_62972_.m_63087_(p_63008_, p_63009_, p_63010_).m_60819_();
    }

    public void m_62981_() {
        this.f_62972_.m_63084_();
    }

    public void m_63006_() {
        this.f_62972_.m_63120_();
    }

    public BlockState m_62986_(int p_62987_, int p_62988_, int p_62989_, BlockState p_62990_) {
        return this.m_62991_(p_62987_, p_62988_, p_62989_, p_62990_, true);
    }

    public BlockState m_62991_(int p_62992_, int p_62993_, int p_62994_, BlockState p_62995_, boolean p_62996_) {
        BlockState $$6;
        if (p_62996_) {
            BlockState $$5 = this.f_62972_.m_63091_(p_62992_, p_62993_, p_62994_, p_62995_);
        } else {
            $$6 = this.f_62972_.m_63127_(p_62992_, p_62993_, p_62994_, p_62995_);
        }
        FluidState $$7 = $$6.m_60819_();
        FluidState $$8 = p_62995_.m_60819_();
        if (!$$6.m_60795_()) {
            this.f_62969_ = (short)(this.f_62969_ - 1);
            if ($$6.m_60823_()) {
                this.f_62970_ = (short)(this.f_62970_ - 1);
            }
        }
        if (!$$7.m_76178_()) {
            this.f_62971_ = (short)(this.f_62971_ - 1);
        }
        if (!p_62995_.m_60795_()) {
            this.f_62969_ = (short)(this.f_62969_ + 1);
            if (p_62995_.m_60823_()) {
                this.f_62970_ = (short)(this.f_62970_ + 1);
            }
        }
        if (!$$8.m_76178_()) {
            this.f_62971_ = (short)(this.f_62971_ + 1);
        }
        return $$6;
    }

    public boolean m_188008_() {
        return this.f_62969_ == 0;
    }

    public boolean m_63014_() {
        return this.m_63015_() || this.m_63016_();
    }

    public boolean m_63015_() {
        return this.f_62970_ > 0;
    }

    public boolean m_63016_() {
        return this.f_62971_ > 0;
    }

    public int m_63017_() {
        return this.f_62968_;
    }

    public void m_63018_() {
        class BlockCounter
        implements PalettedContainer.CountConsumer<BlockState> {
            public int f_204437_;
            public int f_204438_;
            public int f_204439_;

            BlockCounter() {
            }

            @Override
            public void m_63144_(BlockState p_204444_, int p_204445_) {
                FluidState $$2 = p_204444_.m_60819_();
                if (!p_204444_.m_60795_()) {
                    this.f_204437_ += p_204445_;
                    if (p_204444_.m_60823_()) {
                        this.f_204438_ += p_204445_;
                    }
                }
                if (!$$2.m_76178_()) {
                    this.f_204437_ += p_204445_;
                    if ($$2.m_76187_()) {
                        this.f_204439_ += p_204445_;
                    }
                }
            }

            @Override
            public /* synthetic */ void m_63144_(Object object, int n) {
                this.m_63144_((BlockState)object, n);
            }
        }
        BlockCounter $$0 = new BlockCounter();
        this.f_62972_.m_63099_($$0);
        this.f_62969_ = (short)$$0.f_204437_;
        this.f_62970_ = (short)$$0.f_204438_;
        this.f_62971_ = (short)$$0.f_204439_;
    }

    public PalettedContainer<BlockState> m_63019_() {
        return this.f_62972_;
    }

    public PalettedContainerRO<Holder<Biome>> m_187996_() {
        return this.f_187995_;
    }

    public void m_63004_(FriendlyByteBuf p_63005_) {
        this.f_62969_ = p_63005_.readShort();
        this.f_62972_.m_63118_(p_63005_);
        PalettedContainer<Holder<Biome>> $$1 = this.f_187995_.m_238334_();
        $$1.m_63118_(p_63005_);
        this.f_187995_ = $$1;
    }

    public void m_63011_(FriendlyByteBuf p_63012_) {
        p_63012_.writeShort(this.f_62969_);
        this.f_62972_.m_63135_(p_63012_);
        this.f_187995_.m_63135_(p_63012_);
    }

    public int m_63020_() {
        return 2 + this.f_62972_.m_63137_() + this.f_187995_.m_63137_();
    }

    public boolean m_63002_(Predicate<BlockState> p_63003_) {
        return this.f_62972_.m_63109_(p_63003_);
    }

    public Holder<Biome> m_204433_(int p_204434_, int p_204435_, int p_204436_) {
        return this.f_187995_.m_63087_(p_204434_, p_204435_, p_204436_);
    }

    public void m_188003_(BiomeResolver p_188004_, Climate.Sampler p_188005_, int p_188006_, int p_188007_) {
        PalettedContainer<Holder<Biome>> $$4 = this.f_187995_.m_238334_();
        int $$5 = QuartPos.m_175400_(this.m_63017_());
        int $$6 = 4;
        for (int $$7 = 0; $$7 < 4; ++$$7) {
            for (int $$8 = 0; $$8 < 4; ++$$8) {
                for (int $$9 = 0; $$9 < 4; ++$$9) {
                    $$4.m_63127_($$7, $$8, $$9, p_188004_.m_203407_(p_188006_ + $$7, $$5 + $$8, p_188007_ + $$9, p_188005_));
                }
            }
        }
        this.f_187995_ = $$4;
    }
}

