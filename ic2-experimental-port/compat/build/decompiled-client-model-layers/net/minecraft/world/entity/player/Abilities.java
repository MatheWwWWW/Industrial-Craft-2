/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.player;

import net.minecraft.nbt.CompoundTag;

public class Abilities {
    public boolean f_35934_;
    public boolean f_35935_;
    public boolean f_35936_;
    public boolean f_35937_;
    public boolean f_35938_ = true;
    private float f_35939_ = 0.05f;
    private float f_35940_ = 0.1f;

    public void m_35945_(CompoundTag p_35946_) {
        CompoundTag $$1 = new CompoundTag();
        $$1.m_128379_("invulnerable", this.f_35934_);
        $$1.m_128379_("flying", this.f_35935_);
        $$1.m_128379_("mayfly", this.f_35936_);
        $$1.m_128379_("instabuild", this.f_35937_);
        $$1.m_128379_("mayBuild", this.f_35938_);
        $$1.m_128350_("flySpeed", this.f_35939_);
        $$1.m_128350_("walkSpeed", this.f_35940_);
        p_35946_.m_128365_("abilities", $$1);
    }

    public void m_35950_(CompoundTag p_35951_) {
        if (p_35951_.m_128425_("abilities", 10)) {
            CompoundTag $$1 = p_35951_.m_128469_("abilities");
            this.f_35934_ = $$1.m_128471_("invulnerable");
            this.f_35935_ = $$1.m_128471_("flying");
            this.f_35936_ = $$1.m_128471_("mayfly");
            this.f_35937_ = $$1.m_128471_("instabuild");
            if ($$1.m_128425_("flySpeed", 99)) {
                this.f_35939_ = $$1.m_128457_("flySpeed");
                this.f_35940_ = $$1.m_128457_("walkSpeed");
            }
            if ($$1.m_128425_("mayBuild", 1)) {
                this.f_35938_ = $$1.m_128471_("mayBuild");
            }
        }
    }

    public float m_35942_() {
        return this.f_35939_;
    }

    public void m_35943_(float p_35944_) {
        this.f_35939_ = p_35944_;
    }

    public float m_35947_() {
        return this.f_35940_;
    }

    public void m_35948_(float p_35949_) {
        this.f_35940_ = p_35949_;
    }
}

