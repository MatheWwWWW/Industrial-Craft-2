/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.exception;

import com.mojang.realmsclient.exception.RealmsServiceException;

public class RetryCallException
extends RealmsServiceException {
    public static final int f_167328_ = 5;
    public final int f_87787_;

    public RetryCallException(int p_87789_, int p_87790_) {
        super(p_87790_, "Retry operation");
        this.f_87787_ = p_87789_ < 0 || p_87789_ > 120 ? 5 : p_87789_;
    }
}

