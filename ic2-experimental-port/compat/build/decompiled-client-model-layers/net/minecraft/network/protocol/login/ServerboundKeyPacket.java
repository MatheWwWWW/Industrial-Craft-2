/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 */
package net.minecraft.network.protocol.login;

import com.mojang.datafixers.util.Either;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Optional;
import javax.crypto.SecretKey;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.login.ServerLoginPacketListener;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.world.entity.player.ProfilePublicKey;

public class ServerboundKeyPacket
implements Packet<ServerLoginPacketListener> {
    private final byte[] f_134852_;
    private final Either<byte[], Crypt.SaltSignaturePair> f_238055_;

    public ServerboundKeyPacket(SecretKey p_134856_, PublicKey p_134857_, byte[] p_134858_) throws CryptException {
        this.f_134852_ = Crypt.m_13594_(p_134857_, p_134856_.getEncoded());
        this.f_238055_ = Either.left((Object)Crypt.m_13594_(p_134857_, p_134858_));
    }

    public ServerboundKeyPacket(SecretKey p_238057_, PublicKey p_238058_, long p_238059_, byte[] p_238060_) throws CryptException {
        this.f_134852_ = Crypt.m_13594_(p_238058_, p_238057_.getEncoded());
        this.f_238055_ = Either.right((Object)new Crypt.SaltSignaturePair(p_238059_, p_238060_));
    }

    public ServerboundKeyPacket(FriendlyByteBuf p_179829_) {
        this.f_134852_ = p_179829_.m_130052_();
        this.f_238055_ = p_179829_.m_236862_(FriendlyByteBuf::m_130052_, Crypt.SaltSignaturePair::new);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134870_) {
        p_134870_.m_130087_(this.f_134852_);
        p_134870_.m_236810_(this.f_238055_, FriendlyByteBuf::m_130087_, Crypt.SaltSignaturePair::m_216100_);
    }

    @Override
    public void m_5797_(ServerLoginPacketListener p_134866_) {
        p_134866_.m_8072_(this);
    }

    public SecretKey m_134859_(PrivateKey p_134860_) throws CryptException {
        return Crypt.m_13597_(p_134860_, this.f_134852_);
    }

    public boolean m_238071_(byte[] p_238072_, ProfilePublicKey p_238073_) {
        return (Boolean)this.f_238055_.map(p_238066_ -> false, p_238064_ -> p_238073_.m_219785_().m_216378_(p_238070_ -> {
            p_238070_.m_216346_(p_238072_);
            p_238070_.m_216346_(p_238064_.m_216103_());
        }, p_238064_.f_216092_()));
    }

    public boolean m_238074_(byte[] p_238075_, PrivateKey p_238076_) {
        Optional $$2 = this.f_238055_.left();
        try {
            return $$2.isPresent() && Arrays.equals(p_238075_, Crypt.m_13605_(p_238076_, (byte[])$$2.get()));
        }
        catch (CryptException $$3) {
            return false;
        }
    }
}

