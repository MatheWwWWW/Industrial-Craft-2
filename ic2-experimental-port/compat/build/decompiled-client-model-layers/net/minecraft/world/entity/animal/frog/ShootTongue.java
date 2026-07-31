/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.animal.frog;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class ShootTongue
extends Behavior<Frog> {
    public static final int f_218608_ = 100;
    public static final int f_218609_ = 6;
    public static final int f_218610_ = 10;
    private static final float f_218611_ = 1.75f;
    private static final float f_218612_ = 0.75f;
    public static final int f_238166_ = 100;
    public static final int f_238181_ = 5;
    private int f_218613_;
    private int f_218614_;
    private final SoundEvent f_218615_;
    private final SoundEvent f_218616_;
    private Vec3 f_218617_;
    private State f_218618_ = State.DONE;

    public ShootTongue(SoundEvent p_218620_, SoundEvent p_218621_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_217768_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), 100);
        this.f_218615_ = p_218620_;
        this.f_218616_ = p_218621_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_218630_, Frog p_218631_) {
        LivingEntity $$2 = p_218631_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
        boolean $$3 = this.m_238358_(p_218631_, $$2);
        if (!$$3) {
            p_218631_.m_6274_().m_21936_(MemoryModuleType.f_26372_);
            this.m_238443_(p_218631_, $$2);
        }
        return $$3 && p_218631_.m_20089_() != Pose.CROAKING && Frog.m_218532_($$2);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_218633_, Frog p_218634_, long p_218635_) {
        return p_218634_.m_6274_().m_21874_(MemoryModuleType.f_26372_) && this.f_218618_ != State.DONE && !p_218634_.m_6274_().m_21874_(MemoryModuleType.f_217768_);
    }

    @Override
    protected void m_6735_(ServerLevel p_218644_, Frog p_218645_, long p_218646_) {
        LivingEntity $$3 = p_218645_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
        BehaviorUtils.m_22595_(p_218645_, $$3);
        p_218645_.m_218481_($$3);
        p_218645_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget($$3.m_20182_(), 2.0f, 0));
        this.f_218614_ = 10;
        this.f_218618_ = State.MOVE_TO_TARGET;
    }

    @Override
    protected void m_6732_(ServerLevel p_218652_, Frog p_218653_, long p_218654_) {
        p_218653_.m_6274_().m_21936_(MemoryModuleType.f_26372_);
        p_218653_.m_218536_();
        p_218653_.m_20124_(Pose.STANDING);
    }

    private void m_218640_(ServerLevel p_218641_, Frog p_218642_) {
        Entity $$3;
        p_218641_.m_6269_(null, p_218642_, this.f_218616_, SoundSource.NEUTRAL, 2.0f, 1.0f);
        Optional<Entity> $$2 = p_218642_.m_218538_();
        if ($$2.isPresent() && ($$3 = $$2.get()).m_6084_()) {
            p_218642_.m_7327_($$3);
            if (!$$3.m_6084_()) {
                $$3.m_142687_(Entity.RemovalReason.KILLED);
            }
        }
    }

    @Override
    protected void m_6725_(ServerLevel p_218660_, Frog p_218661_, long p_218662_) {
        LivingEntity $$3 = p_218661_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
        p_218661_.m_218481_($$3);
        switch (this.f_218618_) {
            case MOVE_TO_TARGET: {
                if ($$3.m_20270_(p_218661_) < 1.75f) {
                    p_218660_.m_6269_(null, p_218661_, this.f_218615_, SoundSource.NEUTRAL, 2.0f, 1.0f);
                    p_218661_.m_20124_(Pose.USING_TONGUE);
                    $$3.m_20256_($$3.m_20182_().m_82505_(p_218661_.m_20182_()).m_82541_().m_82490_(0.75));
                    this.f_218617_ = $$3.m_20182_();
                    this.f_218613_ = 0;
                    this.f_218618_ = State.CATCH_ANIMATION;
                    break;
                }
                if (this.f_218614_ <= 0) {
                    p_218661_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget($$3.m_20182_(), 2.0f, 0));
                    this.f_218614_ = 10;
                    break;
                }
                --this.f_218614_;
                break;
            }
            case CATCH_ANIMATION: {
                if (this.f_218613_++ < 6) break;
                this.f_218618_ = State.EAT_ANIMATION;
                this.m_218640_(p_218660_, p_218661_);
                break;
            }
            case EAT_ANIMATION: {
                if (this.f_218613_ >= 10) {
                    this.f_218618_ = State.DONE;
                    break;
                }
                ++this.f_218613_;
                break;
            }
        }
    }

    private boolean m_238358_(Frog p_238359_, LivingEntity p_238360_) {
        Path $$2 = p_238359_.m_21573_().m_6570_(p_238360_, 0);
        return $$2 != null && $$2.m_77407_() < 1.75f;
    }

    private void m_238443_(Frog p_238444_, LivingEntity p_243335_) {
        boolean $$3;
        List $$2 = p_238444_.m_6274_().m_21952_(MemoryModuleType.f_238182_).orElseGet(ArrayList::new);
        boolean bl = $$3 = !$$2.contains(p_243335_.m_20148_());
        if ($$2.size() == 5 && $$3) {
            $$2.remove(0);
        }
        if ($$3) {
            $$2.add(p_243335_.m_20148_());
        }
        p_238444_.m_6274_().m_21882_(MemoryModuleType.f_238182_, $$2, 100L);
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Frog)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Frog)livingEntity, l);
    }

    static final class State
    extends Enum<State> {
        public static final /* enum */ State MOVE_TO_TARGET = new State();
        public static final /* enum */ State CATCH_ANIMATION = new State();
        public static final /* enum */ State EAT_ANIMATION = new State();
        public static final /* enum */ State DONE = new State();
        private static final /* synthetic */ State[] $VALUES;

        public static State[] values() {
            return (State[])$VALUES.clone();
        }

        public static State valueOf(String p_218676_) {
            return Enum.valueOf(State.class, p_218676_);
        }

        private static /* synthetic */ State[] m_218674_() {
            return new State[]{MOVE_TO_TARGET, CATCH_ANIMATION, EAT_ANIMATION, DONE};
        }

        static {
            $VALUES = State.m_218674_();
        }
    }
}

