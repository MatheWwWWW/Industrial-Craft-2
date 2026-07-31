/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.BucketItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Material
 *  org.apache.commons.lang3.mutable.Mutable
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.fluid;

import ic2.core.fluid.Ic2FluidStack;
import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import org.apache.commons.lang3.mutable.Mutable;
import org.jetbrains.annotations.Nullable;

public interface EnvFluidHandler {
    public FluidRefs createFluid(ResourceLocation var1, Material var2, int var3, int var4, int var5, int var6, ResourceLocation var7, ResourceLocation var8, int var9);

    public Collection<Fluid> getAllFluids();

    public int getDensity(Fluid var1);

    public int getTemperature(Fluid var1);

    public boolean isGaseous(Fluid var1);

    public ResourceLocation getStillSpriteId(Fluid var1);

    public ResourceLocation getFlowingSpriteId(Fluid var1);

    public int getColor(Fluid var1);

    public Ic2FluidStack createFluidStackMb(Fluid var1, int var2, @Nullable CompoundTag var3);

    public Ic2FluidStack getFluidStack(ItemStack var1);

    public Ic2FluidStack[] getFluidStacks(ItemStack var1);

    public Ic2FluidStack readFluidStack(CompoundTag var1);

    public CompoundTag getFluidStackNbt(Ic2FluidStack var1);

    public Ic2FluidStack drainMb(ItemStack var1, int var2, boolean var3, @Nullable Mutable<ItemStack> var4);

    public int drainMb(ItemStack var1, Ic2FluidStack var2, boolean var3, @Nullable Mutable<ItemStack> var4);

    public int fillMb(ItemStack var1, Ic2FluidStack var2, boolean var3, @Nullable Mutable<ItemStack> var4);

    public boolean isFluidBlock(BlockState var1, Level var2, BlockPos var3, BlockEntity var4, Direction var5);

    public Ic2FluidStack drainMb(BlockState var1, Level var2, BlockPos var3, BlockEntity var4, Direction var5, int var6, boolean var7);

    public int drainMb(BlockState var1, Level var2, BlockPos var3, BlockEntity var4, Direction var5, Ic2FluidStack var6, boolean var7);

    public int fillMb(BlockState var1, Level var2, BlockPos var3, BlockEntity var4, Direction var5, Ic2FluidStack var6, boolean var7);

    public Fluid getWorldFluid(BlockState var1, Level var2, BlockPos var3);

    public int getWorldFluidLevel(BlockState var1, Level var2, BlockPos var3);

    public Ic2FluidStack drainWorldFluid(BlockState var1, Level var2, BlockPos var3, boolean var4);

    public static final class FluidRefs {
        public final Block block;
        public final Fluid still;
        public final Fluid flowing;
        public final BucketItem bucket;

        public FluidRefs(Block block, Fluid fluid, Fluid fluid2, BucketItem bucketItem) {
            this.block = block;
            this.still = fluid;
            this.flowing = fluid2;
            this.bucket = bucketItem;
        }
    }
}

