/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BannerPattern;

public class BannerPatterns {
    public static final ResourceKey<BannerPattern> f_222726_ = BannerPatterns.m_222756_("base");
    public static final ResourceKey<BannerPattern> f_222727_ = BannerPatterns.m_222756_("square_bottom_left");
    public static final ResourceKey<BannerPattern> f_222728_ = BannerPatterns.m_222756_("square_bottom_right");
    public static final ResourceKey<BannerPattern> f_222729_ = BannerPatterns.m_222756_("square_top_left");
    public static final ResourceKey<BannerPattern> f_222730_ = BannerPatterns.m_222756_("square_top_right");
    public static final ResourceKey<BannerPattern> f_222731_ = BannerPatterns.m_222756_("stripe_bottom");
    public static final ResourceKey<BannerPattern> f_222732_ = BannerPatterns.m_222756_("stripe_top");
    public static final ResourceKey<BannerPattern> f_222733_ = BannerPatterns.m_222756_("stripe_left");
    public static final ResourceKey<BannerPattern> f_222734_ = BannerPatterns.m_222756_("stripe_right");
    public static final ResourceKey<BannerPattern> f_222735_ = BannerPatterns.m_222756_("stripe_center");
    public static final ResourceKey<BannerPattern> f_222736_ = BannerPatterns.m_222756_("stripe_middle");
    public static final ResourceKey<BannerPattern> f_222737_ = BannerPatterns.m_222756_("stripe_downright");
    public static final ResourceKey<BannerPattern> f_222738_ = BannerPatterns.m_222756_("stripe_downleft");
    public static final ResourceKey<BannerPattern> f_222739_ = BannerPatterns.m_222756_("small_stripes");
    public static final ResourceKey<BannerPattern> f_222740_ = BannerPatterns.m_222756_("cross");
    public static final ResourceKey<BannerPattern> f_222741_ = BannerPatterns.m_222756_("straight_cross");
    public static final ResourceKey<BannerPattern> f_222742_ = BannerPatterns.m_222756_("triangle_bottom");
    public static final ResourceKey<BannerPattern> f_222743_ = BannerPatterns.m_222756_("triangle_top");
    public static final ResourceKey<BannerPattern> f_222744_ = BannerPatterns.m_222756_("triangles_bottom");
    public static final ResourceKey<BannerPattern> f_222745_ = BannerPatterns.m_222756_("triangles_top");
    public static final ResourceKey<BannerPattern> f_222746_ = BannerPatterns.m_222756_("diagonal_left");
    public static final ResourceKey<BannerPattern> f_222747_ = BannerPatterns.m_222756_("diagonal_up_right");
    public static final ResourceKey<BannerPattern> f_222748_ = BannerPatterns.m_222756_("diagonal_up_left");
    public static final ResourceKey<BannerPattern> f_222749_ = BannerPatterns.m_222756_("diagonal_right");
    public static final ResourceKey<BannerPattern> f_222750_ = BannerPatterns.m_222756_("circle");
    public static final ResourceKey<BannerPattern> f_222751_ = BannerPatterns.m_222756_("rhombus");
    public static final ResourceKey<BannerPattern> f_222711_ = BannerPatterns.m_222756_("half_vertical");
    public static final ResourceKey<BannerPattern> f_222712_ = BannerPatterns.m_222756_("half_horizontal");
    public static final ResourceKey<BannerPattern> f_222713_ = BannerPatterns.m_222756_("half_vertical_right");
    public static final ResourceKey<BannerPattern> f_222714_ = BannerPatterns.m_222756_("half_horizontal_bottom");
    public static final ResourceKey<BannerPattern> f_222715_ = BannerPatterns.m_222756_("border");
    public static final ResourceKey<BannerPattern> f_222716_ = BannerPatterns.m_222756_("curly_border");
    public static final ResourceKey<BannerPattern> f_222717_ = BannerPatterns.m_222756_("gradient");
    public static final ResourceKey<BannerPattern> f_222718_ = BannerPatterns.m_222756_("gradient_up");
    public static final ResourceKey<BannerPattern> f_222719_ = BannerPatterns.m_222756_("bricks");
    public static final ResourceKey<BannerPattern> f_222720_ = BannerPatterns.m_222756_("globe");
    public static final ResourceKey<BannerPattern> f_222721_ = BannerPatterns.m_222756_("creeper");
    public static final ResourceKey<BannerPattern> f_222722_ = BannerPatterns.m_222756_("skull");
    public static final ResourceKey<BannerPattern> f_222723_ = BannerPatterns.m_222756_("flower");
    public static final ResourceKey<BannerPattern> f_222724_ = BannerPatterns.m_222756_("mojang");
    public static final ResourceKey<BannerPattern> f_222725_ = BannerPatterns.m_222756_("piglin");

