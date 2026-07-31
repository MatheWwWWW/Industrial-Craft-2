/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.client.gui.components.VolumeSlider;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FormattedCharSequence;

public class SoundOptionsScreen
extends OptionsSubScreen {
    @Nullable
    private AbstractWidget f_232767_;

    public SoundOptionsScreen(Screen p_96702_, Options p_96703_) {
        super(p_96702_, p_96703_, Component.m_237115_("options.sounds.title"));
    }

    @Override
    protected void m_7856_() {
        int $$0 = this.f_96544_ / 6 - 12;
        int $$1 = 22;
        int $$2 = 0;
        this.m_142416_(new VolumeSlider(this.f_96541_, this.f_96543_ / 2 - 155 + $$2 % 2 * 160, $$0 + 22 * ($$2 >> 1), SoundSource.MASTER, 310));
        $$2 += 2;
        for (SoundSource $$3 : SoundSource.values()) {
            if ($$3 == SoundSource.MASTER) continue;
            this.m_142416_(new VolumeSlider(this.f_96541_, this.f_96543_ / 2 - 155 + $$2 % 2 * 160, $$0 + 22 * ($$2 >> 1), $$3, 150));
            ++$$2;
        }
        if ($$2 % 2 == 1) {
            ++$$2;
        }
        this.m_142416_(this.f_96282_.m_231931_().m_231507_(this.f_96282_, this.f_96543_ / 2 - 155, $$0 + 22 * ($$2 >> 1), 310));
        this.m_142416_(this.f_96282_.m_231825_().m_231507_(this.f_96282_, this.f_96543_ / 2 - 155, $$0 + 22 * (($$2 += 2) >> 1), 150));
        this.f_232767_ = this.f_96282_.m_231826_().m_231507_(this.f_96282_, this.f_96543_ / 2 + 5, $$0 + 22 * ($$2 >> 1), 150);
        this.m_142416_(this.f_232767_);
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, $$0 + 22 * (($$2 += 2) >> 1), 200, 20, CommonComponents.f_130655_, p_96713_ -> this.f_96541_.m_91152_(this.f_96281_)));
    }

    @Override
    public void m_6305_(PoseStack p_96705_, int p_96706_, int p_96707_, float p_96708_) {
        this.m_7333_(p_96705_);
        SoundOptionsScreen.m_93215_(p_96705_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 15, 0xFFFFFF);
        super.m_6305_(p_96705_, p_96706_, p_96707_, p_96708_);
        if (this.f_232767_ != null && this.f_232767_.m_5953_(p_96706_, p_96707_)) {
            List<FormattedCharSequence> $$4 = ((TooltipAccessor)((Object)this.f_232767_)).m_141932_();
            this.m_96617_(p_96705_, $$4, p_96706_, p_96707_);
        }
    }
}

