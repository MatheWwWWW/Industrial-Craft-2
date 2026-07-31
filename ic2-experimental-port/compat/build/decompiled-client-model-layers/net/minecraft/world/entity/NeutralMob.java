/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public interface NeutralMob {
    public static final String f_147283_ = "AngerTime";
    public static final String f_147284_ = "AngryAt";

    public int m_6784_();

    public void m_7870_(int var1);

    @Nullable
    public UUID m_6120_();

    public void m_6925_(@Nullable UUID var1);

    public void m_6825_();

    default public void m_21678_(CompoundTag p_21679_) {
        p_21679_.m_128405_(f_147283_, this.m_6784_());
        if (this.m_6120_() != null) {
            p_21679_.m_128362_(f_147284_, this.m_6120_());
        }
    }

    default public void m_147285_(Level p_147286_, CompoundTag p_147287_) {
        this.m_7870_(p_147287_.m_128451_(f_147283_));
        if (!(p_147286_ instanceof ServerLevel)) {
            return;
        }
        if (!p_147287_.m_128403_(f_147284_)) {
            this.m_6925_(null);
            return;
        }
        UUID $$2 = p_147287_.m_128342_(f_147284_);
        this.m_6925_($$2);
        Entity $$3 = ((ServerLevel)p_147286_).m_8791_($$2);
        if ($$3 == null) {
            return;
        }
        if ($$3 instanceof Mob) {
            this.m_6703_((Mob)$$3);
        }
        if ($$3.m_6095_() == EntityType.f_20532_) {
            this.m_6598_((Player)$$3);
        }
    }

    default public void m_21666_(ServerLevel p_21667_, boolean p_21668_) {
        LivingEntity $$2 = this.m_5448_();
        UUID $$3 = this.m_6120_();
        if (($$2 == null || $$2.m_21224_()) && $$3 != null && p_21667_.m_8791_($$3) instanceof Mob) {
            this.m_21662_();
            return;
        }
        if ($$2 != null && !Objects.equals($$3, $$2.m_20148_())) {
            this.m_6925_($$2.m_20148_());
            this.m_6825_();
        }
        if (!(this.m_6784_() <= 0 || $$2 != null && $$2.m_6095_() == EntityType.f_20532_ && p_21668_)) {
            this.m_7870_(this.m_6784_() - 1);
            if (this.m_6784_() == 0) {
                this.m_21662_();
            }
        }
    }

    default public boolean m_21674_(LivingEntity p_21675_) {
        if (!this.m_6779_(p_21675_)) {
            return false;
        }
        if (p_21675_.m_6095_() == EntityType.f_20532_ && this.m_21670_(p_21675_.f_19853_)) {
            return true;
        }
        return p_21675_.m_20148_().equals(this.m_6120_());
    }

    default public boolean m_21670_(Level p_21671_) {
        return p_21671_.m_46469_().m_46207_(GameRules.f_46127_) && this.m_21660_() && this.m_6120_() == null;
    }

    default public boolean m_21660_() {
        return this.m_6784_() > 0;
    }

    default public void m_21676_(Player p_21677_) {
        if (!p_21677_.f_19853_.m_46469_().m_46207_(GameRules.f_46126_)) {
            return;
        }
        if (!p_21677_.m_20148_().equals(this.m_6120_())) {
            return;
        }
        this.m_21662_();
    }

    default public void m_21661_() {
        this.m_21662_();
        this.m_6825_();
    }

    default public void m_21662_() {
        this.m_6703_(null);
        this.m_6925_(null);
        this.m_6710_(null);
        this.m_7870_(0);
    }

    @Nullable
    public LivingEntity m_21188_();

    public void m_6703_(@Nullable LivingEntity var1);

    public void m_6598_(@Nullable Player var1);

    public void m_6710_(@Nullable LivingEntity var1);

    public boolean m_6779_(LivingEntity var1);

    @Nullable
    public LivingEntity m_5448_();
}

