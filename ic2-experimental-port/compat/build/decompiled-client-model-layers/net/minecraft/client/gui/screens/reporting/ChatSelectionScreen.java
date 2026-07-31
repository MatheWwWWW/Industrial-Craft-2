/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.report.AbuseReportLimits
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.reporting;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.report.AbuseReportLimits;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.reporting.ChatSelectionLogFiller;
import net.minecraft.client.multiplayer.chat.ChatTrustLevel;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
import net.minecraft.client.multiplayer.chat.report.ChatReportBuilder;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class ChatSelectionScreen
extends Screen {
    private static final Component f_238718_ = Component.m_237115_("gui.chatSelection.title");
    private static final Component f_238622_ = Component.m_237115_("gui.chatSelection.context").m_130940_(ChatFormatting.GRAY);
    @Nullable
    private final Screen f_238797_;
    private final ReportingContext f_238651_;
    private Button f_238763_;
    private MultiLineLabel f_238785_;
    @Nullable
    private ChatSelectionList f_238686_;
    final ChatReportBuilder f_238567_;
    private final Consumer<ChatReportBuilder> f_238817_;
    private ChatSelectionLogFiller<LoggedChatMessage.Player> f_238733_;
    @Nullable
    private List<FormattedCharSequence> f_238762_;

    public ChatSelectionScreen(@Nullable Screen p_239090_, ReportingContext p_239091_, ChatReportBuilder p_239092_, Consumer<ChatReportBuilder> p_239093_) {
        super(f_238718_);
        this.f_238797_ = p_239090_;
        this.f_238651_ = p_239091_;
        this.f_238567_ = p_239092_.m_239582_();
        this.f_238817_ = p_239093_;
    }

    @Override
    protected void m_7856_() {
        this.f_238733_ = new ChatSelectionLogFiller<LoggedChatMessage.Player>(this.f_238651_.f_238743_(), this::m_241847_, LoggedChatMessage.Player.class);
        this.f_238785_ = MultiLineLabel.m_94341_(this.f_96547_, f_238622_, this.f_96543_ - 16);
        this.f_238686_ = new ChatSelectionList(this.f_96541_, (this.f_238785_.m_5770_() + 1) * this.f_96547_.f_92710_);
        this.f_238686_.m_93488_(false);
        this.m_7787_(this.f_238686_);
        this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 32, 150, 20, CommonComponents.f_130660_, p_239860_ -> this.m_7379_()));
        this.f_238763_ = this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, this.f_96544_ - 32, 150, 20, CommonComponents.f_130655_, p_239591_ -> {
            this.f_238817_.accept(this.f_238567_);
            this.m_7379_();
        }));
        this.m_239634_();
        this.m_239478_();
        this.f_238686_.m_93410_(this.f_238686_.m_93518_());
    }

    private boolean m_241847_(LoggedChatMessage p_242240_) {
        return p_242240_.m_241866_(this.f_238567_.m_239436_());
    }

    private void m_239478_() {
        int $$0 = this.f_238686_.m_239954_();
        this.f_238733_.m_239015_($$0, this.f_238686_);
    }

    void m_239043_() {
        this.m_239478_();
    }

    void m_239634_() {
        this.f_238763_.f_93623_ = !this.f_238567_.m_239716_().isEmpty();
    }

    @Override
    public void m_6305_(PoseStack p_239286_, int p_239287_, int p_239288_, float p_239289_) {
        this.m_7333_(p_239286_);
        this.f_238686_.m_6305_(p_239286_, p_239287_, p_239288_, p_239289_);
        ChatSelectionScreen.m_93215_(p_239286_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 16, 0xFFFFFF);
        AbuseReportLimits $$4 = this.f_238651_.f_238706_().m_239479_();
        int $$5 = this.f_238567_.m_239716_().size();
        int $$6 = $$4.maxReportedMessageCount();
        MutableComponent $$7 = Component.m_237110_("gui.chatSelection.selected", $$5, $$6);
        ChatSelectionScreen.m_93215_(p_239286_, this.f_96547_, $$7, this.f_96543_ / 2, 16 + this.f_96547_.f_92710_ * 3 / 2, 0xA0A0A0);
        this.f_238785_.m_6276_(p_239286_, this.f_96543_ / 2, this.f_238686_.m_239341_());
        super.m_6305_(p_239286_, p_239287_, p_239288_, p_239289_);
        if (this.f_238762_ != null) {
            this.m_96617_(p_239286_, this.f_238762_, p_239287_, p_239288_);
            this.f_238762_ = null;
        }
    }

    @Override
    public void m_7379_() {
        this.f_96541_.m_91152_(this.f_238797_);
    }

    @Override
    public Component m_142562_() {
        return CommonComponents.m_178398_(super.m_142562_(), f_238622_);
    }

    void m_239305_(@Nullable List<FormattedCharSequence> p_239306_) {
        this.f_238762_ = p_239306_;
    }

    public class ChatSelectionList
    extends ObjectSelectionList<Entry>
    implements ChatSelectionLogFiller.Output<LoggedChatMessage.Player> {
        @Nullable
        private Heading f_238832_;

        public ChatSelectionList(Minecraft p_239060_, int p_239061_) {
            super(p_239060_, ChatSelectionScreen.this.f_96543_, ChatSelectionScreen.this.f_96544_, 40, ChatSelectionScreen.this.f_96544_ - 40 - p_239061_, 16);
        }

        @Override
        public void m_93410_(double p_239021_) {
            double $$1 = this.m_93517_();
            super.m_93410_(p_239021_);
            if ((float)this.m_93518_() > 1.0E-5f && p_239021_ <= (double)1.0E-5f && !Mth.m_14082_(p_239021_, $$1)) {
                ChatSelectionScreen.this.m_239043_();
            }
        }

        @Override
        public void m_239246_(int p_242846_, LoggedChatMessage.Player p_242909_) {
            boolean $$2 = p_242909_.m_241866_(ChatSelectionScreen.this.f_238567_.m_239436_());
            ChatTrustLevel $$3 = p_242909_.f_241609_();
            GuiMessageTag $$4 = $$3.m_240405_(p_242909_.f_241690_());
            MessageEntry $$5 = new MessageEntry(p_242846_, p_242909_.m_241831_(), p_242909_.m_241813_(), $$4, $$2, true);
            this.m_239857_($$5);
            this.m_240017_(p_242909_, $$2);
        }

        private void m_240017_(LoggedChatMessage.Player p_242229_, boolean p_240019_) {
            MessageHeadingEntry $$2 = new MessageHeadingEntry(p_242229_.f_241668_(), p_242229_.m_241865_(), p_240019_);
            this.m_239857_($$2);
            Heading $$3 = new Heading(p_242229_.m_241803_(), $$2);
            if (this.f_238832_ != null && this.f_238832_.m_239747_($$3)) {
                this.m_239045_(this.f_238832_.f_238665_());
            }
            this.f_238832_ = $$3;
        }

        @Override
        public void m_239556_(Component p_239876_) {
            this.m_239857_(new PaddingEntry());
            this.m_239857_(new DividerEntry(p_239876_));
            this.m_239857_(new PaddingEntry());
            this.f_238832_ = null;
        }

        @Override
        protected int m_5756_() {
            return (this.f_93388_ + this.m_5759_()) / 2;
        }

        @Override
        public int m_5759_() {
            return Math.min(350, this.f_93388_ - 50);
        }

        public int m_239954_() {
            return Mth.m_184652_(this.f_93391_ - this.f_93390_, this.f_93387_);
        }

        @Override
        protected void m_238964_(PoseStack p_239774_, int p_239775_, int p_239776_, float p_239777_, int p_239778_, int p_239779_, int p_239780_, int p_239781_, int p_239782_) {
            Entry $$9 = (Entry)this.m_93500_(p_239778_);
            if (this.m_240326_($$9)) {
                boolean $$10 = this.m_93511_() == $$9;
                int $$11 = this.m_5694_() && $$10 ? -1 : -8355712;
                this.m_240140_(p_239774_, p_239780_, p_239781_, p_239782_, $$11, -16777216);
            }
            $$9.m_6311_(p_239774_, p_239778_, p_239780_, p_239779_, p_239781_, p_239782_, p_239775_, p_239776_, this.m_168795_() == $$9, p_239777_);
        }

        private boolean m_240326_(Entry p_240327_) {
            if (p_240327_.m_238989_()) {
                boolean $$1 = this.m_93511_() == p_240327_;
                boolean $$2 = this.m_93511_() == null;
                boolean $$3 = this.m_168795_() == p_240327_;
                return $$1 || $$2 && $$3 && p_240327_.m_240270_();
            }
            return false;
        }

        @Override
        protected void m_6778_(AbstractSelectionList.SelectionDirection p_239561_) {
            if (!this.m_239916_(p_239561_) && p_239561_ == AbstractSelectionList.SelectionDirection.UP) {
                ChatSelectionScreen.this.m_239043_();
                this.m_239916_(p_239561_);
            }
        }

        private boolean m_239916_(AbstractSelectionList.SelectionDirection p_239917_) {
            return this.m_93464_(p_239917_, Entry::m_238989_);
        }

        @Override
        public boolean m_7933_(int p_239322_, int p_239323_, int p_239324_) {
            Entry $$3 = (Entry)this.m_93511_();
            if ($$3 != null && $$3.m_7933_(p_239322_, p_239323_, p_239324_)) {
                return true;
            }
            this.m_7522_(null);
            return super.m_7933_(p_239322_, p_239323_, p_239324_);
        }

        public int m_239341_() {
            return this.f_93391_ + ((ChatSelectionScreen)ChatSelectionScreen.this).f_96547_.f_92710_;
        }

        @Override
        protected boolean m_5694_() {
            return ChatSelectionScreen.this.m_7222_() == this;
        }

        public class MessageEntry
        extends Entry {
            private static final ResourceLocation f_240226_ = new ResourceLocation("realms", "textures/gui/realms/checkmark.png");
            private static final int f_240229_ = 9;
            private static final int f_240224_ = 8;
            private static final int f_238672_ = 11;
            private static final int f_240345_ = 4;
            private final int f_238546_;
            private final FormattedText f_238660_;
            private final Component f_238609_;
            @Nullable
            private final List<FormattedCharSequence> f_238726_;
            @Nullable
            private final GuiMessageTag.Icon f_240368_;
            @Nullable
            private final List<FormattedCharSequence> f_240376_;
            private final boolean f_238811_;
            private final boolean f_238593_;

            public MessageEntry(int p_240650_, @Nullable Component p_240525_, Component p_240539_, GuiMessageTag p_240551_, boolean p_240596_, boolean p_240615_) {
                this.f_238546_ = p_240650_;
                this.f_240368_ = Util.m_214614_(p_240551_, GuiMessageTag::f_240355_);
                this.f_240376_ = p_240551_ != null && p_240551_.f_240381_() != null ? ChatSelectionScreen.this.f_96547_.m_92923_(p_240551_.f_240381_(), ChatSelectionList.this.m_5759_()) : null;
                this.f_238811_ = p_240596_;
                this.f_238593_ = p_240615_;
                FormattedText $$7 = ChatSelectionScreen.this.f_96547_.m_92854_(p_240525_, this.m_239870_() - ChatSelectionScreen.this.f_96547_.m_92852_(CommonComponents.f_238772_));
                if (p_240525_ != $$7) {
                    this.f_238660_ = FormattedText.m_130773_($$7, CommonComponents.f_238772_);
                    this.f_238726_ = ChatSelectionScreen.this.f_96547_.m_92923_(p_240525_, ChatSelectionList.this.m_5759_());
                } else {
                    this.f_238660_ = p_240525_;
                    this.f_238726_ = null;
                }
                this.f_238609_ = p_240539_;
            }

            @Override
            public void m_6311_(PoseStack p_239595_, int p_239596_, int p_239597_, int p_239598_, int p_239599_, int p_239600_, int p_239601_, int p_239602_, boolean p_239603_, float p_239604_) {
                if (this.m_239825_() && this.f_238811_) {
                    this.m_240273_(p_239595_, p_239597_, p_239598_, p_239600_);
                }
                int $$10 = p_239598_ + this.m_239492_();
                int $$11 = p_239597_ + 1 + (p_239600_ - ((ChatSelectionScreen)ChatSelectionScreen.this).f_96547_.f_92710_) / 2;
                GuiComponent.m_168756_(p_239595_, ChatSelectionScreen.this.f_96547_, Language.m_128107_().m_5536_(this.f_238660_), $$10, $$11, this.f_238811_ ? -1 : -1593835521);
                if (this.f_238726_ != null && p_239603_) {
                    ChatSelectionScreen.this.m_239305_(this.f_238726_);
                }
                int $$12 = ChatSelectionScreen.this.f_96547_.m_92852_(this.f_238660_);
                this.m_240479_(p_239595_, $$10 + $$12 + 4, p_239597_, p_239600_, p_239601_, p_239602_);
            }

            private void m_240479_(PoseStack p_240603_, int p_240566_, int p_240565_, int p_240581_, int p_240614_, int p_240612_) {
                if (this.f_240368_ != null) {
                    int $$6 = p_240565_ + (p_240581_ - this.f_240368_.f_240372_) / 2;
                    this.f_240368_.m_240420_(p_240603_, p_240566_, $$6);
                    if (this.f_240376_ != null && p_240614_ >= p_240566_ && p_240614_ <= p_240566_ + this.f_240368_.f_240358_ && p_240612_ >= $$6 && p_240612_ <= $$6 + this.f_240368_.f_240372_) {
                        ChatSelectionScreen.this.m_239305_(this.f_240376_);
                    }
                }
            }

            private void m_240273_(PoseStack p_240274_, int p_240275_, int p_240276_, int p_240277_) {
                int $$4 = p_240276_;
                int $$5 = p_240275_ + (p_240277_ - 8) / 2;
                RenderSystem.m_157456_(0, f_240226_);
                RenderSystem.m_69478_();
                GuiComponent.m_93133_(p_240274_, $$4, $$5, 0.0f, 0.0f, 9, 8, 9, 8);
                RenderSystem.m_69461_();
            }

            private int m_239870_() {
                int $$0 = this.f_240368_ != null ? this.f_240368_.f_240358_ + 4 : 0;
                return ChatSelectionList.this.m_5759_() - this.m_239492_() - 4 - $$0;
            }

            private int m_239492_() {
                return this.f_238593_ ? 11 : 0;
            }

            @Override
            public Component m_142172_() {
                return this.m_239825_() ? Component.m_237110_("narrator.select", this.f_238609_) : this.f_238609_;
            }

            @Override
            public boolean m_6375_(double p_239729_, double p_239730_, int p_239731_) {
                if (p_239731_ == 0) {
                    ChatSelectionList.this.m_6987_(null);
                    return this.m_240066_();
                }
                return false;
            }

            @Override
            public boolean m_7933_(int p_239368_, int p_239369_, int p_239370_) {
                if (p_239368_ == 257 || p_239368_ == 32 || p_239368_ == 335) {
                    return this.m_240066_();
                }
                return false;
            }

            @Override
            public boolean m_239825_() {
                return ChatSelectionScreen.this.f_238567_.m_240221_(this.f_238546_);
            }

            @Override
            public boolean m_238989_() {
                return true;
            }

            @Override
            public boolean m_240270_() {
                return this.f_238811_;
            }

            private boolean m_240066_() {
                if (this.f_238811_) {
                    ChatSelectionScreen.this.f_238567_.m_239051_(this.f_238546_);
                    ChatSelectionScreen.this.m_239634_();
                    return true;
                }
                return false;
            }
        }

        public class MessageHeadingEntry
        extends Entry {
            private static final int f_238676_ = 12;
            private final Component f_238600_;
            private final ResourceLocation f_238674_;
            private final boolean f_238556_;

            public MessageHeadingEntry(GameProfile p_240080_, Component p_240081_, boolean p_240082_) {
                this.f_238600_ = p_240081_;
                this.f_238556_ = p_240082_;
                this.f_238674_ = ChatSelectionList.this.f_93386_.m_91109_().m_240306_(p_240080_);
            }

            @Override
            public void m_6311_(PoseStack p_239156_, int p_239157_, int p_239158_, int p_239159_, int p_239160_, int p_239161_, int p_239162_, int p_239163_, boolean p_239164_, float p_239165_) {
                int $$10 = p_239159_ - 12 - 4;
                int $$11 = p_239158_ + (p_239161_ - 12) / 2;
                this.m_238955_(p_239156_, $$10, $$11, this.f_238674_);
                int $$12 = p_239158_ + 1 + (p_239161_ - ((ChatSelectionScreen)ChatSelectionScreen.this).f_96547_.f_92710_) / 2;
                GuiComponent.m_93243_(p_239156_, ChatSelectionScreen.this.f_96547_, this.f_238600_, p_239159_, $$12, this.f_238556_ ? -1 : -1593835521);
            }

            private void m_238955_(PoseStack p_238956_, int p_238957_, int p_238958_, ResourceLocation p_238959_) {
                RenderSystem.m_157456_(0, p_238959_);
                PlayerFaceRenderer.m_240071_(p_238956_, p_238957_, p_238958_, 12);
            }
        }

        record Heading(UUID f_238587_, Entry f_238665_) {
            public boolean m_239747_(Heading p_239748_) {
                return p_239748_.f_238587_.equals(this.f_238587_);
            }

            @Override
            public final String toString() {
                return ObjectMethods.bootstrap("toString", new MethodHandle[]{Heading.class, "sender;entry", "f_238587_", "f_238665_"}, this);
            }

            @Override
            public final int hashCode() {
                return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Heading.class, "sender;entry", "f_238587_", "f_238665_"}, this);
            }

            @Override
            public final boolean equals(Object p_239723_) {
                return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Heading.class, "sender;entry", "f_238587_", "f_238665_"}, this, p_239723_);
            }
        }

        public abstract class Entry
        extends ObjectSelectionList.Entry<Entry> {
            @Override
            public Component m_142172_() {
                return CommonComponents.f_237098_;
            }

            public boolean m_239825_() {
                return false;
            }

            public boolean m_238989_() {
                return false;
            }

            public boolean m_240270_() {
                return this.m_238989_();
            }
        }

        public class PaddingEntry
        extends Entry {
            @Override
            public void m_6311_(PoseStack p_240109_, int p_240110_, int p_240111_, int p_240112_, int p_240113_, int p_240114_, int p_240115_, int p_240116_, boolean p_240117_, float p_240118_) {
            }
        }

        public class DividerEntry
        extends Entry {
            private static final int f_238646_ = -6250336;
            private final Component f_238728_;

            public DividerEntry(Component p_239672_) {
                this.f_238728_ = p_239672_;
            }

            @Override
            public void m_6311_(PoseStack p_239814_, int p_239815_, int p_239816_, int p_239817_, int p_239818_, int p_239819_, int p_239820_, int p_239821_, boolean p_239822_, float p_239823_) {
                int $$10 = p_239816_ + p_239819_ / 2;
                int $$11 = p_239817_ + p_239818_ - 8;
                int $$12 = ChatSelectionScreen.this.f_96547_.m_92852_(this.f_238728_);
                int $$13 = (p_239817_ + $$11 - $$12) / 2;
                int $$14 = $$10 - ((ChatSelectionScreen)ChatSelectionScreen.this).f_96547_.f_92710_ / 2;
                GuiComponent.m_93243_(p_239814_, ChatSelectionScreen.this.f_96547_, this.f_238728_, $$13, $$14, -6250336);
            }

            @Override
            public Component m_142172_() {
                return this.f_238728_;
            }
        }
    }
}

