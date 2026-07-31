/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.exceptions.MinecraftClientException
 *  com.mojang.authlib.exceptions.MinecraftClientException$ErrorType
 *  com.mojang.authlib.exceptions.MinecraftClientHttpException
 *  com.mojang.authlib.minecraft.UserApiService
 *  com.mojang.authlib.minecraft.report.AbuseReport
 *  com.mojang.authlib.minecraft.report.AbuseReportLimits
 *  com.mojang.authlib.yggdrasil.request.AbuseReportRequest
 *  com.mojang.datafixers.util.Unit
 */
package net.minecraft.client.multiplayer.chat.report;

import com.mojang.authlib.exceptions.MinecraftClientException;
import com.mojang.authlib.exceptions.MinecraftClientHttpException;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.minecraft.report.AbuseReport;
import com.mojang.authlib.minecraft.report.AbuseReportLimits;
import com.mojang.authlib.yggdrasil.request.AbuseReportRequest;
import com.mojang.datafixers.util.Unit;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.minecraft.Util;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ThrowingComponent;

public interface AbuseReportSender {
    public static AbuseReportSender m_239535_(ReportEnvironment p_239536_, UserApiService p_239537_) {
        return new Services(p_239536_, p_239537_);
    }

    public CompletableFuture<Unit> m_239469_(UUID var1, AbuseReport var2);

    public boolean m_238990_();

    default public AbuseReportLimits m_239479_() {
        return AbuseReportLimits.DEFAULTS;
    }

    public record Services(ReportEnvironment f_238713_, UserApiService f_238677_) implements AbuseReportSender
    {
        private static final Component f_238570_ = Component.m_237115_("gui.abuseReport.send.service_unavailable");
        private static final Component f_238579_ = Component.m_237115_("gui.abuseReport.send.http_error");
        private static final Component f_238657_ = Component.m_237115_("gui.abuseReport.send.json_error");

        @Override
        public CompletableFuture<Unit> m_239469_(UUID p_239470_, AbuseReport p_239471_) {
            return CompletableFuture.supplyAsync(() -> {
                AbuseReportRequest $$2 = new AbuseReportRequest(p_239470_, p_239471_, this.f_238713_.m_239120_(), this.f_238713_.m_239166_(), this.f_238713_.m_239906_());
                try {
                    this.f_238677_.reportAbuse($$2);
                    return Unit.INSTANCE;
                }
                catch (MinecraftClientHttpException $$3) {
                    Component $$4 = this.m_239704_($$3);
                    throw new CompletionException(new SendException($$4, (Throwable)$$3));
                }
                catch (MinecraftClientException $$5) {
                    Component $$6 = this.m_240067_($$5);
                    throw new CompletionException(new SendException($$6, (Throwable)$$5));
                }
            }, Util.m_183992_());
        }

        @Override
        public boolean m_238990_() {
            return this.f_238677_.canSendReports();
        }

        private Component m_239704_(MinecraftClientHttpException p_239705_) {
            return Component.m_237110_("gui.abuseReport.send.error_message", p_239705_.getMessage());
        }

        private Component m_240067_(MinecraftClientException p_240068_) {
            return switch (p_240068_.getType()) {
                default -> throw new IncompatibleClassChangeError();
                case MinecraftClientException.ErrorType.SERVICE_UNAVAILABLE -> f_238570_;
                case MinecraftClientException.ErrorType.HTTP_ERROR -> f_238579_;
                case MinecraftClientException.ErrorType.JSON_ERROR -> f_238657_;
            };
        }

        @Override
        public AbuseReportLimits m_239479_() {
            return this.f_238677_.getAbuseReportLimits();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Services.class, "environment;userApiService", "f_238713_", "f_238677_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Services.class, "environment;userApiService", "f_238713_", "f_238677_"}, this);
        }

        @Override
        public final boolean equals(Object p_238985_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Services.class, "environment;userApiService", "f_238713_", "f_238677_"}, this, p_238985_);
        }
    }

    public static class SendException
    extends ThrowingComponent {
        public SendException(Component p_239646_, Throwable p_239647_) {
            super(p_239646_, p_239647_);
        }
    }
}

