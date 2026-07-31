/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SpawnUtil {
    public static <T extends Mob> Optional<T> m_216403_(EntityType<T> p_216404_, MobSpawnType p_216405_, ServerLevel p_216406_, BlockPos p_216407_, int p_216408_, int p_216409_, int p_216410_, Strategy p_216411_) {
        BlockPos.MutableBlockPos $$8 = p_216407_.m_122032_();
        for (int $$9 = 0; $$9 < p_216408_; ++$$9) {
            Mob $$12;
            int $$10 = Mth.m_216287_(p_216406_.f_46441_, -p_216409_, p_216409_);
            int $$11 = Mth.m_216287_(p_216406_.f_46441_, -p_216409_, p_216409_);
            $$8.m_122154_(p_216407_, $$10, p_216410_, $$11);
            if (!p_216406_.m_6857_().m_61937_($$8) || !SpawnUtil.m_216398_(p_216406_, p_216410_, $$8, p_216411_) || ($$12 = (Mob)p_216404_.m_20655_(p_216406_, null, null, null, $$8, p_216405_, false, false)) == null) continue;
            if ($$12.m_5545_(p_216406_, p_216405_) && $$12.m_6914_(p_216406_)) {
                p_216406_.m_47205_($$12);
                return Optional.of($$12);
            }
            $$12.m_146870_();
        }
        return Optional.empty();
    }

    private static boolean m_216398_(ServerLevel p_216399_, int p_216400_, BlockPos.MutableBlockPos p_216401_, Strategy p_216402_) {
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos().m_122190_(p_216401_);
        BlockState $$5 = p_216399_.m_8055_($$4);
        for (int $$6 = p_216400_; $$6 >= -p_216400_; --$$6) {
            p_216401_.m_122173_(Direction.DOWN);
            $$4.m_122159_(p_216401_, Direction.UP);
            BlockState $$7 = p_216399_.m_8055_(p_216401_);
            if (p_216402_.m_216427_(p_216399_, p_216401_, $$7, $$4, $$5)) {
                p_216401_.m_122173_(Direction.UP);
                return true;
            }
            $$5 = $$7;
        }
        return false;
    }

    public static interface Strategy {
        public static final Strategy f_216412_ = (p_216422_, p_216423_, p_216424_, p_216425_, p_216426_) -> (p_216426_.m_60795_() || p_216426_.m_60767_().m_76332_()) && p_216424_.m_60767_().m_76337_();
        public static final Strategy f_216413_ = (p_216416_, p_216417_, p_216418_, p_216419_, p_216420_) -> p_216420_.m_60812_(p_216416_, p_216419_).m_83281_() && Block.m_49918_(p_216418_.m_60812_(p_216416_, p_216417_), Direction.UP);

        public boolean m_216427_(ServerLevel var1, BlockPos var2, BlockState var3, BlockPos var4, BlockState var5);
    }
}

