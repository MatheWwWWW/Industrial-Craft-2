/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class EditBox
extends AbstractWidget
implements Widget,
GuiEventListener {
    public static final int f_168999_ = -1;
    public static final int f_169000_ = 1;
    private static final int f_169002_ = 1;
    private static final int f_169003_ = -3092272;
    private static final String f_169004_ = "_";
    public static final int f_169001_ = 0xE0E0E0;
    private static final int f_169005_ = -1;
    private static final int f_169006_ = -6250336;
    private static final int f_169007_ = -16777216;
    private final Font f_94092_;
    private String f_94093_ = "";
    private int f_94094_ = 32;
    private int f_94095_;
    private boolean f_94096_ = true;
    private boolean f_94097_ = true;
    private boolean f_94098_ = true;
    private boolean f_94099_;
    private int f_94100_;
    private int f_94101_;
    private int f_94102_;
    private int f_94103_ = 0xE0E0E0;
    private int f_94104_ = 0x707070;
    @Nullable
    private String f_94088_;
    @Nullable
    private Consumer<String> f_94089_;
    private Predicate<String> f_94090_ = Objects::nonNull;
    private BiFunction<String, Integer, FormattedCharSequence> f_94091_ = (p_94147_, p_94148_) -> FormattedCharSequence.m_13714_(p_94147_, Style.f_131099_);

    public EditBox(Font p_94114_, int p_94115_, int p_94116_, int p_94117_, int p_94118_, Component p_94119_) {
        this(p_94114_, p_94115_, p_94116_, p_94117_, p_94118_, null, p_94119_);
    }

    public EditBox(Font p_94106_, int p_94107_, int p_94108_, int p_94109_, int p_94110_, @Nullable EditBox p_94111_, Component p_94112_) {
        super(p_94107_, p_94108_, p_94109_, p_94110_, p_94112_);
        this.f_94092_ = p_94106_;
        if (p_94111_ != null) {
            this.m_94144_(p_94111_.m_94155_());
        }
    }

    public void m_94151_(Consumer<String> p_94152_) {
        this.f_94089_ = p_94152_;
    }

    public void m_94149_(BiFunction<String, Integer, FormattedCharSequence> p_94150_) {
        this.f_94091_ = p_94150_;
    }

    public void m_94120_() {
        ++this.f_94095_;
    }

    @Override
    protected MutableComponent m_5646_() {
        Component $$0 = this.m_6035_();
        return Component.m_237110_("gui.narrate.editBox", $$0, this.f_94093_);
    }

    public void m_94144_(String p_94145_) {
        if (!this.f_94090_.test(p_94145_)) {
            return;
        }
        this.f_94093_ = p_94145_.length() > this.f_94094_ ? p_94145_.substring(0, this.f_94094_) : p_94145_;
        this.m_94201_();
        this.m_94208_(this.f_94101_);
        this.m_94174_(p_94145_);
    }

    public String m_94155_() {
        return this.f_94093_;
    }

    public String m_94173_() {
        int $$0 = Math.min(this.f_94101_, this.f_94102_);
        int $$1 = Math.max(this.f_94101_, this.f_94102_);
        return this.f_94093_.substring($$0, $$1);
    }

    public void m_94153_(Predicate<String> p_94154_) {
        this.f_94090_ = p_94154_;
    }

    public void m_94164_(String p_94165_) {
        String $$6;
        String $$4;
        int $$5;
        int $$1 = Math.min(this.f_94101_, this.f_94102_);
        int $$2 = Math.max(this.f_94101_, this.f_94102_);
        int $$3 = this.f_94094_ - this.f_94093_.length() - ($$1 - $$2);
        if ($$3 < ($$5 = ($$4 = SharedConstants.m_136190_(p_94165_)).length())) {
            $$4 = $$4.substring(0, $$3);
            $$5 = $$3;
        }
        if (!this.f_94090_.test($$6 = new StringBuilder(this.f_94093_).replace($$1, $$2, $$4).toString())) {
            return;
        }
        this.f_94093_ = $$6;
        this.m_94196_($$1 + $$5);
        this.m_94208_(this.f_94101_);
        this.m_94174_(this.f_94093_);
    }

    private void m_94174_(String p_94175_) {
        if (this.f_94089_ != null) {
            this.f_94089_.accept(p_94175_);
        }
    }

    private void m_94217_(int p_94218_) {
        if (Screen.m_96637_()) {
            this.m_94176_(p_94218_);
        } else {
            this.m_94180_(p_94218_);
        }
    }

    public void m_94176_(int p_94177_) {
        if (this.f_94093_.isEmpty()) {
            return;
        }
        if (this.f_94102_ != this.f_94101_) {
            this.m_94164_("");
            return;
        }
        this.m_94180_(this.m_94184_(p_94177_) - this.f_94101_);
    }

    public void m_94180_(int p_94181_) {
        int $$3;
        if (this.f_94093_.isEmpty()) {
            return;
        }
        if (this.f_94102_ != this.f_94101_) {
            this.m_94164_("");
            return;
        }
        int $$1 = this.m_94220_(p_94181_);
        int $$2 = Math.min($$1, this.f_94101_);
        if ($$2 == ($$3 = Math.max($$1, this.f_94101_))) {
            return;
        }
        String $$4 = new StringBuilder(this.f_94093_).delete($$2, $$3).toString();
        if (!this.f_94090_.test($$4)) {
            return;
        }
        this.f_94093_ = $$4;
        this.m_94192_($$2);
    }

    public int m_94184_(int p_94185_) {
        return this.m_94128_(p_94185_, this.m_94207_());
    }

    private int m_94128_(int p_94129_, int p_94130_) {
        return this.m_94140_(p_94129_, p_94130_, true);
    }

    private int m_94140_(int p_94141_, int p_94142_, boolean p_94143_) {
        int $$3 = p_94142_;
        boolean $$4 = p_94141_ < 0;
        int $$5 = Math.abs(p_94141_);
        for (int $$6 = 0; $$6 < $$5; ++$$6) {
            if ($$4) {
                while (p_94143_ && $$3 > 0 && this.f_94093_.charAt($$3 - 1) == ' ') {
                    --$$3;
                }
                while ($$3 > 0 && this.f_94093_.charAt($$3 - 1) != ' ') {
                    --$$3;
                }
                continue;
            }
            int $$7 = this.f_94093_.length();
            if (($$3 = this.f_94093_.indexOf(32, $$3)) == -1) {
                $$3 = $$7;
                continue;
            }
            while (p_94143_ && $$3 < $$7 && this.f_94093_.charAt($$3) == ' ') {
                ++$$3;
            }
        }
        return $$3;
    }

    public void m_94188_(int p_94189_) {
        this.m_94192_(this.m_94220_(p_94189_));
    }

    private int m_94220_(int p_94221_) {
        return Util.m_137479_(this.f_94093_, this.f_94101_, p_94221_);
    }

    public void m_94192_(int p_94193_) {
        this.m_94196_(p_94193_);
        if (!this.f_94099_) {
            this.m_94208_(this.f_94101_);
        }
        this.m_94174_(this.f_94093_);
    }

    public void m_94196_(int p_94197_) {
        this.f_94101_ = Mth.m_14045_(p_94197_, 0, this.f_94093_.length());
    }

    public void m_94198_() {
        this.m_94192_(0);
    }

    public void m_94201_() {
        this.m_94192_(this.f_94093_.length());
    }

    @Override
    public boolean m_7933_(int p_94132_, int p_94133_, int p_94134_) {
        if (!this.m_94204_()) {
            return false;
        }
        this.f_94099_ = Screen.m_96638_();
        if (Screen.m_96634_(p_94132_)) {
            this.m_94201_();
            this.m_94208_(0);
            return true;
        }
        if (Screen.m_96632_(p_94132_)) {
            Minecraft.m_91087_().f_91068_.m_90911_(this.m_94173_());
            return true;
        }
        if (Screen.m_96630_(p_94132_)) {
            if (this.f_94098_) {
                this.m_94164_(Minecraft.m_91087_().f_91068_.m_90876_());
            }
            return true;
        }
        if (Screen.m_96628_(p_94132_)) {
            Minecraft.m_91087_().f_91068_.m_90911_(this.m_94173_());
            if (this.f_94098_) {
                this.m_94164_("");
            }
            return true;
        }
        switch (p_94132_) {
            case 263: {
                if (Screen.m_96637_()) {
                    this.m_94192_(this.m_94184_(-1));
                } else {
                    this.m_94188_(-1);
                }
                return true;
            }
            case 262: {
                if (Screen.m_96637_()) {
                    this.m_94192_(this.m_94184_(1));
                } else {
                    this.m_94188_(1);
                }
                return true;
            }
            case 259: {
                if (this.f_94098_) {
                    this.f_94099_ = false;
                    this.m_94217_(-1);
                    this.f_94099_ = Screen.m_96638_();
                }
                return true;
            }
            case 261: {
                if (this.f_94098_) {
                    this.f_94099_ = false;
                    this.m_94217_(1);
                    this.f_94099_ = Screen.m_96638_();
                }
                return true;
            }
            case 268: {
                this.m_94198_();
                return true;
            }
            case 269: {
                this.m_94201_();
                return true;
            }
        }
        return false;
    }

    public boolean m_94204_() {
        return this.m_94213_() && this.m_93696_() && this.m_94222_();
    }

    @Override
    public boolean m_5534_(char p_94122_, int p_94123_) {
        if (!this.m_94204_()) {
            return false;
        }
        if (SharedConstants.m_136188_(p_94122_)) {
            if (this.f_94098_) {
                this.m_94164_(Character.toString(p_94122_));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean m_6375_(double p_94125_, double p_94126_, int p_94127_) {
        boolean $$3;
        if (!this.m_94213_()) {
            return false;
        }
        boolean bl = $$3 = p_94125_ >= (double)this.f_93620_ && p_94125_ < (double)(this.f_93620_ + this.f_93618_) && p_94126_ >= (double)this.f_93621_ && p_94126_ < (double)(this.f_93621_ + this.f_93619_);
        if (this.f_94097_) {
            this.m_94178_($$3);
        }
        if (this.m_93696_() && $$3 && p_94127_ == 0) {
            int $$4 = Mth.m_14107_(p_94125_) - this.f_93620_;
            if (this.f_94096_) {
                $$4 -= 4;
            }
            String $$5 = this.f_94092_.m_92834_(this.f_94093_.substring(this.f_94100_), this.m_94210_());
            this.m_94192_(this.f_94092_.m_92834_($$5, $$4).length() + this.f_94100_);
            return true;
        }
        return false;
    }

    public void m_94178_(boolean p_94179_) {
        this.m_93692_(p_94179_);
    }

    @Override
    public void m_6303_(PoseStack p_94160_, int p_94161_, int p_94162_, float p_94163_) {
        if (!this.m_94213_()) {
            return;
        }
        if (this.m_94219_()) {
            int $$4 = this.m_93696_() ? -1 : -6250336;
            EditBox.m_93172_(p_94160_, this.f_93620_ - 1, this.f_93621_ - 1, this.f_93620_ + this.f_93618_ + 1, this.f_93621_ + this.f_93619_ + 1, $$4);
            EditBox.m_93172_(p_94160_, this.f_93620_, this.f_93621_, this.f_93620_ + this.f_93618_, this.f_93621_ + this.f_93619_, -16777216);
        }
        int $$5 = this.f_94098_ ? this.f_94103_ : this.f_94104_;
        int $$6 = this.f_94101_ - this.f_94100_;
        int $$7 = this.f_94102_ - this.f_94100_;
        String $$8 = this.f_94092_.m_92834_(this.f_94093_.substring(this.f_94100_), this.m_94210_());
        boolean $$9 = $$6 >= 0 && $$6 <= $$8.length();
        boolean $$10 = this.m_93696_() && this.f_94095_ / 6 % 2 == 0 && $$9;
        int $$11 = this.f_94096_ ? this.f_93620_ + 4 : this.f_93620_;
        int $$12 = this.f_94096_ ? this.f_93621_ + (this.f_93619_ - 8) / 2 : this.f_93621_;
        int $$13 = $$11;
        if ($$7 > $$8.length()) {
            $$7 = $$8.length();
        }
        if (!$$8.isEmpty()) {
            String $$14 = $$9 ? $$8.substring(0, $$6) : $$8;
            $$13 = this.f_94092_.m_92744_(p_94160_, this.f_94091_.apply($$14, this.f_94100_), $$13, $$12, $$5);
        }
        boolean $$15 = this.f_94101_ < this.f_94093_.length() || this.f_94093_.length() >= this.m_94216_();
        int $$16 = $$13;
        if (!$$9) {
            $$16 = $$6 > 0 ? $$11 + this.f_93618_ : $$11;
        } else if ($$15) {
            --$$16;
            --$$13;
        }
        if (!$$8.isEmpty() && $$9 && $$6 < $$8.length()) {
            this.f_94092_.m_92744_(p_94160_, this.f_94091_.apply($$8.substring($$6), this.f_94101_), $$13, $$12, $$5);
        }
        if (!$$15 && this.f_94088_ != null) {
            this.f_94092_.m_92750_(p_94160_, this.f_94088_, $$16 - 1, $$12, -8355712);
        }
        if ($$10) {
            if ($$15) {
                GuiComponent.m_93172_(p_94160_, $$16, $$12 - 1, $$16 + 1, $$12 + 1 + this.f_94092_.f_92710_, -3092272);
            } else {
                this.f_94092_.m_92750_(p_94160_, f_169004_, $$16, $$12, $$5);
            }
        }
        if ($$7 != $$6) {
            int $$17 = $$11 + this.f_94092_.m_92895_($$8.substring(0, $$7));
            this.m_94135_($$16, $$12 - 1, $$17 - 1, $$12 + 1 + this.f_94092_.f_92710_);
        }
    }

    private void m_94135_(int p_94136_, int p_94137_, int p_94138_, int p_94139_) {
        if (p_94136_ < p_94138_) {
            int $$4 = p_94136_;
            p_94136_ = p_94138_;
            p_94138_ = $$4;
        }
        if (p_94137_ < p_94139_) {
            int $$5 = p_94137_;
            p_94137_ = p_94139_;
            p_94139_ = $$5;
        }
        if (p_94138_ > this.f_93620_ + this.f_93618_) {
            p_94138_ = this.f_93620_ + this.f_93618_;
        }
        if (p_94136_ > this.f_93620_ + this.f_93618_) {
            p_94136_ = this.f_93620_ + this.f_93618_;
        }
        Tesselator $$6 = Tesselator.m_85913_();
        BufferBuilder $$7 = $$6.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172808_);
        RenderSystem.m_157429_(0.0f, 0.0f, 1.0f, 1.0f);
        RenderSystem.m_69472_();
        RenderSystem.m_69479_();
        RenderSystem.m_69835_(GlStateManager.LogicOp.OR_REVERSE);
        $$7.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
        $$7.m_5483_(p_94136_, p_94139_, 0.0).m_5752_();
        $$7.m_5483_(p_94138_, p_94139_, 0.0).m_5752_();
        $$7.m_5483_(p_94138_, p_94137_, 0.0).m_5752_();
        $$7.m_5483_(p_94136_, p_94137_, 0.0).m_5752_();
        $$6.m_85914_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69462_();
        RenderSystem.m_69493_();
    }

    public void m_94199_(int p_94200_) {
        this.f_94094_ = p_94200_;
        if (this.f_94093_.length() > p_94200_) {
            this.f_94093_ = this.f_94093_.substring(0, p_94200_);
            this.m_94174_(this.f_94093_);
        }
    }

    private int m_94216_() {
        return this.f_94094_;
    }

    public int m_94207_() {
        return this.f_94101_;
    }

    private boolean m_94219_() {
        return this.f_94096_;
    }

    public void m_94182_(boolean p_94183_) {
        this.f_94096_ = p_94183_;
    }

    public void m_94202_(int p_94203_) {
        this.f_94103_ = p_94203_;
    }

    public void m_94205_(int p_94206_) {
        this.f_94104_ = p_94206_;
    }

    @Override
    public boolean m_5755_(boolean p_94172_) {
        if (!this.f_93624_ || !this.f_94098_) {
            return false;
        }
        return super.m_5755_(p_94172_);
    }

    @Override
    public boolean m_5953_(double p_94157_, double p_94158_) {
        return this.f_93624_ && p_94157_ >= (double)this.f_93620_ && p_94157_ < (double)(this.f_93620_ + this.f_93618_) && p_94158_ >= (double)this.f_93621_ && p_94158_ < (double)(this.f_93621_ + this.f_93619_);
    }

    @Override
    protected void m_7207_(boolean p_94170_) {
        if (p_94170_) {
            this.f_94095_ = 0;
        }
    }

    private boolean m_94222_() {
        return this.f_94098_;
    }

    public void m_94186_(boolean p_94187_) {
        this.f_94098_ = p_94187_;
    }

    public int m_94210_() {
        return this.m_94219_() ? this.f_93618_ - 8 : this.f_93618_;
    }

    public void m_94208_(int p_94209_) {
        int $$1 = this.f_94093_.length();
        this.f_94102_ = Mth.m_14045_(p_94209_, 0, $$1);
        if (this.f_94092_ != null) {
            if (this.f_94100_ > $$1) {
                this.f_94100_ = $$1;
            }
            int $$2 = this.m_94210_();
            String $$3 = this.f_94092_.m_92834_(this.f_94093_.substring(this.f_94100_), $$2);
            int $$4 = $$3.length() + this.f_94100_;
            if (this.f_94102_ == this.f_94100_) {
                this.f_94100_ -= this.f_94092_.m_92837_(this.f_94093_, $$2, true).length();
            }
            if (this.f_94102_ > $$4) {
                this.f_94100_ += this.f_94102_ - $$4;
            } else if (this.f_94102_ <= this.f_94100_) {
                this.f_94100_ -= this.f_94100_ - this.f_94102_;
            }
            this.f_94100_ = Mth.m_14045_(this.f_94100_, 0, $$1);
        }
    }

    public void m_94190_(boolean p_94191_) {
        this.f_94097_ = p_94191_;
    }

    public boolean m_94213_() {
        return this.f_93624_;
    }

    public void m_94194_(boolean p_94195_) {
        this.f_93624_ = p_94195_;
    }

    public void m_94167_(@Nullable String p_94168_) {
        this.f_94088_ = p_94168_;
    }

    public int m_94211_(int p_94212_) {
        if (p_94212_ > this.f_94093_.length()) {
            return this.f_93620_;
        }
        return this.f_93620_ + this.f_94092_.m_92895_(this.f_94093_.substring(0, p_94212_));
    }

    public void m_94214_(int p_94215_) {
        this.f_93620_ = p_94215_;
    }

    @Override
    public void m_142291_(NarrationElementOutput p_169009_) {
        p_169009_.m_169146_(NarratedElementType.TITLE, Component.m_237110_("narration.edit_box", this.m_94155_()));
    }
}

