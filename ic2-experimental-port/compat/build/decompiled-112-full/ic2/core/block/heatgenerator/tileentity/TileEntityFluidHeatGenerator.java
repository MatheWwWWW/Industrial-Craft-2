/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.heatgenerator.tileentity;

import ic2.api.recipe.IFluidHeatManager;
import ic2.api.recipe.Recipes;
import ic2.core.ContainerBase;
import ic2.core.FluidHeatManager;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityHeatSourceInventory;
import ic2.core.block.comp.Fluids;
import ic2.core.block.heatgenerator.container.ContainerFluidHeatGenerator;
import ic2.core.block.heatgenerator.gui.GuiFluidHeatGenerator;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.util.ConfigUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityFluidHeatGenerator
extends TileEntityHeatSourceInventory
implements IHasGui {
    public final InvSlotConsumableLiquid fluidSlot = new InvSlotConsumableLiquidByManager(this, "fluidSlot", 1, Recipes.fluidHeatGenerator);
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);
    @GuiSynced
    protected final FluidTank fluidTank;
    private short ticker = 0;
    protected int burnAmount = 0;
    protected int production = 0;
    boolean newActive = false;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityFluidHeatGenerator() {
        this.fluidTank = this.fluids.addTankInsert("fluidTank", 10000, Fluids.fluidPredicate(Recipes.semiFluidGenerator));
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        if (this.needsFluid()) {
            needsInvUpdate = this.gainFuel();
        }
        if (needsInvUpdate) {
            this.func_70296_d();
        }
        if (this.getActive() != this.newActive) {
            this.setActive(this.newActive);
        }
    }

    public boolean isConverting() {
        return this.getTankAmount() > 0 && this.HeatBuffer < this.getMaxHeatEmittedPerTick();
    }

    public static void init() {
        Recipes.fluidHeatGenerator = new FluidHeatManager();
        if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidOil") > 0.0f) {
            TileEntityFluidHeatGenerator.addFuel("oil", 10, Math.round(32.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidOil")));
        }
        if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidFuel") > 0.0f) {
            TileEntityFluidHeatGenerator.addFuel("fuel", 5, Math.round(768.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidFuel")));
        }
        if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiomass") > 0.0f) {
            TileEntityFluidHeatGenerator.addFuel("biomass", 20, Math.round(16.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidBiomass")));
        }
        if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBioethanol") > 0.0f) {
            TileEntityFluidHeatGenerator.addFuel("bio.ethanol", 10, Math.round(32.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidBioethanol")));
        }
        if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiogas") > 0.0f) {
            TileEntityFluidHeatGenerator.addFuel("ic2biogas", 10, Math.round(32.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidBiogas")));
        }
    }

    public static void addFuel(String fluidName, int amount, int heat) {
        Recipes.fluidHeatGenerator.addFluid(fluidName, amount, heat);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbttagcompound) {
        super.func_145839_a(nbttagcompound);
        this.fluidTank.readFromNBT(nbttagcompound.func_74775_l("fluidTank"));
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        NBTTagCompound fluidTankTag = new NBTTagCompound();
        this.fluidTank.writeToNBT(fluidTankTag);
        nbt.func_74782_a("fluidTank", (NBTBase)fluidTankTag);
        return nbt;
    }

    @Override
    protected int fillHeatBuffer(int maxAmount) {
        if (this.isConverting()) {
            if (this.ticker >= 19) {
                this.getFluidTank().drain(this.burnAmount, true);
                this.ticker = 0;
            } else {
                this.ticker = (short)(this.ticker + 1);
            }
            this.newActive = true;
            return this.production;
        }
        this.newActive = false;
        return 0;
    }

    @Override
    public int getMaxHeatEmittedPerTick() {
        return this.calcHeatProduction();
    }

    public ContainerBase<TileEntityFluidHeatGenerator> getGuiContainer(EntityPlayer player) {
        return new ContainerFluidHeatGenerator(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiFluidHeatGenerator(new ContainerFluidHeatGenerator(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    protected int calcHeatProduction() {
        IFluidHeatManager.BurnProperty property;
        if (this.fluidTank.getFluid() != null && this.getFluidfromTank() != null && (property = Recipes.fluidHeatGenerator.getBurnProperty(this.getFluidfromTank())) != null) {
            this.production = property.heat;
            return this.production;
        }
        this.production = 0;
        return 0;
    }

    protected void calcBurnAmount() {
        IFluidHeatManager.BurnProperty property;
        if (this.getFluidfromTank() != null && (property = Recipes.fluidHeatGenerator.getBurnProperty(this.getFluidfromTank())) != null) {
            this.burnAmount = property.amount;
            return;
        }
        this.burnAmount = 0;
    }

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }

    public FluidStack getFluidStackfromTank() {
        return this.getFluidTank().getFluid();
    }

    public Fluid getFluidfromTank() {
        return this.getFluidStackfromTank().getFluid();
    }

    public int getTankAmount() {
        return this.getFluidTank().getFluidAmount();
    }

    public int gaugeLiquidScaled(int i) {
        if (this.getFluidTank().getFluidAmount() <= 0) {
            return 0;
        }
        return this.getFluidTank().getFluidAmount() * i / this.getFluidTank().getCapacity();
    }

    public boolean needsFluid() {
        return this.getFluidTank().getFluidAmount() <= this.getFluidTank().getCapacity();
    }

    protected boolean gainFuel() {
        if (this.fluidTank.getFluid() != null) {
            this.calcHeatProduction();
            this.calcBurnAmount();
        }
        return this.fluidSlot.processIntoTank((IFluidTank)this.fluidTank, this.outputSlot);
    }
}

