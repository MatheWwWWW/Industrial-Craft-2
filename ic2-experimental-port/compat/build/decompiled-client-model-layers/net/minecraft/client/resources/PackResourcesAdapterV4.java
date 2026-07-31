/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.datafixers.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.world.level.block.state.properties.ChestType;

public class PackResourcesAdapterV4
implements PackResources {
    private static final Map<String, Pair<ChestType, ResourceLocation>> f_118737_ = Util.m_137469_(Maps.newHashMap(), p_118784_ -> {
        p_118784_.put("textures/entity/chest/normal_left.png", new Pair((Object)ChestType.LEFT, (Object)new ResourceLocation("textures/entity/chest/normal_double.png")));
        p_118784_.put("textures/entity/chest/normal_right.png", new Pair((Object)ChestType.RIGHT, (Object)new ResourceLocation("textures/entity/chest/normal_double.png")));
        p_118784_.put("textures/entity/chest/normal.png", new Pair((Object)ChestType.SINGLE, (Object)new ResourceLocation("textures/entity/chest/normal.png")));
        p_118784_.put("textures/entity/chest/trapped_left.png", new Pair((Object)ChestType.LEFT, (Object)new ResourceLocation("textures/entity/chest/trapped_double.png")));
        p_118784_.put("textures/entity/chest/trapped_right.png", new Pair((Object)ChestType.RIGHT, (Object)new ResourceLocation("textures/entity/chest/trapped_double.png")));
        p_118784_.put("textures/entity/chest/trapped.png", new Pair((Object)ChestType.SINGLE, (Object)new ResourceLocation("textures/entity/chest/trapped.png")));
        p_118784_.put("textures/entity/chest/christmas_left.png", new Pair((Object)ChestType.LEFT, (Object)new ResourceLocation("textures/entity/chest/christmas_double.png")));
        p_118784_.put("textures/entity/chest/christmas_right.png", new Pair((Object)ChestType.RIGHT, (Object)new ResourceLocation("textures/entity/chest/christmas_double.png")));
        p_118784_.put("textures/entity/chest/christmas.png", new Pair((Object)ChestType.SINGLE, (Object)new ResourceLocation("textures/entity/chest/christmas.png")));
        p_118784_.put("textures/entity/chest/ender.png", new Pair((Object)ChestType.SINGLE, (Object)new ResourceLocation("textures/entity/chest/ender.png")));
    });
    private static final List<String> f_118738_ = Lists.newArrayList((Object[])new String[]{"base", "border", "bricks", "circle", "creeper", "cross", "curly_border", "diagonal_left", "diagonal_right", "diagonal_up_left", "diagonal_up_right", "flower", "globe", "gradient", "gradient_up", "half_horizontal", "half_horizontal_bottom", "half_vertical", "half_vertical_right", "mojang", "rhombus", "skull", "small_stripes", "square_bottom_left", "square_bottom_right", "square_top_left", "square_top_right", "straight_cross", "stripe_bottom", "stripe_center", "stripe_downleft", "stripe_downright", "stripe_left", "stripe_middle", "stripe_right", "stripe_top", "triangle_bottom", "triangle_top", "triangles_bottom", "triangles_top"});
    private static final Set<String> f_118739_ = f_118738_.stream().map(p_118795_ -> "textures/entity/shield/" + p_118795_ + ".png").collect(Collectors.toSet());
    private static final Set<String> f_118740_ = f_118738_.stream().map(p_118782_ -> "textures/entity/banner/" + p_118782_ + ".png").collect(Collectors.toSet());
    public static final ResourceLocation f_118734_ = new ResourceLocation("textures/entity/shield_base.png");
    public static final ResourceLocation f_118735_ = new ResourceLocation("textures/entity/banner_base.png");
    public static final int f_174837_ = 64;
    public static final int f_174838_ = 64;
    public static final int f_174839_ = 64;
    public static final ResourceLocation f_118736_ = new ResourceLocation("textures/entity/iron_golem.png");
    public static final String f_174840_ = "textures/entity/iron_golem/iron_golem.png";
    private final PackResources f_118741_;

    public PackResourcesAdapterV4(PackResources p_118744_) {
        this.f_118741_ = p_118744_;
    }

    @Override
    public InputStream m_5542_(String p_118791_) throws IOException {
        return this.f_118741_.m_5542_(p_118791_);
    }

    @Override
    public boolean m_7211_(PackType p_118786_, ResourceLocation p_118787_) {
        if (!"minecraft".equals(p_118787_.m_135827_())) {
            return this.f_118741_.m_7211_(p_118786_, p_118787_);
        }
        String $$2 = p_118787_.m_135815_();
        if ("textures/misc/enchanted_item_glint.png".equals($$2)) {
            return false;
        }
        if (f_174840_.equals($$2)) {
            return this.f_118741_.m_7211_(p_118786_, f_118736_);
        }
        if ("textures/entity/conduit/wind.png".equals($$2) || "textures/entity/conduit/wind_vertical.png".equals($$2)) {
            return false;
        }
        if (f_118739_.contains($$2)) {
            return this.f_118741_.m_7211_(p_118786_, f_118734_) && this.f_118741_.m_7211_(p_118786_, p_118787_);
        }
        if (f_118740_.contains($$2)) {
            return this.f_118741_.m_7211_(p_118786_, f_118735_) && this.f_118741_.m_7211_(p_118786_, p_118787_);
        }
        Pair<ChestType, ResourceLocation> $$3 = f_118737_.get($$2);
        if ($$3 != null && this.f_118741_.m_7211_(p_118786_, (ResourceLocation)$$3.getSecond())) {
            return true;
        }
        return this.f_118741_.m_7211_(p_118786_, p_118787_);
    }

    @Override
    public InputStream m_8031_(PackType p_118755_, ResourceLocation p_118756_) throws IOException {
        if (!"minecraft".equals(p_118756_.m_135827_())) {
            return this.f_118741_.m_8031_(p_118755_, p_118756_);
        }
        String $$2 = p_118756_.m_135815_();
        if (f_174840_.equals($$2)) {
            return this.f_118741_.m_8031_(p_118755_, f_118736_);
        }
        if (f_118739_.contains($$2)) {
            InputStream $$3 = PackResourcesAdapterV4.m_118773_(this.f_118741_.m_8031_(p_118755_, f_118734_), this.f_118741_.m_8031_(p_118755_, p_118756_), 64, 2, 2, 12, 22);
            if ($$3 != null) {
                return $$3;
            }
        } else if (f_118740_.contains($$2)) {
            InputStream $$4 = PackResourcesAdapterV4.m_118773_(this.f_118741_.m_8031_(p_118755_, f_118735_), this.f_118741_.m_8031_(p_118755_, p_118756_), 64, 0, 0, 42, 41);
            if ($$4 != null) {
                return $$4;
            }
        } else {
            if ("textures/entity/enderdragon/dragon.png".equals($$2) || "textures/entity/enderdragon/dragon_exploding.png".equals($$2)) {
                try (NativeImage $$5 = NativeImage.m_85058_(this.f_118741_.m_8031_(p_118755_, p_118756_));){
                    int $$6 = $$5.m_84982_() / 256;
                    for (int $$7 = 88 * $$6; $$7 < 200 * $$6; ++$$7) {
                        for (int $$8 = 56 * $$6; $$8 < 112 * $$6; ++$$8) {
                            $$5.m_84988_($$8, $$7, 0);
                        }
                    }
                    ByteArrayInputStream $$7 = new ByteArrayInputStream($$5.m_85121_());
                    return $$7;
                }
            }
            if ("textures/entity/conduit/closed_eye.png".equals($$2) || "textures/entity/conduit/open_eye.png".equals($$2)) {
                return PackResourcesAdapterV4.m_118771_(this.f_118741_.m_8031_(p_118755_, p_118756_));
            }
            Pair<ChestType, ResourceLocation> $$9 = f_118737_.get($$2);
            if ($$9 != null) {
                ChestType $$10 = (ChestType)$$9.getFirst();
                InputStream $$11 = this.f_118741_.m_8031_(p_118755_, (ResourceLocation)$$9.getSecond());
                if ($$10 == ChestType.SINGLE) {
                    return PackResourcesAdapterV4.m_118797_($$11);
                }
                if ($$10 == ChestType.LEFT) {
                    return PackResourcesAdapterV4.m_118788_($$11);
                }
                if ($$10 == ChestType.RIGHT) {
                    return PackResourcesAdapterV4.m_118792_($$11);
                }
            }
        }
        return this.f_118741_.m_8031_(p_118755_, p_118756_);
    }

    /*
     * Loose catch block
     */
    @Nullable
    public static InputStream m_118773_(InputStream p_118774_, InputStream p_118775_, int p_118776_, int p_118777_, int p_118778_, int p_118779_, int p_118780_) throws IOException {
        try (NativeImage $$7 = NativeImage.m_85058_(p_118774_);
             NativeImage $$8 = NativeImage.m_85058_(p_118775_);){
            block20: {
                int $$9 = $$7.m_84982_();
                int $$10 = $$7.m_85084_();
                if ($$9 != $$8.m_84982_()) break block20;
                if ($$10 != $$8.m_85084_()) break;
                NativeImage $$11 = new NativeImage($$9, $$10, true);
                try {
                    int $$12 = $$9 / p_118776_;
                    for (int $$13 = p_118778_ * $$12; $$13 < p_118780_ * $$12; ++$$13) {
                        for (int $$14 = p_118777_ * $$12; $$14 < p_118779_ * $$12; ++$$14) {
                            int $$15 = NativeImage.m_85085_($$8.m_84985_($$14, $$13));
                            int $$16 = $$7.m_84985_($$14, $$13);
                            $$11.m_84988_($$14, $$13, NativeImage.m_84992_($$15, NativeImage.m_85119_($$16), NativeImage.m_85103_($$16), NativeImage.m_85085_($$16)));
                        }
                    }
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream($$11.m_85121_());
                    $$11.close();
                    return byteArrayInputStream;
                }
                catch (Throwable throwable) {
                    try {
                        $$11.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
    }

    public static InputStream m_118771_(InputStream p_118772_) throws IOException {
        try (NativeImage $$1 = NativeImage.m_85058_(p_118772_);){
            int $$2 = $$1.m_84982_();
            int $$3 = $$1.m_85084_();
            NativeImage $$4 = new NativeImage(2 * $$2, 2 * $$3, true);
            try {
                PackResourcesAdapterV4.m_118759_($$1, $$4, 0, 0, 0, 0, $$2, $$3, 1, false, false);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream($$4.m_85121_());
                $$4.close();
                return byteArrayInputStream;
            }
            catch (Throwable throwable) {
                try {
                    $$4.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
    }

    public static InputStream m_118788_(InputStream p_118789_) throws IOException {
        try (NativeImage $$1 = NativeImage.m_85058_(p_118789_);){
            int $$2 = $$1.m_84982_();
            int $$3 = $$1.m_85084_();
            NativeImage $$4 = new NativeImage($$2 / 2, $$3, true);
            try {
                int $$5 = $$3 / 64;
                PackResourcesAdapterV4.m_118759_($$1, $$4, 29, 0, 29, 0, 15, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 59, 0, 14, 0, 15, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 29, 14, 43, 14, 15, 5, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 44, 14, 29, 14, 14, 5, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 58, 14, 14, 14, 15, 5, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 29, 19, 29, 19, 15, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 59, 19, 14, 19, 15, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 29, 33, 43, 33, 15, 10, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 44, 33, 29, 33, 14, 10, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 58, 33, 14, 33, 15, 10, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 2, 0, 2, 0, 1, 1, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 4, 0, 1, 0, 1, 1, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 2, 1, 3, 1, 1, 4, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 3, 1, 2, 1, 1, 4, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 4, 1, 1, 1, 1, 4, $$5, true, true);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream($$4.m_85121_());
                $$4.close();
                return byteArrayInputStream;
            }
            catch (Throwable throwable) {
                try {
                    $$4.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
    }

    public static InputStream m_118792_(InputStream p_118793_) throws IOException {
        try (NativeImage $$1 = NativeImage.m_85058_(p_118793_);){
            int $$2 = $$1.m_84982_();
            int $$3 = $$1.m_85084_();
            NativeImage $$4 = new NativeImage($$2 / 2, $$3, true);
            try {
                int $$5 = $$3 / 64;
                PackResourcesAdapterV4.m_118759_($$1, $$4, 14, 0, 29, 0, 15, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 44, 0, 14, 0, 15, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 0, 14, 0, 14, 14, 5, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 14, 14, 43, 14, 15, 5, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 73, 14, 14, 14, 15, 5, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 14, 19, 29, 19, 15, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 44, 19, 14, 19, 15, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 0, 33, 0, 33, 14, 10, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 14, 33, 43, 33, 15, 10, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 73, 33, 14, 33, 15, 10, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 1, 0, 2, 0, 1, 1, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 3, 0, 1, 0, 1, 1, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 0, 1, 0, 1, 1, 4, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 1, 1, 3, 1, 1, 4, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 5, 1, 1, 1, 1, 4, $$5, true, true);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream($$4.m_85121_());
                $$4.close();
                return byteArrayInputStream;
            }
            catch (Throwable throwable) {
                try {
                    $$4.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
    }

    public static InputStream m_118797_(InputStream p_118798_) throws IOException {
        try (NativeImage $$1 = NativeImage.m_85058_(p_118798_);){
            int $$2 = $$1.m_84982_();
            int $$3 = $$1.m_85084_();
            NativeImage $$4 = new NativeImage($$2, $$3, true);
            try {
                int $$5 = $$3 / 64;
                PackResourcesAdapterV4.m_118759_($$1, $$4, 14, 0, 28, 0, 14, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 28, 0, 14, 0, 14, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 0, 14, 0, 14, 14, 5, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 14, 14, 42, 14, 14, 5, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 28, 14, 28, 14, 14, 5, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 42, 14, 14, 14, 14, 5, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 14, 19, 28, 19, 14, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 28, 19, 14, 19, 14, 14, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 0, 33, 0, 33, 14, 10, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 14, 33, 42, 33, 14, 10, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 28, 33, 28, 33, 14, 10, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 42, 33, 14, 33, 14, 10, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 1, 0, 3, 0, 2, 1, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 3, 0, 1, 0, 2, 1, $$5, false, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 0, 1, 0, 1, 1, 4, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 1, 1, 4, 1, 2, 4, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 3, 1, 3, 1, 1, 4, $$5, true, true);
                PackResourcesAdapterV4.m_118759_($$1, $$4, 4, 1, 1, 1, 2, 4, $$5, true, true);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream($$4.m_85121_());
                $$4.close();
                return byteArrayInputStream;
            }
            catch (Throwable throwable) {
                try {
                    $$4.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
    }

    @Override
    public Collection<ResourceLocation> m_214146_(PackType p_235029_, String p_235030_, String p_235031_, Predicate<ResourceLocation> p_235032_) {
        return this.f_118741_.m_214146_(p_235029_, p_235030_, p_235031_, p_235032_);
    }

    @Override
    public Set<String> m_5698_(PackType p_118747_) {
        return this.f_118741_.m_5698_(p_118747_);
    }

    @Override
    @Nullable
    public <T> T m_5550_(MetadataSectionSerializer<T> p_118758_) throws IOException {
        return this.f_118741_.m_5550_(p_118758_);
    }

    @Override
    public String m_8017_() {
        return this.f_118741_.m_8017_();
    }

    @Override
    public void close() {
        this.f_118741_.close();
    }

    private static void m_118759_(NativeImage p_118760_, NativeImage p_118761_, int p_118762_, int p_118763_, int p_118764_, int p_118765_, int p_118766_, int p_118767_, int p_118768_, boolean p_118769_, boolean p_118770_) {
        p_118767_ *= p_118768_;
        p_118766_ *= p_118768_;
        p_118764_ *= p_118768_;
        p_118765_ *= p_118768_;
        p_118762_ *= p_118768_;
        p_118763_ *= p_118768_;
        for (int $$11 = 0; $$11 < p_118767_; ++$$11) {
            for (int $$12 = 0; $$12 < p_118766_; ++$$12) {
                p_118761_.m_84988_(p_118764_ + $$12, p_118765_ + $$11, p_118760_.m_84985_(p_118762_ + (p_118769_ ? p_118766_ - 1 - $$12 : $$12), p_118763_ + (p_118770_ ? p_118767_ - 1 - $$11 : $$11)));
            }
        }
    }
}

