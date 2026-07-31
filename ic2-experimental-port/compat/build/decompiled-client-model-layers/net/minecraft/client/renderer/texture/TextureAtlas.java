/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.PngInfo;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.StitcherException;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.InventoryMenu;
import org.slf4j.Logger;

public class TextureAtlas
extends AbstractTexture
implements Tickable {
    private static final Logger f_118261_ = LogUtils.getLogger();
    @Deprecated
    public static final ResourceLocation f_118259_ = InventoryMenu.f_39692_;
    @Deprecated
    public static final ResourceLocation f_118260_ = new ResourceLocation("textures/atlas/particles.png");
    private static final String f_174697_ = ".png";
    private final List<Tickable> f_118262_ = Lists.newArrayList();
    private final Set<ResourceLocation> f_118263_ = Sets.newHashSet();
    private final Map<ResourceLocation, TextureAtlasSprite> f_118264_ = Maps.newHashMap();
    private final ResourceLocation f_118265_;
    private final int f_118266_;

    public TextureAtlas(ResourceLocation p_118269_) {
        this.f_118265_ = p_118269_;
        this.f_118266_ = RenderSystem.m_69839_();
    }

    @Override
    public void m_6704_(ResourceManager p_118282_) {
    }

    public void m_118312_(Preparations p_118313_) {
        this.f_118263_.clear();
        this.f_118263_.addAll(p_118313_.f_118331_);
        f_118261_.info("Created: {}x{}x{} {}-atlas", new Object[]{p_118313_.f_118332_, p_118313_.f_118333_, p_118313_.f_118334_, this.f_118265_});
        TextureUtil.m_85287_(this.m_117963_(), p_118313_.f_118334_, p_118313_.f_118332_, p_118313_.f_118333_);
        this.m_118329_();
        for (TextureAtlasSprite $$1 : p_118313_.f_118335_) {
            this.f_118264_.put($$1.m_118413_(), $$1);
            try {
                $$1.m_118416_();
            }
            catch (Throwable $$2) {
                CrashReport $$3 = CrashReport.m_127521_($$2, "Stitching texture atlas");
                CrashReportCategory $$4 = $$3.m_127514_("Texture being stitched together");
                $$4.m_128159_("Atlas path", this.f_118265_);
                $$4.m_128159_("Sprite", $$1);
                throw new ReportedException($$3);
            }
            Tickable $$5 = $$1.m_174746_();
            if ($$5 == null) continue;
            this.f_118262_.add($$5);
        }
    }

    public Preparations m_118307_(ResourceManager p_118308_, Stream<ResourceLocation> p_118309_, ProfilerFiller p_118310_, int p_118311_) {
        int $$14;
        p_118310_.m_6180_("preparing");
        Set<ResourceLocation> $$4 = p_118309_.peek(p_118327_ -> {
            if (p_118327_ == null) {
                throw new IllegalArgumentException("Location cannot be null!");
            }
        }).collect(Collectors.toSet());
        int $$5 = this.f_118266_;
        Stitcher $$6 = new Stitcher($$5, $$5, p_118311_);
        int $$7 = Integer.MAX_VALUE;
        int $$8 = 1 << p_118311_;
        p_118310_.m_6182_("extracting_frames");
        for (TextureAtlasSprite.Info $$9 : this.m_118304_(p_118308_, $$4)) {
            $$7 = Math.min($$7, Math.min($$9.m_118434_(), $$9.m_118437_()));
            int $$10 = Math.min(Integer.lowestOneBit($$9.m_118434_()), Integer.lowestOneBit($$9.m_118437_()));
            if ($$10 < $$8) {
                f_118261_.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[]{$$9.m_118431_(), $$9.m_118434_(), $$9.m_118437_(), Mth.m_14173_($$8), Mth.m_14173_($$10)});
                $$8 = $$10;
            }
            $$6.m_118185_($$9);
        }
        int $$11 = Math.min($$7, $$8);
        int $$12 = Mth.m_14173_($$11);
        if ($$12 < p_118311_) {
            f_118261_.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[]{this.f_118265_, p_118311_, $$12, $$11});
            int $$13 = $$12;
        } else {
            $$14 = p_118311_;
        }
        p_118310_.m_6182_("register");
        $$6.m_118185_(MissingTextureAtlasSprite.m_118079_());
        p_118310_.m_6182_("stitching");
        try {
            $$6.m_118193_();
        }
        catch (StitcherException $$15) {
            CrashReport $$16 = CrashReport.m_127521_($$15, "Stitching");
            CrashReportCategory $$17 = $$16.m_127514_("Stitcher");
            $$17.m_128159_("Sprites", $$15.m_118258_().stream().map(p_118315_ -> String.format(Locale.ROOT, "%s[%dx%d]", p_118315_.m_118431_(), p_118315_.m_118434_(), p_118315_.m_118437_())).collect(Collectors.joining(",")));
            $$17.m_128159_("Max Texture Size", $$5);
            throw new ReportedException($$16);
        }
        p_118310_.m_6182_("loading");
        List<TextureAtlasSprite> $$18 = this.m_118283_(p_118308_, $$6, $$14);
        p_118310_.m_7238_();
        return new Preparations($$4, $$6.m_118174_(), $$6.m_118187_(), $$14, $$18);
    }

    private Collection<TextureAtlasSprite.Info> m_118304_(ResourceManager p_118305_, Set<ResourceLocation> p_118306_) {
        ArrayList $$2 = Lists.newArrayList();
        ConcurrentLinkedQueue<TextureAtlasSprite.Info> $$3 = new ConcurrentLinkedQueue<TextureAtlasSprite.Info>();
        for (ResourceLocation $$4 : p_118306_) {
            if (MissingTextureAtlasSprite.m_118071_().equals($$4)) continue;
            $$2.add(CompletableFuture.runAsync(() -> {
                void $$10;
                void $$13;
                ResourceLocation $$3 = this.m_118324_($$4);
                Optional<Resource> $$4 = p_118305_.m_213713_($$3);
                if ($$4.isEmpty()) {
                    f_118261_.error("Using missing texture, file {} not found", (Object)$$3);
                    return;
                }
                Resource $$5 = $$4.get();
                try (InputStream $$6 = $$5.m_215507_();){
                    PngInfo $$7 = new PngInfo($$3::toString, $$6);
                }
                catch (IOException $$9) {
                    f_118261_.error("Using missing texture, unable to load {} : {}", (Object)$$3, (Object)$$9);
                    return;
                }
                try {
                    AnimationMetadataSection $$11 = $$5.m_215509_().m_214059_(AnimationMetadataSection.f_119011_).orElse(AnimationMetadataSection.f_119012_);
                }
                catch (Exception $$12) {
                    f_118261_.error("Unable to parse metadata from {} : {}", (Object)$$3, (Object)$$12);
                    return;
                }
                Pair<Integer, Integer> $$14 = $$13.m_7117_($$10.f_85207_, $$10.f_85208_);
                TextureAtlasSprite.Info $$15 = new TextureAtlasSprite.Info($$4, (Integer)$$14.getFirst(), (Integer)$$14.getSecond(), (AnimationMetadataSection)$$13);
                $$3.add($$15);
            }, Util.m_183991_()));
        }
        CompletableFuture.allOf($$2.toArray(new CompletableFuture[0])).join();
        return $$3;
    }

    private List<TextureAtlasSprite> m_118283_(ResourceManager p_118284_, Stitcher p_118285_, int p_118286_) {
        ConcurrentLinkedQueue $$3 = new ConcurrentLinkedQueue();
        ArrayList $$4 = Lists.newArrayList();
        p_118285_.m_118180_((p_174703_, p_174704_, p_174705_, p_174706_, p_174707_) -> {
            if (p_174703_ == MissingTextureAtlasSprite.m_118079_()) {
                MissingTextureAtlasSprite $$9 = MissingTextureAtlasSprite.m_118072_(this, p_118286_, p_174704_, p_174705_, p_174706_, p_174707_);
                $$3.add($$9);
            } else {
                $$4.add(CompletableFuture.runAsync(() -> {
                    TextureAtlasSprite $$8 = this.m_118287_(p_118284_, p_174703_, p_174704_, p_174705_, p_118286_, p_174706_, p_174707_);
                    if ($$8 != null) {
                        $$3.add($$8);
                    }
                }, Util.m_183991_()));
            }
        });
        CompletableFuture.allOf($$4.toArray(new CompletableFuture[0])).join();
        return Lists.newArrayList($$3);
    }

    @Nullable
    private TextureAtlasSprite m_118287_(ResourceManager p_118288_, TextureAtlasSprite.Info p_118289_, int p_118290_, int p_118291_, int p_118292_, int p_118293_, int p_118294_) {
        TextureAtlasSprite textureAtlasSprite;
        block9: {
            ResourceLocation $$7 = this.m_118324_(p_118289_.m_118431_());
            InputStream $$8 = p_118288_.m_215595_($$7);
            try {
                NativeImage $$9 = NativeImage.m_85058_($$8);
                textureAtlasSprite = new TextureAtlasSprite(this, p_118289_, p_118292_, p_118290_, p_118291_, p_118293_, p_118294_, $$9);
                if ($$8 == null) break block9;
            }
            catch (Throwable throwable) {
                try {
                    if ($$8 != null) {
                        try {
                            $$8.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (RuntimeException $$10) {
                    f_118261_.error("Unable to parse metadata from {}", (Object)$$7, (Object)$$10);
                    return null;
                }
                catch (IOException $$11) {
                    f_118261_.error("Using missing texture, unable to load {}", (Object)$$7, (Object)$$11);
                    return null;
                }
            }
            $$8.close();
        }
        return textureAtlasSprite;
    }

    private ResourceLocation m_118324_(ResourceLocation p_118325_) {
        return new ResourceLocation(p_118325_.m_135827_(), String.format(Locale.ROOT, "textures/%s%s", p_118325_.m_135815_(), f_174697_));
    }

    public void m_118270_() {
        this.m_117966_();
        for (Tickable $$0 : this.f_118262_) {
            $$0.m_7673_();
        }
    }

    @Override
    public void m_7673_() {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(this::m_118270_);
        } else {
            this.m_118270_();
        }
    }

    public TextureAtlasSprite m_118316_(ResourceLocation p_118317_) {
        TextureAtlasSprite $$1 = this.f_118264_.get(p_118317_);
        if ($$1 == null) {
            return this.f_118264_.get(MissingTextureAtlasSprite.m_118071_());
        }
        return $$1;
    }

    public void m_118329_() {
        for (TextureAtlasSprite $$0 : this.f_118264_.values()) {
            $$0.close();
        }
        this.f_118264_.clear();
        this.f_118262_.clear();
    }

    public ResourceLocation m_118330_() {
        return this.f_118265_;
    }

    public void m_118322_(Preparations p_118323_) {
        this.m_117960_(false, p_118323_.f_118334_ > 0);
    }

    public static class Preparations {
        final Set<ResourceLocation> f_118331_;
        final int f_118332_;
        final int f_118333_;
        final int f_118334_;
        final List<TextureAtlasSprite> f_118335_;

        public Preparations(Set<ResourceLocation> p_118337_, int p_118338_, int p_118339_, int p_118340_, List<TextureAtlasSprite> p_118341_) {
            this.f_118331_ = p_118337_;
            this.f_118332_ = p_118338_;
            this.f_118333_ = p_118339_;
            this.f_118334_ = p_118340_;
            this.f_118335_ = p_118341_;
        }
    }
}

