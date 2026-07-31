/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.phys.Vec3;

public class RamTarget
extends Behavior<Goat> {
    public static final int f_147800_ = 200;
    public static final float f_147801_ = 1.65f;
    private final Function<Goat, UniformInt> f_147802_;
    private final TargetingConditions f_147803_;
    private final float f_147805_;
    private final ToDoubleFunction<Goat> f_147806_;
    private Vec3 f_147807_;
    private final Function<Goat, SoundEvent> f_147808_;
    private final Function<Goat, SoundEvent> f_217340_;

    public RamTarget(Function<Goat, UniformInt> p_217342_, TargetingConditions p_217343_, float p_217344_, ToDoubleFunction<Goat> p_217345_, Function<Goat, SoundEvent> p_217346_, Function<Goat, SoundEvent> p_217347_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_148202_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_148203_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), 200);
        this.f_147802_ = p_217342_;
        this.f_147803_ = p_217343_;
        this.f_147805_ = p_217344_;
        this.f_147806_ = p_217345_;
        this.f_147808_ = p_217346_;
        this.f_217340_ = p_217347_;
        this.f_147807_ = Vec3.f_82478_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217349_, Goat p_217350_) {
        return p_217350_.m_6274_().m_21874_(MemoryModuleType.f_148203_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_217352_, Goat p_217353_, long p_217354_) {
        return p_217353_.m_6274_().m_21874_(MemoryModuleType.f_148203_);
    }

    @Override
    protected void m_6735_(ServerLevel p_217359_, Goat p_217360_, long p_217361_) {
        BlockPos $$3 = p_217360_.m_20183_();
        Brain<Goat> $$4 = p_217360_.m_6274_();
        Vec3 $$5 = $$4.m_21952_(MemoryModuleType.f_148203_).get();
        this.f_147807_ = new Vec3((double)$$3.m_123341_() - $$5.m_7096_(), 0.0, (double)$$3.m_123343_() - $$5.m_7094_()).m_82541_();
        $$4.m_21879_(MemoryModuleType.f_26370_, new WalkTarget($$5, this.f_147805_, 0));
    }

    @Override
    protected void m_6725_(ServerLevel p_217366_, Goat p_217367_, long p_217368_) {
        List<LivingEntity> $$3 = p_217366_.m_45971_(LivingEntity.class, this.f_147803_, p_217367_, p_217367_.m_20191_());
        Brain<Goat> $$4 = p_217367_.m_6274_();
        if (!$$3.isEmpty()) {
            LivingEntity $$5 = $$3.get(0);
            $$5.m_6469_(DamageSource.m_19370_(p_217367_).m_181120_(), (float)p_217367_.m_21133_(Attributes.f_22281_));
            int $$6 = p_217367_.m_21023_(MobEffects.f_19596_) ? p_217367_.m_21124_(MobEffects.f_19596_).m_19564_() + 1 : 0;
            int $$7 = p_217367_.m_21023_(MobEffects.f_19597_) ? p_217367_.m_21124_(MobEffects.f_19597_).m_19564_() + 1 : 0;
            float $$8 = 0.25f * (float)($$6 - $$7);
            float $$9 = Mth.m_14036_(p_217367_.m_6113_() * 1.65f, 0.2f, 3.0f) + $$8;
            float $$10 = $$5.m_21275_(DamageSource.m_19370_(p_217367_)) ? 0.5f : 1.0f;
            $$5.m_147240_((double)($$10 * $$9) * this.f_147806_.applyAsDouble(p_217367_), this.f_147807_.m_7096_(), this.f_147807_.m_7094_());
            this.m_217355_(p_217366_, p_217367_);
            p_217366_.m_6269_(null, p_217367_, this.f_147808_.apply(p_217367_), SoundSource.HOSTILE, 1.0f, 1.0f);
        } else if (this.m_217362_(p_217366_, p_217367_)) {
            p_217366_.m_6269_(null, p_217367_, this.f_147808_.apply(p_217367_), SoundSource.HOSTILE, 1.0f, 1.0f);
            boolean $$11 = p_217367_.m_218760_();
            if ($$11) {
                p_217366_.m_6269_(null, p_217367_, this.f_217340_.apply(p_217367_), SoundSource.HOSTILE, 1.0f, 1.0f);
            }
            this.m_217355_(p_217366_, p_217367_);
        } else {
            boolean $$14;
            Optional<WalkTarget> $$12 = $$4.m_21952_(MemoryModuleType.f_26370_);
            Optional<Vec3> $$13 = $$4.m_21952_(MemoryModuleType.f_148203_);
            boolean bl = $$14 = !$$12.isPresent() || !$$13.isPresent() || $$12.get().m_26420_().m_7024_().m_82509_($$13.get(), 0.25);
            if ($$14) {
                this.m_217355_(p_217366_, p_217367_);
            }
        }
    }

    private boolean m_217362_(ServerLevel p_217363_, Goat p_217364_) {
        Vec3 $$2 = p_217364_.m_20184_().m_82542_(1.0, 0.0, 1.0).m_82541_();
        BlockPos $$3 = new BlockPos(p_217364_.m_20182_().m_82549_($$2));
        return p_217363_.m_8055_($$3).m_204336_(BlockTags.f_215832_) || p_217363_.m_8055_($$3.m_7494_()).m_204336_(BlockTags.f_215832_);
    }

    protected void m_217355_(ServerLevel p_217356_, Goat p_217357_) {
        p_217356_.m_7605_(p_217357_, (byte)59);
        p_217357_.m_6274_().m_21879_(MemoryModuleType.f_148202_, this.f_147802_.apply(p_217357_).m_214085_(p_217356_.f_46441_));
        p_217357_.m_6274_().m_21936_(MemoryModuleType.f_148203_);
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Goat)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Goat)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Goat)livingEntity, l);
    }
}

