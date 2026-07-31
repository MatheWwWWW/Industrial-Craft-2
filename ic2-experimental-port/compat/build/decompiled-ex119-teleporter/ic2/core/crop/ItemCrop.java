/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.crop;

import ic2.api.crops.CropSoilType;
import ic2.api.item.IBoxable;
import ic2.core.ref.Ic2Blocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class ItemCrop
extends BlockItem
implements IBoxable {
    public ItemCrop(Item.Properties properties) {
        super(Ic2Blocks.CROP_STICK, properties);
    }

    protected boolean m_40610_(BlockPlaceContext blockPlaceContext, BlockState blockState) {
        return CropSoilType.contains(blockPlaceContext.m_43725_().m_8055_(blockPlaceContext.m_8083_().m_7495_()).m_60734_()) && super.m_40610_(blockPlaceContext, blockState);
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemStack) {
        return true;
    }
}

