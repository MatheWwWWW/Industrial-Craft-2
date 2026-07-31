/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.ev;

import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.base.tiles.impls.machine.multi.DynamicColossalMachineTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ColossalFurnace
extends DynamicColossalMachineTileEntity {
    public static final ResourceLocation[] TEXTURE = new ResourceLocation[]{new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/ev/colossal_furnace/gui_small.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/ev/colossal_furnace/gui_medium.png"), new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/ev/colossal_furnace/gui_large.png")};
    public static final ITextureProvider PROVIDER = ITextureProvider.toggleIC2("machine/hv/colossal_furnace");

    public ColossalFurnace(BlockPos pos, BlockState state) {
        super(pos, state, 30, 1000, 6000, 2048);
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).furnace;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.COLOSSAL_FURNACE;
    }

    @Override
    protected boolean consumeContainers() {
        return true;
    }

    @Override
    public ITextureProvider getMasterTexture() {
        return PROVIDER;
    }

    @Override
    public ResourceLocation getGuiTexture() {
        switch (this.getSlotsInUse()) {
            case 2: {
                return TEXTURE[0];
            }
            case 4: {
                return TEXTURE[1];
            }
            case 8: {
                return TEXTURE[2];
            }
        }
        return TEXTURE[0];
    }
}

