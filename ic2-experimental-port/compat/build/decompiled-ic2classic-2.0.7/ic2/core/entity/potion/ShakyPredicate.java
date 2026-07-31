/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package ic2.core.entity.potion;

import ic2.core.platform.registries.IC2Potions;
import java.util.function.Predicate;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ShakyPredicate
implements Predicate<Entity> {
    public static final ShakyPredicate INSTANCE = new ShakyPredicate();
    public static final long TOTAL_CYCLE_TICKS = 100L;
    public static final long INVINCIBLE_CYCLE_BASE = 10L;

    @Override
    public boolean test(Entity entity) {
        MobEffectInstance instance;
        if (entity instanceof LivingEntity && (instance = ((LivingEntity)entity).m_21124_(IC2Potions.SHAKY)) != null) {
            return entity.m_20193_().m_46467_() % 100L >= 10L * (long)(instance.m_19564_() + 1);
        }
        return true;
    }
}

