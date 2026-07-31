/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core.dispenser;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class ShearsDispenseItemBehavior
extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack m_7498_(BlockSource p_123580_, ItemStack p_123581_) {
        ServerLevel $$2 = p_123580_.m_7727_();
        if (!$$2.m_5776_()) {
            BlockPos $$3 = p_123580_.m_7961_().m_121945_(p_123580_.m_6414_().m_61143_(DispenserBlock.f_52659_));
            this.m_123573_(ShearsDispenseItemBehavior.m_123576_($$2, $$3) || ShearsDispenseItemBehavior.m_123582_($$2, $$3));
            if (this.m_123570_() && p_123581_.m_220157_(1, $$2.m_213780_(), null)) {
                p_123581_.m_41764_(0);
            }
        }
        return p_123581_;
    }

    private static boolean m_123576_(ServerLevel p_123577_, BlockPos p_123578_) {
        int $$3;
        BlockState $$2 = p_123577_.m_8055_(p_123578_);
        if ($$2.m_204338_(BlockTags.f_13072_, p_202454_ -> p_202454_.m_61138_(BeehiveBlock.f_49564_) && p_202454_.m_60734_() instanceof BeehiveBlock) && ($$3 = $$2.m_61143_(BeehiveBlock.f_49564_).intValue()) >= 5) {
            p_123577_.m_5594_(null, p_123578_, SoundEvents.f_11697_, SoundSource.BLOCKS, 1.0f, 1.0f);
            BeehiveBlock.m_49600_(p_123577_, p_123578_);
            ((BeehiveBlock)$$2.m_60734_()).m_49594_(p_123577_, $$2, p_123578_, null, BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED);
            p_123577_.m_142346_(null, GameEvent.f_157781_, p_123578_);
            return true;
        }
        return false;
    }

    private static boolean m_123582_(ServerLevel p_123583_, BlockPos p_123584_) {
        List<Entity> $$2 = p_123583_.m_6443_(LivingEntity.class, new AABB(p_123584_), EntitySelector.f_20408_);
        for (LivingEntity livingEntity : $$2) {
            Shearable $$4;
            if (!(livingEntity instanceof Shearable) || !($$4 = (Shearable)((Object)livingEntity)).m_6220_()) continue;
            $$4.m_5851_(SoundSource.BLOCKS);
            p_123583_.m_142346_(null, GameEvent.f_157781_, p_123584_);
            return true;
        }
        return false;
    }
}

