/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.IMachineRecipeManager;
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
import ic2.core.ref.Ic2BlockEntities;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityCentrifuge
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
    protected final Redstone redstone;
    public static final short maxHeat = 5000;
    @GuiSynced
    public short heat = 0;
    @GuiSynced
    public short workheat = (short)5000;

    public TileEntityCentrifuge(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.CENTRIFUGE, blockPos, blockState, 48, 500, 3, 2);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, (Recipes.IGetter<? extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>>)Recipes.centrifuge);
        this.redstone = this.addComponent(new Redstone(this));
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.heat = compoundTag.m_128448_("heat");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128376_("heat", this.heat);
    }

    public double getHeatRatio() {
        return (double)this.heat / (double)this.workheat;
    }

    private static short min(short s, short s2) {
        return s <= s2 ? s : s2;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.energy.canUseEnergy(1.0)) {
            int n = Short.MIN_VALUE;
            MachineRecipeResult machineRecipeResult = super.getOutput();
            if (machineRecipeResult != null && !this.redstone.hasRedstoneInput()) {
                n = TileEntityCentrifuge.min((short)5000, machineRecipeResult.getRecipe().getMetaData().m_128448_("minHeat"));
                this.workheat = (short)n;
                if (this.heat > n) {
                    this.heat = (short)n;
                }
            } else if (this.heat <= 5000 && this.redstone.hasRedstoneInput()) {
                n = 5000;
                this.workheat = (short)n;
            }
            if (this.heat - 1 < n) {
                this.energy.useEnergy(1.0);
                bl = true;
            }
        }
        this.heat = bl ? (short)(this.heat + 1) : (short)(this.heat - Math.min(this.heat, 1));
    }

    @Override
    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult = super.getOutput();
        if (machineRecipeResult != null) {
            if (machineRecipeResult.getRecipe().getMetaData() == null) {
                return null;
            }
            if (machineRecipeResult.getRecipe().getMetaData().m_128451_("minHeat") > this.heat) {
                return null;
            }
        }
        return machineRecipeResult;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[]{UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing});
    }

    @Override
    public double getGuiValue(String string) {
        if ("heat".equals(string)) {
            return (double)this.heat / (double)this.workheat;
        }
        return super.getGuiValue(string);
    }
}

