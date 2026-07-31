/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerSolarDestiller;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.proxy.EnvProxy;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.util.BiomeUtil;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

@NotClassic
public class TileEntitySolarDestiller
extends TileEntityInventory
implements IHasGui,
IUpgradableBlock {
    public final Ic2FluidTank inputTank;
    public final Ic2FluidTank outputTank;
    private int tickrate;
    private int updateTicker;
    private float skyLight;
    public final InvSlotOutput wateroutputSlot;
    public final InvSlotOutput destiwateroutputSlott;
    public final InvSlotConsumableLiquidByList waterinputSlot;
    public final InvSlotConsumableLiquidByTank destiwaterinputSlot;
    public final InvSlotUpgrade upgradeSlot;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntitySolarDestiller(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.SOLAR_DISTILLER, blockPos, blockState);
        this.inputTank = this.fluids.addTankInsert("inputTank", 10000, Fluids.fluidPredicate(new Fluid[]{net.minecraft.world.level.material.Fluids.f_76193_}));
        this.outputTank = this.fluids.addTankExtract("outputTank", 10000);
        this.waterinputSlot = new InvSlotConsumableLiquidByList(this, "waterInput", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, new Fluid[]{net.minecraft.world.level.material.Fluids.f_76193_});
        this.destiwaterinputSlot = new InvSlotConsumableLiquidByTank(this, "destilledWaterInput", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, this.outputTank);
        this.wateroutputSlot = new InvSlotOutput(this, "waterOutput", 1);
        this.destiwateroutputSlott = new InvSlotOutput(this, "destilledWaterOutput", 1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.tickrate = this.getTickRate();
        this.updateTicker = IC2.random.m_188503_(this.tickrate);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.waterinputSlot.processIntoTank(this.inputTank, this.wateroutputSlot);
        if (++this.updateTicker >= this.tickrate) {
            this.updateSunVisibility();
            if (this.canWork()) {
                this.inputTank.drainMbUnchecked(1, false);
                this.outputTank.fillMbUnchecked(Ic2FluidStack.create(Ic2Fluids.DISTILLED_WATER.still, 1), false);
            }
            this.updateTicker = 0;
        }
        this.destiwaterinputSlot.processFromTank(this.outputTank, this.destiwateroutputSlott);
        this.upgradeSlot.tick();
    }

    public void updateSunVisibility() {
        this.skyLight = TileEntitySolarGenerator.getSkyLight(this.m_58904_(), this.f_58858_.m_7494_());
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerSolarDestiller(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerSolarDestiller(n, inventory, this);
    }

    public int getTickRate() {
        Holder<Biome> holder = BiomeUtil.getBiome((LevelReader)this.m_58904_(), this.f_58858_);
        if (IC2.envProxy.biomeHasType(holder, EnvProxy.BiomeType.HOT)) {
            return 36;
        }
        if (IC2.envProxy.biomeHasType(holder, EnvProxy.BiomeType.COLD)) {
            return 144;
        }
        return 72;
    }

    public int gaugeLiquidScaled(int n, int n2) {
        switch (n2) {
            case 0: {
                if (this.inputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.inputTank.getFluidAmount() * n / this.inputTank.getCapacity();
            }
            case 1: {
                if (this.outputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.outputTank.getFluidAmount() * n / this.outputTank.getCapacity();
            }
        }
        return 0;
    }

    public boolean canWork() {
        return this.inputTank.getFluidAmount() > 0 && this.outputTank.getFluidAmount() < this.outputTank.getCapacity() && (double)this.skyLight > 0.5;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidProducing);
    }

    @Override
    public double getEnergy() {
        return 40.0;
    }

    @Override
    public boolean useEnergy(double d) {
        return true;
    }
}

