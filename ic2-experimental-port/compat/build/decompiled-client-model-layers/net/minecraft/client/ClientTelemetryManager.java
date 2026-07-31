/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.TelemetryEvent
 *  com.mojang.authlib.minecraft.TelemetryPropertyContainer
 *  com.mojang.authlib.minecraft.TelemetrySession
 *  com.mojang.authlib.minecraft.UserApiService
 *  javax.annotation.Nullable
 */
package net.minecraft.client;

import com.mojang.authlib.minecraft.TelemetryEvent;
import com.mojang.authlib.minecraft.TelemetryPropertyContainer;
import com.mojang.authlib.minecraft.TelemetrySession;
import com.mojang.authlib.minecraft.UserApiService;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.WorldVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.util.TelemetryConstants;
import net.minecraft.world.level.GameType;

public class ClientTelemetryManager {
    private static final AtomicInteger f_193530_ = new AtomicInteger(1);
    private static final Executor f_193531_ = Executors.newSingleThreadExecutor(p_193560_ -> {
        Thread $$1 = new Thread(p_193560_);
        $$1.setName("Telemetry-Sender-#" + f_193530_.getAndIncrement());
        return $$1;
    });
    private final Minecraft f_193532_;
    private final TelemetrySession f_193533_;
    private boolean f_193534_;
    @Nullable
    private PlayerInfo f_193535_;
    @Nullable
    private String f_193536_;

    public ClientTelemetryManager(Minecraft p_193539_, UserApiService p_193540_, Optional<String> p_193541_, Optional<String> p_193542_, UUID p_193543_) {
        this.f_193532_ = p_193539_;
        if (!SharedConstants.f_136183_) {
            this.f_193533_ = p_193540_.newTelemetrySession(f_193531_);
            TelemetryPropertyContainer $$5 = this.f_193533_.globalProperties();
            ClientTelemetryManager.m_193563_("UserId", p_193541_, $$5);
            ClientTelemetryManager.m_193563_("ClientId", p_193542_, $$5);
            $$5.addProperty("deviceSessionId", p_193543_.toString());
            $$5.addProperty("WorldSessionId", UUID.randomUUID().toString());
            this.f_193533_.eventSetupFunction(p_193549_ -> p_193549_.addProperty("eventTimestampUtc", TelemetryConstants.f_184761_.format(Instant.now())));
        } else {
            this.f_193533_ = TelemetrySession.DISABLED;
        }
    }

    private static void m_193563_(String p_193564_, Optional<String> p_193565_, TelemetryPropertyContainer p_193566_) {
        p_193565_.ifPresentOrElse(p_193556_ -> p_193566_.addProperty(p_193564_, p_193556_), () -> p_193566_.addNullProperty(p_193564_));
    }

    public void m_193545_(GameType p_193546_, boolean p_193547_) {
        this.f_193535_ = new PlayerInfo(p_193546_, p_193547_);
        if (this.f_193536_ != null) {
            this.m_193557_(this.f_193535_);
        }
    }

    public void m_193561_(String p_193562_) {
        this.f_193536_ = p_193562_;
        if (this.f_193535_ != null) {
            this.m_193557_(this.f_193535_);
        }
    }

    private void m_193557_(PlayerInfo p_193558_) {
        if (this.f_193534_) {
            return;
        }
        this.f_193534_ = true;
        if (!this.f_193533_.isEnabled()) {
            return;
        }
        TelemetryEvent $$1 = this.f_193533_.createNewEvent("WorldLoaded");
        WorldVersion $$2 = SharedConstants.m_183709_();
        $$1.addProperty("build_display_name", $$2.getId());
        $$1.addProperty("clientModded", Minecraft.m_193589_().m_184597_());
        if (this.f_193536_ != null) {
            $$1.addProperty("serverModded", !this.f_193536_.equals("vanilla"));
        } else {
            $$1.addNullProperty("serverModded");
        }
        $$1.addProperty("server_type", this.m_193567_());
        $$1.addProperty("BuildPlat", Util.m_137581_().m_183999_());
        $$1.addProperty("Plat", System.getProperty("os.name"));
        $$1.addProperty("javaVersion", System.getProperty("java.version"));
        $$1.addProperty("PlayerGameMode", p_193558_.m_193575_());
        $$1.send();
    }

    private String m_193567_() {
        if (this.f_193532_.m_91294_()) {
            return "realm";
        }
        if (this.f_193532_.m_91091_()) {
            return "local";
        }
        return "server";
    }

    public void m_193544_() {
        if (this.f_193535_ != null) {
            this.m_193557_(this.f_193535_);
        }
    }

    record PlayerInfo(GameType f_193570_, boolean f_193571_) {
        public int m_193575_() {
            if (this.f_193571_ && this.f_193570_ == GameType.SURVIVAL) {
                return 99;
            }
            return switch (this.f_193570_) {
                default -> throw new IncompatibleClassChangeError();
                case GameType.SURVIVAL -> 0;
                case GameType.CREATIVE -> 1;
                case GameType.ADVENTURE -> 2;
                case GameType.SPECTATOR -> 6;
            };
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{PlayerInfo.class, "gameType;hardcore", "f_193570_", "f_193571_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PlayerInfo.class, "gameType;hardcore", "f_193570_", "f_193571_"}, this);
        }

        @Override
        public final boolean equals(Object p_193579_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PlayerInfo.class, "gameType;hardcore", "f_193570_", "f_193571_"}, this, p_193579_);
        }
    }
}

