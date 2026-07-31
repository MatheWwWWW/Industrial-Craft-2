/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import java.security.SignatureException;

@FunctionalInterface
public interface SignatureUpdater {
    public void m_216344_(Output var1) throws SignatureException;

    @FunctionalInterface
    public static interface Output {
        public void m_216346_(byte[] var1) throws SignatureException;
    }
}

