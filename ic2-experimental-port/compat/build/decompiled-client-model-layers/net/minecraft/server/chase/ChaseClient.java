/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.chase;

import com.google.common.base.Charsets;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.net.Socket;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.ChaseCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class ChaseClient {
    private static final Logger f_195980_ = LogUtils.getLogger();
    private static final int f_195981_ = 5;
    private final String f_195982_;
    private final int f_195983_;
    private final MinecraftServer f_195984_;
    private volatile boolean f_195985_;
    @Nullable
    private Socket f_195986_;
    @Nullable
    private Thread f_195987_;

    public ChaseClient(String p_195990_, int p_195991_, MinecraftServer p_195992_) {
        this.f_195982_ = p_195990_;
        this.f_195983_ = p_195991_;
        this.f_195984_ = p_195992_;
    }

    public void m_195993_() {
        if (this.f_195987_ != null && this.f_195987_.isAlive()) {
            f_195980_.warn("Remote control client was asked to start, but it is already running. Will ignore.");
        }
        this.f_195985_ = true;
        this.f_195987_ = new Thread(this::m_196005_, "chase-client");
        this.f_195987_.setDaemon(true);
        this.f_195987_.start();
    }

    public void m_196000_() {
        this.f_195985_ = false;
        IOUtils.closeQuietly((Socket)this.f_195986_);
        this.f_195986_ = null;
        this.f_195987_ = null;
    }

    public void m_196005_() {
        String $$0 = this.f_195982_ + ":" + this.f_195983_;
        while (this.f_195985_) {
            try {
                f_195980_.info("Connecting to remote control server {}", (Object)$$0);
                this.f_195986_ = new Socket(this.f_195982_, this.f_195983_);
                f_195980_.info("Connected to remote control server! Will continuously execute the command broadcasted by that server.");
                try (BufferedReader $$1 = new BufferedReader(new InputStreamReader(this.f_195986_.getInputStream(), Charsets.US_ASCII));){
                    while (this.f_195985_) {
                        String $$2 = $$1.readLine();
                        if ($$2 == null) {
                            f_195980_.warn("Lost connection to remote control server {}. Will retry in {}s.", (Object)$$0, (Object)5);
                            break;
                        }
                        this.m_195994_($$2);
                    }
                }
                catch (IOException $$3) {
                    f_195980_.warn("Lost connection to remote control server {}. Will retry in {}s.", (Object)$$0, (Object)5);
                }
            }
            catch (IOException $$4) {
                f_195980_.warn("Failed to connect to remote control server {}. Will retry in {}s.", (Object)$$0, (Object)5);
            }
            if (!this.f_195985_) continue;
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    private void m_195994_(String p_195995_) {
        try (Scanner $$1 = new Scanner(new StringReader(p_195995_));){
            $$1.useLocale(Locale.ROOT);
            String $$2 = $$1.next();
            if ("t".equals($$2)) {
                this.m_195996_($$1);
            } else {
                f_195980_.warn("Unknown message type '{}'", (Object)$$2);
            }
        }
        catch (NoSuchElementException $$3) {
            f_195980_.warn("Could not parse message '{}', ignoring", (Object)p_195995_);
        }
    }

    private void m_195996_(Scanner p_195997_) {
        this.m_196003_(p_195997_).ifPresent(p_195999_ -> this.m_196001_(String.format(Locale.ROOT, "execute in %s run tp @s %.3f %.3f %.3f %.3f %.3f", p_195999_.f_196008_.m_135782_(), p_195999_.f_196009_.f_82479_, p_195999_.f_196009_.f_82480_, p_195999_.f_196009_.f_82481_, Float.valueOf(p_195999_.f_196010_.f_82471_), Float.valueOf(p_195999_.f_196010_.f_82470_))));
    }

    private Optional<TeleportTarget> m_196003_(Scanner p_196004_) {
        ResourceKey $$1 = (ResourceKey)ChaseCommand.f_196068_.get((Object)p_196004_.next());
        if ($$1 == null) {
            return Optional.empty();
        }
        float $$2 = p_196004_.nextFloat();
        float $$3 = p_196004_.nextFloat();
        float $$4 = p_196004_.nextFloat();
        float $$5 = p_196004_.nextFloat();
        float $$6 = p_196004_.nextFloat();
        return Optional.of(new TeleportTarget($$1, new Vec3($$2, $$3, $$4), new Vec2($$6, $$5)));
    }

    private void m_196001_(String p_196002_) {
        this.f_195984_.execute(() -> {
            List<ServerPlayer> $$1 = this.f_195984_.m_6846_().m_11314_();
            if ($$1.isEmpty()) {
                return;
            }
            ServerPlayer $$2 = $$1.get(0);
            ServerLevel $$3 = this.f_195984_.m_129783_();
            CommandSourceStack $$4 = new CommandSourceStack($$2, Vec3.m_82528_($$3.m_220360_()), Vec2.f_82462_, $$3, 4, "", CommonComponents.f_237098_, this.f_195984_, $$2);
            Commands $$5 = this.f_195984_.m_129892_();
            $$5.m_230957_($$4, p_196002_);
        });
    }

    record TeleportTarget(ResourceKey<Level> f_196008_, Vec3 f_196009_, Vec2 f_196010_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{TeleportTarget.class, "level;pos;rot", "f_196008_", "f_196009_", "f_196010_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TeleportTarget.class, "level;pos;rot", "f_196008_", "f_196009_", "f_196010_"}, this);
        }

        @Override
        public final boolean equals(Object p_196019_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TeleportTarget.class, "level;pos;rot", "f_196008_", "f_196009_", "f_196010_"}, this, p_196019_);
        }
    }
}

