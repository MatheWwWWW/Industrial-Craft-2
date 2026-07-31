/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.biome;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Climate;

public abstract class BiomeSource
implements BiomeResolver {
    public static final Codec<BiomeSource> f_47888_ = Registry.f_122889_.m_194605_().dispatchStable(BiomeSource::m_5820_, Function.identity());
    private final Set<Holder<Biome>> f_47891_;

    protected BiomeSource(Stream<Holder<Biome>> p_47896_) {
        this(p_47896_.distinct().toList());
    }

    protected BiomeSource(List<Holder<Biome>> p_47894_) {
        this.f_47891_ = new ObjectLinkedOpenHashSet(p_47894_);
    }

    protected abstract Codec<? extends BiomeSource> m_5820_();

    public Set<Holder<Biome>> m_207840_() {
        return this.f_47891_;
    }

    public Set<Holder<Biome>> m_183399_(int p_186705_, int p_186706_, int p_186707_, int p_186708_, Climate.Sampler p_186709_) {
        int $$5 = QuartPos.m_175400_(p_186705_ - p_186708_);
        int $$6 = QuartPos.m_175400_(p_186706_ - p_186708_);
        int $$7 = QuartPos.m_175400_(p_186707_ - p_186708_);
        int $$8 = QuartPos.m_175400_(p_186705_ + p_186708_);
        int $$9 = QuartPos.m_175400_(p_186706_ + p_186708_);
        int $$10 = QuartPos.m_175400_(p_186707_ + p_186708_);
        int $$11 = $$8 - $$5 + 1;
        int $$12 = $$9 - $$6 + 1;
        int $$13 = $$10 - $$7 + 1;
        HashSet $$14 = Sets.newHashSet();
        for (int $$15 = 0; $$15 < $$13; ++$$15) {
            for (int $$16 = 0; $$16 < $$11; ++$$16) {
                for (int $$17 = 0; $$17 < $$12; ++$$17) {
                    int $$18 = $$5 + $$16;
                    int $$19 = $$6 + $$17;
                    int $$20 = $$7 + $$15;
                    $$14.add(this.m_203407_($$18, $$19, $$20, p_186709_));
                }
            }
        }
        return $$14;
    }

    @Nullable
    public Pair<BlockPos, Holder<Biome>> m_220570_(int p_220571_, int p_220572_, int p_220573_, int p_220574_, Predicate<Holder<Biome>> p_220575_, RandomSource p_220576_, Climate.Sampler p_220577_) {
        return this.m_213971_(p_220571_, p_220572_, p_220573_, p_220574_, 1, p_220575_, p_220576_, false, p_220577_);
    }

    @Nullable
    public Pair<BlockPos, Holder<Biome>> m_214004_(BlockPos p_220578_, int p_220579_, int p_220580_, int p_220581_, Predicate<Holder<Biome>> p_220582_, Climate.Sampler p_220583_, LevelReader p_220584_) {
        Set $$7 = this.m_207840_().stream().filter(p_220582_).collect(Collectors.toUnmodifiableSet());
        if ($$7.isEmpty()) {
            return null;
        }
        int $$8 = Math.floorDiv(p_220579_, p_220580_);
        int[] $$9 = Mth.m_216250_(p_220578_.m_123342_(), p_220584_.m_141937_() + 1, p_220584_.m_151558_(), p_220581_).toArray();
        for (BlockPos.MutableBlockPos $$10 : BlockPos.m_121935_(BlockPos.f_121853_, $$8, Direction.EAST, Direction.SOUTH)) {
            int $$11 = p_220578_.m_123341_() + $$10.m_123341_() * p_220580_;
            int $$12 = p_220578_.m_123343_() + $$10.m_123343_() * p_220580_;
            int $$13 = QuartPos.m_175400_($$11);
            int $$14 = QuartPos.m_175400_($$12);
            for (int $$15 : $$9) {
                int $$16 = QuartPos.m_175400_($$15);
                Holder<Biome> $$17 = this.m_203407_($$13, $$16, $$14, p_220583_);
                if (!$$7.contains($$17)) continue;
                return Pair.of((Object)new BlockPos($$11, $$15, $$12), $$17);
            }
        }
        return null;
    }

    @Nullable
    public Pair<BlockPos, Holder<Biome>> m_213971_(int p_220561_, int p_220562_, int p_220563_, int p_220564_, int p_220565_, Predicate<Holder<Biome>> p_220566_, RandomSource p_220567_, boolean p_220568_, Climate.Sampler p_220569_) {
        int $$15;
        int $$9 = QuartPos.m_175400_(p_220561_);
        int $$10 = QuartPos.m_175400_(p_220563_);
        int $$11 = QuartPos.m_175400_(p_220564_);
        int $$12 = QuartPos.m_175400_(p_220562_);
        Pair $$13 = null;
        int $$14 = 0;
        for (int $$16 = $$15 = p_220568_ ? 0 : $$11; $$16 <= $$11; $$16 += p_220565_) {
            int $$17;
            int n = $$17 = SharedConstants.f_183698_ ? 0 : -$$16;
            while ($$17 <= $$16) {
                boolean $$18 = Math.abs($$17) == $$16;
                for (int $$19 = -$$16; $$19 <= $$16; $$19 += p_220565_) {
                    int $$22;
                    int $$21;
                    Holder<Biome> $$23;
                    if (p_220568_) {
                        boolean $$20;
                        boolean bl = $$20 = Math.abs($$19) == $$16;
                        if (!$$20 && !$$18) continue;
                    }
                    if (!p_220566_.test($$23 = this.m_203407_($$21 = $$9 + $$19, $$12, $$22 = $$10 + $$17, p_220569_))) continue;
                    if ($$13 == null || p_220567_.m_188503_($$14 + 1) == 0) {
                        BlockPos $$24 = new BlockPos(QuartPos.m_175402_($$21), p_220562_, QuartPos.m_175402_($$22));
                        if (p_220568_) {
                            return Pair.of((Object)$$24, $$23);
                        }
                        $$13 = Pair.of((Object)$$24, $$23);
                    }
                    ++$$14;
                }
                $$17 += p_220565_;
            }
        }
        return $$13;
    }

    @Override
    public abstract Holder<Biome> m_203407_(int var1, int var2, int var3, Climate.Sampler var4);

    public void m_207301_(List<String> p_207837_, BlockPos p_207838_, Climate.Sampler p_207839_) {
    }
}

