/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class PlayTagWithOtherKids
extends Behavior<PathfinderMob> {
    private static final int f_147700_ = 20;
    private static final int f_147701_ = 8;
    private static final float f_147702_ = 0.6f;
    private static final float f_147703_ = 0.6f;
    private static final int f_147704_ = 5;
    private static final int f_147705_ = 10;

    public PlayTagWithOtherKids() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26366_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26374_, (Object)((Object)MemoryStatus.REGISTERED)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23629_, PathfinderMob p_23630_) {
        return p_23629_.m_213780_().m_188503_(10) == 0 && this.m_23676_(p_23630_);
    }

    @Override
    protected void m_6735_(ServerLevel p_23632_, PathfinderMob p_23633_, long p_23634_) {
        LivingEntity $$3 = this.m_23657_(p_23633_);
        if ($$3 != null) {
            this.m_23635_(p_23632_, p_23633_, $$3);
            return;
        }
        Optional<LivingEntity> $$4 = this.m_23662_(p_23633_);
        if ($$4.isPresent()) {
            PlayTagWithOtherKids.m_23649_(p_23633_, $$4.get());
            return;
        }
        this.m_23647_(p_23633_).ifPresent(p_23666_ -> PlayTagWithOtherKids.m_23649_(p_23633_, p_23666_));
    }

    private void m_23635_(ServerLevel p_23636_, PathfinderMob p_23637_, LivingEntity p_23638_) {
        for (int $$3 = 0; $$3 < 10; ++$$3) {
            Vec3 $$4 = LandRandomPos.m_148488_(p_23637_, 20, 8);
            if ($$4 == null || !p_23636_.m_8802_(new BlockPos($$4))) continue;
            p_23637_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget($$4, 0.6f, 0));
            return;
        }
    }

    private static void m_23649_(PathfinderMob p_23650_, LivingEntity p_23651_) {
        Brain<?> $$2 = p_23650_.m_6274_();
        $$2.m_21879_(MemoryModuleType.f_26374_, p_23651_);
        $$2.m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_23651_, true));
        $$2.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(new EntityTracker(p_23651_, false), 0.6f, 1));
    }

    private Optional<LivingEntity> m_23647_(PathfinderMob p_23648_) {
        return this.m_23674_(p_23648_).stream().findAny();
    }

    private Optional<LivingEntity> m_23662_(PathfinderMob p_23663_) {
        Map<LivingEntity, Integer> $$1 = this.m_23672_(p_23663_);
        return $$1.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).filter(p_23653_ -> (Integer)p_23653_.getValue() > 0 && (Integer)p_23653_.getValue() <= 5).map(Map.Entry::getKey).findFirst();
    }

    private Map<LivingEntity, Integer> m_23672_(PathfinderMob p_23673_) {
        HashMap $$1 = Maps.newHashMap();
        this.m_23674_(p_23673_).stream().filter(this::m_23667_).forEach(p_23656_ -> $$1.compute(this.m_23639_((LivingEntity)p_23656_), (p_147707_, p_147708_) -> p_147708_ == null ? 1 : p_147708_ + 1));
        return $$1;
    }

    private List<LivingEntity> m_23674_(PathfinderMob p_23675_) {
        return p_23675_.m_6274_().m_21952_(MemoryModuleType.f_26366_).get();
    }

    private LivingEntity m_23639_(LivingEntity p_23640_) {
        return p_23640_.m_6274_().m_21952_(MemoryModuleType.f_26374_).get();
    }

    @Nullable
    private LivingEntity m_23657_(LivingEntity p_23658_) {
        return p_23658_.m_6274_().m_21952_(MemoryModuleType.f_26366_).get().stream().filter(p_23671_ -> this.m_23641_(p_23658_, (LivingEntity)p_23671_)).findAny().orElse(null);
    }

    private boolean m_23667_(LivingEntity p_23668_) {
        return p_23668_.m_6274_().m_21952_(MemoryModuleType.f_26374_).isPresent();
    }

    private boolean m_23641_(LivingEntity p_23642_, LivingEntity p_23643_) {
        return p_23643_.m_6274_().m_21952_(MemoryModuleType.f_26374_).filter(p_23661_ -> p_23661_ == p_23642_).isPresent();
    }

    private boolean m_23676_(PathfinderMob p_23677_) {
        return p_23677_.m_6274_().m_21874_(MemoryModuleType.f_26366_);
    }
}

