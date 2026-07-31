/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.gui.screens.RealmsSlotOptionsScreen;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsScreen;

public class RealmsBackupInfoScreen
extends RealmsScreen {
    private static final Component f_167352_ = Component.m_237113_("UNKNOWN");
    private final Screen f_88044_;
    final Backup f_88045_;
    private BackupInfoList f_88046_;

    public RealmsBackupInfoScreen(Screen p_88048_, Backup p_88049_) {
        super(Component.m_237113_("Changes from last backup"));
        this.f_88044_ = p_88048_;
        this.f_88045_ = p_88049_;
    }

    @Override
    public void m_86600_() {
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120 + 24, 200, 20, CommonComponents.f_130660_, p_88066_ -> this.f_96541_.m_91152_(this.f_88044_)));
        this.f_88046_ = new BackupInfoList(this.f_96541_);
        this.m_7787_(this.f_88046_);
        this.m_94725_(this.f_88046_);
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_7933_(int p_88051_, int p_88052_, int p_88053_) {
        if (p_88051_ == 256) {
            this.f_96541_.m_91152_(this.f_88044_);
            return true;
        }
        return super.m_7933_(p_88051_, p_88052_, p_88053_);
    }

    @Override
    public void m_6305_(PoseStack p_88055_, int p_88056_, int p_88057_, float p_88058_) {
        this.m_7333_(p_88055_);
        this.f_88046_.m_6305_(p_88055_, p_88056_, p_88057_, p_88058_);
        RealmsBackupInfoScreen.m_93215_(p_88055_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 10, 0xFFFFFF);
        super.m_6305_(p_88055_, p_88056_, p_88057_, p_88058_);
    }

    Component m_88067_(String p_88068_, String p_88069_) {
        String $$2 = p_88068_.toLowerCase(Locale.ROOT);
        if ($$2.contains("game") && $$2.contains("mode")) {
            return this.m_88075_(p_88069_);
        }
        if ($$2.contains("game") && $$2.contains("difficulty")) {
            return this.m_88073_(p_88069_);
        }
        return Component.m_237113_(p_88069_);
    }

    private Component m_88073_(String p_88074_) {
        try {
            return RealmsSlotOptionsScreen.f_89870_.get(Integer.parseInt(p_88074_)).m_19033_();
        }
        catch (Exception $$1) {
            return f_167352_;
        }
    }

    private Component m_88075_(String p_88076_) {
        try {
            return RealmsSlotOptionsScreen.f_89871_.get(Integer.parseInt(p_88076_)).m_151500_();
        }
        catch (Exception $$1) {
            return f_167352_;
        }
    }

    class BackupInfoList
    extends ObjectSelectionList<BackupInfoListEntry> {
        public BackupInfoList(Minecraft p_88082_) {
            super(p_88082_, RealmsBackupInfoScreen.this.f_96543_, RealmsBackupInfoScreen.this.f_96544_, 32, RealmsBackupInfoScreen.this.f_96544_ - 64, 36);
            this.m_93471_(false);
            if (RealmsBackupInfoScreen.this.f_88045_.f_87393_ != null) {
                RealmsBackupInfoScreen.this.f_88045_.f_87393_.forEach((p_88084_, p_88085_) -> this.m_7085_(new BackupInfoListEntry((String)p_88084_, (String)p_88085_)));
            }
        }
    }

    class BackupInfoListEntry
    extends ObjectSelectionList.Entry<BackupInfoListEntry> {
        private final String f_88087_;
        private final String f_88088_;

        public BackupInfoListEntry(String p_88091_, String p_88092_) {
            this.f_88087_ = p_88091_;
            this.f_88088_ = p_88092_;
        }

        @Override
        public void m_6311_(PoseStack p_88094_, int p_88095_, int p_88096_, int p_88097_, int p_88098_, int p_88099_, int p_88100_, int p_88101_, boolean p_88102_, float p_88103_) {
            Font $$10 = ((RealmsBackupInfoScreen)RealmsBackupInfoScreen.this).f_96541_.f_91062_;
            GuiComponent.m_93236_(p_88094_, $$10, this.f_88087_, p_88097_, p_88096_, 0xA0A0A0);
            GuiComponent.m_93243_(p_88094_, $$10, RealmsBackupInfoScreen.this.m_88067_(this.f_88087_, this.f_88088_), p_88097_, p_88096_ + 12, 0xFFFFFF);
        }

        @Override
        public Component m_142172_() {
            return Component.m_237110_("narrator.select", this.f_88087_ + " " + this.f_88088_);
        }
    }
}

