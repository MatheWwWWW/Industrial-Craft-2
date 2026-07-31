/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.hash.Hashing
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.InsecureTextureException
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.authlib.properties.Property
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;

public class SkinManager {
    public static final String f_174841_ = "textures";
    private final TextureManager f_118807_;
    private final File f_118808_;
    private final MinecraftSessionService f_118809_;
    private final LoadingCache<String, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>> f_118810_;

    public SkinManager(TextureManager p_118812_, File p_118813_, final MinecraftSessionService p_118814_) {
        this.f_118807_ = p_118812_;
        this.f_118808_ = p_118813_;
        this.f_118809_ = p_118814_;
        this.f_118810_ = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build((CacheLoader)new CacheLoader<String, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>>(){

            public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> load(String p_118853_) {
                GameProfile $$1 = new GameProfile(null, "dummy_mcdummyface");
                $$1.getProperties().put((Object)SkinManager.f_174841_, (Object)new Property(SkinManager.f_174841_, p_118853_, ""));
                try {
                    return p_118814_.getTextures($$1, false);
                }
                catch (Throwable $$2) {
                    return ImmutableMap.of();
                }
            }

            public /* synthetic */ Object load(Object object) throws Exception {
                return this.load((String)object);
            }
        });
    }

    public ResourceLocation m_118825_(MinecraftProfileTexture p_118826_, MinecraftProfileTexture.Type p_118827_) {
        return this.m_118828_(p_118826_, p_118827_, null);
    }

    private ResourceLocation m_118828_(MinecraftProfileTexture p_118829_, MinecraftProfileTexture.Type p_118830_, @Nullable SkinTextureCallback p_118831_) {
        String $$3 = Hashing.sha1().hashUnencodedChars((CharSequence)p_118829_.getHash()).toString();
        ResourceLocation $$4 = SkinManager.m_242632_(p_118830_, $$3);
        AbstractTexture $$5 = this.f_118807_.m_174786_($$4, MissingTextureAtlasSprite.m_118080_());
        if ($$5 == MissingTextureAtlasSprite.m_118080_()) {
            File $$6 = new File(this.f_118808_, $$3.length() > 2 ? $$3.substring(0, 2) : "xx");
            File $$7 = new File($$6, $$3);
            HttpTexture $$8 = new HttpTexture($$7, p_118829_.getUrl(), DefaultPlayerSkin.m_118626_(), p_118830_ == MinecraftProfileTexture.Type.SKIN, () -> {
                if (p_118831_ != null) {
                    p_118831_.m_118856_(p_118830_, $$4, p_118829_);
                }
            });
            this.f_118807_.m_118495_($$4, $$8);
        } else if (p_118831_ != null) {
            p_118831_.m_118856_(p_118830_, $$4, p_118829_);
        }
        return $$4;
    }

    private static ResourceLocation m_242632_(MinecraftProfileTexture.Type p_242930_, String p_242947_) {
        String $$2 = switch (p_242930_) {
            default -> throw new IncompatibleClassChangeError();
            case MinecraftProfileTexture.Type.SKIN -> "skins";
            case MinecraftProfileTexture.Type.CAPE -> "capes";
            case MinecraftProfileTexture.Type.ELYTRA -> "elytra";
        };
        return new ResourceLocation($$2 + "/" + p_242947_);
    }

    public void m_118817_(GameProfile p_118818_, SkinTextureCallback p_118819_, boolean p_118820_) {
        Runnable $$3 = () -> {
            HashMap $$3 = Maps.newHashMap();
            try {
                $$3.putAll(this.f_118809_.getTextures(p_118818_, p_118820_));
            }
            catch (InsecureTextureException insecureTextureException) {
                // empty catch block
            }
            if ($$3.isEmpty()) {
                p_118818_.getProperties().clear();
                if (p_118818_.getId().equals(Minecraft.m_91087_().m_91094_().m_92548_().getId())) {
                    p_118818_.getProperties().putAll((Multimap)Minecraft.m_91087_().m_91095_());
                    $$3.putAll(this.f_118809_.getTextures(p_118818_, false));
                } else {
                    this.f_118809_.fillProfileProperties(p_118818_, p_118820_);
                    try {
                        $$3.putAll(this.f_118809_.getTextures(p_118818_, p_118820_));
                    }
                    catch (InsecureTextureException insecureTextureException) {
                        // empty catch block
                    }
                }
            }
            Minecraft.m_91087_().execute(() -> RenderSystem.m_69879_(() -> ImmutableList.of((Object)MinecraftProfileTexture.Type.SKIN, (Object)MinecraftProfileTexture.Type.CAPE).forEach(p_174848_ -> {
                if ($$3.containsKey(p_174848_)) {
                    this.m_118828_((MinecraftProfileTexture)$$3.get(p_174848_), (MinecraftProfileTexture.Type)p_174848_, p_118819_);
                }
            })));
        };
        Util.m_183991_().execute($$3);
    }

    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> m_118815_(GameProfile p_118816_) {
        Property $$1 = (Property)Iterables.getFirst((Iterable)p_118816_.getProperties().get((Object)f_174841_), null);
        if ($$1 == null) {
            return ImmutableMap.of();
        }
        return (Map)this.f_118810_.getUnchecked((Object)$$1.getValue());
    }

    public ResourceLocation m_240306_(GameProfile p_240307_) {
        MinecraftProfileTexture $$1 = this.m_118815_(p_240307_).get(MinecraftProfileTexture.Type.SKIN);
        if ($$1 != null) {
            return this.m_118825_($$1, MinecraftProfileTexture.Type.SKIN);
        }
        return DefaultPlayerSkin.m_118627_(UUIDUtil.m_235875_(p_240307_));
    }

    public static interface SkinTextureCallback {
        public void m_118856_(MinecraftProfileTexture.Type var1, ResourceLocation var2, MinecraftProfileTexture var3);
    }
}

