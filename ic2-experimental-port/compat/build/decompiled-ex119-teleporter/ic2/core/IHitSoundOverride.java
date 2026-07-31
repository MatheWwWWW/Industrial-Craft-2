/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IHitSoundOverride {
    @Environment(value=EnvType.CLIENT)
    public SoundEvent getHitSoundForBlock(LocalPlayer var1, Level var2, BlockPos var3, ItemStack var4);

    @Environment(value=EnvType.CLIENT)
    public SoundEvent getBreakSoundForBlock(LocalPlayer var1, Level var2, BlockPos var3, ItemStack var4);
}

