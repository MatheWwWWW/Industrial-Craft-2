/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.biome;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;

public class BiomeSpecialEffects {
    public static final Codec<BiomeSpecialEffects> f_47926_ = RecordCodecBuilder.create(p_47971_ -> p_47971_.group((App)Codec.INT.fieldOf("fog_color").forGetter(p_151782_ -> p_151782_.f_47927_), (App)Codec.INT.fieldOf("water_color").forGetter(p_151780_ -> p_151780_.f_47928_), (App)Codec.INT.fieldOf("water_fog_color").forGetter(p_151778_ -> p_151778_.f_47929_), (App)Codec.INT.fieldOf("sky_color").forGetter(p_151776_ -> p_151776_.f_47930_), (App)Codec.INT.optionalFieldOf("foliage_color").forGetter(p_151774_ -> p_151774_.f_47931_), (App)Codec.INT.optionalFieldOf("grass_color").forGetter(p_151772_ -> p_151772_.f_47932_), (App)GrassColorModifier.f_48050_.optionalFieldOf("grass_color_modifier", (Object)GrassColorModifier.NONE).forGetter(p_151770_ -> p_151770_.f_47933_), (App)AmbientParticleSettings.f_47412_.optionalFieldOf("particle").forGetter(p_151768_ -> p_151768_.f_47934_), (App)SoundEvent.f_11655_.optionalFieldOf("ambient_sound").forGetter(p_151766_ -> p_151766_.f_47935_), (App)AmbientMoodSettings.f_47386_.optionalFieldOf("mood_sound").forGetter(p_151764_ -> p_151764_.f_47936_), (App)AmbientAdditionsSettings.f_47371_.optionalFieldOf("additions_sound").forGetter(p_151762_ -> p_151762_.f_47937_), (App)Music.f_11620_.optionalFieldOf("music").forGetter(p_151760_ -> p_151760_.f_47938_)).apply((Applicative)p_47971_, BiomeSpecialEffects::new));
    private final int f_47927_;
    private final int f_47928_;
    private final int f_47929_;
    private final int f_47930_;
    private final Optional<Integer> f_47931_;
    private final Optional<Integer> f_47932_;
    private final GrassColorModifier f_47933_;
    private final Optional<AmbientParticleSettings> f_47934_;
    private final Optional<SoundEvent> f_47935_;
    private final Optional<AmbientMoodSettings> f_47936_;
    private final Optional<AmbientAdditionsSettings> f_47937_;
    private final Optional<Music> f_47938_;

    BiomeSpecialEffects(int p_47941_, int p_47942_, int p_47943_, int p_47944_, Optional<Integer> p_47945_, Optional<Integer> p_47946_, GrassColorModifier p_47947_, Optional<AmbientParticleSettings> p_47948_, Optional<SoundEvent> p_47949_, Optional<AmbientMoodSettings> p_47950_, Optional<AmbientAdditionsSettings> p_47951_, Optional<Music> p_47952_) {
        this.f_47927_ = p_47941_;
        this.f_47928_ = p_47942_;
        this.f_47929_ = p_47943_;
        this.f_47930_ = p_47944_;
        this.f_47931_ = p_47945_;
        this.f_47932_ = p_47946_;
        this.f_47933_ = p_47947_;
        this.f_47934_ = p_47948_;
        this.f_47935_ = p_47949_;
        this.f_47936_ = p_47950_;
        this.f_47937_ = p_47951_;
        this.f_47938_ = p_47952_;
    }

    public int m_47967_() {
        return this.f_47927_;
    }

    public int m_47972_() {
        return this.f_47928_;
    }

    public int m_47975_() {
        return this.f_47929_;
    }

    public int m_47978_() {
        return this.f_47930_;
    }

    public Optional<Integer> m_47981_() {
        return this.f_47931_;
    }

    public Optional<Integer> m_47984_() {
        return this.f_47932_;
    }

    public GrassColorModifier m_47987_() {
        return this.f_47933_;
    }

    public Optional<AmbientParticleSettings> m_47990_() {
        return this.f_47934_;
    }

    public Optional<SoundEvent> m_47993_() {
        return this.f_47935_;
    }

    public Optional<AmbientMoodSettings> m_47996_() {
        return this.f_47936_;
    }

    public Optional<AmbientAdditionsSettings> m_47999_() {
        return this.f_47937_;
    }

