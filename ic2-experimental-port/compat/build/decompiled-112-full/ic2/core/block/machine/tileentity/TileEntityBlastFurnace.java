/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.energy.tile.IHeatSource;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.item.type.IngotResourceType;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.recipe.BasicMachineRecipeManager;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityBlastFurnace
extends TileEntityInventory
implements IUpgradableBlock,
IHasGui,
IGuiValueProvider {
    public int heat = 0;
    public static int maxHeat = 50000;
    @GuiSynced
    public float guiHeat;
    protected final Redstone redstone;
    protected final Fluids fluids;
    protected int progress = 0;
    protected int progressNeeded = 300;
    @GuiSynced
    protected float guiProgress;
    public final InvSlotProcessableGeneric inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, Recipes.blastfurnace);
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 2){

        @Override
        public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
            if (player != null && ItemName.ingot.getItemStack(IngotResourceType.steel).func_77969_a(stack)) {
                IC2.achievements.issueAchievement(player, "acquireRefinedIron");
            }
        }
    };
    public final InvSlotConsumableLiquidByList tankInputSlot = new InvSlotConsumableLiquidByList(this, "cellInput", 1, FluidName.air.getInstance());
    public final InvSlotOutput tankOutputSlot = new InvSlotOutput(this, "cellOutput", 1);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 2);
    @GuiSynced
    public final FluidTank fluidTank;

    public TileEntityBlastFurnace() {
        this.redstone = this.addComponent(new Redstone(this));
        this.fluids = this.addComponent(new Fluids(this));
        this.fluidTank = this.fluids.addTankInsert("fluid", 8000, Fluids.fluidPredicate(FluidName.air.getInstance()));
    }

    public static void init() {
        Recipes.blastfurnace = new BasicMachineRecipeManager();
    }

    @Override
    public void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        this.heatup();
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result = this.getOutput();
        if (result != null && this.isHot()) {
            this.setActive(true);
            if (result.getRecipe().getMetaData().func_74762_e("fluid") <= this.fluidTank.getFluidAmount()) {
                ++this.progress;
                this.fluidTank.drainInternal(result.getRecipe().getMetaData().func_74762_e("fluid"), true);
            }
            this.progressNeeded = result.getRecipe().getMetaData().func_74762_e("duration");
            if (this.progress >= result.getRecipe().getMetaData().func_74762_e("duration")) {
                this.operateOnce(result, result.getOutput());
                needsInvUpdate = true;
                this.progress = 0;
            }
        } else {
            if (result == null) {
                this.progress = 0;
            }
            this.setActive(false);
        }
        if (this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity()) {
            this.gainFluid();
        }
        this.guiProgress = (float)this.progress / (float)this.progressNeeded;
        this.guiHeat = (float)this.heat / (float)maxHeat;
        if (needsInvUpdate |= this.upgradeSlot.tickNoMark()) {
            super.func_70296_d();
        }
    }

    public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result, Collection<ItemStack> processResult) {
        this.inputSlot.consume(result);
        this.outputSlot.add(processResult);
    }

    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
        if (this.inputSlot.isEmpty()) {
            return null;
        }
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> output = this.inputSlot.process();
        if (output == null || output.getRecipe().getMetaData() == null) {
            return null;
        }
        if (this.outputSlot.canAdd((Collection)output.getOutput())) {
            return output;
        }
        return null;
    }

    public boolean gainFluid() {
        return this.tankInputSlot.processIntoTank((IFluidTank)this.fluidTank, this.tankOutputSlot);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.heat = nbt.func_74762_e("heat");
        this.progress = nbt.func_74762_e("progress");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("heat", this.heat);
        nbt.func_74768_a("progress", this.progress);
        return nbt;
    }

    private void heatup() {
        boolean coolingPerTick = true;
        int heatRequested = 0;
        int gainhU = 0;
        if (!(this.inputSlot.isEmpty() && this.progress < 1 || this.heat > maxHeat)) {
            heatRequested = maxHeat - this.heat + 100;
        } else if (this.redstone.hasRedstoneInput() && this.heat <= maxHeat) {
            heatRequested = maxHeat - this.heat + 100;
        }
        if (heatRequested > 0) {
            EnumFacing dir = this.getFacing();
            TileEntity te = this.func_145831_w().func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (te instanceof IHeatSource) {
                gainhU = ((IHeatSource)te).drawHeat(dir.func_176734_d(), heatRequested, false);
                this.heat += gainhU;
            }
            if (gainhU == 0) {
                this.heat -= Math.min(this.heat, 1);
            }
        } else {
            this.heat -= Math.min(this.heat, 1);
        }
    }

    public boolean isHot() {
        return this.heat >= maxHeat;
    }

    public ContainerBase<TileEntityBlastFurnace> getGuiContainer(EntityPlayer player) {
        return DynamicContainer.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return DynamicGui.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public double getGuiValue(String name) {
        if (name.equals("progress")) {
            return this.guiProgress;
        }
        if (name.equals("heat")) {
            return this.guiHeat;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public double getEnergy() {
        return 0.0;
    }

    @Override
    public boolean useEnergy(double amount) {
        return false;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming);
    }
}

