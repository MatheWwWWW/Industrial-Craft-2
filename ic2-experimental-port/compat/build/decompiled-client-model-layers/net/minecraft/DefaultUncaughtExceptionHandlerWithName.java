/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package net.minecraft;

import org.slf4j.Logger;

public class DefaultUncaughtExceptionHandlerWithName
implements Thread.UncaughtExceptionHandler {
    private final Logger f_131799_;

    public DefaultUncaughtExceptionHandlerWithName(Logger p_202578_) {
        this.f_131799_ = p_202578_;
    }

    @Override
    public void uncaughtException(Thread p_131803_, Throwable p_131804_) {
        this.f_131799_.error("Caught previously unhandled exception :");
        this.f_131799_.error(p_131803_.getName(), p_131804_);
    }
}

