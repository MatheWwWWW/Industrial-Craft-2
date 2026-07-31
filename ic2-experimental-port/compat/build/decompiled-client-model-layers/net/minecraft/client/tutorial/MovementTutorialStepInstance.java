/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.tutorial;

import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.player.Input;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialStepInstance;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.network.chat.Component;

public class MovementTutorialStepInstance
implements TutorialStepInstance {
    private static final int f_175013_ = 40;
    private static final int f_175014_ = 40;
    private static final int f_175015_ = 100;
    private static final int f_175016_ = 20;
    private static final int f_175017_ = -1;
    private static final Component f_120506_ = Component.m_237110_("tutorial.move.title", Tutorial.m_120592_("forward"), Tutorial.m_120592_("left"), Tutorial.m_120592_("back"), Tutorial.m_120592_("right"));
    private static final Component f_120507_ = Component.m_237110_("tutorial.move.description", Tutorial.m_120592_("jump"));
    private static final Component f_120508_ = Component.m_237115_("tutorial.look.title");
    private static final Component f_120509_ = Component.m_237115_("tutorial.look.description");
    private final Tutorial f_120510_;
    private TutorialToast f_120511_;
    private TutorialToast f_120512_;
    private int f_120513_;
    private int f_120514_;
    private int f_120515_;
    private boolean f_120516_;
    private boolean f_120517_;
    private int f_120518_ = -1;
    private int f_120519_ = -1;

    public MovementTutorialStepInstance(Tutorial p_120522_) {
        this.f_120510_ = p_120522_;
    }

    @Override
    public void m_7737_() {
        ++this.f_120513_;
        if (this.f_120516_) {
            ++this.f_120514_;
            this.f_120516_ = false;
        }
        if (this.f_120517_) {
            ++this.f_120515_;
            this.f_120517_ = false;
        }
        if (this.f_120518_ == -1 && this.f_120514_ > 40) {
            if (this.f_120511_ != null) {
                this.f_120511_.m_94968_();
                this.f_120511_ = null;
            }
            this.f_120518_ = this.f_120513_;
        }
        if (this.f_120519_ == -1 && this.f_120515_ > 40) {
            if (this.f_120512_ != null) {
                this.f_120512_.m_94968_();
                this.f_120512_ = null;
            }
            this.f_120519_ = this.f_120513_;
        }
        if (this.f_120518_ != -1 && this.f_120519_ != -1) {
            if (this.f_120510_.m_175028_()) {
                this.f_120510_.m_120588_(TutorialSteps.FIND_TREE);
            } else {
                this.f_120510_.m_120588_(TutorialSteps.NONE);
            }
        }
        if (this.f_120511_ != null) {
            this.f_120511_.m_94962_((float)this.f_120514_ / 40.0f);
        }
        if (this.f_120512_ != null) {
            this.f_120512_.m_94962_((float)this.f_120515_ / 40.0f);
        }
        if (this.f_120513_ >= 100) {
            if (this.f_120518_ == -1 && this.f_120511_ == null) {
                this.f_120511_ = new TutorialToast(TutorialToast.Icons.MOVEMENT_KEYS, f_120506_, f_120507_, true);
                this.f_120510_.m_120597_().m_91300_().m_94922_(this.f_120511_);
            } else if (this.f_120518_ != -1 && this.f_120513_ - this.f_120518_ >= 20 && this.f_120519_ == -1 && this.f_120512_ == null) {
                this.f_120512_ = new TutorialToast(TutorialToast.Icons.MOUSE, f_120508_, f_120509_, true);
                this.f_120510_.m_120597_().m_91300_().m_94922_(this.f_120512_);
            }
        }
    }

    @Override
    public void m_7736_() {
        if (this.f_120511_ != null) {
            this.f_120511_.m_94968_();
            this.f_120511_ = null;
        }
        if (this.f_120512_ != null) {
            this.f_120512_.m_94968_();
            this.f_120512_ = null;
        }
    }

    @Override
    public void m_6484_(Input p_120528_) {
        if (p_120528_.f_108568_ || p_120528_.f_108569_ || p_120528_.f_108570_ || p_120528_.f_108571_ || p_120528_.f_108572_) {
            this.f_120516_ = true;
        }
    }

    @Override
    public void m_6420_(double p_120525_, double p_120526_) {
        if (Math.abs(p_120525_) > 0.01 || Math.abs(p_120526_) > 0.01) {
            this.f_120517_ = true;
        }
    }
}

