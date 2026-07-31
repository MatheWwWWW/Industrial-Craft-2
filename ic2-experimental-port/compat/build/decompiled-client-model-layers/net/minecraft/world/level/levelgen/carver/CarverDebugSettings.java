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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CarverDebugSettings {
    public static final CarverDebugSettings f_159114_ = new CarverDebugSettings(false, Blocks.f_50308_.m_49966_(), Blocks.f_152482_.m_49966_(), Blocks.f_50148_.m_49966_(), Blocks.f_50058_.m_49966_());
    public static final Codec<CarverDebugSettings> f_159115_ = RecordCodecBuilder.create(p_159135_ -> p_159135_.group((App)Codec.BOOL.optionalFieldOf("debug_mode", (Object)false).forGetter(CarverDebugSettings::m_159128_), (App)BlockState.f_61039_.optionalFieldOf("air_state", (Object)f_159114_.m_159145_()).forGetter(CarverDebugSettings::m_159145_), (App)BlockState.f_61039_.optionalFieldOf("water_state", (Object)f_159114_.m_159145_()).forGetter(CarverDebugSettings::m_159146_), (App)BlockState.f_61039_.optionalFieldOf("lava_state", (Object)f_159114_.m_159145_()).forGetter(CarverDebugSettings::m_159147_), (App)BlockState.f_61039_.optionalFieldOf("barrier_state", (Object)f_159114_.m_159145_()).forGetter(CarverDebugSettings::m_159148_)).apply((Applicative)p_159135_, CarverDebugSettings::new));
    private boolean f_159116_;
    private final BlockState f_159117_;
    private final BlockState f_159118_;
    private final BlockState f_159119_;
    private final BlockState f_159120_;

    public static CarverDebugSettings m_159139_(boolean p_159140_, BlockState p_159141_, BlockState p_159142_, BlockState p_159143_, BlockState p_159144_) {
        return new CarverDebugSettings(p_159140_, p_159141_, p_159142_, p_159143_, p_159144_);
    }

    public static CarverDebugSettings m_159129_(BlockState p_159130_, BlockState p_159131_, BlockState p_159132_, BlockState p_159133_) {
        return new CarverDebugSettings(false, p_159130_, p_159131_, p_159132_, p_159133_);
    }

    public static CarverDebugSettings m_159136_(boolean p_159137_, BlockState p_159138_) {
        return new CarverDebugSettings(p_159137_, p_159138_, f_159114_.m_159146_(), f_159114_.m_159147_(), f_159114_.m_159148_());
    }

    private CarverDebugSettings(boolean p_159123_, BlockState p_159124_, BlockState p_159125_, BlockState p_159126_, BlockState p_159127_) {
        this.f_159116_ = p_159123_;
        this.f_159117_ = p_159124_;
        this.f_159118_ = p_159125_;
        this.f_159119_ = p_159126_;
        this.f_159120_ = p_159127_;
    }

    public boolean m_159128_() {
        return this.f_159116_;
    }

    public BlockState m_159145_() {
        return this.f_159117_;
    }

    public BlockState m_159146_() {
        return this.f_159118_;
    }

    public BlockState m_159147_() {
        return this.f_159119_;
    }

    public BlockState m_159148_() {
        return this.f_159120_;
    }
}

