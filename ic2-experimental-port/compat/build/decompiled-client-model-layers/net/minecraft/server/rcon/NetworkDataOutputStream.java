/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.rcon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NetworkDataOutputStream {
    private final ByteArrayOutputStream f_11467_;
    private final DataOutputStream f_11468_;

    public NetworkDataOutputStream(int p_11470_) {
        this.f_11467_ = new ByteArrayOutputStream(p_11470_);
        this.f_11468_ = new DataOutputStream(this.f_11467_);
    }

    public void m_11478_(byte[] p_11479_) throws IOException {
        this.f_11468_.write(p_11479_, 0, p_11479_.length);
    }

    public void m_11474_(String p_11475_) throws IOException {
        this.f_11468_.writeBytes(p_11475_);
        this.f_11468_.write(0);
    }

    public void m_11472_(int p_11473_) throws IOException {
        this.f_11468_.write(p_11473_);
    }

    public void m_11476_(short p_11477_) throws IOException {
        this.f_11468_.writeShort(Short.reverseBytes(p_11477_));
    }

    public void m_144018_(int p_144019_) throws IOException {
        this.f_11468_.writeInt(Integer.reverseBytes(p_144019_));
    }

    public void m_144016_(float p_144017_) throws IOException {
        this.f_11468_.writeInt(Integer.reverseBytes(Float.floatToIntBits(p_144017_)));
    }

    public byte[] m_11471_() {
        return this.f_11467_.toByteArray();
    }

    public void m_11480_() {
        this.f_11467_.reset();
    }
}

