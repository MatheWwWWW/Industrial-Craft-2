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
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.phys.Vec3;

public class VibrationParticleOption
implements ParticleOptions {
    public static final Codec<VibrationParticleOption> f_175842_ = RecordCodecBuilder.create(p_235978_ -> p_235978_.group((App)PositionSource.f_157868_.fieldOf("destination").forGetter(p_235982_ -> p_235982_.f_235972_), (App)Codec.INT.fieldOf("arrival_in_ticks").forGetter(p_235980_ -> p_235980_.f_235973_)).apply((Applicative)p_235978_, VibrationParticleOption::new));
    public static final ParticleOptions.Deserializer<VibrationParticleOption> f_175843_ = new ParticleOptions.Deserializer<VibrationParticleOption>(){

        @Override
        public VibrationParticleOption m_5739_(ParticleType<VibrationParticleOption> p_175859_, StringReader p_175860_) throws CommandSyntaxException {
            p_175860_.expect(' ');
            float $$2 = (float)p_175860_.readDouble();
            p_175860_.expect(' ');
            float $$3 = (float)p_175860_.readDouble();
            p_175860_.expect(' ');
            float $$4 = (float)p_175860_.readDouble();
            p_175860_.expect(' ');
            int $$5 = p_175860_.readInt();
            BlockPos $$6 = new BlockPos($$2, $$3, $$4);
            return new VibrationParticleOption(new BlockPositionSource($$6), $$5);
        }

        @Override
        public VibrationParticleOption m_6507_(ParticleType<VibrationParticleOption> p_175862_, FriendlyByteBuf p_175863_) {
            PositionSource $$2 = PositionSourceType.m_157885_(p_175863_);
            int $$3 = p_175863_.m_130242_();
            return new VibrationParticleOption($$2, $$3);
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
    private final PositionSource f_235972_;
    private final int f_235973_;

    public VibrationParticleOption(PositionSource p_235975_, int p_235976_) {
        this.f_235972_ = p_235975_;
        this.f_235973_ = p_235976_;
    }

    @Override
    public void m_7711_(FriendlyByteBuf p_175854_) {
        PositionSourceType.m_157874_(this.f_235972_, p_175854_);
        p_175854_.m_130130_(this.f_235973_);
    }

    @Override
    public String m_5942_() {
        Vec3 $$0 = this.f_235972_.m_142502_(null).get();
        double $$1 = $$0.m_7096_();
        double $$2 = $$0.m_7098_();
        double $$3 = $$0.m_7094_();
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %d", Registry.f_122829_.m_7981_(this.m_6012_()), $$1, $$2, $$3, this.f_235973_);
    }

    public ParticleType<VibrationParticleOption> m_6012_() {
        return ParticleTypes.f_175820_;
    }

    public PositionSource m_235983_() {
        return this.f_235972_;
    }

    public int m_235984_() {
        return this.f_235973_;
    }
}

