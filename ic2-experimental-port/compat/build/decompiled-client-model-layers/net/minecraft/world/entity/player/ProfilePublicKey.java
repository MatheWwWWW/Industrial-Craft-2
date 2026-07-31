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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ThrowingComponent;
import net.minecraft.util.Crypt;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.SignatureValidator;

public record ProfilePublicKey(Data f_219781_) {
    public static final Component f_243347_ = Component.m_237115_("multiplayer.disconnect.missing_public_key");
    public static final Component f_243346_ = Component.m_237115_("multiplayer.disconnect.expired_public_key");
    private static final Component f_243345_ = Component.m_237115_("multiplayer.disconnect.invalid_public_key_signature");
    public static final Duration f_243350_ = Duration.ofHours(8L);
    public static final Codec<ProfilePublicKey> f_219780_ = Data.f_219798_.xmap(ProfilePublicKey::new, ProfilePublicKey::f_219781_);

    public static ProfilePublicKey m_243358_(SignatureValidator p_243373_, UUID p_243390_, Data p_243374_, Duration p_243387_) throws ValidationException {
        if (p_243374_.m_243357_(p_243387_)) {
            throw new ValidationException(f_243346_);
        }
        if (!p_243374_.m_240295_(p_243373_, p_243390_)) {
            throw new ValidationException(f_243345_);
        }
        return new ProfilePublicKey(p_243374_);
    }

    public SignatureValidator m_219785_() {
        return SignatureValidator.m_216369_(this.f_219781_.f_219800_, "SHA256withRSA");
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ProfilePublicKey.class, "data", "f_219781_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ProfilePublicKey.class, "data", "f_219781_"}, this);
    }

    @Override
    public final boolean equals(Object p_219795_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ProfilePublicKey.class, "data", "f_219781_"}, this, p_219795_);
    }

    public record Data(Instant f_219799_, PublicKey f_219800_, byte[] f_219801_) {
        private static final int f_219802_ = 4096;
        public static final Codec<Data> f_219798_ = RecordCodecBuilder.create(p_219814_ -> p_219814_.group((App)ExtraCodecs.f_216159_.fieldOf("expires_at").forGetter(Data::f_219799_), (App)Crypt.f_216063_.fieldOf("key").forGetter(Data::f_219800_), (App)ExtraCodecs.f_216160_.fieldOf("signature_v2").forGetter(Data::f_219801_)).apply((Applicative)p_219814_, Data::new));

        public Data(FriendlyByteBuf p_219809_) {
            this(p_219809_.m_236873_(), p_219809_.m_236874_(), p_219809_.m_130101_(4096));
        }

        public void m_219815_(FriendlyByteBuf p_219816_) {
            p_219816_.m_236826_(this.f_219799_);
            p_219816_.m_236824_(this.f_219800_);
            p_219816_.m_130087_(this.f_219801_);
        }

        boolean m_240295_(SignatureValidator p_240296_, UUID p_240297_) {
            return p_240296_.m_216375_(this.m_240266_(p_240297_), this.f_219801_);
        }

        private byte[] m_240266_(UUID p_240267_) {
            byte[] $$1 = this.f_219800_.getEncoded();
            byte[] $$2 = new byte[24 + $$1.length];
            ByteBuffer $$3 = ByteBuffer.wrap($$2).order(ByteOrder.BIG_ENDIAN);
            $$3.putLong(p_240267_.getMostSignificantBits()).putLong(p_240267_.getLeastSignificantBits()).putLong(this.f_219799_.toEpochMilli()).put($$1);
            return $$2;
        }

        public boolean m_219810_() {
            return this.f_219799_.isBefore(Instant.now());
        }

        public boolean m_243357_(Duration p_243376_) {
            return this.f_219799_.plus(p_243376_).isBefore(Instant.now());
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Data.class, "expiresAt;key;keySignature", "f_219799_", "f_219800_", "f_219801_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Data.class, "expiresAt;key;keySignature", "f_219799_", "f_219800_", "f_219801_"}, this);
        }

        @Override
        public final boolean equals(Object p_219822_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Data.class, "expiresAt;key;keySignature", "f_219799_", "f_219800_", "f_219801_"}, this, p_219822_);
        }
    }

    public static class ValidationException
    extends ThrowingComponent {
        public ValidationException(Component p_243378_) {
            super(p_243378_);
        }
    }
}

