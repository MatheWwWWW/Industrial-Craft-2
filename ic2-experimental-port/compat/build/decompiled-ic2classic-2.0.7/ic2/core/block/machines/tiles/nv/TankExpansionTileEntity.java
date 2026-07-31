/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.machines.tiles.nv;

import ic2.api.network.buffer.NetworkInfo;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.tiles.impls.BaseExpansionTileEntity;
import ic2.core.block.machines.containers.nv.FluidExpansionContainer;
import ic2.core.block.machines.logic.crafter.CraftRecipe;
import ic2.core.block.machines.logic.crafter.CraftingList;
import ic2.core.block.machines.logic.crafter.Snapshot;
import ic2.core.fluid.ArrayFluidHandler;
import ic2.core.fluid.IC2Tank;
import ic2.core.fluid.WrappedFluid;
import ic2.core.fluid.WrapperFluidHandler;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.transporters.BaseTransporter;
import ic2.core.platform.recipes.helpers.ItemStackCache;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TankExpansionTileEntity
extends BaseExpansionTileEntity
implements ITileGui,
IClickable {
    public static final String[] FIELDS = new String[]{"firstTank", "secondTank", "thirdTank", "fourthTank"};
    @NetworkInfo
    public IC2Tank firstTank = new IC2Tank(128000, T -> this.canFill(0, (FluidStack)T)).addListener(T -> this.updateTank(0));
    @NetworkInfo
    public IC2Tank secondTank = new IC2Tank(128000, T -> this.canFill(1, (FluidStack)T)).addListener(T -> this.updateTank(1));
    @NetworkInfo
    public IC2Tank thirdTank = new IC2Tank(128000, T -> this.canFill(2, (FluidStack)T)).addListener(T -> this.updateTank(2));
    @NetworkInfo
    public IC2Tank fourthTank = new IC2Tank(128000, T -> this.canFill(3, (FluidStack)T)).addListener(T -> this.updateTank(3));
    IC2Tank[] handlers = new IC2Tank[]{this.firstTank, this.secondTank, this.thirdTank, this.fourthTank};
    CraftRecipe recipe = null;
    int recipeSlot = -1;

    public TankExpansionTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, new ArrayFluidHandler((IFluidHandler[])this.handlers));
        this.addNetworkFields(FIELDS);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.FLUID_EXPANSION;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FluidExpansionContainer(this, player, windowID);
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ArrayFluidHandler handler = new ArrayFluidHandler((IFluidHandler[])this.handlers);
        if (FluidHelper.drainContainers(player.m_21120_(hand), player, handler)) {
            return true;
        }
        return FluidHelper.fillContainers(player.m_21120_(hand), player, handler);
    }

    public IC2Tank getTank(int index) {
        return this.handlers[index];
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128365_("tank_1", (Tag)this.firstTank.writeToNBT(new CompoundTag()));
        compound.m_128365_("tank_2", (Tag)this.secondTank.writeToNBT(new CompoundTag()));
        compound.m_128365_("tank_3", (Tag)this.thirdTank.writeToNBT(new CompoundTag()));
        compound.m_128365_("tank_4", (Tag)this.fourthTank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.firstTank.readFromNBT(compound.m_128469_("tank_1"));
        this.secondTank.readFromNBT(compound.m_128469_("tank_2"));
        this.thirdTank.readFromNBT(compound.m_128469_("tank_3"));
        this.fourthTank.readFromNBT(compound.m_128469_("tank_4"));
    }

    protected void consume(int extra) {
        if (this.recipe == null || this.recipeSlot == -1) {
            return;
        }
        this.recipe.consumeItems(this.recipeSlot, extra);
    }

    public void updateTank(int index) {
        this.updateTileField(FIELDS[index]);
    }

    @Override
    public boolean needsRecipeInfo() {
        return true;
    }

    @Override
    public void setCurrentCraftingRecipe(CraftRecipe recipe, int recipeSlot) {
        this.recipe = recipe;
        this.recipeSlot = recipeSlot;
    }

    @Override
    public void clearCurrentRecipe() {
        this.recipe = null;
        this.recipeSlot = -1;
    }

    protected boolean canFill(int index, FluidStack stack) {
        for (int i = 0; i < 4; ++i) {
            if (i == index || !this.handlers[i].getFluidInTank(0).isFluidEqual(stack)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void createMainTransporter(boolean simulate, List<IItemTransporter> insertion, List<IItemTransporter> extraction) {
        extraction.add(new TankTransporter(this, simulate));
    }

    @Override
    public void createTrashTransporter(boolean simulate, List<IItemTransporter> insertion, List<IItemTransporter> extraction) {
    }

    @Override
    public CraftingList getExpansionRecipeList() {
        return null;
    }

    @Override
    public void createSnapshot() {
        if (this.snapshot != null) {
            throw new RuntimeException("Snapshot already existed");
        }
        this.snapshot = new Snapshot(4);
        this.snapshot.set(0, new WrappedFluid(this.firstTank.getFluid().copy()));
        this.snapshot.set(1, new WrappedFluid(this.secondTank.getFluid().copy()));
        this.snapshot.set(2, new WrappedFluid(this.thirdTank.getFluid().copy()));
        this.snapshot.set(3, new WrappedFluid(this.fourthTank.getFluid().copy()));
    }

    @Override
    public void releaseSnapshot() {
        if (this.snapshot == null) {
            throw new IllegalStateException("Snapshot is missing");
        }
        this.firstTank.setFluid(this.snapshot.get(0, WrappedFluid.class).getFluid());
        this.secondTank.setFluid(this.snapshot.get(1, WrappedFluid.class).getFluid());
        this.thirdTank.setFluid(this.snapshot.get(2, WrappedFluid.class).getFluid());
        this.fourthTank.setFluid(this.snapshot.get(3, WrappedFluid.class).getFluid());
        this.snapshot = null;
    }

    @Override
    public void triggerRestock(IItemTransporter transporter, IFluidHandler handler, Player player) {
        for (int i = 0; i < 4; ++i) {
            FluidStack toFill;
            FluidStack saved = this.snapshot.get(i, WrappedFluid.class).getFluid();
            FluidStack stored = this.handlers[i].getFluid();
            if (saved.isEmpty() && stored.isEmpty()) continue;
            if (saved.isEmpty() && !stored.isEmpty()) {
                this.handlers[i].setFluid(FluidStack.EMPTY);
                continue;
            }
            if (!saved.isEmpty() && stored.isEmpty()) {
                toFill = new FluidStack(saved.getFluid(), 128000);
                toFill.setAmount(this.handlers[i].fill(handler.drain(toFill, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE));
                if (toFill.getAmount() <= 0) continue;
                handler.drain(toFill, IFluidHandler.FluidAction.EXECUTE);
                continue;
            }
            if (!stored.isFluidEqual(saved)) {
                stored.setAmount(0);
                this.handlers[i].setFluid(FluidStack.EMPTY);
            }
            if ((toFill = new FluidStack(saved.getFluid(), 128000 - stored.getAmount())).getAmount() <= 0) continue;
            toFill.setAmount(this.handlers[i].fill(handler.drain(toFill, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE));
            if (toFill.getAmount() <= 0) continue;
            handler.drain(toFill, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    public static class TankTransporter
    extends BaseTransporter {
        TankExpansionTileEntity tile;
        boolean doSimulate;
        IFluidHandler[] handlers;

        public TankTransporter(TankExpansionTileEntity tile, boolean doSimulate) {
            this.tile = tile;
            this.doSimulate = doSimulate;
            this.handlers = new IFluidHandler[tile.handlers.length];
            for (int i = 0; i < this.handlers.length; ++i) {
                this.handlers[i] = doSimulate ? new WrapperFluidHandler((IFluidHandler)tile.handlers[i]) : tile.handlers[i];
            }
        }

        @Override
        public int addItem(ItemStack stack, Direction dir, boolean simulate) {
            return 0;
        }

        @Override
        public ItemStack removeItem(IFilter filter, Direction dir, int amount, boolean simulate) {
            if (amount <= 0) {
                return ItemStack.f_41583_;
            }
            for (int i = 0; i < this.handlers.length; ++i) {
                IFluidHandler handler = this.handlers[i];
                FluidStack stack = handler.getFluidInTank(0);
                if (stack.isEmpty()) continue;
                ObjectArrayList<ItemStack> items = ItemStackCache.INSTANCE.getFluidContainers(stack.getFluid());
                int m = items.size();
                for (int j = 0; j < m; ++j) {
                    ItemStack item = (ItemStack)items.get(j);
                    if (!filter.matches(item) || item.m_41741_() < amount) continue;
                    FluidStack stored = FluidUtil.getFluidContained((ItemStack)item).orElse(FluidStack.EMPTY);
                    int drainedAmount = handler.drain(new FluidStack(stored, Math.min(stored.getAmount() * amount, stack.getAmount() / stored.getAmount() * stack.getAmount())), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE).getAmount() / stored.getAmount();
                    if (!simulate) {
                        this.tile.consume(drainedAmount);
                    }
                    return StackUtil.copyWithSize(item, drainedAmount);
                }
            }
            return ItemStack.f_41583_;
        }

        @Override
        public int getInventorySize(Direction dir) {
            return 0;
        }

        @Override
        public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
            return Object2IntMaps.emptyMap();
        }

        @Override
        public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
            return new IItemTransporter.InvResult(compareNBT);
        }
    }
}

