/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.base.drops;

import ic2.core.platform.registries.IC2Blocks;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockDropProvider {
    public static final IBlockDropProvider SELF = new SelfProvider();
    public static final IBlockDropProvider SELF_OR_MACHINE = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.MACHINE_BLOCK));
    public static final IBlockDropProvider SELF_OR_ADV_MACHINE = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.ADVANCED_MACHINE_BLOCK));
    public static final IBlockDropProvider SELF_OR_STABLE_MACHINE = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.STABILIZED_MACHINE_BLOCK));
    public static final IBlockDropProvider SELF_OR_GENERATOR = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.GENERATOR));
    public static final IBlockDropProvider SELF_OR_COLOSSAL = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.COLOSSAL_BASE));
    public static final IBlockDropProvider SELF_OR_BATBOX = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.BATBOX));
    public static final IBlockDropProvider SELF_OR_MFE = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.MFE));
    public static final IBlockDropProvider SELF_OR_MFSU = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.MFSU));
    public static final IBlockDropProvider SELF_OR_ESU = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.ESU));
    public static final IBlockDropProvider SELF_OR_ISU = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.ISU));
    public static final IBlockDropProvider SELF_OR_MV_TRANSFORMER = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.TRANSFORMER_MV));
    public static final IBlockDropProvider SELF_OR_HV_TRANSFORMER = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.TRANSFORMER_HV));
    public static final IBlockDropProvider SELF_OR_EV_TRANSFORMER = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.TRANSFORMER_EV));
    public static final IBlockDropProvider SELF_OR_IV_TRANSFORMER = new SelfOrOther(() -> new ItemStack((ItemLike)IC2Blocks.TRANSFORMER_IV));

    public static IBlockDropProvider custom(Supplier<ItemStack> supplier) {
        return new CustomProvider(supplier);
    }

    public ItemStack createDrop(BlockState var1, ItemStack var2, RandomSource var3, @Nullable BlockEntity var4, boolean var5);

    public static class CustomProvider
    implements IBlockDropProvider {
        Supplier<ItemStack> drop;

        public CustomProvider(Supplier<ItemStack> drop) {
            this.drop = drop;
        }

        @Override
        public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, BlockEntity tile, boolean wrench) {
            return this.drop.get();
        }
    }

    public static class SelfProvider
    implements IBlockDropProvider {
        @Override
        public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, BlockEntity tile, boolean wrench) {
            return new ItemStack((ItemLike)state.m_60734_());
        }
    }

    public static class SelfOrOther
    implements IBlockDropProvider {
        Supplier<ItemStack> drop;

        public SelfOrOther(Supplier<ItemStack> drop) {
            this.drop = drop;
        }

        @Override
        public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, BlockEntity tile, boolean wrench) {
            return wrench ? new ItemStack((ItemLike)state.m_60734_()) : this.drop.get();
        }
    }
}

