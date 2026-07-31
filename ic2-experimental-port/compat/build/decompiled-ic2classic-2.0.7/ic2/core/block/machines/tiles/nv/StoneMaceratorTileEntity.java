/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.tiles.nv;

import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.base.features.IParticleSpawner;
import ic2.core.block.machines.tiles.nv.StoneBasicMachineTileEntity;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StoneMaceratorTileEntity
extends StoneBasicMachineTileEntity
implements IParticleSpawner {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/stone/gui_stone_macerator.png");

    public StoneMaceratorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 400);
    }

    @Override
    protected ResourceLocation getWorkingSound() {
        return IC2Sounds.MACERATOR_PROCESSING;
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).macerator;
    }

    @Override
    public IMachineRecipeList.RecipeEntry getEntry(ItemStack stack) {
        return this.getRecipeList().getRecipe(stack, true);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.STONE_MACERATOR;
    }

    @Override
    public int getFuel(ItemStack stack) {
        return IC2.RECIPES.get(this.isSimulating()).getFuel((ItemStack)this.inventory.get(0), true) / 2;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void animationTick(RandomSource rand) {
        if (this.isActive()) {
            double x = (float)this.f_58858_.m_123341_() + 1.0f;
            double y = (float)this.f_58858_.m_123342_() + 1.0f;
            double z = (float)this.f_58858_.m_123343_() + 1.0f;
            for (int i = 0; i < 4; ++i) {
                float xOffset = -0.2f - rand.m_188501_() * 0.6f;
                float yOffset = -0.1f + rand.m_188501_() * 0.2f;
                float zOffset = -0.2f - rand.m_188501_() * 0.6f;
                this.f_58857_.m_7106_((ParticleOptions)ParticleTypes.f_123762_, x + (double)xOffset, y + (double)yOffset, z + (double)zOffset, 0.0, 0.0, 0.0);
            }
        }
    }
}

