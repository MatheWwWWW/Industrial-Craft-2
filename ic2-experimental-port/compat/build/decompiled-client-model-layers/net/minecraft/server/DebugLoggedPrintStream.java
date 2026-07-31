/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.io.OutputStream;
import net.minecraft.server.LoggedPrintStream;
import org.slf4j.Logger;

public class DebugLoggedPrintStream
extends LoggedPrintStream {
    private static final Logger f_202580_ = LogUtils.getLogger();

    public DebugLoggedPrintStream(String p_135934_, OutputStream p_135935_) {
        super(p_135934_, p_135935_);
    }

    @Override
    protected void m_6812_(String p_135937_) {
        StackTraceElement[] $$1 = Thread.currentThread().getStackTrace();
        StackTraceElement $$2 = $$1[Math.min(3, $$1.length)];
        f_202580_.info("[{}]@.({}:{}): {}", new Object[]{this.f_135948_, $$2.getFileName(), $$2.getLineNumber(), p_135937_});
    }
}

