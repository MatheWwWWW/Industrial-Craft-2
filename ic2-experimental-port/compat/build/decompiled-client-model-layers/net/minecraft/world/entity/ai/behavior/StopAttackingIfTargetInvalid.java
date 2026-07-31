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
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class StopAttackingIfTargetInvalid<E extends Mob>
extends Behavior<E> {
    private static final int f_147978_ = 200;
    private final Predicate<LivingEntity> f_24233_;
    private final BiConsumer<E, LivingEntity> f_147979_;
    private final boolean f_217397_;

    public StopAttackingIfTargetInvalid(Predicate<LivingEntity> p_217404_, BiConsumer<E, LivingEntity> p_217405_, boolean p_217406_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26326_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_24233_ = p_217404_;
        this.f_147979_ = p_217405_;
        this.f_217397_ = p_217406_;
    }

    public StopAttackingIfTargetInvalid(Predicate<LivingEntity> p_217401_, BiConsumer<E, LivingEntity> p_217402_) {
        this(p_217401_, p_217402_, true);
    }

    public StopAttackingIfTargetInvalid(Predicate<LivingEntity> p_24236_) {
        this(p_24236_, (p_217411_, p_217412_) -> {});
    }

    public StopAttackingIfTargetInvalid(BiConsumer<E, LivingEntity> p_217399_) {
        this((LivingEntity p_147988_) -> false, p_217399_);
    }

    public StopAttackingIfTargetInvalid() {
        this((LivingEntity p_147986_) -> false, (p_217408_, p_217409_) -> {});
    }

    @Override
    protected void m_6735_(ServerLevel p_24242_, E p_24243_, long p_24244_) {
        LivingEntity $$3 = this.m_24251_(p_24243_);
        if (!((LivingEntity)p_24243_).m_6779_($$3)) {
            this.m_24255_(p_24243_);
            return;
        }
        if (this.f_217397_ && StopAttackingIfTargetInvalid.m_24245_(p_24243_)) {
            this.m_24255_(p_24243_);
            return;
        }
        if (this.m_24253_(p_24243_)) {
            this.m_24255_(p_24243_);
            return;
        }
        if (this.m_24247_(p_24243_)) {
            this.m_24255_(p_24243_);
            return;
        }
        if (this.f_24233_.test(this.m_24251_(p_24243_))) {
            this.m_24255_(p_24243_);
            return;
        }
    }

    private boolean m_24247_(E p_24248_) {
        return this.m_24251_(p_24248_).f_19853_ != ((Mob)p_24248_).f_19853_;
    }

    private LivingEntity m_24251_(E p_24252_) {
        return ((LivingEntity)p_24252_).m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
    }

    private static <E extends LivingEntity> boolean m_24245_(E p_24246_) {
        Optional<Long> $$1 = p_24246_.m_6274_().m_21952_(MemoryModuleType.f_26326_);
        return $$1.isPresent() && p_24246_.f_19853_.m_46467_() - $$1.get() > 200L;
    }

    private boolean m_24253_(E p_24254_) {
        Optional<LivingEntity> $$1 = ((LivingEntity)p_24254_).m_6274_().m_21952_(MemoryModuleType.f_26372_);
        return $$1.isPresent() && !$$1.get().m_6084_();
    }

    protected void m_24255_(E p_24256_) {
        this.f_147979_.accept(p_24256_, this.m_24251_(p_24256_));
        ((LivingEntity)p_24256_).m_6274_().m_21936_(MemoryModuleType.f_26372_);
    }
}

