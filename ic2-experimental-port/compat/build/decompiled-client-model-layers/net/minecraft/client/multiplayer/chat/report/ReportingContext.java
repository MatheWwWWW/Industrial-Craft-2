/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.UserApiService
 */
package net.minecraft.client.multiplayer.chat.report;

import com.mojang.authlib.minecraft.UserApiService;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.client.multiplayer.chat.RollingMemoryChatLog;
import net.minecraft.client.multiplayer.chat.report.AbuseReportSender;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;

public record ReportingContext(AbuseReportSender f_238706_, ReportEnvironment f_238644_, ChatLog f_238743_) {
    private static final int f_238714_ = 1024;

    public static ReportingContext m_239685_(ReportEnvironment p_239686_, UserApiService p_239687_) {
        RollingMemoryChatLog $$2 = new RollingMemoryChatLog(1024);
        AbuseReportSender $$3 = AbuseReportSender.m_239535_(p_239686_, p_239687_);
        return new ReportingContext($$3, p_239686_, $$2);
    }

    public boolean m_239733_(ReportEnvironment p_239734_) {
        return Objects.equals(this.f_238644_, p_239734_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ReportingContext.class, "sender;environment;chatLog", "f_238706_", "f_238644_", "f_238743_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ReportingContext.class, "sender;environment;chatLog", "f_238706_", "f_238644_", "f_238743_"}, this);
    }

    @Override
    public final boolean equals(Object p_239905_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ReportingContext.class, "sender;environment;chatLog", "f_238706_", "f_238644_", "f_238743_"}, this, p_239905_);
    }
}

