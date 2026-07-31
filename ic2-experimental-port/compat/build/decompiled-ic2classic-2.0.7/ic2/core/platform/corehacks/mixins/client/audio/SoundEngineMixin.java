/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.sounds.ChannelAccess
 *  net.minecraft.client.sounds.SoundBufferLibrary
 *  net.minecraft.client.sounds.SoundEngine
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package ic2.core.platform.corehacks.mixins.client.audio;

import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@OnlyIn(value=Dist.CLIENT)
@Mixin(value={SoundEngine.class})
public interface SoundEngineMixin {
    @Accessor(value="channelAccess")
    public ChannelAccess getChannels();

    @Accessor(value="soundBuffers")
    public SoundBufferLibrary getAudioBuffers();
}

