/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources.sounds;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;

public class BiomeAmbientSoundsHandler
implements AmbientSoundHandler {
    private static final int f_174920_ = 40;
    private static final float f_174921_ = 0.001f;
    private final LocalPlayer f_119629_;
    private final SoundManager f_119630_;
    private final BiomeManager f_119631_;
    private final RandomSource f_119632_;
    private final Object2ObjectArrayMap<Biome, LoopSoundInstance> f_119633_ = new Object2ObjectArrayMap();
    private Optional<AmbientMoodSettings> f_119634_ = Optional.empty();
    private Optional<AmbientAdditionsSettings> f_119635_ = Optional.empty();
    private float f_119636_;
    @Nullable
    private Biome f_119637_;

    public BiomeAmbientSoundsHandler(LocalPlayer p_119639_, SoundManager p_119640_, BiomeManager p_119641_) {
        this.f_119632_ = p_119639_.f_19853_.m_213780_();
        this.f_119629_ = p_119639_;
        this.f_119630_ = p_119640_;
        this.f_119631_ = p_119641_;
    }

    public float m_119654_() {
        return this.f_119636_;
    }

    @Override
    public void m_7551_() {
        this.f_119633_.values().removeIf(AbstractTickableSoundInstance::m_7801_);
        Biome $$0 = this.f_119631_.m_204206_(this.f_119629_.m_20185_(), this.f_119629_.m_20186_(), this.f_119629_.m_20189_()).m_203334_();
        if ($$0 != this.f_119637_) {
            this.f_119637_ = $$0;
            this.f_119634_ = $$0.m_47564_();
            this.f_119635_ = $$0.m_47565_();
            this.f_119633_.values().forEach(LoopSoundInstance::m_119659_);
            $$0.m_47563_().ifPresent(p_119653_ -> this.f_119633_.compute((Object)$$0, (p_174924_, p_174925_) -> {
                if (p_174925_ == null) {
                    p_174925_ = new LoopSoundInstance((SoundEvent)p_119653_);
                    this.f_119630_.m_120367_((SoundInstance)p_174925_);
                }
                p_174925_.m_119660_();
                return p_174925_;
            }));
        }
        this.f_119635_.ifPresent(p_119648_ -> {
            if (this.f_119632_.m_188500_() < p_119648_.m_47383_()) {
                this.f_119630_.m_120367_(SimpleSoundInstance.m_119759_(p_119648_.m_47378_()));
            }
        });
        this.f_119634_.ifPresent(p_119650_ -> {
            Level $$1 = this.f_119629_.f_19853_;
            int $$2 = p_119650_.m_47406_() * 2 + 1;
            BlockPos $$3 = new BlockPos(this.f_119629_.m_20185_() + (double)this.f_119632_.m_188503_($$2) - (double)p_119650_.m_47406_(), this.f_119629_.m_20188_() + (double)this.f_119632_.m_188503_($$2) - (double)p_119650_.m_47406_(), this.f_119629_.m_20189_() + (double)this.f_119632_.m_188503_($$2) - (double)p_119650_.m_47406_());
            int $$4 = $$1.m_45517_(LightLayer.SKY, $$3);
            this.f_119636_ = $$4 > 0 ? (this.f_119636_ -= (float)$$4 / (float)$$1.m_7469_() * 0.001f) : (this.f_119636_ -= (float)($$1.m_45517_(LightLayer.BLOCK, $$3) - 1) / (float)p_119650_.m_47403_());
            if (this.f_119636_ >= 1.0f) {
                double $$5 = (double)$$3.m_123341_() + 0.5;
                double $$6 = (double)$$3.m_123342_() + 0.5;
                double $$7 = (double)$$3.m_123343_() + 0.5;
                double $$8 = $$5 - this.f_119629_.m_20185_();
                double $$9 = $$6 - this.f_119629_.m_20188_();
                double $$10 = $$7 - this.f_119629_.m_20189_();
                double $$11 = Math.sqrt($$8 * $$8 + $$9 * $$9 + $$10 * $$10);
                double $$12 = $$11 + p_119650_.m_47409_();
                SimpleSoundInstance $$13 = SimpleSoundInstance.m_235127_(p_119650_.m_47398_(), this.f_119632_, this.f_119629_.m_20185_() + $$8 / $$11 * $$12, this.f_119629_.m_20188_() + $$9 / $$11 * $$12, this.f_119629_.m_20189_() + $$10 / $$11 * $$12);
                this.f_119630_.m_120367_($$13);
                this.f_119636_ = 0.0f;
            } else {
                this.f_119636_ = Math.max(this.f_119636_, 0.0f);
            }
        });
    }

    public static class LoopSoundInstance
    extends AbstractTickableSoundInstance {
        private int f_119655_;
        private int f_119656_;

        public LoopSoundInstance(SoundEvent p_119658_) {
            super(p_119658_, SoundSource.AMBIENT, SoundInstance.m_235150_());
            this.f_119578_ = true;
            this.f_119579_ = 0;
            this.f_119573_ = 1.0f;
            this.f_119582_ = true;
        }

        @Override
        public void m_7788_() {
            if (this.f_119656_ < 0) {
                this.m_119609_();
            }
            this.f_119656_ += this.f_119655_;
            this.f_119573_ = Mth.m_14036_((float)this.f_119656_ / 40.0f, 0.0f, 1.0f);
        }

        public void m_119659_() {
            this.f_119656_ = Math.min(this.f_119656_, 40);
            this.f_119655_ = -1;
        }

        public void m_119660_() {
            this.f_119656_ = Math.max(0, this.f_119656_);
            this.f_119655_ = 1;
        }
    }
}

