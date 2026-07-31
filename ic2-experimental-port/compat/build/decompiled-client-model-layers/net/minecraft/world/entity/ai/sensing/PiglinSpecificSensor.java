/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PiglinSpecificSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_148205_, MemoryModuleType.f_148204_, MemoryModuleType.f_26333_, MemoryModuleType.f_26345_, MemoryModuleType.f_26354_, MemoryModuleType.f_26343_, (Object[])new MemoryModuleType[]{MemoryModuleType.f_26344_, MemoryModuleType.f_26347_, MemoryModuleType.f_26346_, MemoryModuleType.f_26352_, MemoryModuleType.f_26353_, MemoryModuleType.f_26356_});
    }

    @Override
    protected void m_5578_(ServerLevel p_26726_, LivingEntity p_26727_) {
        Brain<?> $$2 = p_26727_.m_6274_();
        $$2.m_21886_(MemoryModuleType.f_26356_, PiglinSpecificSensor.m_26734_(p_26726_, p_26727_));
        Optional<Object> $$3 = Optional.empty();
        Optional<Object> $$4 = Optional.empty();
        Optional<Object> $$5 = Optional.empty();
        Optional<Object> $$6 = Optional.empty();
        Optional<Object> $$7 = Optional.empty();
        Optional<Object> $$8 = Optional.empty();
        Optional<Object> $$9 = Optional.empty();
        int $$10 = 0;
        ArrayList $$11 = Lists.newArrayList();
        ArrayList $$12 = Lists.newArrayList();
        NearestVisibleLivingEntities $$13 = $$2.m_21952_(MemoryModuleType.f_148205_).orElse(NearestVisibleLivingEntities.m_186106_());
        for (LivingEntity $$14 : $$13.m_186123_(p_186157_ -> true)) {
            if ($$14 instanceof Hoglin) {
                Hoglin $$15 = (Hoglin)$$14;
                if ($$15.m_6162_() && $$5.isEmpty()) {
                    $$5 = Optional.of($$15);
                    continue;
                }
                if (!$$15.m_34552_()) continue;
                ++$$10;
                if (!$$4.isEmpty() || !$$15.m_34555_()) continue;
                $$4 = Optional.of($$15);
                continue;
            }
            if ($$14 instanceof PiglinBrute) {
                PiglinBrute $$16 = (PiglinBrute)$$14;
                $$11.add($$16);
                continue;
            }
            if ($$14 instanceof Piglin) {
                Piglin $$17 = (Piglin)$$14;
                if ($$17.m_6162_() && $$6.isEmpty()) {
                    $$6 = Optional.of($$17);
                    continue;
                }
                if (!$$17.m_34667_()) continue;
                $$11.add($$17);
                continue;
            }
            if ($$14 instanceof Player) {
                Player $$18 = (Player)$$14;
                if ($$8.isEmpty() && !PiglinAi.m_34808_($$18) && p_26727_.m_6779_($$14)) {
                    $$8 = Optional.of($$18);
                }
                if (!$$9.isEmpty() || $$18.m_5833_() || !PiglinAi.m_34883_($$18)) continue;
                $$9 = Optional.of($$18);
                continue;
            }
            if ($$3.isEmpty() && ($$14 instanceof WitherSkeleton || $$14 instanceof WitherBoss)) {
                $$3 = Optional.of((Mob)$$14);
                continue;
            }
            if (!$$7.isEmpty() || !PiglinAi.m_34806_($$14.m_6095_())) continue;
            $$7 = Optional.of($$14);
        }
        List<LivingEntity> $$19 = $$2.m_21952_(MemoryModuleType.f_148204_).orElse((List<LivingEntity>)ImmutableList.of());
        for (LivingEntity $$20 : $$19) {
            AbstractPiglin $$21;
            if (!($$20 instanceof AbstractPiglin) || !($$21 = (AbstractPiglin)$$20).m_34667_()) continue;
            $$12.add($$21);
        }
        $$2.m_21886_(MemoryModuleType.f_26333_, $$3);
        $$2.m_21886_(MemoryModuleType.f_26343_, $$4);
        $$2.m_21886_(MemoryModuleType.f_26344_, $$5);
        $$2.m_21886_(MemoryModuleType.f_26351_, $$7);
        $$2.m_21886_(MemoryModuleType.f_26345_, $$8);
        $$2.m_21886_(MemoryModuleType.f_26354_, $$9);
        $$2.m_21879_(MemoryModuleType.f_26346_, $$12);
        $$2.m_21879_(MemoryModuleType.f_26347_, $$11);
        $$2.m_21879_(MemoryModuleType.f_26352_, $$11.size());
        $$2.m_21879_(MemoryModuleType.f_26353_, $$10);
    }

    private static Optional<BlockPos> m_26734_(ServerLevel p_26735_, LivingEntity p_26736_) {
        return BlockPos.m_121930_(p_26736_.m_20183_(), 8, 4, p_186160_ -> PiglinSpecificSensor.m_26728_(p_26735_, p_186160_));
    }

    private static boolean m_26728_(ServerLevel p_26729_, BlockPos p_26730_) {
        BlockState $$2 = p_26729_.m_8055_(p_26730_);
        boolean $$3 = $$2.m_204336_(BlockTags.f_13042_);
        if ($$3 && $$2.m_60713_(Blocks.f_50684_)) {
            return CampfireBlock.m_51319_($$2);
        }
        return $$3;
    }
}

