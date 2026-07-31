/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.color.block.BlockColor
 *  net.minecraft.client.color.item.ItemColor
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering;

import ic2.core.block.base.misc.color.IBlockColorListener;
import ic2.core.block.base.misc.color.IItemColorListener;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class IC2Colors
implements BlockColor,
ItemColor {
    public static final IC2Colors INSTANCE = new IC2Colors();

    public int m_92671_(ItemStack stack, int tintIndex) {
        return stack.m_41720_() instanceof IItemColorListener ? ((IItemColorListener)stack.m_41720_()).getItemColor(stack, tintIndex) : -1;
    }

    public int m_92566_(BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) {
        return state.m_60734_() instanceof IBlockColorListener ? ((IBlockColorListener)state.m_60734_()).getBlockColor(state, world, pos, tintIndex) : -1;
    }
}