    private static ResourceKey<BannerPattern> m_222756_(String p_222757_) {
        return ResourceKey.m_135785_(Registry.f_235735_, new ResourceLocation(p_222757_));
    }

    public static BannerPattern m_222754_(Registry<BannerPattern> p_222755_) {
        Registry.m_194579_(p_222755_, f_222726_, new BannerPattern("b"));
        Registry.m_194579_(p_222755_, f_222727_, new BannerPattern("bl"));
        Registry.m_194579_(p_222755_, f_222728_, new BannerPattern("br"));
        Registry.m_194579_(p_222755_, f_222729_, new BannerPattern("tl"));
        Registry.m_194579_(p_222755_, f_222730_, new BannerPattern("tr"));
        Registry.m_194579_(p_222755_, f_222731_, new BannerPattern("bs"));
        Registry.m_194579_(p_222755_, f_222732_, new BannerPattern("ts"));
        Registry.m_194579_(p_222755_, f_222733_, new BannerPattern("ls"));
        Registry.m_194579_(p_222755_, f_222734_, new BannerPattern("rs"));
        Registry.m_194579_(p_222755_, f_222735_, new BannerPattern("cs"));
        Registry.m_194579_(p_222755_, f_222736_, new BannerPattern("ms"));
        Registry.m_194579_(p_222755_, f_222737_, new BannerPattern("drs"));
        Registry.m_194579_(p_222755_, f_222738_, new BannerPattern("dls"));
        Registry.m_194579_(p_222755_, f_222739_, new BannerPattern("ss"));
        Registry.m_194579_(p_222755_, f_222740_, new BannerPattern("cr"));
        Registry.m_194579_(p_222755_, f_222741_, new BannerPattern("sc"));
        Registry.m_194579_(p_222755_, f_222742_, new BannerPattern("bt"));
        Registry.m_194579_(p_222755_, f_222743_, new BannerPattern("tt"));
        Registry.m_194579_(p_222755_, f_222744_, new BannerPattern("bts"));
        Registry.m_194579_(p_222755_, f_222745_, new BannerPattern("tts"));
        Registry.m_194579_(p_222755_, f_222746_, new BannerPattern("ld"));
        Registry.m_194579_(p_222755_, f_222747_, new BannerPattern("rd"));
        Registry.m_194579_(p_222755_, f_222748_, new BannerPattern("lud"));
        Registry.m_194579_(p_222755_, f_222749_, new BannerPattern("rud"));
        Registry.m_194579_(p_222755_, f_222750_, new BannerPattern("mc"));
        Registry.m_194579_(p_222755_, f_222751_, new BannerPattern("mr"));
        Registry.m_194579_(p_222755_, f_222711_, new BannerPattern("vh"));
        Registry.m_194579_(p_222755_, f_222712_, new BannerPattern("hh"));
        Registry.m_194579_(p_222755_, f_222713_, new BannerPattern("vhr"));
        Registry.m_194579_(p_222755_, f_222714_, new BannerPattern("hhb"));
        Registry.m_194579_(p_222755_, f_222715_, new BannerPattern("bo"));
        Registry.m_194579_(p_222755_, f_222716_, new BannerPattern("cbo"));
        Registry.m_194579_(p_222755_, f_222717_, new BannerPattern("gra"));
        Registry.m_194579_(p_222755_, f_222718_, new BannerPattern("gru"));
        Registry.m_194579_(p_222755_, f_222719_, new BannerPattern("bri"));
        Registry.m_194579_(p_222755_, f_222720_, new BannerPattern("glb"));
        Registry.m_194579_(p_222755_, f_222721_, new BannerPattern("cre"));
        Registry.m_194579_(p_222755_, f_222722_, new BannerPattern("sku"));
        Registry.m_194579_(p_222755_, f_222723_, new BannerPattern("flo"));
        Registry.m_194579_(p_222755_, f_222724_, new BannerPattern("moj"));
        return Registry.m_194579_(p_222755_, f_222725_, new BannerPattern("pig"));
    }
}

