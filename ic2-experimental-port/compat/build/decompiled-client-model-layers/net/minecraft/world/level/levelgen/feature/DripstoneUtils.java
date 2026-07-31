/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.feature;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

public class DripstoneUtils {
    protected static double m_159623_(double p_159624_, double p_159625_, double p_159626_, double p_159627_) {
        if (p_159624_ < p_159627_) {
            p_159624_ = p_159627_;
        }
        double $$4 = 0.384;
        double $$5 = p_159624_ / p_159625_ * 0.384;
        double $$6 = 0.75 * Math.pow($$5, 1.3333333333333333);
        double $$7 = Math.pow($$5, 0.6666666666666666);
        double $$8 = 0.3333333333333333 * Math.log($$5);
        double $$9 = p_159626_ * ($$6 - $$7 - $$8);
        $$9 = Math.max($$9, 0.0);
        return $$9 / 0.384 * p_159625_;
    }

    protected static boolean m_159639_(WorldGenLevel p_159640_, BlockPos p_159641_, int p_159642_) {
        if (DripstoneUtils.m_159659_(p_159640_, p_159641_)) {
            return false;
        }
        float $$3 = 6.0f;
        float $$4 = 6.0f / (float)p_159642_;
        for (float $$5 = 0.0f; $$5 < (float)Math.PI * 2; $$5 += $$4) {
            int $$7;
            int $$6 = (int)(Mth.m_14089_($$5) * (float)p_159642_);
            if (!DripstoneUtils.m_159659_(p_159640_, p_159641_.m_7918_($$6, 0, $$7 = (int)(Mth.m_14031_($$5) * (float)p_159642_)))) continue;
            return false;
        }
        return true;
    }

    protected static boolean m_159628_(LevelAccessor p_159629_, BlockPos p_159630_) {
        return p_159629_.m_7433_(p_159630_, DripstoneUtils::m_159664_);
    }

    protected static boolean m_159659_(LevelAccessor p_159660_, BlockPos p_159661_) {
        return p_159660_.m_7433_(p_159661_, DripstoneUtils::m_159666_);
    }

    protected static void m_159651_(Direction p_159652_, int p_159653_, boolean p_159654_, Consumer<BlockState> p_159655_) {
        if (p_159653_ >= 3) {
            p_159655_.accept(DripstoneUtils.m_159656_(p_159652_, DripstoneThickness.BASE));
            for (int $$4 = 0; $$4 < p_159653_ - 3; ++$$4) {
                p_159655_.accept(DripstoneUtils.m_159656_(p_159652_, DripstoneThickness.MIDDLE));
            }
        }
        if (p_159653_ >= 2) {
            p_159655_.accept(DripstoneUtils.m_159656_(p_159652_, DripstoneThickness.FRUSTUM));
        }
        if (p_159653_ >= 1) {
            p_159655_.accept(DripstoneUtils.m_159656_(p_159652_, p_159654_ ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }
    }

    protected static void m_190847_(LevelAccessor p_190848_, BlockPos p_190849_, Direction p_190850_, int p_190851_, boolean p_190852_) {
        if (!DripstoneUtils.m_159662_(p_190848_.m_8055_(p_190849_.m_121945_(p_190850_.m_122424_())))) {
            return;
        }
        BlockPos.MutableBlockPos $$5 = p_190849_.m_122032_();
        DripstoneUtils.m_159651_(p_190850_, p_190851_, p_190852_, p_190846_ -> {
            if (p_190846_.m_60713_(Blocks.f_152588_)) {
                p_190846_ = (BlockState)p_190846_.m_61124_(PointedDripstoneBlock.f_154011_, p_190848_.m_46801_($$5));
            }
            p_190848_.m_7731_($$5, (BlockState)p_190846_, 2);
            $$5.m_122173_(p_190850_);
        });
    }

    protected static boolean m_190853_(LevelAccessor p_190854_, BlockPos p_190855_) {
        BlockState $$2 = p_190854_.m_8055_(p_190855_);
        if ($$2.m_204336_(BlockTags.f_144273_)) {
            p_190854_.m_7731_(p_190855_, Blocks.f_152537_.m_49966_(), 2);
            return true;
        }
        return false;
    }

    private static BlockState m_159656_(Direction p_159657_, DripstoneThickness p_159658_) {
        return (BlockState)((BlockState)Blocks.f_152588_.m_49966_().m_61124_(PointedDripstoneBlock.f_154009_, p_159657_)).m_61124_(PointedDripstoneBlock.f_154010_, p_159658_);
    }

    public static boolean m_159649_(BlockState p_159650_) {
        return DripstoneUtils.m_159662_(p_159650_) || p_159650_.m_60713_(Blocks.f_49991_);
    }

    public static boolean m_159662_(BlockState p_159663_) {
        return p_159663_.m_60713_(Blocks.f_152537_) || p_159663_.m_204336_(BlockTags.f_144273_);
    }

    public static boolean m_159664_(BlockState p_159665_) {
        return p_159665_.m_60795_() || p_159665_.m_60713_(Blocks.f_49990_);
    }

    public static boolean m_203130_(BlockState p_203131_) {
        return !p_203131_.m_60795_() && !p_203131_.m_60713_(Blocks.f_49990_);
    }

    public static boolean m_159666_(BlockState p_159667_) {
        return p_159667_.m_60795_() || p_159667_.m_60713_(Blocks.f_49990_) || p_159667_.m_60713_(Blocks.f_49991_);
    }
}

