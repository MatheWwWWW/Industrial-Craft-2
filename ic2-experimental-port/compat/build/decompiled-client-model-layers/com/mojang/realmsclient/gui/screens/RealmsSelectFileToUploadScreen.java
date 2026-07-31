/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsUploadScreen;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.slf4j.Logger;

public class RealmsSelectFileToUploadScreen
extends RealmsScreen {
    private static final Logger f_89482_ = LogUtils.getLogger();
    static final Component f_89483_ = Component.m_237115_("selectWorld.world");
    static final Component f_89485_ = Component.m_237115_("mco.upload.hardcore").m_130940_(ChatFormatting.DARK_RED);
    static final Component f_89486_ = Component.m_237115_("selectWorld.cheats");
    private static final DateFormat f_89487_ = new SimpleDateFormat();
    private final RealmsResetWorldScreen f_89488_;
    private final long f_89489_;
    private final int f_89490_;
    Button f_89491_;
    List<LevelSummary> f_89492_ = Lists.newArrayList();
    int f_89493_ = -1;
    WorldSelectionList f_89494_;
    private final Runnable f_89481_;

    public RealmsSelectFileToUploadScreen(long p_89498_, int p_89499_, RealmsResetWorldScreen p_89500_, Runnable p_89501_) {
        super(Component.m_237115_("mco.upload.select.world.title"));
        this.f_89488_ = p_89500_;
        this.f_89489_ = p_89498_;
        this.f_89490_ = p_89499_;
        this.f_89481_ = p_89501_;
    }

    private void m_89551_() throws Exception {
        LevelStorageSource.LevelCandidates $$0 = this.f_96541_.m_91392_().m_230833_();
        this.f_89492_ = this.f_96541_.m_91392_().m_230813_($$0).join().stream().filter(p_193517_ -> !p_193517_.m_193020_() && !p_193517_.m_78375_()).collect(Collectors.toList());
        for (LevelSummary $$1 : this.f_89492_) {
            this.f_89494_.m_89587_($$1);
        }
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_89494_ = new WorldSelectionList();
        try {
            this.m_89551_();
        }
        catch (Exception $$0) {
            f_89482_.error("Couldn't load level list", (Throwable)$$0);
            this.f_96541_.m_91152_(new RealmsGenericErrorScreen(Component.m_237113_("Unable to load worlds"), Component.m_130674_($$0.getMessage()), this.f_89488_));
            return;
        }
        this.m_7787_(this.f_89494_);
        this.f_89491_ = this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 32, 153, 20, Component.m_237115_("mco.upload.button.name"), p_231307_ -> this.m_89552_()));
        this.f_89491_.f_93623_ = this.f_89493_ >= 0 && this.f_89493_ < this.f_89492_.size();
        this.m_142416_(new Button(this.f_96543_ / 2 + 6, this.f_96544_ - 32, 153, 20, CommonComponents.f_130660_, p_89525_ -> this.f_96541_.m_91152_(this.f_89488_)));
        this.m_175073_(new RealmsLabel(Component.m_237115_("mco.upload.select.world.subtitle"), this.f_96543_ / 2, RealmsSelectFileToUploadScreen.m_120774_(-1), 0xA0A0A0));
        if (this.f_89492_.isEmpty()) {
            this.m_175073_(new RealmsLabel(Component.m_237115_("mco.upload.select.world.none"), this.f_96543_ / 2, this.f_96544_ / 2 - 20, 0xFFFFFF));
        }
    }

    @Override
    public Component m_142562_() {
        return CommonComponents.m_178398_(this.m_96636_(), this.m_175075_());
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    private void m_89552_() {
        if (this.f_89493_ != -1 && !this.f_89492_.get(this.f_89493_).m_78368_()) {
            LevelSummary $$0 = this.f_89492_.get(this.f_89493_);
            this.f_96541_.m_91152_(new RealmsUploadScreen(this.f_89489_, this.f_89490_, this.f_89488_, $$0, this.f_89481_));
        }
    }

    @Override
    public void m_6305_(PoseStack p_89515_, int p_89516_, int p_89517_, float p_89518_) {
        this.m_7333_(p_89515_);
        this.f_89494_.m_6305_(p_89515_, p_89516_, p_89517_, p_89518_);
        RealmsSelectFileToUploadScreen.m_93215_(p_89515_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 13, 0xFFFFFF);
        super.m_6305_(p_89515_, p_89516_, p_89517_, p_89518_);
    }

    @Override
    public boolean m_7933_(int p_89506_, int p_89507_, int p_89508_) {
        if (p_89506_ == 256) {
            this.f_96541_.m_91152_(this.f_89488_);
            return true;
        }
        return super.m_7933_(p_89506_, p_89507_, p_89508_);
    }

    static Component m_89534_(LevelSummary p_89535_) {
        return p_89535_.m_78367_().m_151499_();
    }

    static String m_89538_(LevelSummary p_89539_) {
        return f_89487_.format(new Date(p_89539_.m_78366_()));
    }

    class WorldSelectionList
    extends RealmsObjectSelectionList<Entry> {
        public WorldSelectionList() {
            super(RealmsSelectFileToUploadScreen.this.f_96543_, RealmsSelectFileToUploadScreen.this.f_96544_, RealmsSelectFileToUploadScreen.m_120774_(0), RealmsSelectFileToUploadScreen.this.f_96544_ - 40, 36);
        }

        public void m_89587_(LevelSummary p_89588_) {
            this.m_7085_(new Entry(p_89588_));
        }

        @Override
        public int m_5775_() {
            return RealmsSelectFileToUploadScreen.this.f_89492_.size() * 36;
        }

        @Override
        public boolean m_5694_() {
            return RealmsSelectFileToUploadScreen.this.m_7222_() == this;
        }

        @Override
        public void m_7733_(PoseStack p_89590_) {
            RealmsSelectFileToUploadScreen.this.m_7333_(p_89590_);
        }

        @Override
        public void m_6987_(@Nullable Entry p_89592_) {
            super.m_6987_(p_89592_);
            RealmsSelectFileToUploadScreen.this.f_89493_ = this.m_6702_().indexOf(p_89592_);
            RealmsSelectFileToUploadScreen.this.f_89491_.f_93623_ = RealmsSelectFileToUploadScreen.this.f_89493_ >= 0 && RealmsSelectFileToUploadScreen.this.f_89493_ < this.m_5773_() && !RealmsSelectFileToUploadScreen.this.f_89492_.get(RealmsSelectFileToUploadScreen.this.f_89493_).m_78368_();
        }
    }

    class Entry
    extends ObjectSelectionList.Entry<Entry> {
        private final LevelSummary f_89554_;
        private final String f_89555_;
        private final String f_89556_;
        private final Component f_89557_;

        public Entry(LevelSummary p_89560_) {
            Component $$2;
            this.f_89554_ = p_89560_;
            this.f_89555_ = p_89560_.m_78361_();
            this.f_89556_ = p_89560_.m_78358_() + " (" + RealmsSelectFileToUploadScreen.m_89538_(p_89560_) + ")";
            if (p_89560_.m_78368_()) {
                Component $$1 = f_89485_;
            } else {
                $$2 = RealmsSelectFileToUploadScreen.m_89534_(p_89560_);
            }
            if (p_89560_.m_78369_()) {
                $$2 = $$2.m_6881_().m_130946_(", ").m_7220_(f_89486_);
            }
            this.f_89557_ = $$2;
        }

        @Override
        public void m_6311_(PoseStack p_89566_, int p_89567_, int p_89568_, int p_89569_, int p_89570_, int p_89571_, int p_89572_, int p_89573_, boolean p_89574_, float p_89575_) {
            this.m_167474_(p_89566_, p_89567_, p_89569_, p_89568_);
        }

        @Override
        public boolean m_6375_(double p_89562_, double p_89563_, int p_89564_) {
            RealmsSelectFileToUploadScreen.this.f_89494_.m_7109_(RealmsSelectFileToUploadScreen.this.f_89492_.indexOf(this.f_89554_));
            return true;
        }

        protected void m_167474_(PoseStack p_167475_, int p_167476_, int p_167477_, int p_167478_) {
            String $$5;
            if (this.f_89555_.isEmpty()) {
                String $$4 = f_89483_ + " " + (p_167476_ + 1);
            } else {
                $$5 = this.f_89555_;
            }
            RealmsSelectFileToUploadScreen.this.f_96547_.m_92883_(p_167475_, $$5, p_167477_ + 2, p_167478_ + 1, 0xFFFFFF);
            RealmsSelectFileToUploadScreen.this.f_96547_.m_92883_(p_167475_, this.f_89556_, p_167477_ + 2, p_167478_ + 12, 0x808080);
            RealmsSelectFileToUploadScreen.this.f_96547_.m_92889_(p_167475_, this.f_89557_, p_167477_ + 2, p_167478_ + 12 + 10, 0x808080);
        }

        @Override
        public Component m_142172_() {
            Component $$0 = CommonComponents.m_178396_(Component.m_237113_(this.f_89554_.m_78361_()), Component.m_237113_(RealmsSelectFileToUploadScreen.m_89538_(this.f_89554_)), RealmsSelectFileToUploadScreen.m_89534_(this.f_89554_));
            return Component.m_237110_("narrator.select", $$0);
        }
    }
}

