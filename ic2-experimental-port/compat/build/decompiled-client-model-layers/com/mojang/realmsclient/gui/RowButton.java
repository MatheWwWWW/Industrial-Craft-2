/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.realms.RealmsObjectSelectionList;

public abstract class RowButton {
    public final int f_88007_;
    public final int f_88008_;
    public final int f_88009_;
    public final int f_88010_;

    public RowButton(int p_88012_, int p_88013_, int p_88014_, int p_88015_) {
        this.f_88007_ = p_88012_;
        this.f_88008_ = p_88013_;
        this.f_88009_ = p_88014_;
        this.f_88010_ = p_88015_;
    }

    public void m_88018_(PoseStack p_88019_, int p_88020_, int p_88021_, int p_88022_, int p_88023_) {
        int $$5 = p_88020_ + this.f_88009_;
        int $$6 = p_88021_ + this.f_88010_;
        boolean $$7 = p_88022_ >= $$5 && p_88022_ <= $$5 + this.f_88007_ && p_88023_ >= $$6 && p_88023_ <= $$6 + this.f_88008_;
        this.m_7537_(p_88019_, $$5, $$6, $$7);
    }

    protected abstract void m_7537_(PoseStack var1, int var2, int var3, boolean var4);

    public int m_88016_() {
        return this.f_88009_ + this.f_88007_;
    }

    public int m_88043_() {
        return this.f_88010_ + this.f_88008_;
    }

    public abstract void m_7516_(int var1);

    public static void m_88028_(PoseStack p_88029_, List<RowButton> p_88030_, RealmsObjectSelectionList<?> p_88031_, int p_88032_, int p_88033_, int p_88034_, int p_88035_) {
        for (RowButton $$7 : p_88030_) {
            if (p_88031_.m_5759_() <= $$7.m_88016_()) continue;
            $$7.m_88018_(p_88029_, p_88032_, p_88033_, p_88034_, p_88035_);
        }
    }

    public static void m_88036_(RealmsObjectSelectionList<?> p_88037_, ObjectSelectionList.Entry<?> p_88038_, List<RowButton> p_88039_, int p_88040_, double p_88041_, double p_88042_) {
        int $$6;
        if (p_88040_ == 0 && ($$6 = p_88037_.m_6702_().indexOf(p_88038_)) > -1) {
            p_88037_.m_7109_($$6);
            int $$7 = p_88037_.m_5747_();
            int $$8 = p_88037_.m_7610_($$6);
            int $$9 = (int)(p_88041_ - (double)$$7);
            int $$10 = (int)(p_88042_ - (double)$$8);
            for (RowButton $$11 : p_88039_) {
                if ($$9 < $$11.f_88009_ || $$9 > $$11.m_88016_() || $$10 < $$11.f_88010_ || $$10 > $$11.m_88043_()) continue;
                $$11.m_7516_($$6);
            }
        }
    }
}

