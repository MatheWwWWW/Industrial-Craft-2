/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Properties;
import net.minecraft.SharedConstants;
import org.slf4j.Logger;

public class Eula {
    private static final Logger f_135938_ = LogUtils.getLogger();
    private final Path f_135939_;
    private final boolean f_135940_;

    public Eula(Path p_135943_) {
        this.f_135939_ = p_135943_;
        this.f_135940_ = SharedConstants.f_136183_ || this.m_135945_();
    }

    private boolean m_135945_() {
        boolean bl;
        block8: {
            InputStream $$0 = Files.newInputStream(this.f_135939_, new OpenOption[0]);
            try {
                Properties $$1 = new Properties();
                $$1.load($$0);
                bl = Boolean.parseBoolean($$1.getProperty("eula", "false"));
                if ($$0 == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if ($$0 != null) {
                        try {
                            $$0.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception $$2) {
                    f_135938_.warn("Failed to load {}", (Object)this.f_135939_);
                    this.m_135946_();
                    return false;
                }
            }
            $$0.close();
        }
        return bl;
    }

    public boolean m_135944_() {
        return this.f_135940_;
    }

    private void m_135946_() {
        if (SharedConstants.f_136183_) {
            return;
        }
        try (OutputStream $$0 = Files.newOutputStream(this.f_135939_, new OpenOption[0]);){
            Properties $$1 = new Properties();
            $$1.setProperty("eula", "false");
            $$1.store($$0, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://aka.ms/MinecraftEULA).");
        }
        catch (Exception $$2) {
            f_135938_.warn("Failed to save {}", (Object)this.f_135939_, (Object)$$2);
        }
    }
}

