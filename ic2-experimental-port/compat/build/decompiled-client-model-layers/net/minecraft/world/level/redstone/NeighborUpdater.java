/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.redstone;

import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface NeighborUpdater {
    public static final Direction[] f_230761_ = new Direction[]{Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH};

    public void m_213547_(Direction var1, BlockState var2, BlockPos var3, BlockPos var4, int var5, int var6);

    public void m_214026_(BlockPos var1, Block var2, BlockPos var3);

    public void m_213858_(BlockState var1, BlockPos var2, Block var3, BlockPos var4, boolean var5);

    default public void m_214152_(BlockPos p_230788_, Block p_230789_, @Nullable Direction p_230790_) {
        for (Direction $$3 : f_230761_) {
            if ($$3 == p_230790_) continue;
            this.m_214026_(p_230788_.m_121945_($$3), p_230789_, p_230788_);
        }
    }

    public static void m_230770_(LevelAccessor p_230771_, Direction p_230772_, BlockState p_230773_, BlockPos p_230774_, BlockPos p_230775_, int p_230776_, int p_230777_) {
        BlockState $$7 = p_230771_.m_8055_(p_230774_);
        BlockState $$8 = $$7.m_60728_(p_230772_, p_230773_, p_230771_, p_230774_, p_230775_);
        Block.m_49908_($$7, $$8, p_230771_, p_230774_, p_230776_, p_230777_);
    }

    public static void m_230763_(Level p_230764_, BlockState p_230765_, BlockPos p_230766_, Block p_230767_, BlockPos p_230768_, boolean p_230769_) {
        try {
            p_230765_.m_60690_(p_230764_, p_230766_, p_230767_, p_230768_, p_230769_);
        }
        catch (Throwable $$6) {
            CrashReport $$7 = CrashReport.m_127521_($$6, "Exception while updating neighbours");
            CrashReportCategory $$8 = $$7.m_127514_("Block being updated");
            $$8.m_128165_("Source block type", () -> {
                try {
                    return String.format(Locale.ROOT, "ID #%s (%s // %s)", Registry.f_122824_.m_7981_(p_230767_), p_230767_.m_7705_(), p_230767_.getClass().getCanonicalName());
                }
                catch (Throwable $$1) {
                    return "ID #" + Registry.f_122824_.m_7981_(p_230767_);
                }
            });
            CrashReportCategory.m_178950_($$8, p_230764_, p_230766_, p_230765_);
            throw new ReportedException($$7);
        }
    }
}

