/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class PhantomSpawner
implements CustomSpawner {
    private int f_64573_;

    @Override
    public int m_7995_(ServerLevel p_64576_, boolean p_64577_, boolean p_64578_) {
        if (!p_64577_) {
            return 0;
        }
        if (!p_64576_.m_46469_().m_46207_(GameRules.f_46155_)) {
            return 0;
        }
        RandomSource $$3 = p_64576_.f_46441_;
        --this.f_64573_;
        if (this.f_64573_ > 0) {
            return 0;
        }
        this.f_64573_ += (60 + $$3.m_188503_(60)) * 20;
        if (p_64576_.m_7445_() < 5 && p_64576_.m_6042_().f_223549_()) {
            return 0;
        }
        int $$4 = 0;
        for (Player player : p_64576_.m_6907_()) {
            FluidState $$13;
            BlockState $$12;
            BlockPos $$11;
            DifficultyInstance $$7;
            if (player.m_5833_()) continue;
            BlockPos $$6 = player.m_20183_();
            if (p_64576_.m_6042_().f_223549_() && ($$6.m_123342_() < p_64576_.m_5736_() || !p_64576_.m_45527_($$6)) || !($$7 = p_64576_.m_6436_($$6)).m_19049_($$3.m_188501_() * 3.0f)) continue;
            ServerStatsCounter $$8 = ((ServerPlayer)player).m_8951_();
            int $$9 = Mth.m_14045_($$8.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12992_)), 1, Integer.MAX_VALUE);
            int $$10 = 24000;
            if ($$3.m_188503_($$9) < 72000 || !NaturalSpawner.m_47056_(p_64576_, $$11 = $$6.m_6630_(20 + $$3.m_188503_(15)).m_122030_(-10 + $$3.m_188503_(21)).m_122020_(-10 + $$3.m_188503_(21)), $$12 = p_64576_.m_8055_($$11), $$13 = p_64576_.m_6425_($$11), EntityType.f_20509_)) continue;
            SpawnGroupData $$14 = null;
            int $$15 = 1 + $$3.m_188503_($$7.m_19048_().m_19028_() + 1);
            for (int $$16 = 0; $$16 < $$15; ++$$16) {
                Phantom $$17 = EntityType.f_20509_.m_20615_(p_64576_);
                $$17.m_20035_($$11, 0.0f, 0.0f);
                $$14 = $$17.m_6518_(p_64576_, $$7, MobSpawnType.NATURAL, $$14, null);
                p_64576_.m_47205_($$17);
            }
            $$4 += $$15;
        }
        return $$4;
    }
}

