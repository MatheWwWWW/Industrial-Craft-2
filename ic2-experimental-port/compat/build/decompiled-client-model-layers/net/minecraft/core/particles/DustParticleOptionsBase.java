/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 */
package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

public abstract class DustParticleOptionsBase
implements ParticleOptions {
    public static final float f_175798_ = 0.01f;
    public static final float f_175799_ = 4.0f;
    protected final Vector3f f_175800_;
    protected final float f_175801_;

    public DustParticleOptionsBase(Vector3f p_175803_, float p_175804_) {
        this.f_175800_ = p_175803_;
        this.f_175801_ = Mth.m_14036_(p_175804_, 0.01f, 4.0f);
    }

    public static Vector3f m_175806_(StringReader p_175807_) throws CommandSyntaxException {
        p_175807_.expect(' ');
        float $$1 = p_175807_.readFloat();
        p_175807_.expect(' ');
        float $$2 = p_175807_.readFloat();
        p_175807_.expect(' ');
        float $$3 = p_175807_.readFloat();
        return new Vector3f($$1, $$2, $$3);
    }

    public static Vector3f m_175810_(FriendlyByteBuf p_175811_) {
        return new Vector3f(p_175811_.readFloat(), p_175811_.readFloat(), p_175811_.readFloat());
    }

    @Override
    public void m_7711_(FriendlyByteBuf p_175809_) {
        p_175809_.writeFloat(this.f_175800_.m_122239_());
        p_175809_.writeFloat(this.f_175800_.m_122260_());
        p_175809_.writeFloat(this.f_175800_.m_122269_());
        p_175809_.writeFloat(this.f_175801_);
    }

    @Override
    public String m_5942_() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.f_122829_.m_7981_(this.m_6012_()), Float.valueOf(this.f_175800_.m_122239_()), Float.valueOf(this.f_175800_.m_122260_()), Float.valueOf(this.f_175800_.m_122269_()), Float.valueOf(this.f_175801_));
    }

    public Vector3f m_175812_() {
        return this.f_175800_;
    }

    public float m_175813_() {
        return this.f_175801_;
    }
}

