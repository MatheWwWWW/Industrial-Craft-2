/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.packs;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.util.FormattedCharSequence;

public class TransferableSelectionList
extends ObjectSelectionList<PackEntry> {
    static final ResourceLocation f_100052_ = new ResourceLocation("textures/gui/resource_packs.png");
    static final Component f_100053_ = Component.m_237115_("pack.incompatible");
    static final Component f_100054_ = Component.m_237115_("pack.incompatible.confirm.title");
    private final Component f_100055_;

    public TransferableSelectionList(Minecraft p_100058_, int p_100059_, int p_100060_, Component p_100061_) {
        super(p_100058_, p_100059_, p_100060_, 32, p_100060_ - 55 + 4, 36);
        this.f_100055_ = p_100061_;
        this.f_93394_ = false;
        Objects.requireNonNull(p_100058_.f_91062_);
        this.m_93473_(true, (int)(9.0f * 1.5f));
    }

    @Override
    protected void m_7154_(PoseStack p_100063_, int p_100064_, int p_100065_, Tesselator p_100066_) {
        MutableComponent $$4 = Component.m_237119_().m_7220_(this.f_100055_).m_130944_(ChatFormatting.UNDERLINE, ChatFormatting.BOLD);
        this.f_93386_.f_91062_.m_92889_(p_100063_, $$4, p_100064_ + this.f_93388_ / 2 - this.f_93386_.f_91062_.m_92852_($$4) / 2, Math.min(this.f_93390_ + 3, p_100065_), 0xFFFFFF);
    }

    @Override
    public int m_5759_() {
        return this.f_93388_;
    }

    @Override
    protected int m_5756_() {
        return this.f_93392_ - 6;
    }

    public static class PackEntry
    extends ObjectSelectionList.Entry<PackEntry> {
        private static final int f_170026_ = 0;
        private static final int f_170027_ = 32;
        private static final int f_170028_ = 64;
        private static final int f_170029_ = 96;
        private static final int f_170030_ = 0;
        private static final int f_170031_ = 32;
        private static final int f_170032_ = 157;
        private static final int f_170033_ = 157;
        private static final String f_170034_ = "...";
        private final TransferableSelectionList f_100077_;
        protected final Minecraft f_100075_;
        protected final Screen f_100076_;
        private final PackSelectionModel.Entry f_100078_;
        private final FormattedCharSequence f_100079_;
        private final MultiLineLabel f_100080_;
        private final FormattedCharSequence f_100081_;
        private final MultiLineLabel f_100082_;

        public PackEntry(Minecraft p_100084_, TransferableSelectionList p_100085_, Screen p_100086_, PackSelectionModel.Entry p_100087_) {
            this.f_100075_ = p_100084_;
            this.f_100076_ = p_100086_;
            this.f_100078_ = p_100087_;
            this.f_100077_ = p_100085_;
            this.f_100079_ = PackEntry.m_100104_(p_100084_, p_100087_.m_7356_());
            this.f_100080_ = PackEntry.m_100109_(p_100084_, p_100087_.m_99929_());
            this.f_100081_ = PackEntry.m_100104_(p_100084_, f_100053_);
            this.f_100082_ = PackEntry.m_100109_(p_100084_, p_100087_.m_7709_().m_10492_());
        }

        private static FormattedCharSequence m_100104_(Minecraft p_100105_, Component p_100106_) {
            int $$2 = p_100105_.f_91062_.m_92852_(p_100106_);
            if ($$2 > 157) {
                FormattedText $$3 = FormattedText.m_130773_(p_100105_.f_91062_.m_92854_(p_100106_, 157 - p_100105_.f_91062_.m_92895_(f_170034_)), FormattedText.m_130775_(f_170034_));
                return Language.m_128107_().m_5536_($$3);
            }
            return p_100106_.m_7532_();
        }

        private static MultiLineLabel m_100109_(Minecraft p_100110_, Component p_100111_) {
            return MultiLineLabel.m_94345_(p_100110_.f_91062_, p_100111_, 157, 2);
        }

        @Override
        public Component m_142172_() {
            return Component.m_237110_("narrator.select", this.f_100078_.m_7356_());
        }

        @Override
        public void m_6311_(PoseStack p_100094_, int p_100095_, int p_100096_, int p_100097_, int p_100098_, int p_100099_, int p_100100_, int p_100101_, boolean p_100102_, float p_100103_) {
            PackCompatibility $$10 = this.f_100078_.m_7709_();
            if (!$$10.m_10489_()) {
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                GuiComponent.m_93172_(p_100094_, p_100097_ - 1, p_100096_ - 1, p_100097_ + p_100098_ - 9, p_100096_ + p_100099_ + 1, -8978432);
            }
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157456_(0, this.f_100078_.m_6876_());
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 0.0f, 0.0f, 32, 32, 32, 32);
            FormattedCharSequence $$11 = this.f_100079_;
            MultiLineLabel $$12 = this.f_100080_;
            if (this.m_100088_() && (this.f_100075_.f_91066_.m_231828_().m_231551_().booleanValue() || p_100102_)) {
                RenderSystem.m_157456_(0, f_100052_);
                GuiComponent.m_93172_(p_100094_, p_100097_, p_100096_, p_100097_ + 32, p_100096_ + 32, -1601138544);
                RenderSystem.m_157427_(GameRenderer::m_172817_);
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                int $$13 = p_100100_ - p_100097_;
                int $$14 = p_100101_ - p_100096_;
                if (!this.f_100078_.m_7709_().m_10489_()) {
                    $$11 = this.f_100081_;
                    $$12 = this.f_100082_;
                }
                if (this.f_100078_.m_99930_()) {
                    if ($$13 < 32) {
                        GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 0.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 0.0f, 0.0f, 32, 32, 256, 256);
                    }
                } else {
                    if (this.f_100078_.m_99931_()) {
                        if ($$13 < 16) {
                            GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 32.0f, 32.0f, 32, 32, 256, 256);
                        } else {
                            GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 32.0f, 0.0f, 32, 32, 256, 256);
                        }
                    }
                    if (this.f_100078_.m_7802_()) {
                        if ($$13 < 32 && $$13 > 16 && $$14 < 16) {
                            GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 96.0f, 32.0f, 32, 32, 256, 256);
                        } else {
                            GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 96.0f, 0.0f, 32, 32, 256, 256);
                        }
                    }
                    if (this.f_100078_.m_7803_()) {
                        if ($$13 < 32 && $$13 > 16 && $$14 > 16) {
                            GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 64.0f, 32.0f, 32, 32, 256, 256);
                        } else {
                            GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 64.0f, 0.0f, 32, 32, 256, 256);
                        }
                    }
                }
            }
            this.f_100075_.f_91062_.m_92744_(p_100094_, $$11, p_100097_ + 32 + 2, p_100096_ + 1, 0xFFFFFF);
            $$12.m_6516_(p_100094_, p_100097_ + 32 + 2, p_100096_ + 12, 10, 0x808080);
        }

        private boolean m_100088_() {
            return !this.f_100078_.m_7867_() || !this.f_100078_.m_7844_();
        }

        @Override
        public boolean m_6375_(double p_100090_, double p_100091_, int p_100092_) {
            double $$3 = p_100090_ - (double)this.f_100077_.m_5747_();
            double $$4 = p_100091_ - (double)this.f_100077_.m_7610_(this.f_100077_.m_6702_().indexOf(this));
            if (this.m_100088_() && $$3 <= 32.0) {
                if (this.f_100078_.m_99930_()) {
                    PackCompatibility $$5 = this.f_100078_.m_7709_();
                    if ($$5.m_10489_()) {
                        this.f_100078_.m_7849_();
                    } else {
                        Component $$6 = $$5.m_10493_();
                        this.f_100075_.m_91152_(new ConfirmScreen(p_100108_ -> {
                            this.f_100075_.m_91152_(this.f_100076_);
                            if (p_100108_) {
                                this.f_100078_.m_7849_();
                            }
                        }, f_100054_, $$6));
                    }
                    return true;
                }
                if ($$3 < 16.0 && this.f_100078_.m_99931_()) {
                    this.f_100078_.m_7850_();
                    return true;
                }
                if ($$3 > 16.0 && $$4 < 16.0 && this.f_100078_.m_7802_()) {
                    this.f_100078_.m_7852_();
                    return true;
                }
                if ($$3 > 16.0 && $$4 > 16.0 && this.f_100078_.m_7803_()) {
                    this.f_100078_.m_7845_();
                    return true;
                }
            }
            return false;
        }
    }
}

