/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public interface ItemSteerable {
    public boolean m_6746_();

    public void m_7760_(Vec3 var1);

    public float m_6748_();

    default public boolean m_20854_(Mob p_20855_, ItemBasedSteering p_20856_, Vec3 p_20857_) {
        if (!p_20855_.m_6084_()) {
            return false;
        }
        Entity $$3 = p_20855_.m_6688_();
        if (!p_20855_.m_20160_() || !($$3 instanceof Player)) {
            p_20855_.f_19793_ = 0.5f;
            p_20855_.f_20887_ = 0.02f;
            this.m_7760_(p_20857_);
            return false;
        }
        p_20855_.m_146922_($$3.m_146908_());
        p_20855_.f_19859_ = p_20855_.m_146908_();
        p_20855_.m_146926_($$3.m_146909_() * 0.5f);
        p_20855_.m_19915_(p_20855_.m_146908_(), p_20855_.m_146909_());
        p_20855_.f_20883_ = p_20855_.m_146908_();
        p_20855_.f_20885_ = p_20855_.m_146908_();
        p_20855_.f_19793_ = 1.0f;
        p_20855_.f_20887_ = p_20855_.m_6113_() * 0.1f;
        if (p_20856_.f_20834_ && p_20856_.f_20835_++ > p_20856_.f_20836_) {
            p_20856_.f_20834_ = false;
        }
        if (p_20855_.m_6109_()) {
            float $$4 = this.m_6748_();
            if (p_20856_.f_20834_) {
                $$4 += $$4 * 1.15f * Mth.m_14031_((float)p_20856_.f_20835_ / (float)p_20856_.f_20836_ * (float)Math.PI);
            }
            p_20855_.m_7910_($$4);
            this.m_7760_(new Vec3(0.0, 0.0, 1.0));
            p_20855_.f_20903_ = 0;
        } else {
            p_20855_.m_21043_(p_20855_, false);
            p_20855_.m_20256_(Vec3.f_82478_);
        }
        p_20855_.m_146872_();
        return true;
    }
}

