/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;

public class BookViewScreen
extends Screen {
    public static final int f_169687_ = 16;
    public static final int f_169688_ = 36;
    public static final int f_169689_ = 30;
    public static final BookAccess f_98251_ = new BookAccess(){

        @Override
        public int m_5732_() {
            return 0;
        }

        @Override
        public FormattedText m_7303_(int p_98306_) {
            return FormattedText.f_130760_;
        }
    };
    public static final ResourceLocation f_98252_ = new ResourceLocation("textures/gui/book.png");
    protected static final int f_169690_ = 114;
    protected static final int f_169691_ = 128;
    protected static final int f_169692_ = 192;
    protected static final int f_169693_ = 192;
    private BookAccess f_98253_;
    private int f_98254_;
    private List<FormattedCharSequence> f_98255_ = Collections.emptyList();
    private int f_98256_ = -1;
    private Component f_98257_ = CommonComponents.f_237098_;
    private PageButton f_98258_;
    private PageButton f_98259_;
    private final boolean f_98260_;

    public BookViewScreen(BookAccess p_98264_) {
        this(p_98264_, true);
    }

    public BookViewScreen() {
        this(f_98251_, false);
    }

    private BookViewScreen(BookAccess p_98266_, boolean p_98267_) {
        super(GameNarrator.f_93310_);
        this.f_98253_ = p_98266_;
        this.f_98260_ = p_98267_;
    }

    public void m_98288_(BookAccess p_98289_) {
        this.f_98253_ = p_98289_;
        this.f_98254_ = Mth.m_14045_(this.f_98254_, 0, p_98289_.m_5732_());
        this.m_98302_();
        this.f_98256_ = -1;
    }

    public boolean m_98275_(int p_98276_) {
        int $$1 = Mth.m_14045_(p_98276_, 0, this.f_98253_.m_5732_() - 1);
        if ($$1 != this.f_98254_) {
            this.f_98254_ = $$1;
            this.m_98302_();
            this.f_98256_ = -1;
            return true;
        }
        return false;
    }

    protected boolean m_7735_(int p_98295_) {
        return this.m_98275_(p_98295_);
    }

    @Override
    protected void m_7856_() {
        this.m_7829_();
        this.m_98301_();
    }

    protected void m_7829_() {
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, 196, 200, 20, CommonComponents.f_130655_, p_98299_ -> this.f_96541_.m_91152_(null)));
    }

    protected void m_98301_() {
        int $$0 = (this.f_96543_ - 192) / 2;
        int $$1 = 2;
        this.f_98258_ = this.m_142416_(new PageButton($$0 + 116, 159, true, p_98297_ -> this.m_7815_(), this.f_98260_));
        this.f_98259_ = this.m_142416_(new PageButton($$0 + 43, 159, false, p_98287_ -> this.m_7811_(), this.f_98260_));
        this.m_98302_();
    }

    private int m_98300_() {
        return this.f_98253_.m_5732_();
    }

    protected void m_7811_() {
        if (this.f_98254_ > 0) {
            --this.f_98254_;
        }
        this.m_98302_();
    }

    protected void m_7815_() {
        if (this.f_98254_ < this.m_98300_() - 1) {
            ++this.f_98254_;
        }
        this.m_98302_();
    }

    private void m_98302_() {
        this.f_98258_.f_93624_ = this.f_98254_ < this.m_98300_() - 1;
        this.f_98259_.f_93624_ = this.f_98254_ > 0;
    }

    @Override
    public boolean m_7933_(int p_98278_, int p_98279_, int p_98280_) {
        if (super.m_7933_(p_98278_, p_98279_, p_98280_)) {
            return true;
        }
        switch (p_98278_) {
            case 266: {
                this.f_98259_.m_5691_();
                return true;
            }
            case 267: {
                this.f_98258_.m_5691_();
                return true;
            }
        }
        return false;
    }

    @Override
    public void m_6305_(PoseStack p_98282_, int p_98283_, int p_98284_, float p_98285_) {
        this.m_7333_(p_98282_);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_98252_);
        int $$4 = (this.f_96543_ - 192) / 2;
        int $$5 = 2;
        this.m_93228_(p_98282_, $$4, 2, 0, 0, 192, 192);
        if (this.f_98256_ != this.f_98254_) {
            FormattedText $$6 = this.f_98253_.m_98310_(this.f_98254_);
            this.f_98255_ = this.f_96547_.m_92923_($$6, 114);
            this.f_98257_ = Component.m_237110_("book.pageIndicator", this.f_98254_ + 1, Math.max(this.m_98300_(), 1));
        }
        this.f_98256_ = this.f_98254_;
        int $$7 = this.f_96547_.m_92852_(this.f_98257_);
        this.f_96547_.m_92889_(p_98282_, this.f_98257_, $$4 - $$7 + 192 - 44, 18.0f, 0);
        int $$8 = Math.min(128 / this.f_96547_.f_92710_, this.f_98255_.size());
        for (int $$9 = 0; $$9 < $$8; ++$$9) {
            FormattedCharSequence $$10 = this.f_98255_.get($$9);
            this.f_96547_.m_92877_(p_98282_, $$10, $$4 + 36, 32 + $$9 * this.f_96547_.f_92710_, 0);
        }
        Style $$11 = this.m_98268_(p_98283_, p_98284_);
        if ($$11 != null) {
            this.m_96570_(p_98282_, $$11, p_98283_, p_98284_);
        }
        super.m_6305_(p_98282_, p_98283_, p_98284_, p_98285_);
    }

    @Override
    public boolean m_6375_(double p_98272_, double p_98273_, int p_98274_) {
        Style $$3;
        if (p_98274_ == 0 && ($$3 = this.m_98268_(p_98272_, p_98273_)) != null && this.m_5561_($$3)) {
            return true;
        }
        return super.m_6375_(p_98272_, p_98273_, p_98274_);
    }

    @Override
    public boolean m_5561_(Style p_98293_) {
        ClickEvent $$1 = p_98293_.m_131182_();
        if ($$1 == null) {
            return false;
        }
        if ($$1.m_130622_() == ClickEvent.Action.CHANGE_PAGE) {
            String $$2 = $$1.m_130623_();
            try {
                int $$3 = Integer.parseInt($$2) - 1;
                return this.m_7735_($$3);
            }
            catch (Exception exception) {
                return false;
            }
        }
        boolean $$4 = super.m_5561_(p_98293_);
        if ($$4 && $$1.m_130622_() == ClickEvent.Action.RUN_COMMAND) {
            this.m_141919_();
        }
        return $$4;
    }

    protected void m_141919_() {
        this.f_96541_.m_91152_(null);
    }

    @Nullable
    public Style m_98268_(double p_98269_, double p_98270_) {
        if (this.f_98255_.isEmpty()) {
            return null;
        }
        int $$2 = Mth.m_14107_(p_98269_ - (double)((this.f_96543_ - 192) / 2) - 36.0);
        int $$3 = Mth.m_14107_(p_98270_ - 2.0 - 30.0);
        if ($$2 < 0 || $$3 < 0) {
            return null;
        }
        int $$4 = Math.min(128 / this.f_96547_.f_92710_, this.f_98255_.size());
        if ($$2 <= 114 && $$3 < this.f_96541_.f_91062_.f_92710_ * $$4 + $$4) {
            int $$5 = $$3 / this.f_96541_.f_91062_.f_92710_;
            if ($$5 >= 0 && $$5 < this.f_98255_.size()) {
                FormattedCharSequence $$6 = this.f_98255_.get($$5);
                return this.f_96541_.f_91062_.m_92865_().m_92338_($$6, $$2);
            }
            return null;
        }
        return null;
    }

    static List<String> m_169694_(CompoundTag p_169695_) {
        ImmutableList.Builder $$1 = ImmutableList.builder();
        BookViewScreen.m_169696_(p_169695_, arg_0 -> ((ImmutableList.Builder)$$1).add(arg_0));
        return $$1.build();
    }

    public static void m_169696_(CompoundTag p_169697_, Consumer<String> p_169698_) {
        IntFunction<String> $$5;
        ListTag $$2 = p_169697_.m_128437_("pages", 8).m_6426_();
        if (Minecraft.m_91087_().m_167974_() && p_169697_.m_128425_("filtered_pages", 10)) {
            CompoundTag $$3 = p_169697_.m_128469_("filtered_pages");
            IntFunction<String> $$4 = p_169702_ -> {
                String $$3 = String.valueOf(p_169702_);
                return $$3.m_128441_($$3) ? $$3.m_128461_($$3) : $$2.m_128778_(p_169702_);
            };
        } else {
            $$5 = $$2::m_128778_;
        }
        for (int $$6 = 0; $$6 < $$2.size(); ++$$6) {
            p_169698_.accept($$5.apply($$6));
        }
    }

    public static interface BookAccess {
        public int m_5732_();

        public FormattedText m_7303_(int var1);

        default public FormattedText m_98310_(int p_98311_) {
            if (p_98311_ >= 0 && p_98311_ < this.m_5732_()) {
                return this.m_7303_(p_98311_);
            }
            return FormattedText.f_130760_;
        }

        public static BookAccess m_98308_(ItemStack p_98309_) {
            if (p_98309_.m_150930_(Items.f_42615_)) {
                return new WrittenBookAccess(p_98309_);
            }
            if (p_98309_.m_150930_(Items.f_42614_)) {
                return new WritableBookAccess(p_98309_);
            }
            return f_98251_;
        }
    }

    public static class WritableBookAccess
    implements BookAccess {
        private final List<String> f_98312_;

        public WritableBookAccess(ItemStack p_98314_) {
            this.f_98312_ = WritableBookAccess.m_98318_(p_98314_);
        }

        private static List<String> m_98318_(ItemStack p_98319_) {
            CompoundTag $$1 = p_98319_.m_41783_();
            return $$1 != null ? BookViewScreen.m_169694_($$1) : ImmutableList.of();
        }

        @Override
        public int m_5732_() {
            return this.f_98312_.size();
        }

        @Override
        public FormattedText m_7303_(int p_98317_) {
            return FormattedText.m_130775_(this.f_98312_.get(p_98317_));
        }
    }

    public static class WrittenBookAccess
    implements BookAccess {
        private final List<String> f_98320_;

        public WrittenBookAccess(ItemStack p_98322_) {
            this.f_98320_ = WrittenBookAccess.m_98326_(p_98322_);
        }

        private static List<String> m_98326_(ItemStack p_98327_) {
            CompoundTag $$1 = p_98327_.m_41783_();
            if ($$1 != null && WrittenBookItem.m_43471_($$1)) {
                return BookViewScreen.m_169694_($$1);
            }
            return ImmutableList.of((Object)Component.Serializer.m_130703_(Component.m_237115_("book.invalid.tag").m_130940_(ChatFormatting.DARK_RED)));
        }

        @Override
        public int m_5732_() {
            return this.f_98320_.size();
        }

        @Override
        public FormattedText m_7303_(int p_98325_) {
            String $$1 = this.f_98320_.get(p_98325_);
            try {
                MutableComponent $$2 = Component.Serializer.m_130701_($$1);
                if ($$2 != null) {
                    return $$2;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return FormattedText.m_130775_($$1);
        }
    }
}

