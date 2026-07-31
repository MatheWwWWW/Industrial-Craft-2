/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.TextRenderingUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class RealmsSelectWorldTemplateScreen
extends RealmsScreen {
    static final Logger f_89605_ = LogUtils.getLogger();
    static final ResourceLocation f_89606_ = new ResourceLocation("realms", "textures/gui/realms/link_icons.png");
    static final ResourceLocation f_89607_ = new ResourceLocation("realms", "textures/gui/realms/trailer_icons.png");
    static final ResourceLocation f_89608_ = new ResourceLocation("realms", "textures/gui/realms/slot_frame.png");
    static final Component f_89609_ = Component.m_237115_("mco.template.info.tooltip");
    static final Component f_89610_ = Component.m_237115_("mco.template.trailer.tooltip");
    private final Consumer<WorldTemplate> f_167479_;
    WorldTemplateObjectSelectionList f_89612_;
    int f_89613_ = -1;
    private Button f_89615_;
    private Button f_89616_;
    private Button f_89617_;
    @Nullable
    Component f_89618_;
    @Nullable
    String f_89597_;
    private final RealmsServer.WorldType f_89598_;
    int f_89599_;
    @Nullable
    private Component[] f_89600_;
    private String f_89601_;
    boolean f_89602_;
    private boolean f_89603_;
    @Nullable
    List<TextRenderingUtils.Line> f_89604_;

    public RealmsSelectWorldTemplateScreen(Component p_167481_, Consumer<WorldTemplate> p_167482_, RealmsServer.WorldType p_167483_) {
        this(p_167481_, p_167482_, p_167483_, null);
    }

    public RealmsSelectWorldTemplateScreen(Component p_167485_, Consumer<WorldTemplate> p_167486_, RealmsServer.WorldType p_167487_, @Nullable WorldTemplatePaginatedList p_167488_) {
        super(p_167485_);
        this.f_167479_ = p_167486_;
        this.f_89598_ = p_167487_;
        if (p_167488_ == null) {
            this.f_89612_ = new WorldTemplateObjectSelectionList();
            this.m_89653_(new WorldTemplatePaginatedList(10));
        } else {
            this.f_89612_ = new WorldTemplateObjectSelectionList(Lists.newArrayList(p_167488_.f_87753_));
            this.m_89653_(p_167488_);
        }
    }

    public void m_89682_(Component ... p_89683_) {
        this.f_89600_ = p_89683_;
        this.f_89602_ = true;
    }

    @Override
    public boolean m_6375_(double p_89629_, double p_89630_, int p_89631_) {
        if (this.f_89603_ && this.f_89601_ != null) {
            Util.m_137581_().m_137646_("https://www.minecraft.net/realms/adventure-maps-in-1-9");
            return true;
        }
        return super.m_6375_(p_89629_, p_89630_, p_89631_);
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_89612_ = new WorldTemplateObjectSelectionList(this.f_89612_.m_89818_());
        this.f_89616_ = this.m_142416_(new Button(this.f_96543_ / 2 - 206, this.f_96544_ - 32, 100, 20, Component.m_237115_("mco.template.button.trailer"), p_89701_ -> this.m_89738_()));
        this.f_89615_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 32, 100, 20, Component.m_237115_("mco.template.button.select"), p_89696_ -> this.m_89736_()));
        Component $$0 = this.f_89598_ == RealmsServer.WorldType.MINIGAME ? CommonComponents.f_130656_ : CommonComponents.f_130660_;
        Button $$1 = new Button(this.f_96543_ / 2 + 6, this.f_96544_ - 32, 100, 20, $$0, p_89691_ -> this.m_7379_());
        this.m_142416_($$1);
        this.f_89617_ = this.m_142416_(new Button(this.f_96543_ / 2 + 112, this.f_96544_ - 32, 100, 20, Component.m_237115_("mco.template.button.publisher"), p_89679_ -> this.m_89739_()));
        this.f_89615_.f_93623_ = false;
        this.f_89616_.f_93624_ = false;
        this.f_89617_.f_93624_ = false;
        this.m_7787_(this.f_89612_);
        this.m_94725_(this.f_89612_);
    }

    @Override
    public Component m_142562_() {
        ArrayList $$0 = Lists.newArrayListWithCapacity((int)2);
        if (this.f_96539_ != null) {
            $$0.add(this.f_96539_);
        }
        if (this.f_89600_ != null) {
            $$0.addAll(Arrays.asList(this.f_89600_));
        }
        return CommonComponents.m_178391_($$0);
    }

    void m_89718_() {
        this.f_89617_.f_93624_ = this.m_89724_();
        this.f_89616_.f_93624_ = this.m_89730_();
        this.f_89615_.f_93623_ = this.m_89721_();
    }

    private boolean m_89721_() {
        return this.f_89613_ != -1;
    }

    private boolean m_89724_() {
        return this.f_89613_ != -1 && !this.m_89727_().f_87730_.isEmpty();
    }

    private WorldTemplate m_89727_() {
        return this.f_89612_.m_89811_(this.f_89613_);
    }

    private boolean m_89730_() {
        return this.f_89613_ != -1 && !this.m_89727_().f_87732_.isEmpty();
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        --this.f_89599_;
        if (this.f_89599_ < 0) {
            this.f_89599_ = 0;
        }
    }

    @Override
    public void m_7379_() {
        this.f_167479_.accept(null);
    }

    void m_89736_() {
        if (this.m_89737_()) {
            this.f_167479_.accept(this.m_89727_());
        }
    }

    private boolean m_89737_() {
        return this.f_89613_ >= 0 && this.f_89613_ < this.f_89612_.m_5773_();
    }

    private void m_89738_() {
        if (this.m_89737_()) {
            WorldTemplate $$0 = this.m_89727_();
            if (!"".equals($$0.f_87732_)) {
                Util.m_137581_().m_137646_($$0.f_87732_);
            }
        }
    }

    private void m_89739_() {
        if (this.m_89737_()) {
            WorldTemplate $$0 = this.m_89727_();
            if (!"".equals($$0.f_87730_)) {
                Util.m_137581_().m_137646_($$0.f_87730_);
            }
        }
    }

    private void m_89653_(final WorldTemplatePaginatedList p_89654_) {
        new Thread("realms-template-fetcher"){

            @Override
            public void run() {
                WorldTemplatePaginatedList $$0 = p_89654_;
                RealmsClient $$1 = RealmsClient.m_87169_();
                while ($$0 != null) {
                    Either<WorldTemplatePaginatedList, String> $$2 = RealmsSelectWorldTemplateScreen.this.m_89655_($$0, $$1);
                    $$0 = RealmsSelectWorldTemplateScreen.this.f_96541_.m_18691_(() -> {
                        if ($$2.right().isPresent()) {
                            f_89605_.error("Couldn't fetch templates: {}", $$2.right().get());
                            if (RealmsSelectWorldTemplateScreen.this.f_89612_.m_89817_()) {
                                RealmsSelectWorldTemplateScreen.this.f_89604_ = TextRenderingUtils.m_90256_(I18n.m_118938_("mco.template.select.failure", new Object[0]), new TextRenderingUtils.LineSegment[0]);
                            }
                            return null;
                        }
                        WorldTemplatePaginatedList $$1 = (WorldTemplatePaginatedList)$$2.left().get();
                        for (WorldTemplate $$2 : $$1.f_87753_) {
                            RealmsSelectWorldTemplateScreen.this.f_89612_.m_89804_($$2);
                        }
                        if ($$1.f_87753_.isEmpty()) {
                            if (RealmsSelectWorldTemplateScreen.this.f_89612_.m_89817_()) {
                                String $$3 = I18n.m_118938_("mco.template.select.none", "%link");
                                TextRenderingUtils.LineSegment $$4 = TextRenderingUtils.LineSegment.m_90281_(I18n.m_118938_("mco.template.select.none.linkTitle", new Object[0]), "https://aka.ms/MinecraftRealmsContentCreator");
                                RealmsSelectWorldTemplateScreen.this.f_89604_ = TextRenderingUtils.m_90256_($$3, $$4);
                            }
                            return null;
                        }
                        return $$1;
                    }).join();
                }
            }
        }.start();
    }

    Either<WorldTemplatePaginatedList, String> m_89655_(WorldTemplatePaginatedList p_89656_, RealmsClient p_89657_) {
        try {
            return Either.left((Object)p_89657_.m_87170_(p_89656_.f_87754_ + 1, p_89656_.f_87755_, this.f_89598_));
        }
        catch (RealmsServiceException $$2) {
            return Either.right((Object)$$2.getMessage());
        }
    }

    @Override
    public void m_6305_(PoseStack p_89639_, int p_89640_, int p_89641_, float p_89642_) {
        this.f_89618_ = null;
        this.f_89597_ = null;
        this.f_89603_ = false;
        this.m_7333_(p_89639_);
        this.f_89612_.m_6305_(p_89639_, p_89640_, p_89641_, p_89642_);
        if (this.f_89604_ != null) {
            this.m_89643_(p_89639_, p_89640_, p_89641_, this.f_89604_);
        }
        RealmsSelectWorldTemplateScreen.m_93215_(p_89639_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 13, 0xFFFFFF);
        if (this.f_89602_) {
            Component[] $$4 = this.f_89600_;
            for (int $$5 = 0; $$5 < $$4.length; ++$$5) {
                int $$6 = this.f_96547_.m_92852_($$4[$$5]);
                int $$7 = this.f_96543_ / 2 - $$6 / 2;
                int $$8 = RealmsSelectWorldTemplateScreen.m_120774_(-1 + $$5);
                if (p_89640_ < $$7 || p_89640_ > $$7 + $$6 || p_89641_ < $$8 || p_89641_ > $$8 + this.f_96547_.f_92710_) continue;
                this.f_89603_ = true;
            }
            for (int $$9 = 0; $$9 < $$4.length; ++$$9) {
                Component $$10 = $$4[$$9];
                int $$11 = 0xA0A0A0;
                if (this.f_89601_ != null) {
                    if (this.f_89603_) {
                        $$11 = 7107012;
                        $$10 = $$10.m_6881_().m_130940_(ChatFormatting.STRIKETHROUGH);
                    } else {
                        $$11 = 0x3366BB;
                    }
                }
                RealmsSelectWorldTemplateScreen.m_93215_(p_89639_, this.f_96547_, $$10, this.f_96543_ / 2, RealmsSelectWorldTemplateScreen.m_120774_(-1 + $$9), $$11);
            }
        }
        super.m_6305_(p_89639_, p_89640_, p_89641_, p_89642_);
        this.m_89648_(p_89639_, this.f_89618_, p_89640_, p_89641_);
    }

    private void m_89643_(PoseStack p_89644_, int p_89645_, int p_89646_, List<TextRenderingUtils.Line> p_89647_) {
        for (int $$4 = 0; $$4 < p_89647_.size(); ++$$4) {
            TextRenderingUtils.Line $$5 = p_89647_.get($$4);
            int $$6 = RealmsSelectWorldTemplateScreen.m_120774_(4 + $$4);
            int $$7 = $$5.f_90262_.stream().mapToInt(p_89677_ -> this.f_96547_.m_92895_(p_89677_.m_90278_())).sum();
            int $$8 = this.f_96543_ / 2 - $$7 / 2;
            for (TextRenderingUtils.LineSegment $$9 : $$5.f_90262_) {
                int $$10 = $$9.m_90284_() ? 0x3366BB : 0xFFFFFF;
                int $$11 = this.f_96547_.m_92750_(p_89644_, $$9.m_90278_(), $$8, $$6, $$10);
                if ($$9.m_90284_() && p_89645_ > $$8 && p_89645_ < $$11 && p_89646_ > $$6 - 3 && p_89646_ < $$6 + 8) {
                    this.f_89618_ = Component.m_237113_($$9.m_90285_());
                    this.f_89597_ = $$9.m_90285_();
                }
                $$8 = $$11;
            }
        }
    }

    protected void m_89648_(PoseStack p_89649_, @Nullable Component p_89650_, int p_89651_, int p_89652_) {
        if (p_89650_ == null) {
            return;
        }
        int $$4 = p_89651_ + 12;
        int $$5 = p_89652_ - 12;
        int $$6 = this.f_96547_.m_92852_(p_89650_);
        this.m_93179_(p_89649_, $$4 - 3, $$5 - 3, $$4 + $$6 + 3, $$5 + 8 + 3, -1073741824, -1073741824);
        this.f_96547_.m_92763_(p_89649_, p_89650_, $$4, $$5, 0xFFFFFF);
    }

    class WorldTemplateObjectSelectionList
    extends RealmsObjectSelectionList<Entry> {
        public WorldTemplateObjectSelectionList() {
            this(Collections.emptyList());
        }

        public WorldTemplateObjectSelectionList(Iterable<WorldTemplate> p_89795_) {
            super(RealmsSelectWorldTemplateScreen.this.f_96543_, RealmsSelectWorldTemplateScreen.this.f_96544_, RealmsSelectWorldTemplateScreen.this.f_89602_ ? RealmsSelectWorldTemplateScreen.m_120774_(1) : 32, RealmsSelectWorldTemplateScreen.this.f_96544_ - 40, 46);
            p_89795_.forEach(this::m_89804_);
        }

        public void m_89804_(WorldTemplate p_89805_) {
            this.m_7085_(new Entry(p_89805_));
        }

        @Override
        public boolean m_6375_(double p_89797_, double p_89798_, int p_89799_) {
            if (p_89799_ == 0 && p_89798_ >= (double)this.f_93390_ && p_89798_ <= (double)this.f_93391_) {
                int $$3 = this.f_93388_ / 2 - 150;
                if (RealmsSelectWorldTemplateScreen.this.f_89597_ != null) {
                    Util.m_137581_().m_137646_(RealmsSelectWorldTemplateScreen.this.f_89597_);
                }
                int $$4 = (int)Math.floor(p_89798_ - (double)this.f_93390_) - this.f_93395_ + (int)this.m_93517_() - 4;
                int $$5 = $$4 / this.f_93387_;
                if (p_89797_ >= (double)$$3 && p_89797_ < (double)this.m_5756_() && $$5 >= 0 && $$4 >= 0 && $$5 < this.m_5773_()) {
                    this.m_7109_($$5);
                    this.m_7980_($$4, $$5, p_89797_, p_89798_, this.f_93388_);
                    if ($$5 >= RealmsSelectWorldTemplateScreen.this.f_89612_.m_5773_()) {
                        return super.m_6375_(p_89797_, p_89798_, p_89799_);
                    }
                    RealmsSelectWorldTemplateScreen.this.f_89599_ += 7;
                    if (RealmsSelectWorldTemplateScreen.this.f_89599_ >= 10) {
                        RealmsSelectWorldTemplateScreen.this.m_89736_();
                    }
                    return true;
                }
            }
            return super.m_6375_(p_89797_, p_89798_, p_89799_);
        }

        @Override
        public void m_6987_(@Nullable Entry p_89807_) {
            super.m_6987_(p_89807_);
            RealmsSelectWorldTemplateScreen.this.f_89613_ = this.m_6702_().indexOf(p_89807_);
            RealmsSelectWorldTemplateScreen.this.m_89718_();
        }

        @Override
        public int m_5775_() {
            return this.m_5773_() * 46;
        }

        @Override
        public int m_5759_() {
            return 300;
        }

        @Override
        public void m_7733_(PoseStack p_89803_) {
            RealmsSelectWorldTemplateScreen.this.m_7333_(p_89803_);
        }

        @Override
        public boolean m_5694_() {
            return RealmsSelectWorldTemplateScreen.this.m_7222_() == this;
        }

        public boolean m_89817_() {
            return this.m_5773_() == 0;
        }

        public WorldTemplate m_89811_(int p_89812_) {
            return ((Entry)this.m_6702_().get((int)p_89812_)).f_89750_;
        }

        public List<WorldTemplate> m_89818_() {
            return this.m_6702_().stream().map(p_89814_ -> p_89814_.f_89750_).collect(Collectors.toList());
        }
    }

    class Entry
    extends ObjectSelectionList.Entry<Entry> {
        final WorldTemplate f_89750_;

        public Entry(WorldTemplate p_89753_) {
            this.f_89750_ = p_89753_;
        }

        @Override
        public void m_6311_(PoseStack p_89755_, int p_89756_, int p_89757_, int p_89758_, int p_89759_, int p_89760_, int p_89761_, int p_89762_, boolean p_89763_, float p_89764_) {
            this.m_89781_(p_89755_, this.f_89750_, p_89758_, p_89757_, p_89761_, p_89762_);
        }

        private void m_89781_(PoseStack p_89782_, WorldTemplate p_89783_, int p_89784_, int p_89785_, int p_89786_, int p_89787_) {
            int $$6 = p_89784_ + 45 + 20;
            RealmsSelectWorldTemplateScreen.this.f_96547_.m_92883_(p_89782_, p_89783_.f_87727_, $$6, p_89785_ + 2, 0xFFFFFF);
            RealmsSelectWorldTemplateScreen.this.f_96547_.m_92883_(p_89782_, p_89783_.f_87729_, $$6, p_89785_ + 15, 0x6C6C6C);
            RealmsSelectWorldTemplateScreen.this.f_96547_.m_92883_(p_89782_, p_89783_.f_87728_, $$6 + 227 - RealmsSelectWorldTemplateScreen.this.f_96547_.m_92895_(p_89783_.f_87728_), p_89785_ + 1, 0x6C6C6C);
            if (!("".equals(p_89783_.f_87730_) && "".equals(p_89783_.f_87732_) && "".equals(p_89783_.f_87733_))) {
                this.m_89772_(p_89782_, $$6 - 1, p_89785_ + 25, p_89786_, p_89787_, p_89783_.f_87730_, p_89783_.f_87732_, p_89783_.f_87733_);
            }
            this.m_89765_(p_89782_, p_89784_, p_89785_ + 1, p_89786_, p_89787_, p_89783_);
        }

        private void m_89765_(PoseStack p_89766_, int p_89767_, int p_89768_, int p_89769_, int p_89770_, WorldTemplate p_89771_) {
            RealmsTextureManager.m_90190_(p_89771_.f_87726_, p_89771_.f_87731_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            GuiComponent.m_93133_(p_89766_, p_89767_ + 1, p_89768_ + 1, 0.0f, 0.0f, 38, 38, 38, 38);
            RenderSystem.m_157456_(0, f_89608_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            GuiComponent.m_93133_(p_89766_, p_89767_, p_89768_, 0.0f, 0.0f, 40, 40, 40, 40);
        }

        private void m_89772_(PoseStack p_89773_, int p_89774_, int p_89775_, int p_89776_, int p_89777_, String p_89778_, String p_89779_, String p_89780_) {
            if (!"".equals(p_89780_)) {
                RealmsSelectWorldTemplateScreen.this.f_96547_.m_92883_(p_89773_, p_89780_, p_89774_, p_89775_ + 4, 0x4C4C4C);
            }
            int $$8 = "".equals(p_89780_) ? 0 : RealmsSelectWorldTemplateScreen.this.f_96547_.m_92895_(p_89780_) + 2;
            boolean $$9 = false;
            boolean $$10 = false;
            boolean $$11 = "".equals(p_89778_);
            if (p_89776_ >= p_89774_ + $$8 && p_89776_ <= p_89774_ + $$8 + 32 && p_89777_ >= p_89775_ && p_89777_ <= p_89775_ + 15 && p_89777_ < RealmsSelectWorldTemplateScreen.this.f_96544_ - 15 && p_89777_ > 32) {
                if (p_89776_ <= p_89774_ + 15 + $$8 && p_89776_ > $$8) {
                    if ($$11) {
                        $$10 = true;
                    } else {
                        $$9 = true;
                    }
                } else if (!$$11) {
                    $$10 = true;
                }
            }
            if (!$$11) {
                RenderSystem.m_157456_(0, f_89606_);
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                float $$12 = $$9 ? 15.0f : 0.0f;
                GuiComponent.m_93133_(p_89773_, p_89774_ + $$8, p_89775_, $$12, 0.0f, 15, 15, 30, 15);
            }
            if (!"".equals(p_89779_)) {
                RenderSystem.m_157456_(0, f_89607_);
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                int $$13 = p_89774_ + $$8 + ($$11 ? 0 : 17);
                float $$14 = $$10 ? 15.0f : 0.0f;
                GuiComponent.m_93133_(p_89773_, $$13, p_89775_, $$14, 0.0f, 15, 15, 30, 15);
            }
            if ($$9) {
                RealmsSelectWorldTemplateScreen.this.f_89618_ = f_89609_;
                RealmsSelectWorldTemplateScreen.this.f_89597_ = p_89778_;
            } else if ($$10 && !"".equals(p_89779_)) {
                RealmsSelectWorldTemplateScreen.this.f_89618_ = f_89610_;
                RealmsSelectWorldTemplateScreen.this.f_89597_ = p_89779_;
            }
        }

        @Override
        public Component m_142172_() {
            Component $$0 = CommonComponents.m_178396_(Component.m_237113_(this.f_89750_.f_87727_), Component.m_237110_("mco.template.select.narrate.authors", this.f_89750_.f_87729_), Component.m_237113_(this.f_89750_.f_87733_), Component.m_237110_("mco.template.select.narrate.version", this.f_89750_.f_87728_));
            return Component.m_237110_("narrator.select", $$0);
        }
    }
}

