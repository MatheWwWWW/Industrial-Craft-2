/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.util.UUIDTypeAdapter
 */
package com.mojang.realmsclient.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;

public class RealmsUtil {
    private static final YggdrasilAuthenticationService f_90215_ = new YggdrasilAuthenticationService(Minecraft.m_91087_().m_91096_());
    static final MinecraftSessionService f_90216_ = f_90215_.createMinecraftSessionService();
    public static LoadingCache<String, GameProfile> f_90214_ = CacheBuilder.newBuilder().expireAfterWrite(60L, TimeUnit.MINUTES).build((CacheLoader)new CacheLoader<String, GameProfile>(){

        public GameProfile load(String p_90229_) throws Exception {
            GameProfile $$1 = f_90216_.fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString((String)p_90229_), null), false);
            if ($$1 == null) {
                throw new Exception("Couldn't get profile");
            }
            return $$1;
        }

        public /* synthetic */ Object load(Object object) throws Exception {
            return this.load((String)object);
        }
    });
    private static final int f_167619_ = 60;
    private static final int f_167620_ = 3600;
    private static final int f_167621_ = 86400;

    public static String m_90221_(String p_90222_) throws Exception {
        GameProfile $$1 = (GameProfile)f_90214_.get((Object)p_90222_);
        return $$1.getName();
    }

    public static Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> m_90225_(String p_90226_) {
        try {
            GameProfile $$1 = (GameProfile)f_90214_.get((Object)p_90226_);
            return f_90216_.getTextures($$1, false);
        }
        catch (Exception $$2) {
            return Maps.newHashMap();
        }
    }

    public static String m_90219_(long p_90220_) {
        if (p_90220_ < 0L) {
            return "right now";
        }
        long $$1 = p_90220_ / 1000L;
        if ($$1 < 60L) {
            return (String)($$1 == 1L ? "1 second" : $$1 + " seconds") + " ago";
        }
        if ($$1 < 3600L) {
            long $$2 = $$1 / 60L;
            return (String)($$2 == 1L ? "1 minute" : $$2 + " minutes") + " ago";
        }
        if ($$1 < 86400L) {
            long $$3 = $$1 / 3600L;
            return (String)($$3 == 1L ? "1 hour" : $$3 + " hours") + " ago";
        }
        long $$4 = $$1 / 86400L;
        return (String)($$4 == 1L ? "1 day" : $$4 + " days") + " ago";
    }

    public static String m_90223_(Date p_90224_) {
        return RealmsUtil.m_90219_(System.currentTimeMillis() - p_90224_.getTime());
    }
}

