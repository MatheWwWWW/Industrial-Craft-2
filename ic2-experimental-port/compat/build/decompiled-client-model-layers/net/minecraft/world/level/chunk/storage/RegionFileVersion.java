/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk.storage;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;
import net.minecraft.util.FastBufferedInputStream;

public class RegionFileVersion {
    private static final Int2ObjectMap<RegionFileVersion> f_63746_ = new Int2ObjectOpenHashMap();
    public static final RegionFileVersion f_63743_ = RegionFileVersion.m_63758_(new RegionFileVersion(1, p_63767_ -> new FastBufferedInputStream(new GZIPInputStream((InputStream)p_63767_)), p_63769_ -> new BufferedOutputStream(new GZIPOutputStream((OutputStream)p_63769_))));
    public static final RegionFileVersion f_63744_ = RegionFileVersion.m_63758_(new RegionFileVersion(2, p_196964_ -> new FastBufferedInputStream(new InflaterInputStream((InputStream)p_196964_)), p_196966_ -> new BufferedOutputStream(new DeflaterOutputStream((OutputStream)p_196966_))));
    public static final RegionFileVersion f_63745_ = RegionFileVersion.m_63758_(new RegionFileVersion(3, p_196960_ -> p_196960_, p_196962_ -> p_196962_));
    private final int f_63747_;
    private final StreamWrapper<InputStream> f_63748_;
    private final StreamWrapper<OutputStream> f_63749_;

    private RegionFileVersion(int p_63752_, StreamWrapper<InputStream> p_63753_, StreamWrapper<OutputStream> p_63754_) {
        this.f_63747_ = p_63752_;
        this.f_63748_ = p_63753_;
        this.f_63749_ = p_63754_;
    }

    private static RegionFileVersion m_63758_(RegionFileVersion p_63759_) {
        f_63746_.put(p_63759_.f_63747_, (Object)p_63759_);
        return p_63759_;
    }

    @Nullable
    public static RegionFileVersion m_63756_(int p_63757_) {
        return (RegionFileVersion)f_63746_.get(p_63757_);
    }

    public static boolean m_63764_(int p_63765_) {
        return f_63746_.containsKey(p_63765_);
    }

    public int m_63755_() {
        return this.f_63747_;
    }

    public OutputStream m_63762_(OutputStream p_63763_) throws IOException {
        return this.f_63749_.m_63770_(p_63763_);
    }

    public InputStream m_63760_(InputStream p_63761_) throws IOException {
        return this.f_63748_.m_63770_(p_63761_);
    }

    @FunctionalInterface
    static interface StreamWrapper<O> {
        public O m_63770_(O var1) throws IOException;
    }
}

