/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.nv;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IFuelStorage;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.FuelComparator;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.machines.containers.nv.StoneCannerContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.FuelFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.recipes.misc.FoodCanRegistry;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class StoneCannerTileEntity
extends BaseInventoryTileEntity
implements IFuelStorage,
IProgressMachine,
IWrenchableTile,
ITickListener,
ITileGui,
ITileActivityProvider {
    @NetworkInfo
    public int fuel = 0;
    @NetworkInfo
    public int maxFuel = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int progress = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int maxProgress = 0;
    boolean checkRecipe = true;

    public StoneCannerTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 4);
        this.addGuiFields("fuel", "maxFuel", "progress", "maxProgress");
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
        this.addComparator(new FuelComparator("fuel", ComparatorNames.FUEL, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1, 2);
        handler.registerSlotAccess(AccessRule.EXPORT, 3);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0, 3);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 1, 2);
        handler.registerInputFilter(FuelFilter.WITHOUT_LAVA, 0);
        handler.registerOutputFilter(FuelFilter.NOT_WITHOUT_LAVA, 0);
        handler.registerInputFilter(SpecialFilters.ALWAYS_FALSE, 3);
        handler.registerInputFilter(T -> this.getFoodAmount(T) > 0, 1);
        handler.registerInputFilter(new SimpleFilter((ItemLike)IC2Items.TIN_CAN), 2);
        handler.registerNamedSlot(SlotType.FUEL, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.CONTAINER, 2);
        handler.registerNamedSlot(SlotType.OUTPUT, 3);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128405_("fuel", this.fuel);
        compound.m_128405_("maxFuel", this.maxFuel);
        compound.m_128405_("progress", this.progress);
        compound.m_128344_("maxProgress", (byte)this.maxProgress);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.fuel = compound.m_128451_("fuel");
        this.maxFuel = compound.m_128451_("maxFuel");
        this.progress = compound.m_128451_("progress");
        this.maxProgress = compound.m_128445_("maxProgress");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.STONE_CANNER;
    }

    @Override
    public void onTick() {
        int newFuel;
        boolean canRun;
        if (this.checkRecipe) {
            int foodAmount;
            if (this.maxProgress != (foodAmount *= this.canFitIntoOutput(foodAmount = this.getFoodAmount((ItemStack)this.inventory.get(1))) ? 1 : 0)) {
                this.maxProgress = foodAmount;
                this.updateGuiField("maxProgress");
            }
            this.checkRecipe = false;
        }
        boolean bl = canRun = ((ItemStack)this.inventory.get(2)).m_41613_() >= this.maxProgress;
        if (this.fuel <= 0 && this.maxProgress > 0 && canRun && !((ItemStack)this.inventory.get(0)).m_41619_() && (newFuel = IC2.RECIPES.get(this.isSimulating()).getFuel((ItemStack)this.inventory.get(0), false)) > 0) {
            this.fuel = newFuel;
            this.maxFuel = newFuel;
            this.updateGuiField("maxFuel");
            this.updateGuiField("fuel");
            ItemStack fuel = (ItemStack)this.inventory.get(0);
            if (fuel.hasCraftingRemainingItem()) {
                this.inventory.set(0, (Object)fuel.getCraftingRemainingItem());
            } else {
                fuel.m_41774_(1);
            }
        }
        if (this.maxProgress > 0 && canRun && this.fuel > 0) {
            ++this.progress;
            if (this.progress >= this.maxProgress * 75) {
                this.progress = 0;
                this.setOrGrow(3, this.getOutput(this.maxProgress), false);
                ItemStack input = (ItemStack)this.inventory.get(1);
                if (input.hasCraftingRemainingItem()) {
                    this.inventory.set(1, (Object)input.getCraftingRemainingItem());
                } else {
                    input.m_41774_(1);
                }
                ((ItemStack)this.inventory.get(2)).m_41774_(this.maxProgress);
                this.checkRecipe = true;
            }
            this.updateGuiField("progress");
        } else {
            if ((this.maxProgress == 0 || ((ItemStack)this.inventory.get(2)).m_41619_()) && this.progress > 0) {
                this.progress = 0;
                this.updateGuiField("progress");
            }
            if (this.progress > 0) {
                --this.progress;
                this.updateGuiField("progress");
            }
        }
        if (this.fuel > 0) {
            --this.fuel;
            this.updateGuiField("fuel");
        }
        this.setActive(this.fuel > 0);
        this.handleComparators();
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        ItemStack lastItem = (ItemStack)this.inventory.get(slot);
        super.setStackInSlot(slot, stack);
        if (IC2.PLATFORM.isSimulating() && slot >= 1) {
            this.checkRecipe = true;
            if (slot == 1 && !StackUtil.isStackEqual(stack, lastItem)) {
                this.progress = 0;
                this.updateGuiField("progress");
            }
        }
    }

    @Override
    public float getProgress() {
        return this.progress;
    }

    @Override
    public float getMaxProgress() {
        return this.maxProgress * 75;
    }

    @Override
    public int getFuel() {
        return this.fuel;
    }

    @Override
    public int getMaxFuel() {
        return this.maxFuel;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return dir != this.getFacing() && dir.m_122434_().m_122479_();
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 1.0;
    }

    @Override
    public boolean isHarvestWrenchRequired(Player player) {
        return false;
    }

    public int getFoodAmount(ItemStack stack) {
        Item item = stack.m_41720_();
        return item.m_41472_() ? Math.max(1, Mth.m_14165_((double)((double)item.m_41473_().m_38744_() / 2.0))) : (item == Items.f_42502_ ? 6 : 0);
    }

    public ItemStack getOutput(int amount) {
        if (amount <= 0) {
            return ItemStack.f_41583_;
        }
        Item output = FoodCanRegistry.INSTANCE.getItemForFood((ItemStack)this.inventory.get(1));
        return new ItemStack((ItemLike)(output == null ? IC2Items.TIN_CAN_FILLED : output), amount);
    }

    public boolean canFitIntoOutput(int amount) {
        ItemStack stack = this.getOutput(amount);
        if (stack.m_41619_()) {
            return false;
        }
        ItemStack stored = (ItemStack)this.inventory.get(3);
        return stored.m_41619_() || StackUtil.canFitInto(stored, stack);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new StoneCannerContainer(this, player, windowID);
    }
}

