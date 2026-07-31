/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.mojang.realmsclient.client;

import java.net.Proxy;
import javax.annotation.Nullable;

public class RealmsClientConfig {
    @Nullable
    private static Proxy f_87291_;

    @Nullable
    public static Proxy m_87292_() {
        return f_87291_;
    }

    public static void m_87293_(Proxy p_87294_) {
        if (f_87291_ == null) {
            f_87291_ = p_87294_;
        }
    }
}

