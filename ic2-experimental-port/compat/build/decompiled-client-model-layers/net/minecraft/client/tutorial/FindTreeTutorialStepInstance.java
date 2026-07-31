/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.tutorial;

import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialStepInstance;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class FindTreeTutorialStepInstance
implements TutorialStepInstance {
    private static final int f_175012_ = 6000;
    private static final Component f_120489_ = Component.m_237115_("tutorial.find_tree.title");
    private static final Component f_120490_ = Component.m_237115_("tutorial.find_tree.description");
    private final Tutorial f_120491_;
    private TutorialToast f_120492_;
    private int f_120493_;

    public FindTreeTutorialStepInstance(Tutorial p_120496_) {
        this.f_120491_ = p_120496_;
    }

    @Override
    public void m_7737_() {
        LocalPlayer $$0;
        ++this.f_120493_;
        if (!this.f_120491_.m_175028_()) {
            this.f_120491_.m_120588_(TutorialSteps.NONE);
            return;
        }
        if (this.f_120493_ == 1 && ($$0 = this.f_120491_.m_120597_().f_91074_) != null && (FindTreeTutorialStepInstance.m_235271_($$0) || FindTreeTutorialStepInstance.m_120503_($$0))) {
            this.f_120491_.m_120588_(TutorialSteps.CRAFT_PLANKS);
            return;
        }
        if (this.f_120493_ >= 6000 && this.f_120492_ == null) {
            this.f_120492_ = new TutorialToast(TutorialToast.Icons.TREE, f_120489_, f_120490_, false);
            this.f_120491_.m_120597_().m_91300_().m_94922_(this.f_120492_);
        }
    }

    @Override
    public void m_7736_() {
        if (this.f_120492_ != null) {
            this.f_120492_.m_94968_();
            this.f_120492_ = null;
        }
    }

    @Override
    public void m_7554_(ClientLevel p_120501_, HitResult p_120502_) {
        BlockState $$2;
        if (p_120502_.m_6662_() == HitResult.Type.BLOCK && ($$2 = p_120501_.m_8055_(((BlockHitResult)p_120502_).m_82425_())).m_204336_(BlockTags.f_215821_)) {
            this.f_120491_.m_120588_(TutorialSteps.PUNCH_TREE);
        }
    }

    @Override
    public void m_6967_(ItemStack p_120499_) {
        if (p_120499_.m_204117_(ItemTags.f_215863_)) {
            this.f_120491_.m_120588_(TutorialSteps.CRAFT_PLANKS);
        }
    }

    private static boolean m_235271_(LocalPlayer p_235272_) {
        return p_235272_.m_150109_().m_216874_(p_235270_ -> p_235270_.m_204117_(ItemTags.f_215863_));
    }

    public static boolean m_120503_(LocalPlayer p_120504_) {
        for (Holder<Block> $$1 : Registry.f_122824_.m_206058_(BlockTags.f_215821_)) {
            Block $$2 = $$1.m_203334_();
            if (p_120504_.m_108630_().m_13015_(Stats.f_12949_.m_12902_($$2)) <= 0) continue;
            return true;
        }
        return false;
    }
}

