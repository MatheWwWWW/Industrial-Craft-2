/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class Sheets {
    public static final ResourceLocation f_110735_ = new ResourceLocation("textures/atlas/shulker_boxes.png");
    public static final ResourceLocation f_110736_ = new ResourceLocation("textures/atlas/beds.png");
    public static final ResourceLocation f_110737_ = new ResourceLocation("textures/atlas/banner_patterns.png");
    public static final ResourceLocation f_110738_ = new ResourceLocation("textures/atlas/shield_patterns.png");
    public static final ResourceLocation f_110739_ = new ResourceLocation("textures/atlas/signs.png");
    public static final ResourceLocation f_110740_ = new ResourceLocation("textures/atlas/chest.png");
    private static final RenderType f_110755_ = RenderType.m_110458_(f_110735_);
    private static final RenderType f_110756_ = RenderType.m_110446_(f_110736_);
    private static final RenderType f_110757_ = RenderType.m_110482_(f_110737_);
    private static final RenderType f_110758_ = RenderType.m_110482_(f_110738_);
    private static final RenderType f_110759_ = RenderType.m_110458_(f_110739_);
    private static final RenderType f_110760_ = RenderType.m_110452_(f_110740_);
    private static final RenderType f_110731_ = RenderType.m_110446_(TextureAtlas.f_118259_);
    private static final RenderType f_110732_ = RenderType.m_110452_(TextureAtlas.f_118259_);
    private static final RenderType f_110733_ = RenderType.m_110467_(TextureAtlas.f_118259_);
    private static final RenderType f_110734_ = RenderType.m_110470_(TextureAtlas.f_118259_);
    public static final Material f_110741_ = new Material(f_110735_, new ResourceLocation("entity/shulker/shulker"));
    public static final List<Material> f_110742_ = (List)Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black").map(p_110784_ -> new Material(f_110735_, new ResourceLocation("entity/shulker/shulker_" + p_110784_))).collect(ImmutableList.toImmutableList());
    public static final Map<WoodType, Material> f_110743_ = WoodType.m_61843_().collect(Collectors.toMap(Function.identity(), Sheets::m_173385_));
    public static final Map<ResourceKey<BannerPattern>, Material> f_173376_ = Registry.f_235736_.m_214010_().stream().collect(Collectors.toMap(Function.identity(), Sheets::m_234351_));
    public static final Map<ResourceKey<BannerPattern>, Material> f_173377_ = Registry.f_235736_.m_214010_().stream().collect(Collectors.toMap(Function.identity(), Sheets::m_234353_));
    public static final Material[] f_110744_ = (Material[])Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::m_41060_)).map(p_110766_ -> new Material(f_110736_, new ResourceLocation("entity/bed/" + p_110766_.m_41065_()))).toArray(Material[]::new);
    public static final Material f_110745_ = Sheets.m_110778_("trapped");
    public static final Material f_110746_ = Sheets.m_110778_("trapped_left");
    public static final Material f_110747_ = Sheets.m_110778_("trapped_right");
    public static final Material f_110748_ = Sheets.m_110778_("christmas");
    public static final Material f_110749_ = Sheets.m_110778_("christmas_left");
    public static final Material f_110750_ = Sheets.m_110778_("christmas_right");
    public static final Material f_110751_ = Sheets.m_110778_("normal");
    public static final Material f_110752_ = Sheets.m_110778_("normal_left");
    public static final Material f_110753_ = Sheets.m_110778_("normal_right");
    public static final Material f_110754_ = Sheets.m_110778_("ender");

    public static RenderType m_110762_() {
        return f_110757_;
    }

    public static RenderType m_110782_() {
        return f_110758_;
    }

    public static RenderType m_110785_() {
        return f_110756_;
    }

    public static RenderType m_110786_() {
        return f_110755_;
    }

    public static RenderType m_110787_() {
        return f_110759_;
    }

    public static RenderType m_110788_() {
        return f_110760_;
    }

    public static RenderType m_110789_() {
        return f_110731_;
    }

    public static RenderType m_110790_() {
        return f_110732_;
    }

    public static RenderType m_110791_() {
        return f_110733_;
    }

    public static RenderType m_110792_() {
        return f_110734_;
    }

    public static void m_110780_(Consumer<Material> p_110781_) {
        p_110781_.accept(f_110741_);
        f_110742_.forEach(p_110781_);
        f_173376_.values().forEach(p_110781_);
        f_173377_.values().forEach(p_110781_);
        f_110743_.values().forEach(p_110781_);
        for (Material $$1 : f_110744_) {
            p_110781_.accept($$1);
        }
        p_110781_.accept(f_110745_);
        p_110781_.accept(f_110746_);
        p_110781_.accept(f_110747_);
        p_110781_.accept(f_110748_);
        p_110781_.accept(f_110749_);
        p_110781_.accept(f_110750_);
        p_110781_.accept(f_110751_);
        p_110781_.accept(f_110752_);
        p_110781_.accept(f_110753_);
        p_110781_.accept(f_110754_);
    }

    private static Material m_173385_(WoodType p_173386_) {
        return new Material(f_110739_, new ResourceLocation("entity/signs/" + p_173386_.m_61846_()));
    }

    public static Material m_173381_(WoodType p_173382_) {
        return f_110743_.get(p_173382_);
    }

    private static Material m_234351_(ResourceKey<BannerPattern> p_234352_) {
        return new Material(f_110737_, BannerPattern.m_222697_(p_234352_, true));
    }

    public static Material m_234347_(ResourceKey<BannerPattern> p_234348_) {
        return f_173376_.get(p_234348_);
    }

    private static Material m_234353_(ResourceKey<BannerPattern> p_234354_) {
        return new Material(f_110738_, BannerPattern.m_222697_(p_234354_, false));
    }

    public static Material m_234349_(ResourceKey<BannerPattern> p_234350_) {
        return f_173377_.get(p_234350_);
    }

    private static Material m_110778_(String p_110779_) {
        return new Material(f_110740_, new ResourceLocation("entity/chest/" + p_110779_));
    }

    public static Material m_110767_(BlockEntity p_110768_, ChestType p_110769_, boolean p_110770_) {
        if (p_110768_ instanceof EnderChestBlockEntity) {
            return f_110754_;
        }
        if (p_110770_) {
            return Sheets.m_110771_(p_110769_, f_110748_, f_110749_, f_110750_);
        }
        if (p_110768_ instanceof TrappedChestBlockEntity) {
            return Sheets.m_110771_(p_110769_, f_110745_, f_110746_, f_110747_);
        }
        return Sheets.m_110771_(p_110769_, f_110751_, f_110752_, f_110753_);
    }

    private static Material m_110771_(ChestType p_110772_, Material p_110773_, Material p_110774_, Material p_110775_) {
        switch (p_110772_) {
            case LEFT: {
                return p_110774_;
            }
            case RIGHT: {
                return p_110775_;
            }
        }
        return p_110773_;
    }
}

