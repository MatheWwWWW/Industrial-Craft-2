/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.chase;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ClosedByInterruptException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.server.commands.ChaseCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class ChaseServer {
    private static final Logger f_196022_ = LogUtils.getLogger();
    private final String f_196023_;
    private final int f_196024_;
    private final PlayerList f_196025_;
    private final int f_196026_;
    private volatile boolean f_196027_;
    @Nullable
    private ServerSocket f_196028_;
    private final CopyOnWriteArrayList<Socket> f_196029_ = new CopyOnWriteArrayList();

    public ChaseServer(String p_196032_, int p_196033_, PlayerList p_196034_, int p_196035_) {
        this.f_196023_ = p_196032_;
        this.f_196024_ = p_196033_;
        this.f_196025_ = p_196034_;
        this.f_196026_ = p_196035_;
    }

    public void m_196036_() throws IOException {
        if (this.f_196028_ != null && !this.f_196028_.isClosed()) {
            f_196022_.warn("Remote control server was asked to start, but it is already running. Will ignore.");
            return;
        }
        this.f_196027_ = true;
        this.f_196028_ = new ServerSocket(this.f_196024_, 50, InetAddress.getByName(this.f_196023_));
        Thread $$0 = new Thread(this::m_196042_, "chase-server-acceptor");
        $$0.setDaemon(true);
        $$0.start();
        Thread $$1 = new Thread(this::m_196041_, "chase-server-sender");
        $$1.setDaemon(true);
        $$1.start();
    }

    private void m_196041_() {
        PlayerPosition $$0 = null;
        while (this.f_196027_) {
            if (!this.f_196029_.isEmpty()) {
                PlayerPosition $$1 = this.m_196043_();
                if ($$1 != null && !$$1.equals($$0)) {
                    $$0 = $$1;
                    byte[] $$2 = $$1.m_196065_().getBytes(StandardCharsets.US_ASCII);
                    for (Socket $$3 : this.f_196029_) {
                        if ($$3.isClosed()) continue;
                        Util.m_183992_().submit(() -> {
                            try {
                                OutputStream $$2 = $$3.getOutputStream();
                                $$2.write($$2);
                                $$2.flush();
                            }
                            catch (IOException $$3) {
                                f_196022_.info("Remote control client socket got an IO exception and will be closed", (Throwable)$$3);
                                IOUtils.closeQuietly((Socket)$$3);
                            }
                        });
                    }
                }
                List $$4 = this.f_196029_.stream().filter(Socket::isClosed).collect(Collectors.toList());
                this.f_196029_.removeAll($$4);
            }
            if (!this.f_196027_) continue;
            try {
                Thread.sleep(this.f_196026_);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    public void m_196040_() {
        this.f_196027_ = false;
        IOUtils.closeQuietly((ServerSocket)this.f_196028_);
        this.f_196028_ = null;
    }

    private void m_196042_() {
        try {
            while (this.f_196027_) {
                if (this.f_196028_ == null) continue;
                f_196022_.info("Remote control server is listening for connections on port {}", (Object)this.f_196024_);
                Socket $$0 = this.f_196028_.accept();
                f_196022_.info("Remote control server received client connection on port {}", (Object)$$0.getPort());
                this.f_196029_.add($$0);
            }
        }
        catch (ClosedByInterruptException $$1) {
            if (this.f_196027_) {
                f_196022_.info("Remote control server closed by interrupt");
            }
        }
        catch (IOException $$2) {
            if (this.f_196027_) {
                f_196022_.error("Remote control server closed because of an IO exception", (Throwable)$$2);
            }
        }
        finally {
            IOUtils.closeQuietly((ServerSocket)this.f_196028_);
        }
        f_196022_.info("Remote control server is now stopped");
        this.f_196027_ = false;
    }

    @Nullable
    private PlayerPosition m_196043_() {
        List<ServerPlayer> $$0 = this.f_196025_.m_11314_();
        if ($$0.isEmpty()) {
            return null;
        }
        ServerPlayer $$1 = $$0.get(0);
        String $$2 = (String)ChaseCommand.f_196068_.inverse().get($$1.m_9236_().m_46472_());
        if ($$2 == null) {
            return null;
        }
        return new PlayerPosition($$2, $$1.m_20185_(), $$1.m_20186_(), $$1.m_20189_(), $$1.m_146908_(), $$1.m_146909_());
    }

    record PlayerPosition(String f_196044_, double f_196045_, double f_196046_, double f_196047_, float f_196048_, float f_196049_) {
        String m_196065_() {
            return String.format(Locale.ROOT, "t %s %.2f %.2f %.2f %.2f %.2f\n", this.f_196044_, this.f_196045_, this.f_196046_, this.f_196047_, Float.valueOf(this.f_196048_), Float.valueOf(this.f_196049_));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{PlayerPosition.class, "dimensionName;x;y;z;yRot;xRot", "f_196044_", "f_196045_", "f_196046_", "f_196047_", "f_196048_", "f_196049_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PlayerPosition.class, "dimensionName;x;y;z;yRot;xRot", "f_196044_", "f_196045_", "f_196046_", "f_196047_", "f_196048_", "f_196049_"}, this);
        }

        @Override
        public final boolean equals(Object p_196063_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PlayerPosition.class, "dimensionName;x;y;z;yRot;xRot", "f_196044_", "f_196045_", "f_196046_", "f_196047_", "f_196048_", "f_196049_"}, this, p_196063_);
        }
    }
}

