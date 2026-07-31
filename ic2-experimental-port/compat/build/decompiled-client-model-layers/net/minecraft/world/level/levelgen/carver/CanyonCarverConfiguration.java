/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.carver;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CanyonCarverConfiguration
extends CarverConfiguration {
    public static final Codec<CanyonCarverConfiguration> f_158966_ = RecordCodecBuilder.create(p_158984_ -> p_158984_.group((App)CarverConfiguration.f_159087_.forGetter(p_158990_ -> p_158990_), (App)FloatProvider.f_146502_.fieldOf("vertical_rotation").forGetter(p_158988_ -> p_158988_.f_158967_), (App)CanyonShapeConfiguration.f_158991_.fieldOf("shape").forGetter(p_158986_ -> p_158986_.f_158968_)).apply((Applicative)p_158984_, CanyonCarverConfiguration::new));
    public final FloatProvider f_158967_;
    public final CanyonShapeConfiguration f_158968_;

    public CanyonCarverConfiguration(float p_224788_, HeightProvider p_224789_, FloatProvider p_224790_, VerticalAnchor p_224791_, CarverDebugSettings p_224792_, HolderSet<Block> p_224793_, FloatProvider p_224794_, CanyonShapeConfiguration p_224795_) {
        super(p_224788_, p_224789_, p_224790_, p_224791_, p_224792_, p_224793_);
        this.f_158967_ = p_224794_;
        this.f_158968_ = p_224795_;
    }

    public CanyonCarverConfiguration(CarverConfiguration p_158980_, FloatProvider p_158981_, CanyonShapeConfiguration p_158982_) {
        this(p_158980_.f_67859_, p_158980_.f_159088_, p_158980_.f_159089_, p_158980_.f_159090_, p_158980_.f_159092_, p_158980_.f_224830_, p_158981_, p_158982_);
    }

    public static class CanyonShapeConfiguration {
        public static final Codec<CanyonShapeConfiguration> f_158991_ = RecordCodecBuilder.create(p_159007_ -> p_159007_.group((App)FloatProvider.f_146502_.fieldOf("distance_factor").forGetter(p_159019_ -> p_159019_.f_158992_), (App)FloatProvider.f_146502_.fieldOf("thickness").forGetter(p_159017_ -> p_159017_.f_158993_), (App)ExtraCodecs.f_144628_.fieldOf("width_smoothness").forGetter(p_159015_ -> p_159015_.f_158994_), (App)FloatProvider.f_146502_.fieldOf("horizontal_radius_factor").forGetter(p_159013_ -> p_159013_.f_158995_), (App)Codec.FLOAT.fieldOf("vertical_radius_default_factor").forGetter(p_159011_ -> Float.valueOf(p_159011_.f_158996_)), (App)Codec.FLOAT.fieldOf("vertical_radius_center_factor").forGetter(p_159009_ -> Float.valueOf(p_159009_.f_158997_))).apply((Applicative)p_159007_, CanyonShapeConfiguration::new));
        public final FloatProvider f_158992_;
        public final FloatProvider f_158993_;
        public final int f_158994_;
        public final FloatProvider f_158995_;
        public final float f_158996_;
        public final float f_158997_;

        public CanyonShapeConfiguration(FloatProvider p_159000_, FloatProvider p_159001_, int p_159002_, FloatProvider p_159003_, float p_159004_, float p_159005_) {
            this.f_158994_ = p_159002_;
            this.f_158995_ = p_159003_;
            this.f_158996_ = p_159004_;
            this.f_158997_ = p_159005_;
            this.f_158992_ = p_159000_;
            this.f_158993_ = p_159001_;
        }
    }
}

