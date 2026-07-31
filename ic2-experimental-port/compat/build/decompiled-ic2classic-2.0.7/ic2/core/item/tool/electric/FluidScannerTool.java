/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.item.tool.electric;

import ic2.api.items.electric.IFluidScanner;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.inv.inventory.FluidScannerInventory;
import ic2.core.item.tool.electric.ScannerTool;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class FluidScannerTool
extends ScannerTool
implements IFluidScanner {
    public FluidScannerTool(String itemName, int cost, int radius, boolean ore, String texture) {
        super(itemName, cost, radius, ore, texture);
    }

    @Override
    public int getOreValue(ItemStack stack, BlockState state) {
        return this.isValuableFluid(stack, state.m_60819_()) ? 1 : 0;
    }

    public static class FluidScannerFiltered
    extends FluidScannerTool
    implements IHasHeldSlotInventory {
        public FluidScannerFiltered() {
            super("fluid_scanner_filtered", 100, 20, false, "filtered_fluid");
            this.tier = 2;
            this.transferLimit = 100;
            this.hasScan = false;
        }

        @Override
        public boolean isValuableFluid(ItemStack stack, FluidState state) {
            if (state.m_76178_() || !state.m_76170_()) {
                return false;
            }
            Fluid fluid = state.m_76152_();
            for (CompoundTag nbt : NBTListWrapper.wrap(StackUtil.getNbtData(stack).m_128437_("fluids", 10), CompoundTag.class)) {
                Fluid stored = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(nbt.m_128461_("fluid")));
                if (stored == Fluids.f_76191_ || stored != fluid) continue;
                return true;
            }
            return false;
        }

        @Override
        protected List<Block> getTargets(ItemStack stack) {
            ObjectList blocks = CollectionUtils.createList();
            for (CompoundTag nbt : NBTListWrapper.wrap(StackUtil.getNbtData(stack).m_128437_("fluids", 10), CompoundTag.class)) {
                FluidState state = ((Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(nbt.m_128461_("fluid")))).m_76145_();
                if (state.m_76178_() || state.m_76188_().m_60734_() == Blocks.f_50016_) continue;
                blocks.add((Block)state.m_76188_().m_60734_());
            }
            return blocks;
        }

        @Override
        public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
            if (IC2.KEYBOARD.isSideInventoryKeyDown(playerIn)) {
                ItemStack stack = playerIn.m_21120_(handIn);
                IC2.PLATFORM.launchGui(playerIn, handIn, Direction.NORTH, this.getInventory(playerIn, handIn, stack));
                return InteractionResultHolder.m_19090_((Object)stack);
            }
            return super.m_7203_(worldIn, playerIn, handIn);
        }

        @Override
        public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
            return new FluidScannerInventory(player, this, stack, null).load(stack);
        }

        @Override
        public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
            return new FluidScannerInventory(player, this, stack, slot).load(stack);
        }
    }

    public static class FluidScanner
    extends FluidScannerTool {
        public FluidScanner() {
            super("fluid_scanner", 100, 20, false, "fluid");
            this.tier = 1;
            this.transferLimit = 50;
            this.hasScan = false;
        }

        @Override
        public boolean isValuableFluid(ItemStack stack, FluidState state) {
            return state.m_76152_() != Fluids.f_76191_ && state.m_76170_();
        }

        @Override
        protected List<Block> getTargets(ItemStack stack) {
            ObjectList blocks = CollectionUtils.createList();
            for (Fluid fluid : ForgeRegistries.FLUIDS) {
                FluidState state = fluid.m_76145_();
                if (fluid == Fluids.f_76191_ || !state.m_76170_() || state.m_76188_().m_60734_() == Blocks.f_50016_) continue;
                blocks.add((Block)state.m_76188_().m_60734_());
            }
            return blocks;
        }
    }
}

