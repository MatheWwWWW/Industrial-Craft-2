/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public abstract class StemGrownBlock
extends Block {
    public StemGrownBlock(BlockBehaviour.Properties p_57058_) {
        super(p_57058_);
    }

    public abstract StemBlock m_7161_();

    public abstract AttachedStemBlock m_7810_();
}

