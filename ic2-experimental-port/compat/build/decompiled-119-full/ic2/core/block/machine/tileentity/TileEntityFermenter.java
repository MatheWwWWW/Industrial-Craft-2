/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.energy.tile.IHeatSource;
import ic2.api.recipe.IFermenterRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerFermenter;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.recipe.FermenterRecipeManager;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityFermenter
extends TileEntityInventory
implements IHasGui,
IGuiValueProvider,
IUpgradableBlock {
    private final Ic2FluidTank inputTank;
    private final Ic2FluidTank outputTank;
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

    public TileEntityFermenter(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.FERMENTER, blockPos, blockState);
        this.fluids = this.addComponent(new Fluids(this));
        this.outputTank = this.fluids.addTankExtract("output", 2000);
        this.inputTank = this.fluids.addTankInsert("input", 10000, Fluids.fluidPredicate(Recipes.fermenter));
        this.fluidInputCellOutSlot = new InvSlotOutput(this, "biomassOutput", 1);
        this.fluidOutputCellOutSlot = new InvSlotOutput(this, "biogassOutput", 1);
        this.fertiliserSlot = new InvSlotOutput(this, "output", 1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 2);
        this.fluidOutputCellInSlot = new InvSlotConsumableLiquidByTank(this, "biogasInput", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, this.outputTank);
        this.fluidInputCellInSlot = new InvSlotConsumableLiquidByManager(this, "biomassInput", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, Recipes.fermenter);
    }

    public static void init() {
        Recipes.fermenter = new FermenterRecipeManager();
        Recipes.fermenter.addRecipe(Ic2Fluids.BIOMASS.still, ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/need_amount_biomass_per_run"), ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/hU_per_run"), Ic2Fluids.BIOGAS.still, ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/output_amount_biogas_per_run"));
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.inputTank.fromNbt(compoundTag.m_128469_("inputTank"));
        this.outputTank.fromNbt(compoundTag.m_128469_("outputTank"));
        this.progress = compoundTag.m_128451_("progress");
        this.heatBuffer = compoundTag.m_128451_("heatBuffer");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128365_("inputTank", (Tag)this.inputTank.toNbt(new CompoundTag()));
        compoundTag.m_128365_("outputTank", (Tag)this.outputTank.toNbt(new CompoundTag()));
        compoundTag.m_128405_("progress", this.progress);
        compoundTag.m_128405_("heatBuffer", this.heatBuffer);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.fluidInputCellInSlot.processIntoTank(this.inputTank, this.fluidInputCellOutSlot);
        this.fluidOutputCellInSlot.processFromTank(this.outputTank, this.fluidOutputCellOutSlot);
        this.newActive = this.work();
        if (this.getActive() != this.newActive) {
            this.setActive(this.newActive);
        }
        this.upgradeSlot.tick();
    }

    private boolean work() {
        IFermenterRecipeManager.FermentationProperty fermentationProperty;
        if (this.progress >= this.maxProgress) {
            this.fertiliserSlot.add(new ItemStack((ItemLike)Ic2Items.FERTILIZER));
            this.progress = 0;
        }
        Direction direction = this.getFacing();
        BlockEntity blockEntity = this.m_58904_().m_7702_(this.f_58858_.m_121945_(direction));
        if (blockEntity instanceof IHeatSource && !this.inputTank.isEmpty() && (fermentationProperty = Recipes.fermenter.getFermentationInformation(this.inputTank.getFluidStack().getFluid())) != null && this.inputTank.getFluidAmount() >= fermentationProperty.inputAmount && fermentationProperty.outputAmount <= this.outputTank.getCapacity() - this.outputTank.getFluidAmount()) {
            this.heatBuffer += ((IHeatSource)blockEntity).drawHeat(direction.m_122424_(), 100, false);
            if (this.heatBuffer >= fermentationProperty.heat) {
                this.heatBuffer -= fermentationProperty.heat;
                this.inputTank.drainMbUnchecked(fermentationProperty.inputAmount, false);
                this.outputTank.fillMbUnchecked(fermentationProperty.getOutput(), false);
                this.progress += fermentationProperty.inputAmount;
            }
            return true;
        }
        return false;
    }

    public ContainerBase<TileEntityFermenter> createServerScreenHandler(int n, Player player) {
        return new ContainerFermenter(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerFermenter(n, inventory, this);
    }

    @Override
    public double getGuiValue(String string) {
        if ("heat".equals(string)) {
            IFermenterRecipeManager.FermentationProperty fermentationProperty;
            if (this.heatBuffer == 0) {
                return 0.0;
            }
            double d = ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/hU_per_run");
            if (!this.inputTank.isEmpty() && (fermentationProperty = Recipes.fermenter.getFermentationInformation(this.inputTank.getFluidStack().getFluid())) != null) {
                d = fermentationProperty.heat;
            }
            return (double)this.heatBuffer / d;
        }
        if ("progress".equals(string)) {
            return this.progress == 0 ? 0.0 : (double)this.progress / (double)this.maxProgress;
        }
        throw new IllegalArgumentException("Invalid GUI value: " + string);
    }

    public int gaugeLiquidScaled(int n, int n2) {
        switch (n2) {
            case 0: {
                if (this.inputTank.isEmpty()) {
                    return 0;
                }
                return this.inputTank.getFluidAmount() * n / this.inputTank.getCapacity();
            }
            case 1: {
                if (this.outputTank.isEmpty()) {
                    return 0;
                }
                return this.outputTank.getFluidAmount() * n / this.outputTank.getCapacity();
            }
        }
        return 0;
    }

    public Ic2FluidTank getInputTank() {
        return this.inputTank;
    }

    public Ic2FluidTank getOutputTank() {
        return this.outputTank;
    }

    @Override
    public double getEnergy() {
        return 40.0;
    }

    @Override
    public boolean useEnergy(double d) {
        return true;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing);
    }
}

