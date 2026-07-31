/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.lwjgl.stb.STBIWriteCallback
 *  org.lwjgl.stb.STBImage
 *  org.lwjgl.stb.STBImageResize
 *  org.lwjgl.stb.STBImageWrite
 *  org.lwjgl.stb.STBTTFontinfo
 *  org.lwjgl.stb.STBTruetype
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.platform;

import com.google.common.base.Charsets;
import com.mojang.blaze3d.platform.DebugMemoryUntracker;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Base64;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.lwjgl.stb.STBIWriteCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;

public final class NativeImage
implements AutoCloseable {
    private static final Logger f_84958_ = LogUtils.getLogger();
    private static final int f_166396_ = 24;
    private static final int f_166397_ = 16;
    private static final int f_166398_ = 8;
    private static final int f_166399_ = 0;
    private static final Set<StandardOpenOption> f_84959_ = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    private final Format f_84960_;
    private final int f_84961_;
    private final int f_84962_;
    private final boolean f_84963_;
    private long f_84964_;
    private final long f_84965_;

    public NativeImage(int p_84968_, int p_84969_, boolean p_84970_) {
        this(Format.RGBA, p_84968_, p_84969_, p_84970_);
    }

    public NativeImage(Format p_84972_, int p_84973_, int p_84974_, boolean p_84975_) {
        if (p_84973_ <= 0 || p_84974_ <= 0) {
            throw new IllegalArgumentException("Invalid texture size: " + p_84973_ + "x" + p_84974_);
        }
        this.f_84960_ = p_84972_;
        this.f_84961_ = p_84973_;
        this.f_84962_ = p_84974_;
        this.f_84965_ = (long)p_84973_ * (long)p_84974_ * (long)p_84972_.m_85161_();
        this.f_84963_ = false;
        this.f_84964_ = p_84975_ ? MemoryUtil.nmemCalloc((long)1L, (long)this.f_84965_) : MemoryUtil.nmemAlloc((long)this.f_84965_);
    }

    private NativeImage(Format p_84977_, int p_84978_, int p_84979_, boolean p_84980_, long p_84981_) {
        if (p_84978_ <= 0 || p_84979_ <= 0) {
            throw new IllegalArgumentException("Invalid texture size: " + p_84978_ + "x" + p_84979_);
        }
        this.f_84960_ = p_84977_;
        this.f_84961_ = p_84978_;
        this.f_84962_ = p_84979_;
        this.f_84963_ = p_84980_;
        this.f_84964_ = p_84981_;
        this.f_84965_ = (long)p_84978_ * (long)p_84979_ * (long)p_84977_.m_85161_();
    }

    public String toString() {
        return "NativeImage[" + this.f_84960_ + " " + this.f_84961_ + "x" + this.f_84962_ + "@" + this.f_84964_ + (this.f_84963_ ? "S" : "N") + "]";
    }

    private boolean m_166422_(int p_166423_, int p_166424_) {
        return p_166423_ < 0 || p_166423_ >= this.f_84961_ || p_166424_ < 0 || p_166424_ >= this.f_84962_;
    }

    public static NativeImage m_85058_(InputStream p_85059_) throws IOException {
        return NativeImage.m_85048_(Format.RGBA, p_85059_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NativeImage m_85048_(@Nullable Format p_85049_, InputStream p_85050_) throws IOException {
        ByteBuffer $$2 = null;
        try {
            $$2 = TextureUtil.m_85303_(p_85050_);
            $$2.rewind();
            NativeImage nativeImage = NativeImage.m_85051_(p_85049_, $$2);
            return nativeImage;
        }
        finally {
            MemoryUtil.memFree((Buffer)$$2);
            IOUtils.closeQuietly((InputStream)p_85050_);
        }
    }

    public static NativeImage m_85062_(ByteBuffer p_85063_) throws IOException {
        return NativeImage.m_85051_(Format.RGBA, p_85063_);
    }

    public static NativeImage m_85051_(@Nullable Format p_85052_, ByteBuffer p_85053_) throws IOException {
        if (p_85052_ != null && !p_85052_.m_85175_()) {
            throw new UnsupportedOperationException("Don't know how to read format " + p_85052_);
        }
        if (MemoryUtil.memAddress((ByteBuffer)p_85053_) == 0L) {
            throw new IllegalArgumentException("Invalid buffer");
        }
        try (MemoryStack $$2 = MemoryStack.stackPush();){
            IntBuffer $$3 = $$2.mallocInt(1);
            IntBuffer $$4 = $$2.mallocInt(1);
            IntBuffer $$5 = $$2.mallocInt(1);
            ByteBuffer $$6 = STBImage.stbi_load_from_memory((ByteBuffer)p_85053_, (IntBuffer)$$3, (IntBuffer)$$4, (IntBuffer)$$5, (int)(p_85052_ == null ? 0 : p_85052_.f_85130_));
            if ($$6 == null) {
                throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }
            NativeImage nativeImage = new NativeImage(p_85052_ == null ? Format.m_85167_($$5.get(0)) : p_85052_, $$3.get(0), $$4.get(0), true, MemoryUtil.memAddress((ByteBuffer)$$6));
            return nativeImage;
        }
    }

    private static void m_85081_(boolean p_85082_, boolean p_85083_) {
        RenderSystem.m_187555_();
        if (p_85082_) {
            GlStateManager.m_84331_(3553, 10241, p_85083_ ? 9987 : 9729);
            GlStateManager.m_84331_(3553, 10240, 9729);
        } else {
            GlStateManager.m_84331_(3553, 10241, p_85083_ ? 9986 : 9728);
            GlStateManager.m_84331_(3553, 10240, 9728);
        }
    }

    private void m_85124_() {
        if (this.f_84964_ == 0L) {
            throw new IllegalStateException("Image is not allocated.");
        }
    }

    @Override
    public void close() {
        if (this.f_84964_ != 0L) {
            if (this.f_84963_) {
                STBImage.nstbi_image_free((long)this.f_84964_);
            } else {
                MemoryUtil.nmemFree((long)this.f_84964_);
            }
        }
        this.f_84964_ = 0L;
    }

    public int m_84982_() {
        return this.f_84961_;
    }

    public int m_85084_() {
        return this.f_84962_;
    }

    public Format m_85102_() {
        return this.f_84960_;
    }

    public int m_84985_(int p_84986_, int p_84987_) {
        if (this.f_84960_ != Format.RGBA) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "getPixelRGBA only works on RGBA images; have %s", new Object[]{this.f_84960_}));
        }
        if (this.m_166422_(p_84986_, p_84987_)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", p_84986_, p_84987_, this.f_84961_, this.f_84962_));
        }
        this.m_85124_();
        long $$2 = ((long)p_84986_ + (long)p_84987_ * (long)this.f_84961_) * 4L;
        return MemoryUtil.memGetInt((long)(this.f_84964_ + $$2));
    }

    public void m_84988_(int p_84989_, int p_84990_, int p_84991_) {
        if (this.f_84960_ != Format.RGBA) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "getPixelRGBA only works on RGBA images; have %s", new Object[]{this.f_84960_}));
        }
        if (this.m_166422_(p_84989_, p_84990_)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", p_84989_, p_84990_, this.f_84961_, this.f_84962_));
        }
        this.m_85124_();
        long $$3 = ((long)p_84989_ + (long)p_84990_ * (long)this.f_84961_) * 4L;
        MemoryUtil.memPutInt((long)(this.f_84964_ + $$3), (int)p_84991_);
    }

    public void m_166402_(int p_166403_, int p_166404_, byte p_166405_) {
        RenderSystem.m_187554_();
        if (!this.f_84960_.m_166428_()) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "setPixelLuminance only works on image with luminance; have %s", new Object[]{this.f_84960_}));
        }
        if (this.m_166422_(p_166403_, p_166404_)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", p_166403_, p_166404_, this.f_84961_, this.f_84962_));
        }
        this.m_85124_();
        long $$3 = ((long)p_166403_ + (long)p_166404_ * (long)this.f_84961_) * (long)this.f_84960_.m_85161_() + (long)(this.f_84960_.m_166432_() / 8);
        MemoryUtil.memPutByte((long)(this.f_84964_ + $$3), (byte)p_166405_);
    }

    public byte m_166408_(int p_166409_, int p_166410_) {
        RenderSystem.m_187554_();
        if (!this.f_84960_.m_166433_()) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "no red or luminance in %s", new Object[]{this.f_84960_}));
        }
        if (this.m_166422_(p_166409_, p_166410_)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", p_166409_, p_166410_, this.f_84961_, this.f_84962_));
        }
        int $$2 = (p_166409_ + p_166410_ * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_166436_() / 8;
        return MemoryUtil.memGetByte((long)(this.f_84964_ + (long)$$2));
    }

    public byte m_166415_(int p_166416_, int p_166417_) {
        RenderSystem.m_187554_();
        if (!this.f_84960_.m_166434_()) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "no green or luminance in %s", new Object[]{this.f_84960_}));
        }
        if (this.m_166422_(p_166416_, p_166417_)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", p_166416_, p_166417_, this.f_84961_, this.f_84962_));
        }
        int $$2 = (p_166416_ + p_166417_ * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_166437_() / 8;
        return MemoryUtil.memGetByte((long)(this.f_84964_ + (long)$$2));
    }

    public byte m_166418_(int p_166419_, int p_166420_) {
        RenderSystem.m_187554_();
        if (!this.f_84960_.m_166435_()) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "no blue or luminance in %s", new Object[]{this.f_84960_}));
        }
        if (this.m_166422_(p_166419_, p_166420_)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", p_166419_, p_166420_, this.f_84961_, this.f_84962_));
        }
        int $$2 = (p_166419_ + p_166420_ * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_166438_() / 8;
        return MemoryUtil.memGetByte((long)(this.f_84964_ + (long)$$2));
    }

    public byte m_85087_(int p_85088_, int p_85089_) {
        if (!this.f_84960_.m_85173_()) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "no luminance or alpha in %s", new Object[]{this.f_84960_}));
        }
        if (this.m_166422_(p_85088_, p_85089_)) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", p_85088_, p_85089_, this.f_84961_, this.f_84962_));
        }
        int $$2 = (p_85088_ + p_85089_ * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_85174_() / 8;
        return MemoryUtil.memGetByte((long)(this.f_84964_ + (long)$$2));
    }

    public void m_166411_(int p_166412_, int p_166413_, int p_166414_) {
        if (this.f_84960_ != Format.RGBA) {
            throw new UnsupportedOperationException("Can only call blendPixel with RGBA format");
        }
        int $$3 = this.m_84985_(p_166412_, p_166413_);
        float $$4 = (float)NativeImage.m_84983_(p_166414_) / 255.0f;
        float $$5 = (float)NativeImage.m_85119_(p_166414_) / 255.0f;
        float $$6 = (float)NativeImage.m_85103_(p_166414_) / 255.0f;
        float $$7 = (float)NativeImage.m_85085_(p_166414_) / 255.0f;
        float $$8 = (float)NativeImage.m_84983_($$3) / 255.0f;
        float $$9 = (float)NativeImage.m_85119_($$3) / 255.0f;
        float $$10 = (float)NativeImage.m_85103_($$3) / 255.0f;
        float $$11 = (float)NativeImage.m_85085_($$3) / 255.0f;
        float $$12 = $$4;
        float $$13 = 1.0f - $$4;
        float $$14 = $$4 * $$12 + $$8 * $$13;
        float $$15 = $$5 * $$12 + $$9 * $$13;
        float $$16 = $$6 * $$12 + $$10 * $$13;
        float $$17 = $$7 * $$12 + $$11 * $$13;
        if ($$14 > 1.0f) {
            $$14 = 1.0f;
        }
        if ($$15 > 1.0f) {
            $$15 = 1.0f;
        }
        if ($$16 > 1.0f) {
            $$16 = 1.0f;
        }
        if ($$17 > 1.0f) {
            $$17 = 1.0f;
        }
        int $$18 = (int)($$14 * 255.0f);
        int $$19 = (int)($$15 * 255.0f);
        int $$20 = (int)($$16 * 255.0f);
        int $$21 = (int)($$17 * 255.0f);
        this.m_84988_(p_166412_, p_166413_, NativeImage.m_84992_($$18, $$19, $$20, $$21));
    }

    @Deprecated
    public int[] m_85118_() {
        if (this.f_84960_ != Format.RGBA) {
            throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
        }
        this.m_85124_();
        int[] $$0 = new int[this.m_84982_() * this.m_85084_()];
        for (int $$1 = 0; $$1 < this.m_85084_(); ++$$1) {
            for (int $$2 = 0; $$2 < this.m_84982_(); ++$$2) {
                int $$8;
                int $$3 = this.m_84985_($$2, $$1);
                int $$4 = NativeImage.m_84983_($$3);
                int $$5 = NativeImage.m_85119_($$3);
                int $$6 = NativeImage.m_85103_($$3);
                int $$7 = NativeImage.m_85085_($$3);
                $$0[$$2 + $$1 * this.m_84982_()] = $$8 = $$4 << 24 | $$7 << 16 | $$6 << 8 | $$5;
            }
        }
        return $$0;
    }

    public void m_85040_(int p_85041_, int p_85042_, int p_85043_, boolean p_85044_) {
        this.m_85003_(p_85041_, p_85042_, p_85043_, 0, 0, this.f_84961_, this.f_84962_, false, p_85044_);
    }

    public void m_85003_(int p_85004_, int p_85005_, int p_85006_, int p_85007_, int p_85008_, int p_85009_, int p_85010_, boolean p_85011_, boolean p_85012_) {
        this.m_85013_(p_85004_, p_85005_, p_85006_, p_85007_, p_85008_, p_85009_, p_85010_, false, false, p_85011_, p_85012_);
    }

    public void m_85013_(int p_85014_, int p_85015_, int p_85016_, int p_85017_, int p_85018_, int p_85019_, int p_85020_, boolean p_85021_, boolean p_85022_, boolean p_85023_, boolean p_85024_) {
        if (!RenderSystem.m_69587_()) {
            RenderSystem.m_69879_(() -> this.m_85090_(p_85014_, p_85015_, p_85016_, p_85017_, p_85018_, p_85019_, p_85020_, p_85021_, p_85022_, p_85023_, p_85024_));
        } else {
            this.m_85090_(p_85014_, p_85015_, p_85016_, p_85017_, p_85018_, p_85019_, p_85020_, p_85021_, p_85022_, p_85023_, p_85024_);
        }
    }

    private void m_85090_(int p_85091_, int p_85092_, int p_85093_, int p_85094_, int p_85095_, int p_85096_, int p_85097_, boolean p_85098_, boolean p_85099_, boolean p_85100_, boolean p_85101_) {
        RenderSystem.m_187555_();
        this.m_85124_();
        NativeImage.m_85081_(p_85098_, p_85100_);
        if (p_85096_ == this.m_84982_()) {
            GlStateManager.m_84522_(3314, 0);
        } else {
            GlStateManager.m_84522_(3314, this.m_84982_());
        }
        GlStateManager.m_84522_(3316, p_85094_);
        GlStateManager.m_84522_(3315, p_85095_);
        this.f_84960_.m_85169_();
        GlStateManager.m_84199_(3553, p_85091_, p_85092_, p_85093_, p_85096_, p_85097_, this.f_84960_.m_85170_(), 5121, this.f_84964_);
        if (p_85099_) {
            GlStateManager.m_84331_(3553, 10242, 33071);
            GlStateManager.m_84331_(3553, 10243, 33071);
        }
        if (p_85101_) {
            this.close();
        }
    }

    public void m_85045_(int p_85046_, boolean p_85047_) {
        RenderSystem.m_187554_();
        this.m_85124_();
        this.f_84960_.m_85166_();
        GlStateManager.m_84227_(3553, p_85046_, this.f_84960_.m_85170_(), 5121, this.f_84964_);
        if (p_85047_ && this.f_84960_.m_85171_()) {
            for (int $$2 = 0; $$2 < this.m_85084_(); ++$$2) {
                for (int $$3 = 0; $$3 < this.m_84982_(); ++$$3) {
                    this.m_84988_($$3, $$2, this.m_84985_($$3, $$2) | 255 << this.f_84960_.m_85172_());
                }
            }
        }
    }

    public void m_166400_(float p_166401_) {
        RenderSystem.m_187554_();
        if (this.f_84960_.m_85161_() != 1) {
            throw new IllegalStateException("Depth buffer must be stored in NativeImage with 1 component.");
        }
        this.m_85124_();
        this.f_84960_.m_85166_();
        GlStateManager.m_157100_(0, 0, this.f_84961_, this.f_84962_, 6402, 5121, this.f_84964_);
    }

    public void m_166421_() {
        RenderSystem.m_187554_();
        this.f_84960_.m_85169_();
        GlStateManager.m_157078_(this.f_84961_, this.f_84962_, this.f_84960_.m_85170_(), 5121, this.f_84964_);
    }

    public void m_166406_(String p_166407_) throws IOException {
        this.m_85066_(FileSystems.getDefault().getPath(p_166407_, new String[0]));
    }

    public void m_85056_(File p_85057_) throws IOException {
        this.m_85066_(p_85057_.toPath());
    }

    public void m_85068_(STBTTFontinfo p_85069_, int p_85070_, int p_85071_, int p_85072_, float p_85073_, float p_85074_, float p_85075_, float p_85076_, int p_85077_, int p_85078_) {
        if (p_85077_ < 0 || p_85077_ + p_85071_ > this.m_84982_() || p_85078_ < 0 || p_85078_ + p_85072_ > this.m_85084_()) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Out of bounds: start: (%s, %s) (size: %sx%s); size: %sx%s", p_85077_, p_85078_, p_85071_, p_85072_, this.m_84982_(), this.m_85084_()));
        }
        if (this.f_84960_.m_85161_() != 1) {
            throw new IllegalArgumentException("Can only write fonts into 1-component images.");
        }
        STBTruetype.nstbtt_MakeGlyphBitmapSubpixel((long)p_85069_.address(), (long)(this.f_84964_ + (long)p_85077_ + (long)(p_85078_ * this.m_84982_())), (int)p_85071_, (int)p_85072_, (int)this.m_84982_(), (float)p_85073_, (float)p_85074_, (float)p_85075_, (float)p_85076_, (int)p_85070_);
    }

    public void m_85066_(Path p_85067_) throws IOException {
        if (!this.f_84960_.m_85175_()) {
            throw new UnsupportedOperationException("Don't know how to write format " + this.f_84960_);
        }
        this.m_85124_();
        try (SeekableByteChannel $$1 = Files.newByteChannel(p_85067_, f_84959_, new FileAttribute[0]);){
            if (!this.m_85064_($$1)) {
                throw new IOException("Could not write image to the PNG file \"" + p_85067_.toAbsolutePath() + "\": " + STBImage.stbi_failure_reason());
            }
        }
    }

    public byte[] m_85121_() throws IOException {
        try (ByteArrayOutputStream $$0 = new ByteArrayOutputStream();){
            byte[] byArray;
            block12: {
                WritableByteChannel $$1 = Channels.newChannel($$0);
                try {
                    if (!this.m_85064_($$1)) {
                        throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
                    }
                    byArray = $$0.toByteArray();
                    if ($$1 == null) break block12;
                }
                catch (Throwable throwable) {
                    if ($$1 != null) {
                        try {
                            $$1.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                $$1.close();
            }
            return byArray;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean m_85064_(WritableByteChannel p_85065_) throws IOException {
        WriteCallback $$1 = new WriteCallback(p_85065_);
        try {
            int $$2 = Math.min(this.m_85084_(), Integer.MAX_VALUE / this.m_84982_() / this.f_84960_.m_85161_());
            if ($$2 < this.m_85084_()) {
                f_84958_.warn("Dropping image height from {} to {} to fit the size into 32-bit signed int", (Object)this.m_85084_(), (Object)$$2);
            }
            if (STBImageWrite.nstbi_write_png_to_func((long)$$1.address(), (long)0L, (int)this.m_84982_(), (int)$$2, (int)this.f_84960_.m_85161_(), (long)this.f_84964_, (int)0) == 0) {
                boolean bl = false;
                return bl;
            }
            $$1.m_85202_();
            boolean bl = true;
            return bl;
        }
        finally {
            $$1.free();
        }
    }

    public void m_85054_(NativeImage p_85055_) {
        if (p_85055_.m_85102_() != this.f_84960_) {
            throw new UnsupportedOperationException("Image formats don't match.");
        }
        int $$1 = this.f_84960_.m_85161_();
        this.m_85124_();
        p_85055_.m_85124_();
        if (this.f_84961_ == p_85055_.f_84961_) {
            MemoryUtil.memCopy((long)p_85055_.f_84964_, (long)this.f_84964_, (long)Math.min(this.f_84965_, p_85055_.f_84965_));
        } else {
            int $$2 = Math.min(this.m_84982_(), p_85055_.m_84982_());
            int $$3 = Math.min(this.m_85084_(), p_85055_.m_85084_());
            for (int $$4 = 0; $$4 < $$3; ++$$4) {
                int $$5 = $$4 * p_85055_.m_84982_() * $$1;
                int $$6 = $$4 * this.m_84982_() * $$1;
                MemoryUtil.memCopy((long)(p_85055_.f_84964_ + (long)$$5), (long)(this.f_84964_ + (long)$$6), (long)$$2);
            }
        }
    }

    public void m_84997_(int p_84998_, int p_84999_, int p_85000_, int p_85001_, int p_85002_) {
        for (int $$5 = p_84999_; $$5 < p_84999_ + p_85001_; ++$$5) {
            for (int $$6 = p_84998_; $$6 < p_84998_ + p_85000_; ++$$6) {
                this.m_84988_($$6, $$5, p_85002_);
            }
        }
    }

    public void m_85025_(int p_85026_, int p_85027_, int p_85028_, int p_85029_, int p_85030_, int p_85031_, boolean p_85032_, boolean p_85033_) {
        for (int $$8 = 0; $$8 < p_85031_; ++$$8) {
            for (int $$9 = 0; $$9 < p_85030_; ++$$9) {
                int $$10 = p_85032_ ? p_85030_ - 1 - $$9 : $$9;
                int $$11 = p_85033_ ? p_85031_ - 1 - $$8 : $$8;
                int $$12 = this.m_84985_(p_85026_ + $$9, p_85027_ + $$8);
                this.m_84988_(p_85026_ + p_85028_ + $$10, p_85027_ + p_85029_ + $$11, $$12);
            }
        }
    }

    public void m_85122_() {
        this.m_85124_();
        try (MemoryStack $$0 = MemoryStack.stackPush();){
            int $$1 = this.f_84960_.m_85161_();
            int $$2 = this.m_84982_() * $$1;
            long $$3 = $$0.nmalloc($$2);
            for (int $$4 = 0; $$4 < this.m_85084_() / 2; ++$$4) {
                int $$5 = $$4 * this.m_84982_() * $$1;
                int $$6 = (this.m_85084_() - 1 - $$4) * this.m_84982_() * $$1;
                MemoryUtil.memCopy((long)(this.f_84964_ + (long)$$5), (long)$$3, (long)$$2);
                MemoryUtil.memCopy((long)(this.f_84964_ + (long)$$6), (long)(this.f_84964_ + (long)$$5), (long)$$2);
                MemoryUtil.memCopy((long)$$3, (long)(this.f_84964_ + (long)$$6), (long)$$2);
            }
        }
    }

    public void m_85034_(int p_85035_, int p_85036_, int p_85037_, int p_85038_, NativeImage p_85039_) {
        this.m_85124_();
        if (p_85039_.m_85102_() != this.f_84960_) {
            throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
        }
        int $$5 = this.f_84960_.m_85161_();
        STBImageResize.nstbir_resize_uint8((long)(this.f_84964_ + (long)((p_85035_ + p_85036_ * this.m_84982_()) * $$5)), (int)p_85037_, (int)p_85038_, (int)(this.m_84982_() * $$5), (long)p_85039_.f_84964_, (int)p_85039_.m_84982_(), (int)p_85039_.m_85084_(), (int)0, (int)$$5);
    }

    public void m_85123_() {
        DebugMemoryUntracker.m_84001_(this.f_84964_);
    }

    public static NativeImage m_85060_(String p_85061_) throws IOException {
        byte[] $$1 = Base64.getDecoder().decode(p_85061_.replaceAll("\n", "").getBytes(Charsets.UTF_8));
        try (MemoryStack $$2 = MemoryStack.stackPush();){
            ByteBuffer $$3 = $$2.malloc($$1.length);
            $$3.put($$1);
            $$3.rewind();
            NativeImage nativeImage = NativeImage.m_85062_($$3);
            return nativeImage;
        }
    }

    public static int m_84983_(int p_84984_) {
        return p_84984_ >> 24 & 0xFF;
    }

    public static int m_85085_(int p_85086_) {
        return p_85086_ >> 0 & 0xFF;
    }

    public static int m_85103_(int p_85104_) {
        return p_85104_ >> 8 & 0xFF;
    }

    public static int m_85119_(int p_85120_) {
        return p_85120_ >> 16 & 0xFF;
    }

    public static int m_84992_(int p_84993_, int p_84994_, int p_84995_, int p_84996_) {
        return (p_84993_ & 0xFF) << 24 | (p_84994_ & 0xFF) << 16 | (p_84995_ & 0xFF) << 8 | (p_84996_ & 0xFF) << 0;
    }

    public static final class Format
    extends Enum<Format> {
        public static final /* enum */ Format RGBA = new Format(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true);
        public static final /* enum */ Format RGB = new Format(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true);
        public static final /* enum */ Format LUMINANCE_ALPHA = new Format(2, 33319, false, false, false, true, true, 255, 255, 255, 0, 8, true);
        public static final /* enum */ Format LUMINANCE = new Format(1, 6403, false, false, false, true, false, 0, 0, 0, 0, 255, true);
        final int f_85130_;
        private final int f_85131_;
        private final boolean f_85132_;
        private final boolean f_85133_;
        private final boolean f_85134_;
        private final boolean f_85135_;
        private final boolean f_85136_;
        private final int f_85137_;
        private final int f_85138_;
        private final int f_85139_;
        private final int f_85140_;
        private final int f_85141_;
        private final boolean f_85142_;
        private static final /* synthetic */ Format[] $VALUES;

        public static Format[] values() {
            return (Format[])$VALUES.clone();
        }

        public static Format valueOf(String p_85177_) {
            return Enum.valueOf(Format.class, p_85177_);
        }

        private Format(int p_85148_, int p_85149_, boolean p_85150_, boolean p_85151_, boolean p_85152_, boolean p_85153_, boolean p_85154_, int p_85155_, int p_85156_, int p_85157_, int p_85158_, int p_85159_, boolean p_85160_) {
            this.f_85130_ = p_85148_;
            this.f_85131_ = p_85149_;
            this.f_85132_ = p_85150_;
            this.f_85133_ = p_85151_;
            this.f_85134_ = p_85152_;
            this.f_85135_ = p_85153_;
            this.f_85136_ = p_85154_;
            this.f_85137_ = p_85155_;
            this.f_85138_ = p_85156_;
            this.f_85139_ = p_85157_;
            this.f_85140_ = p_85158_;
            this.f_85141_ = p_85159_;
            this.f_85142_ = p_85160_;
        }

        public int m_85161_() {
            return this.f_85130_;
        }

        public void m_85166_() {
            RenderSystem.m_187554_();
            GlStateManager.m_84522_(3333, this.m_85161_());
        }

        public void m_85169_() {
            RenderSystem.m_187555_();
            GlStateManager.m_84522_(3317, this.m_85161_());
        }

        public int m_85170_() {
            return this.f_85131_;
        }

        public boolean m_166425_() {
            return this.f_85132_;
        }

        public boolean m_166426_() {
            return this.f_85133_;
        }

        public boolean m_166427_() {
            return this.f_85134_;
        }

        public boolean m_166428_() {
            return this.f_85135_;
        }

        public boolean m_85171_() {
            return this.f_85136_;
        }

        public int m_166429_() {
            return this.f_85137_;
        }

        public int m_166430_() {
            return this.f_85138_;
        }

        public int m_166431_() {
            return this.f_85139_;
        }

        public int m_166432_() {
            return this.f_85140_;
        }

        public int m_85172_() {
            return this.f_85141_;
        }

        public boolean m_166433_() {
            return this.f_85135_ || this.f_85132_;
        }

        public boolean m_166434_() {
            return this.f_85135_ || this.f_85133_;
        }

        public boolean m_166435_() {
            return this.f_85135_ || this.f_85134_;
        }

        public boolean m_85173_() {
            return this.f_85135_ || this.f_85136_;
        }

        public int m_166436_() {
            return this.f_85135_ ? this.f_85140_ : this.f_85137_;
        }

        public int m_166437_() {
            return this.f_85135_ ? this.f_85140_ : this.f_85138_;
        }

        public int m_166438_() {
            return this.f_85135_ ? this.f_85140_ : this.f_85139_;
        }

        public int m_85174_() {
            return this.f_85135_ ? this.f_85140_ : this.f_85141_;
        }

        public boolean m_85175_() {
            return this.f_85142_;
        }

        static Format m_85167_(int p_85168_) {
            switch (p_85168_) {
                case 1: {
                    return LUMINANCE;
                }
                case 2: {
                    return LUMINANCE_ALPHA;
                }
                case 3: {
                    return RGB;
                }
            }
            return RGBA;
        }

        private static /* synthetic */ Format[] m_166439_() {
            return new Format[]{RGBA, RGB, LUMINANCE_ALPHA, LUMINANCE};
        }

        static {
            $VALUES = Format.m_166439_();
        }
    }

    static class WriteCallback
    extends STBIWriteCallback {
        private final WritableByteChannel f_85195_;
        @Nullable
        private IOException f_85196_;

        WriteCallback(WritableByteChannel p_85198_) {
            this.f_85195_ = p_85198_;
        }

        public void invoke(long p_85204_, long p_85205_, int p_85206_) {
            ByteBuffer $$3 = WriteCallback.getData((long)p_85205_, (int)p_85206_);
            try {
                this.f_85195_.write($$3);
            }
            catch (IOException $$4) {
                this.f_85196_ = $$4;
            }
        }

        public void m_85202_() throws IOException {
            if (this.f_85196_ != null) {
                throw this.f_85196_;
            }
        }
    }

    public static final class InternalGlFormat
    extends Enum<InternalGlFormat> {
        public static final /* enum */ InternalGlFormat RGBA = new InternalGlFormat(6408);
        public static final /* enum */ InternalGlFormat RGB = new InternalGlFormat(6407);
        public static final /* enum */ InternalGlFormat RG = new InternalGlFormat(33319);
        public static final /* enum */ InternalGlFormat RED = new InternalGlFormat(6403);
        private final int f_85184_;
        private static final /* synthetic */ InternalGlFormat[] $VALUES;

        public static InternalGlFormat[] values() {
            return (InternalGlFormat[])$VALUES.clone();
        }

        public static InternalGlFormat valueOf(String p_85193_) {
            return Enum.valueOf(InternalGlFormat.class, p_85193_);
        }

        private InternalGlFormat(int p_85190_) {
            this.f_85184_ = p_85190_;
        }

        public int m_85191_() {
            return this.f_85184_;
        }

        private static /* synthetic */ InternalGlFormat[] m_166442_() {
            return new InternalGlFormat[]{RGBA, RGB, RG, RED};
        }

        static {
            $VALUES = InternalGlFormat.m_166442_();
        }
    }
}

