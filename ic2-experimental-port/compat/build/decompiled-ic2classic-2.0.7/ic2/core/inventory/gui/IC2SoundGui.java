/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.inventory.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.inventory.gui.components.base.ScrollSlider;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.tooltips.ILangHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class IC2SoundGui
extends Screen
implements ILangHelper {
    Screen parent;

    public IC2SoundGui(Screen parent) {
        super((Component)Component.m_237113_((String)"IC2 Sound Options"));
        this.parent = parent;
    }

    protected void m_7856_() {
        int x = this.f_96543_ / 2;
        int y = this.f_96544_ / 2;
        ((ScrollSlider)this.m_142416_((GuiEventListener)new ScrollSlider(x - 155, y - 80, 310, 20, (Component)this.translate("gui.ic2.audio.master"), (Component)this.string("%"), 0.0, 100.0, IC2.CONFIG.masterVolume.get() * 100.0, T -> this.onSettingChanged(IC2.CONFIG.masterVolume, T.getValueInt())))).setScrollEffect(1.0);
        ((ScrollSlider)this.m_142416_((GuiEventListener)new ScrollSlider(x - 155, y - 55, 150, 20, (Component)this.translate("gui.ic2.audio.block"), (Component)this.string("%"), 0.0, 100.0, IC2.CONFIG.blockVolume.get() * 100.0, T -> this.onSettingChanged(IC2.CONFIG.blockVolume, T.getValueInt())))).setScrollEffect(1.0);
        ((ScrollSlider)this.m_142416_((GuiEventListener)new ScrollSlider(x + 5, y - 55, 150, 20, (Component)this.translate("gui.ic2.audio.item"), (Component)this.string("%"), 0.0, 100.0, IC2.CONFIG.itemVolume.get() * 100.0, T -> this.onSettingChanged(IC2.CONFIG.itemVolume, T.getValueInt())))).setScrollEffect(1.0);
        ((ScrollSlider)this.m_142416_((GuiEventListener)new ScrollSlider(x - 155, y - 30, 150, 20, (Component)this.translate("gui.ic2.audio.back"), (Component)this.string("%"), 0.0, 100.0, IC2.CONFIG.backVolume.get() * 100.0, T -> this.onSettingChanged(IC2.CONFIG.backVolume, T.getValueInt())))).setScrollEffect(1.0);
        this.m_142416_((GuiEventListener)new ExtendedButton(x + 5, y - 30, 150, 20, (Component)this.translate("gui.ic2.audio.clear"), T -> this.clearSounds()));
        this.m_142416_((GuiEventListener)new ExtendedButton(x - 100, y, 200, 20, CommonComponents.f_130655_, T -> this.f_96541_.m_91152_(this.parent)));
    }

    public void clearSounds() {
        IC2.AUDIO.resetAll();
    }

    public void onSettingChanged(ConfigEntry.DoubleValue value, double newValue) {
        value.set(newValue * 0.01);
        IC2.CONFIG.save();
    }

    public void m_6305_(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.m_7333_(matrixStack);
        IC2SoundGui.m_93215_((PoseStack)matrixStack, (Font)this.f_96547_, (Component)this.f_96539_, (int)(this.f_96543_ / 2), (int)15, (int)0xFFFFFF);
        super.m_6305_(matrixStack, mouseX, mouseY, partialTicks);
    }
}

