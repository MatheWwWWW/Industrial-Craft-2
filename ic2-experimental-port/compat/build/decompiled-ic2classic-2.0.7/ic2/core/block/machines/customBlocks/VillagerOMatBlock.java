/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.util.FakePlayer
 */
package ic2.core.block.machines.customBlocks;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.block.machines.NoStateMachineBlock;
import ic2.core.block.machines.tiles.hv.VillagerOMatTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.FakePlayer;

public class VillagerOMatBlock
extends NoStateMachineBlock {
    public VillagerOMatBlock(String blockName) {
        super(blockName, IBlockDropProvider.SELF_OR_ADV_MACHINE, ITextureProvider.noStateIC2("machine/hv/villager_o_mat"), BaseMachineBlock.BASE_MACHINE, IC2Tiles.VILLAGER_O_MAT);
    }

    @Override
    protected void setPlaceData(BaseTileEntity tile, BlockState state, LivingEntity placer, ItemStack stack) {
        if (tile instanceof VillagerOMatTileEntity) {
            VillagerOMatTileEntity villager = (VillagerOMatTileEntity)tile;
            if (placer instanceof Player && !(placer instanceof FakePlayer)) {
                villager.owner = placer.m_20148_();
            }
        }
    }
}

