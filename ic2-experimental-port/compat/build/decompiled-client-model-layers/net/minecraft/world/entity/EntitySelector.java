/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicates
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.Team;

public final class EntitySelector {
    public static final Predicate<Entity> f_20402_ = Entity::m_6084_;
    public static final Predicate<Entity> f_20403_ = p_20442_ -> p_20442_.m_6084_() && p_20442_ instanceof LivingEntity;
    public static final Predicate<Entity> f_20404_ = p_20440_ -> p_20440_.m_6084_() && !p_20440_.m_20160_() && !p_20440_.m_20159_();
    public static final Predicate<Entity> f_20405_ = p_20438_ -> p_20438_ instanceof Container && p_20438_.m_6084_();
    public static final Predicate<Entity> f_20406_ = p_20436_ -> !(p_20436_ instanceof Player) || !p_20436_.m_5833_() && !((Player)p_20436_).m_7500_();
    public static final Predicate<Entity> f_20408_ = p_20434_ -> !p_20434_.m_5833_();
    public static final Predicate<Entity> f_185987_ = f_20408_.and(Entity::m_5829_);

    private EntitySelector() {
    }

    public static Predicate<Entity> m_20410_(double p_20411_, double p_20412_, double p_20413_, double p_20414_) {
        double $$4 = p_20414_ * p_20414_;
        return p_20420_ -> p_20420_ != null && p_20420_.m_20275_(p_20411_, p_20412_, p_20413_) <= $$4;
    }

    public static Predicate<Entity> m_20421_(Entity p_20422_) {
        Team.CollisionRule $$2;
        Team $$1 = p_20422_.m_5647_();
        Team.CollisionRule collisionRule = $$2 = $$1 == null ? Team.CollisionRule.ALWAYS : $$1.m_7156_();
        if ($$2 == Team.CollisionRule.NEVER) {
            return Predicates.alwaysFalse();
        }
        return f_20408_.and(p_20430_ -> {
            boolean $$6;
            Team.CollisionRule $$5;
            if (!p_20430_.m_6094_()) {
                return false;
            }
            if (!(!p_20427_.f_19853_.f_46443_ || p_20430_ instanceof Player && ((Player)p_20430_).m_7578_())) {
                return false;
            }
            Team $$4 = p_20430_.m_5647_();
            Team.CollisionRule collisionRule = $$5 = $$4 == null ? Team.CollisionRule.ALWAYS : $$4.m_7156_();
            if ($$5 == Team.CollisionRule.NEVER) {
                return false;
            }
            boolean bl = $$6 = $$1 != null && $$1.m_83536_($$4);
            if (($$2 == Team.CollisionRule.PUSH_OWN_TEAM || $$5 == Team.CollisionRule.PUSH_OWN_TEAM) && $$6) {
                return false;
            }
            return $$2 != Team.CollisionRule.PUSH_OTHER_TEAMS && $$5 != Team.CollisionRule.PUSH_OTHER_TEAMS || $$6;
        });
    }

    public static Predicate<Entity> m_20431_(Entity p_20432_) {
        return p_20425_ -> {
            while (p_20425_.m_20159_()) {
                if ((p_20425_ = p_20425_.m_20202_()) != p_20432_) continue;
                return false;
            }
            return true;
        };
    }

    public static class MobCanWearArmorEntitySelector
    implements Predicate<Entity> {
        private final ItemStack f_20443_;

        public MobCanWearArmorEntitySelector(ItemStack p_20445_) {
            this.f_20443_ = p_20445_;
        }

        @Override
        public boolean test(@Nullable Entity p_20447_) {
            if (!p_20447_.m_6084_()) {
                return false;
            }
            if (!(p_20447_ instanceof LivingEntity)) {
                return false;
            }
            LivingEntity $$1 = (LivingEntity)p_20447_;
            return $$1.m_7066_(this.f_20443_);
        }

        @Override
        public /* synthetic */ boolean test(@Nullable Object object) {
            return this.test((Entity)object);
        }
    }
}

