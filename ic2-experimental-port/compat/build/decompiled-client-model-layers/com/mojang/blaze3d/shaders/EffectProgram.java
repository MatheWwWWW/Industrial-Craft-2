/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.shaders;

import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.shaders.Effect;
import com.mojang.blaze3d.shaders.Program;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;

public class EffectProgram
extends Program {
    private static final GlslPreprocessor f_166578_ = new GlslPreprocessor(){

        @Override
        public String m_142138_(boolean p_166595_, String p_166596_) {
            return "#error Import statement not supported";
        }
    };
    private int f_166579_;

    private EffectProgram(Program.Type p_166582_, int p_166583_, String p_166584_) {
        super(p_166582_, p_166583_, p_166584_);
    }

    public void m_166586_(Effect p_166587_) {
        RenderSystem.m_187554_();
        ++this.f_166579_;
        this.m_166610_(p_166587_);
    }

    @Override
    public void m_85543_() {
        RenderSystem.m_187554_();
        --this.f_166579_;
        if (this.f_166579_ <= 0) {
            super.m_85543_();
        }
    }

    public static EffectProgram m_166588_(Program.Type p_166589_, String p_166590_, InputStream p_166591_, String p_166592_) throws IOException {
        RenderSystem.m_187554_();
        int $$4 = EffectProgram.m_166612_(p_166589_, p_166590_, p_166591_, p_166592_, f_166578_);
        EffectProgram $$5 = new EffectProgram(p_166589_, $$4, p_166590_);
        p_166589_.m_85570_().put(p_166590_, $$5);
        return $$5;
    }
}

