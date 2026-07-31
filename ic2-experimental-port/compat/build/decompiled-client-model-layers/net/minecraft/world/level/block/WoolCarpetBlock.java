/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WoolCarpetBlock
extends CarpetBlock {
    private final DyeColor f_58288_;

    protected WoolCarpetBlock(DyeColor p_58291_, BlockBehaviour.Properties p_58292_) {
        super(p_58292_);
        this.f_58288_ = p_58291_;
    }

    public DyeColor m_58309_() {
        return this.f_58288_;
    }
}