    public Optional<Music> m_48002_() {
        return this.f_47938_;
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static abstract class GrassColorModifier
    extends Enum<GrassColorModifier>
    implements StringRepresentable {
        public static final /* enum */ GrassColorModifier NONE = new GrassColorModifier("none"){

            @Override
            public int m_6583_(double p_48081_, double p_48082_, int p_48083_) {
                return p_48083_;
            }
        };
        public static final /* enum */ GrassColorModifier DARK_FOREST = new GrassColorModifier("dark_forest"){

            @Override
            public int m_6583_(double p_48089_, double p_48090_, int p_48091_) {
                return (p_48091_ & 0xFEFEFE) + 2634762 >> 1;
            }
        };
        public static final /* enum */ GrassColorModifier SWAMP = new GrassColorModifier("swamp"){

            @Override
            public int m_6583_(double p_48097_, double p_48098_, int p_48099_) {
                double $$3 = Biome.f_47433_.m_75449_(p_48097_ * 0.0225, p_48098_ * 0.0225, false);
                if ($$3 < -0.1) {
                    return 5011004;
                }
                return 6975545;
            }
        };
        private final String f_48051_;
        public static final Codec<GrassColorModifier> f_48050_;
        private static final /* synthetic */ GrassColorModifier[] $VALUES;

        public static GrassColorModifier[] values() {
            return (GrassColorModifier[])$VALUES.clone();
        }

        public static GrassColorModifier valueOf(String p_48074_) {
            return Enum.valueOf(GrassColorModifier.class, p_48074_);
        }

        public abstract int m_6583_(double var1, double var3, int var5);

        GrassColorModifier(String p_48058_) {
            this.f_48051_ = p_48058_;
        }

        public String m_48072_() {
            return this.f_48051_;
        }

        @Override
        public String m_7912_() {
            return this.f_48051_;
        }

        private static /* synthetic */ GrassColorModifier[] m_151783_() {
            return new GrassColorModifier[]{NONE, DARK_FOREST, SWAMP};
        }

        static {
            $VALUES = GrassColorModifier.m_151783_();
            f_48050_ = StringRepresentable.m_216439_(GrassColorModifier::values);
        }
    }

    public static class Builder {
        private OptionalInt f_48005_ = OptionalInt.empty();
        private OptionalInt f_48006_ = OptionalInt.empty();
        private OptionalInt f_48007_ = OptionalInt.empty();
        private OptionalInt f_48008_ = OptionalInt.empty();
        private Optional<Integer> f_48009_ = Optional.empty();
        private Optional<Integer> f_48010_ = Optional.empty();
        private GrassColorModifier f_48011_ = GrassColorModifier.NONE;
        private Optional<AmbientParticleSettings> f_48012_ = Optional.empty();
        private Optional<SoundEvent> f_48013_ = Optional.empty();
        private Optional<AmbientMoodSettings> f_48014_ = Optional.empty();
        private Optional<AmbientAdditionsSettings> f_48015_ = Optional.empty();
        private Optional<Music> f_48016_ = Optional.empty();

        public Builder m_48019_(int p_48020_) {
            this.f_48005_ = OptionalInt.of(p_48020_);
            return this;
        }

        public Builder m_48034_(int p_48035_) {
            this.f_48006_ = OptionalInt.of(p_48035_);
            return this;
        }

        public Builder m_48037_(int p_48038_) {
            this.f_48007_ = OptionalInt.of(p_48038_);
            return this;
        }

        public Builder m_48040_(int p_48041_) {
            this.f_48008_ = OptionalInt.of(p_48041_);
            return this;
        }

        public Builder m_48043_(int p_48044_) {
            this.f_48009_ = Optional.of(p_48044_);
            return this;
        }

        public Builder m_48045_(int p_48046_) {
            this.f_48010_ = Optional.of(p_48046_);
            return this;
        }

        public Builder m_48031_(GrassColorModifier p_48032_) {
            this.f_48011_ = p_48032_;
            return this;
        }

        public Builder m_48029_(AmbientParticleSettings p_48030_) {
            this.f_48012_ = Optional.of(p_48030_);
            return this;
        }

        public Builder m_48023_(SoundEvent p_48024_) {
            this.f_48013_ = Optional.of(p_48024_);
            return this;
        }

        public Builder m_48027_(AmbientMoodSettings p_48028_) {
            this.f_48014_ = Optional.of(p_48028_);
            return this;
        }

        public Builder m_48025_(AmbientAdditionsSettings p_48026_) {
            this.f_48015_ = Optional.of(p_48026_);
            return this;
        }

        public Builder m_48021_(@Nullable Music p_48022_) {
            this.f_48016_ = Optional.ofNullable(p_48022_);
            return this;
        }

        public BiomeSpecialEffects m_48018_() {
            return new BiomeSpecialEffects(this.f_48005_.orElseThrow(() -> new IllegalStateException("Missing 'fog' color.")), this.f_48006_.orElseThrow(() -> new IllegalStateException("Missing 'water' color.")), this.f_48007_.orElseThrow(() -> new IllegalStateException("Missing 'water fog' color.")), this.f_48008_.orElseThrow(() -> new IllegalStateException("Missing 'sky' color.")), this.f_48009_, this.f_48010_, this.f_48011_, this.f_48012_, this.f_48013_, this.f_48014_, this.f_48015_, this.f_48016_);
        }
    }
}

