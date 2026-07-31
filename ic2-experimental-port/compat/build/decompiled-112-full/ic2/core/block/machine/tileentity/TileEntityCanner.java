/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.IEmptyFluidContainerRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.api.util.FluidContainerOutputMode;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlotConsumableCanner;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotProcessableCanner;
import ic2.core.block.machine.CannerBottleRecipeManager;
import ic2.core.block.machine.CannerEnrichRecipeManager;
import ic2.core.block.machine.container.ContainerCanner;
import ic2.core.block.machine.gui.GuiCanner;
import ic2.core.block.machine.tileentity.TileEntityClassicCanner;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.item.type.CraftingItemType;
import ic2.core.item.type.DustResourceType;
import ic2.core.item.type.NuclearResourceType;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@TeBlock.Delegated(current=TileEntityCanner.class, old=TileEntityClassicCanner.class)
public class TileEntityCanner
extends TileEntityStandardMachine<Object, Object, Object>
implements INetworkClientTileEntityEventListener {
    private Mode mode = Mode.BottleSolid;
    public static final int eventSetModeBase = 0;
    public static final int eventSwapTanks = 0 + Mode.values.length + 1;
    public final FluidTank inputTank;
    public final FluidTank outputTank;
    public final InvSlotConsumableCanner canInputSlot;
    protected final Fluids fluids;

    public static Class<? extends TileEntityElectricMachine> delegate() {
        return IC2.version.isClassic() ? TileEntityClassicCanner.class : TileEntityCanner.class;
    }

    public TileEntityCanner() {
        super(4, 200, 1);
        this.inputSlot = new InvSlotProcessableCanner(this, "input", 1);
        this.canInputSlot = new InvSlotConsumableCanner(this, "canInput", 1);
        this.fluids = this.addComponent(new Fluids(this));
        this.inputTank = this.fluids.addTankInsert("inputTank", 8000);
        this.outputTank = this.fluids.addTankExtract("outputTank", 8000);
    }

    public static void init() {
        Recipes.cannerBottle = new CannerBottleRecipeManager();
        Recipes.cannerEnrich = new CannerEnrichRecipeManager();
        ItemStack fuelRod = ItemName.crafting.getItemStack(CraftingItemType.fuel_rod);
        TileEntityCanner.addBottleRecipe(fuelRod, ItemName.nuclear.getItemStack(NuclearResourceType.uranium), ItemName.uranium_fuel_rod.getItemStack());
        TileEntityCanner.addBottleRecipe(fuelRod, ItemName.nuclear.getItemStack(NuclearResourceType.mox), ItemName.mox_fuel_rod.getItemStack());
        ItemStack tinCan = ItemName.crafting.getItemStack(CraftingItemType.tin_can);
        ItemStack filledTinCan = ItemName.filled_tin_can.getItemStack();
        TileEntityCanner.addBottleRecipe(tinCan, new ItemStack(Items.field_151174_bG), filledTinCan);
        TileEntityCanner.addBottleRecipe(tinCan, 2, new ItemStack(Items.field_151106_aX), StackUtil.copyWithSize(filledTinCan, 2));
        TileEntityCanner.addBottleRecipe(tinCan, 2, new ItemStack(Items.field_151127_ba), StackUtil.copyWithSize(filledTinCan, 2));
        TileEntityCanner.addBottleRecipe(tinCan, 2, new ItemStack(Items.field_151115_aP), StackUtil.copyWithSize(filledTinCan, 2));
        TileEntityCanner.addBottleRecipe(tinCan, 2, new ItemStack(Items.field_151076_bf), StackUtil.copyWithSize(filledTinCan, 2));
        TileEntityCanner.addBottleRecipe(tinCan, 3, new ItemStack(Items.field_151147_al), StackUtil.copyWithSize(filledTinCan, 3));
        TileEntityCanner.addBottleRecipe(tinCan, 3, new ItemStack(Items.field_151082_bd), StackUtil.copyWithSize(filledTinCan, 3));
        TileEntityCanner.addBottleRecipe(tinCan, 4, new ItemStack(Items.field_151034_e), StackUtil.copyWithSize(filledTinCan, 4));
        TileEntityCanner.addBottleRecipe(tinCan, 4, new ItemStack(Items.field_151172_bF), StackUtil.copyWithSize(filledTinCan, 4));
        TileEntityCanner.addBottleRecipe(tinCan, 5, new ItemStack(Items.field_151025_P), StackUtil.copyWithSize(filledTinCan, 5));
        TileEntityCanner.addBottleRecipe(tinCan, 5, new ItemStack(Items.field_179566_aV), StackUtil.copyWithSize(filledTinCan, 5));
        TileEntityCanner.addBottleRecipe(tinCan, 6, new ItemStack(Items.field_151077_bg), StackUtil.copyWithSize(filledTinCan, 6));
        TileEntityCanner.addBottleRecipe(tinCan, 6, new ItemStack(Items.field_151168_bH), StackUtil.copyWithSize(filledTinCan, 6));
        TileEntityCanner.addBottleRecipe(tinCan, 6, new ItemStack(Items.field_151009_A), StackUtil.copyWithSize(filledTinCan, 6));
        TileEntityCanner.addBottleRecipe(tinCan, 6, new ItemStack(Items.field_151158_bO), StackUtil.copyWithSize(filledTinCan, 6));
        TileEntityCanner.addBottleRecipe(tinCan, 8, new ItemStack(Items.field_151157_am), StackUtil.copyWithSize(filledTinCan, 8));
        TileEntityCanner.addBottleRecipe(tinCan, 8, new ItemStack(Items.field_151083_be), StackUtil.copyWithSize(filledTinCan, 8));
        TileEntityCanner.addBottleRecipe(tinCan, 12, new ItemStack(Items.field_151105_aU), StackUtil.copyWithSize(filledTinCan, 12));
        TileEntityCanner.addBottleRecipe(tinCan, new ItemStack(Items.field_151170_bI), 2, filledTinCan);
        TileEntityCanner.addBottleRecipe(tinCan, new ItemStack(Items.field_151078_bh), 2, filledTinCan);
        TileEntityCanner.addEnrichRecipe(FluidRegistry.WATER, ItemName.dust.getItemStack(DustResourceType.milk), FluidName.milk.getInstance());
        TileEntityCanner.addEnrichRecipe(FluidRegistry.WATER, ItemName.crafting.getItemStack(CraftingItemType.cf_powder), FluidName.construction_foam.getInstance());
        TileEntityCanner.addEnrichRecipe(FluidRegistry.WATER, Recipes.inputFactory.forOreDict("dustLapis", 8), FluidName.coolant.getInstance());
        TileEntityCanner.addEnrichRecipe(FluidName.distilled_water.getInstance(), Recipes.inputFactory.forOreDict("dustLapis", 1), FluidName.coolant.getInstance());
        TileEntityCanner.addEnrichRecipe(FluidRegistry.WATER, ItemName.crafting.getItemStack(CraftingItemType.bio_chaff), FluidName.biomass.getInstance());
        TileEntityCanner.addEnrichRecipe(new FluidStack(FluidRegistry.WATER, 6000), Recipes.inputFactory.forStack(new ItemStack(Items.field_151055_y)), new FluidStack(FluidName.hot_water.getInstance(), 1000));
    }

    public static void addBottleRecipe(ItemStack container, int conamount, ItemStack fill, int fillamount, ItemStack output) {
        TileEntityCanner.addBottleRecipe(Recipes.inputFactory.forStack(container, conamount), Recipes.inputFactory.forStack(fill, fillamount), output);
    }

    public static void addBottleRecipe(ItemStack container, ItemStack fill, int fillamount, ItemStack output) {
        TileEntityCanner.addBottleRecipe(Recipes.inputFactory.forStack(container, 1), Recipes.inputFactory.forStack(fill, fillamount), output);
    }

    public static void addBottleRecipe(ItemStack container, int conamount, ItemStack fill, ItemStack output) {
        TileEntityCanner.addBottleRecipe(Recipes.inputFactory.forStack(container, conamount), Recipes.inputFactory.forStack(fill, 1), output);
    }

    public static void addBottleRecipe(ItemStack container, ItemStack fill, ItemStack output) {
        TileEntityCanner.addBottleRecipe(Recipes.inputFactory.forStack(container, 1), Recipes.inputFactory.forStack(fill, 1), output);
    }

    public static void addBottleRecipe(IRecipeInput container, IRecipeInput fill, ItemStack output) {
        Recipes.cannerBottle.addRecipe(container, fill, output);
    }

    public static void addEnrichRecipe(Fluid input, ItemStack additive, Fluid output) {
        TileEntityCanner.addEnrichRecipe(new FluidStack(input, 1000), Recipes.inputFactory.forStack(additive, 1), new FluidStack(output, 1000));
    }

    public static void addEnrichRecipe(Fluid input, IRecipeInput additive, Fluid output) {
        TileEntityCanner.addEnrichRecipe(new FluidStack(input, 1000), additive, new FluidStack(output, 1000));
    }

    public static void addEnrichRecipe(FluidStack input, IRecipeInput additive, FluidStack output) {
        Recipes.cannerEnrich.addRecipe(input, additive, output);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.setMode(Mode.values[nbt.func_74762_e("mode")]);
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("mode", this.mode.ordinal());
        return nbt;
    }

    @Override
    public void operateOnce(MachineRecipeResult<Object, Object, Object> result, Collection<ItemStack> processResult) {
        super.operateOnce(result, processResult);
        if (this.mode == Mode.EmptyLiquid) {
            IEmptyFluidContainerRecipeManager.Output output = (IEmptyFluidContainerRecipeManager.Output)result.getOutput();
            this.getOutputTank().fill(output.fluid, true);
        } else if (this.mode == Mode.EnrichLiquid) {
            FluidStack output = ((FluidStack)result.getOutput()).copy();
            if (!this.canInputSlot.isEmpty()) {
                LiquidUtil.FluidOperationResult outcome;
                do {
                    if ((outcome = LiquidUtil.fillContainer(this.canInputSlot.get(), output, FluidContainerOutputMode.EmptyFullToOutput)) == null) continue;
                    if (outcome.extraOutput == null || this.outputSlot.canAdd(outcome.extraOutput)) {
                        this.canInputSlot.put(outcome.inPlaceOutput);
                        if (outcome.extraOutput != null) {
                            this.outputSlot.add(outcome.extraOutput);
                        }
                        output.amount -= outcome.fluidChange.amount;
                        continue;
                    }
                    outcome = null;
                } while (outcome != null && output.amount > 0);
            }
            this.getOutputTank().fill(output, true);
        }
    }

    @Override
    protected Collection<ItemStack> getOutput(Object output) {
        if (output instanceof ItemStack) {
            return Collections.singletonList((ItemStack)output);
        }
        if (output instanceof FluidStack) {
            return Collections.emptyList();
        }
        if (output instanceof IEmptyFluidContainerRecipeManager.Output) {
            return ((IEmptyFluidContainerRecipeManager.Output)output).container;
        }
        return super.getOutput(output);
    }

    @Override
    public MachineRecipeResult<Object, Object, Object> getOutput() {
        if (this.mode == Mode.EmptyLiquid || this.mode == Mode.BottleLiquid ? this.canInputSlot.isEmpty() : this.inputSlot.isEmpty()) {
            return null;
        }
        MachineRecipeResult<Object, Object, Object> result = this.inputSlot.process();
        if (result == null) {
            return null;
        }
        if (!this.outputSlot.canAdd(this.getOutput(result.getOutput()))) {
            return null;
        }
        if (this.mode == Mode.EmptyLiquid) {
            IEmptyFluidContainerRecipeManager.Output output = (IEmptyFluidContainerRecipeManager.Output)result.getOutput();
            if (this.getOutputTank().fill(output.fluid, false) != output.fluid.amount) {
                return null;
            }
        } else if (this.mode == Mode.EnrichLiquid) {
            FluidStack output = ((FluidStack)result.getOutput()).copy();
            if (!this.canInputSlot.isEmpty()) {
                LiquidUtil.FluidOperationResult outcome;
                do {
                    if ((outcome = LiquidUtil.fillContainer(this.canInputSlot.get(), output, FluidContainerOutputMode.EmptyFullToOutput)) == null) continue;
                    if (outcome.extraOutput == null || this.outputSlot.canAdd(outcome.extraOutput)) {
                        output.amount -= outcome.fluidChange.amount;
                        continue;
                    }
                    outcome = null;
                } while (outcome != null && output.amount > 0);
            }
            if (this.getOutputTank().fill(output, false) != output.amount) {
                return null;
            }
        }
        return result;
    }

    public FluidTank getInputTank() {
        return this.inputTank;
    }

    public FluidTank getOutputTank() {
        return this.outputTank;
    }

    @Override
    public List<String> getNetworkedFields() {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add("canInputSlot");
        ret.addAll(super.getNetworkedFields());
        return ret;
    }

    @Override
    public String getStartSoundFile() {
        switch (this.mode) {
            default: 
        }
        return null;
    }

    @Override
    public String getInterruptSoundFile() {
        switch (this.mode) {
            default: 
        }
        return null;
    }

    @Override
    public ContainerBase<TileEntityCanner> getGuiContainer(EntityPlayer player) {
        return new ContainerCanner(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiCanner(new ContainerCanner(player, this));
    }

    @Override
    public void onNetworkUpdate(String field) {
        super.onNetworkUpdate(field);
        if (field.equals("mode")) {
            this.setMode(this.mode);
        }
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        if (event >= 0 && event < 0 + Mode.values.length) {
            this.setMode(Mode.values[event - 0]);
        } else if (event == eventSwapTanks) {
            this.switchTanks();
        }
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        switch (mode) {
            case BottleSolid: {
                this.canInputSlot.setOpType(InvSlotConsumableLiquid.OpType.None);
                break;
            }
            case BottleLiquid: {
                this.canInputSlot.setOpType(InvSlotConsumableLiquid.OpType.Fill);
                break;
            }
            case EmptyLiquid: {
                this.canInputSlot.setOpType(InvSlotConsumableLiquid.OpType.Drain);
                break;
            }
            case EnrichLiquid: {
                this.canInputSlot.setOpType(InvSlotConsumableLiquid.OpType.Both);
            }
        }
        if (IC2.platform.isRendering()) {
            if (this.audioSource != null) {
                this.audioSource.stop();
            }
            if (this.getStartSoundFile() != null) {
                this.audioSource = IC2.audioManager.createSource(this, this.getStartSoundFile());
            }
        }
    }

    private boolean switchTanks() {
        if (this.progress != 0) {
            return false;
        }
        FluidStack inputStack = this.inputTank.getFluid();
        FluidStack outputStack = this.outputTank.getFluid();
        this.inputTank.setFluid(outputStack);
        this.outputTank.setFluid(inputStack);
        return true;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[]{UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing});
    }

    public static enum Mode {
        BottleSolid,
        EmptyLiquid,
        BottleLiquid,
        EnrichLiquid;

        public static final Mode[] values;

        static {
            values = Mode.values();
        }
    }
}

