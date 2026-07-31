/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  org.apache.commons.lang3.mutable.Mutable
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.fluid;

import ic2.core.IC2;
import ic2.core.fluid.EnvFluidHandler;
import ic2.core.fluid.Ic2FluidBlock;
import ic2.core.fluid.Ic2FluidItem;
import ic2.core.fluid.Ic2FluidStack;
import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import org.apache.commons.lang3.mutable.Mutable;
import org.jetbrains.annotations.Nullable;

public final class FluidHandler {
    static final EnvFluidHandler ENV_HANDLER = IC2.envProxy.createFluidStackHandler();

    public static EnvFluidHandler.FluidRefs createFluid(ResourceLocation resourceLocation, Material material, int n, int n2, int n3, int n4, boolean bl, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3, int n5) {
        return ENV_HANDLER.createFluid(resourceLocation, material, n, n2, n3, n4, resourceLocation2, resourceLocation3, n5);
    }

    public static int getDensity(Fluid fluid) {
        return ENV_HANDLER.getDensity(fluid);
    }

    public static int getTemperature(Fluid fluid) {
        return ENV_HANDLER.getTemperature(fluid);
    }

    public static boolean isGaseous(Fluid fluid) {
        return ENV_HANDLER.isGaseous(fluid);
    }

    public static ResourceLocation getStillSpriteId(Fluid fluid) {
        return ENV_HANDLER.getStillSpriteId(fluid);
    }

    public static ResourceLocation getFlowingSpriteId(Fluid fluid) {
        return ENV_HANDLER.getFlowingSpriteId(fluid);
    }

    public static int getColor(Fluid fluid) {
        return ENV_HANDLER.getColor(fluid);
    }

    public static Ic2FluidStack drainMb(ItemStack itemStack, int n, boolean bl, @Nullable Mutable<ItemStack> mutable) {
        Item item = itemStack.m_41720_();
        if (item instanceof Ic2FluidItem) {
            return ((Ic2FluidItem)item).drainMb(itemStack, n, bl, mutable);
        }
        return ENV_HANDLER.drainMb(itemStack, n, bl, mutable);
    }

    public static int drainMb(ItemStack itemStack, Ic2FluidStack ic2FluidStack, boolean bl, Mutable<ItemStack> mutable) {
        Item item = itemStack.m_41720_();
        if (item instanceof Ic2FluidItem) {
            return ((Ic2FluidItem)item).drainMb(itemStack, ic2FluidStack, bl, mutable);
        }
        return ENV_HANDLER.drainMb(itemStack, ic2FluidStack, bl, mutable);
    }

    public static int fillMb(ItemStack itemStack, Ic2FluidStack ic2FluidStack, boolean bl, Mutable<ItemStack> mutable) {
        Item item = itemStack.m_41720_();
        if (item instanceof Ic2FluidItem) {
            return ((Ic2FluidItem)item).fillMb(itemStack, ic2FluidStack, bl, mutable);
        }
        return ENV_HANDLER.fillMb(itemStack, ic2FluidStack, bl, mutable);
    }

    public static boolean isFluidBlock(BlockState blockState, Level level, BlockPos blockPos, Direction direction) {
        Block block = blockState.m_60734_();
        if (block instanceof Ic2FluidBlock) {
            Ic2FluidBlock ic2FluidBlock = (Ic2FluidBlock)block;
            return ic2FluidBlock.isFluidBlock(blockState, level, blockPos, null);
        }
        return ENV_HANDLER.isFluidBlock(blockState, level, blockPos, null, direction);
    }

    public static boolean isFluidBlock(BlockEntity blockEntity, Direction direction) {
        BlockState blockState = blockEntity.m_58900_();
        return FluidHandler.isFluidBlock(blockState, blockEntity, direction);
    }

    public static boolean isFluidBlock(BlockState blockState, BlockEntity blockEntity, Direction direction) {
        Block block = blockState.m_60734_();
        if (block instanceof Ic2FluidBlock) {
            Ic2FluidBlock ic2FluidBlock = (Ic2FluidBlock)block;
            return ic2FluidBlock.isFluidBlock(null, null, null, blockEntity);
        }
        return ENV_HANDLER.isFluidBlock(blockState, null, null, blockEntity, direction);
    }

    public static Ic2FluidStack drainMb(BlockState blockState, Level level, BlockPos blockPos, Direction direction, int n, boolean bl) {
        Block block = blockState.m_60734_();
        if (block instanceof Ic2FluidBlock) {
            Ic2FluidBlock ic2FluidBlock = (Ic2FluidBlock)block;
            return ic2FluidBlock.drainMb(blockState, level, blockPos, null, direction, n, bl);
        }
        return ENV_HANDLER.drainMb(blockState, level, blockPos, null, direction, n, bl);
    }

