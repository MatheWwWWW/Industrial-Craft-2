/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.social;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.reporting.ChatReportScreen;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;

public class PlayerEntry
extends ContainerObjectSelectionList.Entry<PlayerEntry> {
    private static final ResourceLocation f_238820_ = new ResourceLocation("textures/gui/report_button.png");
    private static final int f_170065_ = 10;
    private static final int f_170066_ = 150;
    private final Minecraft f_100534_;
    private final List<AbstractWidget> f_100535_;
    private final UUID f_100536_;
    private final String f_100537_;
    private final Supplier<ResourceLocation> f_100538_;
    private boolean f_100539_;
    private boolean f_240676_;
    private final boolean f_240670_;
    private final boolean f_243019_;
    @Nullable
    private Button f_100540_;
    @Nullable
    private Button f_100541_;
    @Nullable
    private Button f_238614_;
    final List<FormattedCharSequence> f_100542_;
    final List<FormattedCharSequence> f_100543_;
    List<FormattedCharSequence> f_238711_;
    float f_100544_;
    private static final Component f_100545_ = Component.m_237115_("gui.socialInteractions.status_hidden").m_130940_(ChatFormatting.ITALIC);
    private static final Component f_100546_ = Component.m_237115_("gui.socialInteractions.status_blocked").m_130940_(ChatFormatting.ITALIC);
    private static final Component f_100547_ = Component.m_237115_("gui.socialInteractions.status_offline").m_130940_(ChatFormatting.ITALIC);
    private static final Component f_100548_ = Component.m_237115_("gui.socialInteractions.status_hidden_offline").m_130940_(ChatFormatting.ITALIC);
    private static final Component f_100549_ = Component.m_237115_("gui.socialInteractions.status_blocked_offline").m_130940_(ChatFormatting.ITALIC);
    private static final Component f_238715_ = Component.m_237115_("gui.socialInteractions.tooltip.report.disabled");
    private static final Component f_242995_ = Component.m_237115_("gui.socialInteractions.tooltip.report.not_reportable");
    private static final Component f_240656_ = Component.m_237115_("gui.socialInteractions.tooltip.hide");
    private static final Component f_240657_ = Component.m_237115_("gui.socialInteractions.tooltip.show");
    private static final Component f_240655_ = Component.m_237115_("gui.socialInteractions.tooltip.report");
    private static final int f_170069_ = 24;
    private static final int f_170061_ = 4;
    private static final int f_170062_ = 20;
    private static final int f_170063_ = 0;
    private static final int f_170064_ = 38;
    public static final int f_100529_ = FastColor.ARGB32.m_13660_(190, 0, 0, 0);
    public static final int f_100530_ = FastColor.ARGB32.m_13660_(255, 74, 74, 74);
    public static final int f_100531_ = FastColor.ARGB32.m_13660_(255, 48, 48, 48);
    public static final int f_100532_ = FastColor.ARGB32.m_13660_(255, 255, 255, 255);
    public static final int f_100533_ = FastColor.ARGB32.m_13660_(140, 255, 255, 255);

    public PlayerEntry(final Minecraft p_243293_, final SocialInteractionsScreen p_243214_, UUID p_243288_, String p_243311_, Supplier<ResourceLocation> p_243309_, boolean p_243297_) {
        boolean $$11;
        this.f_100534_ = p_243293_;
        this.f_100536_ = p_243288_;
        this.f_100537_ = p_243311_;
        this.f_100538_ = p_243309_;
        ReportingContext $$6 = p_243293_.m_239211_();
        this.f_240670_ = $$6.f_238706_().m_238990_();
        this.f_243019_ = p_243297_;
        final MutableComponent $$7 = Component.m_237110_("gui.socialInteractions.narration.hide", p_243311_);
        final MutableComponent $$8 = Component.m_237110_("gui.socialInteractions.narration.show", p_243311_);
        this.f_100542_ = p_243293_.f_91062_.m_92923_(f_240656_, 150);
        this.f_100543_ = p_243293_.f_91062_.m_92923_(f_240657_, 150);
        this.f_238711_ = p_243293_.f_91062_.m_92923_(this.m_240696_(false), 150);
        PlayerSocialManager $$9 = p_243293_.m_91266_();
        boolean $$10 = p_243293_.m_168022_().m_142594_(p_243293_.m_91090_());
        boolean bl = $$11 = !p_243293_.f_91074_.m_20148_().equals(p_243288_);
        if ($$11 && $$10 && !$$9.m_100688_(p_243288_)) {
            this.f_238614_ = new ImageButton(0, 0, 20, 20, 0, 0, 20, f_238820_, 64, 64, p_238875_ -> p_243293_.m_91152_(new ChatReportScreen(p_238872_.f_91080_, $$6, p_243288_)), new Button.OnTooltip(){

                @Override
                public void m_93752_(Button p_170090_, PoseStack p_170091_, int p_170092_, int p_170093_) {
                    PlayerEntry.this.f_100544_ += p_243293_.m_91297_();
                    if (PlayerEntry.this.f_100544_ >= 10.0f) {
                        p_243214_.m_100777_(() -> PlayerEntry.m_100588_(p_243214_, p_170091_, PlayerEntry.this.f_238711_, p_170092_, p_170093_));
                    }
                }

                @Override
                public void m_142753_(Consumer<Component> p_170088_) {
                    p_170088_.accept(PlayerEntry.this.m_240696_(true));
                }
            }, Component.m_237115_("gui.socialInteractions.report")){

                @Override
                protected MutableComponent m_5646_() {
                    return PlayerEntry.this.m_100594_(super.m_5646_());
                }
            };
            this.f_100540_ = new ImageButton(0, 0, 20, 20, 0, 38, 20, SocialInteractionsScreen.f_100736_, 256, 256, p_100612_ -> {
                $$9.m_100680_(p_243288_);
                this.m_100596_(true, Component.m_237110_("gui.socialInteractions.hidden_in_chat", p_243311_));
            }, new Button.OnTooltip(){

                @Override
                public void m_93752_(Button p_170109_, PoseStack p_170110_, int p_170111_, int p_170112_) {
                    PlayerEntry.this.f_100544_ += p_243293_.m_91297_();
                    if (PlayerEntry.this.f_100544_ >= 10.0f) {
                        p_243214_.m_100777_(() -> PlayerEntry.m_100588_(p_243214_, p_170110_, PlayerEntry.this.f_100542_, p_170111_, p_170112_));
                    }
                }

                @Override
                public void m_142753_(Consumer<Component> p_170107_) {
                    p_170107_.accept($$7);
                }
            }, Component.m_237115_("gui.socialInteractions.hide")){

                @Override
                protected MutableComponent m_5646_() {
                    return PlayerEntry.this.m_100594_(super.m_5646_());
                }
            };
            this.f_100541_ = new ImageButton(0, 0, 20, 20, 20, 38, 20, SocialInteractionsScreen.f_100736_, 256, 256, p_170074_ -> {
                $$9.m_100682_(p_243288_);
                this.m_100596_(false, Component.m_237110_("gui.socialInteractions.shown_in_chat", p_243311_));
            }, new Button.OnTooltip(){

                @Override
                public void m_93752_(Button p_239193_, PoseStack p_239194_, int p_239195_, int p_239196_) {
                    PlayerEntry.this.f_100544_ += p_243293_.m_91297_();
                    if (PlayerEntry.this.f_100544_ >= 10.0f) {
                        p_243214_.m_100777_(() -> PlayerEntry.m_100588_(p_243214_, p_239194_, PlayerEntry.this.f_100543_, p_239195_, p_239196_));
                    }
                }

                @Override
                public void m_142753_(Consumer<Component> p_239523_) {
                    p_239523_.accept($$8);
                }
            }, Component.m_237115_("gui.socialInteractions.show")){

                @Override
                protected MutableComponent m_5646_() {
                    return PlayerEntry.this.m_100594_(super.m_5646_());
                }
            };
            this.f_100541_.f_93624_ = $$9.m_100686_(p_243288_);
            this.f_100540_.f_93624_ = !this.f_100541_.f_93624_;
            this.f_238614_.f_93623_ = false;
            this.f_100535_ = ImmutableList.of((Object)this.f_100540_, (Object)this.f_100541_, (Object)this.f_238614_);
        } else {
            this.f_100535_ = ImmutableList.of();
        }
    }

    Component m_240696_(boolean p_240816_) {
        if (!this.f_243019_) {
            return f_242995_;
        }
        if (!this.f_240670_) {
            return f_238715_;
        }
        if (!this.f_240676_) {
            return Component.m_237110_("gui.socialInteractions.tooltip.report.no_messages", this.f_100537_);
        }
        return p_240816_ ? Component.m_237110_("gui.socialInteractions.narration.report", this.f_100537_) : f_240655_;
    }

    @Override
    public void m_6311_(PoseStack p_100558_, int p_100559_, int p_100560_, int p_100561_, int p_100562_, int p_100563_, int p_100564_, int p_100565_, boolean p_100566_, float p_100567_) {
        int $$15;
        int $$10 = p_100561_ + 4;
        int $$11 = p_100560_ + (p_100563_ - 24) / 2;
        int $$12 = $$10 + 24 + 4;
        Component $$13 = this.m_100621_();
        if ($$13 == CommonComponents.f_237098_) {
            GuiComponent.m_93172_(p_100558_, p_100561_, p_100560_, p_100561_ + p_100562_, p_100560_ + p_100563_, f_100530_);
            int $$14 = p_100560_ + (p_100563_ - this.f_100534_.f_91062_.f_92710_) / 2;
        } else {
            GuiComponent.m_93172_(p_100558_, p_100561_, p_100560_, p_100561_ + p_100562_, p_100560_ + p_100563_, f_100531_);
            $$15 = p_100560_ + (p_100563_ - (this.f_100534_.f_91062_.f_92710_ + this.f_100534_.f_91062_.f_92710_)) / 2;
            this.f_100534_.f_91062_.m_92889_(p_100558_, $$13, $$12, $$15 + 12, f_100533_);
        }
        RenderSystem.m_157456_(0, this.f_100538_.get());
        PlayerFaceRenderer.m_240071_(p_100558_, $$10, $$11, 24);
        this.f_100534_.f_91062_.m_92883_(p_100558_, this.f_100537_, $$12, $$15, f_100532_);
        if (this.f_100539_) {
            GuiComponent.m_93172_(p_100558_, $$10, $$11, $$10 + 24, $$11 + 24, f_100529_);
        }
        if (this.f_100540_ != null && this.f_100541_ != null && this.f_238614_ != null) {
            float $$16 = this.f_100544_;
            this.f_100540_.f_93620_ = p_100561_ + (p_100562_ - this.f_100540_.m_5711_() - 4) - 20 - 4;
            this.f_100540_.f_93621_ = p_100560_ + (p_100563_ - this.f_100540_.m_93694_()) / 2;
            this.f_100540_.m_6305_(p_100558_, p_100564_, p_100565_, p_100567_);
            this.f_100541_.f_93620_ = p_100561_ + (p_100562_ - this.f_100541_.m_5711_() - 4) - 20 - 4;
            this.f_100541_.f_93621_ = p_100560_ + (p_100563_ - this.f_100541_.m_93694_()) / 2;
            this.f_100541_.m_6305_(p_100558_, p_100564_, p_100565_, p_100567_);
            this.f_238614_.f_93620_ = p_100561_ + (p_100562_ - this.f_100541_.m_5711_() - 4);
            this.f_238614_.f_93621_ = p_100560_ + (p_100563_ - this.f_100541_.m_93694_()) / 2;
            this.f_238614_.m_6305_(p_100558_, p_100564_, p_100565_, p_100567_);
            if ($$16 == this.f_100544_) {
                this.f_100544_ = 0.0f;
            }
        }
    }

    @Override
    public List<? extends GuiEventListener> m_6702_() {
        return this.f_100535_;
    }

    @Override
    public List<? extends NarratableEntry> m_142437_() {
        return this.f_100535_;
    }

    public String m_100600_() {
        return this.f_100537_;
    }

    public UUID m_100618_() {
        return this.f_100536_;
    }

    public void m_100619_(boolean p_100620_) {
        this.f_100539_ = p_100620_;
    }

    public boolean m_240725_() {
        return this.f_100539_;
    }

    public void m_240730_(boolean p_240771_) {
        this.f_240676_ = p_240771_;
        if (this.f_238614_ != null) {
            this.f_238614_.f_93623_ = this.f_240670_ && this.f_243019_ && p_240771_;
        }
        this.f_238711_ = this.f_100534_.f_91062_.m_92923_(this.m_240696_(false), 150);
    }

    public boolean m_240694_() {
        return this.f_240676_;
    }

    private void m_100596_(boolean p_100597_, Component p_100598_) {
        this.f_100541_.f_93624_ = p_100597_;
        this.f_100540_.f_93624_ = !p_100597_;
        this.f_100534_.f_91065_.m_93076_().m_93785_(p_100598_);
        this.f_100534_.m_240477_().m_168785_(p_100598_);
    }

    MutableComponent m_100594_(MutableComponent p_100595_) {
        Component $$1 = this.m_100621_();
        if ($$1 == CommonComponents.f_237098_) {
            return Component.m_237113_(this.f_100537_).m_130946_(", ").m_7220_(p_100595_);
        }
        return Component.m_237113_(this.f_100537_).m_130946_(", ").m_7220_($$1).m_130946_(", ").m_7220_(p_100595_);
    }

    private Component m_100621_() {
        boolean $$0 = this.f_100534_.m_91266_().m_100686_(this.f_100536_);
        boolean $$1 = this.f_100534_.m_91266_().m_100688_(this.f_100536_);
        if ($$1 && this.f_100539_) {
            return f_100549_;
        }
        if ($$0 && this.f_100539_) {
            return f_100548_;
        }
        if ($$1) {
            return f_100546_;
        }
        if ($$0) {
            return f_100545_;
        }
        if (this.f_100539_) {
            return f_100547_;
        }
        return CommonComponents.f_237098_;
    }

    static void m_100588_(SocialInteractionsScreen p_100589_, PoseStack p_100590_, List<FormattedCharSequence> p_100591_, int p_100592_, int p_100593_) {
        p_100589_.m_96617_(p_100590_, p_100591_, p_100592_, p_100593_);
        p_100589_.m_100777_(null);
    }
}

