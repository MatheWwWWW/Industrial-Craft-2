/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.entity.player;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.security.PrivateKey;
import java.time.Instant;
import net.minecraft.util.Crypt;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.ProfilePublicKey;

public record ProfileKeyPair(PrivateKey f_219762_, ProfilePublicKey f_219763_, Instant f_219764_) {
    public static final Codec<ProfileKeyPair> f_219761_ = RecordCodecBuilder.create(p_219772_ -> p_219772_.group((App)Crypt.f_216064_.fieldOf("private_key").forGetter(ProfileKeyPair::f_219762_), (App)ProfilePublicKey.f_219780_.fieldOf("public_key").forGetter(ProfileKeyPair::f_219763_), (App)ExtraCodecs.f_216159_.fieldOf("refreshed_after").forGetter(ProfileKeyPair::f_219764_)).apply((Applicative)p_219772_, ProfileKeyPair::new));

    public boolean m_219770_() {
        return this.f_219764_.isBefore(Instant.now());
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ProfileKeyPair.class, "privateKey;publicKey;refreshedAfter", "f_219762_", "f_219763_", "f_219764_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ProfileKeyPair.class, "privateKey;publicKey;refreshedAfter", "f_219762_", "f_219763_", "f_219764_"}, this);
    }

    @Override
    public final boolean equals(Object p_219777_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ProfileKeyPair.class, "privateKey;publicKey;refreshedAfter", "f_219762_", "f_219763_", "f_219764_"}, this, p_219777_);
    }
}

