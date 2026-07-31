/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.tutorial;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.Input;
import net.minecraft.client.tutorial.BundleTutorial;
import net.minecraft.client.tutorial.TutorialStepInstance;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class Tutorial {
    private final Minecraft f_120559_;
    @Nullable
    private TutorialStepInstance f_120560_;
    private final List<TimedToast> f_120561_ = Lists.newArrayList();
    private final BundleTutorial f_175020_;

    public Tutorial(Minecraft p_175022_, Options p_175023_) {
        this.f_120559_ = p_175022_;
        this.f_175020_ = new BundleTutorial(this, p_175023_);
    }

    public void m_120586_(Input p_120587_) {
        if (this.f_120560_ != null) {
            this.f_120560_.m_6484_(p_120587_);
        }
    }

    public void m_120565_(double p_120566_, double p_120567_) {
        if (this.f_120560_ != null) {
            this.f_120560_.m_6420_(p_120566_, p_120567_);
        }
    }

    public void m_120578_(@Nullable ClientLevel p_120579_, @Nullable HitResult p_120580_) {
        if (this.f_120560_ != null && p_120580_ != null && p_120579_ != null) {
            this.f_120560_.m_7554_(p_120579_, p_120580_);
        }
    }

    public void m_120581_(ClientLevel p_120582_, BlockPos p_120583_, BlockState p_120584_, float p_120585_) {
        if (this.f_120560_ != null) {
            this.f_120560_.m_7464_(p_120582_, p_120583_, p_120584_, p_120585_);
        }
    }

    public void m_120564_() {
        if (this.f_120560_ != null) {
            this.f_120560_.m_7744_();
        }
    }

    public void m_120568_(ItemStack p_120569_) {
        if (this.f_120560_ != null) {
            this.f_120560_.m_6967_(p_120569_);
        }
    }

    public void m_120594_() {
        if (this.f_120560_ == null) {
            return;
        }
        this.f_120560_.m_7736_();
        this.f_120560_ = null;
    }

    public void m_120595_() {
        if (this.f_120560_ != null) {
            this.m_120594_();
        }
        this.f_120560_ = this.f_120559_.f_91066_.f_92030_.m_120640_(this);
    }

    public void m_120572_(TutorialToast p_120573_, int p_120574_) {
        this.f_120561_.add(new TimedToast(p_120573_, p_120574_));
        this.f_120559_.m_91300_().m_94922_(p_120573_);
    }

    public void m_120570_(TutorialToast p_120571_) {
        this.f_120561_.removeIf(p_120577_ -> p_120577_.f_120599_ == p_120571_);
        p_120571_.m_94968_();
    }

    public void m_120596_() {
        this.f_120561_.removeIf(TimedToast::m_120609_);
        if (this.f_120560_ != null) {
            if (this.f_120559_.f_91073_ != null) {
                this.f_120560_.m_7737_();
            } else {
                this.m_120594_();
            }
        } else if (this.f_120559_.f_91073_ != null) {
            this.m_120595_();
        }
    }

    public void m_120588_(TutorialSteps p_120589_) {
        this.f_120559_.f_91066_.f_92030_ = p_120589_;
        this.f_120559_.f_91066_.m_92169_();
        if (this.f_120560_ != null) {
            this.f_120560_.m_7736_();
            this.f_120560_ = p_120589_.m_120640_(this);
        }
    }

    public Minecraft m_120597_() {
        return this.f_120559_;
    }

    public boolean m_175028_() {
        if (this.f_120559_.f_91072_ == null) {
            return false;
        }
        return this.f_120559_.f_91072_.m_105295_() == GameType.SURVIVAL;
    }

    public static Component m_120592_(String p_120593_) {
        return Component.m_237117_("key." + p_120593_).m_130940_(ChatFormatting.BOLD);
    }

    public void m_175024_(ItemStack p_175025_, ItemStack p_175026_, ClickAction p_175027_) {
        this.f_175020_.m_175006_(p_175025_, p_175026_, p_175027_);
    }

    static final class TimedToast {
        final TutorialToast f_120599_;
        private final int f_120600_;
        private int f_120601_;

        TimedToast(TutorialToast p_120603_, int p_120604_) {
            this.f_120599_ = p_120603_;
            this.f_120600_ = p_120604_;
        }

        private boolean m_120609_() {
            this.f_120599_.m_94962_(Math.min((float)(++this.f_120601_) / (float)this.f_120600_, 1.0f));
            if (this.f_120601_ > this.f_120600_) {
                this.f_120599_.m_94968_();
                return true;
            }
            return false;
        }
    }
}

