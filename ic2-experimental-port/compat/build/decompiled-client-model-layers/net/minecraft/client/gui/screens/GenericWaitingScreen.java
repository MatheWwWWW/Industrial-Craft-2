/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.LoadingDotsText;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class GenericWaitingScreen
extends Screen {
    private static final int f_238805_ = 80;
    private static final int f_238592_ = 120;
    private static final int f_238731_ = 360;
    @Nullable
    private final Component f_240231_;
    private final Component f_238770_;
    private final Runnable f_238734_;
    @Nullable
    private MultiLineLabel f_238521_;
    private Button f_238767_;
    private int f_240233_;

    public static GenericWaitingScreen m_240309_(Component p_240310_, Component p_240311_, Runnable p_240312_) {
        return new GenericWaitingScreen(p_240310_, null, p_240311_, p_240312_, 0);
    }

    public static GenericWaitingScreen m_240290_(Component p_240291_, Component p_240292_, Component p_240293_, Runnable p_240294_) {
        return new GenericWaitingScreen(p_240291_, p_240292_, p_240293_, p_240294_, 20);
    }

    protected GenericWaitingScreen(Component p_240300_, @Nullable Component p_240301_, Component p_240302_, Runnable p_240303_, int p_240304_) {
        super(p_240300_);
        this.f_240231_ = p_240301_;
        this.f_238770_ = p_240302_;
        this.f_238734_ = p_240303_;
        this.f_240233_ = p_240304_;
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        if (this.f_240231_ != null) {
            this.f_238521_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_240231_, 360);
        }
        int $$0 = 150;
        int $$1 = 20;
        int $$2 = this.f_238521_ != null ? this.f_238521_.m_5770_() : 1;
        int $$3 = Math.max($$2, 5) * this.f_96547_.f_92710_;
        int $$4 = Math.min(120 + $$3, this.f_96544_ - 40);
        this.f_238767_ = this.m_142416_(new Button((this.f_96543_ - 150) / 2, $$4, 150, 20, this.f_238770_, p_239908_ -> this.m_7379_()));
    }

    @Override
    public void m_86600_() {
        if (this.f_240233_ > 0) {
            --this.f_240233_;
        }
        this.f_238767_.f_93623_ = this.f_240233_ == 0;
    }

    @Override
    public void m_6305_(PoseStack p_239718_, int p_239719_, int p_239720_, float p_239721_) {
        this.m_7333_(p_239718_);
        GenericWaitingScreen.m_93215_(p_239718_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 80, 0xFFFFFF);
        if (this.f_238521_ == null) {
            String $$4 = LoadingDotsText.m_232744_(Util.m_137550_());
            GenericWaitingScreen.m_93208_(p_239718_, this.f_96547_, $$4, this.f_96543_ / 2, 120, 0xA0A0A0);
        } else {
            this.f_238521_.m_6276_(p_239718_, this.f_96543_ / 2, 120);
        }
        super.m_6305_(p_239718_, p_239719_, p_239720_, p_239721_);
    }

    @Override
    public boolean m_6913_() {
        return this.f_238521_ != null && this.f_238767_.f_93623_;
    }

    @Override
    public void m_7379_() {
        this.f_238734_.run();
    }

    @Override
    public Component m_142562_() {
        return CommonComponents.m_178398_(this.f_96539_, this.f_240231_ != null ? this.f_240231_ : CommonComponents.f_237098_);
    }
}

