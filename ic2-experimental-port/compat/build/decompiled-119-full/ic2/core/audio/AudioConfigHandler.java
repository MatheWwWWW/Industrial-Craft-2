/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.AbstractSliderButton
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.SoundOptionsScreen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 */
package ic2.core.audio;

import ic2.core.IC2;
import ic2.core.audio.AudioManagerClient;
import ic2.core.init.MainConfig;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class AudioConfigHandler {
    public static void onGuiCreate(Screen screen, Consumer<GuiEventListener> consumer) {
        if (!(screen instanceof SoundOptionsScreen)) {
            return;
        }
        final AudioManagerClient audioManagerClient = (AudioManagerClient)IC2.sideProxy.getAudioManager();
        if (!audioManagerClient.enabled) {
            return;
        }
        int n = 11;
        consumer.accept((GuiEventListener)new AbstractSliderButton(screen.f_96543_ / 2 - 155 + n % 2 * 160, screen.f_96544_ / 6 - 12 + 24 * (n >> 1), 150, 20, CommonComponents.f_237098_, audioManagerClient.getMasterVolume()){
            {
                super(n, n2, n3, n4, component, d);
                this.m_5695_();
            }

            protected void m_5695_() {
                String string = this.f_93577_ <= 0.0 ? I18n.m_118938_((String)"options.off", (Object[])new Object[0]) : (int)(this.f_93577_ * 100.0) + "%";
                this.m_93666_((Component)Component.m_237110_((String)"ic2.tooltip.sound", (Object[])new Object[]{string}));
            }

            protected void m_5697_() {
                audioManagerClient.masterVolume = (float)this.f_93577_;
                MainConfig.get().set("audio/volume", String.format("%.2f", this.f_93577_));
                MainConfig.save();
            }
        });
    }
}

