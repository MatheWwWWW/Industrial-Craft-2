/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class DustColorTransitionOptions
extends DustParticleOptionsBase {
    public static final Vector3f f_175751_ = new Vector3f(Vec3.m_82501_(3790560));
    public static final DustColorTransitionOptions f_175752_ = new DustColorTransitionOptions(f_175751_, DustParticleOptions.f_175788_, 1.0f);
    public static final Codec<DustColorTransitionOptions> f_175753_ = RecordCodecBuilder.create(p_175763_ -> p_175763_.group((App)Vector3f.f_176762_.fieldOf("fromColor").forGetter(p_175773_ -> p_175773_.f_175800_), (App)Vector3f.f_176762_.fieldOf("toColor").forGetter(p_175770_ -> p_175770_.f_175755_), (App)Codec.FLOAT.fieldOf("scale").forGetter(p_175765_ -> Float.valueOf(p_175765_.f_175801_))).apply((Applicative)p_175763_, DustColorTransitionOptions::new));
    public static final ParticleOptions.Deserializer<DustColorTransitionOptions> f_175754_ = new ParticleOptions.Deserializer<DustColorTransitionOptions>(){

        @Override
        public DustColorTransitionOptions m_5739_(ParticleType<DustColorTransitionOptions> p_175777_, StringReader p_175778_) throws CommandSyntaxException {
            Vector3f $$2 = DustParticleOptionsBase.m_175806_(p_175778_);
            p_175778_.expect(' ');
            float $$3 = p_175778_.readFloat();
            Vector3f $$4 = DustParticleOptionsBase.m_175806_(p_175778_);
            return new DustColorTransitionOptions($$2, $$4, $$3);
        }

        @Override
        public DustColorTransitionOptions m_6507_(ParticleType<DustColorTransitionOptions> p_175780_, FriendlyByteBuf p_175781_) {
            Vector3f $$2 = DustParticleOptionsBase.m_175810_(p_175781_);
            float $$3 = p_175781_.readFloat();
            Vector3f $$4 = DustParticleOptionsBase.m_175810_(p_175781_);
            return new DustColorTransitionOptions($$2, $$4, $$3);
        }

        @Override
        public /* synthetic */ ParticleOptions m_6507_(ParticleType particleType, FriendlyByteBuf friendlyByteBuf) {
            return this.m_6507_(particleType, friendlyByteBuf);
        }

        @Override
        public /* synthetic */ ParticleOptions m_5739_(ParticleType particleType, StringReader stringReader) throws CommandSyntaxException {
            return this.m_5739_(particleType, stringReader);
        }
    };
    private final Vector3f f_175755_;

    public DustColorTransitionOptions(Vector3f p_175758_, Vector3f p_175759_, float p_175760_) {
        super(p_175758_, p_175760_);
        this.f_175755_ = p_175759_;
    }

    public Vector3f m_175771_() {
        return this.f_175800_;
    }

    public Vector3f m_175774_() {
        return this.f_175755_;
    }

    @Override
    public void m_7711_(FriendlyByteBuf p_175767_) {
        super.m_7711_(p_175767_);
        p_175767_.writeFloat(this.f_175755_.m_122239_());
        p_175767_.writeFloat(this.f_175755_.m_122260_());
        p_175767_.writeFloat(this.f_175755_.m_122269_());
    }

    @Override
    public String m_5942_() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %.2f %.2f", Registry.f_122829_.m_7981_(this.m_6012_()), Float.valueOf(this.f_175800_.m_122239_()), Float.valueOf(this.f_175800_.m_122260_()), Float.valueOf(this.f_175800_.m_122269_()), Float.valueOf(this.f_175801_), Float.valueOf(this.f_175755_.m_122239_()), Float.valueOf(this.f_175755_.m_122260_()), Float.valueOf(this.f_175755_.m_122269_()));
    }

    public ParticleType<DustColorTransitionOptions> m_6012_() {
        return ParticleTypes.f_175836_;
    }
}

