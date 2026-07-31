/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.base.tiles.impls.machine.single.BasicMachineTileEntity;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricFurnaceTileEntity
extends BasicMachineTileEntity {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_electric_furnace.png");

    public ElectricFurnaceTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3, 3, 130, 32);
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).furnace;
    }

    @Override
    protected ResourceLocation getWorkingSound() {
        return IC2Sounds.ELECTRIC_FURNACE_PROCESSING;
    }

    @Override
    public boolean isHarvestWrenchRequired(Player player) {
        return false;
    }

    @Override
    protected boolean consumeContainers() {
        return true;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.ELECTRIC_FURNACE;
    }
}

