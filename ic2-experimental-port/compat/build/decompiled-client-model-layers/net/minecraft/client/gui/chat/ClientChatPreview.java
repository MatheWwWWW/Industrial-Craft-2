/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.client.gui.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.ChatPreviewRequests;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.StringUtils;

public class ClientChatPreview {
    private static final long f_232404_ = 200L;
    @Nullable
    private String f_232406_;
    @Nullable
    private String f_232407_;
    private final ChatPreviewRequests f_232408_;
    @Nullable
    private Preview f_232409_;

    public ClientChatPreview(Minecraft p_232411_) {
        this.f_232408_ = new ChatPreviewRequests(p_232411_);
    }

    public void m_232412_() {
        String $$0 = this.f_232407_;
        if ($$0 != null && this.f_232408_.m_232380_($$0, Util.m_137550_())) {
            this.f_232407_ = null;
        }
    }

    public void m_232416_(String p_232417_) {
        if (!(p_232417_ = ClientChatPreview.m_232425_(p_232417_)).isEmpty()) {
            if (!p_232417_.equals(this.f_232406_)) {
                this.f_232406_ = p_232417_;
                this.m_232422_(p_232417_);
            }
        } else {
            this.m_232427_();
        }
    }

    private void m_232422_(String p_232423_) {
        this.f_232407_ = !this.f_232408_.m_232380_(p_232423_, Util.m_137550_()) ? p_232423_ : null;
    }

    public void m_232418_() {
        this.m_232427_();
    }

    private void m_232427_() {
        this.f_232406_ = null;
        this.f_232407_ = null;
        this.f_232409_ = null;
        this.f_232408_.m_232375_();
    }

    public void m_232413_(int p_232414_, @Nullable Component p_232415_) {
        String $$2 = this.f_232408_.m_232376_(p_232414_);
        if ($$2 != null) {
            this.f_232409_ = new Preview(Util.m_137550_(), $$2, p_232415_);
        }
    }

    public boolean m_241947_() {
        return this.f_232407_ != null || this.f_232409_ != null && !this.f_232409_.m_241893_();
    }

    public boolean m_241933_(String p_242426_) {
        return ClientChatPreview.m_232425_(p_242426_).equals(this.f_232406_);
    }

    @Nullable
    public Preview m_241808_() {
        return this.f_232409_;
    }

    @Nullable
    public Preview m_241899_(String p_242462_) {
        if (this.f_232409_ != null && this.f_232409_.m_232436_(p_242462_)) {
            Preview $$1 = this.f_232409_;
            this.f_232409_ = null;
            return $$1;
        }
        return null;
    }

    static String m_232425_(String p_232426_) {
        return StringUtils.normalizeSpace((String)p_232426_.trim());
    }

    public record Preview(long f_232428_, String f_232429_, @Nullable Component f_232430_) {
        public Preview {
            f_232429_ = ClientChatPreview.m_232425_(f_232429_);
        }

        private boolean m_241958_(String p_242232_) {
            return this.f_232429_.equals(ClientChatPreview.m_232425_(p_242232_));
        }

        boolean m_232436_(String p_232437_) {
            if (this.m_241958_(p_232437_)) {
                return this.m_241893_();
            }
            return false;
        }

        boolean m_241893_() {
            long $$0 = this.f_232428_ + 200L;
            return Util.m_137550_() >= $$0;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Preview.class, "receivedTimeStamp;query;response", "f_232428_", "f_232429_", "f_232430_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Preview.class, "receivedTimeStamp;query;response", "f_232428_", "f_232429_", "f_232430_"}, this);
        }

        @Override
        public final boolean equals(Object p_232441_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Preview.class, "receivedTimeStamp;query;response", "f_232428_", "f_232429_", "f_232430_"}, this, p_232441_);
        }
    }
}

