/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.yggdrasil.request.AbuseReportRequest$ClientInfo
 *  com.mojang.authlib.yggdrasil.request.AbuseReportRequest$RealmInfo
 *  com.mojang.authlib.yggdrasil.request.AbuseReportRequest$ThirdPartyServerInfo
 *  javax.annotation.Nullable
 */
package net.minecraft.client.multiplayer.chat.report;

import com.mojang.authlib.yggdrasil.request.AbuseReportRequest;
import com.mojang.realmsclient.dto.RealmsServer;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;

public record ReportEnvironment(String f_238774_, @Nullable Server f_238655_) {
    public static ReportEnvironment m_239898_() {
        return ReportEnvironment.m_239955_(null);
    }

    public static ReportEnvironment m_238998_(String p_238999_) {
        return ReportEnvironment.m_239955_(new Server.ThirdParty(p_238999_));
    }

    public static ReportEnvironment m_239764_(RealmsServer p_239765_) {
        return ReportEnvironment.m_239955_(new Server.Realm(p_239765_));
    }

    public static ReportEnvironment m_239955_(@Nullable Server p_239956_) {
        return new ReportEnvironment(ReportEnvironment.m_239334_(), p_239956_);
    }

    public AbuseReportRequest.ClientInfo m_239120_() {
        return new AbuseReportRequest.ClientInfo(this.f_238774_);
    }

    @Nullable
    public AbuseReportRequest.ThirdPartyServerInfo m_239166_() {
        Server server = this.f_238655_;
        if (server instanceof Server.ThirdParty) {
            Server.ThirdParty $$0 = (Server.ThirdParty)server;
            return new AbuseReportRequest.ThirdPartyServerInfo($$0.f_238648_);
        }
        return null;
    }

    @Nullable
    public AbuseReportRequest.RealmInfo m_239906_() {
        Server server = this.f_238655_;
        if (server instanceof Server.Realm) {
            Server.Realm $$0 = (Server.Realm)server;
            return new AbuseReportRequest.RealmInfo(String.valueOf($$0.f_238769_()), $$0.f_238670_());
        }
        return null;
    }

    private static String m_239334_() {
        StringBuilder $$0 = new StringBuilder();
        $$0.append("1.19.2");
        if (Minecraft.m_193589_().m_184597_()) {
            $$0.append(" (modded)");
        }
        return $$0.toString();
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ReportEnvironment.class, "clientVersion;server", "f_238774_", "f_238655_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ReportEnvironment.class, "clientVersion;server", "f_238774_", "f_238655_"}, this);
    }

    @Override
    public final boolean equals(Object p_240047_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ReportEnvironment.class, "clientVersion;server", "f_238774_", "f_238655_"}, this, p_240047_);
    }

    public static interface Server {

        public record Realm(long f_238769_, int f_238670_) implements Server
        {
            public Realm(RealmsServer p_239068_) {
                this(p_239068_.f_87473_, p_239068_.f_87486_);
            }

            @Override
            public final String toString() {
                return ObjectMethods.bootstrap("toString", new MethodHandle[]{Realm.class, "realmId;slotId", "f_238769_", "f_238670_"}, this);
            }

            @Override
            public final int hashCode() {
                return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Realm.class, "realmId;slotId", "f_238769_", "f_238670_"}, this);
            }

            @Override
            public final boolean equals(Object p_239377_) {
                return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Realm.class, "realmId;slotId", "f_238769_", "f_238670_"}, this, p_239377_);
            }
        }

        public record ThirdParty(String f_238648_) implements Server
        {
            @Override
            public final String toString() {
                return ObjectMethods.bootstrap("toString", new MethodHandle[]{ThirdParty.class, "ip", "f_238648_"}, this);
            }

            @Override
            public final int hashCode() {
                return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ThirdParty.class, "ip", "f_238648_"}, this);
            }

            @Override
            public final boolean equals(Object p_240220_) {
                return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ThirdParty.class, "ip", "f_238648_"}, this, p_240220_);
            }
        }
    }
}

