/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WoodButtonBlock
extends ButtonBlock {
    protected WoodButtonBlock(BlockBehaviour.Properties p_58284_) {
        super(true, p_58284_);
    }

    @Override
    protected SoundEvent m_5722_(boolean p_58286_) {
        return p_58286_ ? SoundEvents.f_12632_ : SoundEvents.f_12631_;
    }
}

