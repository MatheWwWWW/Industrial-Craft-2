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
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class DustParticleOptions
extends DustParticleOptionsBase {
    public static final Vector3f f_175788_ = new Vector3f(Vec3.m_82501_(0xFF0000));
    public static final DustParticleOptions f_123656_ = new DustParticleOptions(f_175788_, 1.0f);
    public static final Codec<DustParticleOptions> f_123657_ = RecordCodecBuilder.create(p_175793_ -> p_175793_.group((App)Vector3f.f_176762_.fieldOf("color").forGetter(p_175797_ -> p_175797_.f_175800_), (App)Codec.FLOAT.fieldOf("scale").forGetter(p_175795_ -> Float.valueOf(p_175795_.f_175801_))).apply((Applicative)p_175793_, DustParticleOptions::new));
    public static final ParticleOptions.Deserializer<DustParticleOptions> f_123658_ = new ParticleOptions.Deserializer<DustParticleOptions>(){

        @Override
        public DustParticleOptions m_5739_(ParticleType<DustParticleOptions> p_123689_, StringReader p_123690_) throws CommandSyntaxException {
            Vector3f $$2 = DustParticleOptionsBase.m_175806_(p_123690_);
            p_123690_.expect(' ');
            float $$3 = p_123690_.readFloat();
            return new DustParticleOptions($$2, $$3);
        }

        @Override
        public DustParticleOptions m_6507_(ParticleType<DustParticleOptions> p_123692_, FriendlyByteBuf p_123693_) {
            return new DustParticleOptions(DustParticleOptionsBase.m_175810_(p_123693_), p_123693_.readFloat());
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

    public DustParticleOptions(Vector3f p_175790_, float p_175791_) {
        super(p_175790_, p_175791_);
    }

    public ParticleType<DustParticleOptions> m_6012_() {
        return ParticleTypes.f_123805_;
    }
}

