/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class PatrolSpawner
implements CustomSpawner {
    private int f_64562_;

    @Override
    public int m_7995_(ServerLevel p_64570_, boolean p_64571_, boolean p_64572_) {
        if (!p_64571_) {
            return 0;
        }
        if (!p_64570_.m_46469_().m_46207_(GameRules.f_46124_)) {
            return 0;
        }
        RandomSource $$3 = p_64570_.f_46441_;
        --this.f_64562_;
        if (this.f_64562_ > 0) {
            return 0;
        }
        this.f_64562_ += 12000 + $$3.m_188503_(1200);
        long $$4 = p_64570_.m_46468_() / 24000L;
        if ($$4 < 5L || !p_64570_.m_46461_()) {
            return 0;
        }
        if ($$3.m_188503_(5) != 0) {
            return 0;
        }
        int $$5 = p_64570_.m_6907_().size();
        if ($$5 < 1) {
            return 0;
        }
        Player $$6 = p_64570_.m_6907_().get($$3.m_188503_($$5));
        if ($$6.m_5833_()) {
            return 0;
        }
        if (p_64570_.m_8736_($$6.m_20183_(), 2)) {
            return 0;
        }
        int $$7 = (24 + $$3.m_188503_(24)) * ($$3.m_188499_() ? -1 : 1);
        int $$8 = (24 + $$3.m_188503_(24)) * ($$3.m_188499_() ? -1 : 1);
        BlockPos.MutableBlockPos $$9 = $$6.m_20183_().m_122032_().m_122184_($$7, 0, $$8);
        int $$10 = 10;
        if (!p_64570_.m_151572_($$9.m_123341_() - 10, $$9.m_123343_() - 10, $$9.m_123341_() + 10, $$9.m_123343_() + 10)) {
            return 0;
        }
        Holder<Biome> $$11 = p_64570_.m_204166_($$9);
        if ($$11.m_203656_(BiomeTags.f_215806_)) {
            return 0;
        }
        int $$12 = 0;
        int $$13 = (int)Math.ceil(p_64570_.m_6436_($$9).m_19056_()) + 1;
        for (int $$14 = 0; $$14 < $$13; ++$$14) {
            ++$$12;
            $$9.m_142448_(p_64570_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$9).m_123342_());
            if ($$14 == 0) {
                if (!this.m_224532_(p_64570_, $$9, $$3, true)) {
                    break;
                }
            } else {
                this.m_224532_(p_64570_, $$9, $$3, false);
            }
            $$9.m_142451_($$9.m_123341_() + $$3.m_188503_(5) - $$3.m_188503_(5));
            $$9.m_142443_($$9.m_123343_() + $$3.m_188503_(5) - $$3.m_188503_(5));
        }
        return $$12;
    }

    private boolean m_224532_(ServerLevel p_224533_, BlockPos p_224534_, RandomSource p_224535_, boolean p_224536_) {
        BlockState $$4 = p_224533_.m_8055_(p_224534_);
        if (!NaturalSpawner.m_47056_(p_224533_, p_224534_, $$4, $$4.m_60819_(), EntityType.f_20513_)) {
            return false;
        }
        if (!PatrollingMonster.m_219025_(EntityType.f_20513_, p_224533_, MobSpawnType.PATROL, p_224534_, p_224535_)) {
            return false;
        }
        PatrollingMonster $$5 = EntityType.f_20513_.m_20615_(p_224533_);
        if ($$5 != null) {
            if (p_224536_) {
                $$5.m_33075_(true);
                $$5.m_33068_();
            }
            $$5.m_6034_(p_224534_.m_123341_(), p_224534_.m_123342_(), p_224534_.m_123343_());
            $$5.m_6518_(p_224533_, p_224533_.m_6436_(p_224534_), MobSpawnType.PATROL, null, null);
            p_224533_.m_47205_($$5);
            return true;
        }
        return false;
    }
}

