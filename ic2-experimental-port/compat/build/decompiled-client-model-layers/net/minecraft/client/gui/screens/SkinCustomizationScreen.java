/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.PlayerModelPart;

public class SkinCustomizationScreen
extends OptionsSubScreen {
    public SkinCustomizationScreen(Screen p_96684_, Options p_96685_) {
        super(p_96684_, p_96685_, Component.m_237115_("options.skinCustomisation.title"));
    }

    @Override
    protected void m_7856_() {
        int $$0 = 0;
        for (PlayerModelPart $$1 : PlayerModelPart.values()) {
            this.m_142416_(CycleButton.m_168916_(this.f_96282_.m_168416_($$1)).m_168936_(this.f_96543_ / 2 - 155 + $$0 % 2 * 160, this.f_96544_ / 6 + 24 * ($$0 >> 1), 150, 20, $$1.m_36447_(), (p_169436_, p_169437_) -> this.f_96282_.m_168418_($$1, (boolean)p_169437_)));
            ++$$0;
        }
        this.m_142416_(this.f_96282_.m_232107_().m_231507_(this.f_96282_, this.f_96543_ / 2 - 155 + $$0 % 2 * 160, this.f_96544_ / 6 + 24 * ($$0 >> 1), 150));
        if (++$$0 % 2 == 1) {
            ++$$0;
        }
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 24 * ($$0 >> 1), 200, 20, CommonComponents.f_130655_, p_96700_ -> this.f_96541_.m_91152_(this.f_96281_)));
    }

    @Override
    public void m_6305_(PoseStack p_96692_, int p_96693_, int p_96694_, float p_96695_) {
        this.m_7333_(p_96692_);
        SkinCustomizationScreen.m_93215_(p_96692_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 0xFFFFFF);
        super.m_6305_(p_96692_, p_96693_, p_96694_, p_96695_);
    }
}