    public static Ic2FluidStack drainMb(BlockEntity blockEntity, Direction direction, int n, boolean bl) {
        BlockState blockState = blockEntity.m_58900_();
        return FluidHandler.drainMb(blockState, blockEntity, direction, n, bl);
    }

    public static Ic2FluidStack drainMb(BlockState blockState, BlockEntity blockEntity, Direction direction, int n, boolean bl) {
        Block block = blockState.m_60734_();
        if (block instanceof Ic2FluidBlock) {
            Ic2FluidBlock ic2FluidBlock = (Ic2FluidBlock)block;
            return ic2FluidBlock.drainMb(null, null, null, blockEntity, direction, n, bl);
        }
        return ENV_HANDLER.drainMb(blockState, null, null, blockEntity, direction, n, bl);
    }

    public static int drainMb(BlockState blockState, Level level, BlockPos blockPos, Direction direction, Ic2FluidStack ic2FluidStack, boolean bl) {
        Block block = blockState.m_60734_();
        if (block instanceof Ic2FluidBlock) {
            Ic2FluidBlock ic2FluidBlock = (Ic2FluidBlock)block;
            return ic2FluidBlock.drainMb(blockState, level, blockPos, null, direction, ic2FluidStack, bl);
        }
        return ENV_HANDLER.drainMb(blockState, level, blockPos, null, direction, ic2FluidStack, bl);
    }

    public static int drainMb(BlockEntity blockEntity, Direction direction, Ic2FluidStack ic2FluidStack, boolean bl) {
        BlockState blockState = blockEntity.m_58900_();
        Block block = blockState.m_60734_();
        if (block instanceof Ic2FluidBlock) {
            Ic2FluidBlock ic2FluidBlock = (Ic2FluidBlock)block;
            return ic2FluidBlock.drainMb(null, null, null, blockEntity, direction, ic2FluidStack, bl);
        }
        return ENV_HANDLER.drainMb(blockState, null, null, blockEntity, direction, ic2FluidStack, bl);
    }

    public static int fillMb(BlockState blockState, Level level, BlockPos blockPos, Direction direction, Ic2FluidStack ic2FluidStack, boolean bl) {
        Block block = blockState.m_60734_();
        if (block instanceof Ic2FluidBlock) {
            Ic2FluidBlock ic2FluidBlock = (Ic2FluidBlock)block;
            return ic2FluidBlock.fillMb(blockState, level, blockPos, null, direction, ic2FluidStack, bl);
        }
        return ENV_HANDLER.fillMb(blockState, level, blockPos, null, direction, ic2FluidStack, bl);
    }

    public static int fillMb(BlockEntity blockEntity, Direction direction, Ic2FluidStack ic2FluidStack, boolean bl) {
        BlockState blockState = blockEntity.m_58900_();
        Block block = blockState.m_60734_();
        if (block instanceof Ic2FluidBlock) {
            Ic2FluidBlock ic2FluidBlock = (Ic2FluidBlock)block;
            return ic2FluidBlock.fillMb(null, null, null, blockEntity, direction, ic2FluidStack, bl);
        }
        return ENV_HANDLER.fillMb(blockState, null, null, blockEntity, direction, ic2FluidStack, bl);
    }

    public static Fluid getWorldFluid(BlockState blockState) {
        return ENV_HANDLER.getWorldFluid(blockState, null, null);
    }

    public static Fluid getWorldFluid(BlockState blockState, Level level, BlockPos blockPos) {
        return ENV_HANDLER.getWorldFluid(blockState, level, blockPos);
    }

    public static int getWorldFluidLevel(BlockState blockState, Level level, BlockPos blockPos) {
        return ENV_HANDLER.getWorldFluidLevel(blockState, level, blockPos);
    }

    public static Ic2FluidStack drainWorldFluid(BlockState blockState, Level level, BlockPos blockPos, boolean bl) {
        return ENV_HANDLER.drainWorldFluid(blockState, level, blockPos, bl);
    }

    public static CompoundTag getFluidStackNbt(Ic2FluidStack ic2FluidStack) {
        return ENV_HANDLER.getFluidStackNbt(ic2FluidStack);
    }

    public static Ic2FluidStack createFluidStackMb(Fluid fluid, int n, CompoundTag compoundTag) {
        return ENV_HANDLER.createFluidStackMb(fluid, n, compoundTag);
    }

    public static Collection<Fluid> getAllFluids() {
        return Registry.f_122822_.m_123024_().filter(fluid -> fluid.m_7444_(fluid.m_76145_()) && fluid != Fluids.f_76191_).toList();
    }
}

