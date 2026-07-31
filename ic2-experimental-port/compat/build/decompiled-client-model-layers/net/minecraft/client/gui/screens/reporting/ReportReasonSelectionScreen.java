/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.reporting;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.chat.report.ReportReason;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ReportReasonSelectionScreen
extends Screen {
    private static final String f_240230_ = "https://aka.ms/aboutjavareporting";
    private static final Component f_238588_ = Component.m_237115_("gui.abuseReport.reason.title");
    private static final Component f_238612_ = Component.m_237115_("gui.abuseReport.reason.description");
    private static final Component f_240235_ = Component.m_237115_("gui.chatReport.read_info");
    private static final int f_238753_ = 95;
    private static final int f_238813_ = 150;
    private static final int f_238582_ = 20;
    private static final int f_238542_ = 320;
    private static final int f_238652_ = 4;
    @Nullable
    private final Screen f_238643_;
    @Nullable
    private ReasonSelectionList f_238613_;
    @Nullable
    ReportReason f_240344_;
    private final Consumer<ReportReason> f_238626_;

    public ReportReasonSelectionScreen(@Nullable Screen p_239438_, @Nullable ReportReason p_239439_, Consumer<ReportReason> p_239440_) {
        super(f_238588_);
        this.f_238643_ = p_239438_;
        this.f_240344_ = p_239439_;
        this.f_238626_ = p_239440_;
    }

    @Override
    protected void m_7856_() {
        this.f_238613_ = new ReasonSelectionList(this.f_96541_);
        this.f_238613_.m_93488_(false);
        this.m_7787_(this.f_238613_);
        ReasonSelectionList.Entry $$0 = Util.m_214614_(this.f_240344_, this.f_238613_::m_239167_);
        this.f_238613_.m_6987_($$0);
        int $$1 = this.f_96543_ / 2 - 150 - 5;
        this.m_142416_(new Button($$1, this.m_240065_(), 150, 20, f_240235_, p_239174_ -> this.f_96541_.m_91152_(new ConfirmLinkScreen(p_239035_ -> {
            if (p_239035_) {
                Util.m_137581_().m_137646_(f_240230_);
            }
            this.f_96541_.m_91152_(this);
        }, f_240230_, true))));
        int $$2 = this.f_96543_ / 2 + 5;
        this.m_142416_(new Button($$2, this.m_240065_(), 150, 20, CommonComponents.f_130655_, p_239301_ -> {
            ReasonSelectionList.Entry $$1 = (ReasonSelectionList.Entry)this.f_238613_.m_93511_();
            if ($$1 != null) {
                this.f_238626_.accept($$1.m_239824_());
            }
            this.f_96541_.m_91152_(this.f_238643_);
        }));
        super.m_7856_();
    }

    @Override
    public void m_6305_(PoseStack p_239451_, int p_239452_, int p_239453_, float p_239454_) {
        this.m_7333_(p_239451_);
        this.f_238613_.m_6305_(p_239451_, p_239452_, p_239453_, p_239454_);
        ReportReasonSelectionScreen.m_93215_(p_239451_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 16, 0xFFFFFF);
        super.m_6305_(p_239451_, p_239452_, p_239453_, p_239454_);
        ReportReasonSelectionScreen.m_93172_(p_239451_, this.m_239885_(), this.m_239996_(), this.m_239650_(), this.m_239592_(), 0x7F000000);
        ReportReasonSelectionScreen.m_93243_(p_239451_, this.f_96547_, f_238612_, this.m_239885_() + 4, this.m_239996_() + 4, -8421505);
        ReasonSelectionList.Entry $$4 = (ReasonSelectionList.Entry)this.f_238613_.m_93511_();
        if ($$4 != null) {
            int $$5 = this.m_239885_() + 4 + 16;
            int $$6 = this.m_239650_() - 4;
            int $$7 = this.m_239996_() + 4 + this.f_96547_.f_92710_ + 2;
            int $$8 = this.m_239592_() - 4;
            int $$9 = $$6 - $$5;
            int $$10 = $$8 - $$7;
            int $$11 = this.f_96547_.m_239133_($$4.f_238519_.m_240151_(), $$9);
            this.f_96547_.m_92857_($$4.f_238519_.m_240151_(), $$5, $$7 + ($$10 - $$11) / 2, $$9, -1);
        }
    }

    private int m_240065_() {
        return this.f_96544_ - 20 - 4;
    }

    private int m_239885_() {
        return (this.f_96543_ - 320) / 2;
    }

    private int m_239650_() {
        return (this.f_96543_ + 320) / 2;
    }

    private int m_239996_() {
        return this.f_96544_ - 95 + 4;
    }

    private int m_239592_() {
        return this.m_240065_() - 4;
    }

    @Override
    public void m_7379_() {
        this.f_96541_.m_91152_(this.f_238643_);
    }

    public class ReasonSelectionList
    extends ObjectSelectionList<Entry> {
        public ReasonSelectionList(Minecraft p_239715_) {
            super(p_239715_, ReportReasonSelectionScreen.this.f_96543_, ReportReasonSelectionScreen.this.f_96544_, 40, ReportReasonSelectionScreen.this.f_96544_ - 95, 18);
            for (ReportReason $$2 : ReportReason.values()) {
                if (!$$2.m_242666_()) continue;
                this.m_7085_(new Entry($$2));
            }
        }

        @Nullable
        public Entry m_239167_(ReportReason p_239168_) {
            return this.m_6702_().stream().filter(p_239293_ -> p_239293_.f_238519_ == p_239168_).findFirst().orElse(null);
        }

        @Override
        public int m_5759_() {
            return 320;
        }

        @Override
        protected int m_5756_() {
            return this.m_93520_() - 2;
        }

        @Override
        protected boolean m_5694_() {
            return ReportReasonSelectionScreen.this.m_7222_() == this;
        }

        @Override
        public void m_6987_(@Nullable Entry p_240601_) {
            super.m_6987_(p_240601_);
            ReportReasonSelectionScreen.this.f_240344_ = p_240601_ != null ? p_240601_.m_239824_() : null;
        }

        public class Entry
        extends ObjectSelectionList.Entry<Entry> {
            final ReportReason f_238519_;

            public Entry(ReportReason p_239267_) {
                this.f_238519_ = p_239267_;
            }

            @Override
            public void m_6311_(PoseStack p_239397_, int p_239398_, int p_239399_, int p_239400_, int p_239401_, int p_239402_, int p_239403_, int p_239404_, boolean p_239405_, float p_239406_) {
                int $$10 = p_239400_ + 1;
                int $$11 = p_239399_ + (p_239402_ - ((ReportReasonSelectionScreen)ReportReasonSelectionScreen.this).f_96547_.f_92710_) / 2 + 1;
                GuiComponent.m_93243_(p_239397_, ReportReasonSelectionScreen.this.f_96547_, this.f_238519_.m_239342_(), $$10, $$11, -1);
            }

            @Override
            public Component m_142172_() {
                return Component.m_237110_("gui.abuseReport.reason.narration", this.f_238519_.m_239342_(), this.f_238519_.m_240151_());
            }

            @Override
            public boolean m_6375_(double p_240021_, double p_240022_, int p_240023_) {
                if (p_240023_ == 0) {
                    ReasonSelectionList.this.m_6987_(this);
                    return true;
                }
                return false;
            }

            public ReportReason m_239824_() {
                return this.f_238519_;
            }
        }
    }
}

