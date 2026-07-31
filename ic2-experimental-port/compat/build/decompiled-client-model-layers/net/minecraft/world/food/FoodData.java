/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.food;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;

public class FoodData {
    private int f_38696_ = 20;
    private float f_38697_ = 5.0f;
    private float f_38698_;
    private int f_38699_;
    private int f_38700_ = 20;

    public void m_38707_(int p_38708_, float p_38709_) {
        this.f_38696_ = Math.min(p_38708_ + this.f_38696_, 20);
        this.f_38697_ = Math.min(this.f_38697_ + (float)p_38708_ * p_38709_ * 2.0f, (float)this.f_38696_);
    }

    public void m_38712_(Item p_38713_, ItemStack p_38714_) {
        if (p_38713_.m_41472_()) {
            FoodProperties $$2 = p_38713_.m_41473_();
            this.m_38707_($$2.m_38744_(), $$2.m_38745_());
        }
    }

    public void m_38710_(Player p_38711_) {
        boolean $$2;
        Difficulty $$1 = p_38711_.f_19853_.m_46791_();
        this.f_38700_ = this.f_38696_;
        if (this.f_38698_ > 4.0f) {
            this.f_38698_ -= 4.0f;
            if (this.f_38697_ > 0.0f) {
                this.f_38697_ = Math.max(this.f_38697_ - 1.0f, 0.0f);
            } else if ($$1 != Difficulty.PEACEFUL) {
                this.f_38696_ = Math.max(this.f_38696_ - 1, 0);
            }
        }
        if (($$2 = p_38711_.f_19853_.m_46469_().m_46207_(GameRules.f_46139_)) && this.f_38697_ > 0.0f && p_38711_.m_36325_() && this.f_38696_ >= 20) {
            ++this.f_38699_;
            if (this.f_38699_ >= 10) {
                float $$3 = Math.min(this.f_38697_, 6.0f);
                p_38711_.m_5634_($$3 / 6.0f);
                this.m_38703_($$3);
                this.f_38699_ = 0;
            }
        } else if ($$2 && this.f_38696_ >= 18 && p_38711_.m_36325_()) {
            ++this.f_38699_;
            if (this.f_38699_ >= 80) {
                p_38711_.m_5634_(1.0f);
                this.m_38703_(6.0f);
                this.f_38699_ = 0;
            }
        } else if (this.f_38696_ <= 0) {
            ++this.f_38699_;
            if (this.f_38699_ >= 80) {
                if (p_38711_.m_21223_() > 10.0f || $$1 == Difficulty.HARD || p_38711_.m_21223_() > 1.0f && $$1 == Difficulty.NORMAL) {
                    p_38711_.m_6469_(DamageSource.f_19313_, 1.0f);
                }
                this.f_38699_ = 0;
            }
        } else {
            this.f_38699_ = 0;
        }
    }

    public void m_38715_(CompoundTag p_38716_) {
        if (p_38716_.m_128425_("foodLevel", 99)) {
            this.f_38696_ = p_38716_.m_128451_("foodLevel");
            this.f_38699_ = p_38716_.m_128451_("foodTickTimer");
            this.f_38697_ = p_38716_.m_128457_("foodSaturationLevel");
            this.f_38698_ = p_38716_.m_128457_("foodExhaustionLevel");
        }
    }

    public void m_38719_(CompoundTag p_38720_) {
        p_38720_.m_128405_("foodLevel", this.f_38696_);
        p_38720_.m_128405_("foodTickTimer", this.f_38699_);
        p_38720_.m_128350_("foodSaturationLevel", this.f_38697_);
        p_38720_.m_128350_("foodExhaustionLevel", this.f_38698_);
    }

    public int m_38702_() {
        return this.f_38696_;
    }

    public int m_150377_() {
        return this.f_38700_;
    }

    public boolean m_38721_() {
        return this.f_38696_ < 20;
    }

    public void m_38703_(float p_38704_) {
        this.f_38698_ = Math.min(this.f_38698_ + p_38704_, 40.0f);
    }

    public float m_150380_() {
        return this.f_38698_;
    }

    public float m_38722_() {
        return this.f_38697_;
    }

    public void m_38705_(int p_38706_) {
        this.f_38696_ = p_38706_;
    }

    public void m_38717_(float p_38718_) {
        this.f_38697_ = p_38718_;
    }

    public void m_150378_(float p_150379_) {
        this.f_38698_ = p_150379_;
    }
}

