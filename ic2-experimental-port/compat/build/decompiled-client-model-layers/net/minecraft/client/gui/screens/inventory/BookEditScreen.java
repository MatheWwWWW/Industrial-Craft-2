/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class BookEditScreen
extends Screen {
    private static final int f_169682_ = 114;
    private static final int f_169683_ = 128;
    private static final int f_169685_ = 192;
    private static final int f_169686_ = 192;
    private static final Component f_98060_ = Component.m_237115_("book.editTitle");
    private static final Component f_98061_ = Component.m_237115_("book.finalizeWarning");
    private static final FormattedCharSequence f_98062_ = FormattedCharSequence.m_13714_("_", Style.f_131099_.m_131140_(ChatFormatting.BLACK));
    private static final FormattedCharSequence f_98063_ = FormattedCharSequence.m_13714_("_", Style.f_131099_.m_131140_(ChatFormatting.GRAY));
    private final Player f_98064_;
    private final ItemStack f_98065_;
    private boolean f_98066_;
    private boolean f_98067_;
    private int f_98068_;
    private int f_98069_;
    private final List<String> f_98070_ = Lists.newArrayList();
    private String f_98071_ = "";
    private final TextFieldHelper f_98072_ = new TextFieldHelper(this::m_98191_, this::m_98158_, this::m_98180_, this::m_98147_, p_98179_ -> p_98179_.length() < 1024 && this.f_96547_.m_92920_((String)p_98179_, 114) <= 128);
    private final TextFieldHelper f_98073_ = new TextFieldHelper(() -> this.f_98071_, p_98175_ -> {
        this.f_98071_ = p_98175_;
    }, this::m_98180_, this::m_98147_, p_98170_ -> p_98170_.length() < 16);
    private long f_98048_;
    private int f_98049_ = -1;
    private PageButton f_98050_;
    private PageButton f_98051_;
    private Button f_98052_;
    private Button f_98053_;
    private Button f_98054_;
    private Button f_98055_;
    private final InteractionHand f_98056_;
    @Nullable
    private DisplayCache f_98057_ = DisplayCache.f_98192_;
    private Component f_98058_ = CommonComponents.f_237098_;
    private final Component f_98059_;

    public BookEditScreen(Player p_98076_, ItemStack p_98077_, InteractionHand p_98078_) {
        super(GameNarrator.f_93310_);
        this.f_98064_ = p_98076_;
        this.f_98065_ = p_98077_;
        this.f_98056_ = p_98078_;
        CompoundTag $$3 = p_98077_.m_41783_();
        if ($$3 != null) {
            BookViewScreen.m_169696_($$3, this.f_98070_::add);
        }
        if (this.f_98070_.isEmpty()) {
            this.f_98070_.add("");
        }
        this.f_98059_ = Component.m_237110_("book.byAuthor", p_98076_.m_7755_()).m_130940_(ChatFormatting.DARK_GRAY);
    }

    private void m_98147_(String p_98148_) {
        if (this.f_96541_ != null) {
            TextFieldHelper.m_95155_(this.f_96541_, p_98148_);
        }
    }

    private String m_98180_() {
        return this.f_96541_ != null ? TextFieldHelper.m_95169_(this.f_96541_) : "";
    }

    private int m_98181_() {
        return this.f_98070_.size();
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        ++this.f_98068_;
    }

    @Override
    protected void m_7856_() {
        this.m_98080_();
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_98053_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, 196, 98, 20, Component.m_237115_("book.signButton"), p_98177_ -> {
            this.f_98067_ = true;
            this.m_98184_();
        }));
        this.f_98052_ = this.m_142416_(new Button(this.f_96543_ / 2 + 2, 196, 98, 20, CommonComponents.f_130655_, p_98173_ -> {
            this.f_96541_.m_91152_(null);
            this.m_98160_(false);
        }));
        this.f_98054_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, 196, 98, 20, Component.m_237115_("book.finalizeButton"), p_98168_ -> {
            if (this.f_98067_) {
                this.m_98160_(true);
                this.f_96541_.m_91152_(null);
            }
        }));
        this.f_98055_ = this.m_142416_(new Button(this.f_96543_ / 2 + 2, 196, 98, 20, CommonComponents.f_130656_, p_98157_ -> {
            if (this.f_98067_) {
                this.f_98067_ = false;
            }
            this.m_98184_();
        }));
        int $$0 = (this.f_96543_ - 192) / 2;
        int $$1 = 2;
        this.f_98050_ = this.m_142416_(new PageButton($$0 + 116, 159, true, p_98144_ -> this.m_98183_(), true));
        this.f_98051_ = this.m_142416_(new PageButton($$0 + 43, 159, false, p_98113_ -> this.m_98182_(), true));
        this.m_98184_();
    }

    private void m_98182_() {
        if (this.f_98069_ > 0) {
            --this.f_98069_;
        }
        this.m_98184_();
        this.m_98081_();
    }

    private void m_98183_() {
        if (this.f_98069_ < this.m_98181_() - 1) {
            ++this.f_98069_;
        } else {
            this.m_98186_();
            if (this.f_98069_ < this.m_98181_() - 1) {
                ++this.f_98069_;
            }
        }
        this.m_98184_();
        this.m_98081_();
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    private void m_98184_() {
        this.f_98051_.f_93624_ = !this.f_98067_ && this.f_98069_ > 0;
        this.f_98050_.f_93624_ = !this.f_98067_;
        this.f_98052_.f_93624_ = !this.f_98067_;
        this.f_98053_.f_93624_ = !this.f_98067_;
        this.f_98055_.f_93624_ = this.f_98067_;
        this.f_98054_.f_93624_ = this.f_98067_;
        this.f_98054_.f_93623_ = !this.f_98071_.trim().isEmpty();
    }

    private void m_98185_() {
        ListIterator<String> $$0 = this.f_98070_.listIterator(this.f_98070_.size());
        while ($$0.hasPrevious() && $$0.previous().isEmpty()) {
            $$0.remove();
        }
    }

    private void m_98160_(boolean p_98161_) {
        if (!this.f_98066_) {
            return;
        }
        this.m_98185_();
        this.m_182574_(p_98161_);
        int $$1 = this.f_98056_ == InteractionHand.MAIN_HAND ? this.f_98064_.m_150109_().f_35977_ : 40;
        this.f_96541_.m_91403_().m_104955_(new ServerboundEditBookPacket($$1, this.f_98070_, p_98161_ ? Optional.of(this.f_98071_.trim()) : Optional.empty()));
    }

    private void m_182574_(boolean p_182575_) {
        ListTag $$1 = new ListTag();
        this.f_98070_.stream().map(StringTag::m_129297_).forEach($$1::add);
        if (!this.f_98070_.isEmpty()) {
            this.f_98065_.m_41700_("pages", $$1);
        }
        if (p_182575_) {
            this.f_98065_.m_41700_("author", StringTag.m_129297_(this.f_98064_.m_36316_().getName()));
            this.f_98065_.m_41700_("title", StringTag.m_129297_(this.f_98071_.trim()));
        }
    }

    private void m_98186_() {
        if (this.m_98181_() >= 100) {
            return;
        }
        this.f_98070_.add("");
        this.f_98066_ = true;
    }

    @Override
    public boolean m_7933_(int p_98100_, int p_98101_, int p_98102_) {
        if (super.m_7933_(p_98100_, p_98101_, p_98102_)) {
            return true;
        }
        if (this.f_98067_) {
            return this.m_98163_(p_98100_, p_98101_, p_98102_);
        }
        boolean $$3 = this.m_98152_(p_98100_, p_98101_, p_98102_);
        if ($$3) {
            this.m_98080_();
            return true;
        }
        return false;
    }

    @Override
    public boolean m_5534_(char p_98085_, int p_98086_) {
        if (super.m_5534_(p_98085_, p_98086_)) {
            return true;
        }
        if (this.f_98067_) {
            boolean $$2 = this.f_98073_.m_95143_(p_98085_);
            if ($$2) {
                this.m_98184_();
                this.f_98066_ = true;
                return true;
            }
            return false;
        }
        if (SharedConstants.m_136188_(p_98085_)) {
            this.f_98072_.m_95158_(Character.toString(p_98085_));
            this.m_98080_();
            return true;
        }
        return false;
    }

    private boolean m_98152_(int p_98153_, int p_98154_, int p_98155_) {
        if (Screen.m_96634_(p_98153_)) {
            this.f_98072_.m_95188_();
            return true;
        }
        if (Screen.m_96632_(p_98153_)) {
            this.f_98072_.m_95178_();
            return true;
        }
        if (Screen.m_96630_(p_98153_)) {
            this.f_98072_.m_95165_();
            return true;
        }
        if (Screen.m_96628_(p_98153_)) {
            this.f_98072_.m_95142_();
            return true;
        }
        TextFieldHelper.CursorStep $$3 = Screen.m_96637_() ? TextFieldHelper.CursorStep.WORD : TextFieldHelper.CursorStep.CHARACTER;
        switch (p_98153_) {
            case 259: {
                this.f_98072_.m_232572_(-1, $$3);
                return true;
            }
            case 261: {
                this.f_98072_.m_232572_(1, $$3);
                return true;
            }
            case 257: 
            case 335: {
                this.f_98072_.m_95158_("\n");
                return true;
            }
            case 263: {
                this.f_98072_.m_232575_(-1, Screen.m_96638_(), $$3);
                return true;
            }
            case 262: {
                this.f_98072_.m_232575_(1, Screen.m_96638_(), $$3);
                return true;
            }
            case 265: {
                this.m_98187_();
                return true;
            }
            case 264: {
                this.m_98188_();
                return true;
            }
            case 266: {
                this.f_98051_.m_5691_();
                return true;
            }
            case 267: {
                this.f_98050_.m_5691_();
                return true;
            }
            case 268: {
                this.m_98189_();
                return true;
            }
            case 269: {
                this.m_98190_();
                return true;
            }
        }
        return false;
    }

    private void m_98187_() {
        this.m_98097_(-1);
    }

    private void m_98188_() {
        this.m_98097_(1);
    }

    private void m_98097_(int p_98098_) {
        int $$1 = this.f_98072_.m_95194_();
        int $$2 = this.m_98079_().m_98210_($$1, p_98098_);
        this.f_98072_.m_95179_($$2, Screen.m_96638_());
    }

    private void m_98189_() {
        if (Screen.m_96637_()) {
            this.f_98072_.m_95176_(Screen.m_96638_());
        } else {
            int $$0 = this.f_98072_.m_95194_();
            int $$1 = this.m_98079_().m_98208_($$0);
            this.f_98072_.m_95179_($$1, Screen.m_96638_());
        }
    }

    private void m_98190_() {
        if (Screen.m_96637_()) {
            this.f_98072_.m_95186_(Screen.m_96638_());
        } else {
            DisplayCache $$0 = this.m_98079_();
            int $$1 = this.f_98072_.m_95194_();
            int $$2 = $$0.m_98218_($$1);
            this.f_98072_.m_95179_($$2, Screen.m_96638_());
        }
    }

    private boolean m_98163_(int p_98164_, int p_98165_, int p_98166_) {
        switch (p_98164_) {
            case 259: {
                this.f_98073_.m_95189_(-1);
                this.m_98184_();
                this.f_98066_ = true;
                return true;
            }
            case 257: 
            case 335: {
                if (!this.f_98071_.isEmpty()) {
                    this.m_98160_(true);
                    this.f_96541_.m_91152_(null);
                }
                return true;
            }
        }
        return false;
    }

    private String m_98191_() {
        if (this.f_98069_ >= 0 && this.f_98069_ < this.f_98070_.size()) {
            return this.f_98070_.get(this.f_98069_);
        }
        return "";
    }

    private void m_98158_(String p_98159_) {
        if (this.f_98069_ >= 0 && this.f_98069_ < this.f_98070_.size()) {
            this.f_98070_.set(this.f_98069_, p_98159_);
            this.f_98066_ = true;
            this.m_98080_();
        }
    }

    @Override
    public void m_6305_(PoseStack p_98104_, int p_98105_, int p_98106_, float p_98107_) {
        this.m_7333_(p_98104_);
        this.m_7522_(null);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, BookViewScreen.f_98252_);
        int $$4 = (this.f_96543_ - 192) / 2;
        int $$5 = 2;
        this.m_93228_(p_98104_, $$4, 2, 0, 0, 192, 192);
        if (this.f_98067_) {
            boolean $$6 = this.f_98068_ / 6 % 2 == 0;
            FormattedCharSequence $$7 = FormattedCharSequence.m_13696_(FormattedCharSequence.m_13714_(this.f_98071_, Style.f_131099_), $$6 ? f_98062_ : f_98063_);
            int $$8 = this.f_96547_.m_92852_(f_98060_);
            this.f_96547_.m_92889_(p_98104_, f_98060_, $$4 + 36 + (114 - $$8) / 2, 34.0f, 0);
            int $$9 = this.f_96547_.m_92724_($$7);
            this.f_96547_.m_92877_(p_98104_, $$7, $$4 + 36 + (114 - $$9) / 2, 50.0f, 0);
            int $$10 = this.f_96547_.m_92852_(this.f_98059_);
            this.f_96547_.m_92889_(p_98104_, this.f_98059_, $$4 + 36 + (114 - $$10) / 2, 60.0f, 0);
            this.f_96547_.m_92857_(f_98061_, $$4 + 36, 82, 114, 0);
        } else {
            int $$11 = this.f_96547_.m_92852_(this.f_98058_);
            this.f_96547_.m_92889_(p_98104_, this.f_98058_, $$4 - $$11 + 192 - 44, 18.0f, 0);
            DisplayCache $$12 = this.m_98079_();
            for (LineInfo $$13 : $$12.f_98197_) {
                this.f_96547_.m_92889_(p_98104_, $$13.f_98228_, $$13.f_98229_, $$13.f_98230_, -16777216);
            }
            this.m_98138_($$12.f_98198_);
            this.m_98108_(p_98104_, $$12.f_98194_, $$12.f_98195_);
        }
        super.m_6305_(p_98104_, p_98105_, p_98106_, p_98107_);
    }

    private void m_98108_(PoseStack p_98109_, Pos2i p_98110_, boolean p_98111_) {
        if (this.f_98068_ / 6 % 2 == 0) {
            p_98110_ = this.m_98145_(p_98110_);
            if (!p_98111_) {
                GuiComponent.m_93172_(p_98109_, p_98110_.f_98246_, p_98110_.f_98247_ - 1, p_98110_.f_98246_ + 1, p_98110_.f_98247_ + this.f_96547_.f_92710_, -16777216);
            } else {
                this.f_96547_.m_92883_(p_98109_, "_", p_98110_.f_98246_, p_98110_.f_98247_, 0);
            }
        }
    }

    private void m_98138_(Rect2i[] p_98139_) {
        Tesselator $$1 = Tesselator.m_85913_();
        BufferBuilder $$2 = $$1.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172808_);
        RenderSystem.m_157429_(0.0f, 0.0f, 255.0f, 255.0f);
        RenderSystem.m_69472_();
        RenderSystem.m_69479_();
        RenderSystem.m_69835_(GlStateManager.LogicOp.OR_REVERSE);
        $$2.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
        for (Rect2i $$3 : p_98139_) {
            int $$4 = $$3.m_110085_();
            int $$5 = $$3.m_110086_();
            int $$6 = $$4 + $$3.m_110090_();
            int $$7 = $$5 + $$3.m_110091_();
            $$2.m_5483_($$4, $$7, 0.0).m_5752_();
            $$2.m_5483_($$6, $$7, 0.0).m_5752_();
            $$2.m_5483_($$6, $$5, 0.0).m_5752_();
            $$2.m_5483_($$4, $$5, 0.0).m_5752_();
        }
        $$1.m_85914_();
        RenderSystem.m_69462_();
        RenderSystem.m_69493_();
    }

    private Pos2i m_98114_(Pos2i p_98115_) {
        return new Pos2i(p_98115_.f_98246_ - (this.f_96543_ - 192) / 2 - 36, p_98115_.f_98247_ - 32);
    }

    private Pos2i m_98145_(Pos2i p_98146_) {
        return new Pos2i(p_98146_.f_98246_ + (this.f_96543_ - 192) / 2 + 36, p_98146_.f_98247_ + 32);
    }

    @Override
    public boolean m_6375_(double p_98088_, double p_98089_, int p_98090_) {
        if (super.m_6375_(p_98088_, p_98089_, p_98090_)) {
            return true;
        }
        if (p_98090_ == 0) {
            long $$3 = Util.m_137550_();
            DisplayCache $$4 = this.m_98079_();
            int $$5 = $$4.m_98213_(this.f_96547_, this.m_98114_(new Pos2i((int)p_98088_, (int)p_98089_)));
            if ($$5 >= 0) {
                if ($$5 == this.f_98049_ && $$3 - this.f_98048_ < 250L) {
                    if (!this.f_98072_.m_95198_()) {
                        this.m_98141_($$5);
                    } else {
                        this.f_98072_.m_95188_();
                    }
                } else {
                    this.f_98072_.m_95179_($$5, Screen.m_96638_());
                }
                this.m_98080_();
            }
            this.f_98049_ = $$5;
            this.f_98048_ = $$3;
        }
        return true;
    }

    private void m_98141_(int p_98142_) {
        String $$1 = this.m_98191_();
        this.f_98072_.m_95147_(StringSplitter.m_92355_($$1, -1, p_98142_, false), StringSplitter.m_92355_($$1, 1, p_98142_, false));
    }

    @Override
    public boolean m_7979_(double p_98092_, double p_98093_, int p_98094_, double p_98095_, double p_98096_) {
        if (super.m_7979_(p_98092_, p_98093_, p_98094_, p_98095_, p_98096_)) {
            return true;
        }
        if (p_98094_ == 0) {
            DisplayCache $$5 = this.m_98079_();
            int $$6 = $$5.m_98213_(this.f_96547_, this.m_98114_(new Pos2i((int)p_98092_, (int)p_98093_)));
            this.f_98072_.m_95179_($$6, true);
            this.m_98080_();
        }
        return true;
    }

    private DisplayCache m_98079_() {
        if (this.f_98057_ == null) {
            this.f_98057_ = this.m_98082_();
            this.f_98058_ = Component.m_237110_("book.pageIndicator", this.f_98069_ + 1, this.m_98181_());
        }
        return this.f_98057_;
    }

    private void m_98080_() {
        this.f_98057_ = null;
    }

    private void m_98081_() {
        this.f_98072_.m_95193_();
        this.m_98080_();
    }

    private DisplayCache m_98082_() {
        Pos2i $$13;
        boolean $$9;
        String $$0 = this.m_98191_();
        if ($$0.isEmpty()) {
            return DisplayCache.f_98192_;
        }
        int $$1 = this.f_98072_.m_95194_();
        int $$2 = this.f_98072_.m_95197_();
        IntArrayList $$3 = new IntArrayList();
        ArrayList $$4 = Lists.newArrayList();
        MutableInt $$5 = new MutableInt();
        MutableBoolean $$6 = new MutableBoolean();
        StringSplitter $$7 = this.f_96547_.m_92865_();
        $$7.m_92364_($$0, 114, Style.f_131099_, true, (arg_0, arg_1, arg_2) -> this.m_98126_($$5, $$0, $$6, (IntList)$$3, $$4, arg_0, arg_1, arg_2));
        int[] $$8 = $$3.toIntArray();
        boolean bl = $$9 = $$1 == $$0.length();
        if ($$9 && $$6.isTrue()) {
            Pos2i $$10 = new Pos2i(0, $$4.size() * this.f_96547_.f_92710_);
        } else {
            int $$11 = BookEditScreen.m_98149_($$8, $$1);
            int $$12 = this.f_96547_.m_92895_($$0.substring($$8[$$11], $$1));
            $$13 = new Pos2i($$12, $$11 * this.f_96547_.f_92710_);
        }
        ArrayList $$14 = Lists.newArrayList();
        if ($$1 != $$2) {
            int $$18;
            int $$15 = Math.min($$1, $$2);
            int $$16 = Math.max($$1, $$2);
            int $$17 = BookEditScreen.m_98149_($$8, $$15);
            if ($$17 == ($$18 = BookEditScreen.m_98149_($$8, $$16))) {
                int $$19 = $$17 * this.f_96547_.f_92710_;
                int $$20 = $$8[$$17];
                $$14.add(this.m_98119_($$0, $$7, $$15, $$16, $$19, $$20));
            } else {
                int $$21 = $$17 + 1 > $$8.length ? $$0.length() : $$8[$$17 + 1];
                $$14.add(this.m_98119_($$0, $$7, $$15, $$21, $$17 * this.f_96547_.f_92710_, $$8[$$17]));
                for (int $$22 = $$17 + 1; $$22 < $$18; ++$$22) {
                    int $$23 = $$22 * this.f_96547_.f_92710_;
                    String $$24 = $$0.substring($$8[$$22], $$8[$$22 + 1]);
                    int $$25 = (int)$$7.m_92353_($$24);
                    $$14.add(this.m_98116_(new Pos2i(0, $$23), new Pos2i($$25, $$23 + this.f_96547_.f_92710_)));
                }
                $$14.add(this.m_98119_($$0, $$7, $$8[$$18], $$16, $$18 * this.f_96547_.f_92710_, $$8[$$18]));
            }
        }
        return new DisplayCache($$0, $$13, $$9, $$8, $$4.toArray(new LineInfo[0]), $$14.toArray(new Rect2i[0]));
    }

    static int m_98149_(int[] p_98150_, int p_98151_) {
        int $$2 = Arrays.binarySearch(p_98150_, p_98151_);
        if ($$2 < 0) {
            return -($$2 + 2);
        }
        return $$2;
    }

    private Rect2i m_98119_(String p_98120_, StringSplitter p_98121_, int p_98122_, int p_98123_, int p_98124_, int p_98125_) {
        String $$6 = p_98120_.substring(p_98125_, p_98122_);
        String $$7 = p_98120_.substring(p_98125_, p_98123_);
        Pos2i $$8 = new Pos2i((int)p_98121_.m_92353_($$6), p_98124_);
        Pos2i $$9 = new Pos2i((int)p_98121_.m_92353_($$7), p_98124_ + this.f_96547_.f_92710_);
        return this.m_98116_($$8, $$9);
    }

    private Rect2i m_98116_(Pos2i p_98117_, Pos2i p_98118_) {
        Pos2i $$2 = this.m_98145_(p_98117_);
        Pos2i $$3 = this.m_98145_(p_98118_);
        int $$4 = Math.min($$2.f_98246_, $$3.f_98246_);
        int $$5 = Math.max($$2.f_98246_, $$3.f_98246_);
        int $$6 = Math.min($$2.f_98247_, $$3.f_98247_);
        int $$7 = Math.max($$2.f_98247_, $$3.f_98247_);
        return new Rect2i($$4, $$6, $$5 - $$4, $$7 - $$6);
    }

    private /* synthetic */ void m_98126_(MutableInt p_98127_, String p_98128_, MutableBoolean p_98129_, IntList p_98130_, List p_98131_, Style p_98132_, int p_98133_, int p_98134_) {
        int $$8 = p_98127_.getAndIncrement();
        String $$9 = p_98128_.substring(p_98133_, p_98134_);
        p_98129_.setValue($$9.endsWith("\n"));
        String $$10 = StringUtils.stripEnd((String)$$9, (String)" \n");
        int $$11 = $$8 * this.f_96547_.f_92710_;
        Pos2i $$12 = this.m_98145_(new Pos2i(0, $$11));
        p_98130_.add(p_98133_);
        p_98131_.add(new LineInfo(p_98132_, $$10, $$12.f_98246_, $$12.f_98247_));
    }

    static class DisplayCache {
        static final DisplayCache f_98192_ = new DisplayCache("", new Pos2i(0, 0), true, new int[]{0}, new LineInfo[]{new LineInfo(Style.f_131099_, "", 0, 0)}, new Rect2i[0]);
        private final String f_98193_;
        final Pos2i f_98194_;
        final boolean f_98195_;
        private final int[] f_98196_;
        final LineInfo[] f_98197_;
        final Rect2i[] f_98198_;

        public DisplayCache(String p_98201_, Pos2i p_98202_, boolean p_98203_, int[] p_98204_, LineInfo[] p_98205_, Rect2i[] p_98206_) {
            this.f_98193_ = p_98201_;
            this.f_98194_ = p_98202_;
            this.f_98195_ = p_98203_;
            this.f_98196_ = p_98204_;
            this.f_98197_ = p_98205_;
            this.f_98198_ = p_98206_;
        }

        public int m_98213_(Font p_98214_, Pos2i p_98215_) {
            int $$2 = p_98215_.f_98247_ / p_98214_.f_92710_;
            if ($$2 < 0) {
                return 0;
            }
            if ($$2 >= this.f_98197_.length) {
                return this.f_98193_.length();
            }
            LineInfo $$3 = this.f_98197_[$$2];
            return this.f_98196_[$$2] + p_98214_.m_92865_().m_92360_($$3.f_98227_, p_98215_.f_98246_, $$3.f_98226_);
        }

        public int m_98210_(int p_98211_, int p_98212_) {
            int $$7;
            int $$2 = BookEditScreen.m_98149_(this.f_98196_, p_98211_);
            int $$3 = $$2 + p_98212_;
            if (0 <= $$3 && $$3 < this.f_98196_.length) {
                int $$4 = p_98211_ - this.f_98196_[$$2];
                int $$5 = this.f_98197_[$$3].f_98227_.length();
                int $$6 = this.f_98196_[$$3] + Math.min($$4, $$5);
            } else {
                $$7 = p_98211_;
            }
            return $$7;
        }

        public int m_98208_(int p_98209_) {
            int $$1 = BookEditScreen.m_98149_(this.f_98196_, p_98209_);
            return this.f_98196_[$$1];
        }

        public int m_98218_(int p_98219_) {
            int $$1 = BookEditScreen.m_98149_(this.f_98196_, p_98219_);
            return this.f_98196_[$$1] + this.f_98197_[$$1].f_98227_.length();
        }
    }

    static class LineInfo {
        final Style f_98226_;
        final String f_98227_;
        final Component f_98228_;
        final int f_98229_;
        final int f_98230_;

        public LineInfo(Style p_98232_, String p_98233_, int p_98234_, int p_98235_) {
            this.f_98226_ = p_98232_;
            this.f_98227_ = p_98233_;
            this.f_98229_ = p_98234_;
            this.f_98230_ = p_98235_;
            this.f_98228_ = Component.m_237113_(p_98233_).m_6270_(p_98232_);
        }
    }

    static class Pos2i {
        public final int f_98246_;
        public final int f_98247_;

        Pos2i(int p_98249_, int p_98250_) {
            this.f_98246_ = p_98249_;
            this.f_98247_ = p_98250_;
        }
    }
}

