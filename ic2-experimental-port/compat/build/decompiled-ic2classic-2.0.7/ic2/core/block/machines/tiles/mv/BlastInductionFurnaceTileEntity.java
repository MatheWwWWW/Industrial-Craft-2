/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.mv;

import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.machines.tiles.mv.InductionFurnaceTileEntity;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlastInductionFurnaceTileEntity
extends InductionFurnaceTileEntity {
    public BlastInductionFurnaceTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 29, 2000);
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).blast_furnace;
    }

    @Override
    protected ResourceLocation getWorkingSound() {
        return IC2Sounds.ELECTRIC_FURNACE_PROCESSING;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.BLAST_FURNACE;
    }
}

