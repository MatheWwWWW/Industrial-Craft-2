/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.yggdrasil.ServicesKeyInfo
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.util;

import com.mojang.authlib.yggdrasil.ServicesKeyInfo;
import com.mojang.logging.LogUtils;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import net.minecraft.util.SignatureUpdater;
import org.slf4j.Logger;

public interface SignatureValidator {
    public static final SignatureValidator f_216348_ = (p_216352_, p_216353_) -> true;
    public static final Logger f_216349_ = LogUtils.getLogger();

    public boolean m_216378_(SignatureUpdater var1, byte[] var2);

    default public boolean m_216375_(byte[] p_216376_, byte[] p_216377_) {
        return this.m_216378_(p_216374_ -> p_216374_.m_216346_(p_216376_), p_216377_);
    }

    private static boolean m_216354_(SignatureUpdater p_216355_, byte[] p_216356_, Signature p_216357_) throws SignatureException {
        p_216355_.m_216344_(p_216357_::update);
        return p_216357_.verify(p_216356_);
    }

    public static SignatureValidator m_216369_(PublicKey p_216370_, String p_216371_) {
        return (p_216367_, p_216368_) -> {
            try {
                Signature $$4 = Signature.getInstance(p_216371_);
                $$4.initVerify(p_216370_);
                return SignatureValidator.m_216354_(p_216367_, p_216368_, $$4);
            }
            catch (Exception $$5) {
                f_216349_.error("Failed to verify signature", (Throwable)$$5);
                return false;
            }
        };
    }

    public static SignatureValidator m_216358_(ServicesKeyInfo p_216359_) {
        return (p_216362_, p_216363_) -> {
            Signature $$3 = p_216359_.signature();
            try {
                return SignatureValidator.m_216354_(p_216362_, p_216363_, $$3);
            }
            catch (SignatureException $$4) {
                f_216349_.error("Failed to verify Services signature", (Throwable)$$4);
                return false;
            }
        };
    }
}

