/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class HarvestFarmland
extends Behavior<Villager> {
    private static final int f_147559_ = 200;
    public static final float f_147558_ = 0.5f;
    @Nullable
    private BlockPos f_23159_;
    private long f_23160_;
    private int f_23161_;
    private final List<BlockPos> f_23162_ = Lists.newArrayList();

    public HarvestFarmland() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26363_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23174_, Villager p_23175_) {
        if (!p_23174_.m_46469_().m_46207_(GameRules.f_46132_)) {
            return false;
        }
        if (p_23175_.m_7141_().m_35571_() != VillagerProfession.f_35590_) {
            return false;
        }
        BlockPos.MutableBlockPos $$2 = p_23175_.m_20183_().m_122032_();
        this.f_23162_.clear();
        for (int $$3 = -1; $$3 <= 1; ++$$3) {
            for (int $$4 = -1; $$4 <= 1; ++$$4) {
                for (int $$5 = -1; $$5 <= 1; ++$$5) {
                    $$2.m_122169_(p_23175_.m_20185_() + (double)$$3, p_23175_.m_20186_() + (double)$$4, p_23175_.m_20189_() + (double)$$5);
                    if (!this.m_23180_($$2, p_23174_)) continue;
                    this.f_23162_.add(new BlockPos($$2));
                }
            }
        }
        this.f_23159_ = this.m_23164_(p_23174_);
        return this.f_23159_ != null;
    }

    @Nullable
    private BlockPos m_23164_(ServerLevel p_23165_) {
        return this.f_23162_.isEmpty() ? null : this.f_23162_.get(p_23165_.m_213780_().m_188503_(this.f_23162_.size()));
    }

    private boolean m_23180_(BlockPos p_23181_, ServerLevel p_23182_) {
        BlockState $$2 = p_23182_.m_8055_(p_23181_);
        Block $$3 = $$2.m_60734_();
        Block $$4 = p_23182_.m_8055_(p_23181_.m_7495_()).m_60734_();
        return $$3 instanceof CropBlock && ((CropBlock)$$3).m_52307_($$2) || $$2.m_60795_() && $$4 instanceof FarmBlock;
    }

    @Override
    protected void m_6735_(ServerLevel p_23177_, Villager p_23178_, long p_23179_) {
        if (p_23179_ > this.f_23160_ && this.f_23159_ != null) {
            p_23178_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new BlockPosTracker(this.f_23159_));
            p_23178_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(new BlockPosTracker(this.f_23159_), 0.5f, 1));
        }
    }

    @Override
    protected void m_6732_(ServerLevel p_23188_, Villager p_23189_, long p_23190_) {
        p_23189_.m_6274_().m_21936_(MemoryModuleType.f_26371_);
        p_23189_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
        this.f_23161_ = 0;
        this.f_23160_ = p_23190_ + 40L;
    }

    @Override
    protected void m_6725_(ServerLevel p_23196_, Villager p_23197_, long p_23198_) {
        if (this.f_23159_ != null && !this.f_23159_.m_203195_(p_23197_.m_20182_(), 1.0)) {
            return;
        }
        if (this.f_23159_ != null && p_23198_ > this.f_23160_) {
            BlockState $$3 = p_23196_.m_8055_(this.f_23159_);
            Block $$4 = $$3.m_60734_();
            Block $$5 = p_23196_.m_8055_(this.f_23159_.m_7495_()).m_60734_();
            if ($$4 instanceof CropBlock && ((CropBlock)$$4).m_52307_($$3)) {
                p_23196_.m_46953_(this.f_23159_, true, p_23197_);
            }
            if ($$3.m_60795_() && $$5 instanceof FarmBlock && p_23197_.m_35516_()) {
                SimpleContainer $$6 = p_23197_.m_35311_();
                for (int $$7 = 0; $$7 < $$6.m_6643_(); ++$$7) {
                    ItemStack $$8 = $$6.m_8020_($$7);
                    boolean $$9 = false;
                    if (!$$8.m_41619_()) {
                        if ($$8.m_150930_(Items.f_42404_)) {
                            BlockState $$10 = Blocks.f_50092_.m_49966_();
                            p_23196_.m_46597_(this.f_23159_, $$10);
                            p_23196_.m_220407_(GameEvent.f_157797_, this.f_23159_, GameEvent.Context.m_223719_(p_23197_, $$10));
                            $$9 = true;
                        } else if ($$8.m_150930_(Items.f_42620_)) {
                            BlockState $$11 = Blocks.f_50250_.m_49966_();
                            p_23196_.m_46597_(this.f_23159_, $$11);
                            p_23196_.m_220407_(GameEvent.f_157797_, this.f_23159_, GameEvent.Context.m_223719_(p_23197_, $$11));
                            $$9 = true;
                        } else if ($$8.m_150930_(Items.f_42619_)) {
                            BlockState $$12 = Blocks.f_50249_.m_49966_();
                            p_23196_.m_46597_(this.f_23159_, $$12);
                            p_23196_.m_220407_(GameEvent.f_157797_, this.f_23159_, GameEvent.Context.m_223719_(p_23197_, $$12));
                            $$9 = true;
                        } else if ($$8.m_150930_(Items.f_42733_)) {
                            BlockState $$13 = Blocks.f_50444_.m_49966_();
                            p_23196_.m_46597_(this.f_23159_, $$13);
                            p_23196_.m_220407_(GameEvent.f_157797_, this.f_23159_, GameEvent.Context.m_223719_(p_23197_, $$13));
                            $$9 = true;
                        }
                    }
                    if (!$$9) continue;
                    p_23196_.m_6263_(null, this.f_23159_.m_123341_(), this.f_23159_.m_123342_(), this.f_23159_.m_123343_(), SoundEvents.f_11839_, SoundSource.BLOCKS, 1.0f, 1.0f);
                    $$8.m_41774_(1);
                    if (!$$8.m_41619_()) break;
                    $$6.m_6836_($$7, ItemStack.f_41583_);
                    break;
                }
            }
            if ($$4 instanceof CropBlock && !((CropBlock)$$4).m_52307_($$3)) {
                this.f_23162_.remove(this.f_23159_);
                this.f_23159_ = this.m_23164_(p_23196_);
                if (this.f_23159_ != null) {
                    this.f_23160_ = p_23198_ + 20L;
                    p_23197_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(new BlockPosTracker(this.f_23159_), 0.5f, 1));
                    p_23197_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new BlockPosTracker(this.f_23159_));
                }
            }
        }
        ++this.f_23161_;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_23204_, Villager p_23205_, long p_23206_) {
        return this.f_23161_ < 200;
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6732_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6732_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Villager)livingEntity, l);
    }
}

