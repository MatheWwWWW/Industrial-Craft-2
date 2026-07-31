/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

public final class OreVeinifier {
    private static final float f_209650_ = 0.4f;
    private static final int f_209651_ = 20;
    private static final double f_209652_ = 0.2;
    private static final float f_209653_ = 0.7f;
    private static final float f_209654_ = 0.1f;
    private static final float f_209655_ = 0.3f;
    private static final float f_209656_ = 0.6f;
    private static final float f_209657_ = 0.02f;
    private static final float f_209658_ = -0.3f;

    private OreVeinifier() {
    }

    protected static NoiseChunk.BlockStateFiller m_209667_(DensityFunction p_209668_, DensityFunction p_209669_, DensityFunction p_209670_, PositionalRandomFactory p_209671_) {
        BlockState $$4 = null;
        return p_209666_ -> {
            double $$6 = p_209668_.m_207386_(p_209666_);
            int $$7 = p_209666_.m_207114_();
            VeinType $$8 = $$6 > 0.0 ? VeinType.COPPER : VeinType.IRON;
            double $$9 = Math.abs($$6);
            int $$10 = $$8.f_209675_ - $$7;
            int $$11 = $$7 - $$8.f_209674_;
            if ($$11 < 0 || $$10 < 0) {
                return $$4;
            }
            int $$12 = Math.min($$10, $$11);
            double $$13 = Mth.m_144851_($$12, 0.0, 20.0, -0.2, 0.0);
            if ($$9 + $$13 < (double)0.4f) {
                return $$4;
            }
            RandomSource $$14 = p_209671_.m_213715_(p_209666_.m_207115_(), $$7, p_209666_.m_207113_());
            if ($$14.m_188501_() > 0.7f) {
                return $$4;
            }
            if (p_209669_.m_207386_(p_209666_) >= 0.0) {
                return $$4;
            }
            double $$15 = Mth.m_144851_($$9, 0.4f, 0.6f, 0.1f, 0.3f);
            if ((double)$$14.m_188501_() < $$15 && p_209670_.m_207386_(p_209666_) > (double)-0.3f) {
                return $$14.m_188501_() < 0.02f ? $$8.f_209677_ : $$8.f_209676_;
            }
            return $$8.f_209678_;
        };
    }

    protected static final class VeinType
    extends Enum<VeinType> {
        public static final /* enum */ VeinType COPPER = new VeinType(Blocks.f_152505_.m_49966_(), Blocks.f_152599_.m_49966_(), Blocks.f_50122_.m_49966_(), 0, 50);
        public static final /* enum */ VeinType IRON = new VeinType(Blocks.f_152468_.m_49966_(), Blocks.f_152598_.m_49966_(), Blocks.f_152496_.m_49966_(), -60, -8);
        final BlockState f_209676_;
        final BlockState f_209677_;
        final BlockState f_209678_;
        protected final int f_209674_;
        protected final int f_209675_;
        private static final /* synthetic */ VeinType[] $VALUES;

        public static VeinType[] values() {
            return (VeinType[])$VALUES.clone();
        }

        public static VeinType valueOf(String p_209691_) {
            return Enum.valueOf(VeinType.class, p_209691_);
        }

        private VeinType(BlockState p_209684_, BlockState p_209685_, BlockState p_209686_, int p_209687_, int p_209688_) {
            this.f_209676_ = p_209684_;
            this.f_209677_ = p_209685_;
            this.f_209678_ = p_209686_;
            this.f_209674_ = p_209687_;
            this.f_209675_ = p_209688_;
        }

        private static /* synthetic */ VeinType[] m_209689_() {
            return new VeinType[]{COPPER, IRON};
        }

        static {
            $VALUES = VeinType.m_209689_();
        }
    }
}

