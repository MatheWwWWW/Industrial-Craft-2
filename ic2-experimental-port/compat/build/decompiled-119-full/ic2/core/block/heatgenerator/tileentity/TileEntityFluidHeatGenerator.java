/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.block.heatgenerator.tileentity;

import ic2.api.recipe.IFluidHeatManager;
import ic2.api.recipe.Recipes;
import ic2.core.ContainerBase;
import ic2.core.FluidHeatManager;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.heatgenerator.container.ContainerFluidHeatGenerator;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.tileentity.TileEntityHeatSourceInventory;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.util.ConfigUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

@NotClassic
public class TileEntityFluidHeatGenerator
extends TileEntityHeatSourceInventory
implements IHasGui {
    public final InvSlotConsumableLiquid fluidSlot = new InvSlotConsumableLiquidByManager(this, "fluidSlot", 1, Recipes.fluidHeatGenerator);
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);
    @GuiSynced
    protected final Ic2FluidTank fluidTank;
    private short ticker = 0;
    protected int burnAmount = 0;
    protected int production = 0;
    boolean newActive = false;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityFluidHeatGenerator(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityHeatSourceInventory>)Ic2BlockEntities.FLUID_HEAT_GENERATOR, blockPos, blockState);
        this.fluidTank = this.fluids.addTankInsert("fluidTank", 10000, Fluids.fluidPredicate(Recipes.semiFluidGenerator));
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.needsFluid()) {
            bl = this.gainFuel();
        }
        if (bl) {
            this.m_6596_();
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
        if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiogas") > 0.0f) {
            TileEntityFluidHeatGenerator.addFuel(Ic2Fluids.BIOGAS.still, 10, Math.round(32.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidBiogas")));
        }
    }

    public static void addFuel(Fluid fluid, int n, int n2) {
        Recipes.fluidHeatGenerator.addFluid(fluid, n, n2);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.fluidTank.fromNbt(compoundTag.m_128469_("fluidTank"));
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        CompoundTag compoundTag2 = new CompoundTag();
        this.fluidTank.toNbt(compoundTag2);
        compoundTag.m_128365_("fluidTank", (Tag)compoundTag2);
    }

    @Override
    protected int fillHeatBuffer(int n) {
        if (this.isConverting()) {
            if (this.ticker >= 19) {
                this.fluidTank.drainMbUnchecked(this.burnAmount, false);
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

    public ContainerBase<TileEntityFluidHeatGenerator> createServerScreenHandler(int n, Player player) {
        return new ContainerFluidHeatGenerator(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerFluidHeatGenerator(n, inventory, this);
    }

    protected int calcHeatProduction() {
        IFluidHeatManager.BurnProperty burnProperty;
        if (!this.fluidTank.isEmpty() && this.getFluidfromTank() != null && (burnProperty = Recipes.fluidHeatGenerator.getBurnProperty(this.getFluidfromTank())) != null) {
            this.production = burnProperty.heat;
            return this.production;
        }
        this.production = 0;
        return 0;
    }

    protected void calcBurnAmount() {
        IFluidHeatManager.BurnProperty burnProperty;
        if (this.getFluidfromTank() != null && (burnProperty = Recipes.fluidHeatGenerator.getBurnProperty(this.getFluidfromTank())) != null) {
            this.burnAmount = burnProperty.amount;
            return;
        }
        this.burnAmount = 0;
    }

    public Ic2FluidTank getFluidTank() {
        return this.fluidTank;
    }

    public Ic2FluidStack getFluidStackfromTank() {
        return this.fluidTank.getFluidStack();
    }

    public Fluid getFluidfromTank() {
        return this.getFluidStackfromTank().getFluid();
    }

    public int getTankAmount() {
        return this.fluidTank.getFluidAmount();
    }

    public int gaugeLiquidScaled(int n) {
        if (this.fluidTank.getFluidAmount() <= 0) {
            return 0;
        }
        return this.fluidTank.getFluidAmount() * n / this.fluidTank.getCapacity();
    }

    public boolean needsFluid() {
        return this.fluidTank.getFluidAmount() <= this.fluidTank.getCapacity();
    }

    protected boolean gainFuel() {
        if (!this.fluidTank.isEmpty()) {
            this.calcHeatProduction();
            this.calcBurnAmount();
        }
        return this.fluidSlot.processIntoTank(this.fluidTank, this.outputSlot);
    }
}

