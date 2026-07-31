/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.tutorial;

import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialStepInstance;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CraftPlanksTutorialStep
implements TutorialStepInstance {
    private static final int f_175011_ = 1200;
    private static final Component f_120460_ = Component.m_237115_("tutorial.craft_planks.title");
    private static final Component f_120461_ = Component.m_237115_("tutorial.craft_planks.description");
    private final Tutorial f_120462_;
    private TutorialToast f_120463_;
    private int f_120464_;

    public CraftPlanksTutorialStep(Tutorial p_120467_) {
        this.f_120462_ = p_120467_;
    }

    @Override
    public void m_7737_() {
        LocalPlayer $$0;
        ++this.f_120464_;
        if (!this.f_120462_.m_175028_()) {
            this.f_120462_.m_120588_(TutorialSteps.NONE);
            return;
        }
        if (this.f_120464_ == 1 && ($$0 = this.f_120462_.m_120597_().f_91074_) != null) {
            if ($$0.m_150109_().m_204075_(ItemTags.f_13168_)) {
                this.f_120462_.m_120588_(TutorialSteps.NONE);
                return;
            }
            if (CraftPlanksTutorialStep.m_205662_($$0, ItemTags.f_13168_)) {
                this.f_120462_.m_120588_(TutorialSteps.NONE);
                return;
            }
        }
        if (this.f_120464_ >= 1200 && this.f_120463_ == null) {
            this.f_120463_ = new TutorialToast(TutorialToast.Icons.WOODEN_PLANKS, f_120460_, f_120461_, false);
            this.f_120462_.m_120597_().m_91300_().m_94922_(this.f_120463_);
        }
    }

    @Override
    public void m_7736_() {
        if (this.f_120463_ != null) {
            this.f_120463_.m_94968_();
            this.f_120463_ = null;
        }
    }

    @Override
    public void m_6967_(ItemStack p_120470_) {
        if (p_120470_.m_204117_(ItemTags.f_13168_)) {
            this.f_120462_.m_120588_(TutorialSteps.NONE);
        }
    }

    public static boolean m_205662_(LocalPlayer p_205663_, TagKey<Item> p_205664_) {
        for (Holder<Item> $$2 : Registry.f_122827_.m_206058_(p_205664_)) {
            if (p_205663_.m_108630_().m_13015_(Stats.f_12981_.m_12902_($$2.m_203334_())) <= 0) continue;
            return true;
        }
        return false;
    }
}

