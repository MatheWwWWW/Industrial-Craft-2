/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.sounds;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.audio.OggAudioStream;
import com.mojang.blaze3d.audio.SoundBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.minecraft.Util;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.LoopingAudioStream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class SoundBufferLibrary {
    private final ResourceManager f_120189_;
    private final Map<ResourceLocation, CompletableFuture<SoundBuffer>> f_120190_ = Maps.newHashMap();

    public SoundBufferLibrary(ResourceManager p_120192_) {
        this.f_120189_ = p_120192_;
    }

    public CompletableFuture<SoundBuffer> m_120202_(ResourceLocation p_120203_) {
        return this.f_120190_.computeIfAbsent(p_120203_, p_120208_ -> CompletableFuture.supplyAsync(() -> {
            try (InputStream $$1 = this.f_120189_.m_215595_((ResourceLocation)p_120208_);){
                SoundBuffer soundBuffer;
                try (OggAudioStream $$2 = new OggAudioStream($$1);){
                    ByteBuffer $$3 = $$2.m_83764_();
                    soundBuffer = new SoundBuffer($$3, $$2.m_6206_());
                }
                return soundBuffer;
            }
            catch (IOException $$4) {
                throw new CompletionException($$4);
            }
        }, Util.m_183991_()));
    }

    public CompletableFuture<AudioStream> m_120204_(ResourceLocation p_120205_, boolean p_120206_) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                InputStream $$2 = this.f_120189_.m_215595_(p_120205_);
                return p_120206_ ? new LoopingAudioStream(OggAudioStream::new, $$2) : new OggAudioStream($$2);
            }
            catch (IOException $$3) {
                throw new CompletionException($$3);
            }
        }, Util.m_183991_());
    }

    public void m_120193_() {
        this.f_120190_.values().forEach(p_120201_ -> p_120201_.thenAccept(SoundBuffer::m_83801_));
        this.f_120190_.clear();
    }

    public CompletableFuture<?> m_120198_(Collection<Sound> p_120199_) {
        return CompletableFuture.allOf((CompletableFuture[])p_120199_.stream().map(p_120197_ -> this.m_120202_(p_120197_.m_119790_())).toArray(CompletableFuture[]::new));
    }
}

