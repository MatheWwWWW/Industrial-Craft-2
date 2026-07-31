/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 */
package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class SurfaceRuleData {
    private static final SurfaceRules.RuleSource f_194779_ = SurfaceRuleData.m_194810_(Blocks.f_50016_);
    private static final SurfaceRules.RuleSource f_194780_ = SurfaceRuleData.m_194810_(Blocks.f_50752_);
    private static final SurfaceRules.RuleSource f_194781_ = SurfaceRuleData.m_194810_(Blocks.f_50287_);
    private static final SurfaceRules.RuleSource f_194782_ = SurfaceRuleData.m_194810_(Blocks.f_50288_);
    private static final SurfaceRules.RuleSource f_194783_ = SurfaceRuleData.m_194810_(Blocks.f_50352_);
    private static final SurfaceRules.RuleSource f_194784_ = SurfaceRuleData.m_194810_(Blocks.f_49993_);
    private static final SurfaceRules.RuleSource f_194785_ = SurfaceRuleData.m_194810_(Blocks.f_50394_);
    private static final SurfaceRules.RuleSource f_194786_ = SurfaceRuleData.m_194810_(Blocks.f_50069_);
    private static final SurfaceRules.RuleSource f_194787_ = SurfaceRuleData.m_194810_(Blocks.f_152550_);
    private static final SurfaceRules.RuleSource f_194788_ = SurfaceRuleData.m_194810_(Blocks.f_50493_);
    private static final SurfaceRules.RuleSource f_194789_ = SurfaceRuleData.m_194810_(Blocks.f_50599_);
    private static final SurfaceRules.RuleSource f_194790_ = SurfaceRuleData.m_194810_(Blocks.f_50546_);
    private static final SurfaceRules.RuleSource f_194791_ = SurfaceRuleData.m_194810_(Blocks.f_50195_);
    private static final SurfaceRules.RuleSource f_194792_ = SurfaceRuleData.m_194810_(Blocks.f_50440_);
    private static final SurfaceRules.RuleSource f_194793_ = SurfaceRuleData.m_194810_(Blocks.f_152497_);
    private static final SurfaceRules.RuleSource f_194794_ = SurfaceRuleData.m_194810_(Blocks.f_49994_);
    private static final SurfaceRules.RuleSource f_194795_ = SurfaceRuleData.m_194810_(Blocks.f_49992_);
    private static final SurfaceRules.RuleSource f_194796_ = SurfaceRuleData.m_194810_(Blocks.f_50062_);
    private static final SurfaceRules.RuleSource f_194797_ = SurfaceRuleData.m_194810_(Blocks.f_50354_);
    private static final SurfaceRules.RuleSource f_194798_ = SurfaceRuleData.m_194810_(Blocks.f_50127_);
    private static final SurfaceRules.RuleSource f_236556_ = SurfaceRuleData.m_194810_(Blocks.f_220864_);
    private static final SurfaceRules.RuleSource f_194799_ = SurfaceRuleData.m_194810_(Blocks.f_152499_);
    private static final SurfaceRules.RuleSource f_194800_ = SurfaceRuleData.m_194810_(Blocks.f_50126_);
    private static final SurfaceRules.RuleSource f_194801_ = SurfaceRuleData.m_194810_(Blocks.f_49990_);
    private static final SurfaceRules.RuleSource f_194802_ = SurfaceRuleData.m_194810_(Blocks.f_49991_);
    private static final SurfaceRules.RuleSource f_194803_ = SurfaceRuleData.m_194810_(Blocks.f_50134_);
    private static final SurfaceRules.RuleSource f_194804_ = SurfaceRuleData.m_194810_(Blocks.f_50135_);
    private static final SurfaceRules.RuleSource f_194771_ = SurfaceRuleData.m_194810_(Blocks.f_50136_);
    private static final SurfaceRules.RuleSource f_194772_ = SurfaceRuleData.m_194810_(Blocks.f_50137_);
    private static final SurfaceRules.RuleSource f_194773_ = SurfaceRuleData.m_194810_(Blocks.f_50730_);
    private static final SurfaceRules.RuleSource f_194774_ = SurfaceRuleData.m_194810_(Blocks.f_50692_);
    private static final SurfaceRules.RuleSource f_194775_ = SurfaceRuleData.m_194810_(Blocks.f_50690_);
    private static final SurfaceRules.RuleSource f_194776_ = SurfaceRuleData.m_194810_(Blocks.f_50451_);
    private static final SurfaceRules.RuleSource f_194777_ = SurfaceRuleData.m_194810_(Blocks.f_50699_);
    private static final SurfaceRules.RuleSource f_194778_ = SurfaceRuleData.m_194810_(Blocks.f_50259_);

    private static SurfaceRules.RuleSource m_194810_(Block p_194811_) {
        return SurfaceRules.m_189390_(p_194811_.m_49966_());
    }

    public static SurfaceRules.RuleSource m_194807_() {
        return SurfaceRuleData.m_198380_(true, false, true);
    }

    public static SurfaceRules.RuleSource m_198380_(boolean p_198381_, boolean p_198382_, boolean p_198383_) {
        SurfaceRules.ConditionSource $$3 = SurfaceRules.m_189400_(VerticalAnchor.m_158922_(97), 2);
        SurfaceRules.ConditionSource $$4 = SurfaceRules.m_189400_(VerticalAnchor.m_158922_(256), 0);
        SurfaceRules.ConditionSource $$5 = SurfaceRules.m_189422_(VerticalAnchor.m_158922_(63), -1);
        SurfaceRules.ConditionSource $$6 = SurfaceRules.m_189422_(VerticalAnchor.m_158922_(74), 1);
        SurfaceRules.ConditionSource $$7 = SurfaceRules.m_189400_(VerticalAnchor.m_158922_(60), 0);
        SurfaceRules.ConditionSource $$8 = SurfaceRules.m_189400_(VerticalAnchor.m_158922_(62), 0);
        SurfaceRules.ConditionSource $$9 = SurfaceRules.m_189400_(VerticalAnchor.m_158922_(63), 0);
        SurfaceRules.ConditionSource $$10 = SurfaceRules.m_189382_(-1, 0);
        SurfaceRules.ConditionSource $$11 = SurfaceRules.m_189382_(0, 0);
        SurfaceRules.ConditionSource $$12 = SurfaceRules.m_189419_(-6, -1);
        SurfaceRules.ConditionSource $$13 = SurfaceRules.m_189418_();
        SurfaceRules.ConditionSource $$14 = SurfaceRules.m_189416_(Biomes.f_48211_, Biomes.f_48172_);
        SurfaceRules.ConditionSource $$15 = SurfaceRules.m_189381_();
        SurfaceRules.RuleSource $$16 = SurfaceRules.m_198272_(SurfaceRules.m_189394_($$11, f_194792_), f_194788_);
        SurfaceRules.RuleSource $$17 = SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.f_189377_, f_194796_), f_194795_);
        SurfaceRules.RuleSource $$18 = SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.f_189377_, f_194786_), f_194794_);
        SurfaceRules.ConditionSource $$19 = SurfaceRules.m_189416_(Biomes.f_48166_, Biomes.f_48217_, Biomes.f_48148_);
        SurfaceRules.ConditionSource $$20 = SurfaceRules.m_189416_(Biomes.f_48203_);
        SurfaceRules.RuleSource $$21 = SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186759_), SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.m_189412_(Noises.f_189266_, -0.0125, 0.0125), f_194793_), f_194786_)), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186760_), SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.m_189412_(Noises.f_189267_, -0.05, 0.05), $$18), f_194786_)), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186765_), SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(1.0), f_194786_)), SurfaceRules.m_189394_($$19, $$17), SurfaceRules.m_189394_($$20, $$17), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_151784_), f_194786_));
        SurfaceRules.RuleSource $$22 = SurfaceRules.m_189394_(SurfaceRules.m_189412_(Noises.f_189268_, 0.45, 0.58), SurfaceRules.m_189394_($$11, f_194799_));
        SurfaceRules.RuleSource $$23 = SurfaceRules.m_189394_(SurfaceRules.m_189412_(Noises.f_189268_, 0.35, 0.6), SurfaceRules.m_189394_($$11, f_194799_));
        SurfaceRules.RuleSource $$24 = SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186757_), SurfaceRules.m_198272_(SurfaceRules.m_189394_($$15, f_194797_), SurfaceRules.m_189394_(SurfaceRules.m_189412_(Noises.f_189270_, -0.5, 0.2), f_194797_), SurfaceRules.m_189394_(SurfaceRules.m_189412_(Noises.f_189271_, -0.0625, 0.025), f_194800_), SurfaceRules.m_189394_($$11, f_194798_))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186756_), SurfaceRules.m_198272_(SurfaceRules.m_189394_($$15, f_194786_), $$22, SurfaceRules.m_189394_($$11, f_194798_))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186758_), f_194786_), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186755_), SurfaceRules.m_198272_($$22, f_194788_)), $$21, SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186768_), SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(1.75), f_194786_)), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186766_), SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(2.0), $$18), SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(1.0), f_194786_), SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(-1.0), f_194788_), $$18)), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_220595_), f_236556_), f_194788_);
        SurfaceRules.RuleSource $$25 = SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186757_), SurfaceRules.m_198272_(SurfaceRules.m_189394_($$15, f_194797_), SurfaceRules.m_189394_(SurfaceRules.m_189412_(Noises.f_189270_, 0.0, 0.2), f_194797_), SurfaceRules.m_189394_(SurfaceRules.m_189412_(Noises.f_189271_, 0.0, 0.025), f_194800_), SurfaceRules.m_189394_($$11, f_194798_))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186756_), SurfaceRules.m_198272_(SurfaceRules.m_189394_($$15, f_194786_), $$23, SurfaceRules.m_189394_($$11, f_194798_))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186758_), SurfaceRules.m_198272_(SurfaceRules.m_189394_($$15, f_194786_), SurfaceRules.m_189394_($$11, f_194798_))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186755_), SurfaceRules.m_198272_($$23, SurfaceRules.m_189394_($$11, f_194798_))), $$21, SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186768_), SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(1.75), f_194786_), SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(-0.5), f_194790_))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186766_), SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(2.0), $$18), SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(1.0), f_194786_), SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(-1.0), $$16), $$18)), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186763_, Biomes.f_186764_), SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(1.75), f_194790_), SurfaceRules.m_189394_(SurfaceRuleData.m_194808_(-0.95), f_194789_))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_48182_), SurfaceRules.m_189394_($$11, f_194798_)), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_220595_), f_236556_), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_48215_), f_194791_), $$16);
        SurfaceRules.ConditionSource $$26 = SurfaceRules.m_189412_(Noises.f_189256_, -0.909, -0.5454);
        SurfaceRules.ConditionSource $$27 = SurfaceRules.m_189412_(Noises.f_189256_, -0.1818, 0.1818);
        SurfaceRules.ConditionSource $$28 = SurfaceRules.m_189412_(Noises.f_189256_, 0.5454, 0.909);
        SurfaceRules.RuleSource $$29 = SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.f_189375_, SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186753_), SurfaceRules.m_189394_($$3, SurfaceRules.m_198272_(SurfaceRules.m_189394_($$26, f_194790_), SurfaceRules.m_189394_($$27, f_194790_), SurfaceRules.m_189394_($$28, f_194790_), $$16))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_48207_), SurfaceRules.m_189394_($$8, SurfaceRules.m_189394_(SurfaceRules.m_189392_($$9), SurfaceRules.m_189394_(SurfaceRules.m_189409_(Noises.f_189265_, 0.0), f_194801_)))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_220595_), SurfaceRules.m_189394_($$7, SurfaceRules.m_189394_(SurfaceRules.m_189392_($$9), SurfaceRules.m_189394_(SurfaceRules.m_189409_(Noises.f_189265_, 0.0), f_194801_)))))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_48159_, Biomes.f_48194_, Biomes.f_186753_), SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.f_189375_, SurfaceRules.m_198272_(SurfaceRules.m_189394_($$4, f_194782_), SurfaceRules.m_189394_($$6, SurfaceRules.m_198272_(SurfaceRules.m_189394_($$26, f_194783_), SurfaceRules.m_189394_($$27, f_194783_), SurfaceRules.m_189394_($$28, f_194783_), SurfaceRules.m_189427_())), SurfaceRules.m_189394_($$10, SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.f_189377_, f_194785_), f_194784_)), SurfaceRules.m_189394_(SurfaceRules.m_189392_($$13), f_194782_), SurfaceRules.m_189394_($$12, f_194781_), $$18)), SurfaceRules.m_189394_($$5, SurfaceRules.m_198272_(SurfaceRules.m_189394_($$9, SurfaceRules.m_189394_(SurfaceRules.m_189392_($$6), f_194782_)), SurfaceRules.m_189427_())), SurfaceRules.m_189394_(SurfaceRules.f_189376_, SurfaceRules.m_189394_($$12, f_194781_)))), SurfaceRules.m_189394_(SurfaceRules.f_189375_, SurfaceRules.m_189394_($$10, SurfaceRules.m_198272_(SurfaceRules.m_189394_($$14, SurfaceRules.m_189394_($$13, SurfaceRules.m_198272_(SurfaceRules.m_189394_($$11, f_194779_), SurfaceRules.m_189394_(SurfaceRules.m_189426_(), f_194800_), f_194801_))), $$25))), SurfaceRules.m_189394_($$12, SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.f_189375_, SurfaceRules.m_189394_($$14, SurfaceRules.m_189394_($$13, f_194801_))), SurfaceRules.m_189394_(SurfaceRules.f_189376_, $$24), SurfaceRules.m_189394_($$19, SurfaceRules.m_189394_(SurfaceRules.f_202169_, f_194796_)), SurfaceRules.m_189394_($$20, SurfaceRules.m_189394_(SurfaceRules.f_202170_, f_194796_)))), SurfaceRules.m_189394_(SurfaceRules.f_189375_, SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_186757_, Biomes.f_186758_), f_194786_), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_48166_, Biomes.f_48167_, Biomes.f_48170_), $$17), $$18)));
        ImmutableList.Builder $$30 = ImmutableList.builder();
        if (p_198382_) {
            $$30.add((Object)SurfaceRules.m_189394_(SurfaceRules.m_189392_(SurfaceRules.m_189403_("bedrock_roof", VerticalAnchor.m_158935_(5), VerticalAnchor.m_158929_())), f_194780_));
        }
        if (p_198383_) {
            $$30.add((Object)SurfaceRules.m_189394_(SurfaceRules.m_189403_("bedrock_floor", VerticalAnchor.m_158921_(), VerticalAnchor.m_158930_(5)), f_194780_));
        }
        SurfaceRules.RuleSource $$31 = SurfaceRules.m_189394_(SurfaceRules.m_189425_(), $$29);
        $$30.add((Object)(p_198381_ ? $$31 : $$29));
        $$30.add((Object)SurfaceRules.m_189394_(SurfaceRules.m_189403_("deepslate", VerticalAnchor.m_158922_(0), VerticalAnchor.m_158922_(8)), f_194787_));
        return SurfaceRules.m_198272_((SurfaceRules.RuleSource[])$$30.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    public static SurfaceRules.RuleSource m_194812_() {
        SurfaceRules.ConditionSource $$0 = SurfaceRules.m_189400_(VerticalAnchor.m_158922_(31), 0);
        SurfaceRules.ConditionSource $$1 = SurfaceRules.m_189400_(VerticalAnchor.m_158922_(32), 0);
        SurfaceRules.ConditionSource $$2 = SurfaceRules.m_189422_(VerticalAnchor.m_158922_(30), 0);
        SurfaceRules.ConditionSource $$3 = SurfaceRules.m_189392_(SurfaceRules.m_189422_(VerticalAnchor.m_158922_(35), 0));
        SurfaceRules.ConditionSource $$4 = SurfaceRules.m_189400_(VerticalAnchor.m_158935_(5), 0);
        SurfaceRules.ConditionSource $$5 = SurfaceRules.m_189418_();
        SurfaceRules.ConditionSource $$6 = SurfaceRules.m_189409_(Noises.f_189272_, -0.012);
        SurfaceRules.ConditionSource $$7 = SurfaceRules.m_189409_(Noises.f_189273_, -0.012);
        SurfaceRules.ConditionSource $$8 = SurfaceRules.m_189409_(Noises.f_189274_, -0.012);
        SurfaceRules.ConditionSource $$9 = SurfaceRules.m_189409_(Noises.f_189275_, 0.54);
        SurfaceRules.ConditionSource $$10 = SurfaceRules.m_189409_(Noises.f_189276_, 1.17);
        SurfaceRules.ConditionSource $$11 = SurfaceRules.m_189409_(Noises.f_189277_, 0.0);
        SurfaceRules.RuleSource $$12 = SurfaceRules.m_189394_($$8, SurfaceRules.m_189394_($$2, SurfaceRules.m_189394_($$3, f_194794_)));
        return SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.m_189403_("bedrock_floor", VerticalAnchor.m_158921_(), VerticalAnchor.m_158930_(5)), f_194780_), SurfaceRules.m_189394_(SurfaceRules.m_189392_(SurfaceRules.m_189403_("bedrock_roof", VerticalAnchor.m_158935_(5), VerticalAnchor.m_158929_())), f_194780_), SurfaceRules.m_189394_($$4, f_194803_), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_48175_), SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.f_189378_, f_194772_), SurfaceRules.m_189394_(SurfaceRules.f_189376_, SurfaceRules.m_198272_($$12, SurfaceRules.m_189394_($$11, f_194772_), f_194773_)))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_48199_), SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.f_189378_, SurfaceRules.m_198272_(SurfaceRules.m_189394_($$11, f_194804_), f_194771_)), SurfaceRules.m_189394_(SurfaceRules.f_189376_, SurfaceRules.m_198272_($$12, SurfaceRules.m_189394_($$11, f_194804_), f_194771_)))), SurfaceRules.m_189394_(SurfaceRules.f_189375_, SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.m_189392_($$1), SurfaceRules.m_189394_($$5, f_194802_)), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_48201_), SurfaceRules.m_189394_(SurfaceRules.m_189392_($$9), SurfaceRules.m_189394_($$0, SurfaceRules.m_198272_(SurfaceRules.m_189394_($$10, f_194774_), f_194775_)))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_48200_), SurfaceRules.m_189394_(SurfaceRules.m_189392_($$9), SurfaceRules.m_189394_($$0, SurfaceRules.m_198272_(SurfaceRules.m_189394_($$10, f_194776_), f_194777_)))))), SurfaceRules.m_189394_(SurfaceRules.m_189416_(Biomes.f_48209_), SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.f_189376_, SurfaceRules.m_189394_($$6, SurfaceRules.m_198272_(SurfaceRules.m_189394_(SurfaceRules.m_189392_($$5), SurfaceRules.m_189394_($$2, SurfaceRules.m_189394_($$3, f_194804_))), f_194803_))), SurfaceRules.m_189394_(SurfaceRules.f_189375_, SurfaceRules.m_189394_($$0, SurfaceRules.m_189394_($$3, SurfaceRules.m_189394_($$7, SurfaceRules.m_198272_(SurfaceRules.m_189394_($$1, f_194794_), SurfaceRules.m_189394_(SurfaceRules.m_189392_($$5), f_194794_)))))))), f_194803_);
    }

    public static SurfaceRules.RuleSource m_194813_() {
        return f_194778_;
    }

    public static SurfaceRules.RuleSource m_238362_() {
        return f_194779_;
    }

    private static SurfaceRules.ConditionSource m_194808_(double p_194809_) {
        return SurfaceRules.m_189412_(Noises.f_189256_, p_194809_ / 8.25, Double.MAX_VALUE);
    }
}

