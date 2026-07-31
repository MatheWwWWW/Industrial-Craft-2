/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.machine.container.ContainerClassicCanner;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.item.armor.ItemArmorFluidTank;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityClassicCanner
extends TileEntityElectricMachine
implements IHasGui,
IGuiValueProvider {
    public short progress = 0;
    public int energyConsume = 1;
    public int operationLength = 600;
    private int fuelQuality = 0;
    protected Mode mode;
    protected AudioSource audioSource;
    public final InvSlot resInputSlot = new InvSlot(this, "input", InvSlot.Access.I, 1);
    public final InvSlotConsumable inputSlot = new InvSlotConsumableItemStack(this, "canInput", 1, new ItemStack[]{new ItemStack((ItemLike)Ic2Items.TIN_CAN), new ItemStack((ItemLike)Ic2Items.EMPTY_FUEL_CAN)}){

        @Override
        public boolean accepts(ItemStack itemStack) {
            if (StackUtil.isEmpty(itemStack)) {
                return false;
            }
            Item item = itemStack.m_41720_();
            if (item == Ic2Items.JETPACK || item == Ic2Items.CF_PACK) {
                return true;
            }
            return super.accepts(itemStack);
        }

        @Override
        public void onChanged() {
            super.onChanged();
            TileEntityClassicCanner.this.mode = TileEntityClassicCanner.this.getMode();
        }
    };
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);

    public TileEntityClassicCanner(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.CLASSIC_CANNER, blockPos, blockState, 600, 1);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.fuelQuality = compoundTag.m_128451_("fuelQuality");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("fuelQuality", this.fuelQuality);
    }

    @Override
    public double getGuiValue(String string) {
        if ("progress".equals(string)) {
            int n;
            int n2 = this.operationLength;
            if (this.mode == Mode.FOOD && !this.resInputSlot.isEmpty() && (n = this.getFoodValue(this.resInputSlot.get())) > 0) {
                n2 = 50 * n;
            }
            if (this.mode == Mode.CF) {
                n2 = 50;
            }
            return (double)this.progress / (double)n2;
        }
        throw new IllegalArgumentException("Unexpected name: " + string);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        boolean bl2 = this.canOperate();
        if (bl2 && this.energy.useEnergy(this.energyConsume)) {
            this.activate();
            this.progress = (short)(this.progress + 1);
            if (this.mode == Mode.FOOD && this.progress >= this.getFoodValue(this.resInputSlot.get()) * 50 || this.mode == Mode.FUEL && this.progress > 0 && this.progress % 100 == 0 || this.mode == Mode.CF && this.progress >= 50) {
                if (this.mode != Mode.FUEL || this.progress >= 600) {
                    this.operate(false);
                    this.fuelQuality = 0;
                    this.progress = 0;
                } else {
                    this.operate(true);
                }
                bl = true;
            }
        } else {
            if (this.getActive()) {
                this.shutdown(this.progress > 0);
            }
            if (!bl2 && this.mode != Mode.FUEL) {
                this.fuelQuality = 0;
                this.progress = 0;
            }
        }
        if (bl) {
            this.m_6596_();
        }
    }

    public void operate(boolean bl) {
        switch (this.mode) {
            case FOOD: {
                MachineRecipeResult machineRecipeResult = Recipes.cannerBottle.get(this.f_58857_).apply(new ICannerBottleRecipeManager.RawInput(this.inputSlot.get(), this.resInputSlot.get()), false);
                this.outputSlot.add((ItemStack)machineRecipeResult.getOutput());
                ICannerBottleRecipeManager.RawInput rawInput = machineRecipeResult.getAdjustedInput();
                this.inputSlot.put(rawInput.container);
                this.resInputSlot.put(rawInput.fill);
                break;
            }
            case FUEL: {
                int n = this.getFuelValue(this.resInputSlot.get());
                this.resInputSlot.put(StackUtil.decSize(this.resInputSlot.get()));
                this.fuelQuality += n;
                if (bl) break;
                if (StackUtil.checkItemEquality(this.inputSlot.get(), new ItemStack((ItemLike)Ic2Items.EMPTY_FUEL_CAN))) {
                    this.inputSlot.consume(1);
                    ItemStack itemStack = new ItemStack((ItemLike)Ic2Items.FILLED_FUEL_CAN);
                    CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
                    compoundTag.m_128405_("value", this.fuelQuality);
                    this.outputSlot.add(itemStack);
                    break;
                }
                ItemStack itemStack = this.inputSlot.get();
                ((ItemArmorFluidTank)itemStack.m_41720_()).fillMb(itemStack, Ic2FluidStack.create(Ic2Fluids.BIOGAS.still, this.fuelQuality), false, null);
                this.inputSlot.clear();
                this.outputSlot.add(itemStack);
                break;
            }
            case CF: {
                this.resInputSlot.put(StackUtil.decSize(this.resInputSlot.get()));
                ItemStack itemStack = this.inputSlot.get();
                itemStack.m_41721_(itemStack.m_41773_() + 2);
                if (this.resInputSlot.isEmpty() || itemStack.m_41773_() > itemStack.m_41776_() - 2) {
                    this.outputSlot.add(itemStack);
                    this.inputSlot.clear();
                    break;
                }
                this.inputSlot.put(itemStack);
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
    }

    public boolean canOperate() {
        if (this.inputSlot.isEmpty() || this.resInputSlot.isEmpty()) {
            return false;
        }
        switch (this.mode) {
            case FOOD: {
                return Recipes.cannerBottle.get(this.f_58857_).apply(new ICannerBottleRecipeManager.RawInput(this.inputSlot.get(), this.resInputSlot.get()), false) != null;
            }
            case FUEL: {
                int n = this.getFuelValue(this.resInputSlot.get());
                return n > 0 && this.outputSlot.canAdd(new ItemStack((ItemLike)Ic2Items.JETPACK));
            }
            case CF: {
                ItemStack itemStack = this.inputSlot.get();
                return itemStack.m_41773_() <= itemStack.m_41776_() - 2 && TileEntityClassicCanner.getPelletValue(this.resInputSlot.get()) > 0 && this.outputSlot.canAdd(itemStack);
            }
        }
        assert (false);
        return false;
    }

    public Mode getMode() {
        if (!this.inputSlot.isEmpty()) {
            ItemStack itemStack = this.inputSlot.get();
            Item item = itemStack.m_41720_();
            if (item == Ic2Items.TIN_CAN) {
                return Mode.FOOD;
            }
            if (item == Ic2Items.EMPTY_FUEL_CAN || item == Ic2Items.JETPACK) {
                return Mode.FUEL;
            }
            if (item == Ic2Items.CF_PACK) {
                return Mode.CF;
            }
        }
        return Mode.NONE;
    }

    public int getFoodValue(ItemStack itemStack) {
        MachineRecipeResult machineRecipeResult = Recipes.cannerBottle.get(this.f_58857_).apply(new ICannerBottleRecipeManager.RawInput(new ItemStack((ItemLike)Ic2Items.TIN_CAN, Integer.MAX_VALUE), itemStack), false);
        return machineRecipeResult == null ? 0 : StackUtil.getSize((ItemStack)machineRecipeResult.getOutput());
    }

    public int getFuelValue(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0;
        }
        Item item = itemStack.m_41720_();
        if (item == Ic2Items.COALFUEL_CELL) {
            return 2548;
        }
        if (item == Ic2Items.BIOFUEL_CELL) {
            return 868;
        }
        if (item == Items.f_42451_ && this.fuelQuality > 0) {
            return (int)((double)this.fuelQuality * 0.2);
        }
        if (item == Items.f_42525_ && this.fuelQuality > 0) {
            return (int)((double)this.fuelQuality * 0.3);
        }
        if (item == Items.f_42403_ && this.fuelQuality > 0) {
            return (int)((double)this.fuelQuality * 0.4);
        }
        return 0;
    }

    public static int getPelletValue(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0;
        }
        return itemStack.m_41720_() == Ic2Items.PELLET ? StackUtil.getSize(itemStack) : 0;
    }

    public ContainerBase<TileEntityClassicCanner> createServerScreenHandler(int n, Player player) {
        return new ContainerClassicCanner(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerClassicCanner(n, inventory, this);
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.MACHINE_CANNER_OPERATE;
    }

    @Override
    public SoundEvent getInterruptSoundEvent() {
        return Ic2SoundEvents.MACHINE_INTERRUPT1;
    }

    private static enum Mode {
        NONE,
        FOOD,
        FUEL,
        CF;

    }
}

