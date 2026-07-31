/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.machine.container.ContainerClassicCanner;
import ic2.core.block.machine.gui.GuiClassicCanner;
import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.item.type.CellType;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.StackUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@TeBlock.Delegated(current=TileEntityCanner.class, old=TileEntityClassicCanner.class)
public class TileEntityClassicCanner
extends TileEntityElectricMachine
implements IHasGui,
IGuiValueProvider,
INetworkTileEntityEventListener {
    public short progress = 0;
    public int energyConsume = 1;
    public int operationLength = 600;
    private int fuelQuality = 0;
    protected Mode mode;
    protected AudioSource audioSource;
    public final InvSlot resInputSlot = new InvSlot(this, "input", InvSlot.Access.I, 1);
    public final InvSlotConsumable inputSlot = new InvSlotConsumableItemStack(this, "canInput", 1, new ItemStack[]{ItemName.crafting.getItemStack(CraftingItemType.tin_can), ItemName.crafting.getItemStack(CraftingItemType.empty_fuel_can)}){

        @Override
        public boolean accepts(ItemStack stack) {
            if (StackUtil.isEmpty(stack)) {
                return false;
            }
            Item item = stack.func_77973_b();
            if (item == ItemName.jetpack.getInstance() || item == ItemName.cf_pack.getInstance()) {
                return true;
            }
            return super.accepts(stack);
        }

        @Override
        public void onChanged() {
            super.onChanged();
            TileEntityClassicCanner.this.mode = TileEntityClassicCanner.this.getMode();
        }
    };
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);

    public TileEntityClassicCanner() {
        super(600, 1);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.fuelQuality = nbt.func_74762_e("fuelQuality");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("fuelQuality", this.fuelQuality);
        return nbt;
    }

    @Override
    protected void onUnloaded() {
        super.onUnloaded();
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
    }

    @Override
    public double getGuiValue(String name) {
        if ("progress".equals(name)) {
            int food;
            int l = this.operationLength;
            if (this.mode == Mode.FOOD && !this.resInputSlot.isEmpty() && (food = TileEntityClassicCanner.getFoodValue(this.resInputSlot.get())) > 0) {
                l = 50 * food;
            }
            if (this.mode == Mode.CF) {
                l = 50;
            }
            return (double)this.progress / (double)l;
        }
        throw new IllegalArgumentException("Unexpected name: " + name);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        boolean canOperate = this.canOperate();
        if (canOperate && this.energy.useEnergy(this.energyConsume)) {
            this.setActive(true);
            if (this.progress == 0) {
                IC2.network.get(true).initiateTileEntityEvent(this, 0, true);
            }
            this.progress = (short)(this.progress + 1);
            if (this.mode == Mode.FOOD && this.progress >= TileEntityClassicCanner.getFoodValue(this.resInputSlot.get()) * 50 || this.mode == Mode.FUEL && this.progress > 0 && this.progress % 100 == 0 || this.mode == Mode.CF && this.progress >= 50) {
                if (this.mode != Mode.FUEL || this.progress >= 600) {
                    this.operate(false);
                    this.fuelQuality = 0;
                    this.progress = 0;
                } else {
                    this.operate(true);
                }
                needsInvUpdate = true;
                IC2.network.get(true).initiateTileEntityEvent(this, 2, true);
            } else if (this.progress % 50 == 0) {
                IC2.network.get(true).initiateTileEntityEvent(this, 2, true);
                IC2.network.get(true).initiateTileEntityEvent(this, 0, true);
            }
        } else {
            if (this.getActive() && this.progress > 0) {
                IC2.network.get(true).initiateTileEntityEvent(this, 1, true);
            }
            if (!canOperate && this.mode != Mode.FUEL) {
                this.fuelQuality = 0;
                this.progress = 0;
            }
            this.setActive(false);
        }
        if (needsInvUpdate) {
            this.func_70296_d();
        }
    }

    public void operate(boolean incremental) {
        switch (this.mode) {
            case FOOD: {
                MachineRecipeResult result = Recipes.cannerBottle.apply(new ICannerBottleRecipeManager.RawInput(this.inputSlot.get(), this.resInputSlot.get()), false);
                this.outputSlot.add((ItemStack)result.getOutput());
                ICannerBottleRecipeManager.RawInput newInput = result.getAdjustedInput();
                this.inputSlot.put(newInput.container);
                this.resInputSlot.put(newInput.fill);
                break;
            }
            case FUEL: {
                int fuel = this.getFuelValue(this.resInputSlot.get());
                this.resInputSlot.put(StackUtil.decSize(this.resInputSlot.get()));
                this.fuelQuality += fuel;
                if (incremental) break;
                if (StackUtil.checkItemEquality(this.inputSlot.get(), ItemName.crafting.getItemStack(CraftingItemType.empty_fuel_can))) {
                    this.inputSlot.consume(1);
                    ItemStack result = ItemName.filled_fuel_can.getItemStack();
                    NBTTagCompound data = StackUtil.getOrCreateNbtData(result);
                    data.func_74768_a("value", this.fuelQuality);
                    this.outputSlot.add(result);
                    break;
                }
                int damage = this.inputSlot.get().func_77952_i();
                if ((damage -= this.fuelQuality) < 1) {
                    damage = 1;
                }
                this.inputSlot.clear();
                this.outputSlot.add(new ItemStack(ItemName.jetpack.getInstance(), 1, damage));
                break;
            }
            case CF: {
                this.resInputSlot.put(StackUtil.decSize(this.resInputSlot.get()));
                ItemStack cfPack = this.inputSlot.get();
                cfPack.func_77964_b(cfPack.func_77952_i() + 2);
                if (this.resInputSlot.isEmpty() || cfPack.func_77952_i() > cfPack.func_77958_k() - 2) {
                    this.outputSlot.add(cfPack);
                    this.inputSlot.clear();
                    break;
                }
                this.inputSlot.put(cfPack);
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
                return Recipes.cannerBottle.apply(new ICannerBottleRecipeManager.RawInput(this.inputSlot.get(), this.resInputSlot.get()), false) != null;
            }
            case FUEL: {
                int fuel = this.getFuelValue(this.resInputSlot.get());
                return fuel > 0 && this.outputSlot.canAdd(ItemName.jetpack.getItemStack());
            }
            case CF: {
                ItemStack cfPack = this.inputSlot.get();
                return cfPack.func_77952_i() <= cfPack.func_77958_k() - 2 && TileEntityClassicCanner.getPelletValue(this.resInputSlot.get()) > 0 && this.outputSlot.canAdd(cfPack);
            }
        }
        assert (false);
        return false;
    }

    public Mode getMode() {
        if (!this.inputSlot.isEmpty()) {
            ItemStack input = this.inputSlot.get();
            if (StackUtil.checkItemEquality(input, ItemName.crafting.getItemStack(CraftingItemType.tin_can))) {
                return Mode.FOOD;
            }
            if (StackUtil.checkItemEquality(input, ItemName.crafting.getItemStack(CraftingItemType.empty_fuel_can)) || input.func_77973_b() == ItemName.jetpack.getInstance()) {
                return Mode.FUEL;
            }
            if (input.func_77973_b() == ItemName.cf_pack.getInstance()) {
                return Mode.CF;
            }
        }
        return Mode.NONE;
    }

    public static int getFoodValue(ItemStack stack) {
        MachineRecipeResult result = Recipes.cannerBottle.apply(new ICannerBottleRecipeManager.RawInput(StackUtil.copyWithSize(ItemName.crafting.getItemStack(CraftingItemType.tin_can), Integer.MAX_VALUE), stack), false);
        return result == null ? 0 : StackUtil.getSize((ItemStack)result.getOutput());
    }

    public int getFuelValue(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            return 0;
        }
        if (StackUtil.checkItemEquality(stack, ItemName.cell.getItemStack(CellType.coalfuel))) {
            return 2548;
        }
        if (StackUtil.checkItemEquality(stack, ItemName.cell.getItemStack(CellType.biofuel))) {
            return 868;
        }
        if (stack.func_77973_b() == Items.field_151137_ax && this.fuelQuality > 0) {
            return (int)((double)this.fuelQuality * 0.2);
        }
        if (stack.func_77973_b() == Items.field_151114_aO && this.fuelQuality > 0) {
            return (int)((double)this.fuelQuality * 0.3);
        }
        if (stack.func_77973_b() == Items.field_151016_H && this.fuelQuality > 0) {
            return (int)((double)this.fuelQuality * 0.4);
        }
        return 0;
    }

    public static int getPelletValue(ItemStack item) {
        if (StackUtil.isEmpty(item)) {
            return 0;
        }
        return StackUtil.checkItemEquality(item, ItemName.crafting.getItemStack(CraftingItemType.pellet)) ? StackUtil.getSize(item) : 0;
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer player) {
        return new ContainerClassicCanner(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiClassicCanner(new ContainerClassicCanner(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    public String getStartSoundFile() {
        return "Machines/CannerOp.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    @Override
    public void onNetworkEvent(int event) {
        if (this.audioSource == null) {
            this.audioSource = IC2.audioManager.createSource(this, this.getStartSoundFile());
        }
        switch (event) {
            case 0: {
                if (this.audioSource == null) break;
                this.audioSource.play();
                break;
            }
            case 2: {
                if (this.audioSource == null) break;
                this.audioSource.stop();
                break;
            }
            case 1: {
                if (this.audioSource == null) break;
                this.audioSource.stop();
                IC2.audioManager.playOnce(this, PositionSpec.Center, this.getInterruptSoundFile(), false, IC2.audioManager.getDefaultVolume());
            }
        }
    }

    private static enum Mode {
        NONE,
        FOOD,
        FUEL,
        CF;

    }
}

