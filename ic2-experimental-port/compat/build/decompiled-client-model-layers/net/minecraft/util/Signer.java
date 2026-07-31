/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.security.PrivateKey;
import java.security.Signature;
import net.minecraft.util.SignatureUpdater;
import org.slf4j.Logger;

public interface Signer {
    public static final Logger f_216381_ = LogUtils.getLogger();

    public byte[] m_216395_(SignatureUpdater var1);

    default public byte[] m_216390_(byte[] p_216391_) {
        return this.m_216395_(p_216394_ -> p_216394_.m_216346_(p_216391_));
    }

    public static Signer m_216387_(PrivateKey p_216388_, String p_216389_) {
        return p_216386_ -> {
            try {
                Signature $$3 = Signature.getInstance(p_216389_);
                $$3.initSign(p_216388_);
                p_216386_.m_216344_($$3::update);
                return $$3.sign();
            }
            catch (Exception $$4) {
                throw new IllegalStateException("Failed to sign message", $$4);
            }
        };
    }
}

