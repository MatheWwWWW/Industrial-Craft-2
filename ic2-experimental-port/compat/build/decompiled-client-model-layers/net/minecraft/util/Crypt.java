/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.primitives.Longs
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  it.unimi.dsi.fastutil.bytes.ByteArrays
 */
package net.minecraft.util;

import com.google.common.primitives.Longs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.CryptException;

public class Crypt {
    private static final String f_144611_ = "AES";
    private static final int f_144612_ = 128;
    private static final String f_144613_ = "RSA";
    private static final int f_144614_ = 1024;
    private static final String f_144615_ = "ISO_8859_1";
    private static final String f_144616_ = "SHA-1";
    public static final String f_216059_ = "SHA256withRSA";
    private static final String f_216065_ = "-----BEGIN RSA PRIVATE KEY-----";
    private static final String f_216066_ = "-----END RSA PRIVATE KEY-----";
    public static final String f_216060_ = "-----BEGIN RSA PUBLIC KEY-----";
    private static final String f_216067_ = "-----END RSA PUBLIC KEY-----";
    public static final String f_216061_ = "\n";
    public static final Base64.Encoder f_216062_ = Base64.getMimeEncoder(76, "\n".getBytes(StandardCharsets.UTF_8));
    public static final Codec<PublicKey> f_216063_ = Codec.STRING.comapFlatMap(p_216087_ -> {
        try {
            return DataResult.success((Object)Crypt.m_216080_(p_216087_));
        }
        catch (CryptException $$1) {
            return DataResult.error((String)$$1.getMessage());
        }
    }, Crypt::m_216078_);
    public static final Codec<PrivateKey> f_216064_ = Codec.STRING.comapFlatMap(p_216085_ -> {
        try {
            return DataResult.success((Object)Crypt.m_216069_(p_216085_));
        }
        catch (CryptException $$1) {
            return DataResult.error((String)$$1.getMessage());
        }
    }, Crypt::m_216076_);

    public static SecretKey m_13578_() throws CryptException {
        try {
            KeyGenerator $$0 = KeyGenerator.getInstance(f_144611_);
            $$0.init(128);
            return $$0.generateKey();
        }
        catch (Exception $$1) {
            throw new CryptException($$1);
        }
    }

    public static KeyPair m_13604_() throws CryptException {
        try {
            KeyPairGenerator $$0 = KeyPairGenerator.getInstance(f_144613_);
            $$0.initialize(1024);
            return $$0.generateKeyPair();
        }
        catch (Exception $$1) {
            throw new CryptException($$1);
        }
    }

    public static byte[] m_13590_(String p_13591_, PublicKey p_13592_, SecretKey p_13593_) throws CryptException {
        try {
            return Crypt.m_13602_(p_13591_.getBytes(f_144615_), p_13593_.getEncoded(), p_13592_.getEncoded());
        }
        catch (Exception $$3) {
            throw new CryptException($$3);
        }
    }

    private static byte[] m_13602_(byte[] ... p_13603_) throws Exception {
        MessageDigest $$1 = MessageDigest.getInstance(f_144616_);
        for (byte[] $$2 : p_13603_) {
            $$1.update($$2);
        }
        return $$1.digest();
    }

    private static <T extends Key> T m_216071_(String p_216072_, String p_216073_, String p_216074_, ByteArrayToKeyFunction<T> p_216075_) throws CryptException {
        int $$4 = p_216072_.indexOf(p_216073_);
        if ($$4 != -1) {
            int $$5 = p_216072_.indexOf(p_216074_, $$4 += p_216073_.length());
            p_216072_ = p_216072_.substring($$4, $$5 + 1);
        }
        try {
            return p_216075_.m_216088_(Base64.getMimeDecoder().decode(p_216072_));
        }
        catch (IllegalArgumentException $$6) {
            throw new CryptException($$6);
        }
    }

    public static PrivateKey m_216069_(String p_216070_) throws CryptException {
        return Crypt.m_216071_(p_216070_, f_216065_, f_216066_, Crypt::m_216082_);
    }

    public static PublicKey m_216080_(String p_216081_) throws CryptException {
        return Crypt.m_216071_(p_216081_, f_216060_, f_216067_, Crypt::m_13600_);
    }

    public static String m_216078_(PublicKey p_216079_) {
        if (!f_144613_.equals(p_216079_.getAlgorithm())) {
            throw new IllegalArgumentException("Public key must be RSA");
        }
        return "-----BEGIN RSA PUBLIC KEY-----\n" + f_216062_.encodeToString(p_216079_.getEncoded()) + "\n-----END RSA PUBLIC KEY-----\n";
    }

