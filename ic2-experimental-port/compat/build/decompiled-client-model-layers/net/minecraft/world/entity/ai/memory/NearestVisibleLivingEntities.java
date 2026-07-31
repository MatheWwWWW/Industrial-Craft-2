/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 */
package net.minecraft.world.entity.ai.memory;

import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class NearestVisibleLivingEntities {
    private static final NearestVisibleLivingEntities f_186098_ = new NearestVisibleLivingEntities();
    private final List<LivingEntity> f_186099_;
    private final Predicate<LivingEntity> f_186100_;

    private NearestVisibleLivingEntities() {
        this.f_186099_ = List.of();
        this.f_186100_ = p_186122_ -> false;
    }

    public NearestVisibleLivingEntities(LivingEntity p_186104_, List<LivingEntity> p_186105_) {
        this.f_186099_ = p_186105_;
        Object2BooleanOpenHashMap $$2 = new Object2BooleanOpenHashMap(p_186105_.size());
        Predicate<LivingEntity> $$3 = p_186111_ -> Sensor.m_26803_(p_186104_, p_186111_);
        this.f_186100_ = p_186115_ -> $$2.computeIfAbsent(p_186115_, $$3);
    }

    public static NearestVisibleLivingEntities m_186106_() {
        return f_186098_;
    }

    public Optional<LivingEntity> m_186116_(Predicate<LivingEntity> p_186117_) {
        for (LivingEntity $$1 : this.f_186099_) {
            if (!p_186117_.test($$1) || !this.f_186100_.test($$1)) continue;
            return Optional.of($$1);
        }
        return Optional.empty();
    }

    public Iterable<LivingEntity> m_186123_(Predicate<LivingEntity> p_186124_) {
        return Iterables.filter(this.f_186099_, p_186127_ -> p_186124_.test((LivingEntity)p_186127_) && this.f_186100_.test((LivingEntity)p_186127_));
    }

    public Stream<LivingEntity> m_186128_(Predicate<LivingEntity> p_186129_) {
        return this.f_186099_.stream().filter(p_186120_ -> p_186129_.test((LivingEntity)p_186120_) && this.f_186100_.test((LivingEntity)p_186120_));
    }

    public boolean m_186107_(LivingEntity p_186108_) {
        return this.f_186099_.contains(p_186108_) && this.f_186100_.test(p_186108_);
    }

    public boolean m_186130_(Predicate<LivingEntity> p_186131_) {
        for (LivingEntity $$1 : this.f_186099_) {
            if (!p_186131_.test($$1) || !this.f_186100_.test($$1)) continue;
            return true;
        }
        return false;
    }
}

