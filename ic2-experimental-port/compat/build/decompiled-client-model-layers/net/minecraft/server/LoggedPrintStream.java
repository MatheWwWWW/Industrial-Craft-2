/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.annotation.Nullable;
import org.slf4j.Logger;

public class LoggedPrintStream
extends PrintStream {
    private static final Logger f_135947_ = LogUtils.getLogger();
    protected final String f_135948_;

    public LoggedPrintStream(String p_135951_, OutputStream p_135952_) {
        super(p_135952_);
        this.f_135948_ = p_135951_;
    }

    @Override
    public void println(@Nullable String p_135957_) {
        this.m_6812_(p_135957_);
    }

    @Override
    public void println(Object p_135955_) {
        this.m_6812_(String.valueOf(p_135955_));
    }

    protected void m_6812_(@Nullable String p_135953_) {
        f_135947_.info("[{}]: {}", (Object)this.f_135948_, (Object)p_135953_);
    }
}

