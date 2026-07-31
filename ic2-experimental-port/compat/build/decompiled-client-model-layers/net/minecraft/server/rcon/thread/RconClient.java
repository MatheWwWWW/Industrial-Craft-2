/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.rcon.thread;

import com.mojang.logging.LogUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.rcon.PktUtils;
import net.minecraft.server.rcon.thread.GenericThread;
import org.slf4j.Logger;

public class RconClient
extends GenericThread {
    private static final Logger f_11579_ = LogUtils.getLogger();
    private static final int f_144029_ = 3;
    private static final int f_144030_ = 2;
    private static final int f_144031_ = 0;
    private static final int f_144032_ = 2;
    private static final int f_144033_ = -1;
    private boolean f_11580_;
    private final Socket f_11581_;
    private final byte[] f_11582_ = new byte[1460];
    private final String f_11583_;
    private final ServerInterface f_11584_;

    RconClient(ServerInterface p_11587_, String p_11588_, Socket p_11589_) {
        super("RCON Client " + p_11589_.getInetAddress());
        this.f_11584_ = p_11587_;
        this.f_11581_ = p_11589_;
        try {
            this.f_11581_.setSoTimeout(0);
        }
        catch (Exception $$3) {
            this.f_11515_ = false;
        }
        this.f_11583_ = p_11588_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            while (this.f_11515_) {
                BufferedInputStream $$0 = new BufferedInputStream(this.f_11581_.getInputStream());
                int $$1 = $$0.read(this.f_11582_, 0, 1460);
                if (10 > $$1) {
                    return;
                }
                int $$2 = 0;
                int $$3 = PktUtils.m_11492_(this.f_11582_, 0, $$1);
                if ($$3 != $$1 - 4) {
                    return;
                }
                int $$4 = PktUtils.m_11492_(this.f_11582_, $$2 += 4, $$1);
                int $$5 = PktUtils.m_11485_(this.f_11582_, $$2 += 4);
                $$2 += 4;
                switch ($$5) {
                    case 3: {
                        String $$6 = PktUtils.m_11488_(this.f_11582_, $$2, $$1);
                        $$2 += $$6.length();
                        if (!$$6.isEmpty() && $$6.equals(this.f_11583_)) {
                            this.f_11580_ = true;
                            this.m_11590_($$4, 2, "");
                            break;
                        }
                        this.f_11580_ = false;
                        this.m_11598_();
                        break;
                    }
                    case 2: {
                        if (this.f_11580_) {
                            String $$7 = PktUtils.m_11488_(this.f_11582_, $$2, $$1);
                            try {
                                this.m_11594_($$4, this.f_11584_.m_7261_($$7));
                            }
                            catch (Exception $$8) {
                                this.m_11594_($$4, "Error executing: " + $$7 + " (" + $$8.getMessage() + ")");
                            }
                            break;
                        }
                        this.m_11598_();
                        break;
                    }
                    default: {
                        this.m_11594_($$4, String.format(Locale.ROOT, "Unknown request %s", Integer.toHexString($$5)));
                    }
                }
            }
        }
        catch (IOException $$0) {
        }
        catch (Exception $$9) {
            f_11579_.error("Exception whilst parsing RCON input", (Throwable)$$9);
        }
        finally {
            this.m_11599_();
            f_11579_.info("Thread {} shutting down", (Object)this.f_11516_);
            this.f_11515_ = false;
        }
    }

    private void m_11590_(int p_11591_, int p_11592_, String p_11593_) throws IOException {
        ByteArrayOutputStream $$3 = new ByteArrayOutputStream(1248);
        DataOutputStream $$4 = new DataOutputStream($$3);
        byte[] $$5 = p_11593_.getBytes(StandardCharsets.UTF_8);
        $$4.writeInt(Integer.reverseBytes($$5.length + 10));
        $$4.writeInt(Integer.reverseBytes(p_11591_));
        $$4.writeInt(Integer.reverseBytes(p_11592_));
        $$4.write($$5);
        $$4.write(0);
        $$4.write(0);
        this.f_11581_.getOutputStream().write($$3.toByteArray());
    }

    private void m_11598_() throws IOException {
        this.m_11590_(-1, 2, "");
    }

    private void m_11594_(int p_11595_, String p_11596_) throws IOException {
        int $$3;
        int $$2 = p_11596_.length();
        do {
            $$3 = 4096 <= $$2 ? 4096 : $$2;
            this.m_11590_(p_11595_, 0, p_11596_.substring(0, $$3));
        } while (0 != ($$2 = (p_11596_ = p_11596_.substring($$3)).length()));
    }

    @Override
    public void m_7530_() {
        this.f_11515_ = false;
        this.m_11599_();
        super.m_7530_();
    }

    private void m_11599_() {
        try {
            this.f_11581_.close();
        }
        catch (IOException $$0) {
            f_11579_.warn("Failed to close socket", (Throwable)$$0);
        }
    }
}

