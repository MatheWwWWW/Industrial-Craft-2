/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.allay.AllayAi;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GoAndGiveItemsToTarget<E extends LivingEntity>
extends Behavior<E> {
    private static final int f_217188_ = 3;
    private static final int f_217189_ = 60;
    private final Function<LivingEntity, Optional<PositionTracker>> f_217190_;
    private final float f_217191_;

    public GoAndGiveItemsToTarget(Function<LivingEntity, Optional<PositionTracker>> p_217193_, float p_217194_) {
        super(Map.of(MemoryModuleType.f_26371_, MemoryStatus.REGISTERED, MemoryModuleType.f_26370_, MemoryStatus.REGISTERED, MemoryModuleType.f_217781_, MemoryStatus.REGISTERED));
        this.f_217190_ = p_217193_;
        this.f_217191_ = p_217194_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217196_, E p_217197_) {
        return this.m_217202_(p_217197_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_217218_, E p_217219_, long p_217220_) {
        return this.m_217202_(p_217219_);
    }

    @Override
    protected void m_6735_(ServerLevel p_217199_, E p_217200_, long p_217201_) {
        this.f_217190_.apply((LivingEntity)p_217200_).ifPresent(p_217206_ -> BehaviorUtils.m_217128_(p_217200_, p_217206_, this.f_217191_, 3));
    }

    @Override
    protected void m_6725_(ServerLevel p_217226_, E p_217227_, long p_217228_) {
        ItemStack $$6;
        Optional<PositionTracker> $$3 = this.f_217190_.apply((LivingEntity)p_217227_);
        if ($$3.isEmpty()) {
            return;
        }
        PositionTracker $$4 = $$3.get();
        double $$5 = $$4.m_7024_().m_82554_(((Entity)p_217227_).m_146892_());
        if ($$5 < 3.0 && !($$6 = ((InventoryCarrier)p_217227_).m_35311_().m_7407_(0, 1)).m_41619_()) {
            GoAndGiveItemsToTarget.m_217207_(p_217227_, $$6, GoAndGiveItemsToTarget.m_217211_($$4));
            if (p_217227_ instanceof Allay) {
                Allay $$7 = (Allay)p_217227_;
                AllayAi.m_218410_($$7).ifPresent(p_217224_ -> this.m_217213_($$4, $$6, (ServerPlayer)p_217224_));
            }
            ((LivingEntity)p_217227_).m_6274_().m_21879_(MemoryModuleType.f_217781_, 60);
        }
    }

    private void m_217213_(PositionTracker p_217214_, ItemStack p_217215_, ServerPlayer p_217216_) {
        BlockPos $$3 = p_217214_.m_6675_().m_7495_();
        CriteriaTriggers.f_215657_.m_220040_(p_217216_, $$3, p_217215_);
    }

    private boolean m_217202_(E p_217203_) {
        if (((InventoryCarrier)p_217203_).m_35311_().m_7983_()) {
            return false;
        }
        Optional<PositionTracker> $$1 = this.f_217190_.apply((LivingEntity)p_217203_);
        return $$1.isPresent();
    }

    private static Vec3 m_217211_(PositionTracker p_217212_) {
        return p_217212_.m_7024_().m_82520_(0.0, 1.0, 0.0);
    }

    public static void m_217207_(LivingEntity p_217208_, ItemStack p_217209_, Vec3 p_217210_) {
        Vec3 $$3 = new Vec3(0.2f, 0.3f, 0.2f);
        BehaviorUtils.m_217133_(p_217208_, p_217209_, p_217210_, $$3, 0.2f);
        Level $$4 = p_217208_.f_19853_;
        if ($$4.m_46467_() % 7L == 0L && $$4.f_46441_.m_188500_() < 0.9) {
            float $$5 = Util.m_214621_(Allay.f_218306_, $$4.m_213780_()).floatValue();
            $$4.m_6269_(null, p_217208_, SoundEvents.f_215678_, SoundSource.NEUTRAL, 1.0f, $$5);
        }
    }
}

