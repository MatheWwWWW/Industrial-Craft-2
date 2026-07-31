/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ConfirmLinkScreen
extends ConfirmScreen {
    private static final Component f_169239_ = Component.m_237115_("chat.copy");
    private static final Component f_169240_ = Component.m_237115_("chat.link.warning");
    private final String f_95628_;
    private final boolean f_95629_;

    public ConfirmLinkScreen(BooleanConsumer p_95631_, String p_95632_, boolean p_95633_) {
        this(p_95631_, ConfirmLinkScreen.m_240013_(p_95633_), Component.m_237113_(p_95632_), p_95632_, p_95633_ ? CommonComponents.f_130656_ : CommonComponents.f_130658_, p_95633_);
    }

    public ConfirmLinkScreen(BooleanConsumer p_238329_, Component p_238330_, String p_238331_, boolean p_238332_) {
        this(p_238329_, p_238330_, p_238331_, p_238332_ ? CommonComponents.f_130656_ : CommonComponents.f_130658_, p_238332_);
    }

    public ConfirmLinkScreen(BooleanConsumer p_239991_, Component p_239992_, String p_239993_, Component p_239994_, boolean p_239995_) {
        this(p_239991_, p_239992_, ConfirmLinkScreen.m_239179_(p_239995_, p_239993_), p_239993_, p_239994_, p_239995_);
    }

    public ConfirmLinkScreen(BooleanConsumer p_240191_, Component p_240192_, Component p_240193_, String p_240194_, Component p_240195_, boolean p_240196_) {
        super(p_240191_, p_240192_, p_240193_);
        this.f_95647_ = p_240196_ ? Component.m_237115_("chat.link.open") : CommonComponents.f_130657_;
        this.f_95648_ = p_240195_;
        this.f_95629_ = !p_240196_;
        this.f_95628_ = p_240194_;
    }

    protected static MutableComponent m_239179_(boolean p_239180_, String p_239181_) {
        return ConfirmLinkScreen.m_240013_(p_239180_).m_130946_(" ").m_7220_(Component.m_237113_(p_239181_));
    }

    protected static MutableComponent m_240013_(boolean p_240014_) {
        return Component.m_237115_(p_240014_ ? "chat.link.confirmTrusted" : "chat.link.confirm");
    }

    @Override
    protected void m_141972_(int p_169243_) {
        this.m_142416_(new Button(this.f_96543_ / 2 - 50 - 105, p_169243_, 100, 20, this.f_95647_, p_169249_ -> this.f_95649_.accept(true)));
        this.m_142416_(new Button(this.f_96543_ / 2 - 50, p_169243_, 100, 20, f_169239_, p_169247_ -> {
            this.m_95646_();
            this.f_95649_.accept(false);
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 - 50 + 105, p_169243_, 100, 20, this.f_95648_, p_169245_ -> this.f_95649_.accept(false)));
    }

    public void m_95646_() {
        this.f_96541_.f_91068_.m_90911_(this.f_95628_);
    }

    @Override
    public void m_6305_(PoseStack p_95635_, int p_95636_, int p_95637_, float p_95638_) {
        super.m_6305_(p_95635_, p_95636_, p_95637_, p_95638_);
        if (this.f_95629_) {
            ConfirmLinkScreen.m_93215_(p_95635_, this.f_96547_, f_169240_, this.f_96543_ / 2, 110, 0xFFCCCC);
        }
    }
}

