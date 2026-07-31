/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package net.minecraft;

import org.slf4j.Logger;

public class DefaultUncaughtExceptionHandler
implements Thread.UncaughtExceptionHandler {
    private final Logger f_131075_;

    public DefaultUncaughtExceptionHandler(Logger p_202576_) {
        this.f_131075_ = p_202576_;
    }

    @Override
    public void uncaughtException(Thread p_131079_, Throwable p_131080_) {
        this.f_131075_.error("Caught previously unhandled exception :", p_131080_);
    }
}

