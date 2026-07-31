/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.advancements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

final class AdvancementTabType
extends Enum<AdvancementTabType> {
    public static final /* enum */ AdvancementTabType ABOVE = new AdvancementTabType(0, 0, 28, 32, 8);
    public static final /* enum */ AdvancementTabType BELOW = new AdvancementTabType(84, 0, 28, 32, 8);
    public static final /* enum */ AdvancementTabType LEFT = new AdvancementTabType(0, 64, 32, 28, 5);
    public static final /* enum */ AdvancementTabType RIGHT = new AdvancementTabType(96, 64, 32, 28, 5);
    private final int f_97195_;
    private final int f_97196_;
    private final int f_97197_;
    private final int f_97198_;
    private final int f_97199_;
    private static final /* synthetic */ AdvancementTabType[] $VALUES;

    public static AdvancementTabType[] values() {
        return (AdvancementTabType[])$VALUES.clone();
    }

    public static AdvancementTabType valueOf(String p_97235_) {
        return Enum.valueOf(AdvancementTabType.class, p_97235_);
    }

    private AdvancementTabType(int p_97205_, int p_97206_, int p_97207_, int p_97208_, int p_97209_) {
        this.f_97195_ = p_97205_;
        this.f_97196_ = p_97206_;
        this.f_97197_ = p_97207_;
        this.f_97198_ = p_97208_;
        this.f_97199_ = p_97209_;
    }

    public int m_97210_() {
        return this.f_97199_;
    }

    public void m_97225_(PoseStack p_97226_, GuiComponent p_97227_, int p_97228_, int p_97229_, boolean p_97230_, int p_97231_) {
        int $$6 = this.f_97195_;
        if (p_97231_ > 0) {
            $$6 += this.f_97197_;
        }
        if (p_97231_ == this.f_97199_ - 1) {
            $$6 += this.f_97197_;
        }
        int $$7 = p_97230_ ? this.f_97196_ + this.f_97198_ : this.f_97196_;
        p_97227_.m_93228_(p_97226_, p_97228_ + this.m_97211_(p_97231_), p_97229_ + this.m_97232_(p_97231_), $$6, $$7, this.f_97197_, this.f_97198_);
    }

    public void m_97219_(int p_97220_, int p_97221_, int p_97222_, ItemRenderer p_97223_, ItemStack p_97224_) {
        int $$5 = p_97220_ + this.m_97211_(p_97222_);
        int $$6 = p_97221_ + this.m_97232_(p_97222_);
        switch (this) {
            case ABOVE: {
                $$5 += 6;
                $$6 += 9;
                break;
            }
            case BELOW: {
                $$5 += 6;
                $$6 += 6;
                break;
            }
            case LEFT: {
                $$5 += 10;
                $$6 += 5;
                break;
            }
            case RIGHT: {
                $$5 += 6;
                $$6 += 5;
            }
        }
        p_97223_.m_115218_(p_97224_, $$5, $$6);
    }

    public int m_97211_(int p_97212_) {
        switch (this) {
            case ABOVE: {
                return (this.f_97197_ + 4) * p_97212_;
            }
            case BELOW: {
                return (this.f_97197_ + 4) * p_97212_;
            }
            case LEFT: {
                return -this.f_97197_ + 4;
            }
            case RIGHT: {
                return 248;
            }
        }
        throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
    }

    public int m_97232_(int p_97233_) {
        switch (this) {
            case ABOVE: {
                return -this.f_97198_ + 4;
            }
            case BELOW: {
                return 136;
            }
            case LEFT: {
                return this.f_97198_ * p_97233_;
            }
            case RIGHT: {
                return this.f_97198_ * p_97233_;
            }
        }
        throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
    }

    public boolean m_97213_(int p_97214_, int p_97215_, int p_97216_, double p_97217_, double p_97218_) {
        int $$5 = p_97214_ + this.m_97211_(p_97216_);
        int $$6 = p_97215_ + this.m_97232_(p_97216_);
        return p_97217_ > (double)$$5 && p_97217_ < (double)($$5 + this.f_97197_) && p_97218_ > (double)$$6 && p_97218_ < (double)($$6 + this.f_97198_);
    }

    private static /* synthetic */ AdvancementTabType[] m_169541_() {
        return new AdvancementTabType[]{ABOVE, BELOW, LEFT, RIGHT};
    }

    static {
        $VALUES = AdvancementTabType.m_169541_();
    }
}

