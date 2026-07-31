/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal.horse;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class SkeletonTrapGoal
extends Goal {
    private final SkeletonHorse f_30925_;

    public SkeletonTrapGoal(SkeletonHorse p_30927_) {
        this.f_30925_ = p_30927_;
    }

    @Override
    public boolean m_8036_() {
        return this.f_30925_.f_19853_.m_45914_(this.f_30925_.m_20185_(), this.f_30925_.m_20186_(), this.f_30925_.m_20189_(), 10.0);
    }

    @Override
    public void m_8037_() {
        ServerLevel $$0 = (ServerLevel)this.f_30925_.f_19853_;
        DifficultyInstance $$1 = $$0.m_6436_(this.f_30925_.m_20183_());
        this.f_30925_.m_30923_(false);
        this.f_30925_.m_30651_(true);
        this.f_30925_.m_146762_(0);
        LightningBolt $$2 = EntityType.f_20465_.m_20615_($$0);
        $$2.m_6027_(this.f_30925_.m_20185_(), this.f_30925_.m_20186_(), this.f_30925_.m_20189_());
        $$2.m_20874_(true);
        $$0.m_7967_($$2);
        Skeleton $$3 = this.m_30931_($$1, this.f_30925_);
        $$3.m_20329_(this.f_30925_);
        $$0.m_47205_($$3);
        for (int $$4 = 0; $$4 < 3; ++$$4) {
            AbstractHorse $$5 = this.m_30929_($$1);
            Skeleton $$6 = this.m_30931_($$1, $$5);
            $$6.m_20329_($$5);
            $$5.m_5997_(this.f_30925_.m_217043_().m_216328_(0.0, 1.1485), 0.0, this.f_30925_.m_217043_().m_216328_(0.0, 1.1485));
            $$0.m_47205_($$5);
        }
    }

    private AbstractHorse m_30929_(DifficultyInstance p_30930_) {
        SkeletonHorse $$1 = EntityType.f_20525_.m_20615_(this.f_30925_.f_19853_);
        $$1.m_6518_((ServerLevel)this.f_30925_.f_19853_, p_30930_, MobSpawnType.TRIGGERED, null, null);
        $$1.m_6034_(this.f_30925_.m_20185_(), this.f_30925_.m_20186_(), this.f_30925_.m_20189_());
        $$1.f_19802_ = 60;
        $$1.m_21530_();
        $$1.m_30651_(true);
        $$1.m_146762_(0);
        return $$1;
    }

    private Skeleton m_30931_(DifficultyInstance p_30932_, AbstractHorse p_30933_) {
        Skeleton $$2 = EntityType.f_20524_.m_20615_(p_30933_.f_19853_);
        $$2.m_6518_((ServerLevel)p_30933_.f_19853_, p_30932_, MobSpawnType.TRIGGERED, null, null);
        $$2.m_6034_(p_30933_.m_20185_(), p_30933_.m_20186_(), p_30933_.m_20189_());
        $$2.f_19802_ = 60;
        $$2.m_21530_();
        if ($$2.m_6844_(EquipmentSlot.HEAD).m_41619_()) {
            $$2.m_8061_(EquipmentSlot.HEAD, new ItemStack(Items.f_42468_));
        }
        $$2.m_8061_(EquipmentSlot.MAINHAND, EnchantmentHelper.m_220292_($$2.m_217043_(), this.m_30934_($$2.m_21205_()), (int)(5.0f + p_30932_.m_19057_() * (float)$$2.m_217043_().m_188503_(18)), false));
        $$2.m_8061_(EquipmentSlot.HEAD, EnchantmentHelper.m_220292_($$2.m_217043_(), this.m_30934_($$2.m_6844_(EquipmentSlot.HEAD)), (int)(5.0f + p_30932_.m_19057_() * (float)$$2.m_217043_().m_188503_(18)), false));
        return $$2;
    }

    private ItemStack m_30934_(ItemStack p_30935_) {
        p_30935_.m_41749_("Enchantments");
        return p_30935_;
    }
}

