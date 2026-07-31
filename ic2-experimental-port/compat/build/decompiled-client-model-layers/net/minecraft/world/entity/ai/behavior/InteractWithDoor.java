/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public class InteractWithDoor
extends Behavior<LivingEntity> {
    private static final int f_147585_ = 20;
    private static final double f_147586_ = 2.0;
    private static final double f_147587_ = 2.0;
    @Nullable
    private Node f_23288_;
    private int f_23289_;

    public InteractWithDoor() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26377_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26379_, (Object)((Object)MemoryStatus.REGISTERED)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23292_, LivingEntity p_23293_) {
        Path $$2 = p_23293_.m_6274_().m_21952_(MemoryModuleType.f_26377_).get();
        if ($$2.m_77387_() || $$2.m_77392_()) {
            return false;
        }
        if (!Objects.equals(this.f_23288_, $$2.m_77401_())) {
            this.f_23289_ = 20;
            return true;
        }
        if (this.f_23289_ > 0) {
            --this.f_23289_;
        }
        return this.f_23289_ == 0;
    }

    @Override
    protected void m_6735_(ServerLevel p_23295_, LivingEntity p_23296_, long p_23297_) {
        DoorBlock $$11;
        BlockPos $$9;
        BlockState $$10;
        Path $$3 = p_23296_.m_6274_().m_21952_(MemoryModuleType.f_26377_).get();
        this.f_23288_ = $$3.m_77401_();
        Node $$4 = $$3.m_77402_();
        Node $$5 = $$3.m_77401_();
        BlockPos $$6 = $$4.m_77288_();
        BlockState $$7 = p_23295_.m_8055_($$6);
        if ($$7.m_204338_(BlockTags.f_13095_, p_201959_ -> p_201959_.m_60734_() instanceof DoorBlock)) {
            DoorBlock $$8 = (DoorBlock)$$7.m_60734_();
            if (!$$8.m_52815_($$7)) {
                $$8.m_153165_(p_23296_, p_23295_, $$7, $$6, true);
            }
            this.m_23325_(p_23295_, p_23296_, $$6);
        }
        if (($$10 = p_23295_.m_8055_($$9 = $$5.m_77288_())).m_204338_(BlockTags.f_13095_, p_201957_ -> p_201957_.m_60734_() instanceof DoorBlock) && !($$11 = (DoorBlock)$$10.m_60734_()).m_52815_($$10)) {
            $$11.m_153165_(p_23296_, p_23295_, $$10, $$9, true);
            this.m_23325_(p_23295_, p_23296_, $$9);
        }
        InteractWithDoor.m_23298_(p_23295_, p_23296_, $$4, $$5);
    }

    public static void m_23298_(ServerLevel p_23299_, LivingEntity p_23300_, @Nullable Node p_23301_, @Nullable Node p_23302_) {
        Brain<Set<GlobalPos>> $$4 = p_23300_.m_6274_();
        if ($$4.m_21874_(MemoryModuleType.f_26379_)) {
            Iterator<GlobalPos> $$5 = $$4.m_21952_(MemoryModuleType.f_26379_).get().iterator();
            while ($$5.hasNext()) {
                GlobalPos $$6 = $$5.next();
                BlockPos $$7 = $$6.m_122646_();
                if (p_23301_ != null && p_23301_.m_77288_().equals($$7) || p_23302_ != null && p_23302_.m_77288_().equals($$7)) continue;
                if (InteractWithDoor.m_23307_(p_23299_, p_23300_, $$6)) {
                    $$5.remove();
                    continue;
                }
                BlockState $$8 = p_23299_.m_8055_($$7);
                if (!$$8.m_204338_(BlockTags.f_13095_, p_201952_ -> p_201952_.m_60734_() instanceof DoorBlock)) {
                    $$5.remove();
                    continue;
                }
                DoorBlock $$9 = (DoorBlock)$$8.m_60734_();
                if (!$$9.m_52815_($$8)) {
                    $$5.remove();
                    continue;
                }
                if (InteractWithDoor.m_23303_(p_23299_, p_23300_, $$7)) {
                    $$5.remove();
                    continue;
                }
                $$9.m_153165_(p_23300_, p_23299_, $$8, $$7, false);
                $$5.remove();
            }
        }
    }

    private static boolean m_23303_(ServerLevel p_23304_, LivingEntity p_23305_, BlockPos p_23306_) {
        Brain<List<LivingEntity>> $$3 = p_23305_.m_6274_();
        if (!$$3.m_21874_(MemoryModuleType.f_148204_)) {
            return false;
        }
        return $$3.m_21952_(MemoryModuleType.f_148204_).get().stream().filter(p_201950_ -> p_201950_.m_6095_() == p_23305_.m_6095_()).filter(p_201955_ -> p_23306_.m_203195_(p_201955_.m_20182_(), 2.0)).anyMatch(p_201947_ -> InteractWithDoor.m_23321_(p_23304_, p_201947_, p_23306_));
    }

    private static boolean m_23321_(ServerLevel p_23322_, LivingEntity p_23323_, BlockPos p_23324_) {
        if (!p_23323_.m_6274_().m_21874_(MemoryModuleType.f_26377_)) {
            return false;
        }
        Path $$3 = p_23323_.m_6274_().m_21952_(MemoryModuleType.f_26377_).get();
        if ($$3.m_77392_()) {
            return false;
        }
        Node $$4 = $$3.m_77402_();
        if ($$4 == null) {
            return false;
        }
        Node $$5 = $$3.m_77401_();
        return p_23324_.equals($$4.m_77288_()) || p_23324_.equals($$5.m_77288_());
    }

    private static boolean m_23307_(ServerLevel p_23308_, LivingEntity p_23309_, GlobalPos p_23310_) {
        return p_23310_.m_122640_() != p_23308_.m_46472_() || !p_23310_.m_122646_().m_203195_(p_23309_.m_20182_(), 2.0);
    }

    private void m_23325_(ServerLevel p_23326_, LivingEntity p_23327_, BlockPos p_23328_) {
        Brain<?> $$3 = p_23327_.m_6274_();
        GlobalPos $$4 = GlobalPos.m_122643_(p_23326_.m_46472_(), p_23328_);
        if ($$3.m_21952_(MemoryModuleType.f_26379_).isPresent()) {
            $$3.m_21952_(MemoryModuleType.f_26379_).get().add($$4);
        } else {
            $$3.m_21879_(MemoryModuleType.f_26379_, Sets.newHashSet((Object[])new GlobalPos[]{$$4}));
        }
    }
}

