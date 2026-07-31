/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
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
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IronFurnaceTileEntity
extends StoneBasicMachineTileEntity
implements IParticleSpawner {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/stone/gui_iron_furnace.png");

    public IronFurnaceTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 160);
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).furnace;
    }

    @Override
    protected ResourceLocation getWorkingSound() {
        return IC2Sounds.IRON_FURNACE_PROCESSING;
    }

    @Override
    protected boolean consumeContainers() {
        return true;
    }

    @Override
    public IMachineRecipeList.RecipeEntry getEntry(ItemStack stack) {
        return this.getRecipeList().getRecipe(stack, true);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.IRON_FURNACE;
    }

    @Override
    public int getFuel(ItemStack stack) {
        return IC2.RECIPES.get(this.isSimulating()).getFuel((ItemStack)this.inventory.get(0), true);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void animationTick(RandomSource rand) {
        if (this.isActive()) {
            Direction direction = this.getFacing();
            Direction.Axis axis = direction.m_122434_();
            double d0 = (double)this.f_58858_.m_123341_() + 0.5 + (axis == Direction.Axis.X ? (double)direction.m_122429_() * 0.52 : rand.m_188500_() * 0.6 - 0.3);
            double d1 = (double)this.f_58858_.m_123342_() + rand.m_188500_() * 6.0 / 16.0;
            double d2 = (double)this.f_58858_.m_123343_() + 0.5 + (axis == Direction.Axis.Z ? (double)direction.m_122431_() * 0.52 : rand.m_188500_() * 0.6 - 0.3);
            if (rand.m_188500_() < 0.1) {
                this.f_58857_.m_7785_(d0, d1, d2, SoundEvents.f_11907_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
            }
            this.f_58857_.m_7106_((ParticleOptions)ParticleTypes.f_123762_, d0, d1, d2, 0.0, 0.0, 0.0);
            this.f_58857_.m_7106_((ParticleOptions)ParticleTypes.f_123744_, d0, d1, d2, 0.0, 0.0, 0.0);
        }
    }
}

