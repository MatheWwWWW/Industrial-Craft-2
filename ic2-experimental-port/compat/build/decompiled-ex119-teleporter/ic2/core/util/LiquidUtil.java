/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  org.apache.commons.lang3.mutable.Mutable
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package ic2.core.util;

import ic2.api.util.FluidContainerOutputMode;
import ic2.core.fluid.FluidHandler;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

public class LiquidUtil {
    public static List<Fluid> getAllFluidsSorted() {
        ArrayList<Fluid> arrayList = new ArrayList<Fluid>(FluidHandler.getAllFluids());
        arrayList.sort(Comparator.comparing(arg_0 -> ((DefaultedRegistry)Registry.f_122822_).m_7981_(arg_0)));
        return arrayList;
    }

    public static LiquidData getLiquid(Level level, BlockPos blockPos) {
        BlockState blockState = level.m_8055_(blockPos);
        Block block = blockState.m_60734_();
        Fluid fluid = FluidHandler.getWorldFluid(blockState, level, blockPos);
        boolean bl = false;
        if (fluid != null) {
            Ic2FluidStack ic2FluidStack = FluidHandler.drainWorldFluid(blockState, level, blockPos, true);
            bl = ic2FluidStack != null && ic2FluidStack.getAmountMb() >= 1000;
        } else if (block == Blocks.f_49990_) {
            fluid = Fluids.f_76193_;
            bl = (Integer)blockState.m_61143_((Property)LiquidBlock.f_54688_) == 0;
        } else if (block == Blocks.f_49991_) {
            fluid = Fluids.f_76195_;
            bl = (Integer)blockState.m_61143_((Property)LiquidBlock.f_54688_) == 0;
        }
        return fluid != null ? new LiquidData(fluid, bl) : null;
    }

    public static boolean isFluidContainer(ItemStack itemStack) {
        return Ic2FluidStack.get(itemStack) != null;
    }

    public static boolean isDrainableFluidContainer(ItemStack itemStack) {
        Ic2FluidStack ic2FluidStack = Ic2FluidStack.get(itemStack);
        return ic2FluidStack != null && !ic2FluidStack.isEmpty();
    }

    public static boolean isFillableFluidContainer(ItemStack itemStack) {
        return LiquidUtil.isFillableFluidContainer(itemStack, null);
    }

    public static boolean isFillableFluidContainer(ItemStack itemStack, Iterable<Fluid> iterable) {
        ItemStack itemStack2;
        Ic2FluidStack[] ic2FluidStackArray;
        if (!LiquidUtil.isFluidContainer(itemStack)) {
            return false;
        }
        if (iterable == null) {
            iterable = FluidHandler.getAllFluids();
        }
        if ((ic2FluidStackArray = Ic2FluidStack.getAll(itemStack2 = StackUtil.copyWithSize(itemStack, 1))) == null) {
            return false;
        }
        for (Ic2FluidStack ic2FluidStack : ic2FluidStackArray) {
            if (ic2FluidStack.getFluid() == null || FluidHandler.fillMb(itemStack2, ic2FluidStack.copyWithAmountMb(Integer.MAX_VALUE), true, null) <= 0) continue;
            return true;
        }
        for (Fluid fluid : FluidHandler.getAllFluids()) {
            if (FluidHandler.fillMb(itemStack2, Ic2FluidStack.create(fluid, Integer.MAX_VALUE), true, null) <= 0) continue;
            return true;
        }
        return false;
    }

