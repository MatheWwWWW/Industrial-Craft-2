/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;

public class PlayerWallHeadBlock
extends WallSkullBlock {
    protected PlayerWallHeadBlock(BlockBehaviour.Properties p_55185_) {
        super(SkullBlock.Types.PLAYER, p_55185_);
    }

    @Override
    public void m_6402_(Level p_55187_, BlockPos p_55188_, BlockState p_55189_, @Nullable LivingEntity p_55190_, ItemStack p_55191_) {
        Blocks.f_50316_.m_6402_(p_55187_, p_55188_, p_55189_, p_55190_, p_55191_);
    }

    @Override
    public List<ItemStack> m_7381_(BlockState p_55193_, LootContext.Builder p_55194_) {
        return Blocks.f_50316_.m_7381_(p_55193_, p_55194_);
    }
}

