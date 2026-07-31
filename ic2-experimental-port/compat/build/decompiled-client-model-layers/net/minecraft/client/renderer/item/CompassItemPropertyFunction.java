/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.item;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class CompassItemPropertyFunction
implements ClampedItemPropertyFunction {
    public static final int f_234928_ = 0;
    private final CompassWobble f_234930_ = new CompassWobble();
    private final CompassWobble f_234931_ = new CompassWobble();
    public final CompassTarget f_234929_;

    public CompassItemPropertyFunction(CompassTarget p_234933_) {
        this.f_234929_ = p_234933_;
    }

    @Override
    public float m_142187_(ItemStack p_234960_, @Nullable ClientLevel p_234961_, @Nullable LivingEntity p_234962_, int p_234963_) {
        Entity $$4;
        Entity entity = $$4 = p_234962_ != null ? p_234962_ : p_234960_.m_41609_();
        if ($$4 == null) {
            return 0.0f;
        }
        if ((p_234961_ = this.m_234945_($$4, p_234961_)) == null) {
            return 0.0f;
        }
        return this.m_234954_(p_234960_, p_234961_, p_234963_, $$4);
    }

    private float m_234954_(ItemStack p_234955_, ClientLevel p_234956_, int p_234957_, Entity p_234958_) {
        GlobalPos $$4 = this.f_234929_.m_234964_(p_234956_, p_234955_, p_234958_);
        long $$5 = p_234956_.m_46467_();
        if (!this.m_234951_(p_234958_, $$4)) {
            return this.m_234936_(p_234957_, $$5);
        }
        return this.m_234941_(p_234958_, $$5, $$4.m_122646_());
    }

    private float m_234936_(int p_234937_, long p_234938_) {
        if (this.f_234931_.m_234972_(p_234938_)) {
            this.f_234931_.m_234974_(p_234938_, Math.random());
        }
        double $$2 = this.f_234931_.f_234968_ + (double)((float)this.m_234934_(p_234937_) / 2.14748365E9f);
        return Mth.m_14091_((float)$$2, 1.0f);
    }

    private float m_234941_(Entity p_234942_, long p_234943_, BlockPos p_234944_) {
        double $$7;
        Player $$5;
        double $$3 = this.m_234948_(p_234942_, p_234944_);
        double $$4 = this.m_234939_(p_234942_);
        if (p_234942_ instanceof Player && ($$5 = (Player)p_234942_).m_7578_()) {
            if (this.f_234930_.m_234972_(p_234943_)) {
                this.f_234930_.m_234974_(p_234943_, 0.5 - ($$4 - 0.25));
            }
            double $$6 = $$3 + this.f_234930_.f_234968_;
        } else {
            $$7 = 0.5 - ($$4 - 0.25 - $$3);
        }
        return Mth.m_14091_((float)$$7, 1.0f);
    }

    @Nullable
    private ClientLevel m_234945_(Entity p_234946_, @Nullable ClientLevel p_234947_) {
        if (p_234947_ == null && p_234946_.f_19853_ instanceof ClientLevel) {
            return (ClientLevel)p_234946_.f_19853_;
        }
        return p_234947_;
    }

    private boolean m_234951_(Entity p_234952_, @Nullable GlobalPos p_234953_) {
        return p_234953_ != null && p_234953_.m_122640_() == p_234952_.f_19853_.m_46472_() && !(p_234953_.m_122646_().m_203193_(p_234952_.m_20182_()) < (double)1.0E-5f);
    }

    private double m_234948_(Entity p_234949_, BlockPos p_234950_) {
        Vec3 $$2 = Vec3.m_82512_(p_234950_);
        return Math.atan2($$2.m_7094_() - p_234949_.m_20189_(), $$2.m_7096_() - p_234949_.m_20185_()) / 6.2831854820251465;
    }

    private double m_234939_(Entity p_234940_) {
        return Mth.m_14109_(p_234940_.m_213816_() / 360.0f, 1.0);
    }

    private int m_234934_(int p_234935_) {
        return p_234935_ * 1327217883;
    }

    static class CompassWobble {
        double f_234968_;
        private double f_234969_;
        private long f_234970_;

        CompassWobble() {
        }

        boolean m_234972_(long p_234973_) {
            return this.f_234970_ != p_234973_;
        }

        void m_234974_(long p_234975_, double p_234976_) {
            this.f_234970_ = p_234975_;
            double $$2 = p_234976_ - this.f_234968_;
            $$2 = Mth.m_14109_($$2 + 0.5, 1.0) - 0.5;
            this.f_234969_ += $$2 * 0.1;
            this.f_234969_ *= 0.8;
            this.f_234968_ = Mth.m_14109_(this.f_234968_ + this.f_234969_, 1.0);
        }
    }

    public static interface CompassTarget {
        @Nullable
        public GlobalPos m_234964_(ClientLevel var1, ItemStack var2, Entity var3);
    }
}

