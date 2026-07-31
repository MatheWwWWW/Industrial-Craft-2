/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class StainedGlassBlock
extends AbstractGlassBlock
implements BeaconBeamBlock {
    private final DyeColor f_56831_;

    public StainedGlassBlock(DyeColor p_56833_, BlockBehaviour.Properties p_56834_) {
        super(p_56834_);
        this.f_56831_ = p_56833_;
    }

    @Override
    public DyeColor m_7988_() {
        return this.f_56831_;
    }
}

