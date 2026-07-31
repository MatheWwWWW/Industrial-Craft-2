/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.LivingEntity
 */
package ic2.api.sound.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public interface ISwingSoundItem {
    public SoundEvent getSwingSound(LivingEntity var1, InteractionHand var2);
}