    public static Ic2FluidStack drainContainer(Player player, InteractionHand interactionHand, Fluid fluid, int n, FluidContainerOutputMode fluidContainerOutputMode, boolean bl) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        FluidOperationResult fluidOperationResult = LiquidUtil.drainContainer(itemStack, fluid, n, fluidContainerOutputMode);
        if (fluidOperationResult == null) {
            return null;
        }
        if (fluidOperationResult.extraOutput != null && !StackUtil.storeInventoryItem(fluidOperationResult.extraOutput, player, bl)) {
            return null;
        }
        if (!bl) {
            StackUtil.set(player, interactionHand, fluidOperationResult.inPlaceOutput);
        }
        return fluidOperationResult.fluidChange;
    }

    public static int fillContainer(Player player, InteractionHand interactionHand, Ic2FluidStack ic2FluidStack, FluidContainerOutputMode fluidContainerOutputMode, boolean bl) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        FluidOperationResult fluidOperationResult = LiquidUtil.fillContainer(itemStack, ic2FluidStack, fluidContainerOutputMode);
        if (fluidOperationResult == null) {
            return 0;
        }
        if (fluidOperationResult.extraOutput != null && !StackUtil.storeInventoryItem(fluidOperationResult.extraOutput, player, bl)) {
            return 0;
        }
        if (!bl) {
            StackUtil.set(player, interactionHand, fluidOperationResult.inPlaceOutput);
        }
        return fluidOperationResult.fluidChange.getAmountMb();
    }

    public static FluidOperationResult drainContainer(ItemStack itemStack, Fluid fluid, int n, FluidContainerOutputMode fluidContainerOutputMode) {
        if (StackUtil.isEmpty(itemStack) || n <= 0) {
            return null;
        }
        Ic2FluidStack ic2FluidStack = Ic2FluidStack.get(itemStack);
        ItemStack itemStack2 = StackUtil.copy(itemStack);
        ItemStack itemStack3 = null;
        if (ic2FluidStack != null) {
            ItemStack itemStack4 = StackUtil.copyWithSize(itemStack2, 1);
            MutableObject mutableObject = new MutableObject();
            if (fluid == null) {
                ic2FluidStack = FluidHandler.drainMb(itemStack4, n, false, (Mutable<ItemStack>)mutableObject);
                if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
                    return null;
                }
            } else {
                ic2FluidStack = Ic2FluidStack.create(fluid, n);
                int n2 = FluidHandler.drainMb(itemStack4, ic2FluidStack, false, (Mutable<ItemStack>)mutableObject);
                if (n2 <= 0) {
                    return null;
                }
                ic2FluidStack.setAmountMb(n2);
            }
            if (StackUtil.isEmpty(itemStack4)) {
                itemStack2 = StackUtil.decSize(itemStack2);
                itemStack3 = (ItemStack)mutableObject.getValue();
            } else {
                boolean bl;
                Ic2FluidStack ic2FluidStack2 = FluidHandler.drainMb(itemStack4, Integer.MAX_VALUE, true, null);
                boolean bl2 = bl = ic2FluidStack2 == null || ic2FluidStack2.isEmpty();
                if (bl && fluidContainerOutputMode.isOutputEmptyFull() || fluidContainerOutputMode == FluidContainerOutputMode.AnyToOutput || fluidContainerOutputMode == FluidContainerOutputMode.InPlacePreferred && StackUtil.getSize(itemStack2) > 1) {
                    itemStack3 = (ItemStack)mutableObject.getValue();
                    itemStack2 = StackUtil.decSize(itemStack2);
                } else {
                    if (StackUtil.getSize(itemStack2) > 1) {
                        return null;
                    }
                    itemStack2 = (ItemStack)mutableObject.getValue();
                }
            }
        } else {
            return null;
        }
        assert (!ic2FluidStack.isEmpty());
        return new FluidOperationResult(ic2FluidStack, itemStack2, itemStack3);
    }

    public static FluidOperationResult fillContainer(ItemStack itemStack, Ic2FluidStack ic2FluidStack, FluidContainerOutputMode fluidContainerOutputMode) {
        MutableObject mutableObject;
        Ic2FluidStack ic2FluidStack2;
        if (StackUtil.isEmpty(itemStack) || ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            return null;
        }
        ItemStack itemStack2 = StackUtil.copy(itemStack);
        ItemStack itemStack3 = StackUtil.copyWithSize(itemStack2, 1);
        int n = FluidHandler.fillMb(itemStack3, ic2FluidStack2 = ic2FluidStack.copy(), false, (Mutable<ItemStack>)(mutableObject = new MutableObject()));
        if (n <= 0) {
            return null;
        }
        ic2FluidStack2.setAmountMb(n);
        Ic2FluidStack ic2FluidStack3 = ic2FluidStack.copy();
        ic2FluidStack3.setAmountMb(Integer.MAX_VALUE);
        boolean bl = FluidHandler.fillMb(itemStack3, ic2FluidStack3, true, null) <= 0;
        itemStack3 = (ItemStack)mutableObject.getValue();
        assert (ic2FluidStack2.getFluid() == ic2FluidStack.getFluid());
        assert (!ic2FluidStack2.isEmpty());
        assert (StackUtil.getSize(itemStack3) == 1);
        ItemStack itemStack4 = null;
        if (bl && fluidContainerOutputMode.isOutputEmptyFull() || fluidContainerOutputMode == FluidContainerOutputMode.AnyToOutput || fluidContainerOutputMode == FluidContainerOutputMode.InPlacePreferred && StackUtil.getSize(itemStack2) > 1) {
            itemStack4 = itemStack3;
            itemStack2 = StackUtil.decSize(itemStack2);
        } else {
            if (StackUtil.getSize(itemStack2) > 1) {
                return null;
            }
            itemStack2 = itemStack3;
        }
        return new FluidOperationResult(ic2FluidStack2, itemStack2, itemStack4);
    }

    public static boolean isFluidTile(BlockEntity blockEntity, Direction direction) {
        return FluidHandler.isFluidBlock(blockEntity, direction);
    }

    public static boolean isFluidTile(BlockState blockState, BlockEntity blockEntity, Direction direction) {
        return FluidHandler.isFluidBlock(blockState, blockEntity, direction);
    }

    public static Ic2FluidStack drainTile(BlockState blockState, BlockEntity blockEntity, Direction direction, int n, boolean bl) {
        return FluidHandler.drainMb(blockState, blockEntity, direction, n, bl);
    }

    public static Ic2FluidStack drainTile(BlockState blockState, Level level, BlockPos blockPos, Direction direction, int n, boolean bl) {
        return FluidHandler.drainMb(blockState, level, blockPos, direction, n, bl);
    }

    public static int drainTile(BlockEntity blockEntity, Direction direction, Fluid fluid, int n, boolean bl) {
        return FluidHandler.drainMb(blockEntity, direction, Ic2FluidStack.create(fluid, n), bl);
    }

    public static int fillTile(BlockEntity blockEntity, Direction direction, Ic2FluidStack ic2FluidStack, boolean bl) {
        return FluidHandler.fillMb(blockEntity, direction, ic2FluidStack, bl);
    }

    public static List<AdjacentFluidHandler> getAdjacentHandlers(BlockEntity blockEntity) {
        ArrayList<AdjacentFluidHandler> arrayList = new ArrayList<AdjacentFluidHandler>();
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity blockEntity2 = blockEntity.m_58904_().m_7702_(blockEntity.m_58899_().m_121945_(direction));
            if (!LiquidUtil.isFluidTile(blockEntity2, direction.m_122424_())) continue;
            arrayList.add(new AdjacentFluidHandler(blockEntity2, direction));
        }
        return arrayList;
    }

    public static AdjacentFluidHandler getAdjacentHandler(BlockEntity blockEntity, Direction direction) {
        BlockEntity blockEntity2 = blockEntity.m_58904_().m_7702_(blockEntity.m_58899_().m_121945_(direction));
        if (!LiquidUtil.isFluidTile(blockEntity2, direction.m_122424_())) {
            return null;
        }
        return new AdjacentFluidHandler(blockEntity2, direction);
    }

    public static int distribute(BlockEntity blockEntity, Ic2FluidStack ic2FluidStack, boolean bl) {
        int n = 0;
        for (AdjacentFluidHandler adjacentFluidHandler : LiquidUtil.getAdjacentHandlers(blockEntity)) {
            int n2 = LiquidUtil.fillTile(adjacentFluidHandler.handler, adjacentFluidHandler.dir.m_122424_(), ic2FluidStack, bl);
            n += n2;
            ic2FluidStack.setAmountMb(ic2FluidStack.getAmountMb() - n2);
            if (!ic2FluidStack.isEmpty()) continue;
            break;
        }
        ic2FluidStack.setAmountMb(ic2FluidStack.getAmountMb() + n);
        return n;
    }

    public static Ic2FluidStack transfer(BlockEntity blockEntity, Direction direction, BlockEntity blockEntity2, int n) {
        Ic2FluidStack ic2FluidStack;
        int n2;
        BlockState blockState = blockEntity.m_58900_();
        do {
            if ((ic2FluidStack = LiquidUtil.drainTile(blockState, blockEntity, direction, n, true)) == null || ic2FluidStack.isEmpty()) {
                return null;
            }
            if (ic2FluidStack.getAmountMb() > n) {
                throw new IllegalStateException("The fluid handler " + blockEntity + " drained more than the requested amount.");
            }
            n2 = LiquidUtil.fillTile(blockEntity2, LiquidUtil.getOppositeDir(direction), ic2FluidStack.copy(), true);
            if (n2 <= n) continue;
            throw new IllegalStateException("The fluid handler " + blockEntity2 + " filled more than the requested amount.");
        } while ((n = n2) != ic2FluidStack.getAmountMb() && n > 0);
        if (n <= 0) {
            return null;
        }
        ic2FluidStack = LiquidUtil.drainTile(blockState, blockEntity, direction, n, false);
        if (ic2FluidStack == null) {
            throw new IllegalStateException("The fluid handler " + blockEntity + " drained inconsistently. Expected " + n + ", couldn't find previous IFluidHandler facing " + direction + ".");
        }
        if (ic2FluidStack.getAmountMb() != n) {
            throw new IllegalStateException("The fluid handler " + blockEntity + " drained inconsistently. Expected " + n + ", got " + ic2FluidStack.getAmountMb() + ".");
        }
        n = LiquidUtil.fillTile(blockEntity2, LiquidUtil.getOppositeDir(direction), ic2FluidStack.copy(), false);
        if (n != ic2FluidStack.getAmountMb()) {
            throw new IllegalStateException("The fluid handler " + blockEntity2 + " filled inconsistently. Expected " + ic2FluidStack.getAmountMb() + ", got " + n + ".");
        }
        return ic2FluidStack;
    }

    public static int distributeAll(BlockEntity blockEntity, int n) {
        if (blockEntity == null) {
            throw new IllegalArgumentException("source has to be a tile entity");
        }
        BlockEntity blockEntity2 = blockEntity;
        int n2 = 0;
        for (Direction direction : Util.ALL_DIRS) {
            Ic2FluidStack ic2FluidStack;
            BlockEntity blockEntity3 = blockEntity2.m_58904_().m_7702_(blockEntity2.m_58899_().m_121945_(direction));
            if (!LiquidUtil.isFluidTile(blockEntity3, direction.m_122424_()) || (ic2FluidStack = LiquidUtil.transfer(blockEntity, direction, blockEntity3, n)) == null) continue;
            n2 += ic2FluidStack.getAmountMb();
            if ((n -= ic2FluidStack.getAmountMb()) <= 0) break;
        }
        return n2;
    }

    private static Direction getOppositeDir(Direction direction) {
        if (direction == null) {
            return null;
        }
        return direction.m_122424_();
    }

    public static boolean check(Ic2FluidStack ic2FluidStack) {
        return ic2FluidStack.getFluid() != null;
    }

    public static Ic2FluidStack drainWorldFluidBlock(Level level, BlockPos blockPos, boolean bl) {
        BlockState blockState = level.m_8055_(blockPos);
        Block block = blockState.m_60734_();
        Ic2FluidStack ic2FluidStack = FluidHandler.drainWorldFluid(blockState, level, blockPos, bl);
        if (ic2FluidStack != null) {
            return ic2FluidStack;
        }
        if (block instanceof LiquidBlock && (Integer)blockState.m_61143_((Property)LiquidBlock.f_54688_) == 0) {
            Ic2FluidStack ic2FluidStack2 = null;
            if (block == Blocks.f_49990_) {
                ic2FluidStack2 = Ic2FluidStack.create((Fluid)Fluids.f_76193_, 1000);
            } else if (block == Blocks.f_49991_) {
                ic2FluidStack2 = Ic2FluidStack.create((Fluid)Fluids.f_76195_, 1000);
            }
            if (ic2FluidStack2 != null && !bl) {
                level.m_7471_(blockPos, false);
            }
            return ic2FluidStack2;
        }
        return null;
    }

    public static boolean drainWorldFluidBlockToContainer(Level level, BlockPos blockPos, Player player, InteractionHand interactionHand) {
        Ic2FluidStack ic2FluidStack = LiquidUtil.drainWorldFluidBlock(level, blockPos, true);
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            return false;
        }
        int n = LiquidUtil.fillContainer(player, interactionHand, ic2FluidStack, FluidContainerOutputMode.InPlacePreferred, true);
        if (n != ic2FluidStack.getAmountMb()) {
            return false;
        }
        ic2FluidStack = LiquidUtil.drainWorldFluidBlock(level, blockPos, false);
        LiquidUtil.fillContainer(player, interactionHand, ic2FluidStack, FluidContainerOutputMode.InPlacePreferred, false);
        return true;
    }

    public static boolean fillWorldFluidBlock(Ic2FluidStack ic2FluidStack, Level level, BlockPos blockPos, boolean bl) {
        if (ic2FluidStack == null || ic2FluidStack.getAmountMb() < 1000) {
            return false;
        }
        Fluid fluid = ic2FluidStack.getFluid();
        Block block = fluid.m_76145_().m_76188_().m_60734_();
        if (fluid == null || block == null) {
            return false;
        }
        BlockState blockState = level.m_8055_(blockPos);
        Block block2 = blockState.m_60734_();
        if (!blockState.m_60795_() && blockState.m_60767_().m_76333_()) {
            return false;
        }
        if (block2 == block && LiquidUtil.isFullWorldFluidBlock(level, blockPos, block2, blockState)) {
            return false;
        }
        if (bl) {
            return true;
        }
        if (level.m_6042_().f_63857_() && block.m_49966_().m_60767_() == Material.f_76305_) {
            level.m_5594_(null, blockPos, SoundEvents.f_11937_, SoundSource.BLOCKS, 0.5f, 2.6f + (level.f_46441_.m_188501_() - level.f_46441_.m_188501_()) * 0.8f);
            for (int i = 0; i < 8; ++i) {
                level.m_7106_((ParticleOptions)ParticleTypes.f_123755_, (double)blockPos.m_123341_() + Math.random(), (double)blockPos.m_123342_() + Math.random(), (double)blockPos.m_123343_() + Math.random(), 0.0, 0.0, 0.0);
            }
        } else {
            if (!(level.f_46443_ || blockState.m_60767_().m_76333_() || blockState.m_60767_().m_76332_())) {
                level.m_7471_(blockPos, true);
            }
            if (!level.m_46597_(blockPos, (block2 = fluid == Fluids.f_76193_ ? Blocks.f_49990_ : (fluid == Fluids.f_76195_ ? Blocks.f_49991_ : fluid.m_76145_().m_76188_().m_60734_())).m_49966_())) {
                return false;
            }
        }
        ic2FluidStack.decreaseMb(1000);
        return true;
    }

    private static boolean isFullWorldFluidBlock(Level level, BlockPos blockPos, Block block, BlockState blockState) {
        Ic2FluidStack ic2FluidStack = FluidHandler.drainWorldFluid(blockState, level, blockPos, true);
        if (ic2FluidStack != null) {
            return ic2FluidStack.getAmountMb() >= 1000;
        }
        if (blockState.m_61148_().containsKey((Object)LiquidBlock.f_54688_)) {
            return (Integer)blockState.m_61143_((Property)LiquidBlock.f_54688_) == 0;
        }
        return false;
    }

    public static boolean fillWorldFluidBlockFromContainer(Level level, BlockPos blockPos, Player player, InteractionHand interactionHand) {
        Ic2FluidStack ic2FluidStack = LiquidUtil.drainContainer(player, interactionHand, null, 1000, FluidContainerOutputMode.InPlacePreferred, true);
        if (ic2FluidStack == null || ic2FluidStack.getAmountMb() < 1000) {
            return false;
        }
        if (!LiquidUtil.fillWorldFluidBlock(ic2FluidStack, level, blockPos, false)) {
            return false;
        }
        LiquidUtil.drainContainer(player, interactionHand, null, 1000, FluidContainerOutputMode.InPlacePreferred, false);
        return true;
    }

    public static boolean storeOutputContainer(MutableObject<ItemStack> mutableObject, Player player) {
        if (mutableObject.getValue() == null) {
            return true;
        }
        return StackUtil.storeInventoryItem((ItemStack)mutableObject.getValue(), player, false);
    }

    public static class LiquidData {
        public final Fluid liquid;
        public final boolean isSource;

        LiquidData(Fluid fluid, boolean bl) {
            this.liquid = fluid;
            this.isSource = bl;
        }
    }

    public static class FluidOperationResult {
        public final Ic2FluidStack fluidChange;
        public final ItemStack inPlaceOutput;
        public final ItemStack extraOutput;

        FluidOperationResult(Ic2FluidStack ic2FluidStack, ItemStack itemStack, ItemStack itemStack2) {
            if (ic2FluidStack == null) {
                throw new NullPointerException("null fluid change");
            }
            this.fluidChange = ic2FluidStack;
            this.inPlaceOutput = itemStack;
            this.extraOutput = itemStack2;
        }
    }

    public static class AdjacentFluidHandler {
        public final BlockEntity handler;
        public final Direction dir;

        AdjacentFluidHandler(BlockEntity blockEntity, Direction direction) {
            this.handler = blockEntity;
            this.dir = direction;
        }
    }
}

