/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.wiring.tileentity;

import ic2.core.block.wiring.tileentity.TileEntityChargepadBlock;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityChargepadBatBox
extends TileEntityChargepadBlock {
    public TileEntityChargepadBatBox(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityChargepadBlock>)Ic2BlockEntities.BATBOX_CHARGEPAD, blockPos, blockState, 1, 32, 40000);
    }

    @Override
    protected void getItems(Player player) {
        if (player != null) {
            for (ItemStack itemStack : player.m_150109_().f_35975_) {
                if (itemStack == null) continue;
                this.chargeItem(itemStack, 32);
            }
            for (ItemStack itemStack : player.m_150109_().f_35974_) {
                if (itemStack == null) continue;
                this.chargeItem(itemStack, 32);
            }
        }
    }
}

