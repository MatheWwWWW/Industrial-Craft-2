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
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CaveCarverConfiguration
extends CarverConfiguration {
    public static final Codec<CaveCarverConfiguration> f_159154_ = RecordCodecBuilder.create(p_159184_ -> p_159184_.group((App)CarverConfiguration.f_159087_.forGetter(p_159192_ -> p_159192_), (App)FloatProvider.f_146502_.fieldOf("horizontal_radius_multiplier").forGetter(p_159190_ -> p_159190_.f_159155_), (App)FloatProvider.f_146502_.fieldOf("vertical_radius_multiplier").forGetter(p_159188_ -> p_159188_.f_159156_), (App)FloatProvider.m_146505_(-1.0f, 1.0f).fieldOf("floor_level").forGetter(p_159186_ -> p_159186_.f_159157_)).apply((Applicative)p_159184_, CaveCarverConfiguration::new));
    public final FloatProvider f_159155_;
    public final FloatProvider f_159156_;
    final FloatProvider f_159157_;

    public CaveCarverConfiguration(float p_224853_, HeightProvider p_224854_, FloatProvider p_224855_, VerticalAnchor p_224856_, CarverDebugSettings p_224857_, HolderSet<Block> p_224858_, FloatProvider p_224859_, FloatProvider p_224860_, FloatProvider p_224861_) {
        super(p_224853_, p_224854_, p_224855_, p_224856_, p_224857_, p_224858_);
        this.f_159155_ = p_224859_;
        this.f_159156_ = p_224860_;
        this.f_159157_ = p_224861_;
    }

    public CaveCarverConfiguration(float p_224863_, HeightProvider p_224864_, FloatProvider p_224865_, VerticalAnchor p_224866_, HolderSet<Block> p_224867_, FloatProvider p_224868_, FloatProvider p_224869_, FloatProvider p_224870_) {
        this(p_224863_, p_224864_, p_224865_, p_224866_, CarverDebugSettings.f_159114_, p_224867_, p_224868_, p_224869_, p_224870_);
    }

    public CaveCarverConfiguration(CarverConfiguration p_159179_, FloatProvider p_159180_, FloatProvider p_159181_, FloatProvider p_159182_) {
        this(p_159179_.f_67859_, p_159179_.f_159088_, p_159179_.f_159089_, p_159179_.f_159090_, p_159179_.f_159092_, p_159179_.f_224830_, p_159180_, p_159181_, p_159182_);
    }
}

