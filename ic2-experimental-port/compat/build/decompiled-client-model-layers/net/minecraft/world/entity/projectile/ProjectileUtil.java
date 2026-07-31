/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.projectile;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class ProjectileUtil {
    public static HitResult m_37294_(Entity p_37295_, Predicate<Entity> p_37296_) {
        EntityHitResult $$7;
        Vec3 $$5;
        Vec3 $$2 = p_37295_.m_20184_();
        Level $$3 = p_37295_.f_19853_;
        Vec3 $$4 = p_37295_.m_20182_();
        HitResult $$6 = $$3.m_45547_(new ClipContext($$4, $$5 = $$4.m_82549_($$2), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, p_37295_));
        if (((HitResult)$$6).m_6662_() != HitResult.Type.MISS) {
            $$5 = $$6.m_82450_();
        }
        if (($$7 = ProjectileUtil.m_37304_($$3, p_37295_, $$4, $$5, p_37295_.m_20191_().m_82369_(p_37295_.m_20184_()).m_82400_(1.0), p_37296_)) != null) {
            $$6 = $$7;
        }
        return $$6;
    }

    @Nullable
    public static EntityHitResult m_37287_(Entity p_37288_, Vec3 p_37289_, Vec3 p_37290_, AABB p_37291_, Predicate<Entity> p_37292_, double p_37293_) {
        Level $$6 = p_37288_.f_19853_;
        double $$7 = p_37293_;
        Entity $$8 = null;
        Vec3 $$9 = null;
        for (Entity $$10 : $$6.m_6249_(p_37288_, p_37291_, p_37292_)) {
            Vec3 $$13;
            double $$14;
            AABB $$11 = $$10.m_20191_().m_82400_($$10.m_6143_());
            Optional<Vec3> $$12 = $$11.m_82371_(p_37289_, p_37290_);
            if ($$11.m_82390_(p_37289_)) {
                if (!($$7 >= 0.0)) continue;
                $$8 = $$10;
                $$9 = $$12.orElse(p_37289_);
                $$7 = 0.0;
                continue;
            }
            if (!$$12.isPresent() || !(($$14 = p_37289_.m_82557_($$13 = $$12.get())) < $$7) && $$7 != 0.0) continue;
            if ($$10.m_20201_() == p_37288_.m_20201_()) {
                if ($$7 != 0.0) continue;
                $$8 = $$10;
                $$9 = $$13;
                continue;
            }
            $$8 = $$10;
            $$9 = $$13;
            $$7 = $$14;
        }
        if ($$8 == null) {
            return null;
        }
        return new EntityHitResult($$8, $$9);
    }

    @Nullable
    public static EntityHitResult m_37304_(Level p_37305_, Entity p_37306_, Vec3 p_37307_, Vec3 p_37308_, AABB p_37309_, Predicate<Entity> p_37310_) {
        return ProjectileUtil.m_150175_(p_37305_, p_37306_, p_37307_, p_37308_, p_37309_, p_37310_, 0.3f);
    }

    @Nullable
    public static EntityHitResult m_150175_(Level p_150176_, Entity p_150177_, Vec3 p_150178_, Vec3 p_150179_, AABB p_150180_, Predicate<Entity> p_150181_, float p_150182_) {
        double $$7 = Double.MAX_VALUE;
        Entity $$8 = null;
        for (Entity $$9 : p_150176_.m_6249_(p_150177_, p_150180_, p_150181_)) {
            double $$12;
            AABB $$10 = $$9.m_20191_().m_82400_(p_150182_);
            Optional<Vec3> $$11 = $$10.m_82371_(p_150178_, p_150179_);
            if (!$$11.isPresent() || !(($$12 = p_150178_.m_82557_($$11.get())) < $$7)) continue;
            $$8 = $$9;
            $$7 = $$12;
        }
        if ($$8 == null) {
            return null;
        }
        return new EntityHitResult($$8);
    }

    public static void m_37284_(Entity p_37285_, float p_37286_) {
        Vec3 $$2 = p_37285_.m_20184_();
        if ($$2.m_82556_() == 0.0) {
            return;
        }
        double $$3 = $$2.m_165924_();
        p_37285_.m_146922_((float)(Mth.m_14136_($$2.f_82481_, $$2.f_82479_) * 57.2957763671875) + 90.0f);
        p_37285_.m_146926_((float)(Mth.m_14136_($$3, $$2.f_82480_) * 57.2957763671875) - 90.0f);
        while (p_37285_.m_146909_() - p_37285_.f_19860_ < -180.0f) {
            p_37285_.f_19860_ -= 360.0f;
        }
        while (p_37285_.m_146909_() - p_37285_.f_19860_ >= 180.0f) {
            p_37285_.f_19860_ += 360.0f;
        }
        while (p_37285_.m_146908_() - p_37285_.f_19859_ < -180.0f) {
            p_37285_.f_19859_ -= 360.0f;
        }
        while (p_37285_.m_146908_() - p_37285_.f_19859_ >= 180.0f) {
            p_37285_.f_19859_ += 360.0f;
        }
        p_37285_.m_146926_(Mth.m_14179_(p_37286_, p_37285_.f_19860_, p_37285_.m_146909_()));
        p_37285_.m_146922_(Mth.m_14179_(p_37286_, p_37285_.f_19859_, p_37285_.m_146908_()));
    }

    public static InteractionHand m_37297_(LivingEntity p_37298_, Item p_37299_) {
        return p_37298_.m_21205_().m_150930_(p_37299_) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    public static AbstractArrow m_37300_(LivingEntity p_37301_, ItemStack p_37302_, float p_37303_) {
        ArrowItem $$3 = (ArrowItem)(p_37302_.m_41720_() instanceof ArrowItem ? p_37302_.m_41720_() : Items.f_42412_);
        AbstractArrow $$4 = $$3.m_6394_(p_37301_.f_19853_, p_37302_, p_37301_);
        $$4.m_36745_(p_37301_, p_37303_);
        if (p_37302_.m_150930_(Items.f_42738_) && $$4 instanceof Arrow) {
            ((Arrow)$$4).m_36878_(p_37302_);
        }
        return $$4;
    }
}

