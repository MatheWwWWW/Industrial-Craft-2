/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.exception;

import org.slf4j.Logger;

public class RealmsDefaultUncaughtExceptionHandler
implements Thread.UncaughtExceptionHandler {
    private final Logger f_87764_;

    public RealmsDefaultUncaughtExceptionHandler(Logger p_202332_) {
        this.f_87764_ = p_202332_;
    }

    @Override
    public void uncaughtException(Thread p_87768_, Throwable p_87769_) {
        this.f_87764_.error("Caught previously unhandled exception", p_87769_);
    }
}

