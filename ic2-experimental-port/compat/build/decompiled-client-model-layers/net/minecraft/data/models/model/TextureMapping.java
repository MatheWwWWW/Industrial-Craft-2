/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 */
package net.minecraft.data.models.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class TextureMapping {
    private final Map<TextureSlot, ResourceLocation> f_125733_ = Maps.newHashMap();
    private final Set<TextureSlot> f_125734_ = Sets.newHashSet();

    public TextureMapping m_125758_(TextureSlot p_125759_, ResourceLocation p_125760_) {
        this.f_125733_.put(p_125759_, p_125760_);
        return this;
    }

    public TextureMapping m_176480_(TextureSlot p_176481_, ResourceLocation p_176482_) {
        this.f_125733_.put(p_176481_, p_176482_);
        this.f_125734_.add(p_176481_);
        return this;
    }

    public Stream<TextureSlot> m_125742_() {
        return this.f_125734_.stream();
    }

    public TextureMapping m_176477_(TextureSlot p_176478_, TextureSlot p_176479_) {
        this.f_125733_.put(p_176479_, this.f_125733_.get(p_176478_));
        return this;
    }

    public TextureMapping m_125773_(TextureSlot p_125774_, TextureSlot p_125775_) {
        this.f_125733_.put(p_125775_, this.f_125733_.get(p_125774_));
        this.f_125734_.add(p_125775_);
        return this;
    }

    public ResourceLocation m_125756_(TextureSlot p_125757_) {
        for (TextureSlot $$1 = p_125757_; $$1 != null; $$1 = $$1.m_125903_()) {
            ResourceLocation $$2 = this.f_125733_.get($$1);
            if ($$2 == null) continue;
            return $$2;
        }
        throw new IllegalStateException("Can't find texture for slot " + p_125757_);
    }

    public TextureMapping m_125785_(TextureSlot p_125786_, ResourceLocation p_125787_) {
        TextureMapping $$2 = new TextureMapping();
        $$2.f_125733_.putAll(this.f_125733_);
        $$2.f_125734_.addAll(this.f_125734_);
        $$2.m_125758_(p_125786_, p_125787_);
        return $$2;
    }

    public static TextureMapping m_125748_(Block p_125749_) {
        ResourceLocation $$1 = TextureMapping.m_125740_(p_125749_);
        return TextureMapping.m_125776_($$1);
    }

    public static TextureMapping m_125768_(Block p_125769_) {
        ResourceLocation $$1 = TextureMapping.m_125740_(p_125769_);
        return TextureMapping.m_125761_($$1);
    }

    public static TextureMapping m_125761_(ResourceLocation p_125762_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125868_, p_125762_);
    }

    public static TextureMapping m_125776_(ResourceLocation p_125777_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125867_, p_125777_);
    }

    public static TextureMapping m_125780_(Block p_125781_) {
        return TextureMapping.m_125795_(TextureSlot.f_125882_, TextureMapping.m_125740_(p_125781_));
    }

    public static TextureMapping m_125788_(ResourceLocation p_125789_) {
        return TextureMapping.m_125795_(TextureSlot.f_125882_, p_125789_);
    }

    public static TextureMapping m_125790_(Block p_125791_) {
        return TextureMapping.m_125795_(TextureSlot.f_125883_, TextureMapping.m_125740_(p_125791_));
    }

    public static TextureMapping m_125798_(ResourceLocation p_125799_) {
        return TextureMapping.m_125795_(TextureSlot.f_125883_, p_125799_);
    }

    public static TextureMapping m_125800_(Block p_125801_) {
        return TextureMapping.m_125795_(TextureSlot.f_125885_, TextureMapping.m_125740_(p_125801_));
    }

    public static TextureMapping m_125802_(ResourceLocation p_125803_) {
        return TextureMapping.m_125795_(TextureSlot.f_125885_, p_125803_);
    }

    public static TextureMapping m_125804_(Block p_125805_) {
        return TextureMapping.m_125795_(TextureSlot.f_125886_, TextureMapping.m_125740_(p_125805_));
    }

    public static TextureMapping m_176486_(ResourceLocation p_176487_) {
        return TextureMapping.m_125795_(TextureSlot.f_125886_, p_176487_);
    }

    public static TextureMapping m_125806_(Block p_125807_) {
        return TextureMapping.m_125795_(TextureSlot.f_125891_, TextureMapping.m_125740_(p_125807_));
    }

    public static TextureMapping m_125750_(Block p_125751_, Block p_125752_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125891_, TextureMapping.m_125740_(p_125751_)).m_125758_(TextureSlot.f_125892_, TextureMapping.m_125740_(p_125752_));
    }

    public static TextureMapping m_125810_(Block p_125811_) {
        return TextureMapping.m_125795_(TextureSlot.f_125887_, TextureMapping.m_125740_(p_125811_));
    }

    public static TextureMapping m_125814_(Block p_125815_) {
        return TextureMapping.m_125795_(TextureSlot.f_125890_, TextureMapping.m_125740_(p_125815_));
    }

    public static TextureMapping m_125808_(ResourceLocation p_125809_) {
        return TextureMapping.m_125795_(TextureSlot.f_125856_, p_125809_);
    }

    public static TextureMapping m_125770_(Block p_125771_, Block p_125772_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125888_, TextureMapping.m_125740_(p_125771_)).m_125758_(TextureSlot.f_125889_, TextureMapping.m_125753_(p_125772_, "_top"));
    }

    public static TextureMapping m_125795_(TextureSlot p_125796_, ResourceLocation p_125797_) {
        return new TextureMapping().m_125758_(p_125796_, p_125797_);
    }

    public static TextureMapping m_125818_(Block p_125819_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(p_125819_, "_side")).m_125758_(TextureSlot.f_125870_, TextureMapping.m_125753_(p_125819_, "_top"));
    }

    public static TextureMapping m_125822_(Block p_125823_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(p_125823_, "_side")).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(p_125823_, "_top"));
    }

    public static TextureMapping m_125824_(Block p_125825_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125875_, TextureMapping.m_125740_(p_125825_)).m_125758_(TextureSlot.f_125870_, TextureMapping.m_125753_(p_125825_, "_top"));
    }

    public static TextureMapping m_125763_(ResourceLocation p_125764_, ResourceLocation p_125765_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125875_, p_125764_).m_125758_(TextureSlot.f_125870_, p_125765_);
    }

    public static TextureMapping m_125826_(Block p_125827_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(p_125827_, "_side")).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(p_125827_, "_top")).m_125758_(TextureSlot.f_125871_, TextureMapping.m_125753_(p_125827_, "_bottom"));
    }

    public static TextureMapping m_125828_(Block p_125829_) {
        ResourceLocation $$1 = TextureMapping.m_125740_(p_125829_);
        return new TextureMapping().m_125758_(TextureSlot.f_125884_, $$1).m_125758_(TextureSlot.f_125875_, $$1).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(p_125829_, "_top")).m_125758_(TextureSlot.f_125871_, TextureMapping.m_125753_(p_125829_, "_bottom"));
    }

    public static TextureMapping m_125830_(Block p_125831_) {
        ResourceLocation $$1 = TextureMapping.m_125740_(p_125831_);
        return new TextureMapping().m_125758_(TextureSlot.f_125884_, $$1).m_125758_(TextureSlot.f_125875_, $$1).m_125758_(TextureSlot.f_125870_, TextureMapping.m_125753_(p_125831_, "_top"));
    }

    public static TextureMapping m_176483_(ResourceLocation p_176484_, ResourceLocation p_176485_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125872_, p_176484_).m_125758_(TextureSlot.f_125871_, p_176485_);
    }

    public static TextureMapping m_125832_(Block p_125833_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(p_125833_, "_top")).m_125758_(TextureSlot.f_125871_, TextureMapping.m_125753_(p_125833_, "_bottom"));
    }

    public static TextureMapping m_125834_(Block p_125835_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125869_, TextureMapping.m_125740_(p_125835_));
    }

    public static TextureMapping m_125812_(ResourceLocation p_125813_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125869_, p_125813_);
    }

    public static TextureMapping m_125836_(Block p_125837_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125858_, TextureMapping.m_125753_(p_125837_, "_0"));
    }

    public static TextureMapping m_125838_(Block p_125839_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125858_, TextureMapping.m_125753_(p_125839_, "_1"));
    }

    public static TextureMapping m_125840_(Block p_125841_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125859_, TextureMapping.m_125740_(p_125841_));
    }

    public static TextureMapping m_125842_(Block p_125843_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125862_, TextureMapping.m_125740_(p_125843_));
    }

    public static TextureMapping m_125816_(ResourceLocation p_125817_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125862_, p_125817_);
    }

    public static TextureMapping m_125743_(Item p_125744_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125869_, TextureMapping.m_125778_(p_125744_));
    }

    public static TextureMapping m_125844_(Block p_125845_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(p_125845_, "_side")).m_125758_(TextureSlot.f_125873_, TextureMapping.m_125753_(p_125845_, "_front")).m_125758_(TextureSlot.f_125874_, TextureMapping.m_125753_(p_125845_, "_back"));
    }

    public static TextureMapping m_125846_(Block p_125847_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(p_125847_, "_side")).m_125758_(TextureSlot.f_125873_, TextureMapping.m_125753_(p_125847_, "_front")).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(p_125847_, "_top")).m_125758_(TextureSlot.f_125871_, TextureMapping.m_125753_(p_125847_, "_bottom"));
    }

    public static TextureMapping m_125848_(Block p_125849_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(p_125849_, "_side")).m_125758_(TextureSlot.f_125873_, TextureMapping.m_125753_(p_125849_, "_front")).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(p_125849_, "_top"));
    }

    public static TextureMapping m_125850_(Block p_125851_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(p_125851_, "_side")).m_125758_(TextureSlot.f_125873_, TextureMapping.m_125753_(p_125851_, "_front")).m_125758_(TextureSlot.f_125870_, TextureMapping.m_125753_(p_125851_, "_end"));
    }

    public static TextureMapping m_125852_(Block p_125853_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(p_125853_, "_top"));
    }

    public static TextureMapping m_125782_(Block p_125783_, Block p_125784_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125869_, TextureMapping.m_125753_(p_125783_, "_front")).m_125758_(TextureSlot.f_125881_, TextureMapping.m_125740_(p_125784_)).m_125758_(TextureSlot.f_125880_, TextureMapping.m_125753_(p_125783_, "_top")).m_125758_(TextureSlot.f_125876_, TextureMapping.m_125753_(p_125783_, "_front")).m_125758_(TextureSlot.f_125878_, TextureMapping.m_125753_(p_125783_, "_side")).m_125758_(TextureSlot.f_125877_, TextureMapping.m_125753_(p_125783_, "_side")).m_125758_(TextureSlot.f_125879_, TextureMapping.m_125753_(p_125783_, "_front"));
    }

    public static TextureMapping m_125792_(Block p_125793_, Block p_125794_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125869_, TextureMapping.m_125753_(p_125793_, "_front")).m_125758_(TextureSlot.f_125881_, TextureMapping.m_125740_(p_125794_)).m_125758_(TextureSlot.f_125880_, TextureMapping.m_125753_(p_125793_, "_top")).m_125758_(TextureSlot.f_125876_, TextureMapping.m_125753_(p_125793_, "_front")).m_125758_(TextureSlot.f_125877_, TextureMapping.m_125753_(p_125793_, "_front")).m_125758_(TextureSlot.f_125878_, TextureMapping.m_125753_(p_125793_, "_side")).m_125758_(TextureSlot.f_125879_, TextureMapping.m_125753_(p_125793_, "_side"));
    }

    public static TextureMapping m_125736_(Block p_125737_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125864_, TextureMapping.m_125753_(p_125737_, "_log_lit")).m_125758_(TextureSlot.f_125858_, TextureMapping.m_125753_(p_125737_, "_fire"));
    }

    public static TextureMapping m_181476_(Block p_181477_, boolean p_181478_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125869_, TextureMapping.m_125753_(Blocks.f_50145_, "_side")).m_125758_(TextureSlot.f_125871_, TextureMapping.m_125753_(Blocks.f_50145_, "_bottom")).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(Blocks.f_50145_, "_top")).m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(Blocks.f_50145_, "_side")).m_125758_(TextureSlot.f_176490_, TextureMapping.m_125753_(p_181477_, p_181478_ ? "_lit" : ""));
    }

    public static TextureMapping m_176488_(ResourceLocation p_176489_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125869_, TextureMapping.m_125753_(Blocks.f_50256_, "_side")).m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(Blocks.f_50256_, "_side")).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(Blocks.f_50256_, "_top")).m_125758_(TextureSlot.f_125871_, TextureMapping.m_125753_(Blocks.f_50256_, "_bottom")).m_125758_(TextureSlot.f_176491_, TextureMapping.m_125753_(Blocks.f_50256_, "_inner")).m_125758_(TextureSlot.f_176492_, p_176489_);
    }

    public static TextureMapping m_236350_(boolean p_236351_) {
        String $$1 = p_236351_ ? "_can_summon" : "";
        return new TextureMapping().m_125758_(TextureSlot.f_125869_, TextureMapping.m_125753_(Blocks.f_220858_, "_bottom")).m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(Blocks.f_220858_, "_side")).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(Blocks.f_220858_, "_top")).m_125758_(TextureSlot.f_236352_, TextureMapping.m_125753_(Blocks.f_220858_, $$1 + "_inner_top")).m_125758_(TextureSlot.f_125871_, TextureMapping.m_125753_(Blocks.f_220858_, "_bottom"));
    }

    public static TextureMapping m_125766_(Item p_125767_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125863_, TextureMapping.m_125778_(p_125767_));
    }

    public static TextureMapping m_125738_(Block p_125739_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125863_, TextureMapping.m_125740_(p_125739_));
    }

    public static TextureMapping m_125820_(ResourceLocation p_125821_) {
        return new TextureMapping().m_125758_(TextureSlot.f_125863_, p_125821_);
    }

    public static ResourceLocation m_125740_(Block p_125741_) {
        ResourceLocation $$1 = Registry.f_122824_.m_7981_(p_125741_);
        return new ResourceLocation($$1.m_135827_(), "block/" + $$1.m_135815_());
    }

    public static ResourceLocation m_125753_(Block p_125754_, String p_125755_) {
        ResourceLocation $$2 = Registry.f_122824_.m_7981_(p_125754_);
        return new ResourceLocation($$2.m_135827_(), "block/" + $$2.m_135815_() + p_125755_);
    }

    public static ResourceLocation m_125778_(Item p_125779_) {
        ResourceLocation $$1 = Registry.f_122827_.m_7981_(p_125779_);
        return new ResourceLocation($$1.m_135827_(), "item/" + $$1.m_135815_());
    }

    public static ResourceLocation m_125745_(Item p_125746_, String p_125747_) {
        ResourceLocation $$2 = Registry.f_122827_.m_7981_(p_125746_);
        return new ResourceLocation($$2.m_135827_(), "item/" + $$2.m_135815_() + p_125747_);
    }
}

