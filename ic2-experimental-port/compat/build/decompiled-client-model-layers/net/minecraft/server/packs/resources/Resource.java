/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.server.packs.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.server.packs.resources.ResourceMetadata;

public class Resource {
    private final String f_215495_;
    private final IoSupplier<InputStream> f_215496_;
    private final IoSupplier<ResourceMetadata> f_215497_;
    @Nullable
    private ResourceMetadata f_215498_;

    public Resource(String p_215503_, IoSupplier<InputStream> p_215504_, IoSupplier<ResourceMetadata> p_215505_) {
        this.f_215495_ = p_215503_;
        this.f_215496_ = p_215504_;
        this.f_215497_ = p_215505_;
    }

    public Resource(String p_215500_, IoSupplier<InputStream> p_215501_) {
        this.f_215495_ = p_215500_;
        this.f_215496_ = p_215501_;
        this.f_215497_ = () -> ResourceMetadata.f_215577_;
        this.f_215498_ = ResourceMetadata.f_215577_;
    }

    public String m_215506_() {
        return this.f_215495_;
    }

    public InputStream m_215507_() throws IOException {
        return this.f_215496_.m_215511_();
    }

    public BufferedReader m_215508_() throws IOException {
        return new BufferedReader(new InputStreamReader(this.m_215507_(), StandardCharsets.UTF_8));
    }

    public ResourceMetadata m_215509_() throws IOException {
        if (this.f_215498_ == null) {
            this.f_215498_ = this.f_215497_.m_215511_();
        }
        return this.f_215498_;
    }

    @FunctionalInterface
    public static interface IoSupplier<T> {
        public T m_215511_() throws IOException;
    }
}

