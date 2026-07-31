/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.PopupScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class VideoSettingsScreen
extends OptionsSubScreen {
    private static final Component f_96794_ = Component.m_237115_("options.graphics.fabulous").m_130940_(ChatFormatting.ITALIC);
    private static final Component f_96795_ = Component.m_237110_("options.graphics.warning.message", f_96794_, f_96794_);
    private static final Component f_96796_ = Component.m_237115_("options.graphics.warning.title").m_130940_(ChatFormatting.RED);
    private static final Component f_96797_ = Component.m_237115_("options.graphics.warning.accept");
    private static final Component f_96798_ = Component.m_237115_("options.graphics.warning.cancel");
    private OptionsList f_96801_;
    private final GpuWarnlistManager f_96802_;
    private final int f_96803_;

    private static OptionInstance<?>[] m_232811_(Options p_232812_) {
        return new OptionInstance[]{p_232812_.m_232060_(), p_232812_.m_231984_(), p_232812_.m_232080_(), p_232812_.m_232001_(), p_232812_.m_232070_(), p_232812_.m_232035_(), p_232812_.m_231817_(), p_232812_.m_231830_(), p_232812_.m_231928_(), p_232812_.m_232120_(), p_232812_.m_231927_(), p_232812_.m_232050_(), p_232812_.m_231829_(), p_232812_.m_231929_(), p_232812_.m_232119_(), p_232812_.m_231818_(), p_232812_.m_231924_(), p_232812_.m_232018_(), p_232812_.m_231925_(), p_232812_.m_231834_()};
    }

    public VideoSettingsScreen(Screen p_96806_, Options p_96807_) {
        super(p_96806_, p_96807_, Component.m_237115_("options.videoTitle"));
        this.f_96802_ = p_96806_.f_96541_.m_91105_();
        this.f_96802_.m_109252_();
        if (p_96807_.m_232060_().m_231551_() == GraphicsStatus.FABULOUS) {
            this.f_96802_.m_109248_();
        }
        this.f_96803_ = p_96807_.m_232119_().m_231551_();
    }

    @Override
    protected void m_7856_() {
        int $$5;
        this.f_96801_ = new OptionsList(this.f_96541_, this.f_96543_, this.f_96544_, 32, this.f_96544_ - 32, 25);
        int $$0 = -1;
        Window $$1 = this.f_96541_.m_91268_();
        Monitor $$2 = $$1.m_85450_();
        if ($$2 == null) {
            int $$3 = -1;
        } else {
            Optional<VideoMode> $$4 = $$1.m_85436_();
            $$5 = $$4.map($$2::m_84946_).orElse(-1);
        }
        OptionInstance<Integer> $$6 = new OptionInstance<Integer>("options.fullscreen.resolution", OptionInstance.m_231498_(), (p_232806_, p_232807_) -> {
            if ($$2 == null) {
                return Component.m_237115_("options.fullscreen.unavailable");
            }
            if (p_232807_ == -1) {
                return Options.m_231921_(p_232806_, Component.m_237115_("options.fullscreen.current"));
            }
            return Options.m_231921_(p_232806_, Component.m_237113_($$2.m_84944_((int)p_232807_).toString()));
        }, new OptionInstance.IntRange(-1, $$2 != null ? $$2.m_84953_() - 1 : -1), $$5, p_232803_ -> {
            if ($$2 == null) {
                return;
            }
            $$1.m_85405_(p_232803_ == -1 ? Optional.empty() : Optional.of($$2.m_84944_((int)p_232803_)));
        });
        this.f_96801_.m_232528_($$6);
        this.f_96801_.m_232528_(this.f_96282_.m_232121_());
        this.f_96801_.m_232533_(VideoSettingsScreen.m_232811_(this.f_96282_));
        this.m_7787_(this.f_96801_);
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 27, 200, 20, CommonComponents.f_130655_, p_232810_ -> {
            this.f_96541_.f_91066_.m_92169_();
            $$1.m_85437_();
            this.f_96541_.m_91152_(this.f_96281_);
        }));
    }

    @Override
    public void m_7861_() {
        if (this.f_96282_.m_232119_().m_231551_() != this.f_96803_) {
            this.f_96541_.m_91312_(this.f_96282_.m_232119_().m_231551_());
            this.f_96541_.m_91088_();
        }
        super.m_7861_();
    }

    @Override
    public boolean m_6375_(double p_96809_, double p_96810_, int p_96811_) {
        int $$3 = this.f_96282_.m_231928_().m_231551_();
        if (super.m_6375_(p_96809_, p_96810_, p_96811_)) {
            if (this.f_96282_.m_231928_().m_231551_() != $$3) {
                this.f_96541_.m_5741_();
            }
            if (this.f_96802_.m_109250_()) {
                String $$7;
                String $$6;
                ArrayList $$4 = Lists.newArrayList((Object[])new Component[]{f_96795_, CommonComponents.f_178388_});
                String $$5 = this.f_96802_.m_109253_();
                if ($$5 != null) {
                    $$4.add(CommonComponents.f_178388_);
                    $$4.add(Component.m_237110_("options.graphics.warning.renderer", $$5).m_130940_(ChatFormatting.GRAY));
                }
                if (($$6 = this.f_96802_.m_109255_()) != null) {
                    $$4.add(CommonComponents.f_178388_);
                    $$4.add(Component.m_237110_("options.graphics.warning.vendor", $$6).m_130940_(ChatFormatting.GRAY));
                }
                if (($$7 = this.f_96802_.m_109254_()) != null) {
                    $$4.add(CommonComponents.f_178388_);
                    $$4.add(Component.m_237110_("options.graphics.warning.version", $$7).m_130940_(ChatFormatting.GRAY));
                }
                this.f_96541_.m_91152_(new PopupScreen(f_96796_, $$4, (ImmutableList<PopupScreen.ButtonOption>)ImmutableList.of((Object)new PopupScreen.ButtonOption(f_96797_, p_232816_ -> {
                    this.f_96282_.m_232060_().m_231514_(GraphicsStatus.FABULOUS);
                    Minecraft.m_91087_().f_91060_.m_109818_();
                    this.f_96802_.m_109248_();
                    this.f_96541_.m_91152_(this);
                }), (Object)new PopupScreen.ButtonOption(f_96798_, p_232814_ -> {
                    this.f_96802_.m_109249_();
                    this.f_96541_.m_91152_(this);
                }))));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean m_6348_(double p_96823_, double p_96824_, int p_96825_) {
        int $$3 = this.f_96282_.m_231928_().m_231551_();
        if (super.m_6348_(p_96823_, p_96824_, p_96825_)) {
            return true;
        }
        if (this.f_96801_.m_6348_(p_96823_, p_96824_, p_96825_)) {
            if (this.f_96282_.m_231928_().m_231551_() != $$3) {
                this.f_96541_.m_5741_();
            }
            return true;
        }
        return false;
    }

    @Override
    public void m_6305_(PoseStack p_96813_, int p_96814_, int p_96815_, float p_96816_) {
        this.m_7333_(p_96813_);
        this.f_96801_.m_6305_(p_96813_, p_96814_, p_96815_, p_96816_);
        VideoSettingsScreen.m_93215_(p_96813_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 5, 0xFFFFFF);
        super.m_6305_(p_96813_, p_96814_, p_96815_, p_96816_);
        List<FormattedCharSequence> $$4 = VideoSettingsScreen.m_96287_(this.f_96801_, p_96814_, p_96815_);
        this.m_96617_(p_96813_, $$4, p_96814_, p_96815_);
    }
}

