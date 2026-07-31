/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.lwjgl.glfw.GLFWVidMode
 *  org.lwjgl.glfw.GLFWVidMode$Buffer
 */
package com.mojang.blaze3d.platform;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFWVidMode;

public final class VideoMode {
    private final int f_85313_;
    private final int f_85314_;
    private final int f_85315_;
    private final int f_85316_;
    private final int f_85317_;
    private final int f_85318_;
    private static final Pattern f_85319_ = Pattern.compile("(\\d+)x(\\d+)(?:@(\\d+)(?::(\\d+))?)?");

    public VideoMode(int p_85322_, int p_85323_, int p_85324_, int p_85325_, int p_85326_, int p_85327_) {
        this.f_85313_ = p_85322_;
        this.f_85314_ = p_85323_;
        this.f_85315_ = p_85324_;
        this.f_85316_ = p_85325_;
        this.f_85317_ = p_85326_;
        this.f_85318_ = p_85327_;
    }

    public VideoMode(GLFWVidMode.Buffer p_85329_) {
        this.f_85313_ = p_85329_.width();
        this.f_85314_ = p_85329_.height();
        this.f_85315_ = p_85329_.redBits();
        this.f_85316_ = p_85329_.greenBits();
        this.f_85317_ = p_85329_.blueBits();
        this.f_85318_ = p_85329_.refreshRate();
    }

    public VideoMode(GLFWVidMode p_85331_) {
        this.f_85313_ = p_85331_.width();
        this.f_85314_ = p_85331_.height();
        this.f_85315_ = p_85331_.redBits();
        this.f_85316_ = p_85331_.greenBits();
        this.f_85317_ = p_85331_.blueBits();
        this.f_85318_ = p_85331_.refreshRate();
    }

    public int m_85332_() {
        return this.f_85313_;
    }

    public int m_85335_() {
        return this.f_85314_;
    }

    public int m_85336_() {
        return this.f_85315_;
    }

    public int m_85337_() {
        return this.f_85316_;
    }

    public int m_85338_() {
        return this.f_85317_;
    }

    public int m_85341_() {
        return this.f_85318_;
    }

    public boolean equals(Object p_85340_) {
        if (this == p_85340_) {
            return true;
        }
        if (p_85340_ == null || this.getClass() != p_85340_.getClass()) {
            return false;
        }
        VideoMode $$1 = (VideoMode)p_85340_;
        return this.f_85313_ == $$1.f_85313_ && this.f_85314_ == $$1.f_85314_ && this.f_85315_ == $$1.f_85315_ && this.f_85316_ == $$1.f_85316_ && this.f_85317_ == $$1.f_85317_ && this.f_85318_ == $$1.f_85318_;
    }

    public int hashCode() {
        return Objects.hash(this.f_85313_, this.f_85314_, this.f_85315_, this.f_85316_, this.f_85317_, this.f_85318_);
    }

    public String toString() {
        return String.format(Locale.ROOT, "%sx%s@%s (%sbit)", this.f_85313_, this.f_85314_, this.f_85318_, this.f_85315_ + this.f_85316_ + this.f_85317_);
    }

    public static Optional<VideoMode> m_85333_(@Nullable String p_85334_) {
        if (p_85334_ == null) {
            return Optional.empty();
        }
        try {
            Matcher $$1 = f_85319_.matcher(p_85334_);
            if ($$1.matches()) {
                int $$9;
                int $$6;
                int $$2 = Integer.parseInt($$1.group(1));
                int $$3 = Integer.parseInt($$1.group(2));
                String $$4 = $$1.group(3);
                if ($$4 == null) {
                    int $$5 = 60;
                } else {
                    $$6 = Integer.parseInt($$4);
                }
                String $$7 = $$1.group(4);
                if ($$7 == null) {
                    int $$8 = 24;
                } else {
                    $$9 = Integer.parseInt($$7);
                }
                int $$10 = $$9 / 3;
                return Optional.of(new VideoMode($$2, $$3, $$10, $$10, $$10, $$6));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return Optional.empty();
    }

    public String m_85342_() {
        return String.format(Locale.ROOT, "%sx%s@%s:%s", this.f_85313_, this.f_85314_, this.f_85318_, this.f_85315_ + this.f_85316_ + this.f_85317_);
    }
}

