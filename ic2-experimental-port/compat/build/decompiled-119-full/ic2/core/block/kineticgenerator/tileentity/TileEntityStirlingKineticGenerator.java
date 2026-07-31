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
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.kineticgenerator.tileentity;

import ic2.api.energy.tile.IHeatSource;
import ic2.api.energy.tile.IKineticSource;
import ic2.api.recipe.ILiquidHeatExchangerManager;
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
import ic2.core.block.kineticgenerator.container.ContainerStirlingKineticGenerator;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.Util;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityStirlingKineticGenerator
extends TileEntityInventory
implements IKineticSource,
IUpgradableBlock,
IHasGui {
    public Ic2FluidTank inputTank;
    public Ic2FluidTank outputTank;
    public InvSlotOutput hotoutputSlot;
    public InvSlotOutput cooloutputSlot;
    public InvSlotConsumableLiquidByTank hotfluidinputSlot;
    public InvSlotConsumableLiquidByManager coolfluidinputSlot;
    public InvSlotUpgrade upgradeSlot;
    private int heatbuffer = 0;
    private final int maxHeatbuffer;
    private int kUBuffer;
    private final int maxkUBuffer;
    private boolean newActive;
    private int liquidHeatStored;
    protected final Fluids fluids = this.addComponent(new Fluids(this));
    private static final int PARTS_KU = 3;
    private static final int PARTS_LIQUID = 1;
    private static final int PARTS_TOTAL = 4;

    public TileEntityStirlingKineticGenerator(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.STIRLING_KINETIC_GENERATOR, blockPos, blockState);
        this.inputTank = this.fluids.addTankInsert("inputTank", 2000, Fluids.fluidPredicate(Recipes.liquidHeatupManager.getSingleDirectionLiquidManager()));
        this.outputTank = this.fluids.addTankExtract("outputTank", 2000);
        this.hotoutputSlot = new InvSlotOutput(this, "hotOutputSlot", 1);
        this.cooloutputSlot = new InvSlotOutput(this, "outputSlot", 1);
        this.coolfluidinputSlot = new InvSlotConsumableLiquidByManager(this, "coolfluidinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, Recipes.liquidHeatupManager.getSingleDirectionLiquidManager());
        this.hotfluidinputSlot = new InvSlotConsumableLiquidByTank(this, "hotfluidoutputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, this.outputTank);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3);
        this.maxHeatbuffer = 1000;
        this.maxkUBuffer = 2000;
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.inputTank.fromNbt(compoundTag.m_128469_("inputTank"));
        this.outputTank.fromNbt(compoundTag.m_128469_("outputTank"));
        this.heatbuffer = compoundTag.m_128451_("heatbuffer");
        this.kUBuffer = compoundTag.m_128451_("kubuffer");
        this.liquidHeatStored = compoundTag.m_128451_("liquidHeatStored");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        CompoundTag compoundTag2 = new CompoundTag();
        this.inputTank.toNbt(compoundTag2);
        compoundTag.m_128365_("inputTank", (Tag)compoundTag2);
        CompoundTag compoundTag3 = new CompoundTag();
        this.outputTank.toNbt(compoundTag3);
        compoundTag.m_128365_("outputTank", (Tag)compoundTag3);
        compoundTag.m_128405_("heatbuffer", this.heatbuffer);
        compoundTag.m_128405_("kUBuffer", this.kUBuffer);
        compoundTag.m_128405_("liquidHeatStored", this.liquidHeatStored);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.coolfluidinputSlot.processIntoTank(this.inputTank, this.cooloutputSlot);
        this.hotfluidinputSlot.processFromTank(this.outputTank, this.hotoutputSlot);
        if (this.heatbuffer < this.maxHeatbuffer) {
            this.heatbuffer += this.drawHu(this.maxHeatbuffer - this.heatbuffer);
        }
        this.newActive = false;
        if (this.inputTank.getFluidAmount() > 0 && this.outputTank.getFluidAmount() < this.outputTank.getCapacity() && Recipes.liquidHeatupManager.getSingleDirectionLiquidManager().acceptsFluid(this.inputTank.getFluidStack().getFluid()) && this.kUBuffer < this.maxkUBuffer) {
            ILiquidHeatExchangerManager.HeatExchangeProperty heatExchangeProperty = Recipes.liquidHeatupManager.getHeatExchangeProperty(this.inputTank.getFluidStack().getFluid());
            if (this.outputTank.isEmpty() || this.outputTank.hasExactFluid(heatExchangeProperty.outputFluid)) {
                int n = this.heatbuffer / 4;
                n = Math.min(n, (Math.min(this.outputTank.getCapacity() - this.outputTank.getFluidAmount(), this.inputTank.getFluidAmount()) * heatExchangeProperty.huPerMB - this.liquidHeatStored) / 1);
                if ((n = Math.min(n, (this.maxkUBuffer - this.kUBuffer) / 3)) > 0) {
                    this.kUBuffer += n * 3 * 4;
                    this.liquidHeatStored += n * 1;
                    this.heatbuffer -= n * 4;
                    this.newActive = true;
                }
                if (this.liquidHeatStored >= heatExchangeProperty.huPerMB) {
                    int n2 = this.liquidHeatStored / heatExchangeProperty.huPerMB;
                    n2 = this.inputTank.drainMbUnchecked(n2, true).getAmountMb();
                    n2 = this.outputTank.fillMbUnchecked(Ic2FluidStack.create(heatExchangeProperty.outputFluid, n2), true);
                    this.liquidHeatStored -= n2 * heatExchangeProperty.huPerMB;
                    this.inputTank.drainMbUnchecked(n2, false);
                    this.outputTank.fillMbUnchecked(Ic2FluidStack.create(heatExchangeProperty.outputFluid, n2), false);
                }
            }
        }
        if (this.getActive() != this.newActive) {
            this.setActive(this.newActive);
        }
        this.upgradeSlot.tick();
    }

    private int drawHu(int n) {
        IHeatSource iHeatSource;
        int n2;
        BlockEntity blockEntity;
        Direction direction;
        if (n <= 0) {
            return 0;
        }
        Level level = this.m_58904_();
        int n3 = n;
        Direction[] directionArray = Util.ALL_DIRS;
        int n4 = directionArray.length;
        for (int i = 0; !(i >= n4 || (direction = directionArray[i]) != this.getFacing() && (blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction))) instanceof IHeatSource && (n2 = (iHeatSource = (IHeatSource)blockEntity).drawHeat(direction.m_122424_(), n3, true)) > 0 && (n3 -= iHeatSource.drawHeat(direction.m_122424_(), n2, false)) <= 0); ++i) {
        }
        return n - n3;
    }

    @Override
    public int maxrequestkineticenergyTick(Direction direction) {
        return Math.min(this.kUBuffer, this.getConnectionBandwidth(direction));
    }

    @Override
    public int getConnectionBandwidth(Direction direction) {
        if (direction != this.getFacing()) {
            return 0;
        }
        return this.maxkUBuffer;
    }

    @Override
    public int requestkineticenergy(Direction direction, int n) {
        return this.drawKineticEnergy(direction, n, false);
    }

    @Override
    public int drawKineticEnergy(Direction direction, int n, boolean bl) {
        if (direction != this.getFacing()) {
            return 0;
        }
        if (n > this.kUBuffer) {
            n = this.kUBuffer;
        }
        if (!bl) {
            this.kUBuffer -= n;
        }
        return n;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing);
    }

    @Override
    public double getEnergy() {
        return 40.0;
    }

    @Override
    public boolean useEnergy(double d) {
        return true;
    }

    public Ic2FluidTank getInputTank() {
        return this.inputTank;
    }

    public Ic2FluidTank getOutputTank() {
        return this.outputTank;
    }

    public ContainerBase<TileEntityStirlingKineticGenerator> createServerScreenHandler(int n, Player player) {
        return new ContainerStirlingKineticGenerator(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerStirlingKineticGenerator(n, inventory, this);
    }
}

