/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.tutorial;

import javax.annotation.Nullable;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BundleTutorial {
    private final Tutorial f_174999_;
    private final Options f_175000_;
    @Nullable
    private TutorialToast f_175001_;

    public BundleTutorial(Tutorial p_175003_, Options p_175004_) {
        this.f_174999_ = p_175003_;
        this.f_175000_ = p_175004_;
    }

    private void m_175005_() {
        if (this.f_175001_ != null) {
            this.f_174999_.m_120570_(this.f_175001_);
        }
        MutableComponent $$0 = Component.m_237115_("tutorial.bundleInsert.title");
        MutableComponent $$1 = Component.m_237115_("tutorial.bundleInsert.description");
        this.f_175001_ = new TutorialToast(TutorialToast.Icons.RIGHT_CLICK, $$0, $$1, true);
        this.f_174999_.m_120572_(this.f_175001_, 160);
    }

    private void m_175010_() {
        if (this.f_175001_ != null) {
            this.f_174999_.m_120570_(this.f_175001_);
            this.f_175001_ = null;
        }
        if (!this.f_175000_.f_168405_) {
            this.f_175000_.f_168405_ = true;
            this.f_175000_.m_92169_();
        }
    }

    public void m_175006_(ItemStack p_175007_, ItemStack p_175008_, ClickAction p_175009_) {
        if (this.f_175000_.f_168405_) {
            return;
        }
        if (!p_175007_.m_41619_() && p_175008_.m_150930_(Items.f_151058_)) {
            if (p_175009_ == ClickAction.PRIMARY) {
                this.m_175005_();
            } else if (p_175009_ == ClickAction.SECONDARY) {
                this.m_175010_();
            }
        } else if (p_175007_.m_150930_(Items.f_151058_) && !p_175008_.m_41619_() && p_175009_ == ClickAction.SECONDARY) {
            this.m_175010_();
        }
    }
}

