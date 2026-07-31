/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MelonBlock
extends StemGrownBlock {
    protected MelonBlock(BlockBehaviour.Properties p_54829_) {
        super(p_54829_);
    }

    @Override
    public StemBlock m_7161_() {
        return (StemBlock)Blocks.f_50190_;
    }

    @Override
    public AttachedStemBlock m_7810_() {
        return (AttachedStemBlock)Blocks.f_50188_;
    }
}

