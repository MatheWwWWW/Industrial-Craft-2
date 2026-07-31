/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.report.AbuseReportLimits
 *  com.mojang.datafixers.util.Unit
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.reporting;

import com.mojang.authlib.minecraft.report.AbuseReportLimits;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Unit;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.GenericWaitingScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.WarningScreen;
import net.minecraft.client.gui.screens.reporting.ChatSelectionScreen;
import net.minecraft.client.gui.screens.reporting.ReportReasonSelectionScreen;
import net.minecraft.client.multiplayer.chat.report.ChatReportBuilder;
import net.minecraft.client.multiplayer.chat.report.ReportReason;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.ThrowingComponent;
import org.slf4j.Logger;

public class ChatReportScreen
extends Screen {
    private static final int f_238525_ = 120;
    private static final int f_238565_ = 20;
    private static final int f_238807_ = 20;
    private static final int f_238678_ = 10;
    private static final int f_238671_ = 25;
    private static final int f_238745_ = 280;
    private static final int f_238558_ = 300;
    private static final Component f_238771_ = Component.m_237115_("gui.chatReport.observed_what");
    private static final Component f_238723_ = Component.m_237115_("gui.chatReport.select_reason");
    private static final Component f_238607_ = Component.m_237115_("gui.chatReport.more_comments");
    private static final Component f_238545_ = Component.m_237115_("gui.chatReport.describe");
    private static final Component f_238530_ = Component.m_237115_("gui.chatReport.report_sent_msg");
    private static final Component f_238795_ = Component.m_237115_("gui.chatReport.select_chat");
    private static final Component f_238783_ = Component.m_237115_("gui.abuseReport.sending.title").m_130940_(ChatFormatting.BOLD);
    private static final Component f_240228_ = Component.m_237115_("gui.abuseReport.sent.title").m_130940_(ChatFormatting.BOLD);
    private static final Component f_240232_ = Component.m_237115_("gui.abuseReport.error.title").m_130940_(ChatFormatting.BOLD);
    private static final Component f_238555_ = Component.m_237115_("gui.abuseReport.send.generic_error");
    private static final Logger f_238568_ = LogUtils.getLogger();
    @Nullable
    final Screen f_238596_;
    private final ReportingContext f_238816_;
    @Nullable
    private MultiLineLabel f_238551_;
    @Nullable
    private MultiLineEditBox f_238775_;
    private Button f_238561_;
    private ChatReportBuilder f_238828_;
    @Nullable
    ChatReportBuilder.CannotBuildReason f_238773_;

    public ChatReportScreen(Screen p_239116_, ReportingContext p_239117_, UUID p_239118_) {
        super(Component.m_237115_("gui.chatReport.title"));
        this.f_238596_ = p_239116_;
        this.f_238816_ = p_239117_;
        this.f_238828_ = new ChatReportBuilder(p_239118_, p_239117_.f_238706_().m_239479_());
    }

    @Override
    protected void m_7856_() {
        MutableComponent $$5;
        this.f_96541_.f_91068_.m_90926_(true);
        AbuseReportLimits $$0 = this.f_238816_.f_238706_().m_239479_();
        int $$1 = this.f_96543_ / 2;
        ReportReason $$2 = this.f_238828_.m_239339_();
        this.f_238551_ = $$2 != null ? MultiLineLabel.m_94341_(this.f_96547_, $$2.m_240151_(), 280) : null;
        IntSet $$3 = this.f_238828_.m_239716_();
        if ($$3.isEmpty()) {
            Component $$4 = f_238795_;
        } else {
            $$5 = Component.m_237110_("gui.chatReport.selected_chat", $$3.size());
        }
        this.m_142416_(new Button(this.m_239357_(), this.m_239320_(), 280, 20, $$5, p_239836_ -> this.f_96541_.m_91152_(new ChatSelectionScreen(this, this.f_238816_, this.f_238828_, p_239697_ -> {
            this.f_238828_ = p_239697_;
            this.m_239041_();
        }))));
        Component $$6 = Util.m_214617_($$2, ReportReason::m_239342_, f_238723_);
        this.m_142416_(new Button(this.m_239357_(), this.m_239099_(), 280, 20, $$6, p_239172_ -> this.f_96541_.m_91152_(new ReportReasonSelectionScreen(this, this.f_238828_.m_239339_(), p_239513_ -> {
            this.f_238828_.m_239097_((ReportReason)((Object)((Object)p_239513_)));
            this.m_239041_();
        }))));
        this.f_238775_ = this.m_142416_(new MultiLineEditBox(this.f_96541_.f_91062_, this.m_239357_(), this.m_240099_(), 280, this.m_239485_() - this.m_240099_(), f_238545_, Component.m_237115_("gui.chatReport.comments")));
        this.f_238775_.m_240159_(this.f_238828_.m_238976_());
        this.f_238775_.m_239313_($$0.maxOpinionCommentsLength());
        this.f_238775_.m_239273_(p_240036_ -> {
            this.f_238828_.m_239079_((String)p_240036_);
            this.m_239041_();
        });
        this.m_142416_(new Button($$1 - 120, this.m_239333_(), 120, 20, CommonComponents.f_130660_, p_239971_ -> this.m_7379_()));
        this.f_238561_ = this.m_142416_(new Button($$1 + 10, this.m_239333_(), 120, 20, Component.m_237115_("gui.chatReport.send"), p_239742_ -> this.m_240000_(), new SubmitButtonTooltip()));
        this.m_239041_();
    }

    private void m_239041_() {
        this.f_238773_ = this.f_238828_.m_239332_();
        this.f_238561_.f_93623_ = this.f_238773_ == null;
    }

    private void m_240000_() {
        this.f_238828_.m_240128_(this.f_238816_).ifLeft(p_240239_ -> {
            CompletableFuture<Unit> $$1 = this.f_238816_.f_238706_().m_239469_(p_240239_.f_238815_(), p_240239_.f_238727_());
            this.f_96541_.m_91152_(GenericWaitingScreen.m_240309_(f_238783_, CommonComponents.f_130656_, () -> {
                this.f_96541_.m_91152_(this);
                $$1.cancel(true);
            }));
            $$1.handleAsync((p_240236_, p_240237_) -> {
                if (p_240237_ == null) {
                    this.m_240265_();
                } else {
                    if (p_240237_ instanceof CancellationException) {
                        return null;
                    }
                    this.m_240313_((Throwable)p_240237_);
                }
                return null;
            }, (Executor)this.f_96541_);
        }).ifRight(p_242967_ -> this.m_242964_(p_242967_.f_238631_()));
    }

    private void m_240265_() {
        this.f_96541_.m_91152_(GenericWaitingScreen.m_240290_(f_240228_, f_238530_, CommonComponents.f_130655_, () -> this.f_96541_.m_91152_(null)));
    }

    private void m_240313_(Throwable p_240314_) {
        Component $$3;
        f_238568_.error("Encountered error while sending abuse report", p_240314_);
        Throwable throwable = p_240314_.getCause();
        if (throwable instanceof ThrowingComponent) {
            ThrowingComponent $$1 = (ThrowingComponent)throwable;
            Component $$2 = $$1.m_237308_();
        } else {
            $$3 = f_238555_;
        }
        this.m_242964_($$3);
    }

    private void m_242964_(Component p_242978_) {
        MutableComponent $$1 = p_242978_.m_6881_().m_130940_(ChatFormatting.RED);
        this.f_96541_.m_91152_(GenericWaitingScreen.m_240290_(f_240232_, $$1, CommonComponents.f_130660_, () -> this.f_96541_.m_91152_(this)));
    }

    @Override
    public void m_6305_(PoseStack p_239922_, int p_239923_, int p_239924_, float p_239925_) {
        int $$4 = this.f_96543_ / 2;
        RenderSystem.m_69465_();
        this.m_7333_(p_239922_);
        ChatReportScreen.m_93215_(p_239922_, this.f_96547_, this.f_96539_, $$4, 10, 0xFFFFFF);
        ChatReportScreen.m_93215_(p_239922_, this.f_96547_, f_238771_, $$4, this.m_239320_() - this.f_96547_.f_92710_ - 6, 0xFFFFFF);
        if (this.f_238551_ != null) {
            this.f_238551_.m_6516_(p_239922_, this.m_239357_(), this.m_239099_() + 20 + 5, this.f_96547_.f_92710_, 0xFFFFFF);
        }
        ChatReportScreen.m_93243_(p_239922_, this.f_96547_, f_238607_, this.m_239357_(), this.m_240099_() - this.f_96547_.f_92710_ - 6, 0xFFFFFF);
        super.m_6305_(p_239922_, p_239923_, p_239924_, p_239925_);
        RenderSystem.m_69482_();
    }

    @Override
    public void m_86600_() {
        this.f_238775_.m_239213_();
        super.m_86600_();
    }

    @Override
    public void m_7379_() {
        if (!this.f_238775_.m_239249_().isEmpty()) {
            this.f_96541_.m_91152_(new DiscardReportWarningScreen());
        } else {
            this.f_96541_.m_91152_(this.f_238596_);
        }
    }

    @Override
    public boolean m_6348_(double p_239350_, double p_239351_, int p_239352_) {
        if (super.m_6348_(p_239350_, p_239351_, p_239352_)) {
            return true;
        }
        return this.f_238775_.m_6348_(p_239350_, p_239351_, p_239352_);
    }

    private int m_239357_() {
        return this.f_96543_ / 2 - 140;
    }

    private int m_239146_() {
        return this.f_96543_ / 2 + 140;
    }

    private int m_239871_() {
        return Math.max((this.f_96544_ - 300) / 2, 0);
    }

    private int m_239033_() {
        return Math.min((this.f_96544_ + 300) / 2, this.f_96544_);
    }

    private int m_239320_() {
        return this.m_239871_() + 40;
    }

    private int m_239099_() {
        return this.m_239320_() + 10 + 20;
    }

    private int m_240099_() {
        int $$0 = this.m_239099_() + 20 + 25;
        if (this.f_238551_ != null) {
            $$0 += (this.f_238551_.m_5770_() + 1) * this.f_96547_.f_92710_;
        }
        return $$0;
    }

    private int m_239485_() {
        return this.m_239333_() - 20;
    }

    private int m_239333_() {
        return this.m_239033_() - 20 - 10;
    }

    class SubmitButtonTooltip
    implements Button.OnTooltip {
        SubmitButtonTooltip() {
        }

        @Override
        public void m_93752_(Button p_240155_, PoseStack p_240156_, int p_240157_, int p_240158_) {
            if (ChatReportScreen.this.f_238773_ != null) {
                Component $$4 = ChatReportScreen.this.f_238773_.f_238631_();
                ChatReportScreen.this.m_96617_(p_240156_, ChatReportScreen.this.f_96547_.m_92923_($$4, Math.max(ChatReportScreen.this.f_96543_ / 2 - 43, 170)), p_240157_, p_240158_);
            }
        }
    }

    class DiscardReportWarningScreen
    extends WarningScreen {
        private static final Component f_238729_ = Component.m_237115_("gui.chatReport.discard.title").m_130940_(ChatFormatting.BOLD);
        private static final Component f_238704_ = Component.m_237115_("gui.chatReport.discard.content");
        private static final Component f_238630_ = Component.m_237115_("gui.chatReport.discard.return");
        private static final Component f_238679_ = Component.m_237115_("gui.chatReport.discard.discard");

        protected DiscardReportWarningScreen() {
            super(f_238729_, f_238704_, f_238704_);
        }

        @Override
        protected void m_207212_(int p_239753_) {
            this.m_142416_(new Button(this.f_96543_ / 2 - 155, 100 + p_239753_, 150, 20, f_238630_, p_239525_ -> this.m_7379_()));
            this.m_142416_(new Button(this.f_96543_ / 2 + 5, 100 + p_239753_, 150, 20, f_238679_, p_239170_ -> this.f_96541_.m_91152_(ChatReportScreen.this.f_238596_)));
        }

        @Override
        public void m_7379_() {
            this.f_96541_.m_91152_(ChatReportScreen.this);
        }

        @Override
        public boolean m_6913_() {
            return false;
        }

        @Override
        protected void m_239056_(PoseStack p_239057_) {
            DiscardReportWarningScreen.m_93243_(p_239057_, this.f_96547_, this.f_96539_, this.f_96543_ / 2 - 155, 30, 0xFFFFFF);
        }
    }
}