    public static String m_216076_(PrivateKey p_216077_) {
        if (!f_144613_.equals(p_216077_.getAlgorithm())) {
            throw new IllegalArgumentException("Private key must be RSA");
        }
        return "-----BEGIN RSA PRIVATE KEY-----\n" + f_216062_.encodeToString(p_216077_.getEncoded()) + "\n-----END RSA PRIVATE KEY-----\n";
    }

    private static PrivateKey m_216082_(byte[] p_216083_) throws CryptException {
        try {
            PKCS8EncodedKeySpec $$1 = new PKCS8EncodedKeySpec(p_216083_);
            KeyFactory $$2 = KeyFactory.getInstance(f_144613_);
            return $$2.generatePrivate($$1);
        }
        catch (Exception $$3) {
            throw new CryptException($$3);
        }
    }

    public static PublicKey m_13600_(byte[] p_13601_) throws CryptException {
        try {
            X509EncodedKeySpec $$1 = new X509EncodedKeySpec(p_13601_);
            KeyFactory $$2 = KeyFactory.getInstance(f_144613_);
            return $$2.generatePublic($$1);
        }
        catch (Exception $$3) {
            throw new CryptException($$3);
        }
    }

    public static SecretKey m_13597_(PrivateKey p_13598_, byte[] p_13599_) throws CryptException {
        byte[] $$2 = Crypt.m_13605_(p_13598_, p_13599_);
        try {
            return new SecretKeySpec($$2, f_144611_);
        }
        catch (Exception $$3) {
            throw new CryptException($$3);
        }
    }

    public static byte[] m_13594_(Key p_13595_, byte[] p_13596_) throws CryptException {
        return Crypt.m_13586_(1, p_13595_, p_13596_);
    }

    public static byte[] m_13605_(Key p_13606_, byte[] p_13607_) throws CryptException {
        return Crypt.m_13586_(2, p_13606_, p_13607_);
    }

    private static byte[] m_13586_(int p_13587_, Key p_13588_, byte[] p_13589_) throws CryptException {
        try {
            return Crypt.m_13579_(p_13587_, p_13588_.getAlgorithm(), p_13588_).doFinal(p_13589_);
        }
        catch (Exception $$3) {
            throw new CryptException($$3);
        }
    }

    private static Cipher m_13579_(int p_13580_, String p_13581_, Key p_13582_) throws Exception {
        Cipher $$3 = Cipher.getInstance(p_13581_);
        $$3.init(p_13580_, p_13582_);
        return $$3;
    }

    public static Cipher m_13583_(int p_13584_, Key p_13585_) throws CryptException {
        try {
            Cipher $$2 = Cipher.getInstance("AES/CFB8/NoPadding");
            $$2.init(p_13584_, p_13585_, new IvParameterSpec(p_13585_.getEncoded()));
            return $$2;
        }
        catch (Exception $$3) {
            throw new CryptException($$3);
        }
    }

    static interface ByteArrayToKeyFunction<T extends Key> {
        public T m_216088_(byte[] var1) throws CryptException;
    }

    public record SaltSignaturePair(long f_216091_, byte[] f_216092_) {
        public static final SaltSignaturePair f_216090_ = new SaltSignaturePair(0L, ByteArrays.EMPTY_ARRAY);

        public SaltSignaturePair(FriendlyByteBuf p_216098_) {
            this(p_216098_.readLong(), p_216098_.m_130052_());
        }

        public boolean m_216099_() {
            return this.f_216092_.length > 0;
        }

        public static void m_216100_(FriendlyByteBuf p_216101_, SaltSignaturePair p_216102_) {
            p_216101_.writeLong(p_216102_.f_216091_);
            p_216101_.m_130087_(p_216102_.f_216092_);
        }

        public byte[] m_216103_() {
            return Longs.toByteArray((long)this.f_216091_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{SaltSignaturePair.class, "salt;signature", "f_216091_", "f_216092_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SaltSignaturePair.class, "salt;signature", "f_216091_", "f_216092_"}, this);
        }

        @Override
        public final boolean equals(Object p_216107_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SaltSignaturePair.class, "salt;signature", "f_216091_", "f_216092_"}, this, p_216107_);
        }
    }

    public static class SaltSupplier {
        private static final SecureRandom f_216110_ = new SecureRandom();

        public static long m_216113_() {
            return f_216110_.nextLong();
        }
    }
}

