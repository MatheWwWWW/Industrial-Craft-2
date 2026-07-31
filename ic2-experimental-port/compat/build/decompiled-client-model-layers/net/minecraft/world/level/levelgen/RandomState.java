/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class RandomState {
    final PositionalRandomFactory f_224545_;
    private final long f_224546_;
    private final Registry<NormalNoise.NoiseParameters> f_224547_;
    private final NoiseRouter f_224548_;
    private final Climate.Sampler f_224549_;
    private final SurfaceSystem f_224550_;
    private final PositionalRandomFactory f_224551_;
    private final PositionalRandomFactory f_224552_;
    private final Map<ResourceKey<NormalNoise.NoiseParameters>, NormalNoise> f_224553_;
    private final Map<ResourceLocation, PositionalRandomFactory> f_224554_;

    public static RandomState m_224574_(RegistryAccess p_224575_, ResourceKey<NoiseGeneratorSettings> p_224576_, long p_224577_) {
        return RandomState.m_224570_(p_224575_.m_175515_(Registry.f_122878_).m_123013_(p_224576_), p_224575_.m_175515_(Registry.f_194568_), p_224577_);
    }

    public static RandomState m_224570_(NoiseGeneratorSettings p_224571_, Registry<NormalNoise.NoiseParameters> p_224572_, long p_224573_) {
        return new RandomState(p_224571_, p_224572_, p_224573_);
    }

    private RandomState(NoiseGeneratorSettings p_224556_, Registry<NormalNoise.NoiseParameters> p_224557_, final long p_224558_) {
        this.f_224545_ = p_224556_.m_188893_().m_224687_(p_224558_).m_188582_();
        this.f_224546_ = p_224558_;
        this.f_224547_ = p_224557_;
        this.f_224551_ = this.f_224545_.m_224540_(new ResourceLocation("aquifer")).m_188582_();
        this.f_224552_ = this.f_224545_.m_224540_(new ResourceLocation("ore")).m_188582_();
        this.f_224553_ = new ConcurrentHashMap<ResourceKey<NormalNoise.NoiseParameters>, NormalNoise>();
        this.f_224554_ = new ConcurrentHashMap<ResourceLocation, PositionalRandomFactory>();
        this.f_224550_ = new SurfaceSystem(this, p_224556_.f_64440_(), p_224556_.f_64444_(), this.f_224545_);
        final boolean $$3 = p_224556_.f_209354_();
        class NoiseWiringHelper
        implements DensityFunction.Visitor {
            private final Map<DensityFunction, DensityFunction> f_224586_ = new HashMap<DensityFunction, DensityFunction>();

            NoiseWiringHelper() {
            }

            private RandomSource m_224591_(long p_224592_) {
                return new LegacyRandomSource(p_224558_ + p_224592_);
            }

            @Override
            public DensityFunction.NoiseHolder m_213918_(DensityFunction.NoiseHolder p_224594_) {
                Holder<NormalNoise.NoiseParameters> $$1 = p_224594_.f_223997_();
                if ($$3) {
                    if (Objects.equals($$1.m_203543_(), Optional.of(Noises.f_189269_))) {
                        NormalNoise $$2 = NormalNoise.m_230508_(this.m_224591_(0L), new NormalNoise.NoiseParameters(-7, 1.0, 1.0));
                        return new DensityFunction.NoiseHolder($$1, $$2);
                    }
                    if (Objects.equals($$1.m_203543_(), Optional.of(Noises.f_189278_))) {
                        NormalNoise $$32 = NormalNoise.m_230508_(this.m_224591_(1L), new NormalNoise.NoiseParameters(-7, 1.0, 1.0));
                        return new DensityFunction.NoiseHolder($$1, $$32);
                    }
                    if (Objects.equals($$1.m_203543_(), Optional.of(Noises.f_189286_))) {
                        NormalNoise $$4 = NormalNoise.m_230511_(RandomState.this.f_224545_.m_224540_(Noises.f_189286_.m_135782_()), new NormalNoise.NoiseParameters(0, 0.0, new double[0]));
                        return new DensityFunction.NoiseHolder($$1, $$4);
                    }
                }
                NormalNoise $$5 = RandomState.this.m_224560_($$1.m_203543_().orElseThrow());
                return new DensityFunction.NoiseHolder($$1, $$5);
            }

            private DensityFunction m_224595_(DensityFunction p_224596_) {
                if (p_224596_ instanceof BlendedNoise) {
                    BlendedNoise $$1 = (BlendedNoise)p_224596_;
                    RandomSource $$2 = $$3 ? this.m_224591_(0L) : RandomState.this.f_224545_.m_224540_(new ResourceLocation("terrain"));
                    return $$1.m_230483_($$2);
                }
                if (p_224596_ instanceof DensityFunctions.EndIslandDensityFunction) {
                    return new DensityFunctions.EndIslandDensityFunction(p_224558_);
                }
                return p_224596_;
            }

            @Override
            public DensityFunction m_214017_(DensityFunction p_224598_) {
                return this.f_224586_.computeIfAbsent(p_224598_, this::m_224595_);
            }
        }
        this.f_224548_ = p_224556_.f_209353_().m_224412_(new NoiseWiringHelper());
        this.f_224549_ = new Climate.Sampler(this.f_224548_.f_209384_(), this.f_224548_.f_224392_(), this.f_224548_.f_209386_(), this.f_224548_.f_209387_(), this.f_224548_.f_209388_(), this.f_224548_.f_209389_(), p_224556_.f_224370_());
    }

    public NormalNoise m_224560_(ResourceKey<NormalNoise.NoiseParameters> p_224561_) {
        return this.f_224553_.computeIfAbsent(p_224561_, p_224564_ -> Noises.m_189305_(this.f_224547_, this.f_224545_, p_224561_));
    }

    public PositionalRandomFactory m_224565_(ResourceLocation p_224566_) {
        return this.f_224554_.computeIfAbsent(p_224566_, p_224569_ -> this.f_224545_.m_224540_(p_224566_).m_188582_());
    }

    public long m_224559_() {
        return this.f_224546_;
    }

    public NoiseRouter m_224578_() {
        return this.f_224548_;
    }

    public Climate.Sampler m_224579_() {
        return this.f_224549_;
    }

    public SurfaceSystem m_224580_() {
        return this.f_224550_;
    }

    public PositionalRandomFactory m_224581_() {
        return this.f_224551_;
    }

    public PositionalRandomFactory m_224582_() {
        return this.f_224552_;
    }
}

