/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public interface DensityFunction {
    public static final Codec<DensityFunction> f_208216_ = DensityFunctions.f_208258_;
    public static final Codec<Holder<DensityFunction>> f_208217_ = RegistryFileCodec.m_135589_(Registry.f_211074_, f_208216_);
    public static final Codec<DensityFunction> f_208218_ = f_208217_.xmap(DensityFunctions.HolderHolder::new, p_208226_ -> {
        if (p_208226_ instanceof DensityFunctions.HolderHolder) {
            DensityFunctions.HolderHolder $$1 = (DensityFunctions.HolderHolder)p_208226_;
            return $$1.f_208636_();
        }
        return new Holder.Direct<DensityFunction>((DensityFunction)p_208226_);
    });

    public double m_207386_(FunctionContext var1);

    public void m_207362_(double[] var1, ContextProvider var2);

    public DensityFunction m_207456_(Visitor var1);

    public double m_207402_();

    public double m_207401_();

    public KeyDispatchDataCodec<? extends DensityFunction> m_214023_();

    default public DensityFunction m_208220_(double p_208221_, double p_208222_) {
        return new DensityFunctions.Clamp(this, p_208221_, p_208222_);
    }

    default public DensityFunction m_208229_() {
        return DensityFunctions.m_208312_(this, DensityFunctions.Mapped.Type.ABS);
    }

    default public DensityFunction m_208230_() {
        return DensityFunctions.m_208312_(this, DensityFunctions.Mapped.Type.SQUARE);
    }

    default public DensityFunction m_208231_() {
        return DensityFunctions.m_208312_(this, DensityFunctions.Mapped.Type.CUBE);
    }

    default public DensityFunction m_208232_() {
        return DensityFunctions.m_208312_(this, DensityFunctions.Mapped.Type.HALF_NEGATIVE);
    }

    default public DensityFunction m_208233_() {
        return DensityFunctions.m_208312_(this, DensityFunctions.Mapped.Type.QUARTER_NEGATIVE);
    }

    default public DensityFunction m_208234_() {
        return DensityFunctions.m_208312_(this, DensityFunctions.Mapped.Type.SQUEEZE);
    }

    public static final class SinglePointContext
    extends Record
    implements FunctionContext {
        private final int f_208243_;
        private final int f_208244_;
        private final int f_208245_;

        public SinglePointContext(int f_208243_, int f_208244_, int f_208245_) {
            this.f_208243_ = f_208243_;
            this.f_208244_ = f_208244_;
            this.f_208245_ = f_208245_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{SinglePointContext.class, "blockX;blockY;blockZ", "f_208243_", "f_208244_", "f_208245_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SinglePointContext.class, "blockX;blockY;blockZ", "f_208243_", "f_208244_", "f_208245_"}, this);
        }

        @Override
        public final boolean equals(Object p_208254_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SinglePointContext.class, "blockX;blockY;blockZ", "f_208243_", "f_208244_", "f_208245_"}, this, p_208254_);
        }

        @Override
        public int m_207115_() {
            return this.f_208243_;
        }

        @Override
        public int m_207114_() {
            return this.f_208244_;
        }

        @Override
        public int m_207113_() {
            return this.f_208245_;
        }
    }

    public static interface FunctionContext {
        public int m_207115_();

        public int m_207114_();

        public int m_207113_();

        default public Blender m_188743_() {
            return Blender.m_190153_();
        }
    }

    public static interface SimpleFunction
    extends DensityFunction {
        @Override
        default public void m_207362_(double[] p_208241_, ContextProvider p_208242_) {
            p_208242_.m_207207_(p_208241_, this);
        }

        @Override
        default public DensityFunction m_207456_(Visitor p_208239_) {
            return p_208239_.m_214017_(this);
        }
    }

    public static interface Visitor {
        public DensityFunction m_214017_(DensityFunction var1);

        default public NoiseHolder m_213918_(NoiseHolder p_224018_) {
            return p_224018_;
        }
    }

    public record NoiseHolder(Holder<NormalNoise.NoiseParameters> f_223997_, @Nullable NormalNoise f_223998_) {
        public static final Codec<NoiseHolder> f_223996_ = NormalNoise.NoiseParameters.f_192852_.xmap(p_224011_ -> new NoiseHolder((Holder<NormalNoise.NoiseParameters>)p_224011_, null), NoiseHolder::f_223997_);

        public NoiseHolder(Holder<NormalNoise.NoiseParameters> p_224001_) {
            this(p_224001_, null);
        }

        public double m_224006_(double p_224007_, double p_224008_, double p_224009_) {
            return this.f_223998_ == null ? 0.0 : this.f_223998_.m_75380_(p_224007_, p_224008_, p_224009_);
        }

        public double m_224005_() {
            return this.f_223998_ == null ? 2.0 : this.f_223998_.m_210630_();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{NoiseHolder.class, "noiseData;noise", "f_223997_", "f_223998_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{NoiseHolder.class, "noiseData;noise", "f_223997_", "f_223998_"}, this);
        }

        @Override
        public final boolean equals(Object p_224015_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{NoiseHolder.class, "noiseData;noise", "f_223997_", "f_223998_"}, this, p_224015_);
        }
    }

    public static interface ContextProvider {
        public FunctionContext m_207263_(int var1);

        public void m_207207_(double[] var1, DensityFunction var2);
    }
}

