/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.tutorial;

import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialStepInstance;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.network.chat.Component;

public class OpenInventoryTutorialStep
implements TutorialStepInstance {
    private static final int f_175018_ = 600;
    private static final Component f_120530_ = Component.m_237115_("tutorial.open_inventory.title");
    private static final Component f_120531_ = Component.m_237110_("tutorial.open_inventory.description", Tutorial.m_120592_("inventory"));
    private final Tutorial f_120532_;
    private TutorialToast f_120533_;
    private int f_120534_;

    public OpenInventoryTutorialStep(Tutorial p_120537_) {
        this.f_120532_ = p_120537_;
    }

    @Override
    public void m_7737_() {
        ++this.f_120534_;
        if (!this.f_120532_.m_175028_()) {
            this.f_120532_.m_120588_(TutorialSteps.NONE);
            return;
        }
        if (this.f_120534_ >= 600 && this.f_120533_ == null) {
            this.f_120533_ = new TutorialToast(TutorialToast.Icons.RECIPE_BOOK, f_120530_, f_120531_, false);
            this.f_120532_.m_120597_().m_91300_().m_94922_(this.f_120533_);
        }
    }

    @Override
    public void m_7736_() {
        if (this.f_120533_ != null) {
            this.f_120533_.m_94968_();
            this.f_120533_ = null;
        }
    }

    @Override
    public void m_7744_() {
        this.f_120532_.m_120588_(TutorialSteps.CRAFT_PLANKS);
    }
}

