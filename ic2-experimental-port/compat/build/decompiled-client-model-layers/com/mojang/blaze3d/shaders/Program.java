/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.StringUtils
 */
package com.mojang.blaze3d.shaders;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.shaders.Shader;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class Program {
    private static final int f_166598_ = 32768;
    private final Type f_85535_;
    private final String f_85536_;
    private int f_85537_;

    protected Program(Type p_85540_, int p_85541_, String p_85542_) {
        this.f_85535_ = p_85540_;
        this.f_85537_ = p_85541_;
        this.f_85536_ = p_85542_;
    }

    public void m_166610_(Shader p_166611_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84423_(p_166611_.m_108943_(), this.m_166618_());
    }

    public void m_85543_() {
        if (this.f_85537_ == -1) {
            return;
        }
        RenderSystem.m_187554_();
        GlStateManager.m_84421_(this.f_85537_);
        this.f_85537_ = -1;
        this.f_85535_.m_85570_().remove(this.f_85536_);
    }

    public String m_85551_() {
        return this.f_85536_;
    }

    public static Program m_166604_(Type p_166605_, String p_166606_, InputStream p_166607_, String p_166608_, GlslPreprocessor p_166609_) throws IOException {
        RenderSystem.m_187554_();
        int $$5 = Program.m_166612_(p_166605_, p_166606_, p_166607_, p_166608_, p_166609_);
        Program $$6 = new Program(p_166605_, $$5, p_166606_);
        p_166605_.m_85570_().put(p_166606_, $$6);
        return $$6;
    }

    protected static int m_166612_(Type p_166613_, String p_166614_, InputStream p_166615_, String p_166616_, GlslPreprocessor p_166617_) throws IOException {
        String $$5 = IOUtils.toString((InputStream)p_166615_, (Charset)StandardCharsets.UTF_8);
        if ($$5 == null) {
            throw new IOException("Could not load program " + p_166613_.m_85566_());
        }
        int $$6 = GlStateManager.m_84447_(p_166613_.m_85571_());
        GlStateManager.m_157116_($$6, p_166617_.m_166461_($$5));
        GlStateManager.m_84465_($$6);
        if (GlStateManager.m_84449_($$6, 35713) == 0) {
            String $$7 = StringUtils.trim((String)GlStateManager.m_84492_($$6, 32768));
            throw new IOException("Couldn't compile " + p_166613_.m_85566_() + " program (" + p_166616_ + ", " + p_166614_ + ") : " + $$7);
        }
        return $$6;
    }

    protected int m_166618_() {
        return this.f_85537_;
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type VERTEX = new Type("vertex", ".vsh", 35633);
        public static final /* enum */ Type FRAGMENT = new Type("fragment", ".fsh", 35632);
        private final String f_85554_;
        private final String f_85555_;
        private final int f_85556_;
        private final Map<String, Program> f_85557_ = Maps.newHashMap();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_85573_) {
            return Enum.valueOf(Type.class, p_85573_);
        }

        private Type(String p_85563_, String p_85564_, int p_85565_) {
            this.f_85554_ = p_85563_;
            this.f_85555_ = p_85564_;
            this.f_85556_ = p_85565_;
        }

        public String m_85566_() {
            return this.f_85554_;
        }

        public String m_85569_() {
            return this.f_85555_;
        }

        int m_85571_() {
            return this.f_85556_;
        }

        public Map<String, Program> m_85570_() {
            return this.f_85557_;
        }

        private static /* synthetic */ Type[] m_166619_() {
            return new Type[]{VERTEX, FRAGMENT};
        }

        static {
            $VALUES = Type.m_166619_();
        }
    }
}

