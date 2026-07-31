/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.realms.RealmsScreen;

public class RealmsSettingsScreen
extends RealmsScreen {
    private static final int f_167508_ = 212;
    private static final Component f_89819_ = Component.m_237115_("mco.configure.world.name");
    private static final Component f_89820_ = Component.m_237115_("mco.configure.world.description");
    private final RealmsConfigureWorldScreen f_89821_;
    private final RealmsServer f_89822_;
    private Button f_89823_;
    private EditBox f_89824_;
    private EditBox f_89825_;

    public RealmsSettingsScreen(RealmsConfigureWorldScreen p_89829_, RealmsServer p_89830_) {
        super(Component.m_237115_("mco.configure.world.settings.title"));
        this.f_89821_ = p_89829_;
        this.f_89822_ = p_89830_;
    }

    @Override
    public void m_86600_() {
        this.f_89825_.m_94120_();
        this.f_89824_.m_94120_();
        this.f_89823_.f_93623_ = !this.f_89825_.m_94155_().trim().isEmpty();
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        int $$0 = this.f_96543_ / 2 - 106;
        this.f_89823_ = this.m_142416_(new Button($$0 - 2, RealmsSettingsScreen.m_120774_(12), 106, 20, Component.m_237115_("mco.configure.world.buttons.done"), p_89847_ -> this.m_89831_()));
        this.m_142416_(new Button(this.f_96543_ / 2 + 2, RealmsSettingsScreen.m_120774_(12), 106, 20, CommonComponents.f_130656_, p_89845_ -> this.f_96541_.m_91152_(this.f_89821_)));
        String $$1 = this.f_89822_.f_87477_ == RealmsServer.State.OPEN ? "mco.configure.world.buttons.close" : "mco.configure.world.buttons.open";
        Button $$2 = new Button(this.f_96543_ / 2 - 53, RealmsSettingsScreen.m_120774_(0), 106, 20, Component.m_237115_($$1), p_89842_ -> {
            if (this.f_89822_.f_87477_ == RealmsServer.State.OPEN) {
                MutableComponent $$1 = Component.m_237115_("mco.configure.world.close.question.line1");
                MutableComponent $$2 = Component.m_237115_("mco.configure.world.close.question.line2");
                this.f_96541_.m_91152_(new RealmsLongConfirmationScreen(p_167510_ -> {
                    if (p_167510_) {
                        this.f_89821_.m_88452_(this);
                    } else {
                        this.f_96541_.m_91152_(this);
                    }
                }, RealmsLongConfirmationScreen.Type.Info, $$1, $$2, true));
            } else {
                this.f_89821_.m_88459_(false, this);
            }
        });
        this.m_142416_($$2);
        this.f_89825_ = new EditBox(this.f_96541_.f_91062_, $$0, RealmsSettingsScreen.m_120774_(4), 212, 20, null, Component.m_237115_("mco.configure.world.name"));
        this.f_89825_.m_94199_(32);
        this.f_89825_.m_94144_(this.f_89822_.m_87512_());
        this.m_7787_(this.f_89825_);
        this.m_94725_(this.f_89825_);
        this.f_89824_ = new EditBox(this.f_96541_.f_91062_, $$0, RealmsSettingsScreen.m_120774_(8), 212, 20, null, Component.m_237115_("mco.configure.world.description"));
        this.f_89824_.m_94199_(32);
        this.f_89824_.m_94144_(this.f_89822_.m_87494_());
        this.m_7787_(this.f_89824_);
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_7933_(int p_89833_, int p_89834_, int p_89835_) {
        if (p_89833_ == 256) {
            this.f_96541_.m_91152_(this.f_89821_);
            return true;
        }
        return super.m_7933_(p_89833_, p_89834_, p_89835_);
    }

    @Override
    public void m_6305_(PoseStack p_89837_, int p_89838_, int p_89839_, float p_89840_) {
        this.m_7333_(p_89837_);
        RealmsSettingsScreen.m_93215_(p_89837_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 17, 0xFFFFFF);
        this.f_96547_.m_92889_(p_89837_, f_89819_, this.f_96543_ / 2 - 106, RealmsSettingsScreen.m_120774_(3), 0xA0A0A0);
        this.f_96547_.m_92889_(p_89837_, f_89820_, this.f_96543_ / 2 - 106, RealmsSettingsScreen.m_120774_(7), 0xA0A0A0);
        this.f_89825_.m_6305_(p_89837_, p_89838_, p_89839_, p_89840_);
        this.f_89824_.m_6305_(p_89837_, p_89838_, p_89839_, p_89840_);
        super.m_6305_(p_89837_, p_89838_, p_89839_, p_89840_);
    }

    public void m_89831_() {
        this.f_89821_.m_88454_(this.f_89825_.m_94155_(), this.f_89824_.m_94155_());
    }
}

