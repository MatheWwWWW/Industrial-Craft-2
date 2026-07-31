/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.upgrades;

import ic2.core.IC2;
import ic2.core.item.base.IC2SimpleItem;
import ic2.core.item.upgrades.swaps.EmptySwapper;
import ic2.core.item.upgrades.swaps.ISwapper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SwapperUpgrade
extends IC2SimpleItem {
    Block from;
    Block to;
    ISwapper swapper;

    public SwapperUpgrade(String itemName, String textureFolder, String textureName, Block from, Block to) {
        this(itemName, textureFolder, textureName, from, to, EmptySwapper.INSTANCE);
    }

    public SwapperUpgrade(String itemName, String textureFolder, String textureName, Block from, Block to, ISwapper swapper) {
        super(itemName, textureFolder, textureName);
        this.from = from;
        this.to = to;
        this.swapper = swapper;
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        BlockPos pos;
        Level world = context.m_43725_();
        BlockState state = world.m_8055_(pos = context.m_8083_());
        if (state.m_60734_() == this.from) {
            BlockEntity before = world.m_7702_(pos);
            if (world.m_46597_(pos, this.swapper.transfer(state, this.to))) {
                BlockEntity after = world.m_7702_(pos);
                this.swapper.transfer(before, after);
                IC2.PLATFORM.markBlockForRenderUpdate(pos);
                if (context.m_43723_() == null || !context.m_43723_().m_7500_()) {
                    stack.m_41774_(1);
                }
                return InteractionResult.m_19078_((boolean)world.f_46443_);
            }
        }
        return InteractionResult.PASS;
    }
}

