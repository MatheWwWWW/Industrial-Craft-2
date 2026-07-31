/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTBase
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
import ic2.api.recipe.IFermenterRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerFermenter;
import ic2.core.block.machine.gui.GuiFermenter;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.MainConfig;
import ic2.core.item.type.CropResItemType;
import ic2.core.profile.NotClassic;
import ic2.core.recipe.FermenterRecipeManager;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.util.ConfigUtil;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityFermenter
extends TileEntityInventory
implements IHasGui,
IGuiValueProvider,
IUpgradableBlock {
    private final FluidTank inputTank;
    private final FluidTank outputTank;
    public final InvSlotConsumableLiquidByManager fluidInputCellInSlot;
    public final InvSlotConsumableLiquidByTank fluidOutputCellInSlot;
    public final InvSlotOutput fluidInputCellOutSlot;
    public final InvSlotOutput fluidOutputCellOutSlot;
    public final InvSlotOutput fertiliserSlot;
    public final InvSlotUpgrade upgradeSlot;
    protected final Fluids fluids;
    private int heatBuffer = 0;
    public int progress = 0;
    private final int maxProgress = ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/biomass_per_fertilizier");
    private boolean newActive = false;

    public TileEntityFermenter() {
        this.fluids = this.addComponent(new Fluids(this));
        this.outputTank = this.fluids.addTankExtract("output", 2000);
        this.inputTank = this.fluids.addTankInsert("input", 10000, Fluids.fluidPredicate(Recipes.fermenter));
        this.fluidInputCellOutSlot = new InvSlotOutput(this, "biomassOutput", 1);
        this.fluidOutputCellOutSlot = new InvSlotOutput(this, "biogassOutput", 1);
        this.fertiliserSlot = new InvSlotOutput(this, "output", 1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 2);
        this.fluidOutputCellInSlot = new InvSlotConsumableLiquidByTank(this, "biogasInput", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.outputTank);
        this.fluidInputCellInSlot = new InvSlotConsumableLiquidByManager(this, "biomassInput", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, Recipes.fermenter);
    }

    public static void init() {
        Recipes.fermenter = new FermenterRecipeManager();
        Recipes.fermenter.addRecipe(FluidName.biomass.getName(), ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/need_amount_biomass_per_run"), ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/hU_per_run"), FluidName.biogas.getName(), ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/output_amount_biogas_per_run"));
    }

    @Override
    public void func_145839_a(NBTTagCompound nbttagcompound) {
        super.func_145839_a(nbttagcompound);
        this.inputTank.readFromNBT(nbttagcompound.func_74775_l("inputTank"));
        this.outputTank.readFromNBT(nbttagcompound.func_74775_l("outputTank"));
        this.progress = nbttagcompound.func_74762_e("progress");
        this.heatBuffer = nbttagcompound.func_74762_e("heatBuffer");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74782_a("inputTank", (NBTBase)this.inputTank.writeToNBT(new NBTTagCompound()));
        nbt.func_74782_a("outputTank", (NBTBase)this.outputTank.writeToNBT(new NBTTagCompound()));
        nbt.func_74768_a("progress", this.progress);
        nbt.func_74768_a("heatBuffer", this.heatBuffer);
        return nbt;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.fluidInputCellInSlot.processIntoTank((IFluidTank)this.inputTank, this.fluidInputCellOutSlot);
        this.fluidOutputCellInSlot.processFromTank((IFluidTank)this.outputTank, this.fluidOutputCellOutSlot);
        this.newActive = this.work();
        if (this.getActive() != this.newActive) {
            this.setActive(this.newActive);
        }
        this.upgradeSlot.tick();
    }

    private boolean work() {
        IFermenterRecipeManager.FermentationProperty fp;
        if (this.progress >= this.maxProgress) {
            this.fertiliserSlot.add(ItemName.crop_res.getItemStack(CropResItemType.fertilizer));
            this.progress = 0;
        }
        EnumFacing dir = this.getFacing();
        TileEntity te = this.func_145831_w().func_175625_s(this.field_174879_c.func_177972_a(dir));
        if (te instanceof IHeatSource && this.inputTank.getFluid() != null && (fp = Recipes.fermenter.getFermentationInformation(this.inputTank.getFluid().getFluid())) != null && this.inputTank.getFluidAmount() >= fp.inputAmount && fp.outputAmount <= this.outputTank.getCapacity() - this.outputTank.getFluidAmount()) {
            this.heatBuffer += ((IHeatSource)te).drawHeat(dir.func_176734_d(), 100, false);
            if (this.heatBuffer >= fp.heat) {
                this.heatBuffer -= fp.heat;
                this.inputTank.drainInternal(fp.inputAmount, true);
                this.outputTank.fillInternal(fp.getOutput(), true);
                this.progress += fp.inputAmount;
            }
            return true;
        }
        return false;
    }

    public ContainerBase<TileEntityFermenter> getGuiContainer(EntityPlayer player) {
        return new ContainerFermenter(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiFermenter(new ContainerFermenter(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public double getGuiValue(String name) {
        if ("heat".equals(name)) {
            IFermenterRecipeManager.FermentationProperty fp;
            if (this.heatBuffer == 0) {
                return 0.0;
            }
            double maxHeatBuff = ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/hU_per_run");
            if (this.inputTank.getFluid() != null && (fp = Recipes.fermenter.getFermentationInformation(this.inputTank.getFluid().getFluid())) != null) {
                maxHeatBuff = fp.heat;
            }
            return (double)this.heatBuffer / maxHeatBuff;
        }
        if ("progress".equals(name)) {
            return this.progress == 0 ? 0.0 : (double)this.progress / (double)this.maxProgress;
        }
        throw new IllegalArgumentException("Invalid GUI value: " + name);
    }

    public int gaugeLiquidScaled(int i, int tank) {
        switch (tank) {
            case 0: {
                if (this.inputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.inputTank.getFluidAmount() * i / this.inputTank.getCapacity();
            }
            case 1: {
                if (this.outputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.outputTank.getFluidAmount() * i / this.outputTank.getCapacity();
            }
        }
        return 0;
    }

    public FluidTank getInputTank() {
        return this.inputTank;
    }

    public FluidTank getOutputTank() {
        return this.outputTank;
    }

    @Override
    public double getEnergy() {
        return 40.0;
    }

    @Override
    public boolean useEnergy(double amount) {
        return true;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing);
    }
}

