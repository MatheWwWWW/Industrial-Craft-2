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
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.pathfinder.Path;

public class VillagerMakeLove
extends Behavior<Villager> {
    private static final int f_148042_ = 5;
    private static final float f_148043_ = 0.5f;
    private long f_24613_;

    public VillagerMakeLove() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26375_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_148205_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), 350, 350);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24623_, Villager p_24624_) {
        return this.m_24639_(p_24624_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_24626_, Villager p_24627_, long p_24628_) {
        return p_24628_ <= this.f_24613_ && this.m_24639_(p_24627_);
    }

    @Override
    protected void m_6735_(ServerLevel p_24652_, Villager p_24653_, long p_24654_) {
        AgeableMob $$3 = p_24653_.m_6274_().m_21952_(MemoryModuleType.f_26375_).get();
        BehaviorUtils.m_22602_(p_24653_, $$3, 0.5f);
        p_24652_.m_7605_($$3, (byte)18);
        p_24652_.m_7605_(p_24653_, (byte)18);
        int $$4 = 275 + p_24653_.m_217043_().m_188503_(50);
        this.f_24613_ = p_24654_ + (long)$$4;
    }

    @Override
    protected void m_6725_(ServerLevel p_24667_, Villager p_24668_, long p_24669_) {
        Villager $$3 = (Villager)p_24668_.m_6274_().m_21952_(MemoryModuleType.f_26375_).get();
        if (p_24668_.m_20280_($$3) > 5.0) {
            return;
        }
        BehaviorUtils.m_22602_(p_24668_, $$3, 0.5f);
        if (p_24669_ >= this.f_24613_) {
            p_24668_.m_35513_();
            $$3.m_35513_();
            this.m_24629_(p_24667_, p_24668_, $$3);
        } else if (p_24668_.m_217043_().m_188503_(35) == 0) {
            p_24667_.m_7605_($$3, (byte)12);
            p_24667_.m_7605_(p_24668_, (byte)12);
        }
    }

    private void m_24629_(ServerLevel p_24630_, Villager p_24631_, Villager p_24632_) {
        Optional<BlockPos> $$3 = this.m_24648_(p_24630_, p_24631_);
        if (!$$3.isPresent()) {
            p_24630_.m_7605_(p_24632_, (byte)13);
            p_24630_.m_7605_(p_24631_, (byte)13);
        } else {
            Optional<Villager> $$4 = this.m_24655_(p_24630_, p_24631_, p_24632_);
            if ($$4.isPresent()) {
                this.m_24633_(p_24630_, $$4.get(), $$3.get());
            } else {
                p_24630_.m_8904_().m_27154_($$3.get());
                DebugPackets.m_133719_(p_24630_, $$3.get());
            }
        }
    }

    @Override
    protected void m_6732_(ServerLevel p_24675_, Villager p_24676_, long p_24677_) {
        p_24676_.m_6274_().m_21936_(MemoryModuleType.f_26375_);
    }

    private boolean m_24639_(Villager p_24640_) {
        Brain<Villager> $$1 = p_24640_.m_6274_();
        Optional<AgeableMob> $$2 = $$1.m_21952_(MemoryModuleType.f_26375_).filter(p_148045_ -> p_148045_.m_6095_() == EntityType.f_20492_);
        if (!$$2.isPresent()) {
            return false;
        }
        return BehaviorUtils.m_22639_($$1, MemoryModuleType.f_26375_, EntityType.f_20492_) && p_24640_.m_35506_() && $$2.get().m_35506_();
    }

    private Optional<BlockPos> m_24648_(ServerLevel p_24649_, Villager p_24650_) {
        return p_24649_.m_8904_().m_217946_(p_217509_ -> p_217509_.m_203565_(PoiTypes.f_218060_), (p_217506_, p_217507_) -> this.m_217500_(p_24650_, (BlockPos)p_217507_, (Holder<PoiType>)p_217506_), p_24650_.m_20183_(), 48);
    }

    private boolean m_217500_(Villager p_217501_, BlockPos p_217502_, Holder<PoiType> p_217503_) {
        Path $$3 = p_217501_.m_21573_().m_7864_(p_217502_, p_217503_.m_203334_().f_27328_());
        return $$3 != null && $$3.m_77403_();
    }

    private Optional<Villager> m_24655_(ServerLevel p_24656_, Villager p_24657_, Villager p_24658_) {
        Villager $$3 = p_24657_.m_142606_(p_24656_, p_24658_);
        if ($$3 == null) {
            return Optional.empty();
        }
        p_24657_.m_146762_(6000);
        p_24658_.m_146762_(6000);
        $$3.m_146762_(-24000);
        $$3.m_7678_(p_24657_.m_20185_(), p_24657_.m_20186_(), p_24657_.m_20189_(), 0.0f, 0.0f);
        p_24656_.m_47205_($$3);
        p_24656_.m_7605_($$3, (byte)12);
        return Optional.of($$3);
    }

    private void m_24633_(ServerLevel p_24634_, Villager p_24635_, BlockPos p_24636_) {
        GlobalPos $$3 = GlobalPos.m_122643_(p_24634_.m_46472_(), p_24636_);
        p_24635_.m_6274_().m_21879_(MemoryModuleType.f_26359_, $$3);
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

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Villager)livingEntity, l);
    }
}

