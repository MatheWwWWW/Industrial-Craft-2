/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.recipe.BasicMachineRecipeManager;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@NotClassic
public class TileEntityCentrifuge
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
    protected final Redstone redstone;
    public static final short maxHeat = 5000;
    @GuiSynced
    public short heat = 0;
    @GuiSynced
    public short workheat = (short)5000;

    public TileEntityCentrifuge() {
        super(48, 500, 3, 2);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, Recipes.centrifuge);
        this.redstone = this.addComponent(new Redstone(this));
    }

    public static void init() {
        Recipes.centrifuge = new BasicMachineRecipeManager();
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.heat = nbt.func_74765_d("heat");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74777_a("heat", this.heat);
        return nbt;
    }

    public double getHeatRatio() {
        return (double)this.heat / (double)this.workheat;
    }

    private static short min(short a, short b) {
        return a <= b ? a : b;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean energyPerHeat = true;
        boolean coolingPerTick = true;
        boolean heating = false;
        if (this.energy.canUseEnergy(1.0)) {
            int heatRequested = Short.MIN_VALUE;
            MachineRecipeResult output = super.getOutput();
            if (output != null && !this.redstone.hasRedstoneInput()) {
                heatRequested = TileEntityCentrifuge.min((short)5000, output.getRecipe().getMetaData().func_74765_d("minHeat"));
                this.workheat = (short)heatRequested;
                if (this.heat > heatRequested) {
                    this.heat = (short)heatRequested;
                }
            } else if (this.heat <= 5000 && this.redstone.hasRedstoneInput()) {
                heatRequested = 5000;
                this.workheat = (short)heatRequested;
            }
            if (this.heat - 1 < heatRequested) {
                this.energy.useEnergy(1.0);
                heating = true;
            }
        }
        this.heat = heating ? (short)(this.heat + 1) : (short)(this.heat - Math.min(this.heat, 1));
    }

    @Override
    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> ret = super.getOutput();
        if (ret != null) {
            if (ret.getRecipe().getMetaData() == null) {
                return null;
            }
            if (ret.getRecipe().getMetaData().func_74762_e("minHeat") > this.heat) {
                return null;
            }
        }
        return ret;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[]{UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing});
    }

    @Override
    public double getGuiValue(String name) {
        if ("heat".equals(name)) {
            return (double)this.heat / (double)this.workheat;
        }
        return super.getGuiValue(name);
    }
}

