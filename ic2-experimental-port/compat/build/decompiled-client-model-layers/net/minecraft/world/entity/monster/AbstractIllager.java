/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

public abstract class AbstractIllager
extends Raider {
    protected AbstractIllager(EntityType<? extends AbstractIllager> p_32105_, Level p_32106_) {
        super((EntityType<? extends Raider>)p_32105_, p_32106_);
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21643_;
    }

    public IllagerArmPose m_6768_() {
        return IllagerArmPose.CROSSED;
    }

    @Override
    public boolean m_6779_(LivingEntity p_186270_) {
        if (p_186270_ instanceof AbstractVillager && p_186270_.m_6162_()) {
            return false;
        }
        return super.m_6779_(p_186270_);
    }

    public static final class IllagerArmPose
    extends Enum<IllagerArmPose> {
        public static final /* enum */ IllagerArmPose CROSSED = new IllagerArmPose();
        public static final /* enum */ IllagerArmPose ATTACKING = new IllagerArmPose();
        public static final /* enum */ IllagerArmPose SPELLCASTING = new IllagerArmPose();
        public static final /* enum */ IllagerArmPose BOW_AND_ARROW = new IllagerArmPose();
        public static final /* enum */ IllagerArmPose CROSSBOW_HOLD = new IllagerArmPose();
        public static final /* enum */ IllagerArmPose CROSSBOW_CHARGE = new IllagerArmPose();
        public static final /* enum */ IllagerArmPose CELEBRATING = new IllagerArmPose();
        public static final /* enum */ IllagerArmPose NEUTRAL = new IllagerArmPose();
        private static final /* synthetic */ IllagerArmPose[] $VALUES;

        public static IllagerArmPose[] values() {
            return (IllagerArmPose[])$VALUES.clone();
        }

        public static IllagerArmPose valueOf(String p_32123_) {
            return Enum.valueOf(IllagerArmPose.class, p_32123_);
        }

        private static /* synthetic */ IllagerArmPose[] m_149681_() {
            return new IllagerArmPose[]{CROSSED, ATTACKING, SPELLCASTING, BOW_AND_ARROW, CROSSBOW_HOLD, CROSSBOW_CHARGE, CELEBRATING, NEUTRAL};
        }

        static {
            $VALUES = IllagerArmPose.m_149681_();
        }
    }

    protected class RaiderOpenDoorGoal
    extends OpenDoorGoal {
        public RaiderOpenDoorGoal(Raider p_32128_) {
            super(p_32128_, false);
        }

        @Override
        public boolean m_8036_() {
            return super.m_8036_() && AbstractIllager.this.m_37886_();
        }
    }
}

