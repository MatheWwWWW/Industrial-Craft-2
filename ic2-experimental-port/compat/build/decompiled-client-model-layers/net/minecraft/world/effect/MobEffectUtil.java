/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.effect;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public final class MobEffectUtil {
    public static String m_19581_(MobEffectInstance p_19582_, float p_19583_) {
        if (p_19582_.m_19577_()) {
            return "**:**";
        }
        int $$2 = Mth.m_14143_((float)p_19582_.m_19557_() * p_19583_);
        return StringUtil.m_14404_($$2);
    }

    public static boolean m_19584_(LivingEntity p_19585_) {
        return p_19585_.m_21023_(MobEffects.f_19598_) || p_19585_.m_21023_(MobEffects.f_19592_);
    }

    public static int m_19586_(LivingEntity p_19587_) {
        int $$1 = 0;
        int $$2 = 0;
        if (p_19587_.m_21023_(MobEffects.f_19598_)) {
            $$1 = p_19587_.m_21124_(MobEffects.f_19598_).m_19564_();
        }
        if (p_19587_.m_21023_(MobEffects.f_19592_)) {
            $$2 = p_19587_.m_21124_(MobEffects.f_19592_).m_19564_();
        }
        return Math.max($$1, $$2);
    }

    public static boolean m_19588_(LivingEntity p_19589_) {
        return p_19589_.m_21023_(MobEffects.f_19608_) || p_19589_.m_21023_(MobEffects.f_19592_);
    }

    public static List<ServerPlayer> m_216946_(ServerLevel p_216947_, @Nullable Entity p_216948_, Vec3 p_216949_, double p_216950_, MobEffectInstance p_216951_, int p_216952_) {
        MobEffect $$6 = p_216951_.m_19544_();
        List<ServerPlayer> $$7 = p_216947_.m_8795_(p_238228_ -> !(!p_238228_.f_8941_.m_9294_() || p_216948_ != null && p_216948_.m_7307_((Entity)p_238228_) || !p_216949_.m_82509_(p_238228_.m_20182_(), p_216950_) || p_238228_.m_21023_($$6) && p_238228_.m_21124_($$6).m_19564_() >= p_216951_.m_19564_() && p_238228_.m_21124_($$6).m_19557_() >= p_216952_));
        $$7.forEach(p_238232_ -> p_238232_.m_147207_(new MobEffectInstance(p_216951_), p_216948_));
        return $$7;
    }
}

