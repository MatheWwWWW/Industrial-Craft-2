/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public interface CrossbowAttackMob
extends RangedAttackMob {
    public void m_6136_(boolean var1);

    public void m_5811_(LivingEntity var1, ItemStack var2, Projectile var3, float var4);

    @Nullable
    public LivingEntity m_5448_();

    public void m_5847_();

    default public void m_32336_(LivingEntity p_32337_, float p_32338_) {
        InteractionHand $$2 = ProjectileUtil.m_37297_(p_32337_, Items.f_42717_);
        ItemStack $$3 = p_32337_.m_21120_($$2);
        if (p_32337_.m_21055_(Items.f_42717_)) {
            CrossbowItem.m_40887_(p_32337_.f_19853_, p_32337_, $$2, $$3, p_32338_, 14 - p_32337_.f_19853_.m_46791_().m_19028_() * 4);
        }
        this.m_5847_();
    }

    default public void m_32322_(LivingEntity p_32323_, LivingEntity p_32324_, Projectile p_32325_, float p_32326_, float p_32327_) {
        Projectile $$5 = p_32325_;
        double $$6 = p_32324_.m_20185_() - p_32323_.m_20185_();
        double $$7 = p_32324_.m_20189_() - p_32323_.m_20189_();
        double $$8 = Math.sqrt($$6 * $$6 + $$7 * $$7);
        double $$9 = p_32324_.m_20227_(0.3333333333333333) - $$5.m_20186_() + $$8 * (double)0.2f;
        Vector3f $$10 = this.m_32332_(p_32323_, new Vec3($$6, $$9, $$7), p_32326_);
        p_32325_.m_6686_($$10.m_122239_(), $$10.m_122260_(), $$10.m_122269_(), p_32327_, 14 - p_32323_.f_19853_.m_46791_().m_19028_() * 4);
        p_32323_.m_5496_(SoundEvents.f_11847_, 1.0f, 1.0f / (p_32323_.m_217043_().m_188501_() * 0.4f + 0.8f));
    }

    default public Vector3f m_32332_(LivingEntity p_32333_, Vec3 p_32334_, float p_32335_) {
        Vec3 $$3 = p_32334_.m_82541_();
        Vec3 $$4 = $$3.m_82537_(new Vec3(0.0, 1.0, 0.0));
        if ($$4.m_82556_() <= 1.0E-7) {
            $$4 = $$3.m_82537_(p_32333_.m_20289_(1.0f));
        }
        Quaternion $$5 = new Quaternion(new Vector3f($$4), 90.0f, true);
        Vector3f $$6 = new Vector3f($$3);
        $$6.m_122251_($$5);
        Quaternion $$7 = new Quaternion($$6, p_32335_, true);
        Vector3f $$8 = new Vector3f($$3);
        $$8.m_122251_($$7);
        return $$8;
    }
}

