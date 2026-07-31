/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;

public class PopupScreen
extends Screen {
    private static final int f_169340_ = 20;
    private static final int f_169341_ = 5;
    private static final int f_169342_ = 20;
    private final Component f_169343_;
    private final FormattedText f_96339_;
    private final ImmutableList<ButtonOption> f_96340_;
    private MultiLineLabel f_96341_ = MultiLineLabel.f_94331_;
    private int f_96342_;
    private int f_96343_;

    protected PopupScreen(Component p_96345_, List<Component> p_96346_, ImmutableList<ButtonOption> p_96347_) {
        super(p_96345_);
        this.f_96339_ = FormattedText.m_130768_(p_96346_);
        this.f_169343_ = CommonComponents.m_178398_(p_96345_, ComponentUtils.m_178433_(p_96346_, CommonComponents.f_237098_));
        this.f_96340_ = p_96347_;
    }

    @Override
    public Component m_142562_() {
        return this.f_169343_;
    }

    @Override
    public void m_7856_() {
        for (ButtonOption $$0 : this.f_96340_) {
            this.f_96343_ = Math.max(this.f_96343_, 20 + this.f_96547_.m_92852_($$0.f_96359_) + 20);
        }
        int $$1 = 5 + this.f_96343_ + 5;
        int $$2 = $$1 * this.f_96340_.size();
        this.f_96341_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_96339_, $$2);
        int $$3 = this.f_96341_.m_5770_() * this.f_96547_.f_92710_;
        this.f_96342_ = (int)((double)this.f_96544_ / 2.0 - (double)$$3 / 2.0);
        int $$4 = this.f_96342_ + $$3 + this.f_96547_.f_92710_ * 2;
        int $$5 = (int)((double)this.f_96543_ / 2.0 - (double)$$2 / 2.0);
        for (ButtonOption $$6 : this.f_96340_) {
            this.m_142416_(new Button($$5, $$4, this.f_96343_, 20, $$6.f_96359_, $$6.f_96360_));
            $$5 += $$1;
        }
    }

    @Override
    public void m_6305_(PoseStack p_96349_, int p_96350_, int p_96351_, float p_96352_) {
        this.m_96626_(0);
        PopupScreen.m_93215_(p_96349_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, this.f_96342_ - this.f_96547_.f_92710_ * 2, -1);
        this.f_96341_.m_6276_(p_96349_, this.f_96543_ / 2, this.f_96342_);
        super.m_6305_(p_96349_, p_96350_, p_96351_, p_96352_);
    }

    @Override
    public boolean m_6913_() {
        return false;
    }

    public static final class ButtonOption {
        final Component f_96359_;
        final Button.OnPress f_96360_;

        public ButtonOption(Component p_96362_, Button.OnPress p_96363_) {
            this.f_96359_ = p_96362_;
            this.f_96360_ = p_96363_;
        }
    }
}

