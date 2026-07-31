/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.tutorial;

import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.tutorial.FindTreeTutorialStepInstance;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialStepInstance;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class PunchTreeTutorialStepInstance
implements TutorialStepInstance {
    private static final int f_175019_ = 600;
    private static final Component f_120541_ = Component.m_237115_("tutorial.punch_tree.title");
    private static final Component f_120542_ = Component.m_237110_("tutorial.punch_tree.description", Tutorial.m_120592_("attack"));
    private final Tutorial f_120543_;
    private TutorialToast f_120544_;
    private int f_120545_;
    private int f_120546_;

    public PunchTreeTutorialStepInstance(Tutorial p_120549_) {
        this.f_120543_ = p_120549_;
    }

    @Override
    public void m_7737_() {
        LocalPlayer $$0;
        ++this.f_120545_;
        if (!this.f_120543_.m_175028_()) {
            this.f_120543_.m_120588_(TutorialSteps.NONE);
            return;
        }
        if (this.f_120545_ == 1 && ($$0 = this.f_120543_.m_120597_().f_91074_) != null) {
            if ($$0.m_150109_().m_204075_(ItemTags.f_13182_)) {
                this.f_120543_.m_120588_(TutorialSteps.CRAFT_PLANKS);
                return;
            }
            if (FindTreeTutorialStepInstance.m_120503_($$0)) {
                this.f_120543_.m_120588_(TutorialSteps.CRAFT_PLANKS);
                return;
            }
        }
        if ((this.f_120545_ >= 600 || this.f_120546_ > 3) && this.f_120544_ == null) {
            this.f_120544_ = new TutorialToast(TutorialToast.Icons.TREE, f_120541_, f_120542_, true);
            this.f_120543_.m_120597_().m_91300_().m_94922_(this.f_120544_);
        }
    }

    @Override
    public void m_7736_() {
        if (this.f_120544_ != null) {
            this.f_120544_.m_94968_();
            this.f_120544_ = null;
        }
    }

    @Override
    public void m_7464_(ClientLevel p_120554_, BlockPos p_120555_, BlockState p_120556_, float p_120557_) {
        boolean $$4 = p_120556_.m_204336_(BlockTags.f_13106_);
        if ($$4 && p_120557_ > 0.0f) {
            if (this.f_120544_ != null) {
                this.f_120544_.m_94962_(p_120557_);
            }
            if (p_120557_ >= 1.0f) {
                this.f_120543_.m_120588_(TutorialSteps.OPEN_INVENTORY);
            }
        } else if (this.f_120544_ != null) {
            this.f_120544_.m_94962_(0.0f);
        } else if ($$4) {
            ++this.f_120546_;
        }
    }

    @Override
    public void m_6967_(ItemStack p_120552_) {
        if (p_120552_.m_204117_(ItemTags.f_13182_)) {
            this.f_120543_.m_120588_(TutorialSteps.CRAFT_PLANKS);
            return;
        }
    }
}

