/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class UseBonemeal
extends Behavior<Villager> {
    private static final int f_148035_ = 80;
    private long f_24461_;
    private long f_24462_;
    private int f_24463_;
    private Optional<BlockPos> f_24464_ = Optional.empty();

    public UseBonemeal() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24474_, Villager p_24475_) {
        if (p_24475_.f_19797_ % 10 != 0 || this.f_24462_ != 0L && this.f_24462_ + 160L > (long)p_24475_.f_19797_) {
            return false;
        }
        if (p_24475_.m_35311_().m_18947_(Items.f_42499_) <= 0) {
            return false;
        }
        this.f_24464_ = this.m_24492_(p_24474_, p_24475_);
        return this.f_24464_.isPresent();
    }

    @Override
    protected boolean m_6737_(ServerLevel p_24477_, Villager p_24478_, long p_24479_) {
        return this.f_24463_ < 80 && this.f_24464_.isPresent();
    }

    private Optional<BlockPos> m_24492_(ServerLevel p_24493_, Villager p_24494_) {
        BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
        Optional<BlockPos> $$3 = Optional.empty();
        int $$4 = 0;
        for (int $$5 = -1; $$5 <= 1; ++$$5) {
            for (int $$6 = -1; $$6 <= 1; ++$$6) {
                for (int $$7 = -1; $$7 <= 1; ++$$7) {
                    $$2.m_122154_(p_24494_.m_20183_(), $$5, $$6, $$7);
                    if (!this.m_24485_($$2, p_24493_) || p_24493_.f_46441_.m_188503_(++$$4) != 0) continue;
                    $$3 = Optional.of($$2.m_7949_());
                }
            }
        }
        return $$3;
    }

    private boolean m_24485_(BlockPos p_24486_, ServerLevel p_24487_) {
        BlockState $$2 = p_24487_.m_8055_(p_24486_);
        Block $$3 = $$2.m_60734_();
        return $$3 instanceof CropBlock && !((CropBlock)$$3).m_52307_($$2);
    }

    @Override
    protected void m_6735_(ServerLevel p_24496_, Villager p_24497_, long p_24498_) {
        this.m_24480_(p_24497_);
        p_24497_.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42499_));
        this.f_24461_ = p_24498_;
        this.f_24463_ = 0;
    }

    private void m_24480_(Villager p_24481_) {
        this.f_24464_.ifPresent(p_24484_ -> {
            BlockPosTracker $$2 = new BlockPosTracker((BlockPos)p_24484_);
            p_24481_.m_6274_().m_21879_(MemoryModuleType.f_26371_, $$2);
            p_24481_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget($$2, 0.5f, 1));
        });
    }

    @Override
    protected void m_6732_(ServerLevel p_24504_, Villager p_24505_, long p_24506_) {
        p_24505_.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
        this.f_24462_ = p_24505_.f_19797_;
    }

    @Override
    protected void m_6725_(ServerLevel p_24512_, Villager p_24513_, long p_24514_) {
        BlockPos $$3 = this.f_24464_.get();
        if (p_24514_ < this.f_24461_ || !$$3.m_203195_(p_24513_.m_20182_(), 1.0)) {
            return;
        }
        ItemStack $$4 = ItemStack.f_41583_;
        SimpleContainer $$5 = p_24513_.m_35311_();
        int $$6 = $$5.m_6643_();
        for (int $$7 = 0; $$7 < $$6; ++$$7) {
            ItemStack $$8 = $$5.m_8020_($$7);
            if (!$$8.m_150930_(Items.f_42499_)) continue;
            $$4 = $$8;
            break;
        }
        if (!$$4.m_41619_() && BoneMealItem.m_40627_($$4, p_24512_, $$3)) {
            p_24512_.m_46796_(1505, $$3, 0);
            this.f_24464_ = this.m_24492_(p_24512_, p_24513_);
            this.m_24480_(p_24513_);
            this.f_24461_ = p_24514_ + 40L;
        }
        ++this.f_24463_;
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Villager)livingEntity, l);
    }
}

