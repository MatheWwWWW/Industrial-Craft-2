/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.BitSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ProtoChunk;

public final class BelowZeroRetrogen {
    private static final BitSet f_188457_ = new BitSet(0);
    private static final Codec<BitSet> f_188458_ = Codec.LONG_STREAM.xmap(p_188484_ -> BitSet.valueOf(p_188484_.toArray()), p_188482_ -> LongStream.of(p_188482_.toLongArray()));
    private static final Codec<ChunkStatus> f_188459_ = Registry.f_122833_.m_194605_().comapFlatMap(p_188473_ -> p_188473_ == ChunkStatus.f_62314_ ? DataResult.error((String)"target_status cannot be empty") : DataResult.success((Object)p_188473_), Function.identity());
    public static final Codec<BelowZeroRetrogen> f_188455_ = RecordCodecBuilder.create(p_188471_ -> p_188471_.group((App)f_188459_.fieldOf("target_status").forGetter(BelowZeroRetrogen::m_188466_), (App)f_188458_.optionalFieldOf("missing_bedrock").forGetter(p_188480_ -> p_188480_.f_188461_.isEmpty() ? Optional.empty() : Optional.of(p_188480_.f_188461_))).apply((Applicative)p_188471_, BelowZeroRetrogen::new));
    private static final Set<ResourceKey<Biome>> f_196980_ = Set.of(Biomes.f_151785_, Biomes.f_151784_);
    public static final LevelHeightAccessor f_188456_ = new LevelHeightAccessor(){

        @Override
        public int m_141928_() {
            return 64;
        }

        @Override
        public int m_141937_() {
            return -64;
        }
    };
    private final ChunkStatus f_188460_;
    private final BitSet f_188461_;

    private BelowZeroRetrogen(ChunkStatus p_188464_, Optional<BitSet> p_188465_) {
        this.f_188460_ = p_188464_;
        this.f_188461_ = p_188465_.orElse(f_188457_);
    }

    @Nullable
    public static BelowZeroRetrogen m_188485_(CompoundTag p_188486_) {
        ChunkStatus $$1 = ChunkStatus.m_62397_(p_188486_.m_128461_("target_status"));
        if ($$1 == ChunkStatus.f_62314_) {
            return null;
        }
        return new BelowZeroRetrogen($$1, Optional.of(BitSet.valueOf(p_188486_.m_128467_("missing_bedrock"))));
    }

    public static void m_188474_(ProtoChunk p_188475_) {
        int $$1 = 4;
        BlockPos.m_121976_(0, 0, 0, 15, 4, 15).forEach(p_188492_ -> {
            if (p_188475_.m_8055_((BlockPos)p_188492_).m_60713_(Blocks.f_50752_)) {
                p_188475_.m_6978_((BlockPos)p_188492_, Blocks.f_152550_.m_49966_(), false);
            }
        });
    }

    public void m_198221_(ProtoChunk p_198222_) {
        LevelHeightAccessor $$1 = p_198222_.m_183618_();
        int $$2 = $$1.m_141937_();
        int $$3 = $$1.m_151558_() - 1;
        for (int $$4 = 0; $$4 < 16; ++$$4) {
            for (int $$5 = 0; $$5 < 16; ++$$5) {
                if (!this.m_198214_($$4, $$5)) continue;
                BlockPos.m_121976_($$4, $$2, $$5, $$4, $$3, $$5).forEach(p_198219_ -> p_198222_.m_6978_((BlockPos)p_198219_, Blocks.f_50016_.m_49966_(), false));
            }
        }
    }

    public ChunkStatus m_188466_() {
        return this.f_188460_;
    }

    public boolean m_198220_() {
        return !this.f_188461_.isEmpty();
    }

    public boolean m_198214_(int p_198215_, int p_198216_) {
        return this.f_188461_.get((p_198216_ & 0xF) * 16 + (p_198215_ & 0xF));
    }

    public static BiomeResolver m_204531_(BiomeResolver p_204532_, ChunkAccess p_204533_) {
        if (!p_204533_.m_187679_()) {
            return p_204532_;
        }
        Predicate<ResourceKey> $$2 = f_196980_::contains;
        return (p_204538_, p_204539_, p_204540_, p_204541_) -> {
            Holder<Biome> $$7 = p_204532_.m_203407_(p_204538_, p_204539_, p_204540_, p_204541_);
            if ($$7.m_203425_($$2)) {
                return $$7;
            }
            return p_204533_.m_203495_(p_204538_, 0, p_204540_);
        };
    }
}

