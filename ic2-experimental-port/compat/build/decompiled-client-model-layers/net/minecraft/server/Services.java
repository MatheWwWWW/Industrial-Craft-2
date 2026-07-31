/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfileRepository
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 */
package net.minecraft.server;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.SignatureValidator;

public record Services(MinecraftSessionService f_214333_, SignatureValidator f_214334_, GameProfileRepository f_214335_, GameProfileCache f_214336_) {
    private static final String f_214337_ = "usercache.json";

    public static Services m_214344_(YggdrasilAuthenticationService p_214345_, File p_214346_) {
        MinecraftSessionService $$2 = p_214345_.createMinecraftSessionService();
        GameProfileRepository $$3 = p_214345_.createProfileRepository();
        GameProfileCache $$4 = new GameProfileCache($$3, new File(p_214346_, f_214337_));
        SignatureValidator $$5 = SignatureValidator.m_216358_(p_214345_.getServicesKey());
        return new Services($$2, $$5, $$3, $$4);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{Services.class, "sessionService;serviceSignatureValidator;profileRepository;profileCache", "f_214333_", "f_214334_", "f_214335_", "f_214336_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Services.class, "sessionService;serviceSignatureValidator;profileRepository;profileCache", "f_214333_", "f_214334_", "f_214335_", "f_214336_"}, this);
    }

    @Override
    public final boolean equals(Object p_214351_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Services.class, "sessionService;serviceSignatureValidator;profileRepository;profileCache", "f_214333_", "f_214334_", "f_214335_", "f_214336_"}, this, p_214351_);
    }
}

